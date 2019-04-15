package com.xaeport.crossborder.controller.api.bondediexit;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondediexit.CrtExitEmptyService;
import com.xaeport.crossborder.tools.GetIpAddr;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/crtexitempty")
public class CrtExitEmptyApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CrtExitEmptyService crtExitEmptyService;

    //查询出区空车核放单数据
    @RequestMapping("/querypassport")
    public ResponseData queryCrtEnterManifest(
            @RequestParam(required = false) String vehicle_no,//车牌号
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("新建出区空车核放单查询条件参数:[vehicle_no:%s]", vehicle_no));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("vehicle_no", vehicle_no);
        paramMap.put("passport_typecd", "6");
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("draw", draw);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        DataList<PassPortHead> dataList = new DataList<>();
        List<PassPortHead> resultList;
        try {
            //查找可用的大于0,和enter的
            resultList = this.crtExitEmptyService.querExitEmptyPassportList(paramMap);
            Integer count = this.crtExitEmptyService.queryExitEmptyPassportCount(paramMap);
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("查询出区核放单数据失败", e);
            return new ResponseData("查询出区核放单数据失败", HttpStatus.BAD_REQUEST);
        }
    }

    //保存出区空车核放单数据
    @RequestMapping(value = "savenewpassport")
    public ResponseData saveExitEmptyInfo(@Param("entryJson") String entryJson) {
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        Map<String, String> rtnMap = new HashMap<>();
        Users users = this.getCurrentUsers();
        try {
            // 保存表头信息和关联单信息
            rtnMap = crtExitEmptyService.saveExitEmptyInfo(object, users);
        } catch (Exception e) {
            this.logger.error("保存出区空车核放单时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存出区空车核放单时发生异常");
        }
        return new ResponseData(rtnMap);
    }

    //修改出区空车核放单数据
    @RequestMapping(value = "updatepassport")
    public ResponseData updatepassport(
            @Param("entryJson") String entryJson
    ) {
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        Map<String, String> rtnMap = new HashMap<>();
        Users users = this.getCurrentUsers();
        try {
            // 保存表头信息和关联单信息
            rtnMap = crtExitEmptyService.updatePassport(object, users);
        } catch (Exception e) {
            this.logger.error("保存出区空车核放单时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存出区空车核放单时发生异常");
        }
        return new ResponseData(rtnMap);
    }


    //查看出区空车核放单详情
    @RequestMapping("/querypassportdetail")
    public ResponseData querypassportdetail(
            @RequestParam(required = false) String etps_preent_no//企业内部编号
    ) {
        this.logger.debug(String.format("出区空车核放单查询条件参数:[etps_preent_no:%s]", etps_preent_no));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("etps_preent_no", etps_preent_no);
        PassPortHead passPortHead;
        try {
            passPortHead = crtExitEmptyService.queryPassportDetail(paramMap);
        } catch (Exception e) {
            this.logger.error("查看出区空车核放单详情信息失败，etps_preent_no =" + etps_preent_no, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(passPortHead);
    }

    /**
     * 出区核放单申报-提交海关置为申报中状态
     **/
    @RequestMapping(value = "/submitCustom", method = RequestMethod.POST)
    public ResponseData submitCustom(
            @RequestParam(required = false) String submitKeys,
            HttpServletRequest request
    ) {
        this.logger.info("出区核放单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请勾选提交海关的出区空车核放单！");
        }
        boolean flag;
        Users user = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("status", StatusCode.CQKCHFDSBZ);//出区空车核放单申报中
        paramMap.put("statusWhere", StatusCode.CQKCHFDDSB);//出区空车核放单待申报
        paramMap.put("userId", user.getId());
        paramMap.put("submitKeys", submitKeys);//企业内部编码

        flag = crtExitEmptyService.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "出区核放单申报海关提交成功！");
        } else {
            return rtnResponse("false", "出区核放单申报海关提交失败！");
        }
    }

    //空车核放单删除
    @RequestMapping(value = "/deletepassport", method = RequestMethod.POST)
    public ResponseData deletePassport(
            @RequestParam(required = false) String submitKeys
    ) {
        if (StringUtils.isEmpty(submitKeys)) return new ResponseData("未提交需要删除的数据", HttpStatus.FORBIDDEN);

        try {
            this.logger.debug(String.format("出区空车核放单删除[submitKeys:%s]", submitKeys));
            this.crtExitEmptyService.deleteExitEmpty(submitKeys);
        } catch (Exception e) {
            this.logger.error("删除出区空车核放单失败，submitKeys=" + submitKeys, e);
            return new ResponseData("删除出区空车核放单失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData("");
    }

}
