package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ManifestHead;
import com.xaeport.crossborder.data.provider.ManifestManageSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManifestManageMapper {


    @SelectProvider(type = ManifestManageSQLProvider.class, method = "queryManifestManageList")
    List<ManifestHead> queryManifestManageList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = ManifestManageSQLProvider.class, method = "queryManifestManageCount")
    Integer queryManifestManageCount(Map<String, String> paramMap);

    @UpdateProvider(type = ManifestManageSQLProvider.class, method = "manifestDeclare")
    void manifestDeclare(Map<String, String> paramMap) throws Exception;

    //查找核放单申报中的数据
    @SelectProvider(type = ManifestManageSQLProvider.class, method = "findManifestManage")
    List<ManifestHead> findManifestManage(Map<String, String> paramMap);

    @UpdateProvider(type = ManifestManageSQLProvider.class, method = "updateManifestManage")
    void updateManifestManage(@Param("manifestNo") String manifestNo);



    @UpdateProvider(type = ManifestManageSQLProvider.class, method = "updateCheckGoodsInfo")
    void updateCheckGoodsInfo(@Param("manifest_no") String manifest_no);

    @UpdateProvider(type = ManifestManageSQLProvider.class, method = "manifestDelete")
    void manifestDelete(@Param("manifest_no") String manifest_no);


}
