package com.xaeport.crossborder.controller.api.deliverymanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.ImpDelivery;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.deliverymanage.DeliveryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/deliveryManage")
public class DeliveryQueryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DeliveryQueryService deliveryQueryService;

    /*
     *  入库明细申报查询
     */
    @RequestMapping("/queryDeliveryQuery")
    public ResponseData queryDeliveryDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String returnStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s,returnStatus:%s]", startFlightTimes, endFlightTimes, billNo, returnStatus));
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("billNo", billNo);
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        paramMap.put("dataStatus", StatusCode.RKMXDSBCG);
        paramMap.put("returnStatus", returnStatus);

        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        //更新人
        List<ImpDelivery> resultList = null;
        try {
            //查询列表
            resultList = this.deliveryQueryService.queryDeliveryQueryList(paramMap);
        } catch (Exception e) {
            this.logger.error("查询入库明细单数据失败", e);
            return new ResponseData("查询入库明细单数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);

    }

}
