package com.xaeport.crossborder.controller.api.manifest;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ManifestHead;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.manifest.ManifestManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manifestManage")
public class ManifestManageApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    ManifestManageService manifestManageService;

    /*
     *  核放单数据查询
     */
    @RequestMapping("/queryManifestManage")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String manifestNo,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询核放单管理条件参数:[startFlightTimes:%s,endFlightTimes:%s,manifestNo:%s]", startFlightTimes, endFlightTimes, manifestNo));
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        paramMap.put("manifestNo", manifestNo);
        paramMap.put("dataStatus", StatusCode.HFDDSB);
        paramMap.put("start", String.valueOf(Integer.parseInt(start) + 1));
        paramMap.put("length", length);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());
        List<ManifestHead> resultList = new ArrayList<ManifestHead>();
        DataList<ManifestHead> dataList = new DataList<ManifestHead>();
        try {
            resultList = manifestManageService.queryManifestManageList(paramMap);
            Integer count = manifestManageService.queryManifestManageCount(paramMap);
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("核放单管理查询失败", e);
            return new ResponseData(dataList);
        }
    }

    /*
    * 核放单申报
    * */
    @RequestMapping(value = "/manifestDeclare", method = RequestMethod.POST)
    public ResponseData manifestDeclare(
            @RequestParam(required = false) String manifestNo,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("核放单申报条件参数:[manifestNo:%s]", manifestNo));
        if (StringUtils.isEmpty(manifestNo)) {
            return rtnResponse("false", "核放单号不能为空");
        }
        boolean flag = this.manifestManageService.manifestDeclare(manifestNo);
        if (flag) {
            return rtnResponse("true", "核放单申报成功！");
        } else {
            return rtnResponse("false", "核放单申报失败！");
        }
    }

    //核放单删除
    @RequestMapping(value = "/manifestDelete/{manifest_no}", method = RequestMethod.DELETE)
    public ResponseData manifestDelete(
            @PathVariable(value = "manifest_no") String manifest_no
    ) {
        this.logger.debug(String.format("核放单删除[manifest_no:%s]", manifest_no));
        this.manifestManageService.updateCheckGoodsInfo(manifest_no);
        this.manifestManageService.manifestDelete(manifest_no);
        return new ResponseData();
    }

}
