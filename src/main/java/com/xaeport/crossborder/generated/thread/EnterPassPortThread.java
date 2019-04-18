package com.xaeport.crossborder.generated.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.enterpassport.EnterBasePassPortXML;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.EnterManifestMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * 进口入区核放单报文
 */
public class EnterPassPortThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private EnterManifestMapper enterManifestMapper;
    private AppConfiguration appConfiguration;
    private EnterBasePassPortXML enterBasePassPortXML;

    //无参数的构造方法。
    public EnterPassPortThread() {
    }

    //有参数的构造方法。
    public EnterPassPortThread(EnterManifestMapper enterManifestMapper, AppConfiguration appConfiguration, EnterBasePassPortXML enterBasePassPortXML) {
        this.enterManifestMapper = enterManifestMapper;
        this.appConfiguration = appConfiguration;
        this.enterBasePassPortXML = enterBasePassPortXML;
    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("status", StatusCode.RQHFDSBZ);//在map中添加状态（dataStatus）为：入区核放单申报中（BDDS30）
        paramMap.put("kcstatus", StatusCode.RQKCHFDSBZ);//入区空车核放单申报中（BDDS70）

        PassPortMessage passPortMessage = new PassPortMessage();

        List<PassPortHead> passPortHeadList;
        List<PassPortAcmp> passPortAcmpList;
        List<PassPortList> passPortLists;
        PassportHeadXml passportHeadXml;
        PassportAcmpXml passportAcmpXml;
        PassPortListXml passPortListXml;
        List<PassportAcmpXml> passportAcmpXmlList;
        List<PassPortListXml> passPortListXmlList;
        String etpsPreentNo = null;
        String xmlName;

        while (true) {

            try {
                passPortHeadList = enterManifestMapper.findWaitGenerated(paramMap);

                if (CollectionUtils.isEmpty(passPortHeadList)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成入区核放单报文的数据，等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("入区核放单报文生成器暂停时发生异常", e);
                    }
                    continue;
                }
                for (PassPortHead passPortHead : passPortHeadList) {
                    try {
                        passportHeadXml = new PassportHeadXml();
                        passportAcmpXmlList = new ArrayList<>();

                        xmlName = passPortHead.getEtps_preent_no();
                        etpsPreentNo = passPortHead.getEtps_preent_no();

                        passportHeadXml.setPassportTypecd(passPortHead.getPassport_typecd());
                        passportHeadXml.setMasterCuscd(passPortHead.getMaster_cuscd());
                        passportHeadXml.setDclTypecd(passPortHead.getDcl_typecd());
                        passportHeadXml.setIoTypecd(passPortHead.getIo_typecd());
                        passportHeadXml.setBindTypecd(passPortHead.getBind_typecd());
                        passportHeadXml.setRltTbTypecd(passPortHead.getRlt_tb_typecd());
                        passportHeadXml.setRltNo(passPortHead.getRlt_no());
                        passportHeadXml.setAreainEtpsno(passPortHead.getAreain_etpsno());
                        passportHeadXml.setAreainEtpsNm(passPortHead.getAreain_etps_nm());
                        passportHeadXml.setVehicleNo(passPortHead.getVehicle_no());
                        passportHeadXml.setVehicleIcNo(passPortHead.getVehicle_ic_no());
                        passportHeadXml.setVehicleWt(passPortHead.getVehicle_wt());
                        passportHeadXml.setVehicleFrameWt(passPortHead.getVehicle_frame_wt());
                        passportHeadXml.setContainerWt(passPortHead.getContainer_wt());
                        passportHeadXml.setContainerType(passPortHead.getContainer_type());
                        passportHeadXml.setTotalWt(passPortHead.getTotal_wt());
                        passportHeadXml.setTotalGrossWt(passPortHead.getTotal_gross_wt());
                        passportHeadXml.setTotalNetWt(passPortHead.getTotal_net_wt());
                        passportHeadXml.setDclErConc(passPortHead.getDcl_er_conc());
                        passportHeadXml.setDclEtpsno(passPortHead.getDcl_etpsno());
                        passportHeadXml.setDclEtpsNm(passPortHead.getDcl_etps_nm());
                        passportHeadXml.setInputCode(passPortHead.getInput_code());
                        passportHeadXml.setInputName(passPortHead.getInput_name());
                        passportHeadXml.setEtpsPreentNo(passPortHead.getEtps_preent_no());

                        if (!"3".equals(passPortHead.getBind_typecd())) {
                            //一票一车和一车多票(关联单证)
                            passPortAcmpList = this.enterManifestMapper.queryPassPortAcmpByHeadNo(etpsPreentNo);
                            for (int j = 0; j < passPortAcmpList.size(); j++) {
                                passportAcmpXml = new PassportAcmpXml();
                                passportAcmpXml.setRtlBillTypecd(passPortAcmpList.get(j).getRtl_tb_typecd());
                                passportAcmpXml.setRtlBillNo(passPortAcmpList.get(j).getRtl_no());
                                passportAcmpXmlList.add(passportAcmpXml);
                            }
                            passPortMessage.setPassportHeadXml(passportHeadXml);
                            passPortMessage.setPassportAcmpXmlList(passportAcmpXmlList);
                        } else {
                            //一票多车(表体)
                            passPortListXmlList = new ArrayList<>();
                            passPortLists = this.enterManifestMapper.queryPassPortListByHeadNo(etpsPreentNo);
                            for (int j = 0; j < passPortLists.size(); j++) {
                                passPortListXml = new PassPortListXml();
                                passPortListXml.setPassPort_seqNo(passPortLists.get(j).getPassPort_seqNo());
                                passPortListXml.setGds_mtNo(passPortLists.get(j).getGds_mtNo());
                                passPortListXml.setGdecd(passPortLists.get(j).getGdecd());
                                passPortListXml.setGds_nm(passPortLists.get(j).getGds_nm());
                                passPortListXml.setGross_wt(passPortLists.get(j).getGross_wt());
                                passPortListXml.setNet_wt(passPortLists.get(j).getNet_wt());
                                passPortListXml.setDcl_unitcd(passPortLists.get(j).getDcl_unitcd());
                                passPortListXml.setDcl_qty(passPortLists.get(j).getDcl_qty());
                                passPortListXmlList.add(passPortListXml);
                            }
                            passPortMessage.setPassportHeadXml(passportHeadXml);
                            passPortMessage.setPassPortListXmlList(passPortListXmlList);
                        }
                        passPortMessage.setOperCusRegCode(passPortHead.getDcl_etpsno());

                        //开始生成报文
                        this.entryProcess(passPortMessage, xmlName, passPortHead);
                        if ("6".equals(passPortHead.getPassport_typecd())) {
                            try {
                                // 更新入区空车核放单状态为已申报
                                this.enterManifestMapper.updatePassPortHeadStatus(etpsPreentNo, StatusCode.RQKCHFDYSB);
                                this.logger.debug(String.format("成功更新入区空车核放单[etpsPreentNo: %s]状态为: %s", etpsPreentNo, StatusCode.RQKCHFDYSB));
                            } catch (Exception e) {
                                String exceptionMsg = String.format("更新入区空车核放单[etpsPreentNo: %s]状态时异常", passportHeadXml.getEtpsPreentNo());
                                this.logger.error(exceptionMsg, e);
                            }
                        } else {
                            try {
                                // 更新入区核放单状态为已申报
                                this.enterManifestMapper.updatePassPortHeadStatus(etpsPreentNo, StatusCode.RQHFDYSB);
                                this.logger.debug(String.format("成功更新入区核放单[etpsPreentNo: %s]状态为: %s", etpsPreentNo, StatusCode.RQHFDYSB));
                            } catch (Exception e) {
                                String exceptionMsg = String.format("更新入区核放单[etpsPreentNo: %s]状态时发生异常", passportHeadXml.getEtpsPreentNo());
                                this.logger.error(exceptionMsg, e);
                            }
                        }
                    } catch (Exception e) {
                        String exceptionMsg = String.format("封装入区核放单报文数据[etpsPreentNo: %s]异常", etpsPreentNo);
                        this.logger.error(exceptionMsg, e);
                    }
                }

            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成入区核放单报文时异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("入区核放单报文生成器暂停时发生异常", ie);
                }
            }
        }
    }

    private void entryProcess(PassPortMessage passPortMessage, String xmlName, PassPortHead passPortHead) throws TransformerException, IOException {
        try {
            // 生成申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = "PASSPORT_" + "Enter_" + xmlName + "_" + sdf.format(new Date()) + ".xml";
            EnvelopInfo envelopInfo = this.setEnvelopInfo(xmlName, passPortHead);
            byte[] xmlByte = this.enterBasePassPortXML.createXML(passPortMessage, "EnterPassPort", envelopInfo);//flag
            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("成功生成入区核放单报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("处理入区核放单报文[etpsPreentNo: %s]时异常", xmlName);
            this.logger.error(exceptionMsg, e);
        }
    }

    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "EnterPassport" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("入区核放单报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sascebPath") + File.separator + fileName;
        this.logger.debug(String.format("入区核放单报文发送文件[sascebPath: %s]", sendFilePath));

        String sendWmsFilePath = this.appConfiguration.getXmlPath().get("sendWmsPath") + File.separator + fileName;
        this.logger.debug(String.format("入区核放单报文发送文件[sendWmsPath: %s]", sendWmsFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("入区核放单报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("入区核放单发送完毕" + fileName);
        this.logger.debug(String.format("入区核放单报文发送文件[sascebPath: %s]生成完毕", sendFilePath));

        File sendWmsFile = new File(sendWmsFilePath);
        FileUtils.save(sendWmsFile, xmlByte);
        this.logger.info("入区核放单发送完毕" + fileName);
        this.logger.debug(String.format("入区核放单报文发送文件[sendWmsPath: %s]生成完毕", sendWmsFilePath));
    }

    private EnvelopInfo setEnvelopInfo(String xmlName, PassPortHead passPortHead) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = "PASSPORT_" + "Enter_" + xmlName + "_" + sdf.format(new Date()) + ".zip";
        SimpleDateFormat sdfXml = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        EnvelopInfo envelopInfo = new EnvelopInfo();
        envelopInfo.setVersion("1.0");
        envelopInfo.setMessage_id(passPortHead.getEtps_preent_no());
        envelopInfo.setFile_name(fileName);
        envelopInfo.setMessage_type("SAS121");
        envelopInfo.setSender_id(this.enterManifestMapper.getDxpId(passPortHead.getCrt_ent_id()));
        envelopInfo.setReceiver_id("DXPEDCSAS0000001");
        envelopInfo.setSend_time(sdfXml.format(passPortHead.getDcl_time()));
        envelopInfo.setIc_Card(this.enterManifestMapper.getDclEtpsIcCard(passPortHead.getCrt_ent_id(), passPortHead.getDcl_etpsno()));
        return envelopInfo;
    }
}
