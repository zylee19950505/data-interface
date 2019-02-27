package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class EntrypriseBillQuantitySQLProvider {

    public String queryEnterpriseBillQuantityList(Map<String, String> paramMap){
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String customsCode = paramMap.get("customsCode");
        final String tradeMode = paramMap.get("tradeMode");
        final String entName = paramMap.get("entName");
        final String entCustomsCode = paramMap.get("entCustomsCode");
        final String creditCode = paramMap.get("creditCode");

        return new SQL(){
            {
                SELECT("iih.ENT_NAME entName");
                SELECT("iih.ENT_CUSTOMS_CODE entCustomsCode");
                SELECT("(select CREDIT_CODE from t_enterprise en where en.ENT_NAME = iih.ENT_NAME) creditCode");
                SELECT("count(decode(ie_flag, 'I', 1, null)) as iInventoryValue");
                SELECT("count(decode(ie_flag, 'E', 1, null)) as eInventoryValue");
                SELECT("count(1) inventoryValue");
                FROM("T_IMP_INVENTORY_HEAD iih");
                if (!StringUtils.isEmpty(customsCode)){
                    WHERE("iih.CUSTOMS_CODE = #{customsCode}");
                }
                if (!StringUtils.isEmpty(tradeMode)){
                    WHERE("iih.TRADE_MODE = #{tradeMode}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("iih.APP_TIME >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("iih.APP_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(entName)){
                    WHERE("iih.ENT_NAME = #{entName}");
                }
                if (!StringUtils.isEmpty(entCustomsCode)){
                    WHERE("iih.ENT_CUSTOMS_CODE = #{entCustomsCode}");
                }
                if (!StringUtils.isEmpty(creditCode)){
                    WHERE("exists(select id from t_enterprise te where te.id = iih.ent_id and te.CREDIT_CODE = #{creditCode})");
                }

                GROUP_BY("iih.ENT_NAME,iih.ENT_CUSTOMS_CODE");
                ORDER_BY("iih.ENT_NAME,iih.ENT_CUSTOMS_CODE");
            }
        }.toString();
    }
}
