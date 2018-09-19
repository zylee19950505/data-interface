package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.CheckGoodsInfo;
import com.xaeport.crossborder.data.entity.ManifestHead;
import com.xaeport.crossborder.data.provider.ManifestCreateSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ManifestCreateMapper {

    @SelectProvider(type = ManifestCreateSQLProvider.class, method = "queryCheckGoodsInfoList")
    List<CheckGoodsInfo> queryCheckGoodsInfoList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = ManifestCreateSQLProvider.class, method = "queryManifestSum")
    CheckGoodsInfo queryManifestSum(Map<String, String> paramMap) throws Exception;

    //保存核放单信息
    @InsertProvider(type = ManifestCreateSQLProvider.class, method = "saveManifest")
    void saveManifest(LinkedHashMap<String, String> entryHead);

}
