package com.xaeport.crossborder.controller.api.paymentmanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.service.paymentmanage.PaymentQueryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/paymentManage/querypayment")
public class PaymentQueryApi extends BaseApi {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	PaymentQueryService paymentQueryService;

	@RequestMapping("/queryPaymentQuery")
	public ResponseData queryOrderDeclare(
			@RequestParam(required = false) String orderNo,
			@RequestParam(required = false) String payTransactionId,
			@RequestParam(required = false) String startFlightTimes,
			@RequestParam(required = false) String endFlightTimes,
			HttpServletRequest request

	) {
		this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s,payTransactionId:%s]",startFlightTimes, endFlightTimes, orderNo, payTransactionId));

		Map<String, String> paramMap = new HashMap<String, String>();

		String startStr = request.getParameter("start");
		String length = request.getParameter("length");
		String extra_search = request.getParameter("extra_search");
		String draw = request.getParameter("draw");
		String start = String.valueOf((Integer.parseInt(startStr) + 1));
		String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

		paramMap.put("orderNo", orderNo);
		paramMap.put("payTransactionId", payTransactionId);
		paramMap.put("startFlightTimes", startFlightTimes);
		paramMap.put("endFlightTimes", endFlightTimes);

		paramMap.put("start", start);
		paramMap.put("length", length);
		paramMap.put("end", end);
		paramMap.put("extra_search", extra_search);

		paramMap.put("entId",this.getCurrentUserEntId());
		paramMap.put("roleId",this.getCurrentUserRoleId());

		DataList<ImpPayment> dataList = null;
		List<ImpPayment> resultList = null;
		try {
			//查询支付单列表
			resultList = this.paymentQueryService.queryPaymentQueryList(paramMap);
			//查询支付单总数
			Integer count = this.paymentQueryService.queryPaymentQueryCount(paramMap);
			dataList = new DataList<>();
			dataList.setDraw(draw);
			dataList.setData(resultList);
			dataList.setRecordsTotal(count);
			dataList.setRecordsFiltered(count);
		} catch (Exception e) {
			this.logger.error("查询支付单查询数据失败", e);
			return new ResponseData("获取支付单查询数据错误", HttpStatus.BAD_REQUEST);
		}
		return new ResponseData(dataList);
	}

	//查询支付单详情
	@RequestMapping("/seePaymentDetail")
	public ResponseData seePaymentDetail(
			@RequestParam(required = false) String orderNo,
			@RequestParam(required = false) String paytransactionid
	){
		this.logger.debug(String.format("查询支付单详情条件参数:[orderNo:%s,paytransactionid:%s]",orderNo,paytransactionid));
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("orderNo",orderNo);
		paramMap.put("paytransactionid",paytransactionid);
		ImpPayment impPayment;
		try {
			impPayment = paymentQueryService.seePaymentDetail(paramMap);
		} catch (Exception e) {
			this.logger.error("查询支付单信息失败，orderNo=" + orderNo, e);
			return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
		}

		return new ResponseData(impPayment);
	}

	//保存支付单信息
	@RequestMapping("/savePaymentDetail")
	public ResponseData savePaymentDetail(@Param("entryJson") String entryJson){
		//支付单json信息
		LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

		// 支付单表头
		LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");

		Map<String, String> rtnMap = new HashMap<>();
		try {
			// 保存详情信息
			rtnMap = paymentQueryService.savePaymentDetail(entryHead);
		} catch (Exception e) {
			logger.error("保存支付单详细信息时发生异常", e);
			rtnMap.put("result", "false");
			rtnMap.put("msg", "保存支付单详细信息时发生异常");
		}
		return new ResponseData(rtnMap);
	}









}
