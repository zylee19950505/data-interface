package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.CrtExitManifestSQLProvider;
import com.xaeport.crossborder.data.provider.ExitInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CrtExitManifestMapper {

    @SelectProvider(type = CrtExitManifestSQLProvider.class, method = "queryEInventoryList")
    List<BondInvtBsc> queryEInventoryList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = CrtExitManifestSQLProvider.class, method = "queryEInventoryCount")
    Integer queryEInventoryCount(Map<String, String> paramMap) throws Exception;

    @Select("SELECT * FROM T_BOND_INVT_BSC t WHERE t.BOND_INVT_NO = #{bond_invt_no}")
    BondInvtBsc queryBondInvtBsc(String bond_invt_no) throws Exception;


    @UpdateProvider(type = CrtExitManifestSQLProvider.class, method = "updateBondInvtStatus")
    void updateBondInvtStatus(@Param("passPortHead") LinkedHashMap<String, String> passPortHead);

    @InsertProvider(type = CrtExitManifestSQLProvider.class, method = "savePassPortHead")
    void savePassPortHead(@Param("passPortHead") LinkedHashMap<String, String> passPortHead, @Param("userInfo") Users userInfo);

    @InsertProvider(type = CrtExitManifestSQLProvider.class, method = "savePassPortAcmpList")
    void savePassPortAcmpList(@Param("passPortHead") LinkedHashMap<String, String> passPortHead, @Param("passPortAcmp") LinkedHashMap<String, String> passPortAcmp, @Param("userInfo") Users userInfo);

}
