package com.xaeport.crossborder.controller.api.trade.statistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpCountryList;
import com.xaeport.crossborder.data.entity.ImpTradeVolumeList;
import com.xaeport.crossborder.service.trade.statistics.ImpCountryService;
import com.xaeport.crossborder.service.trade.statistics.ImpTradeVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 进口国家
 */
@RestController
@RequestMapping("/api/statistics")
public class ImpCountryApi extends BaseApi {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	ImpCountryService impCountryService;

	/*
	 * 进口国家
	 */
	@RequestMapping("/queryImpCountryList")
	public ResponseData queryImpCountryList(
			@RequestParam(required = false) String startFlightTimes,
			@RequestParam(required = false) String endFlightTimes,
			@RequestParam(required = false) String customsCode,//贸易关区
			@RequestParam(required = false) String tradeMode//贸易方式
	) {
		this.logger.debug(String.format("进口国家:[startFlightTimes:%s,endFlightTimes:%s,tradeCustom:%s,tradeWay:%s]", startFlightTimes,endFlightTimes,customsCode,tradeMode));
		Map<String, String> paramMap = new HashMap<String, String>();
		DataList<ImpCountryList> dataList = new DataList<ImpCountryList>();
		paramMap.put("startFlightTimes",startFlightTimes);
		paramMap.put("endFlightTimes",endFlightTimes);
		paramMap.put("customsCode",customsCode);
		paramMap.put("tradeMode",tradeMode);

		List<ImpCountryList> impCountryLists = new ArrayList<ImpCountryList>();

		try {
			impCountryLists = this.impCountryService.queryImpCountryList(paramMap);
			dataList.setData(impCountryLists);
			return new ResponseData(dataList);
		} catch (Exception e) {
			this.logger.error("进口国家", e);
			return new ResponseData(dataList);
		}
	}

	@RequestMapping("/queryImpCountryEChart")
	public ResponseData queryImpCountryEChart(
			@RequestParam(required = false) String startFlightTimes,
			@RequestParam(required = false) String endFlightTimes,
			@RequestParam(required = false) String customsCode,//贸易关区
			@RequestParam(required = false) String tradeMode//贸易方式
	) {
		this.logger.debug(String.format("进口国家:[startFlightTimes:%s,endFlightTimes:%s,tradeCustom:%s,tradeWay:%s]", startFlightTimes,endFlightTimes,customsCode,tradeMode));
		Map<String, String> paramMap = new HashMap<String, String>();
		DataList<ImpCountryList> dataList = new DataList<ImpCountryList>();
		paramMap.put("startFlightTimes",startFlightTimes);
		paramMap.put("endFlightTimes",endFlightTimes);
		paramMap.put("customsCode",customsCode);
		paramMap.put("tradeMode",tradeMode);

		List<ImpCountryList> impCountryLists = new ArrayList<ImpCountryList>();

		try {
			impCountryLists = this.impCountryService.queryImpCountryEChart(paramMap);
			dataList.setData(impCountryLists);
			return new ResponseData(dataList);
		} catch (Exception e) {
			this.logger.error("进口国家", e);
			return new ResponseData(dataList);
		}
	}
}



