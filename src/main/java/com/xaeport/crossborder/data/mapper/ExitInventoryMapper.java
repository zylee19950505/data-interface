package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.ExitInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExitInventoryMapper {

    @Delete("DELETE FROM T_VERIFY_STATUS WHERE ORDER_NO = #{etps_inner_invt_no}")
    int deleteVerifyStatus(String etps_inner_invt_no);

    //查询清单表头详情
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryLogicVerify")
    Verify queryLogicVerify(Map<String, String> paramMap);

    //查询出区核注清单数据
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryEInventoryList")
    List<BondInvtBsc> queryEInventoryList(Map<String, String> paramMap) throws Exception;

    //查询出区核注清单数据总数
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryEInventoryCount")
    Integer queryEInventoryCount(Map<String, String> paramMap) throws Exception;

    //查询核注清单表头
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryBondInvtBsc")
    BondInvtBsc queryBondInvtBsc(Map<String, String> paramMap) throws Exception;

    //查询核注清单表体数据
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryNemsInvtCbecBillTypeList")
    List<NemsInvtCbecBillType> queryNemsInvtCbecBillTypeList(Map<String, String> paramMap) throws Exception;

    //查询需要删除的核注清单数据
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryDeleteHeadByCode")
    List<BondInvtBsc> queryDeleteHeadByCode(Map<String, String> paramMap);

    //查询需要删除的核注清单表体数据
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryDeleteListByCode")
    List<NemsInvtCbecBillType> queryDeleteListByCode(Map<String, String> paramMap);

    //查询需要的InvtListType数据
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryImpInventoryBodyList")
    List<ImpInventoryBody> queryImpInventoryBodyList(String invtNo);

    //修改保税清单数据状态为“未生成”
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateInventoryByInvtNo")
    void updateInventoryByInvtNo(String invtNo);

    //删除核注清单表体数据
    @Delete("DELETE FROM T_BOND_INVT_DT WHERE HEAD_ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}")
    void deleteBondtInvtDtByNo(String etpsInnerInvtNo);

    //删除核注清单表体数据
    @Delete("DELETE FROM T_NEMS_INVT_CBEC_BILL_TYPE WHERE HEAD_ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}")
    void deleteNemsInvtCbecBillTypeByNo(String etpsInnerInvtNo);

    //删除核注清单表头数据
    @Delete("DELETE FROM T_BOND_INVT_BSC WHERE ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}")
    void deleteBondInvtBscByNo(String etpsInnerInvtNo);

    //更新核注清单数据为申报中状态
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap) throws Exception;

    //修改核注清单表头信息
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBsc")
    void updateBondInvtBsc(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

    //修改核注清单表头信息
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBscLog")
    void updateBondInvtBscLog(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

    //修改核注清单表体信息
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateNemsInvtCbecBillType")
    void updateNemsInvtCbecBillType(@Param("nemsInvtCbecBillType") LinkedHashMap<String, String> nemsInvtCbecBillType, @Param("userInfo") Users userInfo);

    //根据表体修改核注清单表头
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBscByList")
    void updateBondInvtBscByList(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

    //根据表体修改核注清单表头逻辑校验
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBscByListLog")
    void updateBondInvtBscByListLog(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

    //查询状态出区核注清单申报中状态的数据
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "findWaitGenerated")
    List<BondInvtBsc> findWaitGenerated(Map<String, String> paramMap);

    //更新核注清单数据为正在发往海关状态
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBscStatus")
    void updateBondInvtBscStatus(@Param("headEtpsInnerInvtNo") String headEtpsInnerInvtNo, @Param("status") String status);

    //查询核注清单申报中所对应表体信息
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryBondInvtListByHeadNo")
    List<NemsInvtCbecBillType> queryBondInvtListByHeadNo(@Param("head_etps_inner_invt_no") String head_etps_inner_invt_no);

    //查询核注清单申报中所对应表体信息
//    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryExitInvtListType")
//    List<BondInvtDt> queryExitInvtListType(@Param("headEtpsInnerInvtNo") String headEtpsInnerInvtNo);
    @Select("SELECT * FROM T_BOND_INVT_DT t WHERE t.HEAD_ETPS_INNER_INVT_NO = #{headEtpsInnerInvtNo}")
    List<BondInvtDt> queryExitInvtListType(@Param("headEtpsInnerInvtNo") String headEtpsInnerInvtNo);

    //查询企业信息
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryCompany")
    BaseTransfer queryCompany(@Param("ent_id") String ent_id);

    //查询企业DxpId
    @Select("SELECT DXP_ID FROM T_ENTERPRISE WHERE ID = #{entId}")
    String getDxpId(String entId);

    //查询用户IcCard
    @Select("SELECT IC_CARD FROM T_USERS WHERE ID = #{userId}")
    String getIcCard(String userId);

    //查询申报企业IcCard
    @Select("SELECT DCL_ETPS_IC_NO FROM T_DCL_ETPS WHERE ENT_ID = #{entId} AND DCL_ETPS_CUSTOMS_CODE = #{customsCode}")
    String getDclEtpsIcCard(@Param("entId") String entId, @Param("customsCode") String customsCode);

    @Select("SELECT INVT_PREENT_NO,BOND_INVT_NO,ETPS_INNER_INVT_NO,RETURN_STATUS,RETURN_TIME,RETURN_INFO FROM T_BOND_INVT_BSC t WHERE t.ID = #{id} and t.ETPS_INNER_INVT_NO = #{etps_inner_invt_no}")
    BondInvtBsc queryBondInvtRecInfo(@Param("id") String id, @Param("etps_inner_invt_no") String etps_inner_invt_no);

}
