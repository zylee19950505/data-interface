package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.VerifyBondHead;
import com.xaeport.crossborder.data.provider.ExitLogSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExitLogMapper {

    @SelectProvider(type = ExitLogSQLProvider.class, method = "getLogicDataByExitBondInvt")
    List<VerifyBondHead> getLogicDataByExitBondInvt(Map<String, String> map);

}
