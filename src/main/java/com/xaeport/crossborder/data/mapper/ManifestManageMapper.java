package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.CheckGoodsInfo;
import com.xaeport.crossborder.data.entity.ManifestHead;
import com.xaeport.crossborder.data.provider.ManifestManageSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManifestManageMapper {

    //查询核放单数据
    @SelectProvider(type = ManifestManageSQLProvider.class, method = "queryManifestManageList")
    List<ManifestHead> queryManifestManageList(Map<String, String> paramMap) throws Exception;

    //查询核放单总数
    @SelectProvider(type = ManifestManageSQLProvider.class, method = "queryManifestManageCount")
    Integer queryManifestManageCount(Map<String, String> paramMap);

    //核放单申报，更新预订数据状态
    @UpdateProvider(type = ManifestManageSQLProvider.class, method = "manifestDeclare")
    void manifestDeclare(Map<String, String> paramMap) throws Exception;

    //查找核放单申报中的数据
    @SelectProvider(type = ManifestManageSQLProvider.class, method = "findManifestManage")
    List<ManifestHead> findManifestManage(Map<String, String> paramMap);

    //把核放单置为已申报状态
    @UpdateProvider(type = ManifestManageSQLProvider.class, method = "updateManifestManage")
    void updateManifestManage(@Param("manifestNo") String manifestNo);

    //查询预订数据
    @SelectProvider(type = ManifestManageSQLProvider.class, method = "queryCheckGoodsInfo")
    List<CheckGoodsInfo> queryCheckGoodsInfo(@Param("manifest_no") String manifest_no);

    //更新预订数据状态
    @UpdateProvider(type = ManifestManageSQLProvider.class, method = "updateCheckGoodsInfo")
    void updateCheckGoodsInfo(@Param("manifest_no") String manifest_no);

    //删除核放单数据
    @DeleteProvider(type = ManifestManageSQLProvider.class, method = "manifestDelete")
    void manifestDelete(@Param("manifest_no") String manifest_no);


    //查询核放单表头打印数据
    @SelectProvider(type = ManifestManageSQLProvider.class, method = "queryManifestHead")
    ManifestHead queryManifestHead(Map<String, String> paramMap) throws Exception;

    //查询核放单表体打印数据
    @SelectProvider(type = ManifestManageSQLProvider.class, method = "queryCheckGoodsInfoList")
    List<CheckGoodsInfo> queryCheckGoodsInfoList(Map<String, String> paramMap) throws Exception;


}
