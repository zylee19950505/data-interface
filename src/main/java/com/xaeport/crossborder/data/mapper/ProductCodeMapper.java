package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ProduecCode;
import com.xaeport.crossborder.data.provider.ProductCodeSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2019/01/18.
 */
@Mapper
public interface ProductCodeMapper {

    @SelectProvider(type = ProductCodeSqlProvider.class, method = "getPostalTaxList")
    List<ProduecCode> getProductCodList(Map<String, String> paramMap);

}
