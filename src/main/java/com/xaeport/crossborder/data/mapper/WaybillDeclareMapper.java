package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.provider.WaybillDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface WaybillDeclareMapper {

    @SelectProvider(type = WaybillDeclareSQLProvider.class,method = "queryWaybillDeclareDataList")
    List<ImpLogistics> queryWaybillDeclareDataList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = WaybillDeclareSQLProvider.class,method = "queryWaybillDeclareCount")
    Integer queryWaybillDeclareCount(Map<String, String> paramMap) throws Exception;

}
