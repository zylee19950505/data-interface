package com.xaeport.crossborder.data.mapper;


/*
 * 支付单申报
 */

import com.xaeport.crossborder.data.entity.BaseTransfer411;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.provider.PaymentDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaymentDeclareMapper {

    /*
     * 查询支付单申报数据
     */
    @SelectProvider(type = PaymentDeclareSQLProvider.class, method = "queryPaymentDeclareList")
    List<ImpPayment> queryPaymentDeclareList(Map<String, String> paramMap) throws Exception;

    /*
     * 查询支付单申报数据总数
     */
    @SelectProvider(type = PaymentDeclareSQLProvider.class, method = "queryPaymentDeclareCount")
    Integer queryPaymentDeclareCount(Map<String, String> paramMap) throws Exception;

    /*
     * 更新提交海关的数据，变为支付单申报中
     */
    @UpdateProvider(type = PaymentDeclareSQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap) throws Exception;

    /*
     * 生产支付单报文数据查询
     */
    @SelectProvider(type = PaymentDeclareSQLProvider.class, method = "findWaitGenerated")
    List<ImpPayment> findWaitGenerated(Map<String, String> paramMap) throws Exception;

    /*
     * 修改支付单申报状态
     */
    @UpdateProvider(type = PaymentDeclareSQLProvider.class, method = "updateImpPaymentStatus")
    void updateImpPaymentStatus(@Param("guid") String guid, @Param("CBDS31") String CBDS31) throws Exception;

    @SelectProvider(type = PaymentDeclareSQLProvider.class, method = "queryPaymentById")
    ImpPayment queryPaymentById(@Param("paytransactionid") String paytransactionid);

    @SelectProvider(type = PaymentDeclareSQLProvider.class, method = "queryCompany")
    BaseTransfer411 queryCompany(@Param("ent_id") String ent_id);
}
