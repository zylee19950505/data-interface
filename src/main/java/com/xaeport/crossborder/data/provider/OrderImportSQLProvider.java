package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class OrderImportSQLProvider {

    /*
     * 导入插入impOrderHead表头数据
     */
    public String insertImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead) throws Exception {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_ORDER_HEAD");
                if (!StringUtils.isEmpty(impOrderHead.getGuid())) {
                    VALUES("guid", "#{impOrderHead.guid}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Type())) {
                    VALUES("app_Type", "#{impOrderHead.app_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Time())) {
                    VALUES("app_Time", "#{impOrderHead.app_Time}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Status())) {
                    VALUES("app_Status", "#{impOrderHead.app_Status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getOrder_Type())) {
                    VALUES("order_Type", "#{impOrderHead.order_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getOrder_No())) {
                    VALUES("order_No", "#{impOrderHead.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbp_Code())) {
                    VALUES("ebp_Code", "#{impOrderHead.ebp_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbp_Name())) {
                    VALUES("ebp_Name", "#{impOrderHead.ebp_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbc_Code())) {
                    VALUES("ebc_Code", "#{impOrderHead.ebc_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbc_Name())) {
                    VALUES("ebc_Name", "#{impOrderHead.ebc_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getGoods_Value())) {
                    VALUES("goods_Value", "#{impOrderHead.goods_Value}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getFreight())) {
                    VALUES("freight", "#{impOrderHead.freight}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getDiscount())) {
                    VALUES("discount", "#{impOrderHead.discount}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getTax_Total())) {
                    VALUES("tax_Total", "#{impOrderHead.tax_Total}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getActural_Paid())) {
                    VALUES("actural_Paid", "#{impOrderHead.actural_Paid}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getCurrency())) {
                    VALUES("currency", "#{impOrderHead.currency}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Reg_No())) {
                    VALUES("buyer_Reg_No", "#{impOrderHead.buyer_Reg_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Name())) {
                    VALUES("buyer_Name", "#{impOrderHead.buyer_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Id_Type())) {
                    VALUES("buyer_Id_Type", "#{impOrderHead.buyer_Id_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Id_Number())) {
                    VALUES("buyer_Id_Number", "#{impOrderHead.buyer_Id_Number}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPay_Code())) {
                    VALUES("pay_Code", "#{impOrderHead.pay_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPayName())) {
                    VALUES("payName", "#{impOrderHead.payName}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPay_Transaction_Id())) {
                    VALUES("pay_Transaction_Id", "#{impOrderHead.pay_Transaction_Id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBatch_Numbers())) {
                    VALUES("batch_Numbers", "#{impOrderHead.batch_Numbers}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee())) {
                    VALUES("consignee", "#{impOrderHead.consignee}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Telephone())) {
                    VALUES("consignee_Telephone", "#{impOrderHead.consignee_Telephone}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Address())) {
                    VALUES("consignee_Address", "#{impOrderHead.consignee_Address}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Ditrict())) {
                    VALUES("consignee_Ditrict", "#{impOrderHead.consignee_Ditrict}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getNote())) {
                    VALUES("note", "#{impOrderHead.note}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getCrt_id())) {
                    VALUES("crt_id", "#{impOrderHead.crt_id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getCrt_tm())) {
                    VALUES("crt_tm", "#{impOrderHead.crt_tm}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getUpd_id())) {
                    VALUES("upd_id", "#{impOrderHead.upd_id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getUpd_tm())) {
                    VALUES("upd_tm", "#{impOrderHead.upd_tm}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getData_status())) {
                    VALUES("data_status", "#{impOrderHead.data_status}");
                }
                if(!StringUtils.isEmpty(impOrderHead.getEnt_id())){
                    VALUES("ent_id","#{impOrderHead.ent_id}");
                }
                if(!StringUtils.isEmpty(impOrderHead.getEnt_name())){
                    VALUES("ent_name","#{impOrderHead.ent_name}");
                }
                if(!StringUtils.isEmpty(impOrderHead.getEnt_customs_code())){
                    VALUES("ent_customs_code","#{impOrderHead.ent_customs_code}");
                }
            }
        }.toString();
    }

    /*
     * 导入插入insertImpOrderGoodsList表体数据
     */
    public String insertImpOrderBody(@Param("impOrderBody") ImpOrderBody impOrderBody) throws Exception {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_ORDER_BODY");
                if (!StringUtils.isEmpty(impOrderBody.getG_num())) {
                    VALUES("g_num", "#{impOrderBody.g_num}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getHead_guid())) {
                    VALUES("head_guid", "#{impOrderBody.head_guid}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getOrder_No())) {
                    VALUES("order_No", "#{impOrderBody.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getItem_No())) {
                    VALUES("item_No", "#{impOrderBody.item_No}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getItem_Name())) {
                    VALUES("item_Name", "#{impOrderBody.item_Name}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getItem_Describe())) {
                    VALUES("item_Describe", "#{impOrderBody.item_Describe}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getBar_Code())) {
                    VALUES("bar_Code", "#{impOrderBody.bar_Code}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getUnit())) {
                    VALUES("unit", "#{impOrderBody.unit}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getQty())) {
                    VALUES("qty", "#{impOrderBody.qty}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getPrice())) {
                    VALUES("price", "#{impOrderBody.price}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getTotal_Price())) {
                    VALUES("total_Price", "#{impOrderBody.total_Price}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getCurrency())) {
                    VALUES("currency", "#{impOrderBody.currency}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getCountry())) {
                    VALUES("country", "#{impOrderBody.country}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getNote())) {
                    VALUES("note", "#{impOrderBody.note}");
                }

            }
        }.toString();
    }

    /*
     * 查询有无重复订单号表头信息
     */
    public String isRepeatOrderNo(@Param("impOrderHead") ImpOrderHead impOrderHead) throws Exception {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_IMP_ORDER_HEAD t");
                WHERE("t.order_No = #{impOrderHead.order_No}");
            }
        }.toString();
    }

    /*
     * 根据订单号查询表头Id 码
     */
    public String queryHeadGuidByOrderNo(String listOrder_no) throws Exception {
        return new SQL() {
            {
                SELECT("t.GUID");
                FROM("T_IMP_ORDER_HEAD t");
                WHERE("t.ORDER_NO = #{listOrder_no}");
            }
        }.toString();
    }

    /*
     * 根据订单号查询表体最大商品编码
     */
    public String queryMaxG_num(String listOrder_no) throws Exception {
        return new SQL() {
            {
                SELECT("max(t.G_NUM)");
                FROM("T_IMP_ORDER_BODY t");
                WHERE("t.ORDER_NO = #{listOrder_no}");
            }
        }.toString();
    }

}
