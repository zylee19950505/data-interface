package com.xaeport.crossborder.controller.api.booksandrecords;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.BwlListType;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.service.booksandrecords.AccountQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/*
 * 订单申报
 */
@RestController
@RequestMapping("/api/booksandrecords")
public class AccountQueryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AccountQueryService accountQueryService;

    @RequestMapping("/accountquery")
    public ResponseData queryOrderHeadList(
            @RequestParam(required = false) String gds_seqno,
            @RequestParam(required = false) String gds_mtno,
            @RequestParam(required = false) String gdecd,
            @RequestParam(required = false) String gds_nm,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("账册查询条件参数:[gds_seqno:%s,gds_mtno:%s,gdecd:%s,gds_nm:%s]", gds_seqno, gds_mtno, gdecd, gds_nm));
        Map<String, String> paramMap = new HashMap<String, String>();

        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("gds_seqno", gds_seqno);
        paramMap.put("gds_mtno", gds_mtno);
        paramMap.put("gdecd", gdecd);
        paramMap.put("gds_nm", gds_nm);

        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        //分页参数
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);

        DataList<BwlListType> dataList = new DataList<BwlListType>();
        List<BwlListType> resultList = new ArrayList<BwlListType>();
        try {
            //查询账册表体数据
            resultList = accountQueryService.queryBwlListTypeData(paramMap);
            //查询账册表体数据总数
            Integer count = accountQueryService.queryBwlListTypeCount(paramMap);
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("账册查询数据失败", e);
            return new ResponseData("获取账册查询数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }
}



