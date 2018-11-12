package com.xaeport.crossborder.generated311.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert311.BaseOrderXml;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.OrderDeclareMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.ordermanage.OrderDeclareSevice;
import com.xaeport.crossborder.tools.BusinessUtils;
import com.xaeport.crossborder.tools.FileUtils;
import com.xaeport.crossborder.tools.MessageUtils;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderMessageThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private AppConfiguration appConfiguration = SpringUtils.getBean(AppConfiguration.class);
    private OrderDeclareMapper orderDeclareMapper = SpringUtils.getBean(OrderDeclareMapper.class);
    private BaseOrderXml baseOrderXml = SpringUtils.getBean(BaseOrderXml.class);
    private static OrderMessageThread manifestGenMsgThread;

    public OrderMessageThread(OrderDeclareMapper orderDeclareMapper, AppConfiguration appConfiguration, MessageUtils messageUtils, BaseOrderXml baseOrderXml, OrderDeclareSevice orderDeclareSevice) {
        this.orderDeclareMapper = orderDeclareMapper;
        this.appConfiguration = appConfiguration;
        this.baseOrderXml = baseOrderXml;
    }

    private OrderMessageThread() {
        super();
    }

    public static OrderMessageThread getInstance() {
        if (manifestGenMsgThread == null) {
            manifestGenMsgThread = new OrderMessageThread();
        }
        return manifestGenMsgThread;
    }


    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.DDSBZ);//订单申报中


        CEB311Message ceb311Message = new CEB311Message();

        List<ImpOrderHead> impOrderHeadLists;
        List<ImpOrderHead> orderHeadLists;
        List<ImpOrderBody> impOrderBodyList;
        List<OrderHead> orderHeadList;
        List<ImpOrderBody> orderListLists;
        ImpOrderHead impOrderHead;
        ImpOrderBody impOrderBody;
        OrderHead orderHead;
        String guid;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String xmlHeadGuid = null;
        String nameBillNo = null;

        while (true) {
            try {
                impOrderHeadLists = orderDeclareMapper.findWaitGenerated(paramMap);

                if (CollectionUtils.isEmpty(impOrderHeadLists)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成订单报文的数据，中等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("订单报文生成器暂停时发生异常", e);
                    }
                    continue;
                }

                Map<String, List<ImpOrderHead>> orderXmlMap = BusinessUtils.getEntIdOrderMap(impOrderHeadLists);


                for (String entId : orderXmlMap.keySet()) {
                    try {
                        orderHeadLists = orderXmlMap.get(entId);
                        orderHeadList = new ArrayList<>();
                        orderListLists = new ArrayList<>();

                        for (int i = 0; i < orderHeadLists.size(); i++) {

                            impOrderHead = orderHeadLists.get(i);
                            xmlHeadGuid = orderHeadLists.get(0).getGuid();
                            nameBillNo = orderHeadLists.get(0).getBill_No();
                            entId = orderHeadLists.get(0).getEnt_id();
                            guid = impOrderHead.getGuid();

                            orderHead = new OrderHead();
                            orderHead.setGuid(guid);
                            orderHead.setAppType(impOrderHead.getApp_Type());
                            orderHead.setAppTime(sdf.format(impOrderHead.getApp_Time()));
                            orderHead.setAppStatus(impOrderHead.getApp_Status());
                            orderHead.setOrderType(impOrderHead.getOrder_Type());
                            orderHead.setOrderNo(impOrderHead.getOrder_No());
                            orderHead.setEbpCode(impOrderHead.getEbp_Code());
                            orderHead.setEbpName(impOrderHead.getEbp_Name());
                            orderHead.setEbcCode(impOrderHead.getEbc_Code());
                            orderHead.setEbcName(impOrderHead.getEbc_Name());
                            orderHead.setGoodsValue(impOrderHead.getGoods_Value());
                            orderHead.setFreight(impOrderHead.getFreight());
                            orderHead.setDiscount(impOrderHead.getDiscount());
                            orderHead.setTaxTotal(impOrderHead.getTax_Total());
                            orderHead.setActuralPaid(impOrderHead.getActural_Paid());
                            orderHead.setCurrency(impOrderHead.getCurrency());
                            orderHead.setBuyerRegNo(impOrderHead.getBuyer_Reg_No());
                            orderHead.setBuyerName(impOrderHead.getBuyer_Name());
                            orderHead.setBuyerTelePhone(impOrderHead.getBuyer_TelePhone());
                            orderHead.setBuyerIdType(impOrderHead.getBuyer_Id_Type());
                            orderHead.setBuyerIdNumber(impOrderHead.getBuyer_Id_Number());
                            orderHead.setPayCode(StringUtils.isEmpty(impOrderHead.getPay_Code()) ? "" : impOrderHead.getPay_Code());
                            orderHead.setPayName(StringUtils.isEmpty(impOrderHead.getPayName()) ? "" : impOrderHead.getPayName());
                            orderHead.setPayTransactionId(StringUtils.isEmpty(impOrderHead.getPay_Transaction_Id()) ? "" : impOrderHead.getPay_Transaction_Id());
                            orderHead.setBatchNumbers(StringUtils.isEmpty(impOrderHead.getBatch_Numbers()) ? "" : impOrderHead.getBatch_Numbers());
                            orderHead.setConsignee(impOrderHead.getConsignee());
                            orderHead.setConsigneeTelephone(impOrderHead.getConsignee_Telephone());
                            orderHead.setConsigneeAddress(impOrderHead.getConsignee_Address());
                            orderHead.setConsigneeDistrict(StringUtils.isEmpty(impOrderHead.getConsignee_Ditrict()) ? "" : impOrderHead.getConsignee_Ditrict());
                            orderHead.setNote(StringUtils.isEmpty(impOrderHead.getNote()) ? "" : impOrderHead.getNote());

                            try {
                                // 更新订单单状态
                                this.orderDeclareMapper.updateEntryHeadOrderStatus(guid, StatusCode.DDYSB);
                                this.logger.debug(String.format("更新订单[guid: %s]状态为: %s", guid, StatusCode.DDYSB));
                            } catch (Exception e) {
                                String exceptionMsg = String.format("更改订单[headGuid: %s]状态时发生异常", orderHead.getGuid());
                                this.logger.error(exceptionMsg, e);
                            }

                            orderHeadList.add(orderHead);

                            impOrderBodyList = this.orderDeclareMapper.queryOrderListByGuid(guid);

                            for (int j = 0; j < impOrderBodyList.size(); j++) {
                                impOrderBody = impOrderBodyList.get(j);
                                orderListLists.add(impOrderBody);
                            }
                        }

                        ceb311Message.setOrderHeadList(orderHeadList);
                        ceb311Message.setImpOrderBodyList(orderListLists);
                        //设置baseTransfer节点
                        BaseTransfer baseTransfer = orderDeclareMapper.queryCompany(entId);
                        ceb311Message.setBaseTransfer(baseTransfer);

                        //开始生成报文
                        this.entryProcess(ceb311Message, nameBillNo, xmlHeadGuid);

                    } catch (Exception e) {
                        String exceptionMsg = String.format("处理订单[entId: %s]时发生异常", entId);
                        this.logger.error(exceptionMsg, e);
                    }
                }

            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成订单311报文时发生异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("订单311报文生成器暂停时发生异常", ie);
                }
            }
        }
    }

    private void entryProcess(CEB311Message ceb311Message, String nameBillNo, String xmlHeadGuid) throws TransformerException, IOException {
        try {
            // 生成订单申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String fileName = "CEB311_" + nameBillNo + "_" + sdf.format(new Date()) + ".xml";
            byte[] xmlByte = this.baseOrderXml.createXML(ceb311Message, "orderDeclare", xmlHeadGuid);//flag
            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("完成生成订单申报报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("处理订单报文生成[headGuid: %s]时发生异常", xmlHeadGuid);
            this.logger.error(exceptionMsg, e);
        }
    }

    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "order" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("订单申报报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
        this.logger.debug(String.format("订单申报报文发送文件[sendFilePath: %s]", sendFilePath));

//        String sendFilePath = this.appConfiguration.getXmlPath().get("sendOrderPath") + File.separator + fileName;
//        this.logger.debug(String.format("订单申报报文发送文件[sendFilePath: %s]", sendFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("订单申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("订单发送完毕" + fileName);
        this.logger.debug(String.format("订单申报报文发送文件[sendFilePath: %s]生成完毕", sendFilePath));
    }
}
