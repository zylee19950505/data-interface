package com.xaeport.crossborder.controller.api.bondinvenmanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryDetail;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondinvenmanage.BondinvenQueryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/bondinvenmanage")
public class BondinvenQueryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    BondinvenQueryService bondinvenQueryService;
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    LoadData loadData;

    //查询保税清单数据
    @RequestMapping("/querybondinvenquery")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String logisticsNo,
            @RequestParam(required = false) String preNo,
            @RequestParam(required = false) String invtNo,
            @RequestParam(required = false) String returnStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询保税清单数据条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s,logisticsNo:%s,preNo:%s,invtNo:%s,returnStatus:%s]", startFlightTimes, endFlightTimes, orderNo, logisticsNo, preNo, invtNo, returnStatus));
        Map<String, String> paramMap = new HashMap<String, String>();
        //分页参数
        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        //查询参数
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        paramMap.put("orderNo", orderNo);
        paramMap.put("logisticsNo", logisticsNo);
        paramMap.put("preNo", preNo);
        paramMap.put("invtNo", invtNo);

        //分页参数
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);
        paramMap.put("business_type", SystemConstants.T_IMP_BOND_INVEN);

        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());
        //类型参数
        paramMap.put("dataStatus", StatusCode.BSQDSBCG);
        if (!StringUtils.isEmpty(returnStatus)) {
            paramMap.put("returnStatus", returnStatus);
        } else {
            paramMap.put("returnStatus", "");
        }

        //更新人
        DataList<ImpInventory> dataList = null;
        List<ImpInventory> resultList = null;
        try {
            //查询列表
            resultList = this.bondinvenQueryService.queryBondInvenQueryData(paramMap);
            //查询总数
            Integer count = this.bondinvenQueryService.queryBondInvenQueryCount(paramMap);
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

//    @RequestMapping("/seebondinvendetail")
//    public ResponseData seeOrderDetail(
//            @RequestParam(required = false) String guid
//    ) {
//        if (StringUtils.isEmpty(guid)) return new ResponseData("订单为空", HttpStatus.FORBIDDEN);
//        this.logger.debug(String.format("查询保税清单条件参数:[guid:%s]", guid));
//        ImpInventoryDetail impInventoryDetail;
//        try {
//            impInventoryDetail = bondinvenQueryService.getImpInventoryDetail(guid);
//        } catch (Exception e) {
//            this.logger.error("查询保税清单信息失败，guid=" + guid, e);
//            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseData(impInventoryDetail);
//    }

    //查询报税清单回执信息
    @RequestMapping("/seebondinvenrec")
    public ResponseData seeInventoryRec(
            @RequestParam(required = false) String guid
    ) {
        if (StringUtils.isEmpty(guid)) return new ResponseData("订单为空", HttpStatus.FORBIDDEN);
        this.logger.debug(String.format("查询保税清单条件参数:[guid:%s]", guid));
        ImpInventoryHead impInventoryHead;
        try {
            impInventoryHead = bondinvenQueryService.getImpBondInvenRec(guid);
        } catch (Exception e) {
            this.logger.error("查询保税清单回执信息失败，guid =" + guid, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(impInventoryHead);
    }

    //保存保税清单信息
    @RequestMapping("/savebondinvenafter")
    public ResponseData saveInventoryDetail(@Param("entryJson") String entryJson) {
        //保税清单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        //保税清单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");
        //保税清单表体
        ArrayList<LinkedHashMap<String, String>> entryLists = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");

        Map<String, String> rtnMap = new HashMap<>();
        try {
            //保存详情信息
            rtnMap = bondinvenQueryService.saveBondInvenAfter(entryHead, entryLists);
        } catch (Exception e) {
            logger.error("保存保税清单详细时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存保税清单详细时发生异常");
        }
        return new ResponseData(rtnMap);
    }

}
