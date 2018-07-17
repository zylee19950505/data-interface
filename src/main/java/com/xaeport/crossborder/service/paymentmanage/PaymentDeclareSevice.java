package com.xaeport.crossborder.service.paymentmanage;


import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.mapper.OrderDeclareMapper;
import com.xaeport.crossborder.data.mapper.PaymentDeclareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 * 支付单申报
 */
@Service
public class PaymentDeclareSevice {

	@Autowired
	PaymentDeclareMapper paymentDeclareMapper;

	/*
     * 查询支付单申报数据
     */
	public List<ImpPayment> queryPaymentDeclareList(Map<String, String> paramMap) throws Exception{
		return  paymentDeclareMapper.queryPaymentDeclareList(paramMap);
	}

	/*
     * 查询支付单申报数据总数
     */
	public Integer queryPaymentDeclareCount(Map<String, String> paramMap) throws Exception {
		return paymentDeclareMapper.queryPaymentDeclareCount(paramMap);
	}
}
