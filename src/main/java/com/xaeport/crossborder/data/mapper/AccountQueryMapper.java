package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BwlListType;
import com.xaeport.crossborder.data.provider.AccountQuerySQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountQueryMapper {

    @SelectProvider(type = AccountQuerySQLProvider.class, method = "queryBwlListTypeData")
    List<BwlListType> queryBwlListTypeData(Map<String, String> paramMap);

    @SelectProvider(type = AccountQuerySQLProvider.class, method = "queryBwlListTypeCount")
    Integer queryBwlListTypeCount(Map<String, String> paramMap);

}
