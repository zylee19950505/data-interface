package com.xaeport.crossborder.controller.api.bondinvenmanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryDetail;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondinvenmanage.BondinvenDeclareService;
import com.xaeport.crossborder.tools.GetIpAddr;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/*
 **清单申报
 */

@RestController
@RequestMapping("/api/bondinvenmanage")
public class BondinvenDeclareApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    BondinvenDeclareService bondinvenDeclareService;

    /*
     * 邮件申报查询
     */
    @RequestMapping("/querybondinvendeclare")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String logisticsNo,
            @RequestParam(required = false) String dataStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询保税清单申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s,logisticsNo:%s,dataStatus:%s]", startFlightTimes, endFlightTimes, orderNo, logisticsNo, dataStatus));
        Map<String, String> paramMap = new HashMap<String, String>();

        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        paramMap.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);
        paramMap.put("orderNo", orderNo);
        paramMap.put("logisticsNo", logisticsNo);

        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);

        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        if (!StringUtils.isEmpty(dataStatus)) {
            paramMap.put("dataStatus", dataStatus);
        } else {
            paramMap.put("dataStatus", String.format("%s,%s,%s", StatusCode.BSQDDSB, StatusCode.BSQDSBZ, StatusCode.BSQDYSB));
        }

        //更新人
        DataList<ImpInventory> dataList = null;
        List<ImpInventory> resultList = null;
        try {
            //查询数据列表
            resultList = this.bondinvenDeclareService.queryBondInvenDeclareList(paramMap);
            //查询数据总数
            Integer count = this.bondinvenDeclareService.queryBondInvenDeclareCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询保税清单申报数据失败", e);
            return new ResponseData("获取保税清单申报数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }

    /**
     * 清单申报-提交海关
     **/
    @RequestMapping(value = "/submitCustom", method = RequestMethod.POST)
    public ResponseData saveSubmitCustom(
            @RequestParam(required = false) String submitKeys,
            HttpServletRequest request
    ) {
        this.logger.info("保税清单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的保税清单信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("dataStatus", StatusCode.BSQDSBZ);//保税清单申报中
        paramMap.put("dataStatusWhere", StatusCode.BSQDDSB);//保税清单待申报
        paramMap.put("currentUserId", currentUser.getId());
        paramMap.put("userId", currentUser.getId());

        paramMap.put("submitKeys", submitKeys);//提运单号
        boolean flag = bondinvenDeclareService.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "保税清单申报海关提交成功！");
        } else {
            return rtnResponse("false", "保税清单申报海关提交失败！");
        }
    }

    @RequestMapping("/seebondinvendetail")
    public ResponseData seeOrderDetail(
            @RequestParam(required = false) String guid
    ) {
        if (StringUtils.isEmpty(guid)) return new ResponseData("订单为空", HttpStatus.FORBIDDEN);
        this.logger.debug(String.format("查询保税清单条件参数:[guid:%s]", guid));
        ImpInventoryDetail impInventoryDetail;
        try {
            impInventoryDetail = bondinvenDeclareService.seeBondInvenDetail(guid);
        } catch (Exception e) {
            this.logger.error("查询保税清单信息失败，guid=" + guid, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(impInventoryDetail);
    }

    //保存清单信息
    @RequestMapping("/savebondinvenbefore")
    public ResponseData saveInventoryDetail(@Param("entryJson") String entryJson) {
        //清单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        // 清单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");

        // 清单表体
        ArrayList<LinkedHashMap<String, String>> entryLists = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");

        Map<String, String> rtnMap = new HashMap<>();
        try {
            // 保存详情信息
            rtnMap = bondinvenDeclareService.saveBondInvenBefore(entryHead, entryLists);
        } catch (Exception e) {
            logger.error("保存保税清单详细信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存保税清单详细信息时发生异常");
        }
        return new ResponseData(rtnMap);
    }

}
