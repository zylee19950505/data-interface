package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.provider.PaymentQuerySQLProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface PaymentQueryMapper {

    @Delete("DELETE FROM T_VERIFY_STATUS WHERE CB_HEAD_ID = #{guid}")
    int deleteVerifyStatus(String guid);

    //查询支付单查询数据
    @SelectProvider(type = PaymentQuerySQLProvider.class, method = "queryPaymentQueryList")
    List<ImpPayment> queryPaymentQueryList(Map<String, String> paramMap) throws Exception;

    //查询支付单查询数据总数
    @SelectProvider(type = PaymentQuerySQLProvider.class, method = "queryPaymentQueryCount")
    Integer queryPaymentQueryCount(Map<String, String> paramMap) throws Exception;

    //查询支付单详情信息
    @SelectProvider(type = PaymentQuerySQLProvider.class, method = "seePaymentDetail")
    ImpPayment seePaymentDetail(Map<String, String> paramMap);

    //查询清单表头详情
    @SelectProvider(type = PaymentQuerySQLProvider.class, method = "queryVerifyDetail")
    Verify queryVerifyDetail(Map<String, String> paramMap);

    //查询支付单回执详情
    @SelectProvider(type = PaymentQuerySQLProvider.class, method = "getImpPaymentRec")
    ImpPayment getImpPaymentRec(Map<String, String> paramMap);

    //修改支付单详情信息（支付单查询）
    @UpdateProvider(type = PaymentQuerySQLProvider.class, method = "updateImpPayment")
    void updateImpPayment(LinkedHashMap<String, String> entryHead);

    //修改支付单详情信息（逻辑校验）
    @UpdateProvider(type = PaymentQuerySQLProvider.class, method = "updateImpPaymentByLogic")
    void updateImpPaymentByLogic(LinkedHashMap<String, String> entryHead);

}
