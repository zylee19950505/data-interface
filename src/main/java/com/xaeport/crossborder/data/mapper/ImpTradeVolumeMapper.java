package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpTradeVolumeList;
import com.xaeport.crossborder.data.provider.ImpTradeVolumeSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface ImpTradeVolumeMapper {


    @SelectProvider(type = ImpTradeVolumeSQLProvider.class,method = "queryImpTradeVolumeList")
    List<ImpTradeVolumeList> queryImpTradeVolumeList(Map<String, String> paramMap);
}
