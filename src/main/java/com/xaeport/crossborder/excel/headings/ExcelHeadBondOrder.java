package com.xaeport.crossborder.excel.headings;

import java.util.ArrayList;
import java.util.List;

//保税订单表头
public class ExcelHeadBondOrder {
    //校验字段
    public final static String orderNo = "订单编号";
    public final static String batch_numbers = "商品批次号";
    public final static String trade_mode = "贸易方式";
    public final static String ebp_Code = "电商平台代码";
    public final static String ebp_Name = "电商平台名称";
    public final static String ebc_Code = "电商企业代码";
    public final static String ebc_Name = "电商企业名称";
    public final static String port_code = "口岸海关代码";
    public final static String buyer_Reg_No = "订购人注册号";
    public final static String buyer_Name = "订购人姓名";
    public final static String buyer_Id_Number = "订购人身份证号码";
    public final static String buyer_TelePhone = "订购人电话";
    public final static String consignee = "收货人姓名";
    public final static String consignee_Telephone = "收货人电话";
    public final static String consignee_Address = "收件地址";
    public final static String insuredFee = "保价费";
    public final static String freight = "运杂费";
    public final static String discount = "非现金抵扣金额";
    public final static String tax_Total = "代扣税款";
    public final static String grossWeight = "订单总毛重";
    public final static String netWeight = "订单总净重";

    public final static String itemName = "企业商品名称";
    public final static String itemNo = "企业商品货号";
    public final static String g_model= "商品规格型号";
    public final static String qty = "数量";
    public final static String unit = "计量单位";
    public final static String total_Price = "总价";
    public final static String note = "备注";

    //订单Excel模板表头所有字段名
    public static List<String> getList() {
        List<String> orderList = new ArrayList<>();
        orderList.add("订单编号");
        orderList.add("商品批次号");
        orderList.add("贸易方式");
        orderList.add("电商平台代码");
        orderList.add("电商平台名称");
        orderList.add("电商企业代码");
        orderList.add("电商企业名称");
        orderList.add("口岸海关代码");
        orderList.add("订购人注册号");
        orderList.add("订购人姓名");
        orderList.add("订购人身份证号码");
        orderList.add("订购人电话");
        orderList.add("收货人姓名");
        orderList.add("收货人电话");
        orderList.add("收件地址");
        orderList.add("保价费");
        orderList.add("运杂费");
        orderList.add("非现金抵扣金额");
        orderList.add("代扣税款");
        orderList.add("订单总毛重");
        orderList.add("订单总净重");

        orderList.add("企业商品名称");
        orderList.add("企业商品货号");
        orderList.add("商品规格型号");
        orderList.add("数量");
        orderList.add("计量单位");
        orderList.add("总价");
        orderList.add("备注");

        return orderList;
    }

}
