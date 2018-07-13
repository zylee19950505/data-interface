package com.xaeport.crossborder.excel.headings;

import java.util.ArrayList;
import java.util.List;
/*
 * 运单状态表头字段
 */
public class ExcelHeadWaybillStatus {

    //校验字段
    public final static String logisticsNo = "物流运单编号";//head
    public final static String logisticsCode = "物流企业代码";//head
    public final static String logisticsName = "物流企业名称";//head
    public final static String logisticsStatus = "物流运单状态";//head
    public final static String logisticsTime = "物流状态时间";//head
    public final static String note = "备注";//head

    //订单Excel模板表头所有字段名
    public static List<String> getList() {
        List<String> wayBillStatusList = new ArrayList<>();
        wayBillStatusList.add("物流运单编号");
        wayBillStatusList.add("物流企业代码");
        wayBillStatusList.add("物流企业名称");
        wayBillStatusList.add("物流运单状态");
        wayBillStatusList.add("物流状态时间");
        wayBillStatusList.add("备注");
        return wayBillStatusList;
    }

}
