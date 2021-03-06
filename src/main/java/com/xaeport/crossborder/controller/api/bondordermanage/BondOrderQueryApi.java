package com.xaeport.crossborder.controller.api.bondordermanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.Order;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondordermanage.BondOrderQueryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

//保税订单查询
@RestController
@RequestMapping("/api/bondordermanage/queryOrder")
public class BondOrderQueryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    BondOrderQueryService bondOrderQueryService;

    //数据查询
    @RequestMapping("/queryOrderHeadList")
    public ResponseData queryOrderHeadList(
            @RequestParam(required = false) String startDeclareTime,
            @RequestParam(required = false) String endDeclareTime,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw
    ) {
        this.logger.debug(String.format("订单查询参数:[startDeclareTime:%s,endDeclareTime:%s,orderNo:%s,billNo:%s,orderStatus:%s,start:%s,length:%s,drew:%s]", startDeclareTime, endDeclareTime, orderNo, billNo, orderStatus, start, length, draw));

        Map<String, String> paramMap = new HashMap<String, String>();
        DataList<ImpOrderHead> dataList = new DataList<ImpOrderHead>();

        paramMap.put("startDeclareTime", startDeclareTime);
        paramMap.put("endDeclareTime", endDeclareTime);
        paramMap.put("orderNo", orderNo);
        paramMap.put("billNo", billNo);
        paramMap.put("orderStatus", orderStatus);
        paramMap.put("start", String.valueOf(Integer.parseInt(start) + 1));
        paramMap.put("length", length);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());
        //查询只查申报成功的
        paramMap.put("dataStatus", StatusCode.BSDDSBCG);

        List<ImpOrderHead> resultList;
        try {
            resultList = bondOrderQueryService.queryOrderHeadList(paramMap);
            //查询总数
            Integer count = bondOrderQueryService.queryOrderHeadListCount(paramMap);
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("订单查询失败", e);
            return new ResponseData(dataList);
        }
    }


    /*
     * 查询表体
     * 点击查看详情
     */
    @RequestMapping("/queryOrderBodyList")
    public ResponseData queryOrderBodyList(
            @RequestParam(required = false) String guid,
            @RequestParam(required = false) String orderNo
    ) {
        this.logger.debug(String.format("查询订单表体参数:[guid:%s,orderNo:%s]", guid, orderNo));
        Map<String, String> paramMap = new HashMap<String, String>();
        DataList<ImpOrderBody> dataList = new DataList<ImpOrderBody>();
        paramMap.put("guid", guid);
        paramMap.put("orderNo", orderNo);
        List<ImpOrderBody> resultList;
        try {
            resultList = bondOrderQueryService.queryOrderBodyList(paramMap);
            //查询总数
            dataList.setData(resultList);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("订单查询失败", e);
            return new ResponseData(dataList);
        }
    }

    /*
    * 点击查看详情
    * */
    @RequestMapping("/seeBondOrderDetail")
    public ResponseData seeOrderDetail(
            @RequestParam(required = false) String guid
    ) {
        if (StringUtils.isEmpty(guid)) return new ResponseData("订单为空", HttpStatus.FORBIDDEN);
        this.logger.debug(String.format("查询邮件条件参数:[guid:%s]", guid));
        Order order;
        try {
            order = bondOrderQueryService.getOrderDetail(guid);
        } catch (Exception e) {
            this.logger.error("查询订单信息失败，entryHeadId=" + guid, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(order);
    }

    /*
    * 保存编辑订单信息
    * saveOrderDetail
    * */
    @RequestMapping("/saveBondOrderDetail")
    public ResponseData saveOrderDetail(@Param("entryJson") String entryJson) {
        //订单信息json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        // 订单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");
        // 订单表体
        ArrayList<LinkedHashMap<String, String>> entryLists = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");

        Map<String, String> rtnMap = new HashMap<>();
        try {
            // 保存订单详情信息
            rtnMap = bondOrderQueryService.saveOrderDetail(entryHead, entryLists);
        } catch (Exception e) {
            logger.error("保存订单详细信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存订单详细信息时发生异常");
        }
        return new ResponseData(rtnMap);
    }

    /*
    * 点击查看订单回执详情
    * */
    @RequestMapping("/returnOrderDetail")
    public ResponseData returnOrderDetail(
            @RequestParam(required = false) String guid,
            @RequestParam(required = false) String orderNo
    ) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("guid", guid);
        paramMap.put("orderNo", orderNo);
        ImpOrderHead impOrderHead;
        try {
            impOrderHead = bondOrderQueryService.returnOrderDetail(paramMap);
        } catch (Exception e) {
            this.logger.error("查询回执详情信息失败，order_no=" + orderNo, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(impOrderHead);
    }

}



