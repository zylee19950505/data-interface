package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.provider.BondinvenQuerySQLProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BondinvenQueryMapper {

    @Delete("DELETE FROM T_VERIFY_STATUS WHERE CB_HEAD_ID = #{entryHeadId}")
    int deleteVerifyStatus(String guid);

    //查询保税清单数据
    @SelectProvider(type = BondinvenQuerySQLProvider.class, method = "queryBondInvenQueryData")
    List<ImpInventory> queryBondInvenQueryData(Map<String, String> paramMap) throws Exception;

    //查询保税清单数据总数
    @SelectProvider(type = BondinvenQuerySQLProvider.class, method = "queryBondInvenQueryCount")
    Integer queryBondInvenQueryCount(Map<String, String> paramMap) throws Exception;

    //查询清单表头详情
    @SelectProvider(type = BondinvenQuerySQLProvider.class, method = "queryImpInventoryHead")
    ImpInventoryHead queryImpInventoryHead(Map<String, String> paramMap);

    //查询清单表体详情
    @SelectProvider(type = BondinvenQuerySQLProvider.class, method = "queryImpInventoryBodies")
    List<ImpInventoryBody> queryImpInventoryBodies(Map<String, String> paramMap);

    //查询清单表头详情
    @SelectProvider(type = BondinvenQuerySQLProvider.class, method = "queryVerifyDetail")
    Verify queryVerifyDetail(Map<String, String> paramMap);

    //修改保税清单表头信息（申报后）
    @UpdateProvider(type = BondinvenQuerySQLProvider.class, method = "updateImpBondInvenHeadAfter")
    void updateImpBondInvenHeadAfter(LinkedHashMap<String, String> entryHead);

    //修改保税清单表体数据（清单查询）（报后）
            @UpdateProvider(type = BondinvenQuerySQLProvider.class, method = "updateImpBondInvenHeadByList")
    void updateImpBondInvenHeadByList(LinkedHashMap<String, String> entryHead);

    //修改保税清单表体信息（清单查询）（申报后）
    @UpdateProvider(type = BondinvenQuerySQLProvider.class, method = "updateImpBondInvenBodyAfter")
    void updateImpBondInvenBodyAfter(LinkedHashMap<String, String> entryList);

    //修改清单表头信息（逻辑校验）
    @UpdateProvider(type = BondinvenQuerySQLProvider.class, method = "updateImpInventoryHeadByLogic")
    void updateImpInventoryHeadByLogic(LinkedHashMap<String, String> entryHead);

    //修改清单表体信息（逻辑校验）
    @UpdateProvider(type = BondinvenQuerySQLProvider.class, method = "updateImpInventoryBodiesByLogic")
    void updateImpInventoryBodiesByLogic(LinkedHashMap<String, String> entryList);

    //查询保税清单回执详情（保税清单查询）（申报后）
    @SelectProvider(type = BondinvenQuerySQLProvider.class, method = "getImpBondInvenRec")
    ImpInventoryHead getImpBondInvenRec(Map<String, String> paramMap);

}
