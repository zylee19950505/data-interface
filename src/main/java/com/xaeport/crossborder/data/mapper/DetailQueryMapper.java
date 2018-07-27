package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
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

    //查询清单表头详情
    @SelectProvider(type = DetailQuerySQLProvider.class,method = "queryImpInventoryHead")
    ImpInventoryHead queryImpInventoryHead(Map<String, String> paramMap);

    //查询清单表体详情
    @SelectProvider(type = DetailQuerySQLProvider.class,method = "queryImpInventoryBodies")
    List<ImpInventoryBody> queryImpInventoryBodies(Map<String, String> paramMap);

}
