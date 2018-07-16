package com.xaeport.crossborder.excel.headings;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付单模板表头字段
 * Created by zwf on 2018/07/10.
 */
public class ExcelHeadPayment {

    //校验字段
    public final static String orderNo = "订单编号";//head //list
    public final static String payCode = "支付企业代码";//QING
    public final static String payName = "支付企业名称";//QING
    public final static String payTransactionId = "支付交易编码";//list
    public final static String ebpCode = "电商平台代码";//QING
    public final static String epbName = "电商平台名称";//QING
    public final static String amountPaid = "支付金额";//list
    public final static String payerIdNumber = "支付人证件号码";//list
    public final static String payerName = "支付人姓名";//LIST
    public final static String payTime = "支付时间";//LIST
    public final static String note = "备注";//LIST
    //订单Excel模板表头所有字段名
    public static List<String> getList() {
        List<String> orderList = new ArrayList<>();
        orderList.add("订单编号");
        orderList.add("支付企业代码");
        orderList.add("支付企业名称");
        orderList.add("支付交易编码");
        orderList.add("电商平台代码");
        orderList.add("电商平台名称");
        orderList.add("支付金额");
        orderList.add("支付人证件号码");
        orderList.add("支付人姓名");
        orderList.add("支付时间");
        orderList.add("备注");
        return orderList;
    }

}
