package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BuilderDetail;
import com.xaeport.crossborder.data.provider.DetailBuilderSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

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
}
