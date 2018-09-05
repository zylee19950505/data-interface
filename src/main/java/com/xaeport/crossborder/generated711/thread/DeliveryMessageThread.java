package com.xaeport.crossborder.generated711.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert711.BaseDeliveryXML;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.DeliveryDeclareMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.BusinessUtils;
import com.xaeport.crossborder.tools.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeliveryMessageThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private DeliveryDeclareMapper deliveryDeclareMapper;
    private AppConfiguration appConfiguration;
    private BaseDeliveryXML baseDeliveryXML;

    //无参数的构造方法。
    public DeliveryMessageThread() {
    }

    //有参数的构造方法。
    public DeliveryMessageThread(DeliveryDeclareMapper deliveryDeclareMapper, AppConfiguration appConfiguration, BaseDeliveryXML baseDeliveryXML) {
        this.deliveryDeclareMapper = deliveryDeclareMapper;
        this.appConfiguration = appConfiguration;
        this.baseDeliveryXML = baseDeliveryXML;
    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.RKMXDSBZ);//在map中添加状态（dataStatus）为：入库明细单申报中（CBDS70）

        CEB711Message ceb711Message = new CEB711Message();
        List<ImpDeliveryHead> impDeliveryHeadList;
        List<ImpDeliveryHead> impDeliveryHeadLists;
        ImpDeliveryHead deliveryHead;
        List<ImpDeliveryBody> impDeliveryBodyList;
        ImpDeliveryBody impDeliveryBody;
        String xmlHeadGuid;
        String nameBillNo;
        String entId;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfSfm = new SimpleDateFormat("yyyyMMddhhmmss");

        while (true) {

            try {
                impDeliveryHeadList = deliveryDeclareMapper.findWaitGenerated(paramMap);

                if (CollectionUtils.isEmpty(impDeliveryHeadList)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成入库明细单报文的数据，中等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("入库明细单报文生成器暂停时发生异常", e);
                    }
                    continue;
                }

                Map<String, List<ImpDeliveryHead>> deliveryXmlMap = BusinessUtils.getBillNoDeliveryMap(impDeliveryHeadList);

                for (String billNo : deliveryXmlMap.keySet()) {

                    try {
                        impDeliveryBodyList = new ArrayList<>();
                        impDeliveryHeadLists = deliveryXmlMap.get(billNo);

                        deliveryHead = impDeliveryHeadLists.get(0);
                        xmlHeadGuid = deliveryHead.getGuid();
                        nameBillNo = deliveryHead.getBill_no();
                        entId = deliveryHead.getEnt_id();

                        for (int j = 0; j < impDeliveryHeadLists.size(); j++) {
                            impDeliveryBody = new ImpDeliveryBody();
                            impDeliveryBody.setG_num(j + 1);
                            impDeliveryBody.setLogistics_no(impDeliveryHeadLists.get(j).getLogistics_no());
                            impDeliveryBodyList.add(impDeliveryBody);
                            try {
                                // 更新入库明细单状态
                                this.deliveryDeclareMapper.updateDeliveryStatus(impDeliveryHeadLists.get(j).getGuid(), StatusCode.RKMXDYSB);
                                this.logger.debug(String.format("更新入库明细单[guid: %s]状态为: %s", impDeliveryHeadLists.get(j).getGuid(), StatusCode.RKMXDYSB));
                            } catch (Exception e) {
                                String exceptionMsg = String.format("更改入库明细单[guid: %s]状态时发生异常", impDeliveryHeadLists.get(j).getGuid());
                                this.logger.error(exceptionMsg, e);
                            }
                        }

                        ceb711Message.setImpDeliveryHead(deliveryHead);
                        ceb711Message.setImpDeliveryBodyList(impDeliveryBodyList);
                        //设置baseTransfer节点
                        BaseTransfer baseTransfer = deliveryDeclareMapper.queryCompany(entId);
                        ceb711Message.setBaseTransfer(baseTransfer);
                        //开始生成报文
                        this.entryProcess(ceb711Message, nameBillNo, xmlHeadGuid);

                    } catch (Exception e) {
                        String exceptionMsg = String.format("处理入库明细单[billNo: %s]时发生异常", billNo);
                        this.logger.error(exceptionMsg, e);
                    }
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成入库明细单711报文时发生异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("入库明细单711报文生成器暂停时发生异常", ie);
                }
            }
        }
    }

    private void entryProcess(CEB711Message ceb711Message, String nameBillNo, String xmlHeadGuid) throws TransformerException, IOException {
        try {
            // 生成申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String fileName = "CEB711_" + nameBillNo + "_" + sdf.format(new Date()) + ".xml";
            byte[] xmlByte = this.baseDeliveryXML.createXML(ceb711Message, "DeliveryDeclare", xmlHeadGuid);//flag
            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("完成生成入库明细单711申报报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("处理入库明细单报文[headGuid: %s]时发生异常", xmlHeadGuid);
            this.logger.error(exceptionMsg, e);
        }
    }

    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "delivery" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("入库明细单711申报报文发送备份文件[backFilePath: %s]", backFilePath));

//        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
//        this.logger.debug(String.format("入库明细单711申报报文发送文件[sendFilePath: %s]", sendFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sendDeliveryPath") + File.separator + fileName;
        this.logger.debug(String.format("入库明细单711申报报文发送文件[sendFilePath: %s]", sendFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("入库明细单711申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("入库明细单发送完毕" + fileName);
        this.logger.debug(String.format("入库明细单711申报报文发送文件[backFilePath: %s]生成完毕", sendFilePath));
    }
}
