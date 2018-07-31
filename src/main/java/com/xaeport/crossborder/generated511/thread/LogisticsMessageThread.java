package com.xaeport.crossborder.generated511.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert511.generate.BaseLogisticsXml;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.PaymentDeclareMapper;
import com.xaeport.crossborder.data.mapper.UserMapper;
import com.xaeport.crossborder.data.mapper.WaybillDeclareMapper;
import com.xaeport.crossborder.data.status.StatusCode;
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

public class LogisticsMessageThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private WaybillDeclareMapper waybillDeclareMapper;
    private AppConfiguration appConfiguration;
    private BaseLogisticsXml baseLogisticsXml;
    private UserMapper userMapper = SpringUtils.getBean(UserMapper.class);

    //无参数的构造方法。
    public LogisticsMessageThread() {
    }
    //有参数的构造方法。
    public LogisticsMessageThread(WaybillDeclareMapper waybillDeclareMapper, AppConfiguration appConfiguration, BaseLogisticsXml baseLogisticsXml) {
        this.waybillDeclareMapper = waybillDeclareMapper;
        this.appConfiguration = appConfiguration;
        this.baseLogisticsXml = baseLogisticsXml;
    }



    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.YDSBZ);//在map中添加状态（dataStatus）为：运单申报中（CBDS40）

        CEB511Message ceb511Message = new CEB511Message();

        List<ImpLogistics> impLogisticsLists;
        List<LogisticsHead> logisticsHeadsLists;
        LogisticsHead logisticsHead;
        ImpLogistics impLogistics;
        String guid;


        while (true) {
            try {
                // 查找运单申报中的数据
                impLogisticsLists = waybillDeclareMapper.findWaitGenerated(paramMap);




                if (CollectionUtils.isEmpty(impLogisticsLists)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成运单报文的数据，等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("运单报文生成器暂停时发生异常", e);
                    }
                    continue;
                }
                logisticsHeadsLists = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                String nameLogisticsNo = null;
                String xmlHeadGuid = null;

                for (int i = 0; i < impLogisticsLists.size(); i++) {
                    impLogistics = impLogisticsLists.get(i);
                    xmlHeadGuid = impLogisticsLists.get(0).getGuid();
                    nameLogisticsNo = impLogisticsLists.get(0).getLogistics_no();
                    guid = impLogistics.getGuid();
                    logisticsHead = new LogisticsHead();

                    logisticsHead.setGuid(guid);//企业系统生成36 位唯一序号（英文字母大写）
                    logisticsHead.setAppType(impLogistics.getApp_type());//企业报送类型。1-新增2-变更3-删除。默认为1。
                    logisticsHead.setAppTime(sdf.format(impLogistics.getApp_time()));//企业报送时间。格式:YYYYMMDDhhmmss。
                    logisticsHead.setAppStatus(impLogistics.getApp_status());//业务状态:1-暂存,2-申报,默认为2。
                    logisticsHead.setLogisticsCode(impLogistics.getLogistics_code());//物流企业的海关注册登记编号。
                    logisticsHead.setLogisticsName(impLogistics.getLogistics_name());//物流企业在海关注册登记的名称。
                    logisticsHead.setLogisticsNo(impLogistics.getLogistics_no());//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
                    logisticsHead.setBillNo(impLogistics.getBill_no());//直购进口为海运提单、空运总单或汽车载货清单
                    logisticsHead.setFreight(StringUtils.isEmpty(impLogistics.getFreight()) ? "0" : impLogistics.getFreight());//商品运输费用，无则填0。
                    logisticsHead.setInsuredFee(StringUtils.isEmpty(impLogistics.getInsured_fee()) ? "0" : impLogistics.getInsured_fee());//商品保险费用，无则填0。
                    logisticsHead.setCurrency(impLogistics.getCurrency());//限定为人民币，填写142。
                    logisticsHead.setWeight(impLogistics.getWeight());//单位为千克。
                    logisticsHead.setPackNo(impLogistics.getPack_no());//单个运单下包裹数，限定为1。
                    logisticsHead.setGoodsInfo(impLogistics.getGoods_info());//配送的商品信息，包括商品名称、数量等。
                    logisticsHead.setConsingee(impLogistics.getConsingee());//收货人姓名。
                    logisticsHead.setConsigneeAddress(impLogistics.getConsignee_address());//收货地址。
                    logisticsHead.setConsigneeTelephone(impLogistics.getConsignee_telephone());//收货人电话号码。
                    logisticsHead.setNote(impLogistics.getNote());//备注
                    try {
                        // 更新支付单状态
                        this.waybillDeclareMapper.updateImpLogisticsStatus(guid, StatusCode.YDYSB);
                        this.logger.debug(String.format("更新运单为已申报[guid: %s]状态为: %s", guid, StatusCode.YDYSB));
                    } catch (Exception e) {
                        String exceptionMsg = String.format("更改运单[headGuid: %s]状态时发生异常", logisticsHead.getGuid());
                        this.logger.error(exceptionMsg, e);
                    }
                    logisticsHeadsLists.add(logisticsHead);
                }

                ceb511Message.setLogisticsHeadList(logisticsHeadsLists);
                //开始生成报文
                this.entryProcess(ceb511Message, nameLogisticsNo, xmlHeadGuid);

            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成支付单报文时发生异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("支付单报文生成器暂停时发生异常", ie);
                }
            }

        }
    }

    private void entryProcess(CEB511Message ceb511Message, String nameLogisticsNo, String xmlHeadGuid) throws TransformerException, IOException {
        try {
            // 生成支付单申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String fileName = "CEB511Message_" + nameLogisticsNo + "_" + sdf.format(new Date()) + ".xml";

            byte[] xmlByte = this.baseLogisticsXml.createXML(ceb511Message, "logistics", xmlHeadGuid);

            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("完成生成运单申报报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("生成运单报文时发生异常");
            this.logger.error(exceptionMsg, e);
        }
    }

//    @Transactional(rollbackForClassName = "Exception")
//    private boolean updateEntryHeadStatus(String entryHeadId, ImpPayment impPayment) {
//        try {
//            // 更新舱单信息表状态
//            this.paymentDeclareMapper.updateEntryHeadOpStatus(entryHeadId, StatusCode.ZFDYSB);
//            logger.debug(String.format("更新舱单分单[entryhead_id: %s]状态为:%s", entryHeadId, StatusCode.ZFDYSB));
//
//            // 更新状态变化记录表
//            StatusRecord statusRecord = StatusCode.entryHeadRecord(impPayment.getAss_bill_no(), "舱单申报-提交海关申报-分单状态变更", StatusCode.ZFDYSB);
//            this.paymentDeclareMapper.insertManifestStatus(statusRecord);
//            logger.debug(String.format("记录舱单分单[entryhead_id: %s]状态变更信息[sr_id: %s]完毕", entryHeadId, statusRecord.getSr_id()));
//        } catch (Exception e) {
//            String exceptionMsg = String.format("更新舱单[entryHeadId: %s]状态时发生异常", entryHeadId);
//            logger.error(exceptionMsg, e);
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return false;
//        }
//        return true;
//    }

    /**
     * 生成Xml文件
     *
     * @param fileName 文件名称
     * @param xmlByte  文件内容Bytes
     */
    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "logistics" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("支付单单申报报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
        this.logger.debug(String.format("支付单申报报文发送文件[sendFilePath: %s]", sendFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("支付单申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("支付单发送完毕" + fileName);
        this.logger.debug(String.format("支付单申报报文发送文件[backFilePath: %s]生成完毕", backFilePath));
    }

//    private PaymentMessageThread() {
//        super();
//    }
//
//    public static PaymentMessageThread getInstance() {
//        if (paymentMessageThread == null) {
//            paymentMessageThread = new PaymentMessageThread();
//        }
//        return paymentMessageThread;
//    }
}
