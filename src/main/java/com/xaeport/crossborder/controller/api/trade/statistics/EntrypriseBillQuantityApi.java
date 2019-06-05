package com.xaeport.crossborder.controller.api.trade.statistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.EnterpriseBillQuantity;
import com.xaeport.crossborder.data.entity.ImpCountryList;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.trade.statistics.EntrypriseBillQuantityService;
import com.xaeport.crossborder.service.trade.statistics.ImpCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 企业清单量
 */
@RestController
@RequestMapping("/api/statistics")
public class EntrypriseBillQuantityApi extends BaseApi {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	EntrypriseBillQuantityService entrypriseBillQuantityService;

	/*
	 * 企业清单量
	 */
	@RequestMapping("/queryEnterpriseBillQuantityList")
	public ResponseData queryEnterpriseBillQuantityList(
			@RequestParam(required = false) String startFlightTimes,
			@RequestParam(required = false) String endFlightTimes,
			@RequestParam(required = false) String customsCode,//贸易关区
			@RequestParam(required = false) String tradeMode,//贸易方式
			@RequestParam(required = false) String entName,//企业名称
			@RequestParam(required = false) String entCustomsCode,//海关十位代码
			@RequestParam(required = false) String creditCode,//统一社会信用代码
			@RequestParam(required = false) String draw,//海关十位代码
			@RequestParam(required = false) String start,//开始
			@RequestParam(required = false) String length//结束
	) {
		this.logger.debug(String.format("企业清单量:[startFlightTimes:%s,endFlightTimes:%s,tradeCustom:%s,tradeWay:%s,entName:%s,entCustomsCode:%s,creditCode:%s]", startFlightTimes,endFlightTimes,customsCode,tradeMode,entName,entCustomsCode,creditCode));
		Map<String, String> paramMap = new HashMap<String, String>();
		DataList<EnterpriseBillQuantity> dataList = new DataList<EnterpriseBillQuantity>();
		paramMap.put("startFlightTimes",startFlightTimes);
		paramMap.put("endFlightTimes",endFlightTimes);
		paramMap.put("customsCode",customsCode);
		paramMap.put("tradeMode",tradeMode);
        paramMap.put("returnStatus", StatusCode.FX);
		paramMap.put("entName",entName);
		paramMap.put("entCustomsCode",entCustomsCode);
		paramMap.put("creditCode",creditCode);
		paramMap.put("start",start);
		paramMap.put("length",length);

		List<EnterpriseBillQuantity> impCountryLists = new ArrayList<EnterpriseBillQuantity>();

		try {
			impCountryLists = this.entrypriseBillQuantityService.queryEnterpriseBillQuantityList(paramMap);
			Integer totalCount = this.entrypriseBillQuantityService.queryEnterpriseBillQuantityCount(paramMap);
			dataList.setDraw(draw);
			dataList.setData(impCountryLists);
			dataList.setRecordsTotal(totalCount);
			dataList.setRecordsFiltered(totalCount);
			return new ResponseData(dataList);
		} catch (Exception e) {
			this.logger.error("企业清单量", e);
			return new ResponseData(dataList);
		}
	}
}



