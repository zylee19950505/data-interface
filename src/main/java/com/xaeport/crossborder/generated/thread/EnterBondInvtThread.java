package com.xaeport.crossborder.generated.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.enterbondinvt.EnterBaseBondInvtXML;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.EnterInventoryMapper;
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
import java.util.*;

/*
 * 进口入区核注清单报文
 */
public class EnterBondInvtThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private EnterInventoryMapper enterInventoryMapper;
    private AppConfiguration appConfiguration;
    private EnterBaseBondInvtXML enterBaseBondInvtXML;

    //无参数的构造方法。
    public EnterBondInvtThread() {
    }

    //有参数的构造方法。
    public EnterBondInvtThread(EnterInventoryMapper enterInventoryMapper, AppConfiguration appConfiguration, EnterBaseBondInvtXML enterBaseBondInvtXML) {
        this.enterInventoryMapper = enterInventoryMapper;
        this.appConfiguration = appConfiguration;
        this.enterBaseBondInvtXML = enterBaseBondInvtXML;
    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("status", StatusCode.RQHZQDSBZ);//在map中添加状态（dataStatus）为：入区核注清单申报中（BDDS10）
        paramMap.put("impexp_markcd", "I");//入区核注清单的进出口标识

        InvtMessage invtMessage = new InvtMessage();

        List<BondInvtBsc> bondInvtBscList;
        List<BondInvtDt> bondInvtDtList;
        InvtHeadType invtHeadType;
        InvtListType invtListType;
        List<InvtListType> invtListTypeList;
        String headEtpsInnerInvtNo = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String xmlName;

        while (true) {

            try {
                bondInvtBscList = enterInventoryMapper.findWaitGenerated(paramMap);

                if (CollectionUtils.isEmpty(bondInvtBscList)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成出区核注清单报文的数据，中等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("入区核注清单报文生成器暂停时发生异常", e);
                    }
                    continue;
                }

                for (BondInvtBsc bondInvtBsc : bondInvtBscList) {
                    try {
                        invtHeadType = new InvtHeadType();
                        invtListTypeList = new ArrayList<>();

                        xmlName = bondInvtBsc.getEtps_inner_invt_no();
                        //获取海关十位
                        String customs_code = this.enterInventoryMapper.queryEnterCustoms(bondInvtBsc.getCrt_ent_id());
                        headEtpsInnerInvtNo = bondInvtBsc.getEtps_inner_invt_no();
                        invtHeadType.setBondInvtNo(bondInvtBsc.getBond_invt_no());
                        invtHeadType.setPutrecNo(bondInvtBsc.getPutrec_no());
                        invtHeadType.setEtpsInnerInvtNo(bondInvtBsc.getEtps_inner_invt_no());
                        invtHeadType.setBizopEtpsSccd(bondInvtBsc.getBizop_etps_sccd());
                        invtHeadType.setBizopEtpsno(bondInvtBsc.getBizop_etpsno());
                        invtHeadType.setBizopEtpsNm(bondInvtBsc.getBizop_etps_nm());
                        invtHeadType.setRcvgdEtpsno(bondInvtBsc.getRcvgd_etpsno());
                        invtHeadType.setRvsngdEtpsSccd(bondInvtBsc.getRvsngd_etps_sccd());
                        invtHeadType.setRcvgdEtpsNm(bondInvtBsc.getRcvgd_etps_nm());
                        invtHeadType.setDclEtpsSccd(bondInvtBsc.getDcl_etps_sccd());
                        invtHeadType.setDclEtpsno(bondInvtBsc.getDcl_etpsno());
                        invtHeadType.setDclEtpsNm(bondInvtBsc.getDcl_etps_nm());
                        invtHeadType.setRltPutrecNo(bondInvtBsc.getRlt_putrec_no());
                        invtHeadType.setRltInvtNo(bondInvtBsc.getRlt_invt_no());

                        invtHeadType.setInvtDclTime(sdf.format(bondInvtBsc.getInvt_dcl_time()));
                        invtHeadType.setImpexpPortcd(bondInvtBsc.getImpexp_portcd());
                        invtHeadType.setDclPlcCuscd(bondInvtBsc.getDcl_plc_cuscd());
                        invtHeadType.setImpexpMarkcd(bondInvtBsc.getImpexp_markcd());
                        invtHeadType.setMtpckEndprdMarkcd(bondInvtBsc.getMtpck_endprd_markcd());
                        invtHeadType.setSupvModecd(bondInvtBsc.getSupv_modecd());
                        invtHeadType.setTrspModecd(bondInvtBsc.getTrsp_modecd());
                        invtHeadType.setDclcusFlag(bondInvtBsc.getDclcus_flag());
                        invtHeadType.setDclcusTypecd(bondInvtBsc.getDclcus_typecd());
                        invtHeadType.setVrfdedMarkcd(bondInvtBsc.getVrfded_markcd());
                        invtHeadType.setInputCode("6101380018");
                        invtHeadType.setInputName("西安口岸电子科技有限公司");
                        invtHeadType.setInputTime("20181210");
                        invtHeadType.setListStat("");
                        invtHeadType.setCorrEntryDclEtpsNo(bondInvtBsc.getDcl_etpsno());
                        invtHeadType.setCorrEntryDclEtpsNm(bondInvtBsc.getDcl_etps_nm());
                        invtHeadType.setDecType(bondInvtBsc.getDec_type());
                        invtHeadType.setAddTime(sdf.format(bondInvtBsc.getInvt_dcl_time()));
                        invtHeadType.setStshipTrsarvNatcd(bondInvtBsc.getStship_trsarv_natcd());
                        //invtHeadType.setInvtType("8");
                        invtHeadType.setInvtType(bondInvtBsc.getBond_invt_typecd());
                        invtHeadType.setCorr_entry_dcl_etps_sccd(bondInvtBsc.getCorr_entry_dcl_etps_sccd());
                        invtHeadType.setCorr_entry_dcl_etps_no(bondInvtBsc.getCorr_entry_dcl_etps_no());
                        invtHeadType.setCorr_entry_dcl_etps_nm(bondInvtBsc.getCorr_entry_dcl_etps_nm());
                        invtHeadType.setRmk(bondInvtBsc.getRmk());

                        invtHeadType.setOperCusRegCode(customs_code);
                        try {
                            // 更新清单状态
                            this.enterInventoryMapper.updateBondInvtBscStatus(headEtpsInnerInvtNo, StatusCode.RQHZQDYSB);
                            this.logger.debug(String.format("更新入区核注清单[headEtpsInnerInvtNo: %s]状态为: %s", headEtpsInnerInvtNo, StatusCode.RQHZQDYSB));
                        } catch (Exception e) {
                            String exceptionMsg = String.format("更改入区核注清单[headEtpsInnerInvtNo: %s]状态时发生异常", invtHeadType.getEtpsInnerInvtNo());
                            this.logger.error(exceptionMsg, e);
                        }

                        bondInvtDtList = this.enterInventoryMapper.queryBondInvtListByHeadNo(headEtpsInnerInvtNo);

                        for (int j = 0; j < bondInvtDtList.size(); j++) {
                            invtListType = new InvtListType();
                            invtListType.setGdsSeqno(StringUtils.isEmpty(bondInvtDtList.get(j).getGds_seqno()) ? "" : String.valueOf(bondInvtDtList.get(j).getGds_seqno()));
                            invtListType.setPutrecSeqno(StringUtils.isEmpty(bondInvtDtList.get(j).getPutrec_seqno()) ? "" : String.valueOf(bondInvtDtList.get(j).getPutrec_seqno()));
                            invtListType.setGdsMtno(StringUtils.isEmpty(bondInvtDtList.get(j).getGds_mtno()) ? "" : bondInvtDtList.get(j).getGds_mtno());
                            invtListType.setGdecd(StringUtils.isEmpty(bondInvtDtList.get(j).getGdecd()) ? "" : bondInvtDtList.get(j).getGdecd());
                            invtListType.setGdsNm(StringUtils.isEmpty(bondInvtDtList.get(j).getGds_nm()) ? "" : bondInvtDtList.get(j).getGds_nm());
                            invtListType.setGdsSpcfModelDesc(StringUtils.isEmpty(bondInvtDtList.get(j).getGds_spcf_model_desc()) ? "" : bondInvtDtList.get(j).getGds_spcf_model_desc());
                            invtListType.setDclUnitcd(StringUtils.isEmpty(bondInvtDtList.get(j).getDcl_unitcd()) ? "" : bondInvtDtList.get(j).getDcl_unitcd());
                            invtListType.setLawfUnitcd(StringUtils.isEmpty(bondInvtDtList.get(j).getLawf_unitcd()) ? "" : bondInvtDtList.get(j).getLawf_unitcd());
                            invtListType.setSecdLawfUnitcd(StringUtils.isEmpty(bondInvtDtList.get(j).getSecd_lawf_unitcd()) ? "" : bondInvtDtList.get(j).getSecd_lawf_unitcd());
                            invtListType.setNatcd(StringUtils.isEmpty(bondInvtDtList.get(j).getNatcd()) ? "" : bondInvtDtList.get(j).getNatcd());
                            invtListType.setDclUprcAmt(StringUtils.isEmpty(bondInvtDtList.get(j).getDcl_uprc_amt()) ? "" : bondInvtDtList.get(j).getDcl_uprc_amt());
                            invtListType.setDclTotalAmt(StringUtils.isEmpty(bondInvtDtList.get(j).getDcl_total_amt()) ? "" : bondInvtDtList.get(j).getDcl_total_amt());
                            invtListType.setDclCurrcd(StringUtils.isEmpty(bondInvtDtList.get(j).getDcl_currcd()) ? "" : bondInvtDtList.get(j).getDcl_currcd());
                            invtListType.setLawfQty(StringUtils.isEmpty(bondInvtDtList.get(j).getLawf_qty()) ? "" : bondInvtDtList.get(j).getLawf_qty());
                            invtListType.setDclQty(StringUtils.isEmpty(bondInvtDtList.get(j).getDcl_qty()) ? "" : bondInvtDtList.get(j).getDcl_qty());
                            invtListType.setSecdLawfQty(StringUtils.isEmpty(bondInvtDtList.get(j).getSecd_lawf_qty()) ? "" : bondInvtDtList.get(j).getSecd_lawf_qty());
                            invtListType.setGrossWt(StringUtils.isEmpty(bondInvtDtList.get(j).getGross_wt()) ? "" : bondInvtDtList.get(j).getGross_wt());
                            invtListType.setNetWt(StringUtils.isEmpty(bondInvtDtList.get(j).getNet_wt()) ? "" : bondInvtDtList.get(j).getNet_wt());
                            invtListType.setUseCd(StringUtils.isEmpty(bondInvtDtList.get(j).getUsecd()) ? "" : bondInvtDtList.get(j).getUsecd());
                            invtListType.setLvyrlfModecd(StringUtils.isEmpty(bondInvtDtList.get(j).getLvyrlf_modecd()) ? "" : bondInvtDtList.get(j).getLvyrlf_modecd());
                            invtListType.setEntryGdsSeqno(StringUtils.isEmpty(bondInvtDtList.get(j).getEntry_gds_seqno()) ? "" : bondInvtDtList.get(j).getEntry_gds_seqno());
                            invtListType.setDestinationNatcd(StringUtils.isEmpty(bondInvtDtList.get(j).getDestination_natcd()) ? "" : bondInvtDtList.get(j).getDestination_natcd());
                            invtListType.setModfMarkcd(StringUtils.isEmpty(bondInvtDtList.get(j).getModf_markcd()) ? "" : bondInvtDtList.get(j).getModf_markcd());
                            invtListTypeList.add(invtListType);
                        }

                        invtMessage.setInvtHeadType(invtHeadType);
                        invtMessage.setInvtListTypeList(invtListTypeList);
                        invtMessage.setOperCusRegCode("6101380003");
                        invtMessage.setSysId("Z8");

                        //开始生成报文
                        this.entryProcess(invtMessage, xmlName, bondInvtBsc);

                    } catch (Exception e) {
                        String exceptionMsg = String.format("处理企业[headEtpsInnerInvtNo: %s]核注清单数据时发生异常", headEtpsInnerInvtNo);
                        this.logger.error(exceptionMsg, e);
                    }
                }

            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成入区核注清单报文时异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("入区核注清单报文生成器暂停时发生异常", ie);
                }
            }
        }
    }

    private void entryProcess(InvtMessage invtMessage, String xmlName, BondInvtBsc bondInvtBsc) throws TransformerException, IOException {
        try {
            // 生成申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = "INVT_" + "Enter" + xmlName + "_" + sdf.format(new Date()) + ".xml";
            EnvelopInfo envelopInfo = this.setEnvelopInfo(xmlName, bondInvtBsc);
            byte[] xmlByte = this.enterBaseBondInvtXML.createXML(invtMessage, "EnterBondInvt", envelopInfo);//flag
            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("完成入区核注清单清单报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("处理入区核注清单[headGuid: %s]时发生异常", xmlName);
            this.logger.error(exceptionMsg, e);
        }
    }

    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "EnterBondinvt" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("入区核注清单报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sascebPath") + File.separator + fileName;
        this.logger.debug(String.format("入区核注清单报文发送文件[sascebPath: %s]", sendFilePath));

        String sendWmsFilePath = this.appConfiguration.getXmlPath().get("sendWmsPath") + File.separator + fileName;
        this.logger.debug(String.format("入区核注清单报文发送WMS[sendWmsPath: %s]", sendWmsFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("入区核注清单报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("入区核注清单发送完毕" + fileName);
        this.logger.debug(String.format("入区核注清单报文发送文件[sascebPath: %s]生成完毕", sendFilePath));

        File sendWmsFile = new File(sendWmsFilePath);
        FileUtils.save(sendWmsFile, xmlByte);
        this.logger.info("入区核注清单发送完毕" + fileName);
        this.logger.debug(String.format("出区核注清单报文发送WMS[sendWmsPath: %s]生成完毕", sendWmsFilePath));
    }

    private EnvelopInfo setEnvelopInfo(String xmlName, BondInvtBsc bondInvtBsc) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = "INVT_" + "Enter" + xmlName + "_" + sdf.format(new Date()) + ".zip";
        SimpleDateFormat sdfXml = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        EnvelopInfo envelopInfo = new EnvelopInfo();
        envelopInfo.setVersion("1.0");
        envelopInfo.setMessage_id(bondInvtBsc.getEtps_inner_invt_no());
        envelopInfo.setFile_name(fileName);
        envelopInfo.setMessage_type("INV101");
        envelopInfo.setSender_id(this.enterInventoryMapper.getDxpId(bondInvtBsc.getCrt_ent_id()));
        envelopInfo.setReceiver_id("DXPEDCSAS0000001");
        envelopInfo.setSend_time(sdfXml.format(bondInvtBsc.getInvt_dcl_time()));
        envelopInfo.setIc_Card(StringUtils.isEmpty(this.enterInventoryMapper.getIcCard(bondInvtBsc.getCrt_user())) ? "" : this.enterInventoryMapper.getIcCard(bondInvtBsc.getCrt_user()));
        return envelopInfo;
    }
}
