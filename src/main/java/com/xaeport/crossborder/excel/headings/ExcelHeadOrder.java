package com.xaeport.crossborder.excel.headings;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单模板表头字段
 * Created by lzy on 2018/06/28.
 */
public class ExcelHeadOrder {

    //校验字段
    public final static String orderNo = "订单编号";//head //list
    public final static String itemName = "企业商品名称";//list
    public final static String  g_model= "商品规格型号";//list
    public final static String qty = "数量";//list
    public final static String unit = "计量单位";//list
    public final static String total_Price = "总价";//list
    public final static String ebp_Code = "电商平台代码";//head
    public final static String ebp_Name = "电商平台名称";//head
    public final static String ebc_Code = "电商企业代码";//head
    public final static String ebc_Name = "电商企业名称";//head
    public final static String buyer_Reg_No = "订购人注册号";//head
    public final static String buyer_Id_Number = "订购人证件号码";//head
    public final static String buyer_Name = "订购人姓名";//head
    public final static String buyer_TelePhone = "订购人电话";//head
    public final static String consignee = "收货人姓名";//head
    public final static String consignee_Telephone = "收货人电话";//head
    public final static String consignee_Address = "收件地址";//head
    public final static String freight = "运杂费";//head
    public final static String discount = "非现金抵扣金额";//head
    public final static String tax_Total = "代扣税款";//head
    public final static String originCountry = "原产国";//list
    public final static String note = "备注";//list

    //订单Excel模板表头所有字段名
    public static List<String> getList() {
        List<String> orderList = new ArrayList<>();
        orderList.add("订单编号");
        orderList.add("企业商品名称");
        orderList.add("商品规格型号");
        orderList.add("数量");
        orderList.add("计量单位");
        orderList.add("总价");
        orderList.add("电商平台代码");
        orderList.add("电商平台名称");
        orderList.add("电商企业代码");
        orderList.add("电商企业名称");
        orderList.add("订购人注册号");
        orderList.add("订购人证件号码");
        orderList.add("订购人姓名");
        orderList.add("订购人电话");
        orderList.add("收货人姓名");
        orderList.add("收货人电话");
        orderList.add("收件地址");
        orderList.add("运杂费");
        orderList.add("非现金抵扣金额");
        orderList.add("代扣税款");
        orderList.add("原产国");
        orderList.add("备注");

        return orderList;
    }

}
