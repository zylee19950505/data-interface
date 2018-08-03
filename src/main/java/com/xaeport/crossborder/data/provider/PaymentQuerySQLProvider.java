package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PaymentQuerySQLProvider {

	public String seePaymentDetail(Map<String,String> paramMap){
		final String paytransactionid = paramMap.get("paytransactionid");
		final String orderNo = paramMap.get("orderNo");

		return new SQL(){
			{
				SELECT("*");
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

	//修改清单表头信息
	public String updateImpPayment(LinkedHashMap<String, String> entryHead){
		return new SQL(){
			{
				UPDATE("T_IMP_PAYMENT t");
				WHERE("t.GUID = #{entryhead_guid}");
				if (!StringUtils.isEmpty(entryHead.get("order_no"))){
					SET("t.order_no = #{order_no}");
				}
				if (!StringUtils.isEmpty(entryHead.get("pay_code"))){
					SET("t.pay_code = #{pay_code}");
				}
				if (!StringUtils.isEmpty(entryHead.get("pay_name"))){
					SET("t.pay_name = #{pay_name}");
				}
				if (!StringUtils.isEmpty(entryHead.get("pay_transaction_id"))){
					SET("t.pay_transaction_id = #{pay_transaction_id}");
				}
				if (!StringUtils.isEmpty(entryHead.get("ebp_code"))){
					SET("t.ebp_code = #{ebp_code}");
				}
				if (!StringUtils.isEmpty(entryHead.get("ebp_name"))){
					SET("t.ebp_name = #{ebp_name}");
				}
				if (!StringUtils.isEmpty(entryHead.get("amount_paid"))){
					SET("t.amount_paid = #{amount_paid}");
				}
				if (!StringUtils.isEmpty(entryHead.get("payer_id_type"))){
					SET("t.payer_id_type = #{payer_id_type}");
				}
				if (!StringUtils.isEmpty(entryHead.get("payer_id_number"))){
					SET("t.payer_id_number = #{payer_id_number}");
				}
				if (!StringUtils.isEmpty(entryHead.get("payer_name"))){
					SET("t.payer_name = #{payer_name}");
				}
				if (!StringUtils.isEmpty(entryHead.get("pay_time"))){
					SET("t.pay_time = #{pay_time}");
				}
				if (!StringUtils.isEmpty(entryHead.get("note"))){
					SET("t.note = #{note}");
				}
			}
		}.toString();
	}

}
