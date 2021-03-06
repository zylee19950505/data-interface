package com.xaeport.crossborder.controller.api.bondediexit;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondediexit.ExitInventoryService;
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
public class ExitInventoryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    ExitInventoryService exitInventoryService;

    //查询出区核注清单数据
    @RequestMapping(value = "/queryexitinventory", method = RequestMethod.GET)
    public ResponseData queryCrtExitInventory(
            @RequestParam(required = false) String invt_dcl_time,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String return_status,
            @RequestParam(required = false) String bond_invt_no,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询出区核注清单数据参数:[invt_dcl_time:%s,status:%s,return_status:%s,bond_invt_no:%s]", invt_dcl_time, status, return_status, bond_invt_no));
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

        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());
        paramMap.put("invt_dcl_time", invt_dcl_time);
        if (!StringUtils.isEmpty(status)) {
            paramMap.put("status", status);
        } else {
            paramMap.put("status", String.format("%s,%s,%s,%s", StatusCode.CQHZQDDSB, StatusCode.CQHZQDYSB, StatusCode.CQHZQDSBZ, StatusCode.CQHZQDSBCG));
        }
        paramMap.put("return_status", return_status);
        paramMap.put("bond_invt_no", bond_invt_no);

        DataList<BondInvtBsc> dataList;
        List<BondInvtBsc> resultList;
        try {
            //查询列表
            resultList = this.exitInventoryService.queryEInventoryList(paramMap);
            //查询总数
            Integer count = this.exitInventoryService.queryEInventoryCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询出区核注清单数据失败", e);
            return new ResponseData("查询出区核注清单数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);

    }

    //查询出区核注清单数据，获取数据
    @RequestMapping(value = "/exitinventory", method = RequestMethod.GET)
    public ResponseData exitInventory(
            @RequestParam(required = false) String dataInfo
    ) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("etpsInnerInvtNo", dataInfo);

        ExitBondInvt exitBondInvt = new ExitBondInvt();
        BondInvtBsc bondInvtBsc;
        List<NemsInvtCbecBillType> nemsInvtCbecBillTypeList;
        try {
            //查询列表
            bondInvtBsc = this.exitInventoryService.queryBondInvtBsc(paramMap);
            nemsInvtCbecBillTypeList = this.exitInventoryService.queryNemsInvtCbecBillTypeList(paramMap);
            for (int i = 0; i < nemsInvtCbecBillTypeList.size(); i++) {
                nemsInvtCbecBillTypeList.get(i).setNo(i + 1);
            }
            Verify verify = this.exitInventoryService.queryLogicVerify(paramMap);
            exitBondInvt.setBondInvtBsc(bondInvtBsc);
            exitBondInvt.setNemsInvtCbecBillTypeList(nemsInvtCbecBillTypeList);
            exitBondInvt.setVerify(verify);
        } catch (Exception e) {
            this.logger.error("查询出区核注清单数据失败", e);
            return new ResponseData("查询出区核注清单数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(exitBondInvt);
    }

    //修改出区核注清单信息
    @RequestMapping("/updateExitInventory")
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
            rtnMap = exitInventoryService.updateExitInventory(BondInvtBsc, nemsInvtCbecBillTypeList, userInfo);
        } catch (Exception e) {
            logger.error("修改出区核注清单信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "修改出区核注清单信息时发生异常");
        }
        return new ResponseData(rtnMap);
    }

    /**
     * 出区核注清单数据申报-提交海关置为申报中状态
     **/
    @RequestMapping(value = "/exitinventory/submitCustom", method = RequestMethod.POST)
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
        paramMap.put("status", StatusCode.CQHZQDSBZ);//申报中
        paramMap.put("statusWhere", StatusCode.CQHZQDDSB);//待申报
        paramMap.put("userId", user.getId());
        paramMap.put("submitKeys", submitKeys);//清单唯一编码
        // 调用清单申报Service获取提交海关结果
        boolean flag = exitInventoryService.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "出区核注清单申报海关提交成功！");
        } else {
            return rtnResponse("false", "出区核注清单申报海关提交失败！");
        }
    }

    //删除出区核注清单操作
    @RequestMapping(value = "/exitinventory/deleteExitInventory", method = RequestMethod.POST)
    public ResponseData deleteVerifyIdCard(
            String submitKeys
    ) {
        if (StringUtils.isEmpty(submitKeys)) return new ResponseData("未提交出区核注清单数据", HttpStatus.FORBIDDEN);
        try {
            this.exitInventoryService.deleteExitInventory(submitKeys, this.getCurrentUserEntId());
        } catch (Exception e) {
            this.logger.error("删除出区核注清单失败，submitKeys=" + submitKeys, e);
            return new ResponseData("删除出区核注清单失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData("");
    }

    @RequestMapping(value = "/seeBondInvtRec", method = RequestMethod.GET)
    public ResponseData queryBondInvtRecInfo(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String etps_inner_invt_no
    ) {
        if (StringUtils.isEmpty(id)) return new ResponseData("核注清单Id为空", HttpStatus.FORBIDDEN);
        if (StringUtils.isEmpty(etps_inner_invt_no)) return new ResponseData("核注清单内部编码为空", HttpStatus.FORBIDDEN);

        this.logger.debug(String.format("查询核注清单回执参数:[id:%s,etps_inner_invt_no:%s]", id, etps_inner_invt_no));
        BondInvtBsc bondInvtBsc;
        try {
            bondInvtBsc = exitInventoryService.queryBondInvtRecInfo(id, etps_inner_invt_no);
        } catch (Exception e) {
            this.logger.error("查询核注清单回执失败，etps_inner_invt_no=" + etps_inner_invt_no, e);
            return new ResponseData("查询核注清单回执失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(bondInvtBsc);
    }

}
