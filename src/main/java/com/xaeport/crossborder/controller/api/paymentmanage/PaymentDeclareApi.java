package com.xaeport.crossborder.controller.api.paymentmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.service.paymentmanage.PaymentDeclareSevice;
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
            HttpServletRequest request

    ) {
        System.err.println("进入控制层。 获取数据为："+orderNo+"-------"+payTransactionId);
        this.logger.debug(String.format("查询邮件申报条件参数:[orderNo:%s,payTransactionId:%s]", orderNo, payTransactionId));

        Map<String, String> paramMap = new HashMap<String, String>();

        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("orderNo", orderNo);
        paramMap.put("payTransactionId", payTransactionId);

        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);

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


}
