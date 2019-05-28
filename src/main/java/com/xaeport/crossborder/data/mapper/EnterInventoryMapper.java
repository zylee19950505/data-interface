package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.EnterInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface EnterInventoryMapper {
    @SelectProvider(type = EnterInventorySQLProvider.class,method = "queryEnterInventory")
    List<BondInvtBsc> queryEnterInventory(Map<String, String> paramMap);

    @SelectProvider(type = EnterInventorySQLProvider.class,method = "queryEnterInventoryCount")
    Integer queryEnterInventoryCount(Map<String, String> paramMap);

    @SelectProvider(type = EnterInventorySQLProvider.class,method = "queryDeleteDataByCode")
    List<BondInvtBsc> queryDeleteDataByCode(Map<String, String> paramMap);

    @Delete("DELETE FROM T_BOND_INVT_BSC WHERE ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}")
    void deleteBondInvtBscByNo(@Param("etpsInnerInvtNo") String etpsInnerInvtNo);

    @Delete("DELETE FROM T_BOND_INVT_DT WHERE HEAD_ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}")
    void deleteBondInvtDtByNo(@Param("etpsInnerInvtNo") String etpsInnerInvtNo);

    @UpdateProvider(type = EnterInventorySQLProvider.class,method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap);

    //查询入区核注清单正在申报中的数据信息
    @SelectProvider(type = EnterInventorySQLProvider.class,method = "findWaitGenerated")
    List<BondInvtBsc> findWaitGenerated(Map<String, String> paramMap);

    //修改入区核注清单的数据状态
    @UpdateProvider(type = EnterInventorySQLProvider.class,method = "updateBondInvtBscStatus")
    void updateBondInvtBscStatus(@Param("headEtpsInnerInvtNo")String headEtpsInnerInvtNo,@Param("rqhzqdysb") String rqhzqdysb);

    //查找核注清单表体数据
    @SelectProvider(type = EnterInventorySQLProvider.class,method = "queryBondInvtListByHeadNo")
    List<BondInvtDt> queryBondInvtListByHeadNo(@Param("headEtpsInnerInvtNo") String headEtpsInnerInvtNo);

    //查询企业DxpId
    @Select("SELECT DXP_ID FROM T_ENTERPRISE WHERE ID = #{entId}")
    String getDxpId(String entId);

    @Select("select CUSTOMS_CODE from T_ENTERPRISE where id = #{crt_ent_id}")
    String queryEnterCustoms(@Param("crt_ent_id") String crt_ent_id);

    //查询申报企业IcCard
    @Select("SELECT DCL_ETPS_IC_NO FROM T_DCL_ETPS WHERE ENT_ID = #{crt_ent_id} AND DCL_ETPS_CUSTOMS_CODE = #{dcl_etpsno}")
    String getDclEtpsIcCard(@Param("crt_ent_id") String crt_ent_id, @Param("dcl_etpsno") String dcl_etpsno);

    //修改入区核注清单表头信息
    @UpdateProvider(type = EnterInventorySQLProvider.class, method = "updateEnterInvHead")
    void updateEnterInvHead(@Param("bondInvtBsc") LinkedHashMap<String, String> bondInvtBsc, @Param("users") Users users);

    //修改入区核注清单表体信息
    @UpdateProvider(type = EnterInventorySQLProvider.class, method = "updateEnterInvBody")
    void updateEnterInvBody(@Param("bondInvtDt") LinkedHashMap<String, String> bondInvtDt, @Param("users") Users users);

    //修改入区核注清单表头状态
    @UpdateProvider(type = EnterInventorySQLProvider.class, method = "updateEnterInvHeadByBody")
    void updateEnterInvHeadByBody(@Param("bondInvtBsc") LinkedHashMap<String, String> bondInvtBsc, @Param("users") Users users);


}
