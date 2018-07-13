package com.xaeport.crossborder.excel.headings;

import java.util.ArrayList;
import java.util.List;

public class ExcelHeadWaybill {
    //校验字段
    public final static String logisticsNo = "物流运单编号";//head
    public final static String logisticsCode = "物流企业代码";//head
    public final static String logisticsName = "物流企业名称";//head
    public final static String consignee = "收货人姓名";//head
    public final static String consigneeTelephone = "收货人电话";//head
    public final static String consigneeAddress = "收件地址";//head
    public final static String freight = "运费";//head
    public final static String insuredFee = "保价费";//head
    public final static String grossWeight = "毛重";//head
    public final static String note = "备注";//head

    //订单Excel模板表头所有字段名
    public static List<String> getList() {
        List<String> wayBillList = new ArrayList<>();
        wayBillList.add("物流运单编号");
        wayBillList.add("物流企业代码");
        wayBillList.add("物流企业名称");
        wayBillList.add("收货人姓名");
        wayBillList.add("收货人电话");
        wayBillList.add("收件地址");
        wayBillList.add("运费");
        wayBillList.add("保价费");
        wayBillList.add("毛重");
        wayBillList.add("备注");
        return wayBillList;
    }
}
