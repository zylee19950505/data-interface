package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.provider.WaybillQuerySQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface WaybillQueryMapper {

    @SelectProvider(type = WaybillQuerySQLProvider.class,method = "queryWaybillQueryDataList")
    List<ImpLogistics> queryWaybillQueryDataList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = WaybillQuerySQLProvider.class,method = "queryWaybillQueryCount")
    Integer queryWaybillQueryCount(Map<String, String> paramMap) throws Exception;

}
