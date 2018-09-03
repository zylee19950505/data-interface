package com.xaeport.crossborder.controller.api.detaillistmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 **清单申报
 */

@RestController
@RequestMapping("/api/detailManage")
public class DetailDeclareApi extends BaseApi{

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
            @RequestParam(required = false) String orderNo,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s]",startFlightTimes, endFlightTimes, orderNo));
        Map<String, String> paramMap = new HashMap<String, String>();

        //查询参数
        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("orderNo", orderNo);
        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        //分页参数
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);

        paramMap.put("entId",this.getCurrentUserEntId());
        paramMap.put("roleId",this.getCurrentUserRoleId());
        //类型参数
        paramMap.put("dataStatus", String.format("%s,%s,%s,%s,%s,%s,%s", StatusCode.QDDSB, StatusCode.QDSBZ,StatusCode.QDYSB, StatusCode.QDCB,StatusCode.EXPORT,StatusCode.QDBWSCZ,StatusCode.QDBWXZWC));

        //更新人
        DataList<ImpInventory> dataList = null;
        List<ImpInventory> resultList = null;
        try {
            //查询列表
            resultList = this.detailDeclareService.queryInventoryDeclareList(paramMap);
            //查询总数
            Integer count = this.detailDeclareService.queryInventoryDeclareCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询清单申报数据失败", e);
            return new ResponseData("获取清单申报数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }
    /**
     * 清单申报-提交海关
     *
     * @param submitKeys EntryHead.IDs
     */
    @RequestMapping(value = "/submitCustom", method = RequestMethod.POST)
    public ResponseData saveSubmitCustom(@RequestParam(required = false) String submitKeys,
                                         HttpServletRequest request) {
        this.logger.info("清单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的清单信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("dataStatus", StatusCode.QDSBZ);
        paramMap.put("dataStatusWhere", StatusCode.QDDSB + "," + StatusCode.QDCB+","+StatusCode.EXPORT);//可以申报的状态,支付单待申报,支付单重报,已导入
        paramMap.put("currentUserId", currentUser.getId());

        //* paramMap.put("enterpriseId", this.getCurrentUserEnterpriseId());*//*  //暂时不获取企业id
        paramMap.put("submitKeys", submitKeys);//清单遍号
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
     *
     * @param submitKeys EntryHead.IDs
     */
    @RequestMapping(value = "/InvenXmlDownload", method = RequestMethod.POST)
    public ResponseData orderXmlDownload(@RequestParam(required = false) String submitKeys,
                                         @RequestParam(required = false) String idCardValidate,
                                         @RequestParam(required = false) String ieFlag,
                                         @RequestParam(required = false) String entryType,
                                         HttpServletRequest request) {
        this.logger.info("清单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要下载的清单信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("dataStatus", StatusCode.QDBWSCZ);//提交海关后,状态改为订单申报中,逻辑校验在这个之前
        paramMap.put("dataStatusWhere", StatusCode.QDDSB + "," + StatusCode.QDCB + "," + StatusCode.EXPORT);//可以申报的状态,订单待申报,订单重报,已经导入
        paramMap.put("currentUserId", currentUser.getId());

        paramMap.put("submitKeys", submitKeys);//订单编号
        paramMap.put("idCardValidate", idCardValidate);
        paramMap.put("entryType", entryType);
        paramMap.put("ieFlag", ieFlag);

        // 调用订单申报Service 获取提交海关结果
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
