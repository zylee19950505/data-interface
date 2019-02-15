package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpDelivery;
import com.xaeport.crossborder.data.provider.DeliveryQuerySQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeliveryQueryMapper {

    //查询入库明细单数据
    @SelectProvider(type = DeliveryQuerySQLProvider.class, method = "queryDeliveryQueryList")
    List<ImpDelivery> queryDeliveryQueryList(Map<String, String> paramMap) throws Exception;

}
