package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by lzy on 2019/01/18.
 */
public class ProductCodeSqlProvider {
    public String getPostalTaxList(final Map<String, String> paramMap) {
        final String customsCode = paramMap.get("customsCode");
        final String type = paramMap.get("type");

        return new SQL() {
            {
                SELECT("customs_code customsCode," +
                        "product_name productName," +
                        "unit_1 unit1," +
                        "unit_2 unit2," +
                        "import_duties_preference importDutiesPreference," +
                        "import_duties_general importDutiesGeneral," +
                        "added_tax addedTax," +
                        "consumption_tax consumptionTax," +
                        "regulatory_conditions regulatoryConditions," +
                        "type," +
                        "note");
                FROM("T_PRODUCTCODE");
                if (!StringUtils.isEmpty(customsCode)) {
                    WHERE("customs_code like '%'|| #{customsCode} ||'%' ");
                }
                if (!StringUtils.isEmpty(type)) {
                    WHERE("type = #{type}");
                } else {
                    WHERE("type in ('BONDED_I','CB_I')");
                }
                ORDER_BY("customs_code asc");
            }
        }.toString();
    }
}
