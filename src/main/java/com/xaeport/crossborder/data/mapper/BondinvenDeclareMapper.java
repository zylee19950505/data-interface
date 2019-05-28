package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.BondinvenDeclareSQLProvider;
import com.xaeport.crossborder.data.provider.DetailQuerySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BondinvenDeclareMapper {


    //查询保税清单数据
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "queryBondInvenDeclareList")
    List<ImpInventory> queryBondInvenDeclareList(Map<String, String> paramMap) throws Exception;

    //查询保税清单数据总数
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "queryBondInvenDeclareCount")
    Integer queryBondInvenDeclareCount(Map<String, String> paramMap) throws Exception;

    //提交海关，更新保税清单数据为申报中
    @UpdateProvider(type = BondinvenDeclareSQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap) throws Exception;

    //查询保税清单表头详情
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "queryImpBondInvenHead")
    ImpInventoryHead queryImpBondInvenHead(Map<String, String> paramMap);

    //查询保税清单表体详情
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "queryImpBondInvenBodies")
    List<ImpInventoryBody> queryImpBondInvenBodies(Map<String, String> paramMap);

    //查询清单表头详情
    @SelectProvider(type = DetailQuerySQLProvider.class, method = "queryVerifyDetail")
    Verify queryVerifyDetail(Map<String, String> paramMap);

    //修改保税清单表头信息（保税清单申报）
    @UpdateProvider(type = BondinvenDeclareSQLProvider.class, method = "updateImpBondInvenHead")
    void updateImpBondInvenHead(LinkedHashMap<String, String> entryHead);

    //修改保税清单表头数据根据表体（保税清单申报）
    @UpdateProvider(type = BondinvenDeclareSQLProvider.class, method = "updateImpBondInvenHeadByList")
    void updateImpBondInvenHeadByList(LinkedHashMap<String, String> entryHead);

    //修改保税清单表体信息（保税清单申报）
    @UpdateProvider(type = BondinvenDeclareSQLProvider.class, method = "updateImpBondInvenBodies")
    void updateImpBondInvenBodies(LinkedHashMap<String, String> entryList);

    //查询需要生成报文的保税清单数据表头（申报中状态）
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "findWaitGenerated")
    List<ImpInventoryHead> findWaitGenerated(Map<String, String> paramMap);

    //更新保税清单数据为申报中
    @UpdateProvider(type = BondinvenDeclareSQLProvider.class, method = "updateEntryHeadDetailStatus")
    void updateEntryHeadDetailStatus(@Param("headGuid") String headGuid, @Param("dataStatus") String dataStatus);

    //查询需要生成报文的保税清单表体数据
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "querydetailDeclareListByGuid")
    List<ImpInventoryBody> querydetailDeclareListByGuid(@Param("headGuid") String headGuid);

    //查询企业信息
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "queryCompany")
    BaseTransfer queryCompany(@Param("ent_id") String ent_id);

    //查询运单清单表
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "queryLogInvCombine")
    LogInvCombine queryLogInvCombine(@Param("billNo") String billNo, @Param("orderNo") String orderNo, @Param("logisticsNo") String logisticsNo);

    //设置运单清单表数据值
    @UpdateProvider(type = BondinvenDeclareSQLProvider.class, method = "updateLogInvCombine")
    void updateLogInvCombine(@Param("billNo") String billNo, @Param("orderNo") String orderNo, @Param("logisticsNo") String logisticsNo, @Param("mark") String mark);

    //插入整合表
    @InsertProvider(type = BondinvenDeclareSQLProvider.class, method = "insertLogInvCombine")
    void insertLogInvCombine(@Param("logInvCombine") LogInvCombine logInvCombine);

}
