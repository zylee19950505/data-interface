package com.xaeport.crossborder.controller.api.waybillmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.service.waybillmanage.WaybillQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/waybillManage")
public class WaybillQueryApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    WaybillQueryService waybillService;

    /*
     * 运单申报查询
     */
    @RequestMapping(value = "/queryWaybillQuery" , method = RequestMethod.GET)
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String logisticsNo,
            @RequestParam(required = false) String logisticsStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("运单查询查询条件参数:[startFlightTimes:%s,endFlightTimes:%s,logisticsNo:%s,declareStatus:%s]", startFlightTimes, endFlightTimes, logisticsNo, logisticsStatus));
        Map<String, String> map = new HashMap<String,String>();

        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        map.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        map.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);
        map.put("logisticsNo", logisticsNo);
        map.put("logisticsStatus", logisticsStatus);

        map.put("start", start);
        map.put("length", length);
        map.put("end", end);
        map.put("extra_search", extra_search);
        DataList<ImpLogistics> dataList = null;
        List<ImpLogistics> impLogisticsList = null;
        try {
            //查询数据
            impLogisticsList = this.waybillService.queryWaybillQueryDataList(map);
            //查询数据总数
            Integer count = this.waybillService.queryWaybillQueryCount(map);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(impLogisticsList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询运单查询数据失败", e);
            return new ResponseData("获取运单查询数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }

}
