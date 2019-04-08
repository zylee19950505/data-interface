package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BuilderDetail;
import com.xaeport.crossborder.data.provider.DetailBuilderSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DetailBuilderMapper {

    @SelectProvider(type = DetailBuilderSQLProvider.class,method = "queryBuilderDetailList")
    List<BuilderDetail> queryBuilderDetailList(Map<String, String> paramMap);

    @SelectProvider(type = DetailBuilderSQLProvider.class,method = "queryBuilderDetailCount")
    Integer queryBuilderDetailCount(Map<String, String> paramMap);

    @InsertProvider(type = DetailBuilderSQLProvider.class,method = "insertBuilderCache")
    void insertBuilderCache(Map<String, String> map);

    @Select("select count(1) count from T_BUILDER_CACHE t where t.ORDER_NO = #{orderNo}")
    int queryBuilderCache(Map<String, String> map);

    @UpdateProvider(type = DetailBuilderSQLProvider.class,method = "updateBuilderCache")
    void updateBuilderCache(Map<String, String> map);
}
