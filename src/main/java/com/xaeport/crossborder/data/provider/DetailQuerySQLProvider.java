package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class DetailQuerySQLProvider extends BaseSQLProvider {

    //查询清单页面数据
    public String queryInventoryQueryList(Map<String, String> paramMap) throws Exception {
        final String billNo = paramMap.get("billNo");
        final String orderNo = paramMap.get("orderNo");
        final String logisticsNo = paramMap.get("logisticsNo");
        final String end = paramMap.get("end");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT t.bill_no," +
                        "t.guid," +
                        "t.order_no," +
                        "t.logistics_no," +
                        "t.invt_no," +
                        "t.ebp_name," +
                        "t.ebc_name," +
                        "t.logistics_name," +
                        "t.app_time," +
                        "t.return_status," +
                        "t.cop_no," +
//                        "(SELECT return_status " +
//                        "from t_imp_rec_inventory tt " +
//                        "where tt.cop_no = t.cop_no " +
//                        "and tt.return_time = " +
//                        "(select max(zz.return_time) " +
//                        "from t_imp_rec_inventory zz " +
//                        "where zz.cop_no = tt.cop_no " +
//                        "and length(zz.return_status) = 3) " +
//                        "and rownum = 1) maxtime_three_return_status," +
                        "(select ss.status_name " +
                        "from t_status ss " +
                        "where ss.status_code = t.return_status) return_status_name");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.DATA_STATUS = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE(splitJointIn("t.return_Status", returnStatus));
                }
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.bill_No = #{billNo}");
                }
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.order_no = #{orderNo}");
                }
                if (!StringUtils.isEmpty(logisticsNo)) {
                    WHERE("t.logistics_No = #{logisticsNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.app_time >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.app_time desc ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.app_time desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    //查询清单页面数据总数
    public String queryInventoryQueryCount(Map<String, String> paramMap) throws Exception {
        final String billNo = paramMap.get("billNo");
        final String orderNo = paramMap.get("orderNo");
        final String logisticsNo = paramMap.get("logisticsNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.DATA_STATUS = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE(splitJointIn("t.return_Status", returnStatus));
                }
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.bill_No = #{billNo}");
                }
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.ORDER_NO = #{orderNo}");
                }
                if (!StringUtils.isEmpty(logisticsNo)) {
                    WHERE("t.logistics_No = #{logisticsNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.app_time >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
            }
        }.toString();
    }

    //查询清单表头信息
    public String queryImpInventoryHead(Map<String, String> paramMap) {
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
    public String queryImpInventoryBodies(Map<String, String> paramMap) {
        final String id = paramMap.get("id");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_IMP_INVENTORY_BODY t");
                WHERE("t.HEAD_GUID = #{id}");
            }
        }.toString();
    }

    //查询清单回执信息
    public String getImpInventoryRec(Map<String, String> paramMap) {
        final String id = paramMap.get("id");
        return new SQL() {
            {
                SELECT("BILL_NO");
                SELECT("ORDER_NO");
                SELECT("LOGISTICS_NO");
                SELECT("COP_NO");
                SELECT("RETURN_STATUS");
                SELECT("RETURN_INFO");
                SELECT("RETURN_TIME");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.GUID = #{id}");
            }
        }.toString();

    }

    //修改清单表头信息
    public String updateImpInventoryHead(LinkedHashMap<String, String> entryHead) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE("t.GUID = #{entryhead_guid}");
                SET("t.PRE_NO = '' ");
                SET("t.INVT_NO = '' ");
                SET("t.DATA_STATUS = 'CBDS6'");
                SET("t.UPD_TM = sysdate ");
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
                if (!StringUtils.isEmpty(entryHead.get("traf_no"))) {
                    SET("t.traf_no = #{traf_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("voyage_no"))) {
                    SET("t.voyage_no = #{voyage_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("bill_no"))) {
                    SET("t.bill_no = #{bill_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("country"))) {
                    SET("t.country = #{country}");
                }
                if (!StringUtils.isEmpty(entryHead.get("gross_weight"))) {
                    SET("t.gross_weight = #{gross_weight}");
                }
                if (!StringUtils.isEmpty(entryHead.get("note"))) {
                    SET("t.note = #{note}");
                }
            }
        }.toString();
    }

    //修改清单表头信息
    public String updateImpInventoryHeadByList(LinkedHashMap<String, String> entryHead) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE("t.GUID = #{entryhead_guid}");
                SET("t.PRE_NO = '' ");
                SET("t.INVT_NO = '' ");
                SET("t.DATA_STATUS = 'CBDS6'");
                SET("t.UPD_TM = sysdate ");
            }
        }.toString();
    }

    //修改清单表体信息
    public String updateImpInventoryBodies(LinkedHashMap<String, String> entryList) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_BODY t");
                WHERE("t.HEAD_GUID = #{entryhead_guid}");
                WHERE("t.G_NUM = #{g_no}");
                if (!StringUtils.isEmpty(entryList.get("g_name"))) {
                    SET("t.g_name = #{g_name}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_code"))) {
                    SET("t.g_code = #{g_code}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_model"))) {
                    SET("t.g_model = #{g_model}");
                }
                if (!StringUtils.isEmpty(entryList.get("country"))) {
                    SET("t.country = #{country}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty"))) {
                    SET("t.qty = #{qty}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit"))) {
                    SET("t.unit = #{unit}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty1"))) {
                    SET("t.qty1 = #{qty1}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit1"))) {
                    SET("t.unit1 = #{unit1}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty2"))) {
                    SET("t.qty2 = #{qty2}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit2"))) {
                    SET("t.unit2 = #{unit2}");
                }
                if (!StringUtils.isEmpty(entryList.get("price"))) {
                    SET("t.price = #{price}");
                }
                if (!StringUtils.isEmpty(entryList.get("total_price"))) {
                    SET("t.total_price = #{total_price}");
                }
            }
        }.toString();
    }

    //修改清单表头信息
    public String updateImpInventoryHeadByLogic(LinkedHashMap<String, String> entryHead) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE("t.GUID = #{entryhead_guid}");
                SET("t.UPD_TM = sysdate ");
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
                if (!StringUtils.isEmpty(entryHead.get("traf_no"))) {
                    SET("t.traf_no = #{traf_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("voyage_no"))) {
                    SET("t.voyage_no = #{voyage_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("bill_no"))) {
                    SET("t.bill_no = #{bill_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("country"))) {
                    SET("t.country = #{country}");
                }
                if (!StringUtils.isEmpty(entryHead.get("gross_weight"))) {
                    SET("t.gross_weight = #{gross_weight}");
                }
                if (!StringUtils.isEmpty(entryHead.get("note"))) {
                    SET("t.note = #{note}");
                }
            }
        }.toString();
    }

    //修改清单表体信息
    public String updateImpInventoryBodiesByLogic(LinkedHashMap<String, String> entryList) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_BODY t");
                WHERE("t.HEAD_GUID = #{entryhead_guid}");
                WHERE("t.G_NUM = #{g_no}");
                if (!StringUtils.isEmpty(entryList.get("g_name"))) {
                    SET("t.g_name = #{g_name}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_code"))) {
                    SET("t.g_code = #{g_code}");
                }
                if (!StringUtils.isEmpty(entryList.get("g_model"))) {
                    SET("t.g_model = #{g_model}");
                }
                if (!StringUtils.isEmpty(entryList.get("country"))) {
                    SET("t.country = #{country}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty"))) {
                    SET("t.qty = #{qty}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit"))) {
                    SET("t.unit = #{unit}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty1"))) {
                    SET("t.qty1 = #{qty1}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit1"))) {
                    SET("t.unit1 = #{unit1}");
                }
                if (!StringUtils.isEmpty(entryList.get("qty2"))) {
                    SET("t.qty2 = #{qty2}");
                }
                if (!StringUtils.isEmpty(entryList.get("unit2"))) {
                    SET("t.unit2 = #{unit2}");
                }
                if (!StringUtils.isEmpty(entryList.get("price"))) {
                    SET("t.price = #{price}");
                }
                if (!StringUtils.isEmpty(entryList.get("total_price"))) {
                    SET("t.total_price = #{total_price}");
                }
            }
        }.toString();
    }

    public String queryVerifyDetail(Map<String, String> paramMap) {
        final String id = paramMap.get("id");
        return new SQL(){
            {
                SELECT("t.CB_HEAD_ID");
                SELECT("t.STATUS");
                SELECT("t.RESULT");
                FROM("T_VERIFY_STATUS t");
                WHERE("t.CB_HEAD_ID = #{id}");
                WHERE("t.STATUS = 'N'");
            }
        }.toString();
    }


}
