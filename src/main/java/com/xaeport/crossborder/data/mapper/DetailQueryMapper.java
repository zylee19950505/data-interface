package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.provider.DetailQuerySQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface DetailQueryMapper {

    @SelectProvider(type = DetailQuerySQLProvider.class,method = "queryInventoryQueryList")
    List<ImpInventory> queryInventoryQueryList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = DetailQuerySQLProvider.class,method = "queryInventoryQueryCount")
    Integer queryInventoryQueryCount(Map<String, String> paramMap) throws Exception;

}
