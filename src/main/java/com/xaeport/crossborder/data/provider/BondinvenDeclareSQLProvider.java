package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.LinkedHashMap;
import java.util.Map;

public class BondinvenDeclareSQLProvider extends BaseSQLProvider {

    //查询保税清单页面数据
    public String queryBondInvenDeclareList(Map<String, String> paramMap) throws Exception {
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String orderNo = paramMap.get("orderNo");
        final String logisticsNo = paramMap.get("logisticsNo");
        final String end = paramMap.get("end");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT " +
                        "t.guid," +
                        "t.order_no," +
                        "t.logistics_no," +
                        "t.ebp_name," +
                        "t.ebc_name," +
                        "t.logistics_name," +
                        "t.app_time," +
                        "t.data_status," +
                        "t.cop_no");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE(splitJointIn("t.DATA_STATUS", dataStatus));
                }
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.order_no = #{orderNo}");
                }
                if (!StringUtils.isEmpty(logisticsNo)) {
                    WHERE("t.logistics_No = #{logisticsNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.CRT_TM >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.CRT_TM <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.CRT_TM desc ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.CRT_TM desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    //查询保税清单页面数据总数
    public String queryBondInvenDeclareCount(Map<String, String> paramMap) throws Exception {
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String orderNo = paramMap.get("orderNo");
        final String logisticsNo = paramMap.get("logisticsNo");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE(splitJointIn("t.DATA_STATUS", dataStatus));
                }
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.ORDER_NO = #{orderNo}");
                }
                if (!StringUtils.isEmpty(logisticsNo)) {
                    WHERE("t.logistics_No = #{logisticsNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.CRT_TM >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.CRT_TM <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
            }
        }.toString();
    }

    //更新提交海关数据为申报中
    public String updateSubmitCustom(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        final String dataStatusWhere = paramMap.get("dataStatusWhere");
        final String dataStatus = paramMap.get("dataStatus");
        final String userId = paramMap.get("userId");
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE(splitJointIn("t.GUID", submitKeys));
                WHERE(splitJointIn("t.DATA_STATUS", dataStatusWhere));
                SET("t.DATA_STATUS = #{dataStatus}");
                SET("t.APP_TIME = sysdate");
                SET("t.UPD_TM = sysdate");
                SET("t.UPD_ID = #{userId}");
            }
        }.toString();
    }

    //查询清单表头信息
    public String queryImpBondInvenHead(Map<String, String> paramMap) {
        final String id = paramMap.get("id");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.GUID = #{id}");
            }
        }.toString();

    }

    //查询清单表体信息
    public String queryImpBondInvenBodies(Map<String, String> paramMap) {
        final String id = paramMap.get("id");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_IMP_INVENTORY_BODY t");
                WHERE("t.HEAD_GUID = #{id}");
            }
        }.toString();
    }

    //修改清单表头信息
    public String updateImpBondInvenHead(LinkedHashMap<String, String> entryHead) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE("t.GUID = #{entryhead_guid}");
                SET("t.PRE_NO = '' ");
                SET("t.INVT_NO = '' ");
                SET("t.DATA_STATUS = 'BDDS5'");
                SET("t.UPD_TM = sysdate");
                if (!StringUtils.isEmpty(entryHead.get("order_no"))) {
                    SET("t.order_no = #{order_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("cop_no"))) {
                    SET("t.cop_no = #{cop_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("logistics_no"))) {
                    SET("t.logistics_no = #{logistics_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ebp_code"))) {
                    SET("t.ebp_code = #{ebp_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ebp_name"))) {
                    SET("t.ebp_name = #{ebp_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ebc_code"))) {
                    SET("t.ebc_code = #{ebc_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ebc_name"))) {
                    SET("t.ebc_name = #{ebc_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("assure_code"))) {
                    SET("t.assure_code = #{assure_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("customs_code"))) {
                    SET("t.customs_code = #{customs_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("port_code"))) {
                    SET("t.port_code = #{port_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ie_date"))) {
                    SET("t.ie_date = to_date(#{ie_date},'yyyy-MM-dd')");
                }
                if (!StringUtils.isEmpty(entryHead.get("buyer_id_number"))) {
                    SET("t.buyer_id_number = #{buyer_id_number}");
                }
                if (!StringUtils.isEmpty(entryHead.get("buyer_name"))) {
                    SET("t.buyer_name = #{buyer_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("buyer_telephone"))) {
                    SET("t.buyer_telephone = #{buyer_telephone}");
                }
                if (!StringUtils.isEmpty(entryHead.get("consignee_address"))) {
                    SET("t.consignee_address = #{consignee_address}");
                }
                if (!StringUtils.isEmpty(entryHead.get("freight"))) {
                    SET("t.freight = #{freight}");
                }
                if (!StringUtils.isEmpty(entryHead.get("wrap_type"))) {
                    SET("t.wrap_type = #{wrap_type}");
                }
                if (!StringUtils.isEmpty(entryHead.get("agent_code"))) {
                    SET("t.agent_code = #{agent_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("agent_name"))) {
                    SET("t.agent_name = #{agent_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("traf_mode"))) {
                    SET("t.traf_mode = #{traf_mode}");
                }
                if (!StringUtils.isEmpty(entryHead.get("area_code"))) {
                    SET("t.area_code = #{area_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("area_name"))) {
                    SET("t.area_name = #{area_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("head_country"))) {
                    SET("t.country = #{head_country}");
                }
                if (!StringUtils.isEmpty(entryHead.get("gross_weight"))) {
                    SET("t.gross_weight = #{gross_weight}");
                }
                if (!StringUtils.isEmpty(entryHead.get("net_weight"))) {
                    SET("t.net_weight = #{net_weight}");
                }
                if (!StringUtils.isEmpty(entryHead.get("note"))) {
                    SET("t.note = #{note}");
                }
                if (!StringUtils.isEmpty(entryHead.get("total_sum"))) {
                    SET("t.total_prices = #{total_sum}");
                }
            }
        }.toString();
    }

    //修改清单表头信息
    public String updateImpBondInvenHeadByList(LinkedHashMap<String, String> entryHead) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE("t.GUID = #{entryhead_guid}");
                SET("t.PRE_NO = '' ");
                SET("t.INVT_NO = '' ");
                SET("t.DATA_STATUS = 'BDDS5'");
                SET("t.UPD_TM = sysdate ");
            }
        }.toString();
    }

    //修改清单表体信息
    public String updateImpBondInvenBodies(LinkedHashMap<String, String> entryList) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_BODY t");
                WHERE("t.HEAD_GUID = #{entryhead_guid}");
                WHERE("t.G_NUM = #{g_no}");
                if (!StringUtils.isEmpty(entryList.get("g_name"))) {
                    SET("t.G_NAME = #{g_name}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_itemRecordNo"))) {
                    SET("t.ITEM_RECORD_NO = #{g_itemRecordNo}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_code"))) {
                    SET("t.G_CODE = #{g_code}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_model"))) {
                    SET("t.G_MODEL = #{g_model}");
                }
                if (!StringUtils.isEmpty(entryList.get("country"))) {
                    SET("t.COUNTRY = #{country}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_qty"))) {
                    SET("t.QTY = #{g_qty}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_unit"))) {
                    SET("t.UNIT = #{g_unit}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty_1"))) {
                    SET("t.QTY1 = #{qty_1}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit_1"))) {
                    SET("t.UNIT1 = #{unit_1}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty_2"))) {
                    SET("t.QTY2 = #{qty_2}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit_2"))) {
                    SET("t.UNIT2 = #{unit_2}");
                }
                if (!StringUtils.isEmpty(entryList.get("price"))) {
                    SET("t.PRICE = #{price}");
                }
                if (!StringUtils.isEmpty(entryList.get("total_price"))) {
                    SET("t.TOTAL_PRICE = #{total_price}");
                }
            }
        }.toString();
    }

    public String findWaitGenerated(Map<String, String> paramMap) {
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT("GUID");
                SELECT("APP_TYPE");
                SELECT("APP_TIME");
                SELECT("APP_STATUS");
                SELECT("ORDER_NO");
                SELECT("EBP_CODE");
                SELECT("EBP_NAME");
                SELECT("EBC_CODE");
                SELECT("EBC_NAME");
                SELECT("LOGISTICS_NO");
                SELECT("LOGISTICS_CODE");
                SELECT("LOGISTICS_NAME");
                SELECT("COP_NO");
                SELECT("PRE_NO");
                SELECT("ASSURE_CODE");
                SELECT("EMS_NO");
                SELECT("INVT_NO");
                SELECT("IE_FLAG");
                SELECT("DECL_TIME");
                SELECT("CUSTOMS_CODE");
                SELECT("PORT_CODE");
                SELECT("IE_DATE");
                SELECT("BUYER_ID_TYPE");
                SELECT("BUYER_ID_NUMBER");
                SELECT("BUYER_NAME");
                SELECT("BUYER_TELEPHONE");
                SELECT("CONSIGNEE_ADDRESS");
                SELECT("AGENT_CODE");
                SELECT("AGENT_NAME");
                SELECT("AREA_CODE");
                SELECT("AREA_NAME");
                SELECT("TRADE_MODE");
                SELECT("TRAF_MODE");
                SELECT("TRAF_NO");
                SELECT("VOYAGE_NO");
                SELECT("BILL_NO");
                SELECT("LOCT_NO");
                SELECT("LICENSE_NO");
                SELECT("COUNTRY");
                SELECT("FREIGHT");
                SELECT("INSURED_FEE");
                SELECT("CURRENCY");
                SELECT("WRAP_TYPE");
                SELECT("PACK_NO");
                SELECT("GROSS_WEIGHT");
                SELECT("NET_WEIGHT");
                SELECT("NOTE");
                SELECT("DATA_STATUS");
                SELECT("CRT_ID");
                SELECT("CRT_TM");
                SELECT("UPD_ID");
                SELECT("UPD_TM");
                SELECT("RETURN_STATUS");
                SELECT("ENT_ID");
                SELECT("ENT_NAME");
                SELECT("ENT_CUSTOMS_CODE");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("DATA_STATUS = #{dataStatus}");
                WHERE("rownum <= 100");
                ORDER_BY("t.CRT_TM asc,t.ORDER_NO asc");
            }
        }.toString();
    }

    public String updateEntryHeadDetailStatus(@Param("headGuid") String headGuid, @Param("dataStatus") String dataStatus) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE("t.GUID = #{headGuid}");
                SET("t.DATA_STATUS = #{dataStatus}");
                SET("t.UPD_TM = sysdate");
            }
        }.toString();
    }

    public String querydetailDeclareListByGuid(@Param("headGuid") String headGuid) {
        return new SQL() {
            {
                SELECT("G_NUM");
                SELECT("HEAD_GUID");
                SELECT("ORDER_NO");
                SELECT("ITEM_NO");
                SELECT("ITEM_NAME");
                SELECT("ITEM_RECORD_NO");
                SELECT("G_CODE");
                SELECT("G_NAME");
                SELECT("G_MODEL");
                SELECT("BAR_CODE");
                SELECT("COUNTRY");
                SELECT("CURRENCY");
                SELECT("QTY");
                SELECT("QTY1");
                SELECT("QTY2");
                SELECT("UNIT");
                SELECT("UNIT1");
                SELECT("UNIT2");
                SELECT("PRICE");
                SELECT("TOTAL_PRICE");
                SELECT("NOTE");
                FROM("T_IMP_INVENTORY_BODY t");
                WHERE("t.HEAD_GUID = #{headGuid}");
            }
        }.toString();
    }

    public String queryCompany(@Param("ent_id") String ent_id) {
        return new SQL() {
            {
                SELECT("t.CUSTOMS_CODE as copCode");
                SELECT("t.ENT_NAME as copName");
                SELECT("'DXP' as dxpMode");
                SELECT("t.DXP_ID as dxpId");
                SELECT("t.note as note");
                FROM("T_ENTERPRISE t");
                WHERE("t.id = #{ent_id}");
            }
        }.toString();
    }

}
