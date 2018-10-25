package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.StatusRecord;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.provider.VerificationSQLProvider;
import com.xaeport.crossborder.verification.entity.ImpCBBodyVer;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 数据校验
 */
@Mapper
public interface VerificationMapper {

    // 新增跨境校验状态表
    @InsertProvider(type = VerificationSQLProvider.class, method = "insertVerifyStatus")
    int insertVerifyStatus(Verify verify) throws Exception;

    // 新增跨境校验记录表
    @InsertProvider(type = VerificationSQLProvider.class, method = "insertVerifyRecordByVerify")
    void insertVerifyRecordByVerify(Verify verify);


//    //更新清单表数据状态
//    @Update("update entryhead set op_status=#{status} where entryhead_id=#{entryHeadId}")
//    int updateEntryHeadStatus(@Param("entryHeadId") String entryHeadId, @Param("status") String status);

    //更新清单表数据状态
    @UpdateProvider(type = VerificationSQLProvider.class, method = "updateEntryHeadStatus")
    int updateEntryHeadStatus(@Param("guid") String guid, @Param("status") String status);

    //新增跨境状态历史记录表
    @InsertProvider(type = VerificationSQLProvider.class, method = "insertStatusRecord")
    void insertStatusRecord(@Param("statusRecord") StatusRecord statusRecord);


    //获取未逻辑校验清单表头数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByInventoryHead")
    List<ImpCBHeadVer> unverifiedByInventoryHead();

    //获取未逻辑校验清单表体数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByInventoryBody")
    List<ImpCBBodyVer> unverifiedByInventoryBody(Map<String, String> paramMap);


}