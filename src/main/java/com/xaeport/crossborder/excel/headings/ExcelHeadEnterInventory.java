package com.xaeport.crossborder.excel.headings;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单模板表头字段
 * Created by lzy on 2018/06/28.
 */
public class ExcelHeadEnterInventory {

    //校验字段
    public final static String gds_mtno = "账册备案料号";
    public final static String gdecd = "商品编码";
    public final static String gds_nm = "商品名称";
    public final static String gds_spcf_model_desc = "规格型号";
    public final static String dcl_qty = "申报数量";
    public final static String dcl_unitcd = "申报单位";
    public final static String lawf_qty = "法定数量";
    public final static String lawf_unitcd = "法定单位";
    public final static String secd_lawf_qty = "第二法定数量";
    public final static String secd_lawf_unitcd = "第二法定单位";
    public final static String dcl_total_amt = "商品总价";
    public final static String dcl_currcd = "币制";
    public final static String natcd = "原产国(地区)代码";
    public final static String lvyrlf_modecd = "征免方式代码";

    //订单Excel模板表头所有字段名
    public static List<String> getList() {
        List<String> inventoryList = new ArrayList<>();
        inventoryList.add("账册备案料号");
        inventoryList.add("商品编码");
        inventoryList.add("商品名称");
        inventoryList.add("规格型号");
        inventoryList.add("申报数量");
        inventoryList.add("申报单位");
        inventoryList.add("法定数量");
        inventoryList.add("法定单位");
        inventoryList.add("第二法定数量");
        inventoryList.add("第二法定单位");
        inventoryList.add("商品总价");
        inventoryList.add("币制");
        inventoryList.add("原产国(地区)代码");
        inventoryList.add("征免方式代码");
        return inventoryList;
    }

}
