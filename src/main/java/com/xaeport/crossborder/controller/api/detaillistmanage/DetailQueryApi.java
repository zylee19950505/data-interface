package com.xaeport.crossborder.controller.api.detaillistmanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryDetail;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.detaillistmanage.DetailQueryService;
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
@RequestMapping("/api/detailManage")
public class DetailQueryApi extends BaseApi{

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DetailQueryService detailQueryService;

    /*
 * 邮件申报查询
 */
    @RequestMapping("/queryDetailQuery")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String orderNo,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s]",startFlightTimes, endFlightTimes, orderNo));
        Map<String, String> paramMap = new HashMap<String, String>();
        //查询参数
        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("orderNo", orderNo);
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        //分页参数
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);

        paramMap.put("entId",this.getCurrentUserEntId());
        paramMap.put("roleId",this.getCurrentUserRoleId());
        //类型参数
        paramMap.put("dataStatus", StatusCode.QDSBCG);

        //更新人
        DataList<ImpInventory> dataList = null;
        List<ImpInventory> resultList = null;
        try {
            //查询列表
            resultList = this.detailQueryService.queryInventoryQueryList(paramMap);
            //查询总数
            Integer count = this.detailQueryService.queryInventoryQueryCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询清单申报数据失败", e);
            return new ResponseData("获取清单申报数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }

    /*
* 点击查看邮件详情
* */
    @RequestMapping("/seeOrderDetail")
    public ResponseData seeOrderDetail(
            @RequestParam(required = false) String guid
    ) {
        if (StringUtils.isEmpty(guid)) return new ResponseData("订单为空", HttpStatus.FORBIDDEN);
        this.logger.debug(String.format("查询邮件条件参数:[guid:%s]",guid));
        ImpInventoryDetail impInventoryDetail;
        try {
            impInventoryDetail = detailQueryService.getImpInventoryDetail(guid);
        } catch (Exception e) {
            this.logger.error("查询分单信息失败，entryHeadId=" + guid, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(impInventoryDetail);
    }

    //保存清单信息
    @RequestMapping("/saveInventoryDetail")
    public ResponseData saveInventoryDetail(@Param("entryJson") String entryJson){
        //清单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        // 清单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");

        // 清单表体
        ArrayList<LinkedHashMap<String, String>> entryLists = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");

        Map<String, String> rtnMap = new HashMap<>();
        try {
            // 保存详情信息
            rtnMap = detailQueryService.saveInventoryDetail(entryHead, entryLists);
        } catch (Exception e) {
            logger.error("保存清单详细信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存清单详细信息时发生异常");
        }
        return new ResponseData(rtnMap);
    }
















}
