package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ImpCountrySQLProvider {


    public String queryImpCountryList(Map<String, String> paramMap){
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String customsCode = paramMap.get("customsCode");
        final String tradeMode = paramMap.get("tradeMode");
        return new SQL(){
            {
                SELECT("w.* from " +
                        " (SELECT " +
                        " t.COUNTRY countryCode," +
                        " (select ca_cn_name FROM t_country_area ca WHERE ca.ca_code = t.country) countryName," +
                        " sum(t.TOTAL_PRICES)/10000 cargoValue," +
                        " count(1) detailedCount");
                FROM("T_IMP_INVENTORY_HEAD t ");
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
                GROUP_BY("t.COUNTRY");
                ORDER_BY("cargoValue desc)w");
            }
        }.toString();
    }
    public String queryImpCountryEChart(Map<String, String> paramMap){
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String customsCode = paramMap.get("customsCode");
        final String tradeMode = paramMap.get("tradeMode");
        return new SQL(){
            {
                SELECT("w.*,rownum from " +
                        " (SELECT " +
                        " t.COUNTRY countryCode," +
                        " (select ca_cn_name FROM t_country_area ca WHERE ca.ca_code = t.country) countryName,\n" +
                        " sum(t.TOTAL_PRICES)/10000 cargoValue," +
                        " count(1) detailedCount");
                FROM("T_IMP_INVENTORY_HEAD t ");
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
                GROUP_BY("t.COUNTRY");
                ORDER_BY("cargoValue desc)w where rownum<6");
            }
        }.toString();
    }
}
