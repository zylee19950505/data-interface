package com.xaeport.crossborder.controller.api.bondordermanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.ImpCrossBorderHead;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.data.status.VerifyType;
import com.xaeport.crossborder.service.bondordermanage.BondOrderQueryService;
import com.xaeport.crossborder.service.logic.BondLogicalService;
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

//保税订单逻辑校验
@RestController
public class BondOrderLogApi extends BaseApi {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    BondLogicalService bondlogicalService;
    @Autowired
    BondOrderQueryService bondOrderQueryService;
    @Autowired
    AppConfiguration appConfiguration;

    //逻辑校验列表查询
    @RequestMapping(value = "/bondorder/logical", method = RequestMethod.GET)
    public ResponseData getLogicalInventoryData(
            @RequestParam String bill_no,
            @RequestParam String order_no,
            @RequestParam String status,
            HttpServletRequest request
    ) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("bill_no", bill_no);
            map.put("order_no", order_no);
            map.put("data_status", StatusCode.BSYDR);
            map.put("type", VerifyType.LOGIC);
            map.put("status", status);
            map.put("entId", this.getCurrentUserEntId());
            map.put("roleId", this.getCurrentUserRoleId());

            List<ImpCrossBorderHead> orderLogicList = this.bondlogicalService.getOrderLogicData(map);
            return new ResponseData(orderLogicList);
        } catch (Exception e) {
            this.log.error("获取订单逻辑校验列表错误", e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }

    }

//    //保存清单信息
    @RequestMapping(value = "/bondorder/saveLogicalDetail")
    public ResponseData saveLogicalDetail(@Param("entryJson") String entryJson) {
        //订单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        // 订单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");

        // 订单表体
        ArrayList<LinkedHashMap<String, String>> entryLists = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");

        Map<String, String> rtnMap = new HashMap<>();
        try {
            // 保存详情信息
            rtnMap = bondOrderQueryService.saveLogicalDetail(entryHead, entryLists);
        } catch (Exception e) {
            log.error("保存订单详细信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存订单详细信息时发生异常");
        }
        return new ResponseData(rtnMap);
    }

    //逻辑校验删除运单
    @RequestMapping(value = "/bondorder/deleteLogical", method = RequestMethod.POST)
    public ResponseData deleteVerifyIdCard(String submitKeys) {
        if (StringUtils.isEmpty(submitKeys))
            return new ResponseData("未提交订单数据", HttpStatus.FORBIDDEN);
        try {
            this.bondlogicalService.deleteLogicalByOrder(submitKeys, this.getCurrentUserEntId());
        } catch (Exception e) {
            this.log.error("逻辑校验删除订单失败，submitKeys=" + submitKeys, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData("");
    }


}
