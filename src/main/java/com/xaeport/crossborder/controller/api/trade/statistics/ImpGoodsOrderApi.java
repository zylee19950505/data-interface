package com.xaeport.crossborder.controller.api.trade.statistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.EnterpriseBillQuantity;
import com.xaeport.crossborder.data.entity.ImpGoodsOrder;
import com.xaeport.crossborder.service.trade.statistics.EntrypriseBillQuantityService;
import com.xaeport.crossborder.service.trade.statistics.ImpGoodsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 进口商品总值排序
 */
@RestController
@RequestMapping("/api/statistics")
public class ImpGoodsOrderApi extends BaseApi {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	ImpGoodsOrderService impGoodsOrderService;

	/*
	 * 进口商品总值排序
	 */
	@RequestMapping("/queryImpGoodsOrderList")
	public ResponseData queryImpGoodsOrderList(
			@RequestParam(required = false) String startFlightTimes,
			@RequestParam(required = false) String endFlightTimes,
			@RequestParam(required = false) String customsCode,//贸易关区
			@RequestParam(required = false) String tradeMode,//贸易方式
			@RequestParam(required = false) String draw,//次数
			@RequestParam(required = false) String start,//开始
			@RequestParam(required = false) String length//结束

	) {
		this.logger.debug(String.format("进口商品总值排序:[startFlightTimes:%s,endFlightTimes:%s,tradeCustom:%s,tradeWay:%s]", startFlightTimes,endFlightTimes,customsCode,tradeMode));
		Map<String, String> paramMap = new HashMap<String, String>();
		DataList<ImpGoodsOrder> dataList = new DataList<ImpGoodsOrder>();
		paramMap.put("startDeclareTime",startFlightTimes);
		paramMap.put("endFlightTimes",endFlightTimes);
		paramMap.put("customsCode",customsCode);
		paramMap.put("tradeMode",tradeMode);
		paramMap.put("draw",draw);
		paramMap.put("start",start);
		paramMap.put("length",length);

		List<ImpGoodsOrder> impGoodsOrderLists = new ArrayList<ImpGoodsOrder>();

		try {
			impGoodsOrderLists = this.impGoodsOrderService.queryImpGoodsOrderList(paramMap);
			Integer totalcount = this.impGoodsOrderService.queryImpGoodsOrderListCount(paramMap);
			dataList.setDraw(draw);
			dataList.setData(impGoodsOrderLists);
			dataList.setRecordsTotal(totalcount);
			dataList.setRecordsFiltered(totalcount);
			return new ResponseData(dataList);
		} catch (Exception e) {
			this.logger.error("进口商品总值排序", e);
			return new ResponseData(dataList);
		}
	}
}



