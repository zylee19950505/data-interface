package com.xaeport.crossborder.excel.headings;

import java.util.ArrayList;
import java.util.List;

/**
 * 保税清单模板表头字段
 * Created by lzy on 2018-12-27
 */
public class ExcelHeadBondInven {

    //校验字段
    public final static String orderNo = "订单编号";
    public final static String ebpCode = "电商平台代码";
    public final static String ebpName = "电商平台名称";
    public final static String logisticsNo = "物流运单编号";
    public final static String logisticsCode = "物流企业代码";
    public final static String logisticsName = "物流企业名称";
    public final static String portCode = "进境关别";
    public final static String customsCode = "申报地海关代码";
    public final static String grossWeight = "毛重";
    public final static String netWeight = "净重";
    public final static String buyerIdNumber = "订购人证件号码";
    public final static String buyerName = "订购人姓名";
    public final static String buyerTelephone = "订购人电话";
    public final static String consigneeAddress = "收件地址";
    public final static String trafMode = "运输方式代码";
    public final static String freight = "运费";
    public final static String insuredFee = "保费";
    public final static String wrapType = "包装种类代码";
    public final static String itemNo = "账册备案料号";
    public final static String gCode = "商品编码";
    public final static String gName = "商品名称";
    public final static String gModel = "商品规格型号";
    public final static String qty = "数量";
    public final static String unit = "计量单位";
    public final static String qty1 = "法定数量";
    public final static String unit1 = "法定单位";
    public final static String qty2 = "第二法定数量";
    public final static String unit2 = "第二法定单位";
    public final static String totalPrice = "总价";
    public final static String originCountry = "原产国代码";
    public final static String note = "备注";

    //保税清单Excel模板所有字段名
    public static List<String> getList() {
        List<String> bondInvenList = new ArrayList<>();
        bondInvenList.add("订单编号");
        bondInvenList.add("电商平台代码");
        bondInvenList.add("电商平台名称");
        bondInvenList.add("物流运单编号");
        bondInvenList.add("物流企业代码");
        bondInvenList.add("物流企业名称");
        bondInvenList.add("进境关别");
        bondInvenList.add("申报地海关代码");
        bondInvenList.add("毛重");
        bondInvenList.add("净重");
        bondInvenList.add("订购人证件号码");
        bondInvenList.add("订购人姓名");
        bondInvenList.add("订购人电话");
        bondInvenList.add("收件地址");
        bondInvenList.add("运输方式代码");
        bondInvenList.add("运费");
        bondInvenList.add("保费");
        bondInvenList.add("包装种类代码");
        bondInvenList.add("账册备案料号");
        bondInvenList.add("商品编码");
        bondInvenList.add("商品名称");
        bondInvenList.add("商品规格型号");
        bondInvenList.add("数量");
        bondInvenList.add("计量单位");
        bondInvenList.add("法定数量");
        bondInvenList.add("法定单位");
        bondInvenList.add("第二法定数量");
        bondInvenList.add("第二法定单位");
        bondInvenList.add("总价");
        bondInvenList.add("原产国代码");
        bondInvenList.add("备注");
        return bondInvenList;
    }
}
