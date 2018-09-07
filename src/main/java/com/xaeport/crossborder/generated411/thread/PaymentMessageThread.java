package com.xaeport.crossborder.generated411.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert411.BasePaymentXml;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.PaymentDeclareMapper;
import com.xaeport.crossborder.data.mapper.UserMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.BusinessUtils;
import com.xaeport.crossborder.tools.FileUtils;
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

public class PaymentMessageThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private PaymentDeclareMapper paymentDeclareMapper;
    private AppConfiguration appConfiguration;
    private BasePaymentXml basePaymentXml;
    private UserMapper userMapper = SpringUtils.getBean(UserMapper.class);

    public PaymentMessageThread(PaymentDeclareMapper paymentDeclareMapper, AppConfiguration appConfiguration, BasePaymentXml basePaymentXml) {
        this.paymentDeclareMapper = paymentDeclareMapper;
        this.appConfiguration = appConfiguration;
        this.basePaymentXml = basePaymentXml;
    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.ZFDSBZ);//支付单申报中

        CEB411Message ceb411Message = new CEB411Message();

        List<ImpPayment> impPaymentLists;
        List<ImpPayment> paymentLists;
        List<PaymentHead> paymentHeadLists;///报文实体类
        PaymentHead paymentHead;
        ImpPayment impPayment;
        String guid;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String xmlHeadGuid = null;
        String nameOrderNo = null;

        while (true) {
            try {
                // 查找支付单申报中的数据
                impPaymentLists = paymentDeclareMapper.findWaitGenerated(paramMap);

                if (CollectionUtils.isEmpty(impPaymentLists)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成支付单报文的数据，等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("支付单报文生成器暂停时发生异常", e);
                    }
                    continue;
                }

                Map<String, List<ImpPayment>> paymentXmlMap = BusinessUtils.getEntIdDataMap(impPaymentLists);

                for (String entId : paymentXmlMap.keySet()) {
                    try {

                        paymentLists = paymentXmlMap.get(entId);
                        paymentHeadLists = new ArrayList<>();

                        for (int i = 0; i < paymentLists.size(); i++) {

                            impPayment = paymentLists.get(i);
                            xmlHeadGuid = paymentLists.get(0).getGuid();
                            nameOrderNo = paymentLists.get(0).getOrder_no();
                            entId = paymentLists.get(0).getEnt_id();

                            guid = impPayment.getGuid();
                            paymentHead = new PaymentHead();
                            paymentHead.setGuid(guid);
                            paymentHead.setEntId(impPayment.getEnt_id());
                            paymentHead.setAppType(impPayment.getApp_type());
                            paymentHead.setAppTime(sdf.format(impPayment.getApp_time()));
                            paymentHead.setAppStatus(impPayment.getApp_status());
                            paymentHead.setPayCode(impPayment.getPay_code());
                            paymentHead.setPayName(impPayment.getPay_name());
                            paymentHead.setPayTransactionId(impPayment.getPay_transaction_id());
                            paymentHead.setOrderNo(impPayment.getOrder_no());
                            paymentHead.setEbpCode(impPayment.getEbp_code());
                            paymentHead.setEbpName(impPayment.getEbp_name());
                            paymentHead.setPayerIdType(impPayment.getPayer_id_type());
                            paymentHead.setPayerIdNumber(impPayment.getPayer_id_number());
                            paymentHead.setPayerName(impPayment.getPayer_name());
                            paymentHead.setTelephone(StringUtils.isEmpty(impPayment.getTelephone()) ? "" : impPayment.getTelephone());
                            paymentHead.setAmountPaid(impPayment.getAmount_paid());
                            paymentHead.setCurrency(impPayment.getCurrency());
                            paymentHead.setPayTime(sdf.format(impPayment.getPay_time()));
                            paymentHead.setNote(StringUtils.isEmpty(impPayment.getNote()) ? "" : impPayment.getNote());

                            try {
                                // 更新支付单状态
                                this.paymentDeclareMapper.updateImpPaymentStatus(guid, StatusCode.ZFDYSB);
                                this.logger.debug(String.format("更新支付单为已申报[guid: %s]状态为: %s", guid, StatusCode.ZFDYSB));
                            } catch (Exception e) {
                                String exceptionMsg = String.format("更改支付单[headGuid: %s]状态时发生异常", paymentHead.getGuid());
                                this.logger.error(exceptionMsg, e);
                            }

                            paymentHeadLists.add(paymentHead);

                        }

                        ceb411Message.setPaymentHeadList(paymentHeadLists);
                        //设置baseTransfer411节点
                        BaseTransfer411 baseTransfer411 = new BaseTransfer411();
                        baseTransfer411 = paymentDeclareMapper.queryCompany(entId);
                        ceb411Message.setBaseTransfer411(baseTransfer411);

                        //开始生成报文
                        this.entryProcess(ceb411Message, nameOrderNo, xmlHeadGuid);

                    } catch (Exception e) {
                        String exceptionMsg = String.format("处理支付单[headGuid: %s]状态时发生异常", entId);
                        this.logger.error(exceptionMsg, e);
                    }
                }

            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成支付单411报文时发生异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("支付单411报文生成器暂停时发生异常", ie);
                }
            }
        }
    }

    private void entryProcess(CEB411Message ceb411Message, String nameOrderNo, String xmlHeadGuid) throws TransformerException, IOException {
        try {
            // 生成支付单申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String fileName = "CEB411Message_" + nameOrderNo + "_" + sdf.format(new Date()) + ".xml";
            byte[] xmlByte = this.basePaymentXml.createXML(ceb411Message, "payment", xmlHeadGuid);
            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("完成生成支付单411申报报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("生成支付单411报文时发生异常");
            this.logger.error(exceptionMsg, e);
        }
    }

    /**
     * 生成Xml文件
     *
     * @param fileName 文件名称
     * @param xmlByte  文件内容Bytes
     */
    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "payment" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("支付单411申报报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
        this.logger.debug(String.format("支付单411申报报文发送文件[sendFilePath: %s]", sendFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("支付单411申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("支付单发送完毕" + fileName);
        this.logger.debug(String.format("支付单411申报报文发送文件[sendFilePath: %s]生成完毕", sendFilePath));
    }

}
