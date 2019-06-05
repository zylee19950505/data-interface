package com.xaeport.crossborder.controller.api.trade.statistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpTradeVolumeList;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.trade.statistics.ImpTradeVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/*
 * 进口贸易额
 */
@RestController
@RequestMapping("/api/statistics")
public class ImpTradeVolumeApi extends BaseApi {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	ImpTradeVolumeService impTradeVolumeService;

	/*
	 * 进口贸易额
	 */
	@RequestMapping("/queryImpTradeVolumeList")
	public ResponseData queryImpTradeVolumeList(
			@RequestParam(required = false) String startFlightTimes,
			@RequestParam(required = false) String endFlightTimes,
			@RequestParam(required = false) String customsCode,//贸易关区
			@RequestParam(required = false) String tradeMode//贸易方式
	) {
		this.logger.debug(String.format("跨境贸易统计:[startFlightTimes:%s,endFlightTimes:%s,tradeCustom:%s,tradeWay:%s]", startFlightTimes,endFlightTimes,customsCode,tradeMode));
		Map<String, String> paramMap = new HashMap<String, String>();
		DataList<ImpTradeVolumeList> dataList = new DataList<ImpTradeVolumeList>();
		paramMap.put("startFlightTimes",startFlightTimes);
		paramMap.put("endFlightTimes",endFlightTimes);
		paramMap.put("customsCode",customsCode);
		paramMap.put("tradeMode",tradeMode);
        paramMap.put("returnStatus", StatusCode.FX);

		List<ImpTradeVolumeList> impTradeVolumeListList = new ArrayList<ImpTradeVolumeList>();
		try {
			impTradeVolumeListList = this.impTradeVolumeService.queryImpTradeVolumeList(paramMap);
			dataList.setData(impTradeVolumeListList);
			return new ResponseData(dataList);
		} catch (Exception e) {
			this.logger.error("进口贸易额查询失败", e);
			return new ResponseData(dataList);
		}
	}

    @RequestMapping("/queryImpTradeVolumeEChart")
    public ResponseData queryImpTradeVolumeEChart(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String customsCode,//贸易关区
            @RequestParam(required = false) String tradeMode//贸易方式
    ) {
        this.logger.debug(String.format("跨境贸易统计:[startFlightTimes:%s,endFlightTimes:%s,tradeCustom:%s,tradeWay:%s]", startFlightTimes,endFlightTimes,customsCode,tradeMode));
        Map<String, String> paramMap = new HashMap<String, String>();
        DataList<ImpTradeVolumeList> dataList = new DataList<ImpTradeVolumeList>();
        paramMap.put("startFlightTimes",startFlightTimes);
        paramMap.put("endFlightTimes",endFlightTimes);
        paramMap.put("customsCode",customsCode);
        paramMap.put("tradeMode",tradeMode);
        paramMap.put("returnStatus", StatusCode.FX);

        List<ImpTradeVolumeList> impTradeVolumeListList = new ArrayList<ImpTradeVolumeList>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date fDate=sdf.parse(startFlightTimes);
			Date oDate=sdf.parse(endFlightTimes);
			System.out.println(fDate.getTime());
			System.out.println(oDate.getTime());
			long mouth=(oDate.getTime()-fDate.getTime())/(1000*3600*24)/30;
			System.out.println(mouth);
			if (mouth>12){
			    //超过12个月也只显示12个月的业务量
                impTradeVolumeListList = this.impTradeVolumeService.queryImpTradeVolumeEChart(paramMap);
            }else {
                impTradeVolumeListList = this.impTradeVolumeService.queryImpTradeVolumeList(paramMap);
            }
            dataList.setData(impTradeVolumeListList);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("进口贸易额条形图查询失败", e);
            return new ResponseData(dataList);
        }
    }
}



