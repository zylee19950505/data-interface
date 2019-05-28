package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.StatusRecord;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.provider.VerificationSQLProvider;
import com.xaeport.crossborder.verification.entity.ImpBDBodyVer;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
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

    //更新直购数据模块

    //更新订单表数据状态
    @UpdateProvider(type = VerificationSQLProvider.class, method = "updateOrderStatus")
    int updateOrderStatus(@Param("guid") String guid, @Param("status") String status);

    //更新支付单表数据状态
    @UpdateProvider(type = VerificationSQLProvider.class, method = "updatePaymentStatus")
    int updatePaymentStatus(@Param("guid") String guid, @Param("status") String status);

    //更新运单表数据状态
    @UpdateProvider(type = VerificationSQLProvider.class, method = "updateLogisticsStatus")
    int updateLogisticsStatus(@Param("guid") String guid, @Param("status") String status);

    //更新清单表数据状态
    @UpdateProvider(type = VerificationSQLProvider.class, method = "updateInventoryStatus")
    int updateInventoryStatus(@Param("guid") String guid, @Param("status") String status);

    //更新保税数据模块

    //更新保税订单表数据状态
    @UpdateProvider(type = VerificationSQLProvider.class, method = "updateBondOrderStatus")
    int updateBondOrderStatus(@Param("guid") String guid, @Param("status") String status);

    //更新保税订单表数据状态
    @UpdateProvider(type = VerificationSQLProvider.class, method = "updateBondInvenStatus")
    int updateBondInvenStatus(@Param("guid") String guid, @Param("status") String status);

    //更新核注清单表数据状态
    @UpdateProvider(type = VerificationSQLProvider.class, method = "updateBondInvtStatus")
    int updateBondInvtStatus(@Param("id") String id, @Param("status") String status);

    //更新核放单表数据状态
    @UpdateProvider(type = VerificationSQLProvider.class, method = "updatePassPortStatus")
    int updatePassPortStatus(@Param("id") String id, @Param("status") String status);

    //新增跨境状态历史记录表
    @InsertProvider(type = VerificationSQLProvider.class, method = "insertStatusRecord")
    void insertStatusRecord(@Param("statusRecord") StatusRecord statusRecord);

    //直购数据模块

    //获取未逻辑校验订单表头数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByPayment")
    List<ImpCBHeadVer> unverifiedByPayment();

    //获取未逻辑校验订单表头数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByLogistics")
    List<ImpCBHeadVer> unverifiedByLogistics();

    //获取未逻辑校验订单表头数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByOrderHead")
    List<ImpCBHeadVer> unverifiedByOrderHead();

    //获取未逻辑校验订单表体数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByOrderBody")
    List<ImpCBBodyVer> unverifiedByOrderBody(Map<String, String> paramMap);

    //获取未逻辑校验清单表头数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByInventoryHead")
    List<ImpCBHeadVer> unverifiedByInventoryHead();

    //获取未逻辑校验清单表体数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByInventoryBody")
    List<ImpCBBodyVer> unverifiedByInventoryBody(Map<String, String> paramMap);

    //保税数据模块

    //获取未逻辑校验保税订单表头数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByBondOrderHead")
    List<ImpBDHeadVer> unverifiedByBondOrderHead(@Param("status") String status);

    //获取未逻辑校验保税订单表体数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByBondOrderBody")
    List<ImpBDBodyVer> unverifiedByBondOrderBody(Map<String, String> paramMap);

    //获取未逻辑校验保税清单表头数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByBondInvenHead")
    List<ImpBDHeadVer> unverifiedByBondInvenHead(@Param("status") String status);

    //获取未逻辑校验保税清单表体数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByBondInvenBody")
    List<ImpBDBodyVer> unverifiedByBondInvenBody(Map<String, String> paramMap);

    //获取未逻辑校验核放单表头数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByPassPort")
    List<ImpBDHeadVer> unverifiedByPassPort(@Param("status") String status, @Param("flag") String flag);

    //获取未逻辑校验核注清单表头数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByBondInvtHead")
    List<ImpBDHeadVer> unverifiedByBondInvtHead(@Param("status") String status, @Param("flag") String flag);

    //获取未逻辑校验核注清单表体数据
    @SelectProvider(type = VerificationSQLProvider.class, method = "unverifiedByBondInvtBody")
    List<ImpBDBodyVer> unverifiedByBondInvtBody(Map<String, String> paramMap);

}