package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.CrtEnterManifestSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
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

    //@Select("select t.DCL_QTY,t.GROSS_WT,t.NET_WT from T_BOND_INVT_DT t where t.HEAD_ETPS_INNER_INVT_NO = (select ETPS_INNER_INVT_NO from T_BOND_INVT_BSC where BOND_INVT_NO = #{invtNo})")
    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "queryEnterInvtory")
    List<BondInvtDt> queryEnterInvtory(@Param("invtNo") String invtNo);

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "queryEnterManifestOneCar")
    PassPortHead queryEnterManifestOneCar( Map<String, String> paramMap);

    @UpdateProvider(type = CrtEnterManifestSQLProvider.class,method = "updateEnterManifestDetailOneCar")
    void updateEnterManifestDetailOneCar(@Param("passPortHead") PassPortHead passPortHead);

    @InsertProvider(type = CrtEnterManifestSQLProvider.class,method = "createEnterManifestList")
    void createEnterManifestList(@Param("passPortList") PassPortList passPortList);

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "querySelectBondDtList")
    List<BondInvtDt> querySelectBondDtList(Map<String, String> paramMap);

    @UpdateProvider(type = CrtEnterManifestSQLProvider.class,method = "updateEnterInventoryMoreCar")
    void updateEnterInventoryMoreCar(Map<String, String> paramMap);

    @UpdateProvider(type = CrtEnterManifestSQLProvider.class,method = "updatePassPortHead")
    void updatePassPortHead(LinkedHashMap<String, String> entryHead);

    @InsertProvider(type = CrtEnterManifestSQLProvider.class,method = "crtPassPortList")
    void crtPassPortList(LinkedHashMap<String, String> entryList);

    @UpdateProvider(type = CrtEnterManifestSQLProvider.class,method = "updateEnterBondInvtDt")
    void updateEnterBondInvtDt(LinkedHashMap<String, String> entryList);

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "querypassPortListNm")
    List<PassPortList> querypassPortListNm(@Param("bond_invt_no") String bond_invt_no);

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "queryBondBscNm")
    int queryBondBscNm(@Param("bond_invt_no") String bond_invt_no);

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "queryBondDtList")
    List<BondInvtDt> queryBondDtList(@Param("bond_invt_no") String bond_invt_no);

    @UpdateProvider(type = CrtEnterManifestSQLProvider.class,method = "canelEnterManifestDetail")
    void canelEnterManifestDetail(Map<String, String> paramMap);

    @SelectProvider(type = CrtEnterManifestSQLProvider.class,method = "queryDclEtpsMsg")
    BondInvtBsc queryDclEtpsMsg(@Param("invtNo") String invtNo);
}
