package com.xaeport.crossborder.controller.api.bondedienter;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondedienter.EnterManifestService;
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


@RestController
@RequestMapping("/api/enterManifest")
public class EnterManifestApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    EnterManifestService enterManifestService;

    /*
     * 入区核注清单查询
     */
    @RequestMapping("/queryEnterManifest")
    public ResponseData queryEnterManifest(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String bond_invt_no,//核注清单编号
            @RequestParam(required = false) String passport_declareStatus,//核放单申报状态
            @RequestParam(required = false) String passport_dataStatus,//核放单回执状态
            @RequestParam(required = false) String passport_no,//核放单编号
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw
    ) {
        this.logger.debug(String.format("入区核注清单:[startFlightTimes:%s,bond_invt_no:%s,passport_declareStatus:%s,passport_dataStatus:%s,passport_no:%s]", startFlightTimes, bond_invt_no, passport_declareStatus, passport_dataStatus, passport_no));
        Map<String, String> paramMap = new HashMap<String, String>();
        //查询参数
        paramMap.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        paramMap.put("bond_invt_no", bond_invt_no);
        paramMap.put("passport_declareStatus", passport_declareStatus);
        paramMap.put("passport_dataStatus", passport_dataStatus);
        paramMap.put("passport_no", passport_no);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());
        paramMap.put("start", String.valueOf(Integer.parseInt(start) + 1));
        paramMap.put("length", length);

        DataList<PassPortHead> dataList = new DataList<>();
        List<PassPortHead> resultList;
        try {
            //查询列表
            resultList = enterManifestService.queryEnterManifest(paramMap);
            //查询总数
            Integer count = enterManifestService.queryEnterManifestCount(paramMap);
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("查询入区核放单数据失败", e);
            return new ResponseData("查询入区核放单数据失败", HttpStatus.BAD_REQUEST);
        }
    }

    //删除核放单
    @RequestMapping(value = "/deleteEnterManifest", method = RequestMethod.POST)
    public ResponseData deleteEnterManifest(
            String submitKeys
    ) {
        if (StringUtils.isEmpty(submitKeys)) return new ResponseData("未提交数据", HttpStatus.FORBIDDEN);

        try {
            this.enterManifestService.deleteEnterManifest(submitKeys, this.getCurrentUsers());
        } catch (Exception e) {
            this.logger.error("删除入区核放单失败，submitKeys=" + submitKeys, e);
            return new ResponseData("删除入区核放单失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData("");
    }

    /**
     * 核放单申报-提交海关
     **/
    @RequestMapping(value = "/submitCustom", method = RequestMethod.POST)
    public ResponseData saveSubmitCustom(
            @RequestParam(required = false) String submitKeys,
            HttpServletRequest request
    ) {
        this.logger.info("核放单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的提交的核放单信息！");
        }
        Users user = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("status", StatusCode.RQHFDSBZ);//申报中
        paramMap.put("statusWhere", StatusCode.RQHFDDSB);//待申报
        paramMap.put("userId", user.getId());
        paramMap.put("submitKeys", submitKeys);
        boolean flag = enterManifestService.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "入区核放单申报海关提交成功！");
        } else {
            return rtnResponse("false", "入区核放单申报海关提交失败！");
        }
    }

    /**
     * 点击查看核放单详情
     */
    @RequestMapping(value = "/seeEnterPassportDetail", method = RequestMethod.GET)
    public ResponseData seeEnterPassportDetail(
            @RequestParam(required = false) String etps_preent_no
    ) {
        if (StringUtils.isEmpty(etps_preent_no)) return new ResponseData("数据为空", HttpStatus.FORBIDDEN);
        this.logger.debug(String.format("点击查看核放单详情条件参数:[etps_preent_no:%s]", etps_preent_no));
        PassPort passPort = new PassPort();
        try {
            PassPortHead passPortHead;
            List<PassPortList> passPortLists;
            passPortHead = enterManifestService.getImpPassportHead(etps_preent_no);
            passPortLists = enterManifestService.getImpPassportList(passPortHead.getId());
            passPort.setPassPortHead(passPortHead);
            passPort.setPassPortList(passPortLists);
        } catch (Exception e) {
            this.logger.error("点击查看核放单详情信息失败，etps_preent_no =" + etps_preent_no, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(passPort);
    }
}
