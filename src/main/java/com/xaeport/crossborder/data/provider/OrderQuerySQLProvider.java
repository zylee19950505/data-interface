package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderQuerySQLProvider extends BaseSQLProvider{


	public String queryOrderHeadList(Map<String, String> paramMap){
		final String orderNo = paramMap.get("orderNo");
		final String startDeclareTime = paramMap.get("startDeclareTime");
		final String endDeclareTimes = paramMap.get("endDeclareTimes");
		final String start = paramMap.get("start");
		final String length = paramMap.get("length");
		final String entId = paramMap.get("entId");
		final String roleId = paramMap.get("roleId");
		final String dataStatus = paramMap.get("dataStatus");

		return new SQL() {
			{
				SELECT(" * from ( select w.*, ROWNUM AS rn from ( " +
						" select * from T_IMP_ORDER_HEAD th ");
				WHERE("1=1");
				if(!roleId.equals("admin")){
					WHERE("th.ent_id = #{entId}");
				}
				if (!StringUtils.isEmpty(orderNo)) {
					WHERE("th.order_no = #{orderNo}");
				}
				if (!StringUtils.isEmpty(dataStatus)) {
					WHERE("th.DATA_STATUS = #{dataStatus}");
				}
				if (!StringUtils.isEmpty(startDeclareTime)) {
					WHERE(" th.app_time >= to_date(#{startDeclareTime}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
				}
				if (!StringUtils.isEmpty(endDeclareTimes)) {
					WHERE(" th.app_time <= to_date(#{endDeclareTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
				}
				if (!"-1".equals(length)) {
					ORDER_BY("th.crt_tm desc ) w  )   WHERE rn >= #{start} AND rn < #{start} + #{length} ");
				} else {
					ORDER_BY("th.crt_tm desc ) w  )   WHERE rn >= #{start}");
				}
			}
		}.toString();
	}

	/*订单查询条数
	* queryOrderHeadListCount
	* */
	public String queryOrderHeadListCount(Map<String, String> paramMap){
		final String orderNo = paramMap.get("orderNo");
		final String startDeclareTime = paramMap.get("startDeclareTime");
		final String endDeclareTimes = paramMap.get("endDeclareTimes");
		final String entId = paramMap.get("entId");
		final String roleId = paramMap.get("roleId");
		final String dataStatus = paramMap.get("dataStatus");

		return new SQL() {
			{
				SELECT(" count(*) count from T_IMP_ORDER_HEAD th ");
				WHERE("1=1");
				if(!roleId.equals("admin")){
					WHERE("th.ent_id = #{entId}");
				}
				if (!StringUtils.isEmpty(orderNo)) {
					WHERE("th.order_no = #{orderNo}");
				}
				if (!StringUtils.isEmpty(dataStatus)) {
					WHERE("th.DATA_STATUS = #{dataStatus}");
				}
				if (!StringUtils.isEmpty(startDeclareTime)) {
					WHERE(" th.app_time >= to_date(#{startDeclareTime}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
				}
				if (!StringUtils.isEmpty(endDeclareTimes)) {
					WHERE(" th.app_time <= to_date(#{endDeclareTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
				}
			}
		}.toString();
	}

	/*
	* 查询表体
	* 点击查看详情
	* queryOrderBodyList
	* */
	public String queryOrderBodyList(Map<String, String> paramMap){
		final String guid = paramMap.get("guid");
		final String orderNo = paramMap.get("orderNo");
	/*	final String start = paramMap.get("start");
		final String length = paramMap.get("length");*/
		return new SQL() {
			{
				SELECT( " * from T_IMP_ORDER_body tb ");
				WHERE("1=1");
				if (!StringUtils.isEmpty(orderNo)) {
					WHERE("tb.ORDER_NO = #{orderNo}");
				}
				if (!StringUtils.isEmpty(guid)){
					WHERE("tb.HEAD_GUID = #{guid}");
				}
				/*if (!"-1".equals(length)) {
					ORDER_BY("th.APP_TIME desc ) w  )   WHERE rn >= #{start} AND rn < #{start} + #{length} ");
				} else {
					ORDER_BY("th.APP_TIME desc ) w  )   WHERE rn >= #{start}");
				}*/
			}
		}.toString();
	}


	/*
	* 查询表体
	* 点击查看详情
	* queryOrderBodyListCount
	* */
	public String queryOrderBodyListCount(Map<String, String> paramMap){
		final String guid = paramMap.get("guid");
		final String orderNo = paramMap.get("orderNo");
		return new SQL() {
			{
				SELECT(" count(*) count from T_IMP_ORDER_BODY tb ");
				WHERE("1=1");
				if (!StringUtils.isEmpty(orderNo)) {
					WHERE("tb.ORDER_NO = #{orderNo}");
				}
				if (!StringUtils.isEmpty(guid)){
					WHERE("tb.HEAD_GUID = #{guid}");
				}
			}
		}.toString();
	}

/*点击查看邮件详情
* queryOrderHead
* */
	public String queryOrderHead(Map<String,String> paramMap){
		final String id = paramMap.get("id");
		return  new SQL(){
			{
				SELECT("*");
				FROM("T_IMP_ORDER_HEAD th");
				WHERE("th.GUID = #{id}");
			}
		}.toString();

	}
	public String queryOrderBody(Map<String,String> paramMap){
		final String id = paramMap.get("id");
		return  new SQL(){
			{
				SELECT("*");
				FROM("T_IMP_ORDER_BODY tb");
				WHERE("tb.HEAD_GUID = #{id}");
			}
		}.toString();
	}
	/*修改邮件查询编辑信息(表头)
	* updateOrderHead
	* */
	public String updateOrderHead(LinkedHashMap<String, String> entryHead){
		return new SQL(){
			{
				UPDATE("T_IMP_ORDER_HEAD t");
				WHERE("t.guid = #{entryhead_guid}");
				if (!StringUtils.isEmpty(entryHead.get("order_No"))){
					SET("t.ORDER_NO = #{order_No}");
				}
				if (!StringUtils.isEmpty(entryHead.get("goods_Value"))){
					SET("t.GOODS_VALUE = #{goods_Value}");
				}
				if (!StringUtils.isEmpty(entryHead.get("ebp_Code"))){
					SET("t.EBP_CODE = #{ebp_Code}");
				}
				if (!StringUtils.isEmpty(entryHead.get("ebp_Name"))){
					SET("t.EBP_NAME = #{ebp_Name}");
				}
				if (!StringUtils.isEmpty(entryHead.get("ebc_Code"))){
					SET("t.EBC_CODE = #{ebc_Code}");
				}
				if (!StringUtils.isEmpty(entryHead.get("ebc_Name"))){
					SET("t.EBC_NAME = #{ebc_Name}");
				}
				if (!StringUtils.isEmpty(entryHead.get("buyer_Reg_No"))){
					SET("t.BUYER_REG_NO = #{buyer_Reg_No}");
				}
				if (!StringUtils.isEmpty(entryHead.get("buyer_Id_Number"))){
					SET("t.BUYER_ID_NUMBER = #{buyer_Id_Number}");
				}
				if (!StringUtils.isEmpty(entryHead.get("buyer_Name"))){
					SET("t.BUYER_NAME = #{buyer_Name}");
				}
				if (!StringUtils.isEmpty(entryHead.get("consignee"))){
					SET("t.CONSIGNEE = #{consignee}");
				}
				if (!StringUtils.isEmpty(entryHead.get("consignee_Telephone"))){
					SET("t.CONSIGNEE_TELEPHONE = #{consignee_Telephone}");
				}
				if (!StringUtils.isEmpty(entryHead.get("consignee_Address"))){
					SET("t.CONSIGNEE_ADDRESS = #{consignee_Address}");
				}
				if (!StringUtils.isEmpty(entryHead.get("discount"))){
					SET("t.DISCOUNT = #{discount}");
				}
				if (!StringUtils.isEmpty(entryHead.get("tax_Total"))){
					SET("t.TAX_TOTAL = #{tax_Total}");
				}
				if (!StringUtils.isEmpty(entryHead.get("freight"))){
					SET("t.FREIGHT = #{freight}");
				}
				if (!StringUtils.isEmpty(entryHead.get("consignee_Ditrict"))){
					SET("t.CONSIGNEE_DITRICT = #{consignee_Ditrict}");
				}
				if (!StringUtils.isEmpty(entryHead.get("note"))){
					SET("t.NOTE = #{note}");
				}




			}
		}.toString();
	}
	/*修改邮件查询编辑信息(表体)
	* updateOrderList
	* */
	public String updateOrderList(LinkedHashMap<String, String> entryList){
		return new SQL(){
			{
				UPDATE("T_IMP_ORDER_BODY t");
				WHERE("t.HEAD_GUID = #{entryhead_guid}");
				WHERE("t.G_NUM = #{g_no}");
				if (!StringUtils.isEmpty(entryList.get("order_No"))){
					SET("t.ORDER_NO = #{order_No}");
				}
				if (!StringUtils.isEmpty(entryList.get("item_Name"))){
					SET("t.ITEM_NAME = #{item_Name}");
				}
				if (!StringUtils.isEmpty(entryList.get("qty"))){
					SET("t.QTY = #{qty}");
				}
				if (!StringUtils.isEmpty(entryList.get("price"))){
					SET("t.PRICE = #{price}");
				}
				if (!StringUtils.isEmpty(entryList.get("unit"))){
					SET("t.UNIT = #{unit}");
				}
				if (!StringUtils.isEmpty(entryList.get("total_Price"))){
					SET("t.TOTAL_PRICE = #{total_Price}");
				}
				if (!StringUtils.isEmpty(entryList.get("note"))){
					SET("t.NOTE = #{note}");
				}
			}
		}.toString();
	}

}
