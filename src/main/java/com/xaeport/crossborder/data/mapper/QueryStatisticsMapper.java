package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.provider.QueryStatisticsSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface QueryStatisticsMapper {

    //查询统计——通关统计数据
    @SelectProvider(type = QueryStatisticsSQLProvider.class, method = "queryCustoms")
    List<ImpInventory> queryCustoms(Map<String, String> paramMap) throws Exception;

    //查询统计——商品统计数据
    @SelectProvider(type = QueryStatisticsSQLProvider.class, method = "queryCommodity")
    List<ImpInventory> queryCommodity(Map<String, String> paramMap) throws Exception;

    //查询统计——贸易国统计数据
    @SelectProvider(type = QueryStatisticsSQLProvider.class, method = "queryTradeCountryData")
    List<ImpInventory> queryTradeCountryData(Map<String, String> paramMap) throws Exception;

    //查询统计——清单查询数据
    @SelectProvider(type = QueryStatisticsSQLProvider.class, method = "queryInventoryQueryList")
    List<ImpInventoryHead> queryInventoryQueryList(Map<String, String> paramMap) throws Exception;

    //查询统计——清单查询总数
    @SelectProvider(type = QueryStatisticsSQLProvider.class, method = "queryInventoryQueryCount")
    Integer queryInventoryQueryCount(Map<String, String> paramMap) throws Exception;

    //查询统计——清单导出EXCEL表格数据
    @SelectProvider(type = QueryStatisticsSQLProvider.class, method = "queryInventoryExcelList")
    List<ImpInventory> queryInventoryExcelList(Map<String, String> paramMap) throws Exception;

    //查询统计——查询电商企业
    @SelectProvider(type = QueryStatisticsSQLProvider.class, method = "queryEbusinessEnt")
    List<Enterprise> queryEbusinessEnt() throws Exception;

}
