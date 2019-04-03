package com.xaeport.crossborder.excel.headings;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单模板表头字段
 * Created by lzy on 2018/06/28.
 */
public class ExcelHeadEnterInventory {

    //校验字段
    public final static String gds_mtno = "账册备案料号";//list
    public final static String gdecd = "商品编码";//list
    public final static String gds_nm = "商品名称";//list
    public final static String gds_spcf_model_desc = "商品规格型号";//list
    public final static String dcl_unitcd = "计量单位";//list
    public final static String lawf_unitcd = "法定计量单位";//list
    public final static String lawf_qty = "法定数量";//list
    public final static String secd_lawf_qty = "第二法定数量";//list
    public final static String secd_lawf_unitcd = "第二法定计量单位";//list
    public final static String gross_wt= "毛重";//list
    public final static String net_wt= "净重";//list
    public final static String dcl_total_amt = "总价";//list
    public final static String dcl_qty = "数量";//list
    public final static String natcd = "原产国(地区)";//list
    public final static String lvyrlf_modecd = "征免代码";//list
    public final static String rmk = "备注";//list



    //订单Excel模板表头所有字段名
    public static List<String> getList() {
        List<String> inventoryList = new ArrayList<>();
        inventoryList.add("账册备案料号");
        inventoryList.add("商品编码");
        inventoryList.add("商品名称");
        inventoryList.add("商品规格型号");
        inventoryList.add("计量单位");
        inventoryList.add("法定计量单位");
        inventoryList.add("法定数量");
        inventoryList.add("第二法定数量");
        inventoryList.add("第二法定计量单位");
        inventoryList.add("毛重");
        inventoryList.add("净重");
        inventoryList.add("总价");
        inventoryList.add("数量");
        inventoryList.add("原产国(地区)");
        inventoryList.add("征免代码");
        inventoryList.add("备注");
        return inventoryList;
    }

}
