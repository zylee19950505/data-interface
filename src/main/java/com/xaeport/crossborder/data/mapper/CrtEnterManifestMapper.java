package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.entity.PassPortAcmp;
import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.provider.CrtEnterManifestSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrtEnterManifestMapper {

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "queryCrtEnterManifestList")
    List<BondInvtBsc> queryCrtEnterManifestList(Map<String, String> paramMap);

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "queryCrtEnterManifestCount")
    Integer queryCrtEnterManifestCount(Map<String, String> paramMap);

    @Select("select SEQ_PASS_PORT_HEAD.nextVal from dual")
    String querySeqPassPortHeadNextval();

    @InsertProvider(type = CrtEnterManifestSQLProvider.class,method = "createEnterManifest")
    void createEnterManifest(@Param("passPortHead") PassPortHead passPortHead);

    @UpdateProvider(type = CrtEnterManifestSQLProvider.class,method = "updateEnterInventory")
    void updateEnterInventory(Map<String, String> paramMap);

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "queryInventoryList")
    List<BondInvtDt> queryInventoryList(@Param("invtNo") String invtNo);

    @Select("select t.PASSPORT_NO from T_PASS_PORT_HEAD t where t.BOND_INVT_NO = #{invtNo}")
    String queryEnterManifestPassPortNo(@Param("invtNo") String invtNo);

    @InsertProvider(type = CrtEnterManifestSQLProvider.class,method = "createPassPortAcmp")
    void createPassPortAcmp(@Param("passPortAcmp") PassPortAcmp passPortAcmp);

    @Select("select t.DCL_QTY,t.GROSS_WT,t.NET_WT from T_BOND_INVT_DT t where t.HEAD_ETPS_INNER_INVT_NO = (select ETPS_INNER_INVT_NO from T_BOND_INVT_BSC where BOND_INVT_NO = #{invtNo})")
    List<BondInvtDt> queryEnterInvtory(@Param("invtNo") String invtNo);

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "queryManifestOneCar")
    PassPortHead queryManifestOneCar(@Param("bond_invt_no") String bond_invt_no);

    @UpdateProvider(type = CrtEnterManifestSQLProvider.class,method = "updateEnterManifestDetailOneCar")
    void updateEnterManifestDetailOneCar(@Param("passPortHead") PassPortHead passPortHead);
}
