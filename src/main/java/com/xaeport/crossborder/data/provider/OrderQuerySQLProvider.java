package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class OrderQuerySQLProvider extends BaseSQLProvider{


	public String queryOrderHeadList(Map<String, String> paramMap){
		final String orderNo = paramMap.get("orderNo");
		final String startDeclareTime = paramMap.get("startDeclareTime");
		final String endDeclareTimes = paramMap.get("endDeclareTimes");
		final String start = paramMap.get("start");
		final String length = paramMap.get("length");

		return new SQL() {
			{
				SELECT(" * from ( select w.*, ROWNUM AS rn from ( " +
						" select * from T_IMP_ORDER_HEAD th ");
				WHERE("1=1");
				if (!StringUtils.isEmpty(orderNo)) {
					WHERE("th.order_no = #{orderNo}");
				}
				if (!StringUtils.isEmpty(startDeclareTime)) {
					//现在改为upd_tm
					//WHERE(" th.APP_TIME >= to_date(#{startDeclareTime}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
					WHERE(" th.upd_tm >= to_date(#{startDeclareTime}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
				}
				if (!StringUtils.isEmpty(endDeclareTimes)) {
					//现在先改为upd_tm
					//WHERE(" th.APP_TIME <= to_date(#{endDeclareTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
					WHERE(" th.upd_tm <= to_date(#{endDeclareTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
				}
				if (!"-1".equals(length)) {
					ORDER_BY("th.APP_TIME desc ) w  )   WHERE rn >= #{start} AND rn < #{start} + #{length} ");
				} else {
					ORDER_BY("th.APP_TIME desc ) w  )   WHERE rn >= #{start}");
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

		return new SQL() {
			{
				SELECT(" count(*) count from T_IMP_ORDER_HEAD th ");
				WHERE("1=1");
				if (!StringUtils.isEmpty(orderNo)) {
					WHERE("th.order_no = #{orderNo}");
				}
				if (!StringUtils.isEmpty(startDeclareTime)) {
					//现在改为upd_tm
					//WHERE(" th.APP_TIME >= to_date(#{startDeclareTime}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
					WHERE(" th.upd_tm >= to_date(#{startDeclareTime}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
				}
				if (!StringUtils.isEmpty(endDeclareTimes)) {
					//现在先改为upd_tm
					//WHERE(" th.APP_TIME <= to_date(#{endDeclareTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
					WHERE(" th.upd_tm <= to_date(#{endDeclareTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
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


}
