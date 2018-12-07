//package com.xaeport.crossborder.controller.api.bondedIExit;
//
//import com.alibaba.druid.support.json.JSONUtils;
//import com.alibaba.druid.support.logging.Log;
//import com.alibaba.druid.support.logging.LogFactory;
//import com.xaeport.crossborder.controller.api.BaseApi;
//import com.xaeport.crossborder.data.ResponseData;
//import com.xaeport.crossborder.data.entity.CheckGoodsInfo;
//import com.xaeport.crossborder.data.entity.ManifestData;
//import com.xaeport.crossborder.data.entity.Users;
//import com.xaeport.crossborder.service.manifest.ManifestCreateService;
//import org.apache.ibatis.annotations.Param;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/manifest")
//public class CrtExitInventoryApi extends BaseApi {
//
//    private Log logger = LogFactory.getLog(this.getClass());
//
//    @Autowired
//    ManifestCreateService manifestCreateService;
//
//    /*
//     *  预订数据查询
//     */
//    @RequestMapping("/queryManifestCreate")
//    public ResponseData queryDeliveryDeclare(
////            @RequestParam(required = false) String startFlightTimes,
////            @RequestParam(required = false) String endFlightTimes,
//            @RequestParam(required = false) String billNo,
////            @RequestParam(required = false) String returnStatus,
//            HttpServletRequest request
//    ) {
//        this.logger.debug(String.format("查询邮件申报条件参数:[billNo:%s]", billNo));
//        Map<String, String> paramMap = new HashMap<String, String>();
//
//        paramMap.put("billNo", billNo);
////        paramMap.put("startFlightTimes", startFlightTimes);
////        paramMap.put("endFlightTimes", endFlightTimes);
////        paramMap.put("returnStatus",returnStatus);
//
//        paramMap.put("entId", this.getCurrentUserEntId());
//        paramMap.put("roleId", this.getCurrentUserRoleId());
//
//        //更新人
//        List<CheckGoodsInfo> resultList = null;
//        try {
//            //查询列表
//            resultList = this.manifestCreateService.queryCheckGoodsInfoList(paramMap);
//        } catch (Exception e) {
//            this.logger.error("查询预订数据失败", e);
//            return new ResponseData("获取预订数据错误", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseData(resultList);
//
//    }
//
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
//
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
//
//}
