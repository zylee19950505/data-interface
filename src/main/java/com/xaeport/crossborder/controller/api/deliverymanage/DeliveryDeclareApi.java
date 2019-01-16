package com.xaeport.crossborder.controller.api.deliverymanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.ImpDelivery;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.deliverymanage.DeliveryDeclareService;
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
@RequestMapping("/api/deliveryManage")
public class DeliveryDeclareApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DeliveryDeclareService deliveryDeclService;

    /*
     *  入库明细申报查询
     */
    @RequestMapping("/queryDeliveryDeclare")
    public ResponseData queryDeliveryDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String dataStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s,dataStatus:%s]", startFlightTimes, endFlightTimes, billNo, dataStatus));
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("billNo", billNo);
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        if (!StringUtils.isEmpty(dataStatus)) {
            paramMap.put("dataStatus", dataStatus);
        } else {
            paramMap.put("dataStatus", String.format("%s,%s,%s", StatusCode.RKMXDDSB, StatusCode.RKMXDSBZ, StatusCode.RKMXDYSB));
        }
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        //更新人
        List<ImpDelivery> resultList = null;
        try {
            //查询列表
            resultList = this.deliveryDeclService.queryDeliveryDeclareList(paramMap);
        } catch (Exception e) {
            this.logger.error("查询入库明细单申报数据失败", e);
            return new ResponseData("查询入库明细单申报数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);

    }

    /**
     * 订单单申报-提交海关
     *
     * @param submitKeys EntryHead.IDs
     */
    @RequestMapping(value = "/submitCustom", method = RequestMethod.POST)
    public ResponseData saveSubmitCustom(
            @RequestParam(required = false) String submitKeys,
            HttpServletRequest request
    ) {
        this.logger.info("入库明细单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的入库明细单信息！");
        }

        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("dataStatus", StatusCode.RKMXDSBZ);//提交海关后,状态改为申报中,逻辑校验在这个之前
        paramMap.put("dataStatusWhere", StatusCode.RKMXDDSB + "," + StatusCode.EXPORT);//可以申报的状态:待申报,已经导入
        paramMap.put("userId", currentUser.getId());

        paramMap.put("submitKeys", submitKeys);

        // 调用订单申报Service 获取提交海关结果
        this.deliveryDeclService.setDeliveryData(this.getCurrentUserEntId(), submitKeys);
        int flag = 0;
        flag = this.deliveryDeclService.updateSubmitCustom(paramMap);
        if (flag == 1) {
            return rtnResponse("true", "入库明细单申报海关提交成功！");
        } else if (flag == 2) {
            return rtnResponse("fill", "");
        } else {
            return rtnResponse("false", "入库明细单申报海关提交失败！");
        }
    }

    /*
     *  查询入库明细单待填写数据
     */
    @RequestMapping("/querydeliverytofill")
    public ResponseData querydeliverytofill(
            @RequestParam(required = false) String dataInfo
    ) {
        this.logger.debug(String.format("查询入库明细单参数:[dataInfo:%s]", dataInfo));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("billNo", dataInfo);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());
        //更新人
        List<ImpDelivery> resultList = null;
        try {
            //查询入库明细单待填写数据
            resultList = this.deliveryDeclService.querydeliverytofill(paramMap);
            System.out.println("查到了");
        } catch (Exception e) {
            this.logger.error("获取入库明细单待填写数据失败", e);
            return new ResponseData("获取入库明细单待填写数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);
    }

    //填充入库明细单信息
    @RequestMapping("/filldeliveryinfo")
    public ResponseData saveManifestInfo(@Param("entryJson") String entryJson) {
        //入库明细单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        //获取入库明细单信息
        ArrayList<LinkedHashMap<String, String>> deliveryHeadLists = (ArrayList<LinkedHashMap<String, String>>) object.get("deliveryHeadList");
        //获取用户所选定的所有提运单号
        String billNos = (String) object.get("billNos");

        Users userInfo = this.getCurrentUsers();
        Map<String, String> map = new HashMap<>();

        Map<String, String> paramMap = new HashMap<>();
        Users currentUser = this.getCurrentUsers();
        paramMap.put("dataStatus", StatusCode.RKMXDSBZ);//提交海关后,状态改为申报中,逻辑校验在这个之前
        paramMap.put("dataStatusWhere", StatusCode.RKMXDDSB + "," + StatusCode.RKMXDCB + "," + StatusCode.EXPORT);//可以申报的状态,待申报,重报,已经导入
        paramMap.put("userId", currentUser.getId());
        paramMap.put("submitKeys", billNos);
        try {
            // 保存详情信息
            map = this.deliveryDeclService.fillDeliveryInfo(deliveryHeadLists, userInfo);
            List<String> billNoList = deliveryDeclService.queryDeliveryByEmptyVoyage(billNos);
            this.deliveryDeclService.updateDeliveryInfoByLogistic(billNoList, paramMap);

        } catch (Exception e) {
            logger.error("填充入库明细单时发生异常", e);
            map.put("result", "false");
            map.put("msg", "填充入库明细单时发生异常");
        }
        return new ResponseData(map);
    }


}
