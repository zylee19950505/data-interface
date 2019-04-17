package com.xaeport.crossborder.controller.api.bondinvenmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.BuilderDetail;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.service.detaillistmanage.DetailBuilderService;
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

//清单查询
@RestController
@RequestMapping("/api/bondinvenmanage")
public class BondinvenBuilderApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DetailBuilderService detailBuilderService;
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    LoadData loadData;

    @RequestMapping("/queryDetailBuilder")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String logisticsNo,
            @RequestParam(required = false) String dataStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("清单查询参数:[orderNo:%s,orderNo:%s,logisticsNo:%s,dataStatus:%s]",orderNo,orderNo,logisticsNo,dataStatus));
        Map<String, String> paramMap = new HashMap<String, String>();
        //查询参数
        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("billNo", billNo);
        paramMap.put("orderNo", orderNo);
        paramMap.put("logisticsNo", logisticsNo);
        paramMap.put("dataStatus", dataStatus);
        paramMap.put("business_type", "BONDORDER");
        //分页参数
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        DataList<BuilderDetail> dataList;
        List<BuilderDetail> resultList;
        try {
            //查询列表
            resultList = this.detailBuilderService.queryBuilderDetailList(paramMap);
            //查询总数
            Integer count = this.detailBuilderService.queryBuilderDetailCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        }catch (Exception e){
            this.logger.error("获取生成清单数据表失败", e);
            return new ResponseData("获取生成清单数据表失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }

    /**
     * 生成清单
     **/
    @RequestMapping(value = "/builderDetail", method = RequestMethod.POST)
    public ResponseData builderDetail(
            @RequestParam(required = false) String submitKeys,
            HttpServletRequest request) {
        this.logger.info("生成清单客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要清单的信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("submitKeys", submitKeys);//订单编号
        Map<String, String> rtnMap = new HashMap<>() ;
        rtnMap = detailBuilderService.builderDetail(paramMap,currentUser);
        return new ResponseData(rtnMap);
    }

}
