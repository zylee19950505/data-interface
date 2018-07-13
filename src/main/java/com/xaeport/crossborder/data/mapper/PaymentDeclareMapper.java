package com.xaeport.crossborder.data.mapper;


/*
 * 支付单申报
 */

import com.xaeport.crossborder.data.provider.PaymentDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaymentDeclareMapper {

	/*
	 * 查询支付单申报数据
	 */
	@SelectProvider(type = PaymentDeclareSQLProvider.class,method = "queryPaymentDeclareList")
	List<Map<String,String>> queryPaymentDeclareList(Map<String, Object> paramMap);

	/*
	 * 查询支付单申报数据总数
	 */
	@SelectProvider(type = PaymentDeclareSQLProvider.class,method = "queryPaymentDeclareCount")
	Integer queryPaymentDeclareCount(Map<String, Object> paramMap);


}
