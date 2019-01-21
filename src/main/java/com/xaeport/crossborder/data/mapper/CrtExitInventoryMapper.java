package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.CrtExitInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CrtExitInventoryMapper {

    //查询保税清单数据
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryCrtEInventoryList")
    List<ImpInventory> queryCrtEInventoryList(Map<String, String> paramMap) throws Exception;

    //查询保税清单数据总数
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryCrtEInventoryCount")
    Integer queryCrtEInventoryCount(Map<String, String> paramMap) throws Exception;

    //查询账册表头海关编码
    @Select("SELECT t.MASTER_CUSCD FROM T_BWL_HEAD_TYPE t WHERE t.CRT_ENT_ID = #{ent_id}")
    String queryDcl_plc_cuscd(String ent_id);

    //查询账册表头账册编码
    @Select("SELECT t.BWS_NO FROM T_BWL_HEAD_TYPE t WHERE t.CRT_ENT_ID = #{ent_id}")
    String queryBws_no(String ent_id);

    //查询该企业海关编码
    @Select("SELECT t.PORT FROM T_ENTERPRISE t WHERE t.ID = #{ent_id}")
    String queryCustomsByEndId(String ent_id);

    //根据保税清单编码查询对应guid
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryGuidByInvtNos")
    List<String> queryGuidByInvtNos(String invtNos);

    //根据保税清单编码查询对应保税清单表头数据
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryInvtNos")
    List<ImpInventoryHead> queryInvtNos(String guids);

//    //查询保税清单数据表体数据
//    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryImpInventoryBodyList")
//    List<ImpInventoryBody> queryImpInventoryBodyList(String dataList) throws Exception;

    //根据所选数据修改对应保税清单的数据状态
    @UpdateProvider(type = CrtExitInventorySQLProvider.class, method = "updateInventoryDataByBondInvt")
    void updateInventoryDataByBondInvt(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc);

    //插入核注清单新数据（表头数据）
    @InsertProvider(type = CrtExitInventorySQLProvider.class, method = "saveBondInvtBsc")
    void saveBondInvtBsc(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

    //插入核注清单新数据（表体数据）
    @InsertProvider(type = CrtExitInventorySQLProvider.class, method = "saveNemsInvtCbecBillType")
    void saveNemsInvtCbecBillType(@Param("nemsInvtCbecBillType") LinkedHashMap<String, String> nemsInvtCbecBillType, @Param("userInfo") Users userInfo);


}
