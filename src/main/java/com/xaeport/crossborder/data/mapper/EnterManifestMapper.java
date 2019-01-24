package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.PassPortList;
import com.xaeport.crossborder.data.provider.EnterManifestSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EnterManifestMapper {

    @SelectProvider(type = EnterManifestSQLProvider.class,method = "queryEnterManifest")
    List<PassPortHead> queryEnterManifest(Map<String, String> paramMap);

    @SelectProvider(type = EnterManifestSQLProvider.class,method = "queryEnterManifestCount")
    Integer queryEnterManifestCount(Map<String, String> paramMap);

    @UpdateProvider(type = EnterManifestSQLProvider.class,method = "updateSubmitCustom")
    boolean updateSubmitCustom(Map<String, String> paramMap);

    @SelectProvider(type = EnterManifestSQLProvider.class,method = "queryEnterManifestBindType")
    PassPortHead queryEnterManifestBindType(Map<String, String> paramMap);

    @DeleteProvider(type = EnterManifestSQLProvider.class,method = "deleteEnterPassportAcmp")
    void deleteEnterPassportAcmp(Map<String, String> paramMap);

    @SelectProvider(type = EnterManifestSQLProvider.class,method = "queryEnterPassportAcmp")
    String queryEnterPassportAcmp(Map<String, String> paramMap);

    @UpdateProvider(type = EnterManifestSQLProvider.class,method = "updateEnterBondInvtBsc")
    void updateEnterBondInvtBsc(Map<String, String> paramMap);

    @SelectProvider(type = EnterManifestSQLProvider.class,method = "queryEnterBondInvtDtID")
    String[] queryEnterBondInvtDtID(Map<String, String> paramMap);

    @DeleteProvider(type = EnterManifestSQLProvider.class,method = "updateEnterBondInvtDt")
    void updateEnterBondInvtDt(@Param("etps_invt_no") String etps_invt_no);

    @DeleteProvider(type = EnterManifestSQLProvider.class,method = "deleteEnterPassportHead")
    void deleteEnterPassportHead(Map<String, String> paramMap);

    @SelectProvider(type = EnterManifestSQLProvider.class,method = "queryEnterPassportList")
    List<PassPortList> queryEnterPassportList(Map<String, String> paramMap);

    @UpdateProvider(type = EnterManifestSQLProvider.class,method = "updateEnterBondInvtDtMoreCar")
    void updateEnterBondInvtDtMoreCar(@Param("passPortList") PassPortList passPortList);

    @SelectProvider(type = EnterManifestSQLProvider.class,method = "queryEnterBondInvtBsc")
    String queryEnterBondInvtBsc(Map<String, String> paramMap);

    @UpdateProvider(type = EnterManifestSQLProvider.class,method = "updateEnterBondInvtBscMoreCar")
    void updateEnterBondInvtBscMoreCar(Map<String, String> paramMap);

    @DeleteProvider(type = EnterManifestSQLProvider.class,method = "deleteEnterPassportList")
    void deleteEnterPassportList(Map<String, String> paramMap);
}
