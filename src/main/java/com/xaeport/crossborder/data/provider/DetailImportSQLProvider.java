package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class DetailImportSQLProvider extends BaseSQLProvider{

    /*
     * 导入插入ImpInventoryHead数据
     */
    public String insertImpInventoryHead(@Param("impInventoryHead") ImpInventoryHead impInventoryHead) throws Exception {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_INVENTORY_HEAD");
                if (!StringUtils.isEmpty(impInventoryHead.getGuid())) {
                    VALUES("guid", "#{impInventoryHead.guid}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getApp_type())) {
                    VALUES("app_type", "#{impInventoryHead.app_type}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getApp_time())) {
                    VALUES("app_time", "#{impInventoryHead.app_time}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getApp_status())) {
                    VALUES("app_status", "#{impInventoryHead.app_status}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getOrder_no())) {
                    VALUES("order_no", "#{impInventoryHead.order_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbp_code())) {
                    VALUES("ebp_code", "#{impInventoryHead.ebp_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbp_name())) {
                    VALUES("ebp_name", "#{impInventoryHead.ebp_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbc_code())) {
                    VALUES("ebc_code", "#{impInventoryHead.ebc_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbc_name())) {
                    VALUES("ebc_name", "#{impInventoryHead.ebc_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLogistics_no())) {
                    VALUES("logistics_no", "#{impInventoryHead.logistics_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLogistics_code())) {
                    VALUES("logistics_code", "#{impInventoryHead.logistics_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLogistics_name())) {
                    VALUES("logistics_name", "#{impInventoryHead.logistics_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCop_no())) {
                    VALUES("cop_no", "#{impInventoryHead.cop_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getPre_no())) {
                    VALUES("pre_no", "#{impInventoryHead.pre_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getAssure_code())) {
                    VALUES("assure_code", "#{impInventoryHead.assure_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEms_no())) {
                    VALUES("ems_no", "#{impInventoryHead.ems_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getInvt_no())) {
                    VALUES("invt_no", "#{impInventoryHead.invt_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getIe_flag())) {
                    VALUES("ie_flag", "#{impInventoryHead.ie_flag}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getDecl_time())) {
                    VALUES("decl_time", "#{impInventoryHead.decl_time}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCustoms_code())) {
                    VALUES("customs_code", "#{impInventoryHead.customs_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getPort_code())) {
                    VALUES("port_code", "#{impInventoryHead.port_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getIe_date())) {
                    VALUES("ie_date", "#{impInventoryHead.ie_date}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_id_type())) {
                    VALUES("buyer_id_type", "#{impInventoryHead.buyer_id_type}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_id_number())) {
                    VALUES("buyer_id_number", "#{impInventoryHead.buyer_id_number}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_name())) {
                    VALUES("buyer_name", "#{impInventoryHead.buyer_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_telephone())) {
                    VALUES("buyer_telephone", "#{impInventoryHead.buyer_telephone}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getConsignee_address())) {
                    VALUES("consignee_address", "#{impInventoryHead.consignee_address}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getAgent_code())) {
                    VALUES("agent_code", "#{impInventoryHead.agent_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getAgent_name())) {
                    VALUES("agent_name", "#{impInventoryHead.agent_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getArea_code())) {
                    VALUES("area_code", "#{impInventoryHead.area_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getArea_name())) {
                    VALUES("area_name", "#{impInventoryHead.area_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getTrade_mode())) {
                    VALUES("trade_mode", "#{impInventoryHead.trade_mode}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getTraf_mode())) {
                    VALUES("traf_mode", "#{impInventoryHead.traf_mode}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getTraf_no())) {
                    VALUES("traf_no", "#{impInventoryHead.traf_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getVoyage_no())) {
                    VALUES("voyage_no", "#{impInventoryHead.voyage_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBill_no())) {
                    VALUES("bill_no", "#{impInventoryHead.bill_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLoct_no())) {
                    VALUES("loct_no", "#{impInventoryHead.loct_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLicense_no())) {
                    VALUES("license_no", "#{impInventoryHead.license_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCountry())) {
                    VALUES("country", "#{impInventoryHead.country}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getFreight())) {
                    VALUES("freight", "#{impInventoryHead.freight}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getInsured_fee())) {
                    VALUES("insured_fee", "#{impInventoryHead.insured_fee}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCurrency())) {
                    VALUES("currency", "#{impInventoryHead.currency}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getWrap_type())) {
                    VALUES("wrap_type", "#{impInventoryHead.wrap_type}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getPack_no())) {
                    VALUES("pack_no", "#{impInventoryHead.pack_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getGross_weight())) {
                    VALUES("gross_weight", "#{impInventoryHead.gross_weight}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getNet_weight())) {
                    VALUES("net_weight", "#{impInventoryHead.net_weight}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getNote())) {
                    VALUES("note", "#{impInventoryHead.note}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getData_status())) {
                    VALUES("data_status", "#{impInventoryHead.data_status}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCrt_id())) {
                    VALUES("crt_id", "#{impInventoryHead.crt_id}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCrt_tm())) {
                    VALUES("crt_tm", "#{impInventoryHead.crt_tm}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getUpd_id())) {
                    VALUES("upd_id", "#{impInventoryHead.upd_id}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getUpd_tm())) {
                    VALUES("upd_tm", "#{impInventoryHead.upd_tm}");
                }
                if(!StringUtils.isEmpty(impInventoryHead.getEnt_id())){
                    VALUES("ent_id","#{impInventoryHead.ent_id}");
                }
                if(!StringUtils.isEmpty(impInventoryHead.getEnt_name())){
                    VALUES("ent_name","#{impInventoryHead.ent_name}");
                }
                if(!StringUtils.isEmpty(impInventoryHead.getEnt_customs_code())){
                    VALUES("ent_customs_code","#{impInventoryHead.ent_customs_code}");
                }
                if(!StringUtils.isEmpty(impInventoryHead.getBusiness_type())){
                    VALUES("business_type","#{impInventoryHead.business_type}");
                }
            }
        }.toString();
    }

    /*
     * 导入插入impInventoryBody数据
     */
    public String insertImpInventoryBody(@Param("impInventoryBody") ImpInventoryBody impInventoryBody) throws Exception {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_INVENTORY_BODY");
                if (!StringUtils.isEmpty(impInventoryBody.getG_num())) {
                    VALUES("g_num", "#{impInventoryBody.g_num}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getHead_guid())) {
                    VALUES("head_guid", "#{impInventoryBody.head_guid}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getOrder_no())) {
                    VALUES("order_no", "#{impInventoryBody.order_no}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getItem_record_no())) {
                    VALUES("item_record_no", "#{impInventoryBody.item_record_no}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getItem_no())) {
                    VALUES("item_no", "#{impInventoryBody.item_no}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getItem_name())) {
                    VALUES("item_name", "#{impInventoryBody.item_name}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getG_code())) {
                    VALUES("g_code", "#{impInventoryBody.g_code}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getG_name())) {
                    VALUES("g_name", "#{impInventoryBody.g_name}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getG_model())) {
                    VALUES("g_model", "#{impInventoryBody.g_model}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getBar_code())) {
                    VALUES("bar_code", "#{impInventoryBody.bar_code}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getCountry())) {
                    VALUES("country", "#{impInventoryBody.country}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getCurrency())) {
                    VALUES("currency", "#{impInventoryBody.currency}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getQty())) {
                    VALUES("qty", "#{impInventoryBody.qty}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getQty1())) {
                    VALUES("qty1", "#{impInventoryBody.qty1}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getQty2())) {
                    VALUES("qty2", "#{impInventoryBody.qty2}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getUnit())) {
                    VALUES("unit", "#{impInventoryBody.unit}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getUnit1())) {
                    VALUES("unit1", "#{impInventoryBody.unit1}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getUnit2())) {
                    VALUES("unit2", "#{impInventoryBody.unit2}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getPrice())) {
                    VALUES("price", "#{impInventoryBody.price}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getTotal_price())) {
                    VALUES("total_price", "#{impInventoryBody.total_price}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getNote())) {
                    VALUES("note", "#{impInventoryBody.note}");
                }

            }
        }.toString();
    }

    /*
     * 查询有无重复订单号表头信息
     */
    public String isRepeatOrderNo(@Param("impInventoryHead") ImpInventoryHead impInventoryHead) throws Exception {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.order_no = #{impInventoryHead.order_no}");
            }
        }.toString();
    }


}
