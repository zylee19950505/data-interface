package com.xaeport.crossborder.controller.api.waybillmanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.waybillmanage.WaybillQueryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/waybillManage")
public class WaybillQueryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    WaybillQueryService waybillService;

    /*
     * 运单查询
     */
    @RequestMapping(value = "/queryWaybillQuery", method = RequestMethod.GET)
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String logisticsNo,
            @RequestParam(required = false) String logisticsStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("运单查询查询条件参数:[startFlightTimes:%s,endFlightTimes:%s,logisticsNo:%s,billNo:%s,declareStatus:%s]", startFlightTimes, endFlightTimes, logisticsNo, billNo, logisticsStatus));
        Map<String, String> map = new HashMap<String, String>();

        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        map.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        map.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);
        map.put("billNo", billNo);
        map.put("orderNo", orderNo);
        map.put("logisticsNo", logisticsNo);
        map.put("logisticsStatus", logisticsStatus);

        map.put("start", start);
        map.put("length", length);
        map.put("end", end);
        map.put("extra_search", extra_search);
        map.put("entId", this.getCurrentUserEntId());
        map.put("roleId", this.getCurrentUserRoleId());
        //查询的状态可以使运单申报成功和运单状态申报成功
        map.put("dataStatus", StatusCode.YDSBCG);
        map.put("staDataStatus", StatusCode.YDZTSBCG);
        DataList<ImpLogisticsData> dataList = null;
        List<ImpLogisticsData> impLogisticsDataList = null;
        try {
            //查询数据
            impLogisticsDataList = this.waybillService.queryWaybillQueryDataList(map);
            //查询数据总数
            Integer count = this.waybillService.queryWaybillQueryCount(map);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(impLogisticsDataList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询运单查询数据失败", e);
            return new ResponseData("获取运单查询数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }

    /*
     * 运单详情查询
     */
    @RequestMapping("/seeWaybillDetail")
    public ResponseData waybillQueryById(
            @RequestParam(required = false) String guid,
            @RequestParam(required = false) String logistics_no
    ) {
        this.logger.debug(String.format("查询运单详情条件参数:[guid:%s,logistics_no:%s]", guid, logistics_no));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("guid", guid);
        paramMap.put("logisticsno", logistics_no);
        ImpLogisticsDetail impLogisticsDetail;
        try {
            impLogisticsDetail = waybillService.seeWaybillDetail(paramMap);
        } catch (Exception e) {
            this.logger.error("查询运单信息失败，logistics_no=" + logistics_no, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }

        return new ResponseData(impLogisticsDetail);
    }

    /*
    * 查询编辑运单详情
    * */
    @RequestMapping("/saveBillDetail")
    public ResponseData saveBillDetail(@Param("entryJson") String entryJson) {
        //订单信息json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        // 订单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");
        // 订单表体
        ArrayList<LinkedHashMap<String, String>> entryLists = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");
        Map<String, String> rtnMap = new HashMap<>();
        try {
            // 保存订单详情信息
            rtnMap = waybillService.saveBillDetail(entryHead, entryLists);
        } catch (Exception e) {
            logger.error("保存运单详细信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存运单详细信息时发生异常");
        }
        return new ResponseData(rtnMap);

    }

    /*
    * 运单回执详情
    * */
    @RequestMapping("/returnDetail")
    public ResponseData returnDetail(
            @RequestParam(required = false) String guid,
            @RequestParam(required = false) String logisticsNo
    ) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("guId", guid);
        paramMap.put("logisticsNo", logisticsNo);
        ImpLogistics impLogistics;
        try {
            impLogistics = waybillService.queryReturnDetail(paramMap);
        } catch (Exception e) {
            this.logger.error("查询回执详情信息失败，logistics_no=" + logisticsNo, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(impLogistics);
    }

}
