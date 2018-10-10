package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PaymentQuerySQLProvider extends BaseSQLProvider{


	//支付单查询数据
	public String queryPaymentQueryList(Map<String, String> paramMap) throws Exception {
		final String orderNo = paramMap.get("orderNo");
		final String payTransactionId = paramMap.get("payTransactionId");
		final String end = paramMap.get("end");
		final String startFlightTimes = paramMap.get("startFlightTimes");
		final String endFlightTimes = paramMap.get("endFlightTimes");
		final String entId = paramMap.get("entId");
		final String roleId = paramMap.get("roleId");
		final String dataStatus = paramMap.get("dataStatus");
		final String returnStatus = paramMap.get("returnStatus");

		return new SQL() {
			{
				SELECT(
						" * from ( select rownum rn, f.* from ( " +
								" SELECT " +
								"    t.PAY_TRANSACTION_ID," +
								"    t.GUID," +
								"    t.ORDER_NO," +
								"    t.PAY_NAME," +
								"    t.EBP_NAME," +
								"    t.PAYER_NAME," +
								"    t.AMOUNT_PAID," +
								"    t.PAY_TIME," +
								"    t.NOTE," +
								"    t.RETURN_STATUS," +
								"    t.RETURN_INFO," +
								"    t.RETURN_TIME," +
								"    t.DATA_STATUS,"+
								" (select ss.status_name from t_status ss " +
								" where ss.status_code = t.return_status ) return_status_name");
				FROM("T_IMP_PAYMENT t");
				if(!roleId.equals("admin")){
					WHERE("t.ent_id = #{entId}");
				}
				if (!StringUtils.isEmpty(orderNo)) {
					WHERE("t.order_no = #{orderNo}");
				}
				if (!StringUtils.isEmpty(dataStatus)){
					WHERE("t.DATA_STATUS = #{dataStatus}");
				}
				if (!StringUtils.isEmpty(returnStatus)){
					if (returnStatus.equals("-")) {
						WHERE("t.RETURN_STATUS like '%'||#{returnStatus}||'%' ");
					} else {
						WHERE("t.RETURN_STATUS = #{returnStatus}");
					}
				}
				if (!StringUtils.isEmpty(payTransactionId)) {
					WHERE("t.PAY_TRANSACTION_ID = #{payTransactionId}");
				}
				if (!StringUtils.isEmpty(startFlightTimes)) {
					WHERE("t.app_time >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
				}
				if (!StringUtils.isEmpty(endFlightTimes)) {
					WHERE("t.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
				}
				if (!"-1".equals(end)) {
					ORDER_BY("t.crt_tm desc ) f  )  WHERE rn between #{start} and #{end}");
				} else {
					ORDER_BY("t.crt_tm desc ) f  )  WHERE rn >= #{start}");
				}
			}
		}.toString();
	}


	//支付单查询数据总数
	public String queryPaymentQueryCount(Map<String, String> paramMap) throws Exception {
		final String orderNo = paramMap.get("orderNo");
		final String payTransactionId = paramMap.get("payTransactionId");
		final String startFlightTimes = paramMap.get("startFlightTimes");
		final String endFlightTimes = paramMap.get("endFlightTimes");
		final String entId = paramMap.get("entId");
		final String roleId = paramMap.get("roleId");
		final String dataStatus = paramMap.get("dataStatus");
		final String returnStatus = paramMap.get("returnStatus");

		return new SQL() {
			{
				SELECT("COUNT(1)");
				FROM("T_IMP_PAYMENT t");
				if(!roleId.equals("admin")){
					WHERE("t.ent_id = #{entId}");
				}
				if (!StringUtils.isEmpty(dataStatus)){
					WHERE("t.DATA_STATUS = #{dataStatus}");
				}
				if (!StringUtils.isEmpty(returnStatus)){
					if (returnStatus.equals("-")) {
						WHERE("t.RETURN_STATUS like '%'||#{returnStatus}||'%' ");
					} else {
						WHERE("t.RETURN_STATUS = #{returnStatus}");
					}
				}
				if (!StringUtils.isEmpty(orderNo)) {
					WHERE("t.ORDER_NO = #{orderNo}");
				}
				if (!StringUtils.isEmpty(payTransactionId)) {
					WHERE("t.PAY_TRANSACTION_ID = #{payTransactionId}");
				}
				if (!StringUtils.isEmpty(startFlightTimes)) {
					WHERE("t.app_time >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
				}
				if (!StringUtils.isEmpty(endFlightTimes)) {
					WHERE("t.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
				}
			}
		}.toString();
	}

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

	//查询清单回执信息
	public String getImpPaymentRec(Map<String, String> paramMap) {
		final String id = paramMap.get("id");
		return new SQL() {
			{
				SELECT("ORDER_NO");
				SELECT("PAY_TRANSACTION_ID");
				SELECT("RETURN_STATUS");
				SELECT("RETURN_INFO");
				SELECT("RETURN_TIME");
				FROM("T_IMP_PAYMENT t");
				WHERE("t.GUID = #{id}");
			}
		}.toString();

	}

	//修改清单表头信息
	public String updateImpPayment(LinkedHashMap<String, String> entryHead){
		return new SQL(){
			{
				UPDATE("T_IMP_PAYMENT t");
				WHERE("t.GUID = #{entryhead_guid}");
				SET("t.APP_TYPE = '2'");
				SET("t.DATA_STATUS = 'CBDS1'");
				SET("t.UPD_TM = sysdate");
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
					SET("t.pay_time = to_date(#{pay_time},'yyyy-MM-dd')");
				}
				if (!StringUtils.isEmpty(entryHead.get("note"))){
					SET("t.note = #{note}");
				}
			}
		}.toString();
	}

}
