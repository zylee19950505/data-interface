package com.xaeport.crossborder.service.paymentmanage;

import com.xaeport.crossborder.data.entity.Payment;
import com.xaeport.crossborder.data.entity.PaymentHead;
import com.xaeport.crossborder.data.mapper.PaymentQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentQueryService {

	@Autowired
	PaymentQueryMapper paymentQueryMapper;


	public Payment seePaymentDetail(Map<String, String> paramMap) {
		PaymentHead paymentHead = paymentQueryMapper.seePaymentDetail(paramMap);
		Payment payment = new Payment();
		payment.setPaymentHead(paymentHead);
		return payment;
	}
}
