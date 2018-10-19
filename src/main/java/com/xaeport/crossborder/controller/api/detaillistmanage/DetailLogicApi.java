package com.xaeport.crossborder.controller.api.detaillistmanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.ImpCrossBorderHead;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.data.status.VerifyType;
import com.xaeport.crossborder.service.detaillistmanage.DetailQueryService;
import com.xaeport.crossborder.service.logic.LogicalService;
import org.apache.ibatis.annotations.Param;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class DetailLogicApi extends BaseApi {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    LogicalService logicalService;
    @Autowired
    DetailQueryService detailQueryService;
//    @Autowired
//    VerificationService verificationService;
    @Autowired
    AppConfiguration appConfiguration;

    //逻辑校验列表查询
    @RequestMapping(value = "/inventory/logical", method = RequestMethod.GET)
    public ResponseData getLogicalInventoryData(
            @RequestParam String bill_no,
            @RequestParam String order_no,
            @RequestParam String voyage_no,
            @RequestParam String ie_date,
            @RequestParam String status,
            HttpServletRequest request
    ) {
        try {
            Map<String,String> map = new HashMap<>();
            map.put("bill_no",bill_no);
            map.put("order_no",order_no);
            map.put("voyage_no",voyage_no);
            map.put("ie_date",ie_date);
            map.put("data_status", StatusCode.EXPORT);
            map.put("type", VerifyType.LOGIC);
            map.put("status",status);
            map.put("entId", this.getCurrentUserEntId());
            map.put("roleId", this.getCurrentUserRoleId());

            List<ImpCrossBorderHead> inventoryLogicList = this.logicalService.getInventoryLogicData(map);
            return new ResponseData(inventoryLogicList);
        } catch (Exception e) {
            this.log.error("获取清单逻辑校验列表错误", e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }

    }

    //保存清单信息
    @RequestMapping(value = "/inventory/saveLogicalDetail")
    public ResponseData saveLogicalDetail(@Param("entryJson") String entryJson) {
        //清单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        // 清单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");

        // 清单表体
        ArrayList<LinkedHashMap<String, String>> entryLists = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");

        Map<String, String> rtnMap = new HashMap<>();
        try {
            // 保存详情信息
            rtnMap = detailQueryService.saveLogicalDetail(entryHead, entryLists);
        } catch (Exception e) {
            log.error("保存清单详细信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存清单详细信息时发生异常");
        }
        return new ResponseData(rtnMap);
    }



}
