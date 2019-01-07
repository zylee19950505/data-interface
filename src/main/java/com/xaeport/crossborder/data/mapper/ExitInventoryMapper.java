package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.ExitInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExitInventoryMapper {

    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryEInventoryList")
    List<BondInvtBsc> queryEInventoryList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryEInventoryCount")
    Integer queryEInventoryCount(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryBondInvtBsc")
    BondInvtBsc queryBondInvtBsc(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryNemsInvtCbecBillTypeList")
    List<NemsInvtCbecBillType> queryNemsInvtCbecBillTypeList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryDeleteHeadByCode")
    List<BondInvtBsc> queryDeleteHeadByCode(Map<String, String> paramMap);

    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryDeleteListByCode")
    List<NemsInvtCbecBillType> queryDeleteListByCode(Map<String, String> paramMap);

    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateInventoryByInvtNo")
    void updateInventoryByInvtNo(String invtNo);

    @Delete("DELETE FROM T_NEMS_INVT_CBEC_BILL_TYPE WHERE HEAD_ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}")
    void deleteNemsInvtCbecBillTypeByNo(String etpsInnerInvtNo);

    @Delete("DELETE FROM T_BOND_INVT_BSC WHERE ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}")
    void deleteBondInvtBscByNo(String etpsInnerInvtNo);

    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap) throws Exception;

    //修改清单表头信息
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBsc")
    void updateBondInvtBsc(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

    //修改清单表体信息（清单查询）
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateNemsInvtCbecBillType")
    void updateNemsInvtCbecBillType(@Param("nemsInvtCbecBillType") LinkedHashMap<String, String> nemsInvtCbecBillType, @Param("userInfo") Users userInfo);

    //修改清单表体数据（清单查询）
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBscByList")
    void updateBondInvtBscByList(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);



    @SelectProvider(type = ExitInventorySQLProvider.class, method = "findWaitGenerated")
    List<BondInvtBsc> findWaitGenerated(Map<String, String> paramMap);

    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBscStatus")
    void updateBondInvtBscStatus(@Param("headEtpsInnerInvtNo") String headEtpsInnerInvtNo, @Param("status") String status);

    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryBondInvtListByHeadNo")
    List<NemsInvtCbecBillType> queryBondInvtListByHeadNo(@Param("head_etps_inner_invt_no") String head_etps_inner_invt_no);

    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryCompany")
    BaseTransfer queryCompany(@Param("ent_id") String ent_id);



}
