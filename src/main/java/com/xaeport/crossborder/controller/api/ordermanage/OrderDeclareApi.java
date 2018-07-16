package com.xaeport.crossborder.controller.api.ordermanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.service.ordermanage.OrderDeclareSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/*
 * 订单申报
 */

@RestController
@RequestMapping("/api/orderManage")
public class OrderDeclareApi extends BaseApi {


    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    OrderDeclareSevice orderDeclareService;

    /*
     * 邮件申报查询
     */
    @RequestMapping("/queryOrderDeclare")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String idCardValidate,
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[idCardValidate:%s,startFlightTimes:%s,endFlightTimes:%s,orderNo:%s,start:%s,length:%s]", idCardValidate, startFlightTimes, endFlightTimes, orderNo, start, length));
        Map<String, Object> paramMap = new HashMap<String, Object>();
        DataList<List<Map<String, String>>> dataList = new DataList<List<Map<String, String>>>();
        paramMap.put("idCardValidate", idCardValidate);
        paramMap.put("orderNo", orderNo);
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        paramMap.put("start", start);
        paramMap.put("length", length);
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        try {
            //查询列表
            resultList = orderDeclareService.queryOrderDeclareList(paramMap);
            //查询总数
            Integer count = orderDeclareService.queryOrderDeclareCount(paramMap);
            dataList.setDraw(draw);
//            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("查询订单申报数据失败", e);
            return new ResponseData(dataList);
        }
    }


}
