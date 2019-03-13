package com.xaeport.crossborder.controller.api.bondordermanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.OrderSum;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondordermanage.BondOrderDeclSevice;
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

//保税订单申报
@RestController
@RequestMapping("/api/bondordermanage")
public class BondOrderDeclApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    BondOrderDeclSevice bondOrderDeclSevice;

    //数据查询
    @RequestMapping("/queryOrderDeclare")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String dataStatus
    ) {
        this.logger.debug(String.format("订单申报查询参数:[startFlightTimes:%s,endFlightTimes:%s,billNo:%s,dataStatus:%s]", startFlightTimes, endFlightTimes, billNo, dataStatus));
        Map<String, String> paramMap = new HashMap<String, String>();
        //查询参数
        paramMap.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        paramMap.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);
        paramMap.put("billNo", billNo);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());
        if (!StringUtils.isEmpty(dataStatus)) {
            paramMap.put("dataStatus", dataStatus);
        } else {
            paramMap.put("dataStatus", String.format("%s,%s,%s,%s", StatusCode.BSDDDSB, StatusCode.BSDDSBZ, StatusCode.BSDDYSB, StatusCode.BSDDSBCG));
        }

        List<OrderSum> resultList;
        try {
            //查询列表
            resultList = bondOrderDeclSevice.queryOrderDeclareList(paramMap);
        } catch (Exception e) {
            this.logger.error("查询订单申报数据失败", e);
            return new ResponseData("获取订单申报数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);
    }


    /**
     * 订单申报-提交海关
     **/
    @RequestMapping(value = "/submitCustom", method = RequestMethod.POST)
    public ResponseData saveSubmitCustom(
            @RequestParam(required = false) String submitKeys,
            HttpServletRequest request) {
        this.logger.info("订单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
        if (StringUtils.isEmpty(submitKeys)) {
            return rtnResponse("false", "请先勾选要提交海关的订单信息！");
        }
        Users currentUser = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("opStatus", StatusCode.BSDDSBZ);//保税订单申报中
        paramMap.put("opStatusWhere", StatusCode.BSDDDSB);//保税订单待申报
        paramMap.put("currentUserId", currentUser.getId());
        paramMap.put("submitKeys", submitKeys);//提运单号

        // 调用订单申报Service 获取提交海关结果
        boolean flag = bondOrderDeclSevice.updateSubmitCustom(paramMap);
        if (flag) {
            return rtnResponse("true", "订单申报海关提交成功！");
        } else {
            return rtnResponse("false", "订单申报海关提交失败！");
        }
    }

//    /**
//     * 订单报文下载
//     **/
//    @RequestMapping(value = "/orderXmlDownload", method = RequestMethod.POST)
//    public ResponseData orderXmlDownload(
//            @RequestParam(required = false) String submitKeys,
//            HttpServletRequest request) {
//        this.logger.info("订单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
//        if (StringUtils.isEmpty(submitKeys)) {
//            return rtnResponse("false", "请先勾选要下载的订单信息！");
//        }
//        Users currentUser = this.getCurrentUsers();
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("opStatus", StatusCode.DDBWSCZ);//订单报文生成中
//        paramMap.put("opStatusWhere", StatusCode.DDDSB);//订单待申报
//        paramMap.put("currentUserId", currentUser.getId());
//
//        paramMap.put("submitKeys", submitKeys);//提运单号
//
//        boolean flag = bondOrderDeclSevice.orderXmlDownload(paramMap);
//        String orderZipPath = bondOrderDeclSevice.OrderXml(this.getCurrentUserEntId());
//        if (!StringUtils.isEmpty(orderZipPath)) {
//            return rtnResponse("1" + orderZipPath, "订单报文生成提交成功");
//        } else {
//            return rtnResponse("0" + orderZipPath, "订单报文生成提交失败");
//        }
//    }
//
//    @RequestMapping(value = "/downloadFile")
//    public void excelModelDownload(
//            HttpServletResponse response,
//            @RequestParam(value = "type") String type
//    ) {
//        File file = new File(type);
//        DownloadUtils.download(response, file, SystemConstants.HTTP_CONTENT_TYPE_ZIP);
//    }

}
