package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 *
 * Created by zwj on 2017/10/24.
 */
public class ProductCodeSqlProvider {
    public String getPostalTaxList(final Map<String, String> paramMap) {
        final String customsCode = paramMap.get("customsCode");

        return new SQL() {
            {
                SELECT("customs_code," +
                        "product_name," +
                        "unit_1," +
                        "unit_2," +
                        "import_duties_preference," +
                        "import_duties_general," +
                        "added_tax," +
                        "consumption_tax",
                        "regulatory_conditions");
                FROM("T_PRODUCTCODE");
                if (!StringUtils.isEmpty(customsCode)) {
                    WHERE("customs_code like '%'|| #{customsCode} ||'%' ");
                }
                ORDER_BY("customs_code asc");
            }
        }.toString();
    }
}
