package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpLogisticsData;
import com.xaeport.crossborder.data.entity.LogisticsHead;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.provider.WaybillQuerySQLProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface WaybillQueryMapper {

    @Delete("DELETE FROM T_VERIFY_STATUS WHERE CB_HEAD_ID = #{guid}")
    int deleteVerifyStatus(String guid);

    //查询运单数据
    @SelectProvider(type = WaybillQuerySQLProvider.class, method = "queryWaybillQueryDataList")
    List<ImpLogisticsData> queryWaybillQueryDataList(Map<String, String> paramMap) throws Exception;

    //查询运单数据总数
    @SelectProvider(type = WaybillQuerySQLProvider.class, method = "queryWaybillQueryCount")
    Integer queryWaybillQueryCount(Map<String, String> paramMap) throws Exception;

    //查看运单详情
    @SelectProvider(type = WaybillQuerySQLProvider.class, method = "seeWaybillDetail")
    LogisticsHead seeWaybillDetail(Map<String, String> paramMap);

    //查询逻辑校验信息
    @SelectProvider(type = WaybillQuerySQLProvider.class, method = "queryVerifyDetail")
    Verify queryVerifyDetail(Map<String, String> paramMap);

    //修改运单详情信息（运单查询）
    @UpdateProvider(type = WaybillQuerySQLProvider.class, method = "updateLogistics")
    void updateLogistics(LinkedHashMap<String, String> entryHead);

    //修改运单详情信息（逻辑校验）
    @UpdateProvider(type = WaybillQuerySQLProvider.class, method = "updateLogisticsByLogic")
    void updateLogisticsByLogic(LinkedHashMap<String, String> entryHead);

    //查询运单回执详情信息
    @SelectProvider(type = WaybillQuerySQLProvider.class, method = "queryReturnDetail")
    ImpLogistics queryReturnDetail(Map<String, String> paramMap);
}
