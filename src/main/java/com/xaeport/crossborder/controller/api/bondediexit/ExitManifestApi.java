package com.xaeport.crossborder.controller.api.bondediexit;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondediexit.ExitManifestService;
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

@RestController
@RequestMapping("/api/bondediexit")
public class ExitManifestApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    ExitManifestService exitManifestService;

    /*
     * 查询出区核放单数据
     */
    @RequestMapping(value = "/queryExitManifest", method = RequestMethod.GET)
    public ResponseData queryExitManifest(
            @RequestParam(required = false) String dcl_time,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String return_status,
            @RequestParam(required = false) String passport_no,
            @RequestParam(required = false) String rlt_no,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询出区核放单条件参数:[dcl_time:%s,status:%s,return_status:%s,passport_no:%s,rlt_no:%s]", dcl_time, status, return_status, passport_no, rlt_no));
        Map<String, String> paramMap = new HashMap<String, String>();

        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);
        paramMap.put("dcl_time", dcl_time);
//        paramMap.put("status", status);
        paramMap.put("return_status", return_status);
        paramMap.put("passport_no", passport_no);
        paramMap.put("rlt_no", rlt_no);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        if (!StringUtils.isEmpty(status)) {
            paramMap.put("status", status);
        } else {
            paramMap.put("status", String.format("%s,%s,%s,%s,%s", StatusCode.CQHFDDSB, StatusCode.CQHFDSBZ, StatusCode.CQHFDYSB, StatusCode.CQHFDSBCG, StatusCode.SJDBC));
        }

        DataList<PassPortHead> dataList = null;
        List<PassPortHead> resultList = null;
        try {
            //查询列表
            resultList = this.exitManifestService.queryExitManifestData(paramMap);
            //查询总数
            Integer count = this.exitManifestService.queryExitManifestCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("出区核放单-查询出区核放单数据失败", e);
            return new ResponseData("出区核放单-获取出区核放单数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }


    //查询出区核放单数据详情信息
    @RequestMapping(value = "/exitmanifest", method = RequestMethod.GET)
    public ResponseData exitInventory(
            @RequestParam(required = false) String dataInfo
    ) {
        Map<String, String> paramMap = new HashMap<>();

        paramMap.put("etps_preent_no", dataInfo);

        PassPort passPort = new PassPort();
        PassPortHead passPortHead = new PassPortHead();
        List<PassPortAcmp> passPortAcmpList = new ArrayList<>();
        try {
            //查询列表
            passPortHead = this.exitManifestService.queryPassPortHead(paramMap);
            passPortAcmpList = this.exitManifestService.queryPassPortAcmp(paramMap);
            passPort.setPassPortHead(passPortHead);
            passPort.setPassPortAcmpList(passPortAcmpList);
        } catch (Exception e) {
            this.logger.error("获取出区核放单数据失败", e);
            return new ResponseData("获取出区核放单数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(passPort);
    }

    //修改出区核放单信息
    @RequestMapping("/updateExitManifest")
    public ResponseData updateExitManifest(@Param("entryJson") String entryJson) {
        //出区核注清单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        String dcl_etps_nm = (String) object.get("dcl_etps_nm");

        // 出区核注清单表头
        LinkedHashMap<String, String> passPortHead = (LinkedHashMap<String, String>) object.get("passPortHead");
        // 出区核注清单表体
        LinkedHashMap<String, String> passPortAcmpList = (LinkedHashMap<String, String>) object.get("passPortAcmpList");

        passPortHead.put("dcl_etps_nm", dcl_etps_nm);

        Users userInfo = this.getCurrentUsers();
        Map<String, String> rtnMap = new HashMap<>();

        try {
            // 保存详情信息
            rtnMap = exitManifestService.updateExitManifest(passPortHead, passPortAcmpList, userInfo);
        } catch (Exception e) {
            logger.error("修改出区核放单数据时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "修改出区核放单数据时发生异常");
        }
        return new ResponseData(rtnMap);
    }


    /**
     * 出区核放单申报-提交海关置为申报中状态
     **/
    @RequestMapping(value = "/exitmanifest/submitCustom", method = RequestMethod.POST)
    public ResponseData submitCustom(
            @RequestParam(required = false) String submitKeys,
            HttpServletRequest request
    ) {
        this.logger.info("出区核放单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的出区核放单信息！");
        }
        Users user = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("status", StatusCode.CQHFDSBZ);//申报中
        paramMap.put("statusWhere", StatusCode.CQHFDDSB);//待申报
        paramMap.put("userId", user.getId());
        paramMap.put("submitKeys", submitKeys);//清单唯一编码
        // 调用清单申报Service获取提交海关结果
        boolean flag;
        flag = exitManifestService.queryDataFull(paramMap);
        if (flag) {
            flag = exitManifestService.updateSubmitCustom(paramMap);
        }
        if (flag) {
            return rtnResponse("true", "出区核放单申报海关提交成功！");
        } else {
            return rtnResponse("false", "出区核放单申报海关提交失败！");
        }
    }

    //删除出区核放单数据操作
    @RequestMapping(value = "/exitmanifest/deleteExitManifest", method = RequestMethod.POST)
    public ResponseData deleteExitManifest(
            @RequestParam(required = false) String submitKeys
    ) {
        if (StringUtils.isEmpty(submitKeys)) return new ResponseData("未提交需要删除的数据", HttpStatus.FORBIDDEN);
        try {
            this.exitManifestService.deleteExitManifest(submitKeys, this.getCurrentUserEntId());
        } catch (Exception e) {
            this.logger.error("删除出区核放单失败，submitKeys=" + submitKeys, e);
            return new ResponseData("删除出区核放单失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData("");
    }

}
