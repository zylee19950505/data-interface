package com.xaeport.crossborder.controller.api.trade.statistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpTradeVolumeList;
import com.xaeport.crossborder.service.trade.statistics.ImpTradeVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/*
 * 进口交易量
 */
@RestController
@RequestMapping("/api/statistics")
public class QueryEnterVolumeApi extends BaseApi {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	ImpTradeVolumeService impTradeVolumeService;


	@CrossOrigin(origins = "http://cb.xaeport.com")
    @RequestMapping("/queryEnterVolume")
    public ResponseData QueryEnterVolume(
    ) {
        Map<String, String> paramMap = new HashMap<String, String>();
        DataList<ImpTradeVolumeList> dataList = new DataList<ImpTradeVolumeList>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		paramMap.put("endFlightTimes",sdf.format(new Date()));
		paramMap.put("startFlightTimes",getNowOfLastYear());

		List<ImpTradeVolumeList> impTradeVolumeListList = new ArrayList<ImpTradeVolumeList>();
		try {

			impTradeVolumeListList = this.impTradeVolumeService.queryImpTradeVolumeList(paramMap);
            dataList.setData(impTradeVolumeListList);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("跨境进口交易额查询失败", e);
            return new ResponseData(dataList);
        }
    }

	//去年的现在
	public static String getNowOfLastYear() {
		SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar aGregorianCalendar = new GregorianCalendar();
		aGregorianCalendar.set(Calendar.YEAR, aGregorianCalendar
				.get(Calendar.YEAR) - 1);
		String currentYearAndMonth = aSimpleDateFormat
				.format(aGregorianCalendar.getTime());
		return currentYearAndMonth;
	}
}



