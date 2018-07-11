package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ProduecCode;
import com.xaeport.crossborder.data.provider.ProductCodeSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by zwj on 2017/10/24.
 */
@Mapper
public interface ProductCodeMapper {

    @SelectProvider(type = ProductCodeSqlProvider.class, method = "getPostalTaxList")
    @Results(
            {
                    @Result(column = "customs_code", property = "customsCode"),
                    @Result(column = "product_name", property = "productName"),
                    @Result(column = "unit_1", property = "unit1"),
                    @Result(column = "unit_2", property = "unit2"),
                    @Result(column = "import_duties_preference", property = "importDutiesPreference"),
                    @Result(column = "import_duties_general", property = "importDutiesGeneral"),
                    @Result(column = "added_tax", property = "addedTax"),
                    @Result(column = "consumption_tax", property = "consumptionTax"),
                    @Result(column = "regulatory_conditions", property = "regulatoryConditions"),
            }
    )
    List<ProduecCode> getProductCodList(Map<String, String> paramMap);
}
