//package com.xaeport.crossborder.controller.api.bondedienter;
//
//import com.alibaba.druid.support.logging.Log;
//import com.alibaba.druid.support.logging.LogFactory;
//import com.xaeport.crossborder.configuration.AppConfiguration;
//import com.xaeport.crossborder.configuration.SystemConstants;
//import com.xaeport.crossborder.controller.api.BaseApi;
//import com.xaeport.crossborder.data.ResponseData;
//import com.xaeport.crossborder.data.entity.DataList;
//import com.xaeport.crossborder.data.entity.OrderSum;
//import com.xaeport.crossborder.data.entity.Users;
//import com.xaeport.crossborder.data.status.StatusCode;
//import com.xaeport.crossborder.service.ordermanage.OrderDeclareSevice;
//import com.xaeport.crossborder.tools.DownloadUtils;
//import com.xaeport.crossborder.tools.GetIpAddr;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
///*
// * 订单申报
// */
//@RestController
//@RequestMapping("/api/orderManage")
//public class EntryRecordInventoryApi extends BaseApi {
//
//    private Log logger = LogFactory.getLog(this.getClass());
//
//    @Autowired
//    AppConfiguration appConfiguration;
//    @Autowired
//    OrderDeclareSevice orderDeclareService;
//
//    /*
//     * 邮件申报查询
//     */
//    @RequestMapping("/queryOrderDeclare")
//    public ResponseData queryOrderDeclare(
//            //身份验证
//            @RequestParam(required = false) String startFlightTimes,
//            @RequestParam(required = false) String endFlightTimes,
//            @RequestParam(required = false) String billNo,
//            @RequestParam(required = false) String dataStatus
//    ) {
//        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,billNo:%s,dataStatus:%s]", startFlightTimes, endFlightTimes, billNo, dataStatus));
//        Map<String, String> paramMap = new HashMap<String, String>();
//        //查询参数
//        paramMap.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
//        paramMap.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);
//
//        paramMap.put("billNo", billNo);
//        paramMap.put("dataStatus", dataStatus);
//
//        paramMap.put("entId", this.getCurrentUserEntId());
//        paramMap.put("roleId", this.getCurrentUserRoleId());
//
//        DataList<OrderSum> dataList = new DataList<>();
//        List<OrderSum> resultList = new ArrayList<OrderSum>();
//        try {
//            //查询列表
//            resultList = orderDeclareService.queryOrderDeclareList(paramMap);
//        } catch (Exception e) {
//            this.logger.error("查询订单申报数据失败", e);
//            return new ResponseData("获取订单申报数据错误", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseData(resultList);
//    }
//
//
//    /**
//     * 订单单申报-提交海关
//     **/
//    @RequestMapping(value = "/submitCustom", method = RequestMethod.POST)
//    public ResponseData saveSubmitCustom(
//            @RequestParam(required = false) String submitKeys,
//            HttpServletRequest request) {
//        this.logger.info("订单申报客户端操作地址为 " + GetIpAddr.getRemoteIpAdd(request));
//        if (StringUtils.isEmpty(submitKeys)) {
//            return rtnResponse("false", "请先勾选要提交海关的订单信息！");
//        }
//        Users currentUser = this.getCurrentUsers();
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("opStatus", StatusCode.DDSBZ);//订单申报中
//        paramMap.put("opStatusWhere", StatusCode.DDDSB);//订单待申报
//        paramMap.put("currentUserId", currentUser.getId());
//        paramMap.put("submitKeys", submitKeys);//提运单号
//
//        // 调用订单申报Service 获取提交海关结果
//        boolean flag = orderDeclareService.updateSubmitCustom(paramMap);
//        if (flag) {
//            return rtnResponse("true", "订单申报海关提交成功！");
//        } else {
//            return rtnResponse("false", "订单申报海关提交失败！");
//        }
//    }
//
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
//        boolean flag = orderDeclareService.orderXmlDownload(paramMap);
//        String orderZipPath = orderDeclareService.OrderXml(this.getCurrentUserEntId());
//        if (!StringUtils.isEmpty(orderZipPath)) {
//            return rtnResponse("1" + orderZipPath, "订单报文生成提交成功");
//        } else {
//            return rtnResponse("0" + orderZipPath, "订单报文生成提交失败");
//        }
//    }
//
//    /**
//     * excel 跨境电子商务进口订单模板下载
//     */
//    @RequestMapping(value = "/downloadFile")
//    public void excelModelDownload(
//            HttpServletResponse response,
//            @RequestParam(value = "type") String type
//    ) {
//        File file = new File(type);
//        DownloadUtils.download(response, file, SystemConstants.HTTP_CONTENT_TYPE_ZIP);
//    }
//
//}
