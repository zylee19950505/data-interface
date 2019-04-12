package com.xaeport.crossborder.controller.api.bondedienter;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondedIEnter.CrtEnterEmptyService;
import com.xaeport.crossborder.tools.GetIpAddr;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/crtEnterEmpty")
public class CrtEnterEmptyApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CrtEnterEmptyService crtEnterEmptyService;

   /*
   * 新建入区空车核放单
   * */
    @RequestMapping("/queryEnterEmpty")
    public ResponseData queryCrtEnterManifest(
            @RequestParam(required = false) String vehicle_no,//车牌号
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("新建入区空车核放单查询条件参数:[vehicle_no:%s]", vehicle_no));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("vehicle_no", vehicle_no);
        paramMap.put("passport_typecd", "6");
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("draw", draw);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        DataList<PassPortHead> dataList = new DataList<>();
        List<PassPortHead> resultList = new ArrayList<PassPortHead>();
        try {
            //查询列表
            //查找可用的大于0,和enter的
            resultList = this.crtEnterEmptyService.queryCrtEnterEmptyList(paramMap);
            Integer count = this.crtEnterEmptyService.queryCrtEnterEmptyCount(paramMap);
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

    /**
     * 新建入区空车核放单
     */
    @RequestMapping(value = "saveEntryEmptyInfo")
    public ResponseData saveEntryEmptyInfo(@Param("entryJson") String entryJson) {
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        Map<String, String> rtnMap = new HashMap<>();
        Users users = this.getCurrentUsers();
        try {
            // 保存表头信息和关联单信息
            rtnMap = crtEnterEmptyService.saveEntryEmptyInfo(object, users);
        } catch (Exception e) {
            this.logger.error("保存入区空车核放单时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存入区空车核放单时发生异常");
        }
        return new ResponseData(rtnMap);
    }
    /**
     * 保存入区空车核放单
     */
    @RequestMapping(value = "updateEntryEmptyInfo")
    public ResponseData updateEntryEmptyInfo(@Param("entryJson") String entryJson) {
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        Map<String, String> rtnMap = new HashMap<>();
        Users users = this.getCurrentUsers();
        try {
            // 保存表头信息和关联单信息
            rtnMap = crtEnterEmptyService.updateEntryEmptyInfo(object, users);
        } catch (Exception e) {
            this.logger.error("保存入区空车核放单时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存入区空车核放单时发生异常");
        }
        return new ResponseData(rtnMap);
    }

    /**
     * 核放单申报-提交海关
     **/
    @RequestMapping(value = "/submitEmptyCustom", method = RequestMethod.POST)
    public ResponseData submitEmptyCustom(
            @RequestParam(required = false) String submitKeys,
            HttpServletRequest request
    ) {
        this.logger.info("核放单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的提交的核放单信息！");
        }
        Users user = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("status", StatusCode.RQKCHFDSBZ);//申报中
        paramMap.put("statusWhere", StatusCode.RQKCHFDDSB);//待申报
        paramMap.put("userId", user.getId());
        paramMap.put("submitKeys", submitKeys);
        boolean flag = crtEnterEmptyService.submitEmptyCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "入区空车核放单申报海关提交成功！");
        } else {
            return rtnResponse("false", "入区空车核放单申报海关提交失败！");
        }
    }

    //空车核放单删除
    @RequestMapping(value = "/deleteEnterEmpty/{id}", method = RequestMethod.DELETE)
    public ResponseData manifestDelete(
            @PathVariable(value = "id") String id
    ) {
        this.logger.debug(String.format("核放单删除[id:%s]", id));
        this.crtEnterEmptyService.deleteEnterEmpty(id);
        return new ResponseData();
    }



    /*
     * 查看入区空车核放单详情
     * */
    @RequestMapping("/queryEnterEmptyDetails")
    public ResponseData queryEnterEmptyDetails(
            @RequestParam(required = false) String etps_preent_no//企业内部编号
    ) {
        this.logger.debug(String.format("新建入区空车核放单查询条件参数:[etps_preent_no:%s]", etps_preent_no));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("etps_preent_no", etps_preent_no);
        PassPortHead passPortHead = new PassPortHead();
        try {
            passPortHead = crtEnterEmptyService.queryEnterEmptyDetails(paramMap);
        } catch (Exception e) {
            this.logger.error("查看入区空车核放单详情信息失败，etps_preent_no =" + etps_preent_no, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(passPortHead);
    }
}
