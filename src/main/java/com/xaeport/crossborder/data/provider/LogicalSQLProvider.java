package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class LogicalSQLProvider {

    public String queryDeleteOrderByGuids(Map<String, String> paramMap) throws Exception {
        final String type = paramMap.get("type");
        if (StringUtils.isEmpty(type)) throw new Exception("校验类型 type 为空");
        final String headGuid = paramMap.get("headGuid");
        final String entId = paramMap.get("entId");
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_IMP_ORDER_HEAD t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS vs ON t.GUID = vs.CB_HEAD_ID and vs.type = #{type}");
                if (!StringUtils.isEmpty(headGuid)) {
                    String headGuidStr = headGuid.replace(",", "','");
                    WHERE("t.GUID in ('" + headGuidStr + "')");
                }
                if (!StringUtils.isEmpty(entId)) WHERE("t.ENT_ID = #{entId}");
                if (!StringUtils.isEmpty(dataStatus)) WHERE("t.DATA_STATUS = #{dataStatus}");
                WHERE("(vs.STATUS is null OR vs.STATUS = 'N')");
                ORDER_BY("t.BILL_NO asc");
                ORDER_BY("t.ORDER_NO asc");
            }
        }.toString();
    }

    public String queryDeleteInventoryByGuids(Map<String, String> paramMap) throws Exception {
        final String type = paramMap.get("type");
        if (StringUtils.isEmpty(type)) throw new Exception("校验类型 type 为空");
        final String headGuid = paramMap.get("headGuid");
        final String entId = paramMap.get("entId");
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_IMP_INVENTORY_HEAD t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS vs ON t.GUID = vs.CB_HEAD_ID and vs.type = #{type}");
                if (!StringUtils.isEmpty(headGuid)) {
                    String headGuidStr = headGuid.replace(",", "','");
                    WHERE("t.GUID in ('" + headGuidStr + "')");
                }
                if (!StringUtils.isEmpty(entId)) WHERE("t.ENT_ID = #{entId}");
                if (!StringUtils.isEmpty(dataStatus)) WHERE("t.DATA_STATUS = #{dataStatus}");
                WHERE("(vs.STATUS is null OR vs.STATUS = 'N')");
                ORDER_BY("t.BILL_NO asc");
                ORDER_BY("t.ORDER_NO asc");
            }
        }.toString();
    }

    public String queryDeleteLogisticsByGuids(Map<String, String> paramMap) throws Exception {
        final String type = paramMap.get("type");
        if (StringUtils.isEmpty(type)) throw new Exception("校验类型 type 为空");
        final String headGuid = paramMap.get("headGuid");
        final String entId = paramMap.get("entId");
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_IMP_LOGISTICS t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS vs ON t.GUID = vs.CB_HEAD_ID and vs.type = #{type}");
                if (!StringUtils.isEmpty(headGuid)) {
                    String headGuidStr = headGuid.replace(",", "','");
                    WHERE("t.GUID in ('" + headGuidStr + "')");
                }
                if (!StringUtils.isEmpty(entId)) WHERE("t.ENT_ID = #{entId}");
                if (!StringUtils.isEmpty(dataStatus)) WHERE("t.DATA_STATUS = #{dataStatus}");
                WHERE("(vs.STATUS is null OR vs.STATUS = 'N')");
                ORDER_BY("t.BILL_NO asc");
                ORDER_BY("t.ORDER_NO asc");
            }
        }.toString();
    }

    public String queryDeletePaymentByGuids(Map<String, String> paramMap) throws Exception {
        final String type = paramMap.get("type");
        if (StringUtils.isEmpty(type)) throw new Exception("校验类型 type 为空");
        final String headGuid = paramMap.get("headGuid");
        final String entId = paramMap.get("entId");
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_IMP_PAYMENT t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS vs ON t.GUID = vs.CB_HEAD_ID and vs.type = #{type}");
                if (!StringUtils.isEmpty(headGuid)) {
                    String headGuidStr = headGuid.replace(",", "','");
                    WHERE("t.GUID in ('" + headGuidStr + "')");
                }
                if (!StringUtils.isEmpty(entId)) WHERE("t.ENT_ID = #{entId}");
                if (!StringUtils.isEmpty(dataStatus)) WHERE("t.DATA_STATUS = #{dataStatus}");
                WHERE("(vs.STATUS is null OR vs.STATUS = 'N')");
                ORDER_BY("t.ORDER_NO asc");
            }
        }.toString();
    }

    public String getOrderLogicData(Map<String, String> map) {
        final String bill_no = map.get("bill_no");
        final String order_no = map.get("order_no");
        final String data_status = map.get("data_status");
        final String type = map.get("type");
        final String status = map.get("status");
        final String entId = map.get("entId");
        final String roleId = map.get("roleId");
        return new SQL() {
            {
                SELECT("t.guid");
                SELECT("t.bill_no");
                SELECT("t.order_no");
                SELECT("v.result vs_result");
                FROM("T_IMP_ORDER_HEAD t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS v on t.GUID = v.CB_HEAD_ID and v.type = #{type}");
                WHERE("t.data_status = #{data_status}");
                WHERE("v.status = #{status}");
                if(!roleId.equals("admin")){
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(bill_no)) {
                    WHERE("t.bill_no = #{bill_no}");
                }
                if (!StringUtils.isEmpty(order_no)) {
                    WHERE("t.order_no = #{order_no}");
                }
                ORDER_BY("t.bill_no asc");
                ORDER_BY("t.order_no asc");
            }
        }.toString();
    }

    public String getInventoryLogicData(Map<String, String> map) {
        final String bill_no = map.get("bill_no");
        final String order_no = map.get("order_no");
        final String voyage_no = map.get("voyage_no");
        final String ie_date = map.get("ie_date");
        final String data_status = map.get("data_status");
        final String type = map.get("type");
        final String status = map.get("status");
        final String entId = map.get("entId");
        final String roleId = map.get("roleId");
        return new SQL() {
            {
                SELECT("t.guid");
                SELECT("t.bill_no");
                SELECT("t.order_no");
                SELECT("t.voyage_no");
                SELECT("t.ie_date");
                SELECT("v.result vs_result");
                FROM("T_IMP_INVENTORY_HEAD t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS v on t.GUID = v.CB_HEAD_ID and v.type = #{type}");
                WHERE("t.data_status = #{data_status}");
                WHERE("v.status = #{status}");
                if(!roleId.equals("admin")){
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(bill_no)) {
                    WHERE("t.bill_no = #{bill_no}");
                }
                if (!StringUtils.isEmpty(order_no)) {
                    WHERE("t.order_no = #{order_no}");
                }
                if (!StringUtils.isEmpty(voyage_no)) {
                    WHERE("t.voyage_no = #{voyage_no}");
                }
                if (!StringUtils.isEmpty(ie_date)) {
                    WHERE("t.ie_date >= to_date(#{ie_date} || '00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                ORDER_BY("t.bill_no asc");
                ORDER_BY("t.order_no asc");
            }
        }.toString();
    }

    public String getPaymentLogicData(Map<String, String> map) {
        final String order_no = map.get("order_no");
        final String pay_transaction_id = map.get("pay_transaction_id");
        final String status = map.get("status");
        final String data_status = map.get("data_status");
        final String type = map.get("type");
        final String entId = map.get("entId");
        final String roleId = map.get("roleId");
        return new SQL() {
            {
                SELECT("t.guid");
                SELECT("t.pay_transaction_id");
                SELECT("t.order_no");
                SELECT("v.result vs_result");
                FROM("T_IMP_PAYMENT t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS v on t.GUID = v.CB_HEAD_ID and v.type = #{type}");
                WHERE("t.data_status = #{data_status}");
                WHERE("v.status = #{status}");
                if(!roleId.equals("admin")){
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(pay_transaction_id)) {
                    WHERE("t.pay_transaction_id = #{pay_transaction_id}");
                }
                if (!StringUtils.isEmpty(order_no)) {
                    WHERE("t.order_no = #{order_no}");
                }
                ORDER_BY("t.order_no asc");
                ORDER_BY("t.pay_transaction_id asc");
            }
        }.toString();
    }

    public String getLogisticsLogicData(Map<String, String> map) {
        final String bill_no = map.get("bill_no");
        final String order_no = map.get("order_no");
        final String logistics_no = map.get("logistics_no");
        final String status = map.get("status");
        final String data_status = map.get("data_status");
        final String type = map.get("type");
        final String entId = map.get("entId");
        final String roleId = map.get("roleId");
        return new SQL() {
            {
                SELECT("t.guid");
                SELECT("t.bill_no");
                SELECT("t.order_no");
                SELECT("t.logistics_no");
                SELECT("v.result vs_result");
                FROM("T_IMP_LOGISTICS t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS v on t.GUID = v.CB_HEAD_ID and v.type = #{type}");
                WHERE("t.data_status = #{data_status}");
                WHERE("v.status = #{status}");
                if(!roleId.equals("admin")){
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(bill_no)) {
                    WHERE("t.bill_no = #{bill_no}");
                }
                if (!StringUtils.isEmpty(order_no)) {
                    WHERE("t.order_no = #{order_no}");
                }
                if (!StringUtils.isEmpty(logistics_no)) {
                    WHERE("t.logistics_no = #{logistics_no}");
                }
                ORDER_BY("t.bill_no asc");
                ORDER_BY("t.logistics_no asc");
            }
        }.toString();
    }


}
