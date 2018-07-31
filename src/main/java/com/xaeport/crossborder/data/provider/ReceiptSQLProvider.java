package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.ImpRecPayment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class ReceiptSQLProvider extends BaseSQLProvider {

    public String createImpRecPayment(
            @Param("impRecPayment") ImpRecPayment impRecPayment
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_REC_PAYMENT");
                if (!StringUtils.isEmpty(impRecPayment.getId())) {
                    VALUES("ID", "impRecPayment.id");
                }
                if (!StringUtils.isEmpty(impRecPayment.getGuid())) {
                    VALUES("GUID", "impRecPayment.guid");
                }
                if (!StringUtils.isEmpty(impRecPayment.getPay_code())) {
                    VALUES("PAY_CODE", "impRecPayment.pay_code");
                }
                if (!StringUtils.isEmpty(impRecPayment.getPay_transaction_id())) {
                    VALUES("PAY_TRANSACTION_ID", "impRecPayment.pay_transaction_id");
                }
                if (!StringUtils.isEmpty(impRecPayment.getOrder_no())) {
                    VALUES("ORDER_NO", "impRecPayment.order_no");
                }
                if (!StringUtils.isEmpty(impRecPayment.getReturn_status())) {
                    VALUES("RETURN_STATUS", "impRecPayment.return_status");
                }
                if (!StringUtils.isEmpty(impRecPayment.getReturn_time())) {
                    VALUES("RETURN_TIME", "impRecPayment.return_time");
                }
                if (!StringUtils.isEmpty(impRecPayment.getReturn_info())) {
                    VALUES("RETURN_INFO", "impRecPayment.return_info");
                }
                if (!StringUtils.isEmpty(impRecPayment.getCrt_tm())) {
                    VALUES("CRT_TM", "impRecPayment.crt_tm");
                }
                if (!StringUtils.isEmpty(impRecPayment.getUpd_tm())) {
                    VALUES("UPD_TM", "impRecPayment.upd_tm");
                }
            }
        }.toString();
    }

    public String updateImpPayment(
            @Param("impPayment") ImpPayment impPayment
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_PAYMENT t");
                WHERE("t.DATA_STATUS = 'CBDS31'");
                if(StringUtils.isEmpty(impPayment.getPay_transaction_id())){
                    WHERE("t.PAY_TRANSACTION_ID = #{impPayment.pay_transaction_id}");
                }
                if(StringUtils.isEmpty(impPayment.getPay_code())){
                    WHERE("t.PAY_CODE = #{impPayment.pay_code}");
                }
                if(StringUtils.isEmpty(impPayment.getReturn_status())){
                    SET("t.RETURN_STATUS = #{impPayment.return_status}");
                }
                if(StringUtils.isEmpty(impPayment.getReturn_info())){
                    SET("t.RETURN_INFO = #{impPayment.return_info}");
                }
                if(StringUtils.isEmpty(impPayment.getReturn_time())){
                    SET("t.RETURN_TIME = #{impPayment.return_time}");
                }
            }
        }.toString();
    }


}
