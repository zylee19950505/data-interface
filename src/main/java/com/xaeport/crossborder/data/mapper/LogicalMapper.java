package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpCrossBorderHead;
import com.xaeport.crossborder.data.provider.LogicalSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogicalMapper {

    @SelectProvider(type = LogicalSQLProvider.class,method = "getOrderLogicData")
    List<ImpCrossBorderHead> getOrderLogicData(Map<String,String> map);

    @SelectProvider(type = LogicalSQLProvider.class,method = "getInventoryLogicData")
    List<ImpCrossBorderHead> getInventoryLogicData(Map<String,String> map);

    @SelectProvider(type = LogicalSQLProvider.class,method = "getPaymentLogicData")
    List<ImpCrossBorderHead> getPaymentLogicData(Map<String,String> map);

    @SelectProvider(type = LogicalSQLProvider.class,method = "getLogisticsLogicData")
    List<ImpCrossBorderHead> getLogisticsLogicData(Map<String,String> map);

}
