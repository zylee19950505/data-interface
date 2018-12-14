package com.xaeport.crossborder.controller.api.querystatistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.querystatistics.CustomsStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/customs")
public class CustomsStatisticsApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CustomsStatisticsService customsStatisticsService;
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    LoadData loadData;

    @RequestMapping("/queryCustoms")
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
            resultList = this.customsStatisticsService.queryCustoms(paramMap);
        } catch (Exception e) {
            this.logger.error("查询通关统计数据失败", e);
            return new ResponseData("获取通关统计数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);
    }


}
