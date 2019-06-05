package com.xaeport.crossborder.generated.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.loginvcombine.LogInvCombineXML;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.LogInvCombineMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.FileUtils;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogInvCombineThread implements Runnable {


    private Log logger = LogFactory.getLog(this.getClass());
    private LogInvCombineMapper logInvCombineMapper;
    private AppConfiguration appConfiguration;
    private LogInvCombineXML logInvCombineXML;

    //无参数的构造方法
    public LogInvCombineThread() {
    }

    //有参数的构造方法
    public LogInvCombineThread(LogInvCombineMapper logInvCombineMapper, AppConfiguration appConfiguration, LogInvCombineXML logInvCombineXML) {
        this.logInvCombineMapper = logInvCombineMapper;
        this.appConfiguration = appConfiguration;
        this.logInvCombineXML = logInvCombineXML;
    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        paramMap.put("mark", StatusCode.YZR);
        //报文结构表
        LogInvCombineMessage logInvCombineMessage;
        List<LogInvCombine> logInvCombineList;
        List<LogInvData> logInvDataList;
        List<LogInvList> logInvLists;
        //清单表，运单表
        ImpInventoryHead impInventoryHead;
        List<ImpInventoryBody> impInventoryBodyList;
        ImpLogistics impLogistics;
        //报文结构数据
        LogInvData logInvData;
        LogInvHead logInvHead;
        LogInvList logInvList;
        //单号,名称
        String billNo = null;
        String orderNo = null;
        String logisticsNo = null;
        String xmlName = null;

        while (true) {
            try {
                logInvCombineList = logInvCombineMapper.findWaitGenerated(paramMap);

                if (CollectionUtils.isEmpty(logInvCombineList)) {
                    try {
                        Thread.sleep(5000);
                        this.logger.debug("未发现需生成的运单清单整合数据，等待3秒");
                    } catch (InterruptedException ie) {
                        this.logger.error("运单清单整合报文生成器暂存时发生异常", ie);
                    }
                    continue;
                }

                logInvDataList = new ArrayList<>();

                for (LogInvCombine logInvCombine : logInvCombineList) {
                    logInvLists = new ArrayList<>();
                    logInvHead = new LogInvHead();
                    try {
                        xmlName = logInvCombine.getLogistics_no();
                        billNo = logInvCombine.getBill_no();
                        orderNo = logInvCombine.getOrder_no();
                        logisticsNo = logInvCombine.getLogistics_no();

                        impInventoryHead = this.logInvCombineMapper.queryInventoryHead(logInvCombine);
                        impInventoryBodyList = this.logInvCombineMapper.queryInventoryBody(logInvCombine);
                        impLogistics = this.logInvCombineMapper.queryLogistics(logInvCombine);

                        //封装表头head数据
                        logInvHead.setCopNo(impInventoryHead.getCop_no());
                        logInvHead.setAppTime(sdf.format(impInventoryHead.getApp_time()));
                        logInvHead.setOrderNo(impInventoryHead.getOrder_no());
                        logInvHead.setLogisticsNo(impInventoryHead.getLogistics_no());
                        logInvHead.setEbcCode(impInventoryHead.getEbc_code());
                        logInvHead.setEbcName(impInventoryHead.getEbc_name());
                        logInvHead.setLogisticsCode(impInventoryHead.getLogistics_code());
                        logInvHead.setLogisticsName(impInventoryHead.getLogistics_name());
                        logInvHead.setBuyerName(impInventoryHead.getBuyer_name());
                        logInvHead.setConsignee(impLogistics.getConsingee());
                        logInvHead.setConsigneeAddress(impLogistics.getConsignee_address());
                        logInvHead.setConsigneeTelephone(impLogistics.getConsignee_telephone());
                        logInvHead.setWeight(impLogistics.getWeight());

                        for (ImpInventoryBody invBody : impInventoryBodyList) {
                            //封装表体list数据
                            logInvList = new LogInvList();
                            logInvList.setItemNo(invBody.getItem_no());
                            logInvList.setQty(invBody.getQty());
                            logInvList.setUnit(invBody.getUnit());
                            logInvLists.add(logInvList);
                        }

                        //把data中的表头及表体数据封装在一起
                        logInvData = new LogInvData();
                        logInvData.setLogInvHead(logInvHead);
                        logInvData.setLogInvLists(logInvLists);

                        try {
                            this.logInvCombineMapper.insertLogInvCombineHis(logInvCombine);
                            this.logInvCombineMapper.deleteLogInvCombine(logInvCombine);
                            this.logger.info(String.format("运单清单整合数据成功存入历史表，并删除数据[billNo: %s,orderNo: %s,logisticsNo: %s]", billNo, orderNo, logisticsNo));
                        } catch (Exception e) {
                            String exceptionMsg = String.format("删除运单清单整合数据[billNo: %s,orderNo: %s,logisticsNo: %s]时异常", billNo, orderNo, logisticsNo);
                            this.logger.error(exceptionMsg, e);
                        }
                        //loginv数据存入List数据中
                        logInvDataList.add(logInvData);
                    } catch (Exception e) {
                        String exceptionMsg = String.format("封装运单清单整合报文数据[billNo: %s,orderNo: %s,logisticsNo: %s]异常", billNo, orderNo, logisticsNo);
                        this.logger.error(exceptionMsg, e);
                    }
                }

                logInvCombineMessage = new LogInvCombineMessage();
                logInvCombineMessage.setLogInvDataList(logInvDataList);
                this.entryProcess(logInvCombineMessage, xmlName, logisticsNo);
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    this.logger.error("生成运单清单整合报文时异常，等待5秒后重新开始获取数据");
                } catch (InterruptedException ie) {
                    this.logger.error("运单清单整合报文生成器暂停时发生异常", ie);
                }
            }
        }
    }

    private void entryProcess(LogInvCombineMessage logInvCombineMessage, String xmlName, String logisticsNo) throws TransformerException, IOException {
        try {
            // 生成申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = "LogInv_" + "Combine_" + xmlName + "_" + sdf.format(new Date()) + ".xml";
            EnvelopInfo envelopInfo = this.setEnvelopInfo();
            byte[] xmlByte = this.logInvCombineXML.createXML(logInvCombineMessage, "LogInv", envelopInfo);
            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("成功生成运单清单整合报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("处理运单清单整合报文[logisticsNo: %s]异常", logisticsNo);
            this.logger.error(exceptionMsg, e);
        }
    }

    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "loginvcombine" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("运单清单整合报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendWmsFilePath = this.appConfiguration.getXmlPath().get("sendWmsPath") + File.separator + fileName;
        this.logger.debug(String.format("运单清单整合报文发送文件[sendWmsPath: %s]", sendWmsFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("运单清单整合报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendWmsFile = new File(sendWmsFilePath);
        FileUtils.save(sendWmsFile, xmlByte);
        this.logger.info("运单清单整合报文发送完毕" + fileName);
        this.logger.debug(String.format("运单清单整合报文发送文件[sendWmsPath: %s]生成完毕", sendWmsFilePath));
    }

    private EnvelopInfo setEnvelopInfo() {
        EnvelopInfo envelopInfo = new EnvelopInfo();
        envelopInfo.setMessage_id(IdUtils.getUUId());
        envelopInfo.setMessage_type("WMSCEB");
        envelopInfo.setSender_id("9009000001");
        envelopInfo.setReceiver_id("DXPENT0000021945");
        return envelopInfo;
    }

}
