package com.xaeport.crossborder.excel.headings;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单模板表头字段
 * Created by lzy on 2018/06/28.
 */
public class ExcelHeadEnterInventory {

    //校验字段
    public final static String putrec_seqno = "备案序号";//list
    public final static String gds_seqno = "商品序号";//list
    public final static String gds_mtno = "商品料号";//list
    public final static String gdecd = "商品编码";//list
    public final static String gds_nm = "商品名称";//list
    public final static String gds_spcf_model_desc = "商品规格型号";//list
    public final static String dcl_unitcd = "申报计量单位";//list
    public final static String dcl_qty = "申报数量";//list
    public final static String dcl_uprc_amt = "申报单价";//list
    public final static String dcl_total_amt = "申报总价";//list
    public final static String dcl_currcd = "币制";//list
    public final static String usd_stat_total_amt = "美元统计总金额";//list



    //订单Excel模板表头所有字段名
    public static List<String> getList() {
        List<String> inventoryList = new ArrayList<>();
        inventoryList.add("备案序号");
        inventoryList.add("商品序号");
        inventoryList.add("商品料号");
        inventoryList.add("商品编码");
        inventoryList.add("商品名称");
        inventoryList.add("商品规格型号");
        inventoryList.add("申报计量单位");
        inventoryList.add("申报数量");
        inventoryList.add("申报单价");
        inventoryList.add("申报总价");
        inventoryList.add("币制");
        inventoryList.add("美元统计总金额");
        return inventoryList;
    }

}
