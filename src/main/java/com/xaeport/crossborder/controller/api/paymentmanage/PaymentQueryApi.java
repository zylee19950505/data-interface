package com.xaeport.crossborder.controller.api.paymentmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.Payment;
import com.xaeport.crossborder.service.paymentmanage.PaymentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/paymentmanage/querypayment")
public class PaymentQueryApi extends BaseApi {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	PaymentQueryService paymentQueryService;

	@RequestMapping("/seePaymentDetail")
	public ResponseData seePaymentDetail(
			@RequestParam(required = false) String orderNo,
			@RequestParam(required = false) String paytransactionid
	){
		this.logger.debug(String.format("查询支付单详情条件参数:[orderNo:%s,paytransactionid:%s]",orderNo,paytransactionid));
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("orderNo",orderNo);
		paramMap.put("paytransactionid",paytransactionid);
		Payment payment;
		try {
			 payment = paymentQueryService.seePaymentDetail(paramMap);
		} catch (Exception e) {
			this.logger.error("查询支付单信息失败，orderNo=" + orderNo, e);
			return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
		}

		return new ResponseData(payment);
	}
}
