package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class DetailQuerySQLProvider extends BaseSQLProvider{

    //查询清单页面数据
    public String queryInventoryQueryList(Map<String, String> paramMap) throws Exception {
        final String orderNo = paramMap.get("orderNo");
        final String end = paramMap.get("end");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        return new SQL() {
            {
                SELECT(
                        " * from ( select rownum rn, f.* from ( " +
                                " SELECT * ");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.order_no = #{orderNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.crt_tm >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.crt_tm <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.upd_tm desc ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.upd_tm desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    //查询清单页面数据总数
    public String queryInventoryQueryCount(Map<String, String> paramMap) throws Exception {
        final String orderNo = paramMap.get("orderNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.ORDER_NO = #{orderNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.crt_tm >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.crt_tm <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
            }
        }.toString();
    }

    //查询清单表头信息
    public String queryImpInventoryHead(Map<String,String> paramMap){
        final String id = paramMap.get("id");
        return  new SQL(){
            {
                SELECT("*");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.GUID = #{id}");
            }
        }.toString();

    }

    //查询清单表体信息
    public String queryImpInventoryBodies(Map<String,String> paramMap){
        final String id = paramMap.get("id");
        return  new SQL(){
            {
                SELECT("*");
                FROM("T_IMP_INVENTORY_BODY t");
                WHERE("t.HEAD_GUID = #{id}");
            }
        }.toString();
    }

    //修改清单表头信息
    public String updateImpInventoryHead(LinkedHashMap<String, String> entryHead){
        return new SQL(){
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE("t.GUID = #{entryhead_guid}");
                if (!StringUtils.isEmpty(entryHead.get("order_no"))){
                    SET("t.order_no = #{order_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("cop_no"))){
                    SET("t.cop_no = #{cop_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("logistics_no"))){
                    SET("t.logistics_no = #{logistics_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ebp_code"))){
                    SET("t.ebp_code = #{ebp_Name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ebp_name"))){
                    SET("t.ebp_name = #{ebc_Code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ebc_code"))){
                    SET("t.ebc_code = #{ebc_Name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ebc_name"))){
                    SET("t.ebc_name = #{ebc_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("assure_code"))){
                    SET("t.assure_code = #{assure_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("customs_code"))){
                    SET("t.customs_code = #{customs_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("port_code"))){
                    SET("t.port_code = #{port_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ie_date"))){
                    SET("t.ie_date = #{ie_date}");
                }
                if (!StringUtils.isEmpty(entryHead.get("buyer_id_number"))){
                    SET("t.buyer_id_number = #{buyer_id_number}");
                }
                if (!StringUtils.isEmpty(entryHead.get("buyer_name"))){
                    SET("t.buyer_name = #{buyer_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("buyer_telephone"))){
                    SET("t.buyer_telephone = #{buyer_telephone}");
                }
                if (!StringUtils.isEmpty(entryHead.get("consignee_address"))){
                    SET("t.consignee_address = #{consignee_address}");
                }
                if (!StringUtils.isEmpty(entryHead.get("freight"))){
                    SET("t.freight = #{freight}");
                }
                if (!StringUtils.isEmpty(entryHead.get("agent_code"))){
                    SET("t.agent_code = #{agent_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("agent_name"))){
                    SET("t.agent_name = #{agent_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("traf_mode"))){
                    SET("t.traf_mode = #{traf_mode}");
                }
                if (!StringUtils.isEmpty(entryHead.get("traf_no"))){
                    SET("t.traf_no = #{traf_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("voyage_no"))){
                    SET("t.voyage_no = #{voyage_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("bill_no"))){
                    SET("t.bill_no = #{bill_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("country"))){
                    SET("t.country = #{country}");
                }
                if (!StringUtils.isEmpty(entryHead.get("gross_weight"))){
                    SET("t.gross_weight = #{gross_weight}");
                }
                if (!StringUtils.isEmpty(entryHead.get("note"))){
                    SET("t.note = #{note}");
                }
            }
        }.toString();
    }

    //修改清单表体信息
    public String updateImpInventoryBodies(LinkedHashMap<String, String> entryList){
        return new SQL(){
            {
                UPDATE("T_IMP_INVENTORY_BODY t");
                WHERE("t.HEAD_GUID = #{entryhead_guid}");
                WHERE("t.G_NUM = #{g_no}");
                if (!StringUtils.isEmpty(entryList.get("g_name"))){
                    SET("t.g_name = #{g_name}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_code"))){
                    SET("t.g_code = #{g_code}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_model"))){
                    SET("t.g_model = #{g_model}");
                }
                if (!StringUtils.isEmpty(entryList.get("country"))){
                    SET("t.country = #{country}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty"))){
                    SET("t.qty = #{qty}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit"))){
                    SET("t.unit = #{unit}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty1"))){
                    SET("t.qty1 = #{qty1}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit1"))){
                    SET("t.unit1 = #{unit1}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty2"))){
                    SET("t.qty2 = #{qty2}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit2"))){
                    SET("t.unit2 = #{unit2}");
                }
                if (!StringUtils.isEmpty(entryList.get("price"))){
                    SET("t.price = #{price}");
                }
                if (!StringUtils.isEmpty(entryList.get("total_price"))){
                    SET("t.total_price = #{total_price}");
                }
            }
        }.toString();
    }

















}
