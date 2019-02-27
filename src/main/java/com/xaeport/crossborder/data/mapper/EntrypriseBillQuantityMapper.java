package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.EnterpriseBillQuantity;
import com.xaeport.crossborder.data.provider.EntrypriseBillQuantitySQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntrypriseBillQuantityMapper {


    @SelectProvider(type = EntrypriseBillQuantitySQLProvider.class,method = "queryEnterpriseBillQuantityList")
    List<EnterpriseBillQuantity> queryEnterpriseBillQuantityList(Map<String, String> paramMap);
}
