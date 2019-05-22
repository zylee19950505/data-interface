package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.entity.VerifyBondHead;
import com.xaeport.crossborder.data.provider.ExitLogSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExitLogMapper {

    @SelectProvider(type = ExitLogSQLProvider.class, method = "getLogicDataByExitBondInvt")
    List<VerifyBondHead> getLogicDataByExitBondInvt(Map<String, String> map);

    @SelectProvider(type = ExitLogSQLProvider.class, method = "getLogicDataByExitPassPort")
    List<VerifyBondHead> getLogicDataByExitPassPort(Map<String, String> map);

    @Delete("DELETE FROM T_VERIFY_STATUS WHERE ORDER_NO = #{etps_inner_invt_no}")
    void deleteVerifyStatus(String etps_inner_invt_no);

    //修改入区核注清单表头信息
    @UpdateProvider(type = ExitLogSQLProvider.class, method = "updateEnterBondInvtBscLogic")
    void updateEnterBondInvtBscLogic(@Param("bondInvtBsc") LinkedHashMap<String, String> bondInvtBsc, @Param("users") Users users);

    //修改入区核注清单表体信息
    @UpdateProvider(type = ExitLogSQLProvider.class, method = "updateEnterBondInvtDtLogic")
    void updateEnterBondInvtDtLogic(@Param("bondInvtDt") LinkedHashMap<String, String> bondInvtDt, @Param("users") Users users);


}
