package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class PaymentQuerySQLProvider {

	public String seePaymentDetail(Map<String,String> paramMap){
		final String paytransactionid = paramMap.get("paytransactionid");
		final String orderNo = paramMap.get("orderNo");

		return new SQL(){
			{
				SELECT("t.GUID as guid");
				SELECT("t.APP_TYPE as appType");
				SELECT("t.APP_TIME as appTime");
				SELECT("t.APP_STATUS as appStatus");
				SELECT("t.PAY_CODE as payCode");
				SELECT("t.PAY_NAME as payName");
				SELECT("t.PAY_TRANSACTION_ID as payTransactionId");
				SELECT("t.ORDER_NO as orderNo");
				SELECT("t.EBP_CODE as ebpCode");
				SELECT("t.EBP_NAME as ebpName");
				SELECT("t.PAYER_ID_TYPE as payerIdType");
				SELECT("t.PAYER_ID_NUMBER as payerIdNumber");
				SELECT("t.PAYER_NAME as payerName");
				SELECT("t.TELEPHONE as telephone");
				SELECT("t.AMOUNT_PAID as amountPaid");
				SELECT("t.CURRENCY as currency");
				SELECT("t.PAY_TIME as payTime");
				SELECT("t.NOTE as note");
				SELECT("t.DATA_STATUS as dataStatus");
				SELECT("t.CRT_ID as crtId");
				SELECT("t.CRT_TM as crtTm");
				SELECT("t.UPD_ID as updId");
				SELECT("t.UPD_TM as updTm");
				SELECT("t.RETURN_STATUS as returnStatus");

				FROM("T_IMP_PAYMENT t");
				if (!StringUtils.isEmpty(paytransactionid)){
				WHERE("t.PAY_TRANSACTION_ID = #{paytransactionid}");
				}
				if (!StringUtils.isEmpty(orderNo)){
					WHERE("t.ORDER_NO = #{orderNo}");
				}
			}
		}.toString();
	}
}
