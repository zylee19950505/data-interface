package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class LogicalSQLProvider {

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
                    WHERE("t.ie_date = #{ie_date}");
                }
                ORDER_BY("t.bill_no asc");
                ORDER_BY("t.order_no asc");
            }
        }.toString();
    }

}
