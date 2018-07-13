package com.xaeport.crossborder.data.provider;


import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class PaymentDeclareSQLProvider extends BaseSQLProvider {

    /*
     * 订单申报数据查询
	 */
    public String queryPaymentDeclareList(Map<String, Object> paramMap) throws Exception {
        final String orderNo = paramMap.get("orderNo").toString();
        final String payTransactionId = paramMap.get("payTransactionId").toString();
        final String start = paramMap.get("start").toString();
        final String length = paramMap.get("length").toString();

        return new SQL() {
            {
                SELECT(
                        " * from ( select w.*, ROWNUM AS rn from ( " +
                        "SELECT" +
                        "    t.PAY_TRANSACTION_ID," +
                        "    t.ORDER_NO," +
                        "    t.PAY_NAME," +
                        "    t.EBP_NAME," +
                        "    t.PAYER_NAME," +
                        "    t.AMOUNT_PAID," +
                        "    t.PAY_TIME," +
                        "    t.NOTE," +
                        "    DECODE(t.APP_STATUS,'1','暂存','2','申报')as appStatus" +
                        "  FROM T_IMP_PAYMENT t");
                WHERE("1=1");
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.order_no = #{orderNo}");
                }
                if (!StringUtils.isEmpty(payTransactionId)) {
                    WHERE("t.PAY_TRANSACTION_ID = #{payTransactionId}");
                }
                if (!"-1".equals(length)) {
                    ORDER_BY("t.upd_tm desc ) w  )   WHERE rn >= #{start} AND rn < #{start} + #{length} ");
                } else {
                    ORDER_BY("t.upd_tm desc ) w  )   WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    /*
     * 订单申报总数查询
     */
    public String queryPaymentDeclareCount(Map<String, Object> paramMap) throws Exception {

        final String orderNo = paramMap.get("orderNo").toString();
        final String payTransactionId = paramMap.get("payTransactionId").toString();

        return new SQL() {
            {
                SELECT(" COUNT(*)  FROM T_IMP_PAYMENT t");
                WHERE("1=1");
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.order_no = #{orderNo}");
                }
                if (!StringUtils.isEmpty(payTransactionId)) {
                    WHERE("t.PAY_TRANSACTION_ID = #{payTransactionId}");
                }
            }
        }.toString();
    }
}
