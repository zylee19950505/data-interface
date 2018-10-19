package com.xaeport.crossborder.data.provider;


import com.xaeport.crossborder.data.entity.ImpPayment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/*
* 支付单sqlProvider
* zwf
* 2018/07/11
* */
public class PaymentImportSQLProvider {

    /*
     * 导入插入impPayment数据
     */
    public String insertImpPaymentHead(@Param("impPayment") ImpPayment impPayment) throws Exception {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_PAYMENT");
                if (!StringUtils.isEmpty(impPayment.getGuid())) {
                    VALUES("GUID", "#{impPayment.guid}");
                }
                if (!StringUtils.isEmpty(impPayment.getApp_type())) {
                    VALUES("APP_TYPE", "#{impPayment.app_type}");
                }
                if (!StringUtils.isEmpty(impPayment.getApp_time())) {
                    VALUES("APP_TIME", "#{impPayment.app_time}");
                }
                if (!StringUtils.isEmpty(impPayment.getApp_status())) {
                    VALUES("APP_STATUS", "#{impPayment.app_status}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_code())) {
                    VALUES("PAY_CODE", "#{impPayment.pay_code}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_name())) {
                    VALUES("PAY_NAME", "#{impPayment.pay_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_transaction_id())) {
                    VALUES("PAY_TRANSACTION_ID", "#{impPayment.pay_transaction_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getOrder_no())) {
                    VALUES("ORDER_NO", "#{impPayment.order_no}");
                }
                if (!StringUtils.isEmpty(impPayment.getEbp_code())) {
                    VALUES("EBP_CODE", "#{impPayment.ebp_code}");
                }
                if (!StringUtils.isEmpty(impPayment.getEbp_name())) {
                    VALUES("EBP_NAME", "#{impPayment.ebp_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getPayer_id_type())) {
                    VALUES("PAYER_ID_TYPE", "#{impPayment.payer_id_type}");
                }
                if (!StringUtils.isEmpty(impPayment.getPayer_id_number())) {
                    VALUES("PAYER_ID_NUMBER", "#{impPayment.payer_id_number}");
                }
                if (!StringUtils.isEmpty(impPayment.getPayer_name())) {
                    VALUES("PAYER_NAME", "#{impPayment.payer_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getTelephone())) {
                    VALUES("TELEPHONE", "#{impPayment.telephone}");
                }
                if (!StringUtils.isEmpty(impPayment.getAmount_paid())) {
                    VALUES("AMOUNT_PAID", "#{impPayment.amount_paid}");
                }
                if (!StringUtils.isEmpty(impPayment.getCurrency())) {
                    VALUES("CURRENCY", "#{impPayment.currency}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_time())) {
                    VALUES("PAY_TIME", "#{impPayment.pay_time}");
                }
                if (!StringUtils.isEmpty(impPayment.getNote())) {
                    VALUES("NOTE", "#{impPayment.note}");
                }
                if (!StringUtils.isEmpty(impPayment.getData_status())) {
                    VALUES("DATA_STATUS", "#{impPayment.data_status}");
                }
                if (!StringUtils.isEmpty(impPayment.getCrt_id())) {
                    VALUES("CRT_ID", "#{impPayment.crt_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impPayment.crt_tm}");
                }
                if (!StringUtils.isEmpty(impPayment.getUpd_id())) {
                    VALUES("UPD_ID", "#{impPayment.upd_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getUpd_tm())) {
                    VALUES("UPD_TM", "#{impPayment.upd_tm}");
                }
                if(!StringUtils.isEmpty(impPayment.getEnt_id())){
                    VALUES("ent_id","#{impPayment.ent_id}");
                }
                if(!StringUtils.isEmpty(impPayment.getEnt_name())){
                    VALUES("ent_name","#{impPayment.ent_name}");
                }
                if(!StringUtils.isEmpty(impPayment.getEnt_customs_code())){
                    VALUES("ent_customs_code","#{impPayment.ent_customs_code}");
                }
                if(!StringUtils.isEmpty(impPayment.getBusiness_type())){
                    VALUES("business_type","#{impPayment.business_type}");
                }
            }
        }.toString();
    }

    /*
     * 查询有无重复订单号
     */
    public String isRepeatOrderNo(@Param("impPayment") ImpPayment impPayment) throws Exception {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_IMP_PAYMENT t");
                WHERE("t.ORDER_NO = #{impPayment.order_no}");
            }
        }.toString();
    }
/*
* 查询支付单流水号是否唯一isRepeatPaytransId
* */
    public String isRepeatPaytransId(@Param("impPayment") ImpPayment impPayment) throws Exception {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_IMP_PAYMENT t");
                WHERE("t.PAY_TRANSACTION_ID = #{impPayment.pay_transaction_id}");
            }
        }.toString();
    }

}
