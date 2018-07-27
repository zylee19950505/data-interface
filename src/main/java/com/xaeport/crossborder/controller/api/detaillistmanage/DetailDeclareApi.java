package com.xaeport.crossborder.controller.api.detaillistmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.detaillistmanage.DetailDeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/detailManage")
public class DetailDeclareApi extends BaseApi{

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DetailDeclareService detailDeclareService;

    /*
     * 邮件申报查询
     */
    @RequestMapping("/queryDetailDeclare")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String orderNo,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s]",startFlightTimes, endFlightTimes, orderNo));
        Map<String, String> paramMap = new HashMap<String, String>();
        //查询参数
        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("orderNo", orderNo);
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        //分页参数
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);
        //类型参数
        paramMap.put("dsStatus", String.format("%s,%s,%s", StatusCode.QDDSB, StatusCode.QDYSB, StatusCode.QDCB));

        //更新人
        DataList<ImpInventory> dataList = null;
        List<ImpInventory> resultList = null;
        try {
            //查询列表
            resultList = this.detailDeclareService.queryInventoryDeclareList(paramMap);
            //查询总数
            Integer count = this.detailDeclareService.queryInventoryDeclareCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询清单申报数据失败", e);
            return new ResponseData("获取清单申报数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }

























}
