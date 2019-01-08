package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.ExitInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExitInventoryMapper {

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

    //修改保税清单数据状态为“未生成”
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateInventoryByInvtNo")
    void updateInventoryByInvtNo(String invtNo);

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

    //修改核注清单表体信息
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateNemsInvtCbecBillType")
    void updateNemsInvtCbecBillType(@Param("nemsInvtCbecBillType") LinkedHashMap<String, String> nemsInvtCbecBillType, @Param("userInfo") Users userInfo);

    //根据表体修改核注清单表头
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBscByList")
    void updateBondInvtBscByList(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

    //查询状态出区核注清单申报中状态的数据
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "findWaitGenerated")
    List<BondInvtBsc> findWaitGenerated(Map<String, String> paramMap);

    //更新核注清单数据为已申报状态
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBscStatus")
    void updateBondInvtBscStatus(@Param("headEtpsInnerInvtNo") String headEtpsInnerInvtNo, @Param("status") String status);

    //查询核注清单申报中所对应表体信息
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryBondInvtListByHeadNo")
    List<NemsInvtCbecBillType> queryBondInvtListByHeadNo(@Param("head_etps_inner_invt_no") String head_etps_inner_invt_no);

    //查询企业信息
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryCompany")
    BaseTransfer queryCompany(@Param("ent_id") String ent_id);



}
