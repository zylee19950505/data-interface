package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ImpTradeVolumeSQLProvider extends BaseSQLProvider{

    public String queryImpTradeVolumeList(Map<String, String> paramMap){
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String customsCode = paramMap.get("customsCode");
        final String tradeMode = paramMap.get("tradeMode");

        return new SQL(){
            {
                SELECT("to_char(APP_TIME,'yyyy-MM') statisticsDate,sum(TOTAL_PRICES)/10000 cargoValue,count(1) detailedCount");
                FROM("T_IMP_INVENTORY_HEAD");
                if (!StringUtils.isEmpty(customsCode)){
                    WHERE("CUSTOMS_CODE = #{customsCode}");
                }
                if (!StringUtils.isEmpty(tradeMode)){
                    WHERE("TRADE_MODE = #{tradeMode}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("APP_TIME >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("APP_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                GROUP_BY("to_char(APP_TIME,'yyyy-MM')");
                ORDER_BY("to_char(APP_TIME,'yyyy-MM')");
            }
        }.toString();
    }

}
