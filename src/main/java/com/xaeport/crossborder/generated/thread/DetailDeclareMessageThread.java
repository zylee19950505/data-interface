package com.xaeport.crossborder.generated.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.inventory621.BaseDetailDeclareXML;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.DetailDeclareMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.BusinessUtils;
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

public class DetailDeclareMessageThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private DetailDeclareMapper detailDeclareMapper;
    private AppConfiguration appConfiguration;
    private BaseDetailDeclareXML baseDetailDeclareXML;

    //无参数的构造方法。
    public DetailDeclareMessageThread() {
    }

    //有参数的构造方法。
    public DetailDeclareMessageThread(DetailDeclareMapper detailDeclareMapper, AppConfiguration appConfiguration, BaseDetailDeclareXML baseDetailDeclareXML) {
        this.detailDeclareMapper = detailDeclareMapper;
        this.appConfiguration = appConfiguration;
        this.baseDetailDeclareXML = baseDetailDeclareXML;
    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.QDSBZ);//在map中添加状态（dataStatus）为：清单申报中（CBDS60）

        CEB621Message ceb621Message = new CEB621Message();

        List<ImpInventoryHead> impInventoryHeadLists;
        List<ImpInventoryHead> inventoryHeadLists;
        List<ImpInventoryBody> impInventoryBodyList;
        List<InventoryHead> inventoryHeads;
        List<ImpInventoryBody> inventoryLists;
        ImpInventoryHead impInventoryHead;
        ImpInventoryBody impInventoryBody;
        InventoryHead inventoryHead;
        String guid;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfSfm = new SimpleDateFormat("yyyyMMddHHmmss");
        String xmlHeadGuid = null;
        String nameBillNo = null;

        while (true) {

            try {
                impInventoryHeadLists = detailDeclareMapper.findWaitGenerated(paramMap);

                if (CollectionUtils.isEmpty(impInventoryHeadLists)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成清单报文的数据，中等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("清单报文生成器暂停时发生异常", e);
                    }
                    continue;
                }

                Map<String, List<ImpInventoryHead>> inventoryXmlMap = BusinessUtils.getEntIdInventoryMap(impInventoryHeadLists);

                for (String entId : inventoryXmlMap.keySet()) {
                    try {
                        inventoryHeadLists = inventoryXmlMap.get(entId);
                        inventoryHeads = new ArrayList<>();
                        inventoryLists = new ArrayList<>();

                        for (int i = 0; i < inventoryHeadLists.size(); i++) {

                            impInventoryHead = inventoryHeadLists.get(i);
                            xmlHeadGuid = inventoryHeadLists.get(0).getGuid();
                            nameBillNo = inventoryHeadLists.get(0).getBill_no();
                            entId = inventoryHeadLists.get(0).getEnt_id();
                            guid = impInventoryHead.getGuid();

                            inventoryHead = new InventoryHead();
                            inventoryHead.setGuid(guid);
                            inventoryHead.setAppType(impInventoryHead.getApp_type());
                            inventoryHead.setAppTime(sdfSfm.format(impInventoryHead.getApp_time()));
                            inventoryHead.setAppStatus(impInventoryHead.getApp_status());
                            inventoryHead.setOrderNo(impInventoryHead.getOrder_no());
                            inventoryHead.setEbpCode(impInventoryHead.getEbp_code());
                            inventoryHead.setEbpName(impInventoryHead.getEbp_name());
                            inventoryHead.setEbcCode(impInventoryHead.getEbc_code());
                            inventoryHead.setEbcName(impInventoryHead.getEbc_name());
                            inventoryHead.setLogisticsNo(impInventoryHead.getLogistics_no());
                            inventoryHead.setLogisticsCode(impInventoryHead.getLogistics_code());
                            inventoryHead.setLogisticsName(impInventoryHead.getLogistics_name());
                            inventoryHead.setCopNo(impInventoryHead.getCop_no());
                            inventoryHead.setPreNo(impInventoryHead.getPre_no());
                            inventoryHead.setAssureCode(impInventoryHead.getAssure_code());
                            inventoryHead.setEmsNo(impInventoryHead.getEms_no());
                            inventoryHead.setInvtNo(impInventoryHead.getInvt_no());
                            inventoryHead.setIeFlag(impInventoryHead.getIe_flag());
                            inventoryHead.setDeclTime(sdf.format(impInventoryHead.getApp_time()));
                            inventoryHead.setCustomsCode(impInventoryHead.getCustoms_code());
                            inventoryHead.setPortCode(impInventoryHead.getPort_code());
                            inventoryHead.setIeDate(sdf.format(impInventoryHead.getIe_date()));
                            inventoryHead.setBuyerIdType(impInventoryHead.getBuyer_id_type());
                            inventoryHead.setBuyerIdNumber(impInventoryHead.getBuyer_id_number());
                            inventoryHead.setBuyerName(impInventoryHead.getBuyer_name());
                            inventoryHead.setBuyerTelephone(impInventoryHead.getBuyer_telephone());
                            inventoryHead.setConsigneeAddress(impInventoryHead.getConsignee_address());
                            inventoryHead.setAgentCode(impInventoryHead.getAgent_code());
                            inventoryHead.setAgentName(impInventoryHead.getAgent_name());
                            inventoryHead.setAreaCode(impInventoryHead.getArea_code());
                            inventoryHead.setAreaName(impInventoryHead.getArea_name());
                            inventoryHead.setTradeMode(impInventoryHead.getTrade_mode());
                            inventoryHead.setTrafMode(impInventoryHead.getTraf_mode());
                            inventoryHead.setTrafNo(impInventoryHead.getTraf_no());
                            inventoryHead.setVoyageNo(impInventoryHead.getVoyage_no());
                            inventoryHead.setBillNo(impInventoryHead.getBill_no());
                            inventoryHead.setLoctNo(impInventoryHead.getLoct_no());
                            inventoryHead.setLicenseNo(impInventoryHead.getLicense_no());
                            inventoryHead.setCountry(impInventoryHead.getCountry());
                            inventoryHead.setFreight(impInventoryHead.getFreight());
                            inventoryHead.setInsuredFee(impInventoryHead.getInsured_fee());
                            inventoryHead.setCurrency(impInventoryHead.getCurrency());
                            inventoryHead.setWrapType(impInventoryHead.getWrap_type());
                            inventoryHead.setPackNo(impInventoryHead.getPack_no());
                            inventoryHead.setGrossWeight(impInventoryHead.getGross_weight());
                            inventoryHead.setNetWeight(impInventoryHead.getNet_weight());
                            inventoryHead.setNote(StringUtils.isEmpty(impInventoryHead.getNote()) ? "" : impInventoryHead.getNote());

                            try {
                                // 更新清单状态
                                this.detailDeclareMapper.updateEntryHeadDetailStatus(guid, StatusCode.QDYSB);
                                this.logger.debug(String.format("更新清单[guid: %s]状态为: %s", guid, StatusCode.QDYSB));
                            } catch (Exception e) {
                                String exceptionMsg = String.format("更改订单[headGuid: %s]状态时发生异常", inventoryHead.getGuid());
                                this.logger.error(exceptionMsg, e);
                            }

                            inventoryHeads.add(inventoryHead);
                            impInventoryBodyList = this.detailDeclareMapper.querydetailDeclareListByGuid(guid);

                            for (int j = 0; j < impInventoryBodyList.size(); j++) {
                                impInventoryBody = impInventoryBodyList.get(j);
                                inventoryLists.add(impInventoryBody);
                            }
                        }

                        ceb621Message.setInventoryHeadList(inventoryHeads);
                        ceb621Message.setImpInventoryBodyList(inventoryLists);
                        //设置baseTransfer节点
                        BaseTransfer baseTransfer = detailDeclareMapper.queryCompany(entId);
                        ceb621Message.setBaseTransfer(baseTransfer);

                        //开始生成报文
                        this.entryProcess(ceb621Message, nameBillNo, xmlHeadGuid);

                    } catch (Exception e) {
                        String exceptionMsg = String.format("处理订单[entId: %s]时发生异常", entId);
                        this.logger.error(exceptionMsg, e);
                    }
                }

            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成清单621报文时发生异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("清单621报文生成器暂停时发生异常", ie);
                }
            }
        }
    }

    private void entryProcess(CEB621Message ceb621Message, String nameBillNo, String xmlHeadGuid) throws TransformerException, IOException {
        try {
            // 生成申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = "CEB621_" + nameBillNo + "_" + sdf.format(new Date()) + ".xml";
            byte[] xmlByte = this.baseDetailDeclareXML.createXML(ceb621Message, "DetailDeclare", xmlHeadGuid);//flag
            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("完成生成清单621申报报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("处理清单[headGuid: %s]时发生异常", xmlHeadGuid);
            this.logger.error(exceptionMsg, e);
        }
    }

    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "inventory" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("清单621申报报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
        this.logger.debug(String.format("清单621申报报文发送文件[sendFilePath: %s]", sendFilePath));

//        String sendFilePath = this.appConfiguration.getXmlPath().get("sendInventoryPath") + File.separator + fileName;
//        this.logger.debug(String.format("清单621申报报文发送文件[sendFilePath: %s]", sendFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("清单621申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("清单发送完毕" + fileName);
        this.logger.debug(String.format("清单621申报报文发送文件[sendFilePath: %s]生成完毕", sendFilePath));
    }
}
