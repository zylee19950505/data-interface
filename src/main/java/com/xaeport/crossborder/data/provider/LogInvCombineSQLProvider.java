package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.LogInvCombine;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class LogInvCombineSQLProvider extends BaseSQLProvider {

    public String findWaitGenerated(Map<String, String> paramMap) {
        String mark = paramMap.get("mark");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_LOG_INV_COMBINE");
                WHERE("ORDER_MARK = #{mark}");
                WHERE("LOGISTICS_MARK = #{mark}");
                WHERE("ROWNUM <= 100");
            }
        }.toString();
    }

    public String insertLogInvCombineHis(@Param("logInvCombine") LogInvCombine logInvCombine) {
        return new SQL() {
            {
                INSERT_INTO("T_LOG_INV_COMBINE_HIS");
                if (!StringUtils.isEmpty(logInvCombine.getId())) {
                    VALUES("ID", "#{logInvCombine.id}");
                }
                if (!StringUtils.isEmpty(logInvCombine.getBill_no())) {
                    VALUES("BILL_NO", "#{logInvCombine.bill_no}");
                }
                if (!StringUtils.isEmpty(logInvCombine.getOrder_no())) {
                    VALUES("ORDER_NO", "#{logInvCombine.order_no}");
                }
                if (!StringUtils.isEmpty(logInvCombine.getLogistics_no())) {
                    VALUES("LOGISTICS_NO", "#{logInvCombine.logistics_no}");
                }
                if (!StringUtils.isEmpty(logInvCombine.getOrder_mark())) {
                    VALUES("ORDER_MARK", "#{logInvCombine.order_mark}");
                }
                if (!StringUtils.isEmpty(logInvCombine.getLogistics_mark())) {
                    VALUES("LOGISTICS_MARK", "#{logInvCombine.logistics_mark}");
                }
                if (!StringUtils.isEmpty(logInvCombine.getId())) {
                    VALUES("CRT_TM", "sysdate");
                }
            }
        }.toString();
    }

    public String deleteLogInvCombine(@Param("logInvCombine") LogInvCombine logInvCombine) {
        return new SQL() {
            {
                DELETE_FROM("T_LOG_INV_COMBINE");
                WHERE("ID = #{logInvCombine.id}");
                WHERE("ORDER_NO = #{logInvCombine.order_no}");
                WHERE("LOGISTICS_NO = #{logInvCombine.logistics_no}");
            }
        }.toString();
    }

    public String queryInventoryHead(@Param("logInvCombine") LogInvCombine logInvCombine) {
        return new SQL() {
            {
                SELECT("COP_NO");
                SELECT("APP_TIME");
                SELECT("ORDER_NO");
                SELECT("LOGISTICS_NO");
                SELECT("EBP_CODE");
                SELECT("EBP_NAME");
                SELECT("LOGISTICS_CODE");
                SELECT("LOGISTICS_NAME");
                SELECT("BUYER_NAME");
                FROM("T_IMP_INVENTORY_HEAD");
                WHERE("BILL_NO = #{logInvCombine.bill_no}");
                WHERE("ORDER_NO = #{logInvCombine.order_no}");
                WHERE("LOGISTICS_NO = #{logInvCombine.logistics_no}");
            }
        }.toString();
    }

    public String queryInventoryBody(@Param("logInvCombine") LogInvCombine logInvCombine) {
        return new SQL() {
            {
                SELECT("ITEM_NO");
                SELECT("QTY");
                SELECT("UNIT");
                FROM("T_IMP_INVENTORY_BODY");
                WHERE("HEAD_GUID IN ( " +
                        " SELECT GUID FROM T_IMP_INVENTORY_HEAD " +
                        " WHERE BILL_NO = #{logInvCombine.bill_no}" +
                        " AND ORDER_NO = #{logInvCombine.order_no}" +
                        " AND LOGISTICS_NO = #{logInvCombine.logistics_no}" +
                        " )");
            }
        }.toString();
    }

    public String queryLogistics(@Param("logInvCombine") LogInvCombine logInvCombine) {
        return new SQL() {
            {
                SELECT("CONSINGEE");
                SELECT("CONSIGNEE_ADDRESS");
                SELECT("CONSIGNEE_TELEPHONE");
                SELECT("WEIGHT");
                FROM("T_IMP_LOGISTICS");
                WHERE("BILL_NO = #{logInvCombine.bill_no}");
                WHERE("ORDER_NO = #{logInvCombine.order_no}");
                WHERE("LOGISTICS_NO = #{logInvCombine.logistics_no}");
            }
        }.toString();
    }

}
