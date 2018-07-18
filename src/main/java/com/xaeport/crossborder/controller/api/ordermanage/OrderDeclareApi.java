package com.xaeport.crossborder.controller.api.ordermanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.OrderHeadAndList;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.ordermanage.OrderDeclareSevice;
import com.xaeport.crossborder.tools.GetIpAddr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
            //身份验证
            @RequestParam(required = false) String idCardValidate,
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String orderNo,
            //分页参数
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw
            //未获取的参数entryType和ie_flag
           /* , @RequestParam(required = false) String entryType,
            @RequestParam(required = false) String ieFlag*/
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[idCardValidate:%s,startFlightTimes:%s,endFlightTimes:%s,orderNo:%s,start:%s,length:%s]", idCardValidate, startFlightTimes, endFlightTimes, orderNo, start, length));
        Map<String, Object> paramMap = new HashMap<String, Object>();
        DataList<OrderHeadAndList> dataList = new DataList<>();
        //查询参数
        paramMap.put("idCardValidate", idCardValidate);
        paramMap.put("orderNo", orderNo);
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        //分页参数
        paramMap.put("start", Integer.parseInt(start)+1);
        paramMap.put("length", length);
        //类型参数
        /*paramMap.put("ieFlag",ieFlag);
        paramMap.put("entryType",entryType);*/
        // 固定参数
        paramMap.put("dsStatus", String.format("%s,%s,%s", StatusCode.DDDSB, StatusCode.DDYSB, StatusCode.DDCB));

        //更新人
        paramMap.put("UPD_ID",this.getCurrentUsers().getId());
        List<OrderHeadAndList> resultList = new ArrayList<OrderHeadAndList>();
        try {
            //查询列表
            resultList = orderDeclareService.queryOrderDeclareList(paramMap);
            //查询总数
            Integer count = orderDeclareService.queryOrderDeclareCount(paramMap);
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("查询订单申报数据失败", e);
            return new ResponseData(dataList);
        }
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
        //this.log.debug(String.format("舱单申报-提交海关舱单Keys：%s", submitKeys));
        this.logger.info("订单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的订单单信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("opStatus", StatusCode.DDSBZ);//提交海关后,状态改为订单申报中,逻辑校验在这个之前
        paramMap.put("opStatusWhere", StatusCode.DDDSB + "," + StatusCode.DDCB+","+StatusCode.EXPORT);//可以申报的状态,订单待申报,订单重报,已经导入
        paramMap.put("currentUserId", currentUser.getId());

       /* paramMap.put("enterpriseId", this.getCurrentUserEnterpriseId());*/  //暂时不获取企业id
        paramMap.put("submitKeys", submitKeys);//订单遍号
        paramMap.put("idCardValidate", idCardValidate);
        paramMap.put("entryType", entryType);
        paramMap.put("ieFlag", ieFlag);

        // 调用订单申报Service 获取提交海关结果
        boolean flag = orderDeclareService.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "舱单申报海关提交成功！");
        } else {
            return rtnResponse("false", "舱单申报海关提交失败！");
        }
    }

}
