package com.xaeport.crossborder.controller.api.bondediexit;

import com.alibaba.druid.support.json.JSONUtils;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.entity.VerifyBondHead;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.data.status.VerifyType;
import com.xaeport.crossborder.service.bondediexit.ExitInventoryService;
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
public class ExitBondInvtLogApi extends BaseApi {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    ExitLogService exitLogService;
    @Autowired
    ExitInventoryService exitInventoryService;

    //逻辑校验列表查询
    @RequestMapping(value = "/exitbondinvt/exitbondinvtlog", method = RequestMethod.GET)
    public ResponseData getLogicalInventoryData(
            @RequestParam String etps_inner_invt_no,
            @RequestParam String status,
            HttpServletRequest request
    ) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("etps_inner_invt_no", etps_inner_invt_no);
            map.put("data_status", StatusCode.BSYDR);
            map.put("type", VerifyType.LOGIC);
            map.put("status", status);
            map.put("entId", this.getCurrentUserEntId());
            map.put("roleId", this.getCurrentUserRoleId());

            List<VerifyBondHead> exitBondInvtLogicList = this.exitLogService.getLogicDataByExitBondInvt(map);
            return new ResponseData(exitBondInvtLogicList);
        } catch (Exception e) {
            this.log.error("获取出区核注清单逻辑校验数据错误", e);
            return new ResponseData("获取出区核注清单逻辑校验数据错误", HttpStatus.BAD_REQUEST);
        }

    }

    //删除出区核注清单操作
    @RequestMapping(value = "/exitbondinvt/deletelogicdata", method = RequestMethod.POST)
    public ResponseData deleteVerifyIdCard(
            String submitKeys
    ) {
        if (StringUtils.isEmpty(submitKeys)) return new ResponseData("未提交出区核注清单数据", HttpStatus.FORBIDDEN);
        try {
            this.exitInventoryService.deleteExitInventory(submitKeys, this.getCurrentUserEntId());
        } catch (Exception e) {
            this.log.error("删除出区核注清单失败，submitKeys=" + submitKeys, e);
            return new ResponseData("删除出区核注清单失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData("");
    }

    //修改出区核注清单信息
    @RequestMapping("/exitbondinvt/updateExitLogic")
    public ResponseData updateExitInventory(@Param("entryJson") String entryJson) {
        //出区核注清单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        String dcl_etps_nm = (String) object.get("dcl_etps_nm");

        // 出区核注清单表头
        LinkedHashMap<String, String> BondInvtBsc = (LinkedHashMap<String, String>) object.get("BondInvtBsc");
        // 出区核注清单表体
        ArrayList<LinkedHashMap<String, String>> nemsInvtCbecBillTypeList = (ArrayList<LinkedHashMap<String, String>>) object.get("nemsInvtCbecBillTypeList");

        BondInvtBsc.put("dcl_etps_nm", dcl_etps_nm);

        Users userInfo = this.getCurrentUsers();
        Map<String, String> rtnMap = new HashMap<>();
        try {
            // 保存详情信息
            rtnMap = exitInventoryService.updateExitLogic(BondInvtBsc, nemsInvtCbecBillTypeList, userInfo);
        } catch (Exception e) {
            this.log.error("修改出区核注清单信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "修改出区核注清单信息时发生异常");
        }
        return new ResponseData(rtnMap);
    }

}
