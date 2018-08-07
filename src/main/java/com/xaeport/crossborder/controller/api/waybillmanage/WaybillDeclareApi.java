package com.xaeport.crossborder.controller.api.waybillmanage;


import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpLogisticsData;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.waybillmanage.WaybillDeclareService;
import com.xaeport.crossborder.tools.GetIpAddr;
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
public class WaybillDeclareApi extends BaseApi{

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    WaybillDeclareService waybillService;

    /*
     * 运单申报查询
     */
    @RequestMapping(value = "/queryWaybillDeclare" , method = RequestMethod.GET)
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String logisticsNo,
            @RequestParam(required = false) String dataStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("运单申报查询条件参数:[startFlightTimes:%s,endFlightTimes:%s,logisticsNo:%s,dataStatus:%s]", startFlightTimes, endFlightTimes, logisticsNo, dataStatus));

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
        map.put("dataStatus", dataStatus);

        map.put("start", start);
        map.put("length", length);
        map.put("end", end);
        map.put("extra_search", extra_search);
        DataList<ImpLogisticsData> dataList = null;
        List<ImpLogisticsData> impLogisticsDataList = null;
        try {
            //查询数据
            impLogisticsDataList = this.waybillService.queryWaybillDeclareDataList(map);
            //查询数据总数
            Integer count = this.waybillService.queryWaybillDeclareCount(map);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(impLogisticsDataList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询运单申报数据失败", e);
             return new ResponseData("获取运单申报数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }
    /**
     * 运单申报-提交海关
     *
     * @param submitKeys EntryHead.IDs
     */
    @RequestMapping(value = "/submitCustom", method = RequestMethod.POST)
    public ResponseData saveSubmitCustom(@RequestParam(required = false) String submitKeys,
                                         HttpServletRequest request) {
        this.logger.info("运单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的运单信息！");
        }
            Users currentUser = this.getCurrentUsers();
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("dataStatus", StatusCode.YDSBZ);
            paramMap.put("dataStatusWhere", StatusCode.YDDSB + "," + StatusCode.YDCB+","+StatusCode.EXPORT);//可以申报的状态,支付单待申报,支付单重报,已导入
            paramMap.put("currentUserId", currentUser.getId());
            /* paramMap.put("enterpriseId", this.getCurrentUserEnterpriseId());*/  //暂时不获取企业id
            paramMap.put("submitKeys", submitKeys);//订单遍号

            // 调用支付单申报Service获取提交海关结果
            boolean flag = waybillService.updateSubmitWaybill(paramMap);
            if (flag) {
                return rtnResponse("true", "运单申报海关提交成功！");
            } else {
                return rtnResponse("false", "运单申报海关提交失败！");
            }
    }
    /**
     * 运单状态申报-提交海关
     *
     * @param submitKeys EntryHead.IDs
     */
    @RequestMapping(value = "/submitCustomToStatus", method = RequestMethod.POST)
    public ResponseData submitCustomToStatus(@RequestParam(required = false) String submitKeys,
                                         HttpServletRequest request) {
        this.logger.info("运单状态申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的运单信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("dataStatus", StatusCode.YDZTSBZ);
        paramMap.put("dataStatusWhere", StatusCode.YDZTDSB + "," + StatusCode.YDZTCB+","+StatusCode.EXPORT);//可以申报的状态,支付单待申报,支付单重报,已导入
        paramMap.put("currentUserId", currentUser.getId());
        /* paramMap.put("enterpriseId", this.getCurrentUserEnterpriseId());*/  //暂时不获取企业id
        paramMap.put("submitKeys", submitKeys);//运单编号

        // 调用运单申报Service获取提交海关结果
        boolean flag = waybillService.updateSubmitWaybillToStatus(paramMap);
        if (flag) {
            return rtnResponse("true", "运单状态申报海关提交成功！");
        } else {
            return rtnResponse("false", "运单状态申报海关提交失败！");
        }
    }
}
