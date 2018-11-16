package com.xaeport.crossborder.controller.api.paymentmanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.ImpCrossBorderHead;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.data.status.VerifyType;
import com.xaeport.crossborder.service.logic.LogicalService;
import com.xaeport.crossborder.service.ordermanage.OrderQueryService;
import com.xaeport.crossborder.service.paymentmanage.PaymentQueryService;
import org.apache.ibatis.annotations.Param;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
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
public class PaymentLogicApi extends BaseApi {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    LogicalService logicalService;
    @Autowired
    PaymentQueryService paymentQueryService;
    @Autowired
    AppConfiguration appConfiguration;

    //逻辑校验列表查询
    @RequestMapping(value = "/payment/logical", method = RequestMethod.GET)
    public ResponseData getLogicalInventoryData(
            @RequestParam String order_no,
            @RequestParam String pay_transaction_id,
            @RequestParam String status,
            HttpServletRequest request
    ) {
        try {
            Map<String,String> map = new HashMap<>();
            map.put("order_no",order_no);
            map.put("pay_transaction_id",pay_transaction_id);
            map.put("status",status);
            map.put("data_status", StatusCode.EXPORT);
            map.put("type", VerifyType.LOGIC);
            map.put("entId", this.getCurrentUserEntId());
            map.put("roleId", this.getCurrentUserRoleId());

            List<ImpCrossBorderHead> paymentLogicList = this.logicalService.getPaymentLogicData(map);
            return new ResponseData(paymentLogicList);
        } catch (Exception e) {
            this.log.error("获取支付单逻辑校验列表错误", e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }

    }

    //保存支付单信息
    @RequestMapping(value = "/payment/saveLogicalDetail")
    public ResponseData saveLogicalDetail(@Param("entryJson") String entryJson) {
        //支付单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        //支付单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");

        Map<String, String> rtnMap = new HashMap<>();
        try {
            // 保存详情信息
            rtnMap = paymentQueryService.saveLogicalDetail(entryHead);
        } catch (Exception e) {
            log.error("保存支付单详细信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存支付单详细信息时发生异常");
        }
        return new ResponseData(rtnMap);
    }

    //逻辑校验删除运单
    @RequestMapping(value = "/payment/deleteLogical", method = RequestMethod.POST)
    public ResponseData deleteVerifyIdCard(String submitKeys) {
        if (StringUtils.isEmpty(submitKeys))
            return new ResponseData("未提交支付单数据", HttpStatus.FORBIDDEN);
        try {
            this.logicalService.deleteLogicalByPayment(submitKeys, this.getCurrentUserEntId());
        } catch (Exception e) {
            this.log.error("逻辑校验删除支付单失败，submitKeys=" + submitKeys, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData("");
    }




}
