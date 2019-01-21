package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class DeliveryQuerySQLProvider extends BaseSQLProvider{

    public String queryDeliveryQueryList(Map<String, String> paramMap) throws Exception {
        final String billNo = paramMap.get("billNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT("BILL_NO");
                SELECT("APP_TIME");
                SELECT("count(LOGISTICS_NO) as asscount");
                SELECT("LOGISTICS_CODE");
                SELECT("LOGISTICS_NAME");
                SELECT("DATA_STATUS");
                SELECT("RETURN_STATUS");
                SELECT("RETURN_INFO");
                SELECT("RETURN_TIME");
                FROM("T_IMP_DELIVERY_HEAD t");
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE(splitJointIn("t.DATA_STATUS", dataStatus));
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.RETURN_STATUS = #{returnStatus}");
                }
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.BILL_NO = #{billNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.APP_TIME >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.APP_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                GROUP_BY("BILL_NO,APP_TIME,LOGISTICS_CODE,LOGISTICS_NAME,DATA_STATUS,RETURN_STATUS,RETURN_INFO,RETURN_TIME");
                ORDER_BY("t.BILL_NO,t.APP_TIME desc");
            }
        }.toString();
    }


}
