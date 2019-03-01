package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpCountryList;
import com.xaeport.crossborder.data.provider.ImpCountrySQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface ImpCountryMapper {


    @SelectProvider(type = ImpCountrySQLProvider.class,method = "queryImpCountryList")
    List<ImpCountryList> queryImpCountryList(Map<String, String> paramMap);

    @SelectProvider(type = ImpCountrySQLProvider.class,method = "queryImpCountryEChart")
    List<ImpCountryList> queryImpCountryEChart(Map<String, String> paramMap);
}
