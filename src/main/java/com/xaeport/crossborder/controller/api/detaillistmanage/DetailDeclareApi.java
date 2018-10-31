package com.xaeport.crossborder.controller.api.detaillistmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.tools.DownloadUtils;
import org.springframework.util.StringUtils;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.detaillistmanage.DetailDeclareService;
import com.xaeport.crossborder.tools.GetIpAddr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 **清单申报
 */

@RestController
@RequestMapping("/api/detailManage")
public class DetailDeclareApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DetailDeclareService detailDeclareService;

    /*
     * 邮件申报查询
     */
    @RequestMapping("/queryDetailDeclare")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String dataStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,billNo:%s]", startFlightTimes, endFlightTimes, billNo));
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("billNo", billNo);
        paramMap.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        paramMap.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);

        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        if (!StringUtils.isEmpty(dataStatus)) {
            paramMap.put("dataStatus", dataStatus);
        } else {
            paramMap.put("dataStatus", String.format("%s,%s,%s,%s,%s,%s,%s,%s", StatusCode.QDDSB, StatusCode.QDSBZ, StatusCode.QDYSB, StatusCode.QDCB, StatusCode.EXPORT, StatusCode.QDBWSCZ, StatusCode.QDBWXZWC, StatusCode.QDSBCG));
        }

        //更新人
        List<ImpInventory> resultList = null;
        try {
            //查询列表
            resultList = this.detailDeclareService.queryInventoryDeclareList(paramMap);
        } catch (Exception e) {
            this.logger.error("查询清单申报数据失败", e);
            return new ResponseData("获取清单申报数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);
    }

    /**
     * 清单申报-提交海关
     **/
    @RequestMapping(value = "/submitCustom", method = RequestMethod.POST)
    public ResponseData saveSubmitCustom(@RequestParam(required = false) String submitKeys,
                                         HttpServletRequest request) {
        this.logger.info("清单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的清单信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("dataStatus", StatusCode.QDSBZ);//清单申报中
        paramMap.put("dataStatusWhere", StatusCode.QDDSB);//清单待申报
        paramMap.put("currentUserId", currentUser.getId());
        paramMap.put("userId", currentUser.getId());

        paramMap.put("submitKeys", submitKeys);//提运单号
        // 调用清单申报Service获取提交海关结果
        boolean flag = detailDeclareService.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "清单申报海关提交成功！");
        } else {
            return rtnResponse("false", "清单申报海关提交失败！");
        }
    }

    /**
     * 清单报文下载
     **/
    @RequestMapping(value = "/InvenXmlDownload", method = RequestMethod.POST)
    public ResponseData orderXmlDownload(@RequestParam(required = false) String submitKeys,
                                         HttpServletRequest request) {
        this.logger.info("清单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要下载的清单信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("dataStatus", StatusCode.QDBWSCZ);//清单报文生成中
        paramMap.put("dataStatusWhere", StatusCode.QDDSB);//清单待申报
        paramMap.put("userId", currentUser.getId());

        paramMap.put("submitKeys", submitKeys);//提运单号

        boolean flag = detailDeclareService.invenXmlDownload(paramMap);
        String inventoryZipPath = detailDeclareService.invenXml(this.getCurrentUserEntId());
        if (!StringUtils.isEmpty(inventoryZipPath)) {
            return rtnResponse("1" + inventoryZipPath, "清单报文生成提交成功");
        } else {
            return rtnResponse("0" + inventoryZipPath, "清单报文生成提交失败");
        }
    }

    /**
     * excel 跨境电子商务进口订单模板下载
     */
    @RequestMapping(value = "/downloadFile")
    public void excelModelDownload(
            HttpServletResponse response,
            @RequestParam(value = "type") String type) {
        File file = new File(type);
        DownloadUtils.download(response, file, SystemConstants.HTTP_CONTENT_TYPE_ZIP);
    }

}
