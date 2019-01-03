package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BwlListType;
import com.xaeport.crossborder.data.provider.StockControlSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockControlMapper {

    @SelectProvider(type = StockControlSQLProvider.class, method = "queryStockControlData")
    List<BwlListType> queryStockControlData(Map<String, String> paramMap);

    @SelectProvider(type = StockControlSQLProvider.class, method = "queryStockControlCount")
    Integer queryStockControlCount(Map<String, String> paramMap);

}
