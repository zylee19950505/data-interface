package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.provider.EEmptyPassportSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EEmptyPassportMapper {

    @SelectProvider(type = EEmptyPassportSQLProvider.class, method = "findWaitGenerated")
    List<PassPortHead> findWaitGenerated(Map<String, String> paramMap);

    @UpdateProvider(type = EEmptyPassportSQLProvider.class, method = "updatePassportStatus")
    void updatePassportStatus(@Param("etpsPreentNo") String etpsPreentNo, @Param("status") String status);

    //查询企业DxpId
    @Select("SELECT DXP_ID FROM T_ENTERPRISE WHERE ID = #{entId}")
    String getDxpId(String entId);

    //查询申报企业IcCard
    @Select("SELECT DCL_ETPS_IC_NO FROM T_DCL_ETPS WHERE ENT_ID = #{entId} AND DCL_ETPS_CUSTOMS_CODE = #{customsCode}")
    String getDclEtpsIcCard(@Param("entId") String entId, @Param("customsCode") String customsCode);


}
