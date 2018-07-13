package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.headings.ExcelHeadDetail;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelDataDetail implements ExcelData {
    private Log log = LogFactory.getLog(this.getClass());
    private int orderNoIndex; //"订单编号";//head //list
    private int copNoIndex; //"企业内部编号";//head
    private int logisticsNoIndex; //"物流运单编号";//head
    private int itemNameIndex; //"商品名称";//list
    private int gcodeIndex; //"商品编码";//list
    private int gmodelIndex; //"商品规格型号";//list
    private int qtyIndex; //"数量";//list
    private int unitIndex; //"计量单位";//list
    private int qty1Index; //"第一法定数量";//list
    private int unit1Index; //"第一法定计量单位";//list
    private int qty2Index; //"第二法定数量";//list
    private int unit2Index; //"第二法定计量单位";//list
    private int total_PriceIndex; //"总价";//list
    private int ebp_CodeIndex; //"电商平台代码";//head
    private int ebp_NameIndex; //"电商平台名称";//head
    private int ebc_CodeIndex; //"电商企业代码";//head
    private int ebc_NameIndex; //"电商企业名称";//head
    private int assureCodeIndex; //"担保企业编号";//head
    private int customsCodeIndex; //"申报海关代码";//head
    private int portCodeIndex; //"口岸海关代码";//head
    private int ieDateIndex; //"进口日期";//head
    private int buyer_Id_NumberIndex; //"订购人证件号码";//head
    private int buyer_NameIndex; //"订购人姓名";//head
    private int buyerTelephoneIndex; //"订购人电话";//head
    private int consignee_AddressIndex; //"收件地址";//head
    private int freightIndex; //"运费";//head
    private int agentCodeIndex; //"申报企业代码";//head
    private int agentNameIndex; //"申报企业名称";//head
    private int trafModeIndex; //"运输方式";//head
    private int trafNoIndex; //"运输工具编号";//head
    private int flightVoyageIndex; //"航班航次号";//head
    private int billNoIndex; //"提运单号";//head
    private int originCountryIndex; //"原产国";//list
    private int startCountryIndex; //"起运国";//head
    private int netWeightIndex; //"净重";//head
    private int noteIndex; //"备注";//list


    
    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
//        this.getIndexValue(excelData.get(0));//初始化表头索引
//        List<ImpPayment> orderList =  this.getEIntlMailDataToPayment(excelData);
//        map.put("ImpPayment", orderList);
//
//        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 初始化索引值
     *
     * @param detailLists
     */
    public void getIndexValue(List<String> detailLists) {
        orderNoIndex = detailLists.indexOf(ExcelHeadDetail.orderNo);
        copNoIndex = detailLists.indexOf(ExcelHeadDetail.copNo);
        logisticsNoIndex = detailLists.indexOf(ExcelHeadDetail.logisticsNo);
        itemNameIndex = detailLists.indexOf(ExcelHeadDetail.itemName);
        gcodeIndex = detailLists.indexOf(ExcelHeadDetail.gcode);
        gmodelIndex = detailLists.indexOf(ExcelHeadDetail.gmodel);
        qtyIndex = detailLists.indexOf(ExcelHeadDetail.qty);
        unitIndex = detailLists.indexOf(ExcelHeadDetail.unit);
        qty1Index = detailLists.indexOf(ExcelHeadDetail.qty1);
        unit1Index = detailLists.indexOf(ExcelHeadDetail.unit1);
        qty2Index = detailLists.indexOf(ExcelHeadDetail.qty2);
        unit2Index = detailLists.indexOf(ExcelHeadDetail.unit2);
        total_PriceIndex = detailLists.indexOf(ExcelHeadDetail.total_Price);
        ebp_CodeIndex = detailLists.indexOf(ExcelHeadDetail.ebp_Code);
        ebp_NameIndex = detailLists.indexOf(ExcelHeadDetail.ebp_Name);
        ebc_CodeIndex = detailLists.indexOf(ExcelHeadDetail.ebc_Code);
        ebc_NameIndex = detailLists.indexOf(ExcelHeadDetail.ebc_Name);
        assureCodeIndex = detailLists.indexOf(ExcelHeadDetail.assureCode);
        customsCodeIndex = detailLists.indexOf(ExcelHeadDetail.customsCode);
        portCodeIndex = detailLists.indexOf(ExcelHeadDetail.portCode);
        ieDateIndex = detailLists.indexOf(ExcelHeadDetail.ieDate);
        buyer_Id_NumberIndex = detailLists.indexOf(ExcelHeadDetail.buyer_Id_Number);
        buyer_NameIndex = detailLists.indexOf(ExcelHeadDetail.buyer_Name);
        buyerTelephoneIndex = detailLists.indexOf(ExcelHeadDetail.buyerTelephone);
        consignee_AddressIndex = detailLists.indexOf(ExcelHeadDetail.consignee_Address);
        freightIndex = detailLists.indexOf(ExcelHeadDetail.freight);
        agentCodeIndex = detailLists.indexOf(ExcelHeadDetail.agentCode);
        agentNameIndex = detailLists.indexOf(ExcelHeadDetail.agentName);
        trafModeIndex = detailLists.indexOf(ExcelHeadDetail.trafMode);
        trafNoIndex = detailLists.indexOf(ExcelHeadDetail.trafNo);
        flightVoyageIndex = detailLists.indexOf(ExcelHeadDetail.flightVoyage);
        billNoIndex = detailLists.indexOf(ExcelHeadDetail.billNo);
        originCountryIndex = detailLists.indexOf(ExcelHeadDetail.originCountry);
        startCountryIndex = detailLists.indexOf(ExcelHeadDetail.startCountry);
        netWeightIndex = detailLists.indexOf(ExcelHeadDetail.netWeight);
        noteIndex = detailLists.indexOf(ExcelHeadDetail.note);


    }
}
