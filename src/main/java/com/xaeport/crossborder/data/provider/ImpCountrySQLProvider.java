package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ImpCountrySQLProvider {


    public String queryImpCountryList(Map<String, String> paramMap){
        return new SQL(){
            {
                SELECT("t.COUNTRY countryCode,(select ca_cn_name from t_country_area ca where ca.ca_code = t.country) countryName,sum(t.TOTAL_PRICES)/10000 cargoValue,count(1) detailedCount");
                FROM("T_IMP_INVENTORY_HEAD t");
                GROUP_BY("t.COUNTRY");
                ORDER_BY("t.COUNTRY");
            }
        }.toString();
    }
}
