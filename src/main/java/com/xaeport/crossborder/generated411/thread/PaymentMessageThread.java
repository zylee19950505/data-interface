//package com.xaeport.crossborder.generated411.thread;
//
//import com.xaeport.crossborder.configuration.AppConfiguration;
//import com.xaeport.crossborder.convert411.generate.BaseXml;
//import com.xaeport.crossborder.convert411.tool.MessageUtils411;
//import com.xaeport.crossborder.data.entity.CEB411Message;
//import com.xaeport.crossborder.data.entity.ImpPayment;
//import com.xaeport.crossborder.data.entity.PaymentHead;
//import com.xaeport.crossborder.data.mapper.PaymentDeclareMapper;
//import com.xaeport.crossborder.data.mapper.UserMapper;
//import com.xaeport.crossborder.data.status.StatusCode;
//import com.xaeport.crossborder.tools.FileUtils;
//import com.xaeport.crossborder.tools.SpringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.util.CollectionUtils;
//
//import javax.xml.transform.TransformerException;
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class PaymentMessageThread implements Runnable {
//
//    private Log logger = LogFactory.getLog(this.getClass());
//    private PaymentDeclareMapper paymentDeclareMapper;
//    private AppConfiguration appConfiguration;
////    private MessageUtils411 messageUtils;
//    private BaseXml baseXml;
//    private UserMapper userMapper = SpringUtils.getBean(UserMapper.class);
////    private static PaymentMessageThread paymentMessageThread;
////    private EnterpriseMapper enterpriseMapper = SpringUtils.getBean(EnterpriseMapper.class);
//
//    public PaymentMessageThread(PaymentDeclareMapper paymentDeclareMapper, AppConfiguration appConfiguration, BaseXml baseXml) {
//        this.paymentDeclareMapper = paymentDeclareMapper;
//        this.appConfiguration = appConfiguration;
//        this.baseXml = baseXml;
//    }
//
//    @Override
//    public void run() {
//        Map<String, String> paramMap = new HashMap<String, String>();
//        paramMap.put("dataStatus", StatusCode.ZFDSBZ);
//
//        CEB411Message ceb411Message = null;
//        List<PaymentHead> paymentHeadLists;
//        PaymentHead paymentHead;
//        List<ImpPayment> impPaymentList;
//        ImpPayment impPayment;
//
//        List<ImpPayment> impPaymentLists;
//
//        while (true) {
//            try {
//                // 查找支付单申报中的数据
//                impPaymentLists = paymentDeclareMapper.findWaitGenerated(paramMap);
//
//                if (CollectionUtils.isEmpty(impPaymentLists)) {
//                    // 如无待生成数据，则等待3s后重新确认
//                    try {
//                        Thread.sleep(3000);
//                        logger.debug("未发现需生成支付单报文的数据，等待3秒");
//                    } catch (InterruptedException e) {
//                        logger.error("支付单报文生成器暂停时发生异常", e);
//                    }
//                    continue;
//                }
//                paymentHeadLists = new ArrayList<>();
//
//                for (int i = 0; i < impPaymentLists.size(); i++) {
//                    impPayment = impPaymentLists.get(i);
//                    String paymentGuid = impPayment.getGuid();
//                    paymentHead = new PaymentHead();
//                    paymentHead.setGuid(impPayment.getGuid());
//                    paymentHead.setAppType(impPayment.getApp_type());
//                    paymentHead.setAppTime(impPayment.getApp_status());
//                    paymentHead.setPayCode(impPayment.getPay_code());
//                    paymentHead.setPayName(impPayment.getPay_name());
//                    paymentHead.setPayTransactionId(impPayment.getPay_transaction_id());
//                    paymentHead.setOrderNo(impPayment.getOrder_no());
//                    paymentHead.setEbpCode(impPayment.getEbp_code());
//                    paymentHead.setEbpName(impPayment.getEbp_name());
//                    paymentHead.setPayerIdType(impPayment.getPayer_id_type());
//                    paymentHead.setPayerIdNumber(impPayment.getPayer_id_number());
//                    paymentHead.setPayName(impPayment.getPay_name());
//                    paymentHead.setTelephone(impPayment.getTelephone());
//                    paymentHead.setAmountPaid(impPayment.getAmount_paid());
//                    paymentHead.setCurrency(impPayment.getCurrency());
////                    paymentHead.setPayTime(impPayment.getPay_time());
//                    paymentHead.setNote(impPayment.getNote());
//
//                    paymentHeadLists.add(paymentHead);
//                }
//
//
//                //获取head表的id
////                String orderNo = impPayment.getOrder_no();//订单号,用于报文头信息
//                //设置表头
//
//                ceb411Message.setPaymentHeadList(paymentHeadLists);
//                List<ImpPayment> paymentLists = new ArrayList<ImpPayment>();
//                //开始生成报文
////                this.entryProcess(ceb411Message, paymentGuid, orderNo);
//
//            } catch (Exception e) {
//                try {
//                    Thread.sleep(5000);
//                    logger.error("生成支付单报文时发生异常，等待5秒重新开始获取数据", e);
//                } catch (InterruptedException ie) {
//                    logger.error("支付单报文生成器暂停时发生异常", ie);
//                }
//            }
//        }
//    }
//
//    private void entryProcess(CEB411Message ceb411Message, String headGuid, String orderNo) throws TransformerException, IOException {
//        try {
//            // 更新支付单状态
//            this.paymentDeclareMapper.updateImpPaymentStatus(headGuid, StatusCode.ZFDYSB);
//            this.logger.debug(String.format("更新支付单[headGuid: %s]状态为: %s", headGuid, StatusCode.ZFDYSB));
//
//            // 生成支付单申报报文
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
//            String fileName = "CEB411Message_" + orderNo + "_" + sdf.format(new Date()) + ".xml";
//            byte[] xmlByte = this.baseXml.createXML(ceb411Message, "payment");//flag 原来是wayBill
//            saveXmlFile(fileName, xmlByte);
//            this.logger.debug(String.format("完成生成支付单申报报文[fileName: %s]", fileName));
//        } catch (Exception e) {
//            String exceptionMsg = String.format("处理支付单[headGuid: %s]时发生异常", headGuid);
//            this.logger.error(exceptionMsg, e);
//        }
//    }
//
////    @Transactional(rollbackForClassName = "Exception")
////    private boolean updateEntryHeadStatus(String entryHeadId, ImpPayment impPayment) {
////        try {
////            // 更新舱单信息表状态
////            this.paymentDeclareMapper.updateEntryHeadOpStatus(entryHeadId, StatusCode.ZFDYSB);
////            logger.debug(String.format("更新舱单分单[entryhead_id: %s]状态为:%s", entryHeadId, StatusCode.ZFDYSB));
////
////            // 更新状态变化记录表
////            StatusRecord statusRecord = StatusCode.entryHeadRecord(impPayment.getAss_bill_no(), "舱单申报-提交海关申报-分单状态变更", StatusCode.ZFDYSB);
////            this.paymentDeclareMapper.insertManifestStatus(statusRecord);
////            logger.debug(String.format("记录舱单分单[entryhead_id: %s]状态变更信息[sr_id: %s]完毕", entryHeadId, statusRecord.getSr_id()));
////        } catch (Exception e) {
////            String exceptionMsg = String.format("更新舱单[entryHeadId: %s]状态时发生异常", entryHeadId);
////            logger.error(exceptionMsg, e);
////            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
////            return false;
////        }
////        return true;
////    }
//
//    /**
//     * 生成Xml文件
//     *
//     * @param fileName 文件名称
//     * @param xmlByte  文件内容Bytes
//     */
//    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "payment" + File.separator + sdf.format(new Date()) + File.separator + fileName;
//        logger.debug(String.format("支付单单申报报文发送备份文件[backFilePath: %s]", backFilePath));
//
//        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
//        logger.debug(String.format("支付单申报报文发送文件[sendFilePath: %s]", sendFilePath));
//
//        File backupFile = new File(backFilePath);
//        FileUtils.save(backupFile, xmlByte);
//        logger.debug(String.format("支付单申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));
//
//        File sendFile = new File(sendFilePath);
//        FileUtils.save(sendFile, xmlByte);
//        this.logger.info("支付单发送完毕" + fileName);
//        logger.debug(String.format("支付单申报报文发送文件[backFilePath: %s]生成完毕", backFilePath));
//    }
//
////    private PaymentMessageThread() {
////        super();
////    }
////
////    public static PaymentMessageThread getInstance() {
////        if (paymentMessageThread == null) {
////            paymentMessageThread = new PaymentMessageThread();
////        }
////        return paymentMessageThread;
////    }
//}
