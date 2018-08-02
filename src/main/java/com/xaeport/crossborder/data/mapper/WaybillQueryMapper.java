package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.LogisticsHead;
import com.xaeport.crossborder.data.provider.WaybillQuerySQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface WaybillQueryMapper {

    @SelectProvider(type = WaybillQuerySQLProvider.class,method = "queryWaybillQueryDataList")
    List<ImpLogistics> queryWaybillQueryDataList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = WaybillQuerySQLProvider.class,method = "queryWaybillQueryCount")
    Integer queryWaybillQueryCount(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = WaybillQuerySQLProvider.class,method ="waybillQueryById")
    LogisticsHead waybillQueryById(Map<String, String> paramMap);

    @UpdateProvider(type =  WaybillQuerySQLProvider.class,method ="updateBillHead")
	void updateBillHead(LinkedHashMap<String, String> entryHead);
}
