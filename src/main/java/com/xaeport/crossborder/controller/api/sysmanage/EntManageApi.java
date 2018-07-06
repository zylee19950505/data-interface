package com.xaeport.crossborder.controller.api.sysmanage;

import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

/**
 * Created by wx on 2018/4/27.
 */

@RestController
public class EntManageApi extends BaseApi{
    private Log logger = LogFactory.getLog(this.getClass());

    //系统日志—查询数据
    @RequestMapping(value = "/syslog",method = RequestMethod.GET)
    public ResponseData querySysLog(
            @RequestParam String startFlightTimes,
            @RequestParam String endFlightTimes,
            @RequestParam String module,
            HttpServletRequest request
    ){
        this.logger.debug(String.format("开始进行系统日志数据查询"));
        Map<String, String> paramMap = new HashMap<String, String>();

        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        paramMap.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);
        paramMap.put("module",module);

        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);

        DataList<Map<String, String>> dataList = null;
        List<Map<String, String>> sysLogList = null;

        try{
//            List<SysLog> sysLogList = this.sysLogSerivce.querySysLog(paramMap);
//            return new ResponseData(sysLogList);
          /*  sysLogList = this.sysLogSerivce.querySysLog(paramMap);
            int count = this.sysLogSerivce.querySysLogCount(paramMap);*/
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(sysLogList);
           /* dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);*/
        }catch (Exception e){
            this.logger.debug("获取系统日志数据时发生异常",e);
            return new ResponseData("获取系统日志数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }


}
