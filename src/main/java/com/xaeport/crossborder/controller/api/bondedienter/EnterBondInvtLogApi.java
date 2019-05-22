package com.xaeport.crossborder.controller.api.bondedienter;

import com.alibaba.druid.support.json.JSONUtils;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.entity.VerifyBondHead;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.data.status.VerifyType;
import com.xaeport.crossborder.service.bondedienter.EnterInventoryService;
import com.xaeport.crossborder.service.logic.ExitLogService;
import org.apache.ibatis.annotations.Param;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
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
public class EnterBondInvtLogApi extends BaseApi {


    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    ExitLogService exitLogService;
    @Autowired
    EnterInventoryService enterInventoryService;

    //逻辑校验:列表查询
    @RequestMapping(value = "/enterbondinvt/enterbondinvtlog", method = RequestMethod.GET)
    public ResponseData getLogicalInventoryData(
            @RequestParam String etps_inner_invt_no,
            @RequestParam String status,
            HttpServletRequest request
    ) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("etps_inner_invt_no", etps_inner_invt_no);
            map.put("flag", SystemConstants.BSRQ);
            map.put("data_status", StatusCode.BSYDR);
            map.put("type", VerifyType.LOGIC);
            map.put("status", status);
            map.put("entId", this.getCurrentUserEntId());
            map.put("roleId", this.getCurrentUserRoleId());

            List<VerifyBondHead> enterBondInvtLogicList = this.exitLogService.getLogicDataByExitBondInvt(map);
            return new ResponseData(enterBondInvtLogicList);
        } catch (Exception e) {
            this.log.error("获取入区核注清单逻辑校验数据错误", e);
            return new ResponseData("获取入区核注清单逻辑校验数据错误", HttpStatus.BAD_REQUEST);
        }

    }

    //逻辑校验:删除入区核注清单
    @RequestMapping(value = "/enterbondinvt/deletelogicdata", method = RequestMethod.POST)
    public ResponseData deleteEnterInventory(
            String submitKeys
    ) {
        if (StringUtils.isEmpty(submitKeys)) return new ResponseData("未提交数据", HttpStatus.FORBIDDEN);

        try {
            this.enterInventoryService.deleteEnterInventory(submitKeys, this.getCurrentUsers());
        } catch (Exception e) {
            this.log.error("删除入区核注清单失败，submitKeys=" + submitKeys, e);
            return new ResponseData("删除入区核注清单失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData("");
    }

    //逻辑校验:修改入区核注清单数据
    @RequestMapping("/enterbondinvt/updateEnterLogic")
    public ResponseData saveInventoryDetail(@Param("entryJson") String entryJson) {
        //核注清单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        // 核注清单表头
        LinkedHashMap<String, String> bondInvtBsc = (LinkedHashMap<String, String>) object.get("entryHead");
        // 核注清单表体
        ArrayList<LinkedHashMap<String, String>> bondInvtDts = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");

        Map<String, String> rtnMap = new HashMap<>();
        Users users = this.getCurrentUsers();
        try {
            // 保存详情信息
            rtnMap = exitLogService.updateEnterBondInvtLog(bondInvtBsc, bondInvtDts, users);
        } catch (Exception e) {
            log.error("修改入区核注清单时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "修改入区核注清单时发生异常");
        }
        return new ResponseData(rtnMap);
    }

}
