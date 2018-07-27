package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.Payment;
import com.xaeport.crossborder.data.entity.PaymentHead;
import com.xaeport.crossborder.data.provider.PaymentQuerySQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Map;

@Mapper
public interface PaymentQueryMapper {

	@SelectProvider(type = PaymentQuerySQLProvider.class,method ="seePaymentDetail")
	PaymentHead seePaymentDetail(Map<String, String> paramMap);
}
