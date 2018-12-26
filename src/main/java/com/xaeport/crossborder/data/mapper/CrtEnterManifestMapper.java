package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.provider.CrtEnterManifestSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrtEnterManifestMapper {

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "queryCrtEnterManifest")
    List<BondInvtBsc> queryCrtEnterManifest(Map<String, String> paramMap);
}
