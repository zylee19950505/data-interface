package com.xaeport.crossborder.controller.api.waybillmanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.waybillmanage.WaybillQueryService;
import com.xaeport.crossborder.tools.DateTools;
import com.xaeport.crossborder.tools.DownloadUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

//运单查询
@RestController
@RequestMapping("/api/waybillManage")
public class WaybillQueryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    WaybillQueryService waybillService;
    @Autowired
    AppConfiguration appConfiguration;

    //数据查询
    @RequestMapping(value = "/queryWaybillQuery", method = RequestMethod.GET)
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String logisticsNo,
            @RequestParam(required = false) String logisticsStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("运单查询参数:[startFlightTimes:%s,endFlightTimes:%s,logisticsNo:%s,billNo:%s,declareStatus:%s]", startFlightTimes, endFlightTimes, logisticsNo, billNo, logisticsStatus));
        Map<String, String> map = new HashMap<String, String>();

        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        map.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        map.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);
        map.put("billNo", billNo);
        map.put("orderNo", orderNo);
        map.put("logisticsNo", logisticsNo);
        map.put("logisticsStatus", logisticsStatus);

        map.put("start", start);
        map.put("length", length);
        map.put("end", end);
        map.put("extra_search", extra_search);
        map.put("entId", this.getCurrentUserEntId());
        map.put("roleId", this.getCurrentUserRoleId());
        //查询的状态可以使运单申报成功和运单状态申报成功
        map.put("dataStatus", StatusCode.YDSBCG);
        map.put("staDataStatus", StatusCode.YDZTSBCG);
        DataList<ImpLogisticsData> dataList;
        List<ImpLogisticsData> impLogisticsDataList;
        try {
            //查询数据
            impLogisticsDataList = this.waybillService.queryWaybillQueryDataList(map);
            //查询数据总数
            Integer count = this.waybillService.queryWaybillQueryCount(map);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(impLogisticsDataList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("查询运单查询数据失败", e);
            return new ResponseData("获取运单查询数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }

    /*
     * 运单详情查询
     */
    @RequestMapping("/seeWaybillDetail")
    public ResponseData waybillQueryById(
            @RequestParam(required = false) String guid,
            @RequestParam(required = false) String logistics_no
    ) {
        this.logger.debug(String.format("查询运单详情条件参数:[guid:%s,logistics_no:%s]", guid, logistics_no));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("guid", guid);
        paramMap.put("logisticsno", logistics_no);
        ImpLogisticsDetail impLogisticsDetail;
        try {
            impLogisticsDetail = waybillService.seeWaybillDetail(paramMap);
        } catch (Exception e) {
            this.logger.error("查询运单信息失败，logistics_no=" + logistics_no, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }

        return new ResponseData(impLogisticsDetail);
    }

    /*
    * 查询编辑运单详情
    * */
    @RequestMapping("/saveBillDetail")
    public ResponseData saveBillDetail(@Param("entryJson") String entryJson) {
        //订单信息json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        // 订单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");
        // 订单表体
        ArrayList<LinkedHashMap<String, String>> entryLists = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");
        Map<String, String> rtnMap = new HashMap<>();
        try {
            // 保存订单详情信息
            rtnMap = waybillService.saveBillDetail(entryHead, entryLists);
        } catch (Exception e) {
            logger.error("保存运单详细信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存运单详细信息时发生异常");
        }
        return new ResponseData(rtnMap);

    }

    /*
    * 运单回执详情
    * */
    @RequestMapping("/returnDetail")
    public ResponseData returnDetail(
            @RequestParam(required = false) String guid,
            @RequestParam(required = false) String logisticsNo
    ) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("guId", guid);
        paramMap.put("logisticsNo", logisticsNo);
        ImpLogistics impLogistics;
        try {
            impLogistics = waybillService.queryReturnDetail(paramMap);
        } catch (Exception e) {
            this.logger.error("查询回执详情信息失败，logistics_no=" + logisticsNo, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(impLogistics);
    }

    /**
     * 文件下载excel生成
     */
    @RequestMapping(value = "/downloadFile")
    public void downloadFile(
            @RequestParam String fileName,
            HttpServletRequest request,
            HttpServletResponse response) {
        fileName = fileName.replace("\\\\|/", "");
        String downloadFolder = this.appConfiguration.getDownloadFolder();
        String enterpriseId = this.getCurrentUserEntId();

        // 文件路径为：下载目录/企业ID/传入文件名
        String filePath = downloadFolder + File.separator + enterpriseId + File.separator + fileName;
        File file = new File(filePath);
        DownloadUtils.download(response, file, SystemConstants.HTTP_CONTENT_TYPE_EXCEL);
    }

    //清单数据Excel下载
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public ResponseData implogisticsListLoad(
            @RequestParam String billNo,
            @RequestParam String startStr,
            @RequestParam String length
    ) {
        Map<String, String> map = new HashMap<String, String>();
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));
        map.put("billNo", billNo);
        map.put("dataStatus", StatusCode.QDSBCG);
        map.put("start", start);
        map.put("length", length);
        map.put("end", end);

        List<ImpLogistics> impLogisticsList;
        String fileName;
        try {
            impLogisticsList = this.waybillService.queryImpLogisticsByBillNo(map);
            fileName = this.generateImpLogisticsListExcel(impLogisticsList);
        } catch (Exception e) {
            this.logger.error("物流数据下载时发生异常", e);
            return new ResponseData("物流数据下载时发生异常", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(fileName);
    }

    //导出excel
    private String generateImpLogisticsListExcel(List<ImpLogistics> list) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("提运单号下的物流数据");
        HSSFRow row = sheet.createRow(0);

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12); //字体高度
        font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
        font.setFontName("宋体"); //字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度

        // 设置表头样式
        HSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setFont(font);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
        headStyle.setWrapText(true);

        // 设置表体样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
        style.setWrapText(true);
        String[] head = new String[]{"序号", "提运单号", "物流运单号", "订单号", "物流企业编码", "物流企业名称", "重量"};

        HSSFCell cell;
        for (int i = 0; i < head.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head[i]);
            cell.setCellStyle(headStyle);
        }

        ImpLogistics impLogistics;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HSSFCell cell0, cell1, cell2, cell3, cell4, cell5, cell6;
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            impLogistics = list.get(i);

            // 序号
            cell0 = row.createCell(0);
            cell0.setCellValue((i + 1));
            cell0.setCellStyle(style);

            // 提运单号
            cell1 = row.createCell(1);
            cell1.setCellValue(impLogistics.getBill_no());
            cell1.setCellStyle(style);

            // 物流运单号
            cell2 = row.createCell(2);
            cell2.setCellValue(impLogistics.getLogistics_no());
            cell2.setCellStyle(style);

            // 订单号
            cell3 = row.createCell(3);
            cell3.setCellValue(impLogistics.getOrder_no());
            cell3.setCellStyle(style);

            // 物流企业编码
            cell4 = row.createCell(4);
            cell4.setCellValue(impLogistics.getLogistics_code());
            cell4.setCellStyle(style);

            // 物流企业名称
            cell5 = row.createCell(5);
            cell5.setCellValue(impLogistics.getLogistics_name());
            cell5.setCellStyle(style);

            // 重量
            cell6 = row.createCell(6);
            cell6.setCellValue(impLogistics.getWeight());
            cell6.setCellStyle(style);

        }

        // 调整单元格宽度自适应
        for (int i = 0; i < head.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 第六步，将文件存到指定位置
        String fileName = sdf + "-" + DateTools.getDateTimeStr17String(new Date()) + ".xls";
        String generatePath = this.appConfiguration.getDownloadFolder() + File.separator + this.getCurrentUserEntId();
        String filePath = generatePath + File.separator + fileName;
        File file = new File(generatePath);
        if (!file.exists()) file.mkdirs();
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            wb.write(fos);
            fos.close();
        } catch (Exception e) {
            this.logger.error("生成商品统计下载excel失败，filePath=" + filePath, e);
        }
        return fileName;
    }

}
