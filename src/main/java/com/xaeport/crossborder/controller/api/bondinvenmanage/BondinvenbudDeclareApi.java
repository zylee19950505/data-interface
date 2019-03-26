package com.xaeport.crossborder.controller.api.bondinvenmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondinvenmanage.BondinvenDeclareService;
import com.xaeport.crossborder.service.bondinvenmanage.BondinvenbudDeclareService;
import com.xaeport.crossborder.tools.GetIpAddr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bondinvenmanage")
public class BondinvenbudDeclareApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    BondinvenbudDeclareService bondinvenbudDeclareService;

    @RequestMapping("/queryBondinvenbudDeclare")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String dataStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询保税清单申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,billNo:%s,dataStatus:%s]", startFlightTimes, endFlightTimes, billNo,dataStatus));
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("billNo", billNo);
        paramMap.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        paramMap.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);

        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());
        paramMap.put("business_type", "BONDINVEN");

        if (!StringUtils.isEmpty(dataStatus)) {
            paramMap.put("dataStatus", dataStatus);
        } else {
            paramMap.put("dataStatus", String.format("%s,%s,%s,%s,%s,%s,%s", StatusCode.BSQDDSB, StatusCode.BSQDSBZ, StatusCode.BSQDYSB, StatusCode.EXPORT, StatusCode.QDBWSCZ, StatusCode.QDBWXZWC, StatusCode.BSQDSBCG));
        }

        //更新人
        List<ImpInventory> resultList;
        try {
            //查询列表
            resultList = this.bondinvenbudDeclareService.queryBondinvenbudDeclare(paramMap);
        } catch (Exception e) {
            this.logger.error("查询保税清单申报数据失败", e);
            return new ResponseData("获取保税清单申报数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);
    }


    /**
     * 清单申报-提交海关
     **/
    @RequestMapping(value = "/submitBudCustom", method = RequestMethod.POST)
    public ResponseData saveSubmitCustom(@RequestParam(required = false) String submitKeys,
                                         HttpServletRequest request) {
        this.logger.info("清单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的清单信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("dataStatus", StatusCode.BSQDSBZ);//保税清单申报中
        paramMap.put("dataStatusWhere", StatusCode.BSQDDSB);//保税清单待申报
        paramMap.put("currentUserId", currentUser.getId());
        paramMap.put("userId", currentUser.getId());

        paramMap.put("submitKeys", submitKeys);//提运单号
        // 调用清单申报Service获取提交海关结果
        boolean flag = bondinvenbudDeclareService.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "保税清单申报海关提交成功！");
        } else {
            return rtnResponse("false", "保税清单申报海关提交失败！");
        }
    }
}
