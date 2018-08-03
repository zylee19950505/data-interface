package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.provider.PaymentQuerySQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.LinkedHashMap;
import java.util.Map;

@Mapper
public interface PaymentQueryMapper {

	//查询支付单详情信息
	@SelectProvider(type = PaymentQuerySQLProvider.class,method ="seePaymentDetail")
	ImpPayment seePaymentDetail(Map<String, String> paramMap);

	//修改支付单详情信息
	@UpdateProvider(type = PaymentQuerySQLProvider.class,method = "updateImpPayment")
	void updateImpPayment(LinkedHashMap<String, String> entryHead);

}