package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.provider.EnterInventorySQLProvider;
import org.apache.ibatis.annotations.*;

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
}
