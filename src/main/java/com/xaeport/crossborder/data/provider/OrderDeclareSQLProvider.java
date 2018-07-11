package com.xaeport.crossborder.data.provider;


import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class OrderDeclareSQLProvider extends BaseSQLProvider {

    /*
     * 订单申报数据查询
	 */
    public String queryOrderDeclareList(Map<String, Object> paramMap) throws Exception {
        final String orderNo = paramMap.get("orderNo").toString();
        final String startFlightTimes = paramMap.get("startFlightTimes").toString();
        final String endFlightTimes = paramMap.get("endFlightTimes").toString();
        final String start = paramMap.get("start").toString();
        final String length = paramMap.get("length").toString();

        return new SQL() {
            {
                SELECT(" * from ( select w.*, ROWNUM AS rn from ( " +
                        "SELECT" +
                        "    tl.order_no," +
                        "    th.ebp_name," +
                        "    th.ebc_name," +
                        "    tl.item_name," +
                        "    tl.total_price," +
                        "    th.buyer_name," +
                        "    DECODE(th.APP_STATUS,'1','暂存','2','申报')as appStatus," +
                        "    th.APP_TIME," +
                        "    th.NOTE" +
                        "  FROM" +
                        "    t_imp_order_body tl," +
                        "    t_imp_order_head th");
                WHERE("1=1");
                WHERE("th.order_no = tl.order_no");
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("th.order_no = #{orderNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE(" th.upd_tm >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE(" th.upd_tm <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(length)) {
                    ORDER_BY("th.upd_tm desc ) w  )   WHERE rn >= #{start} AND rn < #{start} + #{length} ");
                } else {
                    ORDER_BY("th.upd_tm desc ) w  )   WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    /*
     * 订单申报总数查询
     */
    public String queryOrderDeclareCount(Map<String, Object> paramMap) throws Exception {

        final String orderNo = paramMap.get("orderNo").toString();
        final String startFlightTimes = paramMap.get("startFlightTimes").toString();
        final String endFlightTimes = paramMap.get("endFlightTimes").toString();

        String orderNoStr = "";
        if (!StringUtils.isEmpty(orderNo)) {
            orderNoStr = " AND th.order_no = #{orderNo}";
        }
        String startTimesStr = "";
        if (!StringUtils.isEmpty(startFlightTimes)) {
            startTimesStr = " AND th.upd_tm >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')";
        }
        String endTimesStr = "";
        if (!StringUtils.isEmpty(endFlightTimes)) {
            endTimesStr = " AND th.upd_tm <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')";
        }
        return " select COUNT(*) count FROM t_imp_order_body tl, t_imp_order_head th where 1=1 and th.ORDER_NO =tl.ORDER_NO " + startTimesStr + endTimesStr;

    }
}
