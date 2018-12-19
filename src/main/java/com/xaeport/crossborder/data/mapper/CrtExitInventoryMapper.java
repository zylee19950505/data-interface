package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.CrtExitInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CrtExitInventoryMapper {

    //查询清单数据
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryCrtEInventoryList")
    List<ImpInventory> queryCrtEInventoryList(Map<String, String> paramMap) throws Exception;

    //查询清单数据总数
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryCrtEInventoryCount")
    Integer queryCrtEInventoryCount(Map<String, String> paramMap) throws Exception;

    @Select("SELECT t.MASTER_CUSCD FROM T_BWL_HEAD_TYPE t WHERE t.CRT_ENT_ID = #{ent_id}")
    String queryDcl_plc_cuscd(String ent_id);

    @Select("SELECT t.BWS_NO FROM T_BWL_HEAD_TYPE t WHERE t.CRT_ENT_ID = #{ent_id}")
    String queryBws_no(String ent_id);

    @Select("SELECT t.PORT FROM T_ENTERPRISE t WHERE t.ID = #{ent_id}")
    String queryCustomsByEndId(String ent_id);

    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryGuids")
    List<String> queryGuids(String invtNos);

    //查询清单数据总数
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryImpInventoryBodyList")
    List<ImpInventoryBody> queryImpInventoryBodyList(String dataList) throws Exception;

    @InsertProvider(type = CrtExitInventorySQLProvider.class, method = "saveBondInvtBsc")
    void saveBondInvtBsc(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

    @InsertProvider(type = CrtExitInventorySQLProvider.class, method = "saveBondInvtDt")
    void saveBondInvtDt(@Param("BondInvtDt") LinkedHashMap<String, String> BondInvtDt, @Param("userInfo") Users userInfo);


}
