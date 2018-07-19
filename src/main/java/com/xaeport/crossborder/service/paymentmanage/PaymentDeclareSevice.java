package com.xaeport.crossborder.service.paymentmanage;


import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.Payment;
import com.xaeport.crossborder.data.mapper.OrderDeclareMapper;
import com.xaeport.crossborder.data.mapper.PaymentDeclareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/*
 * 支付单申报
 */
@Service
public class PaymentDeclareSevice {

	@Autowired
	PaymentDeclareMapper paymentDeclareMapper;

	private Log logger = LogFactory.getLog(this.getClass());


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

	public ImpPayment queryPaymentById(String paytransactionid) throws Exception {
		return paymentDeclareMapper.queryPaymentById(paytransactionid);
	}

	/**
	 * 更新舱单申报状态
	 *
	 * @return
	 */
	@Transactional
	public boolean updateSubmitCustom(Map<String, String> paramMap) {
		boolean flag;
		try {
			this.paymentDeclareMapper.updateSubmitCustom(paramMap);
			flag = true;
		} catch (Exception e) {
			flag = false;
			String exceptionMsg = String.format("处理支付单[orderNo: %s]时发生异常", paramMap.get("submitKeys"));
			logger.error(exceptionMsg, e);
		}
		return flag;
	}

}
