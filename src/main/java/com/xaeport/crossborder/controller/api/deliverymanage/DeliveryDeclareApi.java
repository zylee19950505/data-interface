package com.xaeport.crossborder.controller.api.deliverymanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpDelivery;
import com.xaeport.crossborder.data.entity.ImpDeliveryHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.deliverymanage.DeliveryDeclareService;
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
@RequestMapping("/api/deliveryManage")
public class DeliveryDeclareApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DeliveryDeclareService deliveryDeclService;



    /*
     *  入库明细申报查询
     */
    @RequestMapping("/queryDeliveryDeclare")
    public ResponseData queryDeliveryDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String dataStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s,dataStatus:%s]", startFlightTimes, endFlightTimes, billNo, dataStatus));
        Map<String, String> paramMap = new HashMap<String, String>();

        //查询参数
        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("billNo", billNo);
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        if(!StringUtils.isEmpty(dataStatus)){
            paramMap.put("dataStatus",dataStatus);
        }else {
            paramMap.put("dataStatus", String.format("%s,%s,%s,%s,%s,%s", StatusCode.RKMXDDSB, StatusCode.RKMXDSBZ,StatusCode.RKMXDYSB, StatusCode.RKMXDSBCG,StatusCode.EXPORT,StatusCode.RKMXDCB));
        }

        //分页参数
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);

        paramMap.put("entId",this.getCurrentUserEntId());
        paramMap.put("roleId",this.getCurrentUserRoleId());

        //更新人
        DataList<ImpDelivery> dataList = null;
        List<ImpDelivery> resultList = null;
        try {
            //查询列表
            resultList = this.deliveryDeclService.queryDeliveryDeclareList(paramMap);
            //查询总数
            Integer count = this.deliveryDeclService.queryDeliveryDeclareCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询入库明细单申报数据失败", e);
            return new ResponseData("获取入库明细单申报数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);

    }


    /**
     * 订单单申报-提交海关
     *
     * @param submitKeys EntryHead.IDs
     */
    @RequestMapping(value = "/submitCustom", method = RequestMethod.POST)
    public ResponseData saveSubmitCustom(@RequestParam(required = false) String submitKeys,
                                         @RequestParam(required = false) String idCardValidate,
                                         @RequestParam(required = false) String ieFlag,
                                         @RequestParam(required = false) String entryType,
                                         HttpServletRequest request) {
        this.logger.info("入库明细单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的入库明细单信息！");
        }

        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("dataStatus", StatusCode.RKMXDSBZ);//提交海关后,状态改为订单申报中,逻辑校验在这个之前
        paramMap.put("dataStatusWhere", StatusCode.RKMXDDSB + "," + StatusCode.RKMXDCB + "," + StatusCode.EXPORT);//可以申报的状态,订单待申报,订单重报,已经导入
        paramMap.put("userId", currentUser.getId());

        paramMap.put("submitKeys", submitKeys);
        paramMap.put("idCardValidate", idCardValidate);
        paramMap.put("entryType", entryType);
        paramMap.put("ieFlag", ieFlag);

        // 调用订单申报Service 获取提交海关结果

        this.deliveryDeclService.setDeliveryData(this.getCurrentUserEntId(),submitKeys);
        boolean flag = this.deliveryDeclService.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "入库明细单申报海关提交成功！");
        } else {
            return rtnResponse("false", "入库明细单申报海关提交失败！");
        }
    }


}
