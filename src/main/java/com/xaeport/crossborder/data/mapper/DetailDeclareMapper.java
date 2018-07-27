package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.provider.DetailDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface DetailDeclareMapper {

    @SelectProvider(type = DetailDeclareSQLProvider.class,method = "queryInventoryDeclareList")
    List<ImpInventory> queryInventoryDeclareList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = DetailDeclareSQLProvider.class,method = "queryInventoryDeclareCount")
    Integer queryInventoryDeclareCount(Map<String, String> paramMap) throws Exception;

}
