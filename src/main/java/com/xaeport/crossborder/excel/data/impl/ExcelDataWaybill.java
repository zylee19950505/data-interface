package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.headings.ExcelHeadWaybill;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import java.util.*;

public class ExcelDataWaybill implements ExcelData {
    private Log log = LogFactory.getLog(this.getClass());
    private int  logisticsNoIndex; //物流运单编号";//head
    private int  logisticsCodeIndex; //物流企业代码";//head
    private int  logisticsNameIndex; //物流企业名称";//head
    private int  consigneeIndex; //收货人姓名";//head
    private int  consigneeTelephoneIndex; //收货人电话";//head
    private int  consigneeAddressIndex; //收件地址";//head
    private int  freightIndex; //运费";//head
    private int  insuredFeeIndex; //保价费";//head
    private int  grossWeightIndex; //毛重";//head
    private int  noteIndex; //备注";//head



    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
//        List<ImpOrderBody> impOrderBodyList = new ArrayList<>();
//        Map<String, List<String>> impOrderHeadMap = new LinkedHashMap<>();
//        ImpOrderBody impOrderBody;
//        Map<String, String> temporary = new HashMap<>();//用于存放临时计算值
//        this.getIndexValue(excelData.get(0));//初始化表头索引
//        for (int i = 1, length = excelData.size(); i < length; i++) {
//            impOrderBody = new ImpOrderBody();
//            impOrderHeadMap = this.getMergeData(excelData.get(i), impOrderHeadMap);
//            impOrderBodyList = this.impOrderBodyData(excelData.get(i), impOrderBody, impOrderBodyList);
//        }
//
//        List<ImpOrderHead> impOrderHeadList = this.getOrderHeadData(impOrderHeadMap);
//        map.put("ImpOrderHead", impOrderHeadList);
//        map.put("ImpOrderBody", impOrderBodyList);
        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 初始化索引值
     *
     * @param waybillLists
     */
    public void getIndexValue(List<String> waybillLists) {
        logisticsNoIndex = waybillLists.indexOf(ExcelHeadWaybill.logisticsNo);
        logisticsCodeIndex = waybillLists.indexOf(ExcelHeadWaybill.logisticsCode);
        logisticsNameIndex = waybillLists.indexOf(ExcelHeadWaybill.logisticsName);
        consigneeIndex = waybillLists.indexOf(ExcelHeadWaybill.consignee);
        consigneeTelephoneIndex = waybillLists.indexOf(ExcelHeadWaybill.consigneeTelephone);
        consigneeAddressIndex = waybillLists.indexOf(ExcelHeadWaybill.consigneeAddress);
        freightIndex = waybillLists.indexOf(ExcelHeadWaybill.freight);
        insuredFeeIndex = waybillLists.indexOf(ExcelHeadWaybill.insuredFee);
        grossWeightIndex = waybillLists.indexOf(ExcelHeadWaybill.grossWeight);
        noteIndex = waybillLists.indexOf(ExcelHeadWaybill.note);

    }
}
