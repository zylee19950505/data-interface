package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.entity.Users;
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

    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryBondInvtDtList")
    List<BondInvtDt> queryBondInvtDtList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryDeleteDataByCode")
    List<BondInvtBsc> queryDeleteDataByCode(Map<String, String> paramMap);

    @Delete("DELETE FROM T_BOND_INVT_DT WHERE HEAD_ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}")
    void deleteBondInvtDtByNo(String etpsInnerInvtNo);

    @Delete("DELETE FROM T_BOND_INVT_BSC WHERE ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}")
    void deleteBondInvtBscByNo(String etpsInnerInvtNo);

    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap) throws Exception;

    //修改清单表头信息
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBsc")
    void updateBondInvtBsc(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

    //修改清单表体信息（清单查询）
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtDt")
    void updateBondInvtDt(@Param("BondInvtDt") LinkedHashMap<String, String> BondInvtDt, @Param("userInfo") Users userInfo);

    //修改清单表体数据（清单查询）
    @UpdateProvider(type = ExitInventorySQLProvider.class, method = "updateBondInvtBscByInvtDt")
    void updateBondInvtBscByInvtDt(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

}
