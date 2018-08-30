//package com.xaeport.crossborder.generated711.thread;
//
//import com.xaeport.crossborder.configuration.AppConfiguration;
//import com.xaeport.crossborder.convert711.BaseDeliveryDeclareXML;
//import com.xaeport.crossborder.data.entity.*;
//import com.xaeport.crossborder.data.mapper.DeliveryDeclareMapper;
//import com.xaeport.crossborder.data.status.StatusCode;
//import com.xaeport.crossborder.tools.BusinessUtils;
//import com.xaeport.crossborder.tools.FileUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import javax.xml.transform.TransformerException;
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class DeliveryDeclareMessageThread implements Runnable {
//
//    private Log logger = LogFactory.getLog(this.getClass());
//    private DeliveryDeclareMapper deliveryDeclareMapper;
//    private AppConfiguration appConfiguration;
//    private BaseDeliveryDeclareXML baseDeliveryDeclareXML;
//
//    //无参数的构造方法。
//    public DeliveryDeclareMessageThread() {
//    }
//
//    //有参数的构造方法。
//    public DeliveryDeclareMessageThread(DeliveryDeclareMapper deliveryDeclareMapper, AppConfiguration appConfiguration, BaseDeliveryDeclareXML baseDeliveryDeclareXML) {
//        this.deliveryDeclareMapper = deliveryDeclareMapper;
//        this.appConfiguration = appConfiguration;
//        this.baseDeliveryDeclareXML = baseDeliveryDeclareXML;
//    }
//
//    @Override
//    public void run() {
//        Map<String, String> paramMap = new HashMap<String, String>();
//        paramMap.put("dataStatus", StatusCode.RKMXDSBZ);//在map中添加状态（dataStatus）为：入库明细单申报中（CBDS70）
//
//        CEB711Message ceb711Message = new CEB711Message();
//
//        List<ImpDeliveryHead> impDeliveryHeadList;
//        List<ImpDeliveryBody> impDeliveryBodyList;
//        ImpDeliveryBody impDeliveryBody;
//        String guid;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat sdfSfm = new SimpleDateFormat("yyyyMMddhhmmss");
//        String nameCopNo = null;
//
//        while (true) {
//
//            try {
//                impDeliveryHeadList = deliveryDeclareMapper.findWaitGenerated(paramMap);
//
//                if (CollectionUtils.isEmpty(impDeliveryHeadList)) {
//                    // 如无待生成数据，则等待3s后重新确认
//                    try {
//                        Thread.sleep(3000);
//                        logger.debug("未发现需生成入库明细单报文的数据，中等待3秒");
//                    } catch (InterruptedException e) {
//                        logger.error("入库明细单报文生成器暂停时发生异常", e);
//                    }
//                    continue;
//                }
//
//                for (ImpDeliveryHead impDeliveryHead : impDeliveryHeadList) {
//                    try {
//                        guid = impDeliveryHead.getGuid();
//                        nameCopNo = impDeliveryHead.getCop_no();
//
//                        try {
//                            // 更新入库明细单状态
//                            this.deliveryDeclareMapper.updateEntryHeadDetailStatus(guid, StatusCode.RKMXDYSB);
//                            this.logger.debug(String.format("更新入库明细单[guid: %s]状态为: %s", guid, StatusCode.RKMXDYSB));
//                        } catch (Exception e) {
//                            String exceptionMsg = String.format("更改入库明细单[headGuid: %s]状态时发生异常", guid);
//                            this.logger.error(exceptionMsg, e);
//                        }
//
//                        impDeliveryBodyList = this.deliveryDeclareMapper.queryDeliveryDeclareListByGuid(guid);
//
//                        ceb711Message.setImpDeliveryHead(impDeliveryHead);
//                        ceb711Message.setImpDeliveryBodyList(impDeliveryBodyList);
//                        //设置baseTransfer节点
//                        BaseTransfer baseTransfer = deliveryDeclareMapper.queryCompany(impDeliveryHead);
//                        ceb711Message.setBaseTransfer(baseTransfer);
//
//                        //开始生成报文
//                        this.entryProcess(ceb711Message, nameCopNo, guid);
//
//                    } catch (Exception e) {
//                        String exceptionMsg = String.format("处理订单[entId: %s]时发生异常", impDeliveryHead.getEnt_id());
//                        this.logger.error(exceptionMsg, e);
//                    }
//
//                }
//
//            } catch (Exception e) {
//                try {
//                    Thread.sleep(5000);
//                    logger.error("生成入库明细单711报文时发生异常，等待5秒重新开始获取数据", e);
//                } catch (InterruptedException ie) {
//                    logger.error("入库明细单711报文生成器暂停时发生异常", ie);
//                }
//            }
//        }
//    }
//
//    private void entryProcess(CEB711Message ceb711Message, String nameCopNo, String guid) throws TransformerException, IOException {
//        try {
//            // 生成报单申报报文
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
//            String fileName = "CEB711_" + nameCopNo + "_" + sdf.format(new Date()) + ".xml";
//            byte[] xmlByte = this.baseDeliveryDeclareXML.createXML(ceb711Message, "DetailDeclare", guid);//flag
//            saveXmlFile(fileName, xmlByte);
//            this.logger.debug(String.format("完成生成入库明细单711申报报文[fileName: %s]", fileName));
//        } catch (Exception e) {
//            String exceptionMsg = String.format("处理入库明细单[headGuid: %s]时发生异常", guid);
//            this.logger.error(exceptionMsg, e);
//        }
//    }
//
//    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "delivery" + File.separator + sdf.format(new Date()) + File.separator + fileName;
//        this.logger.debug(String.format("入库明细单711申报报文发送备份文件[backFilePath: %s]", backFilePath));
//
//        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
//        this.logger.debug(String.format("入库明细单711申报报文发送文件[sendFilePath: %s]", sendFilePath));
//
//        File backupFile = new File(backFilePath);
//        FileUtils.save(backupFile, xmlByte);
//        this.logger.debug(String.format("入库明细单711申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));
//
//        File sendFile = new File(sendFilePath);
//        FileUtils.save(sendFile, xmlByte);
//        this.logger.info("入库明细单发送完毕" + fileName);
//        this.logger.debug(String.format("入库明细单711申报报文发送文件[backFilePath: %s]生成完毕", backFilePath));
//    }
//}
