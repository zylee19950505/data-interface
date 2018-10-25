package com.xaeport.crossborder.controller.api.querystatistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.queryStatistics.CountryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/country")
public class CountryStatisticsApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CountryStatisticsService countryStatisticsService;
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    LoadData loadData;

    @RequestMapping("/queryTradeCountry")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String ieFlag,
            @RequestParam(required = false) String entId
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,entId:%s]", startFlightTimes, endFlightTimes, entId));
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        paramMap.put("ieFlag", ieFlag);
        paramMap.put("entId", entId);
        paramMap.put("dataStatus", StatusCode.QDSBCG);
        paramMap.put("returnStatus", StatusCode.FX);

        List<ImpInventory> resultList;
        try {
            resultList = this.countryStatisticsService.queryTradeCountryData(paramMap);
        } catch (Exception e) {
            this.logger.error("查询贸易国统计数据失败", e);
            return new ResponseData("查询贸易国统计数据失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);
    }

}
