package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.provider.DetailQuerySQLProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface DetailQueryMapper {

    @Delete("DELETE FROM T_VERIFY_STATUS WHERE CB_HEAD_ID = #{guid}")
    int deleteVerifyStatus(String guid);

    @SelectProvider(type = DetailQuerySQLProvider.class, method = "queryInventoryQueryList")
    List<ImpInventory> queryInventoryQueryList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = DetailQuerySQLProvider.class, method = "queryInventoryQueryCount")
    Integer queryInventoryQueryCount(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = DetailQuerySQLProvider.class, method = "queryInventoryExcelList")
    List<ImpInventory> queryInventoryExcelList(Map<String, String> paramMap) throws Exception;

    //查询清单表头详情
    @SelectProvider(type = DetailQuerySQLProvider.class, method = "queryImpInventoryHead")
    ImpInventoryHead queryImpInventoryHead(Map<String, String> paramMap);

    //查询清单表体详情
    @SelectProvider(type = DetailQuerySQLProvider.class, method = "queryImpInventoryBodies")
    List<ImpInventoryBody> queryImpInventoryBodies(Map<String, String> paramMap);

    //修改清单表头信息
    @UpdateProvider(type = DetailQuerySQLProvider.class, method = "updateImpInventoryHead")
    void updateImpInventoryHead(LinkedHashMap<String, String> entryHead);

    //修改清单表头信息
    @UpdateProvider(type = DetailQuerySQLProvider.class, method = "updateImpInventoryHeadByLogic")
    void updateImpInventoryHeadByLogic(LinkedHashMap<String, String> entryHead);

    @UpdateProvider(type = DetailQuerySQLProvider.class, method = "updateImpInventoryHeadByList")
    void updateImpInventoryHeadByList(LinkedHashMap<String, String> entryHead);

    //修改清单表体信息
    @UpdateProvider(type = DetailQuerySQLProvider.class, method = "updateImpInventoryBodies")
    void updateImpInventoryBodies(LinkedHashMap<String, String> entryList);

    //修改清单表体信息
    @UpdateProvider(type = DetailQuerySQLProvider.class, method = "updateImpInventoryBodiesByLogic")
    void updateImpInventoryBodiesByLogic(LinkedHashMap<String, String> entryList);

    //查询清单回执详情
    @SelectProvider(type = DetailQuerySQLProvider.class, method = "getImpInventoryRec")
    ImpInventoryHead getImpInventoryRec(Map<String, String> paramMap);

}
