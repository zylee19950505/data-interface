package com.xaeport.crossborder.controller.api.paymentmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.service.parametermanage.PaymentDeclareSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * 支付单申报
 */

@RestController
@RequestMapping("/api/paymentManage")
public class PaymentDeclareApi extends BaseApi {


    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    PaymentDeclareSevice paymentDeclareSevice;
    /*
     * 支付单申报查询
     */
    @RequestMapping("/queryPaymentDeclare")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String payTransactionId,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw
    ) {
        System.err.println("进入控制层。 获取数据为："+orderNo+"-------"+payTransactionId);
        this.logger.debug(String.format("查询邮件申报条件参数:[orderNo:%s,payTransactionId:%s,start:%s,length:%s]", orderNo, payTransactionId,start, length));

        Map<String, Object> paramMap = new HashMap<String, Object>();
        DataList<List<Map<String, String>>> dataList = new DataList<List<Map<String, String>>>();
        paramMap.put("orderNo", orderNo);
        paramMap.put("payTransactionId", payTransactionId);
        paramMap.put("start", start);
        paramMap.put("length", length);
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        try {
            //查询支付单列表
            resultList = paymentDeclareSevice.queryPaymentDeclareList(paramMap);
            //查询支付单总数
            Integer count = paymentDeclareSevice.queryPaymentDeclareCount(paramMap);
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("查询支付单申报数据失败", e);
            return new ResponseData(dataList);
        }
    }


}
