package com.xaeport.crossborder.controller.api.paymentmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.paymentmanage.PaymentDeclareSevice;
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


/*
 * 支付单模块
 */

@RestController
@RequestMapping("/api/paymentManage")
public class PaymentDeclareApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    PaymentDeclareSevice paymentDeclareSevice;
    /*
     * 支付单申报查询和支付单查询共用一个方法。
     *支付单申报查询：条件 没有 时间
     * 支付单查询：条件 有 时间
     */
    @RequestMapping("/queryPaymentDeclare")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String payTransactionId,
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s,payTransactionId:%s]",startFlightTimes, endFlightTimes, orderNo, payTransactionId));

        Map<String, String> paramMap = new HashMap<String, String>();

        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("orderNo", orderNo);
        paramMap.put("payTransactionId", payTransactionId);
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);

        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);
        // 固定参数
        paramMap.put("dataStatus", String.format("%s,%s,%s,%s,%s", StatusCode.ZFDDSB,StatusCode.ZFDSBZ, StatusCode.ZFDYSB, StatusCode.ZFDCB,StatusCode.EXPORT));

        paramMap.put("entId",this.getCurrentUserEntId());
        paramMap.put("roleId",this.getCurrentUserRoleId());

        DataList<ImpPayment> dataList = null;
        List<ImpPayment> resultList = null;
        try {
            //查询支付单列表
            resultList = this.paymentDeclareSevice.queryPaymentDeclareList(paramMap);
            //查询支付单总数
            Integer count = this.paymentDeclareSevice.queryPaymentDeclareCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询支付单申报数据失败", e);
            return new ResponseData("获取支付单申报数据错误", HttpStatus.BAD_REQUEST);
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
                                         HttpServletRequest request) {
        this.logger.info("支付单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的订单单信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("dataStatus", StatusCode.ZFDSBZ);
        paramMap.put("dataStatusWhere", StatusCode.ZFDDSB + "," + StatusCode.ZFDCB+","+StatusCode.EXPORT);//可以申报的状态,支付单待申报,支付单重报,已导入
        paramMap.put("currentUserId", currentUser.getId());

       /* paramMap.put("enterpriseId", this.getCurrentUserEnterpriseId());*/  //暂时不获取企业id
        paramMap.put("submitKeys", submitKeys);//订单遍号

        // 调用支付单申报Service获取提交海关结果
        boolean flag = paymentDeclareSevice.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "支付单申报海关提交成功！");
        } else {
            return rtnResponse("false", "支付单申报海关提交失败！");
        }
    }

    /**
     * 查询单条数据。
     * @param request
     * @return 返回的是一个实体对象。页面方便接收。
     */
    @RequestMapping(value = "/queryPaymentById", method = RequestMethod.POST)
    public ImpPayment queryPaymentById(HttpServletRequest request) {
        String paytransactionid = request.getParameter("paytransactionid");
        ImpPayment impPayment = null;
        try {
            impPayment = paymentDeclareSevice.queryPaymentById(paytransactionid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return impPayment;
    }

    /**
     * 支付单修改功能因还没有确定需求。暂存。
     * @param
     * @return
     */
    @RequestMapping(value = "/submitUpdata", method = RequestMethod.POST)
    public boolean submitUpdata(
            @RequestParam String GUID,
            @RequestParam String order_no,
            @RequestParam String pay_code,
            @RequestParam String pay_transaction_id,
            @RequestParam String pay_name,
            @RequestParam String ebp_code,
            @RequestParam String ebp_name,
            @RequestParam String payer_id_type,
            @RequestParam String payer_name,
            @RequestParam String note,
            @RequestParam String amount_paid,
            @RequestParam String payer_id_number,
            @RequestParam String pay_time,
            @RequestParam String data_status
    ) {
        System.err.println("进入api。。。。执行修改方法。");
        System.err.println("GUID:"+GUID);
        return true;
    }

}
