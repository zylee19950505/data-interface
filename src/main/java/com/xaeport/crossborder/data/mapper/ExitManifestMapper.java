package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.PassPortAcmp;
import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.ExitManifestSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExitManifestMapper {

    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryExitManifestData")
    List<PassPortHead> queryExitManifestData(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryExitManifestCount")
    Integer queryExitManifestCount(Map<String, String> paramMap) throws Exception;

    @UpdateProvider(type = ExitManifestSQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryPassPortHeadList")
    List<PassPortHead> queryPassPortHeadList(Map<String, String> paramMap);

    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryPassPortAcmpList")
    List<PassPortAcmp> queryPassPortAcmpList(Map<String, String> paramMap);

    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryPassPortHead")
    PassPortHead queryPassPortHead(Map<String, String> paramMap);

    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryPassPortAcmp")
    List<PassPortAcmp> queryPassPortAcmp(Map<String, String> paramMap);

    @UpdateProvider(type = ExitManifestSQLProvider.class, method = "updateBondInvtBsc")
    void updateBondInvtBsc(String bondInvtNo);

    @Delete("DELETE FROM T_PASS_PORT_ACMP WHERE HEAD_ETPS_PREENT_NO = #{etpsPreentNo}")
    void deletePassPortAcmp(String etpsPreentNo);

    @Delete("DELETE FROM T_PASS_PORT_HEAD WHERE ETPS_PREENT_NO = #{etpsPreentNo}")
    void deletePassPortHead(String etpsPreentNo);

    //修改出区核放单表头信息
    @UpdateProvider(type = ExitManifestSQLProvider.class, method = "updatePassPortHead")
    void updatePassPortHead(@Param("passPortHead") LinkedHashMap<String, String> passPortHead, @Param("userInfo") Users userInfo);

    //修改出区核放单表体
    @UpdateProvider(type = ExitManifestSQLProvider.class, method = "updatePassPortAcmp")
    void updatePassPortAcmp(@Param("passPortHead") LinkedHashMap<String, String> passPortHead, @Param("userInfo") Users userInfo);


    @SelectProvider(type = ExitManifestSQLProvider.class, method = "findWaitGenerated")
    List<PassPortHead> findWaitGenerated(Map<String, String> paramMap);

    @UpdateProvider(type = ExitManifestSQLProvider.class, method = "updatePassPortHeadStatus")
    void updatePassPortHeadStatus(@Param("etpsPreentNo") String etpsPreentNo, @Param("status") String status);

    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryPassPortAcmpByHeadNo")
    List<PassPortAcmp> queryPassPortAcmpByHeadNo(@Param("etpsPreentNo") String etpsPreentNo);

}
