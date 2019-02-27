package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpGoodsOrder;
import com.xaeport.crossborder.data.provider.ImpGoodsOrderSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface ImpGoodsOrderMapper {


    @SelectProvider(type = ImpGoodsOrderSQLProvider.class,method = "queryImpGoodsOrderList")
    List<ImpGoodsOrder> queryImpGoodsOrderList(Map<String, String> paramMap);


    @SelectProvider(type = ImpGoodsOrderSQLProvider.class,method = "queryImpGoodsOrderListCount")
    Integer queryImpGoodsOrderListCount(Map<String, String> paramMap);
}
