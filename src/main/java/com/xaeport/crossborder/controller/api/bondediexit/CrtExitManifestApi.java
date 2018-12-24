package com.xaeport.crossborder.controller.api.bondediexit;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondedIExit.CrtExitManifestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bondediexit")
public class CrtExitManifestApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    CrtExitManifestService crtExitManifestService;

    /*
     *  预订数据查询
     */
    @RequestMapping(value = "/queryCrtExitManifest", method = RequestMethod.GET)
    public ResponseData queryDeliveryDeclare(
            @RequestParam(required = false) String bondInvtNo,
            @RequestParam(required = false) String returnStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询出区核放单数据条件参数:[bondInvtNo:%s]", bondInvtNo));
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
        paramMap.put("bondInvtNo", bondInvtNo);
        paramMap.put("returnStatus", returnStatus);
        paramMap.put("status", StatusCode.CQHZQDSBCG);

        DataList<BondInvtBsc> dataList = null;
        List<BondInvtBsc> resultList = null;
        try {
            //查询列表
            resultList = this.crtExitManifestService.queryEInventoryList(paramMap);
            //查询总数
            Integer count = this.crtExitManifestService.queryEInventoryCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("出区核放单—获取出区核注清单数据失败", e);
            return new ResponseData("出区核放单—获取出区核注清单数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);

    }

//    /**
//     * 新建核放单
//     **/
//    @RequestMapping(value = "/manifestCreate", method = RequestMethod.GET)
//    public ResponseData saveSubmitCustom(
//            @RequestParam(required = false) String totalLogisticsNo
//    ) {
//        Users users = this.getCurrentUsers();
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("userId", users.getId());
//        paramMap.put("entId", users.getEnt_Id());
//        paramMap.put("roleId", users.getRoleId());
//        paramMap.put("ent_customs_code", users.getEnt_Customs_Code());
//
//        paramMap.put("totalLogisticsNo", totalLogisticsNo);
//
//        paramMap.put("app_person", users.getLoginName());
//        paramMap.put("input_code", users.getEnt_Customs_Code());
//        paramMap.put("input_name", users.getEnt_Name());
//        paramMap.put("trade_code", users.getEnt_Customs_Code());
//        paramMap.put("trade_name", users.getEnt_Name());
//
//        ManifestData manifestData = null;
//        try {
//            //查询列表
//            manifestData = this.manifestCreateService.queryManifestData(paramMap);
//        } catch (Exception e) {
//            this.logger.error("新建核放单数据失败", e);
//            return new ResponseData("获取核放单数据错误", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseData(manifestData);
//
//    }
//
//    //保存核放单信息
//    @RequestMapping("/saveManifestInfo")
//    public ResponseData saveManifestInfo(@Param("entryJson") String entryJson) {
//        //核放单json信息
//        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) JSONUtils.parse(entryJson);
//        Map<String, String> rtnMap = new HashMap<>();
//        try {
//            // 保存详情信息
//            rtnMap = this.manifestCreateService.saveManifestInfo(entryHead);
//        } catch (Exception e) {
//            logger.error("保存核放单信息时发生异常", e);
//            rtnMap.put("result", "false");
//            rtnMap.put("msg", "保存核放单信息时发生异常");
//        }
//        return new ResponseData(rtnMap);
//    }

//    /**
//     * 新建核放单
//     **/
//    @RequestMapping(value = "/newManifestCreate", method = RequestMethod.GET)
//    public ResponseData saveNewManifestCreate(
//    ) {
//        Users users = this.getCurrentUsers();
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("userId", users.getId());
//        paramMap.put("entId", users.getEnt_Id());
//        paramMap.put("roleId", users.getRoleId());
//        paramMap.put("ent_customs_code", users.getEnt_Customs_Code());
//        paramMap.put("app_person", users.getLoginName());
//        paramMap.put("input_code", users.getEnt_Customs_Code());
//        paramMap.put("input_name", users.getEnt_Name());
//        paramMap.put("trade_code", users.getEnt_Customs_Code());
//        paramMap.put("trade_name", users.getEnt_Name());
//        ManifestData manifestData = null;
//        try {
//            //查询列表
//            manifestData = this.manifestCreateService.queryManifestData(paramMap);
//        } catch (Exception e) {
//            this.logger.error("自建核放单数据失败", e);
//            return new ResponseData("获取核放单数据错误", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseData(manifestData);
//
//    }
//
//    //保存核放单信息
//    @RequestMapping("/saveNewManifestInfo")
//    public ResponseData saveNewManifestInfo(@Param("entryJson") String entryJson) {
//        //核放单json信息
//        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) JSONUtils.parse(entryJson);
//        Map<String, String> rtnMap = new HashMap<>();
//        try {
//            // 保存详情信息
//            rtnMap = this.manifestCreateService.saveManifestInfo(entryHead);
//        } catch (Exception e) {
//            logger.error("保存核放单信息时发生异常", e);
//            rtnMap.put("result", "false");
//            rtnMap.put("msg", "保存核放单信息时发生异常");
//        }
//        return new ResponseData(rtnMap);
//    }
//

}
