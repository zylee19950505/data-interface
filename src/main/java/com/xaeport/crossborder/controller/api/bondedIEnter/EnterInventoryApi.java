package com.xaeport.crossborder.controller.api.bondedienter;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondedienter.EnterInventoryService;
import com.xaeport.crossborder.tools.GetIpAddr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/enterInventory")
public class EnterInventoryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    EnterInventoryService enterInventoryService;

    /*
     * 入区核注清单查询
     */
    @RequestMapping("/queryEnterInventory")
    public ResponseData queryEnterInventory(
            //身份验证
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String dataStatus,
            @RequestParam(required = false) String returnDataStatus,
            @RequestParam(required = false) String invtNo,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw
    ) {
        this.logger.debug(String.format("入区核注清单:[startFlightTimes:%s,dataStatus:%s,returnDataStatus:%s,invtNo:%s]", startFlightTimes, dataStatus, returnDataStatus, invtNo));
        Map<String, String> paramMap = new HashMap<String, String>();
        //查询参数
        paramMap.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        paramMap.put("dataStatus", dataStatus);
        paramMap.put("returnDataStatus", returnDataStatus);
        paramMap.put("invtNo", invtNo);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId",this.getCurrentUserRoleId());
        paramMap.put("start", String.valueOf(Integer.parseInt(start)+1));
        paramMap.put("length",length);

        DataList<BondInvtBsc> dataList = new DataList<>();
        List<BondInvtBsc> resultList = new ArrayList<BondInvtBsc>();
        try {
            //查询列表
            resultList = enterInventoryService.queryEnterInventory(paramMap);
            //查询总数
            Integer count = enterInventoryService.queryEnterInventoryCount(paramMap);
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("查询入区核注清单数据失败", e);
            return new ResponseData("查询入区核注清单数据失败", HttpStatus.BAD_REQUEST);
        }
    }

    //删除入区核注清单
    @RequestMapping(value = "/enterinventory/deleteEnterInventory", method = RequestMethod.POST)
    public ResponseData deleteEnterInventory(
            String submitKeys
    ) {
        if (StringUtils.isEmpty(submitKeys)) return new ResponseData("未提交数据", HttpStatus.FORBIDDEN);

        try {
            this.enterInventoryService.deleteEnterInventory(submitKeys, this.getCurrentUsers());
        } catch (Exception e) {
            this.logger.error("删除入区核注清单失败，submitKeys=" + submitKeys, e);
            return new ResponseData("删除入区核注清单失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData("");
    }
    /**
     * 清单申报-提交海关
     **/
    @RequestMapping(value = "/enterinventory/submitCustom", method = RequestMethod.POST)
    public ResponseData saveSubmitCustom(
            @RequestParam(required = false) String submitKeys,
            HttpServletRequest request
    ) {
        this.logger.info("清单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的出区核注清单信息！");
        }
        Users user = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("status", StatusCode.RQHZQDSBZ);//申报中
        paramMap.put("statusWhere", StatusCode.RQHZQDDSB);//待申报
        paramMap.put("userId", user.getId());

        paramMap.put("submitKeys", submitKeys);//清单唯一编码
        boolean flag = enterInventoryService.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "入区核注清单申报海关提交成功！");
        } else {
            return rtnResponse("false", "入区核注清单申报海关提交失败！");
        }
    }
}
