package com.xaeport.crossborder.controller.api.bondediexit;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.service.bondedIExit.ExitInventoryService;
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


/*
 * 订单申报
 */
@RestController
@RequestMapping("/api/bondediexit")
public class ExitInventoryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    ExitInventoryService exitInventoryService;

    @RequestMapping(value = "/queryexitinventory", method = RequestMethod.GET)
    public ResponseData queryCrtExitInventory(
            @RequestParam(required = false) String entry_dcl_time,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String return_status,
            @RequestParam(required = false) String bond_invt_no,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询出区核注清单数据参数:[bond_invt_no:%s]", bond_invt_no));
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
        paramMap.put("entry_dcl_time", entry_dcl_time);
        paramMap.put("status", status);
        paramMap.put("return_status", return_status);
        paramMap.put("bond_invt_no", bond_invt_no);

        DataList<BondInvtBsc> dataList = null;
        List<BondInvtBsc> resultList = null;
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

}
