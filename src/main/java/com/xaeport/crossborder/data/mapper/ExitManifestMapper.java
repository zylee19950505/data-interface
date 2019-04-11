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

    //查询进口出区核放单数据
    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryExitManifestData")
    List<PassPortHead> queryExitManifestData(Map<String, String> paramMap) throws Exception;

    //查询进口出区核放单总数
    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryExitManifestCount")
    Integer queryExitManifestCount(Map<String, String> paramMap) throws Exception;

    //更新修改出区核放单数据为申报中状态（提交海关）
    @UpdateProvider(type = ExitManifestSQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap) throws Exception;

    //获取需要删除的出区核放单表头数据
    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryPassPortHeadList")
    List<PassPortHead> queryPassPortHeadList(Map<String, String> paramMap);

    //获取需要删除的出区核放单表体数据
    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryPassPortAcmpList")
    List<PassPortAcmp> queryPassPortAcmpList(Map<String, String> paramMap);

    //查询进口出区核放单表头详情
    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryPassPortHead")
    PassPortHead queryPassPortHead(Map<String, String> paramMap);

    //查询进口出区核放单表体详情
    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryPassPortAcmp")
    List<PassPortAcmp> queryPassPortAcmp(Map<String, String> paramMap);

    //修改出区核注清单状态为“未生成核放单”状态
    @UpdateProvider(type = ExitManifestSQLProvider.class, method = "updateBondInvtBsc")
    void updateBondInvtBsc(String BondInvtNos);

    //删除出区核放单表头数据
    @Delete("DELETE FROM T_PASS_PORT_ACMP WHERE HEAD_ETPS_PREENT_NO = #{etpsPreentNo}")
    void deletePassPortAcmp(String etpsPreentNo);

    //删除出区核放单表体数据
    @Delete("DELETE FROM T_PASS_PORT_HEAD WHERE ETPS_PREENT_NO = #{etpsPreentNo}")
    void deletePassPortHead(String etpsPreentNo);

    //修改出区核放单表头信息
    @UpdateProvider(type = ExitManifestSQLProvider.class, method = "updatePassPortHead")
    void updatePassPortHead(@Param("passPortHead") LinkedHashMap<String, String> passPortHead, @Param("userInfo") Users userInfo);

    //修改出区核放单表体信息
    @UpdateProvider(type = ExitManifestSQLProvider.class, method = "updatePassPortAcmp")
    void updatePassPortAcmp(@Param("passPortHead") LinkedHashMap<String, String> passPortHead, @Param("passPortAcmpList") LinkedHashMap<String, String> passPortAcmpList, @Param("userInfo") Users userInfo);

    //查找可以生成报文的已申报状态表头数据
    @SelectProvider(type = ExitManifestSQLProvider.class, method = "findWaitGenerated")
    List<PassPortHead> findWaitGenerated(Map<String, String> paramMap);

    //查询数据是否填写完整
    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryDataFull")
    List<String> queryDataFull(Map<String, String> paramMap);

    //更新出区核放单数据为已申报状态
    @UpdateProvider(type = ExitManifestSQLProvider.class, method = "updatePassPortHeadStatus")
    void updatePassPortHeadStatus(@Param("etpsPreentNo") String etpsPreentNo, @Param("status") String status);

    //查询需生成报文的核放单表体数据
    @SelectProvider(type = ExitManifestSQLProvider.class, method = "queryPassPortAcmpByHeadNo")
    List<PassPortAcmp> queryPassPortAcmpByHeadNo(@Param("etpsPreentNo") String etpsPreentNo);

    //查询企业DxpId
    @Select("SELECT DXP_ID FROM T_ENTERPRISE WHERE ID = #{entId}")
    String getDxpId(String entId);

    //查询用户IcCard
    @Select("SELECT IC_CARD FROM T_USERS WHERE ID = #{userId}")
    String getIcCard(String userId);

    //查询申报企业IcCard
    @Select("SELECT DCL_ETPS_IC_NO FROM T_DCL_ETPS WHERE ENT_ID = #{entId} AND DCL_ETPS_CUSTOMS_CODE = #{customsCode}")
    String getDclEtpsIcCard(@Param("entId") String entId, @Param("customsCode") String customsCode);

}
