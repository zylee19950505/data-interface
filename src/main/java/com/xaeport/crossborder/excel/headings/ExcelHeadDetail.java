package com.xaeport.crossborder.excel.headings;


import java.util.ArrayList;
import java.util.List;

/**
 * 清单模板表头字段
 * Created by lzy on 2018/07/12.
 */
public class ExcelHeadDetail {

    //校验字段
    public final static String orderNo = "订单编号";//head //list
    public final static String copNo = "企业内部编号";//head
    public final static String logisticsNo = "物流运单编号";//head
    public final static String itemName = "商品名称";//list
    public final static String gcode = "商品编码";//list
    public final static String gmodel = "商品规格型号";//list
    public final static String qty = "数量";//list
    public final static String unit = "计量单位";//list
    public final static String qty1 = "第一法定数量";//list
    public final static String unit1 = "第一法定计量单位";//list
    public final static String qty2 = "第二法定数量";//list
    public final static String unit2 = "第二法定计量单位";//list
    public final static String total_Price = "总价";//list
    public final static String ebp_Code = "电商平台代码";//head
    public final static String ebp_Name = "电商平台名称";//head
    public final static String ebc_Code = "电商企业代码";//head
    public final static String ebc_Name = "电商企业名称";//head
    public final static String assureCode = "担保企业编号";//head
    public final static String customsCode = "申报海关代码";//head
    public final static String portCode = "口岸海关代码";//head
    public final static String ieDate = "进口日期";//head
    public final static String buyer_Id_Number = "订购人证件号码";//head
    public final static String buyer_Name = "订购人姓名";//head
    public final static String buyerTelephone = "订购人电话";//head
    public final static String consignee_Address = "收件地址";//head
    public final static String freight = "运费";//head
    public final static String agentCode = "申报企业代码";//head
    public final static String agentName = "申报企业名称";//head
    public final static String trafMode = "运输方式";//head
    public final static String trafNo = "运输工具编号";//head
    public final static String flightVoyage = "航班航次号";//head
    public final static String billNo = "提运单号";//head
    public final static String originCountry = "原产国";//list
    public final static String startCountry = "起运国";//head
    public final static String netWeight = "净重";//head
    public final static String note = "备注";//list
    //    public final static String buyer_Reg_No = "订购人注册号";//head
//    public final static String consignee = "收货人姓名";//head
//    public final static String consignee_Telephone = "收货人电话";//head
//    public final static String discount = "非现金抵扣金额";//head
//    public final static String tax_Total = "代扣税款";//head


    //清单Excel模板所有字段名
    public static List<String> getList() {
        List<String> detailList = new ArrayList<>();
        detailList.add("订单编号");
        detailList.add("企业内部编号");
        detailList.add("物流运单编号");
        detailList.add("商品名称");
        detailList.add("商品编码");
        detailList.add("商品规格型号");
        detailList.add("数量");
        detailList.add("计量单位");
        detailList.add("第一法定数量");
        detailList.add("第一法定计量单位");
        detailList.add("第二法定数量");
        detailList.add("第二法定计量单位");
        detailList.add("总价");
        detailList.add("电商平台代码");
        detailList.add("电商平台名称");
        detailList.add("电商企业代码");
        detailList.add("电商企业名称");
        detailList.add("担保企业编号");
        detailList.add("申报海关代码");
        detailList.add("口岸海关代码");
        detailList.add("进口日期");
        detailList.add("订购人证件号码");
        detailList.add("订购人姓名");
        detailList.add("订购人电话");
        detailList.add("收件地址");
        detailList.add("运费");
        detailList.add("申报企业代码");
        detailList.add("申报企业名称");
        detailList.add("运输方式");
        detailList.add("运输工具编号");
        detailList.add("航班航次号");
        detailList.add("提运单号");
        detailList.add("原产国");
        detailList.add("起运国");
        detailList.add("净重");
        detailList.add("备注");
        //        detailList.add("订购人注册号");
//        detailList.add("收货人姓名");
//        detailList.add("收货人电话");
//        detailList.add("非现金抵扣金额");
//        detailList.add("代扣税款");
        return detailList;
    }
    
}
