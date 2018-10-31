package com.xaeport.crossborder.service.detaillistmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert621.BaseDetailDeclareXML;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.DetailDeclareMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.FileUtils;
import com.xaeport.crossborder.tools.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DetailDeclareService {

    @Autowired
    DetailDeclareMapper detailDeclareMapper;
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    BaseDetailDeclareXML baseDetailDeclareXML;

    private Log logger = LogFactory.getLog(this.getClass());

    /*
     * 查询清单申报数据hashCode
     */
    public List<ImpInventory> queryInventoryDeclareList(Map<String, String> paramMap) throws Exception {
        int hashValue;
        String str;
        List<ImpInventory> impInventoryList = this.detailDeclareMapper.queryInventoryDeclareList(paramMap);
        List<ImpInventory> result = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        if(!StringUtils.isEmpty(impInventoryList)){
            for(ImpInventory impInventory :impInventoryList){
                str = String.format("%s%s",impInventory.getBill_no(),impInventory.getSum());
                hashValue = str.hashCode();
                if(list.contains(hashValue)){
                    impInventory.setNo("0");
                    result.add(impInventory);
                }else{
                    list.add(hashValue);
                    impInventory.setNo("1");
                    result.add(impInventory);
                }
            }
        }
        list.clear();

        return result;
    }

    /*
     * 查询清单申报总数
     */
    public Integer queryInventoryDeclareCount(Map<String, String> paramMap) throws Exception {
        return this.detailDeclareMapper.queryInventoryDeclareCount(paramMap);
    }

    /**
     * 更新清单状态
     *
     * @return
     */
    @Transactional
    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.detailDeclareMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("处理清单[orderNo: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }

    public boolean invenXmlDownload(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.detailDeclareMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("生成清单报文[orderNo: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }

    public String invenXml(String ent_id) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.QDBWSCZ);//清单申报中
        paramMap.put("ent_id", ent_id);//企业唯一id码

        CEB621Message ceb621Message = new CEB621Message();
        List<ImpInventoryHead> impInventoryHeadLists;
        List<ImpInventoryBody> impInventoryBodyList;
        List<InventoryHead> inventoryHeads;
        List<ImpInventoryBody> inventoryLists;
        ImpInventoryHead impInventoryHead;
        ImpInventoryBody impInventoryBody;
        InventoryHead inventoryHead;
        String entId;
        String guid;
        SimpleDateFormat sdfshort = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        SimpleDateFormat sdflong = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String xmlHeadGuid = null;
        String nameOrderNo = null;
        String orderZipPath = null;

        impInventoryHeadLists = detailDeclareMapper.findWaitGeneratedByXml(paramMap);

        entId = impInventoryHeadLists.get(0).getEnt_id();
        List<File> fileList = new ArrayList<>();

        try {
            inventoryHeads = new ArrayList<>();
            inventoryLists = new ArrayList<>();
            String invenXmlContent = sdflong.format(new Date());

            String sendFilePath = this.appConfiguration.getXmlPath().get("xmlDownload") + File.separator + invenXmlContent + File.separator;
            for (int i = 0; i < impInventoryHeadLists.size(); i++) {
                impInventoryHead = impInventoryHeadLists.get(i);
                guid = impInventoryHead.getGuid();

                inventoryHead = new InventoryHead();
                inventoryHead.setGuid(guid);
                inventoryHead.setAppType(impInventoryHead.getApp_type());
                inventoryHead.setAppTime(sdf.format(impInventoryHead.getApp_time()));
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
                inventoryHead.setDeclTime(sdfshort.format(impInventoryHead.getApp_time()));
                inventoryHead.setCustomsCode(impInventoryHead.getCustoms_code());
                inventoryHead.setPortCode(impInventoryHead.getPort_code());
                inventoryHead.setIeDate(sdfshort.format(impInventoryHead.getIe_date()));
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
                
                // 更新清单状态
                this.detailDeclareMapper.updateEntryHeadDetailStatus(guid, StatusCode.QDBWXZWC);
                this.logger.debug(String.format("更新清单[guid: %s]状态为: %s", guid, StatusCode.QDBWXZWC));
                inventoryHeads.add(inventoryHead);
                impInventoryBodyList = this.detailDeclareMapper.querydetailDeclareListByGuid(guid);
                for (int j = 0; j < impInventoryBodyList.size(); j++) {
                    impInventoryBody = impInventoryBodyList.get(j);
                    inventoryLists.add(impInventoryBody);
                }
            }

            if (inventoryHeads.size() > 0 && !StringUtils.isEmpty(inventoryHeads)) {
                int limitCount = 100;
                Integer maxCount = inventoryHeads.size();
                if (limitCount < maxCount) {
                    int part = maxCount / limitCount;//分批数
                    this.logger.debug(String.format("共有：" + maxCount + "条，分为" + part + "批"));
                    for (int i = 0; i < part; i++) {
                        nameOrderNo = inventoryHeads.get(0).getOrderNo();
                        xmlHeadGuid = inventoryHeads.get(0).getGuid();
                        List<InventoryHead> listPage = inventoryHeads.subList(0, limitCount);
                        ceb621Message.setInventoryHeadList(listPage);
                        ceb621Message.setImpInventoryBodyList(inventoryLists);
                        //设置baseTransfer节点
                        BaseTransfer baseTransfer = detailDeclareMapper.queryCompany(entId);
                        ceb621Message.setBaseTransfer(baseTransfer);

                        //开始生成报文
                        this.logger.debug(String.format("清单申报报文发送文件[sendFilePath: %s]", sendFilePath));
                        String sendFilePathAnd = this.entryProcess(ceb621Message, nameOrderNo, xmlHeadGuid, sendFilePath);
                        fileList.add(new File(sendFilePathAnd));
                        this.logger.debug(String.format("第" + (i + 1) + "次，执行处理" + listPage));
                        inventoryHeads.subList(0, limitCount).clear();
                    }
                    if (!inventoryHeads.isEmpty()) {
                        nameOrderNo = inventoryHeads.get(0).getOrderNo();
                        xmlHeadGuid = inventoryHeads.get(0).getGuid();
                        ceb621Message.setInventoryHeadList(inventoryHeads);
                        ceb621Message.setImpInventoryBodyList(inventoryLists);
                        //设置baseTransfer节点
                        BaseTransfer baseTransfer = detailDeclareMapper.queryCompany(entId);
                        ceb621Message.setBaseTransfer(baseTransfer);

                        //开始生成报文
                        this.logger.debug(String.format("清单申报报文发送文件[sendFilePath: %s]", sendFilePath));
                        String sendFilePathAnd = this.entryProcess(ceb621Message, nameOrderNo, xmlHeadGuid, sendFilePath);
                        fileList.add(new File(sendFilePathAnd));
                        this.logger.debug(String.format("最后一批数据，执行处理：" + inventoryHeads));
                    }
                } else {
                    this.logger.debug(String.format("不需要分批，执行处理：" + inventoryHeads));
                    nameOrderNo = inventoryHeads.get(0).getOrderNo();
                    xmlHeadGuid = inventoryHeads.get(0).getGuid();
                    ceb621Message.setInventoryHeadList(inventoryHeads);
                    ceb621Message.setImpInventoryBodyList(inventoryLists);
                    //设置baseTransfer节点
                    BaseTransfer baseTransfer = detailDeclareMapper.queryCompany(entId);
                    ceb621Message.setBaseTransfer(baseTransfer);

                    //开始生成报文
                    this.logger.debug(String.format("清单申报报文发送文件[sendFilePath: %s]", sendFilePath));
                    String sendFilePathAnd = this.entryProcess(ceb621Message, nameOrderNo, xmlHeadGuid, sendFilePath);
                    fileList.add(new File(sendFilePathAnd));
                }
            }
            FileOutputStream fos1 = new FileOutputStream(new File(sendFilePath + "Inventory" + invenXmlContent + ".zip"));
            long zipConsumeTime = ZipUtils.toZip(fileList, fos1);
            this.logger.debug("清单报文压缩完成，耗时" + zipConsumeTime + "ms");
            orderZipPath = sendFilePath + "Inventory" + invenXmlContent + ".zip";
        } catch (Exception e) {
            String exceptionMsg = String.format("清单报文下载生成[entId: %s]时发生异常", entId);
            this.logger.error(exceptionMsg, e);
        }

        return orderZipPath;
    }

    private String entryProcess(CEB621Message ceb621Message, String nameOrderNo, String xmlHeadGuid, String sendFilePath) throws TransformerException, IOException {
        String sendFilePathAnd = "";
        try {
            // 生成清单申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String fileName = "CEB621_" + nameOrderNo + "_" + sdf.format(new Date()) + ".xml";
            byte[] xmlByte = this.baseDetailDeclareXML.createXML(ceb621Message, "DetailDeclare", xmlHeadGuid);//flag
            saveXmlFile(fileName, xmlByte, sendFilePath);
            this.logger.debug(String.format("完成生成清单申报报文[fileName: %s]", fileName));
            sendFilePathAnd = sendFilePath + fileName;
        } catch (Exception e) {
            String exceptionMsg = String.format("处理清单报文生成[headGuid: %s]时发生异常", xmlHeadGuid);
            this.logger.error(exceptionMsg, e);
        }
        return sendFilePathAnd;
    }

    private void saveXmlFile(String fileName, byte[] xmlByte, String sendFilePath) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "Inventory" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("清单申报报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePathAnd = sendFilePath + fileName;
        this.logger.debug(String.format("清单申报报文发送文件[sendFilePath: %s]", sendFilePathAnd));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("清单申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePathAnd);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("清单发送完毕" + fileName);
        this.logger.debug(String.format("清单申报报文发送文件[sendFilePathAnd: %s]生成完毕", sendFilePathAnd));
    }
    

}
