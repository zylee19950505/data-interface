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
    public final static String copNo = "企业内部编号";//QING
    public final static String logisticsNo = "物流运单编号";//QING
    public final static String itemName = "商品名称";//list
    public final static String gcode = "商品编码";//QING
    public final static String gmodel = "商品规格型号";//QING
    public final static String qty = "数量";//list
    public final static String unit = "计量单位";//list
    public final static String qty1 = "第一法定数量";//LIST
    public final static String unit1 = "第一法定计量单位";//LIST
    public final static String qty2 = "第二法定数量";//LIST
    public final static String unit2 = "第二法定计量单位";//LIST
    public final static String total_Price = "总价";//list
    public final static String ebp_Code = "电商平台代码";//head//QING
    public final static String ebp_Name = "电商平台名称";//head//QING
    public final static String ebc_Code = "电商企业代码";//head//QING
    public final static String ebc_Name = "电商企业名称";//head//QING
    public final static String assureCode = "担保企业编号";//QING
    public final static String customsCode = "申报海关代码";//QING
    public final static String portCode = "口岸海关代码";//QING
    public final static String ieDate = "进口日期";//
    public final static String buyer_Reg_No = "订购人注册号";//head
    public final static String buyer_Id_Number = "订购人证件号码";//head//QING
    public final static String buyer_Name = "订购人姓名";//head//QING
    public final static String buyerTelephone = "订购人电话";//QING
    public final static String consignee = "收货人姓名";//head
    public final static String consignee_Telephone = "收货人电话";//head
    public final static String consignee_Address = "收件地址";//head//QING
    public final static String goodsValue = "商品价格";//head
    public final static String freight = "运杂费";//head//QING
    public final static String discount = "非现金抵扣金额";//head
    public final static String tax_Total = "代扣税款";//head
    public final static String agentCode = "申报企业代码";//QING
    public final static String agentName = "申报企业名称";//QING
    public final static String trafMode = "运输方式";//QING
    public final static String trafNo = "运输工具编号";//QING
    public final static String flightVoyage = "航班航次号";//QING
    public final static String billNo = "提运单号";//QING
    public final static String originCountry = "原产国";//list//QING
    public final static String startCountry = "起运国";//QING
    public final static String netWeight = "净重";//QING
    public final static String note = "备注";//head

    //订单Excel模板表头所有字段名
    public static List<String> getList() {
        List<String> orderList = new ArrayList<>();
        orderList.add("订单编号");
        orderList.add("企业内部编号");
        orderList.add("物流运单编号");
        orderList.add("商品名称");
        orderList.add("商品编码");
        orderList.add("商品规格型号");
        orderList.add("数量");
        orderList.add("计量单位");
        orderList.add("第一法定数量");
        orderList.add("第一法定计量单位");
        orderList.add("第二法定数量");
        orderList.add("第二法定计量单位");
        orderList.add("总价");
        orderList.add("电商平台代码");
        orderList.add("电商平台名称");
        orderList.add("电商企业代码");
        orderList.add("电商企业名称");
        orderList.add("担保企业编号");
        orderList.add("申报海关代码");
        orderList.add("口岸海关代码");
        orderList.add("进口日期");
        orderList.add("订购人注册号");
        orderList.add("订购人证件号码");
        orderList.add("订购人姓名");
        orderList.add("订购人电话");
        orderList.add("收货人姓名");
        orderList.add("收货人电话");
        orderList.add("收件地址");
        orderList.add("商品价格");
        orderList.add("运杂费");
        orderList.add("非现金抵扣金额");
        orderList.add("代扣税款");
        orderList.add("申报企业代码");
        orderList.add("申报企业名称");
        orderList.add("运输方式");
        orderList.add("运输工具编号");
        orderList.add("航班航次号");
        orderList.add("提运单号");
        orderList.add("原产国");
        orderList.add("起运国");
        orderList.add("净重");
        orderList.add("备注");

        return orderList;
    }

}
