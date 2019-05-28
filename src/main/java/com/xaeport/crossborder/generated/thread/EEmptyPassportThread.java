package com.xaeport.crossborder.generated.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.exitpassport.EEmptyPassportXML;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.EEmptyPassportMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EEmptyPassportThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private EEmptyPassportMapper eEmptyPassportMapper;
    private AppConfiguration appConfiguration;
    private EEmptyPassportXML eEmptyPassportXML;

    //无参数的构造方法
    public EEmptyPassportThread() {
    }

    //有参数的构造方法
    public EEmptyPassportThread(EEmptyPassportMapper eEmptyPassportMapper, AppConfiguration appConfiguration, EEmptyPassportXML eEmptyPassportXML) {
        this.eEmptyPassportMapper = eEmptyPassportMapper;
        this.appConfiguration = appConfiguration;
        this.eEmptyPassportXML = eEmptyPassportXML;
    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("status", StatusCode.CQKCHFDSBZ);
        PassPortMessage passPortMessage = new PassPortMessage();
        List<PassPortHead> passPortHeadList;
        PassportHeadXml passportHeadXml;
        String etpsPreentNo = null;
        String xmlName;

        while (true) {
            try {
                passPortHeadList = eEmptyPassportMapper.findWaitGenerated(paramMap);

                if (CollectionUtils.isEmpty(passPortHeadList)) {
                    try {
                        Thread.sleep(5000);
                        this.logger.debug("未发现需生成出区空车核放单数据，等待3秒");
                    } catch (InterruptedException ie) {
                        this.logger.error("出区空车核放单报文生成器暂存时发生异常", ie);
                    }
                }
                for (PassPortHead passPortHead : passPortHeadList) {
                    try {
                        passportHeadXml = new PassportHeadXml();
                        xmlName = passPortHead.getEtps_preent_no();
                        etpsPreentNo = passPortHead.getEtps_preent_no();
                        passportHeadXml.setPassportTypecd(passPortHead.getPassport_typecd());
                        passportHeadXml.setMasterCuscd(passPortHead.getMaster_cuscd());
                        passportHeadXml.setDclTypecd(passPortHead.getDcl_typecd());
                        passportHeadXml.setIoTypecd(passPortHead.getIo_typecd());
                        passportHeadXml.setAreainEtpsno(passPortHead.getAreain_etpsno());
                        passportHeadXml.setAreainEtpsNm(passPortHead.getAreain_etps_nm());
                        passportHeadXml.setVehicleNo(passPortHead.getVehicle_no());
                        passportHeadXml.setVehicleIcNo(passPortHead.getVehicle_ic_no());
                        passportHeadXml.setVehicleWt(passPortHead.getVehicle_wt());
                        passportHeadXml.setVehicleFrameWt(passPortHead.getVehicle_frame_wt());
                        passportHeadXml.setContainerWt(passPortHead.getContainer_wt());
                        passportHeadXml.setContainerType(StringUtils.isEmpty(passPortHead.getContainer_type()) ? "" : passPortHead.getContainer_type());
                        passportHeadXml.setTotalWt(passPortHead.getTotal_wt());
                        passportHeadXml.setTotalGrossWt(passPortHead.getTotal_gross_wt());
                        passportHeadXml.setTotalNetWt(passPortHead.getTotal_net_wt());
                        passportHeadXml.setDclErConc(passPortHead.getDcl_er_conc());
                        passportHeadXml.setDclEtpsno(passPortHead.getDcl_etpsno());
                        passportHeadXml.setDclEtpsNm(passPortHead.getDcl_etps_nm());
                        passportHeadXml.setInputCode(passPortHead.getInput_code());
                        passportHeadXml.setInputName(passPortHead.getInput_name());
                        passportHeadXml.setEtpsPreentNo(passPortHead.getEtps_preent_no());
                        try {
                            this.eEmptyPassportMapper.updatePassportStatus(etpsPreentNo, StatusCode.CQKCHFDYSB);
                            this.logger.info(String.format("成功更新出区空车核放单[etpsPreentNo: %s]状态为: %s", etpsPreentNo, StatusCode.CQKCHFDYSB));
                        } catch (Exception e) {
                            String exceptionMsg = String.format("更新出区空车核放单[etpsPreentNo: %s]状态时异常", etpsPreentNo);
                            this.logger.error(exceptionMsg, e);
                        }
                        passPortMessage.setPassportHeadXml(passportHeadXml);
                        passPortMessage.setOperCusRegCode(passportHeadXml.getDclEtpsno());
                        this.entryProcess(passPortMessage, xmlName, passPortHead);
                    } catch (Exception e) {
                        String exceptionMsg = String.format("封装出区空车核放单报文数据[etpsPreentNo: %s]异常", etpsPreentNo);
                        this.logger.error(exceptionMsg, e);
                    }
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    this.logger.error("生成出区空车核放单报文时异常，等待5秒后重新开始获取数据");
                } catch (InterruptedException ie) {
                    this.logger.error("出区空车核放单报文生成器暂停时发生异常", ie);
                }
            }
        }
    }

    private void entryProcess(PassPortMessage passPortMessage, String xmlName, PassPortHead passPortHead) throws TransformerException, IOException {
        try {
            // 生成申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = "PASSPORT_" + "E_Empty_" + xmlName + "_" + sdf.format(new Date()) + ".xml";
            EnvelopInfo envelopInfo = this.setEnvelopInfo(xmlName, passPortHead);
            byte[] xmlByte = this.eEmptyPassportXML.createXML(passPortMessage, "EEmptyPassport", envelopInfo);
            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("成功生成出区空车核放单报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("处理出区空车核放单报文[etpsPreentNo: %s]异常", xmlName);
            this.logger.error(exceptionMsg, e);
        }
    }

    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "ExitPassport" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("出区空车核放单报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sascebPath") + File.separator + fileName;
        this.logger.debug(String.format("出区空车核放单报文发送文件[sascebPath: %s]", sendFilePath));

        String sendWmsFilePath = this.appConfiguration.getXmlPath().get("sendWmsPath") + File.separator + fileName;
        this.logger.debug(String.format("出区空车核放单报文发送文件[sendWmsPath: %s]", sendWmsFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("出区空车核放单报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("出区空车核放单发送完毕" + fileName);
        this.logger.debug(String.format("出区空车核放单报文发送文件[sascebPath: %s]生成完毕", sendFilePath));

        File sendWmsFile = new File(sendWmsFilePath);
        FileUtils.save(sendWmsFile, xmlByte);
        this.logger.info("出区空车核放单发送完毕" + fileName);
        this.logger.debug(String.format("出区空车核放单报文发送文件[sendWmsPath: %s]生成完毕", sendWmsFilePath));
    }

    private EnvelopInfo setEnvelopInfo(String xmlName, PassPortHead passPortHead) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = "PASSPORT_" + "E_Empty_" + xmlName + "_" + sdf.format(new Date()) + ".zip";
        SimpleDateFormat sdfXml = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        EnvelopInfo envelopInfo = new EnvelopInfo();
        envelopInfo.setVersion("1.0");
        envelopInfo.setMessage_id(passPortHead.getEtps_preent_no());
        envelopInfo.setFile_name(fileName);
        envelopInfo.setMessage_type("SAS121");
        envelopInfo.setSender_id(this.eEmptyPassportMapper.getDxpId(passPortHead.getCrt_ent_id()));
        envelopInfo.setReceiver_id("DXPEDCSAS0000001");
        envelopInfo.setSend_time(sdfXml.format(passPortHead.getDcl_time()));
        envelopInfo.setIc_Card(this.eEmptyPassportMapper.getDclEtpsIcCard(passPortHead.getCrt_ent_id(), passPortHead.getDcl_etpsno()));
        return envelopInfo;
    }

}
