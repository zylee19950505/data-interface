package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class DetailQuerySQLProvider extends BaseSQLProvider{

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

}
