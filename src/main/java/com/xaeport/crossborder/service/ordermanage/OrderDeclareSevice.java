package com.xaeport.crossborder.service.ordermanage;


import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert311.BaseOrderXml;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.OrderDeclareMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.BusinessUtils;
import com.xaeport.crossborder.tools.FileUtils;
import com.xaeport.crossborder.tools.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * 订单申报
 */
@Service
public class OrderDeclareSevice {

    @Autowired
    OrderDeclareMapper orderDeclareMapper;
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    BaseOrderXml baseOrderXml;

    private Log logger = LogFactory.getLog(this.getClass());

    /*
     * 查询订单申报数据
     */
    public List<OrderHeadAndList> queryOrderDeclareList(Map<String, Object> paramMap) {
        return orderDeclareMapper.queryOrderDeclareList(paramMap);
    }

    /*
     * 查询订单申报数据总数
     */
    public Integer queryOrderDeclareCount(Map<String, Object> paramMap) {
        return orderDeclareMapper.queryOrderDeclareCount(paramMap);
    }

    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        boolean flag;
        try {
            orderDeclareMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("处理订单[orderNo: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }

    public boolean orderXmlDownload(Map<String, String> paramMap) {
        boolean flag;
        try {
            orderDeclareMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("生成订单报文[orderNo: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }

    public String OrderXml(String ent_id) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.DDBWSCZ);//订单申报中
        paramMap.put("ent_id", ent_id);//企业唯一id码

        CEB311Message ceb311Message = new CEB311Message();
        List<ImpOrderHead> impOrderHeadLists;
        List<ImpOrderBody> impOrderBodyList;
        List<OrderHead> orderHeadList;
        List<ImpOrderBody> orderListLists;
        ImpOrderHead impOrderHead;
        ImpOrderBody impOrderBody;
        OrderHead orderHead;
        String entId;
        String guid;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        SimpleDateFormat sdflong = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String xmlHeadGuid = null;
        String nameOrderNo = null;
        String orderZipPath = null;

        impOrderHeadLists = orderDeclareMapper.findWaitGeneratedByXml(paramMap);
        double impOrderHeadCount = orderDeclareMapper.findWaitGeneratedByXmlCount(paramMap);

        entId = impOrderHeadLists.get(0).getEnt_id();
        List<File> fileList = new ArrayList<>();

        try {
            orderHeadList = new ArrayList<>();
            orderListLists = new ArrayList<>();
            String orderXmlContent = sdflong.format(new Date());

            String sendFilePath = this.appConfiguration.getXmlPath().get("xmlDownload") + File.separator + orderXmlContent + File.separator;
            for (int i = 0; i < impOrderHeadLists.size(); i++) {
                impOrderHead = impOrderHeadLists.get(i);
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
                // 更新支付单状态
                this.orderDeclareMapper.updateEntryHeadOrderStatus(guid, StatusCode.DDBWXZWC);
                this.logger.debug(String.format("更新订单[guid: %s]状态为: %s", guid, StatusCode.DDBWXZWC));
                orderHeadList.add(orderHead);
                impOrderBodyList = this.orderDeclareMapper.queryOrderListByGuid(guid);
                for (int j = 0; j < impOrderBodyList.size(); j++) {
                    impOrderBody = impOrderBodyList.get(j);
                    orderListLists.add(impOrderBody);
                }
            }

            if (orderHeadList.size() > 0 && !StringUtils.isEmpty(orderHeadList)) {
                int limitCount = 100;
                Integer maxCount = orderHeadList.size();
                if (limitCount < maxCount) {
                    int part = maxCount / limitCount;//分批数
                    this.logger.debug(String.format("共有：" + maxCount + "条，分为" + part + "批"));
                    for (int i = 0; i < part; i++) {
                        nameOrderNo = orderHeadList.get(0).getOrderNo();
                        xmlHeadGuid = orderHeadList.get(0).getGuid();
                        List<OrderHead> listPage = orderHeadList.subList(0, limitCount);
                        ceb311Message.setOrderHeadList(listPage);
                        ceb311Message.setImpOrderBodyList(orderListLists);
                        //设置baseTransfer节点
                        BaseTransfer baseTransfer = orderDeclareMapper.queryCompany(entId);
                        ceb311Message.setBaseTransfer(baseTransfer);

                        //开始生成报文
                        this.logger.debug(String.format("订单申报报文发送文件[sendFilePath: %s]", sendFilePath));
                        String sendFilePathAnd = this.entryProcess(ceb311Message, nameOrderNo, xmlHeadGuid, sendFilePath);
                        fileList.add(new File(sendFilePathAnd));
                        this.logger.debug(String.format("第" + (i + 1) + "次，执行处理" + listPage));
                        orderHeadList.subList(0, limitCount).clear();
                    }
                    if (!orderHeadList.isEmpty()) {
                        nameOrderNo = orderHeadList.get(0).getOrderNo();
                        xmlHeadGuid = orderHeadList.get(0).getGuid();
                        ceb311Message.setOrderHeadList(orderHeadList);
                        ceb311Message.setImpOrderBodyList(orderListLists);
                        //设置baseTransfer节点
                        BaseTransfer baseTransfer = orderDeclareMapper.queryCompany(entId);
                        ceb311Message.setBaseTransfer(baseTransfer);

                        //开始生成报文
                        this.logger.debug(String.format("订单申报报文发送文件[sendFilePath: %s]", sendFilePath));
                        String sendFilePathAnd = this.entryProcess(ceb311Message, nameOrderNo, xmlHeadGuid, sendFilePath);
                        fileList.add(new File(sendFilePathAnd));
                        this.logger.debug(String.format("最后一批数据，执行处理：" + orderHeadList));
                    }
                } else {
                    this.logger.debug(String.format("不需要分批，执行处理：" + orderHeadList));
                    nameOrderNo = orderHeadList.get(0).getOrderNo();
                    xmlHeadGuid = orderHeadList.get(0).getGuid();
                    ceb311Message.setOrderHeadList(orderHeadList);
                    ceb311Message.setImpOrderBodyList(orderListLists);
                    //设置baseTransfer节点
                    BaseTransfer baseTransfer = orderDeclareMapper.queryCompany(entId);
                    ceb311Message.setBaseTransfer(baseTransfer);

                    //开始生成报文
                    this.logger.debug(String.format("订单申报报文发送文件[sendFilePath: %s]", sendFilePath));
                    String sendFilePathAnd = this.entryProcess(ceb311Message, nameOrderNo, xmlHeadGuid, sendFilePath);
                    fileList.add(new File(sendFilePathAnd));
                }
            }
            FileOutputStream fos1 = new FileOutputStream(new File(sendFilePath + "order" + orderXmlContent + ".zip"));
            long zipConsumeTime = ZipUtils.toZip(fileList, fos1);
            this.logger.debug("订单报文压缩完成，耗时" + zipConsumeTime + "ms");
            orderZipPath = sendFilePath + "order" + orderXmlContent + ".zip";
        } catch (Exception e) {
            String exceptionMsg = String.format("订单报文下载生成[entId: %s]时发生异常", entId);
            this.logger.error(exceptionMsg, e);
        }

        return orderZipPath;
    }

    private String entryProcess(CEB311Message ceb311Message, String nameOrderNo, String xmlHeadGuid, String sendFilePath) throws TransformerException, IOException {
        String sendFilePathAnd = "";
        try {
            // 生成订单申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String fileName = "CEB311_" + nameOrderNo + "_" + sdf.format(new Date()) + ".xml";
            byte[] xmlByte = this.baseOrderXml.createXML(ceb311Message, "orderDeclare", xmlHeadGuid);//flag
            saveXmlFile(fileName, xmlByte, sendFilePath);
            this.logger.debug(String.format("完成生成订单申报报文[fileName: %s]", fileName));
            sendFilePathAnd = sendFilePath + fileName;
        } catch (Exception e) {
            String exceptionMsg = String.format("处理订单报文生成[headGuid: %s]时发生异常", xmlHeadGuid);
            this.logger.error(exceptionMsg, e);
        }
        return sendFilePathAnd;
    }

    private void saveXmlFile(String fileName, byte[] xmlByte, String sendFilePath) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "order" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("订单申报报文发送备份文件[backFilePath: %s]", backFilePath));

//        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
//        this.logger.debug(String.format("订单申报报文发送文件[sendFilePath: %s]", sendFilePath));

        String sendFilePathAnd = sendFilePath + fileName;
        this.logger.debug(String.format("订单申报报文发送文件[sendFilePath: %s]", sendFilePathAnd));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("订单申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePathAnd);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("订单发送完毕" + fileName);
        this.logger.debug(String.format("订单申报报文发送文件[sendFilePathAnd: %s]生成完毕", sendFilePathAnd));
    }

}
