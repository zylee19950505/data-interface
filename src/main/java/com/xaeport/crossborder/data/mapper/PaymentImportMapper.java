package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.provider.PaymentImportSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

/*
* zwf
* 2018/07/11
* 支付单mapper
* */
@Mapper
public interface PaymentImportMapper {

    /*
     * 导入插入ImpPayment数据
     */
    @InsertProvider(type= PaymentImportSQLProvider.class,method = "insertImpPaymentHead")
    boolean insertImpPaymentHead(@Param("impPayment") ImpPayment impPayment) throws Exception;

    /*查询有无重复支付单号信息*/
    @SelectProvider(type = PaymentImportSQLProvider.class,method = "isRepeatOrderNo")
    int isRepeatOrderNo(@Param("impPayment") ImpPayment impPayment) throws Exception;

    @SelectProvider(type = PaymentImportSQLProvider.class,method = "isRepeatPaytransId")
    int isRepeatPaytransId(@Param("impPayment") ImpPayment impPayment) throws Exception;
}
