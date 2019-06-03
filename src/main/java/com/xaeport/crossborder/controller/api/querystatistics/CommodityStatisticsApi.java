package com.xaeport.crossborder.controller.api.querystatistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.querystatistics.CommodityStatisticsService;
import com.xaeport.crossborder.tools.DateTools;
import com.xaeport.crossborder.tools.DownloadUtils;
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

@RestController
@RequestMapping("/api/commodity")
public class CommodityStatisticsApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CommodityStatisticsService commodityStatisticsService;
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    LoadData loadData;

    @RequestMapping("/queryCommodity")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String ieFlag,
            @RequestParam(required = false) String customCode
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,customsCode:%s]", startFlightTimes, endFlightTimes, customCode));
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        paramMap.put("ieFlag", ieFlag);
        paramMap.put("customCode", customCode);
//        paramMap.put("dataStatus", StatusCode.QDSBCG);
        paramMap.put("returnStatus", StatusCode.FX);

        List<ImpInventory> resultList;
        try {
            resultList = this.commodityStatisticsService.queryCommodity(paramMap);
        } catch (Exception e) {
            this.logger.error("查询商品统计数据失败", e);
            return new ResponseData("获取商品统计数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);
    }

    /**
     * 文件下载excel生成
     */
    @RequestMapping(value = "/query/downloadFile")
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
    public ResponseData SameDocumentsDownLoad(
            @RequestParam String startFlightTimes,
            @RequestParam String endFlightTimes,
            @RequestParam String ieFlag,
            @RequestParam String customCode,
            @RequestParam String startStr,
            @RequestParam String length

    ) {
        Map<String, String> map = new HashMap<String, String>();
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        map.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        map.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);
        map.put("ieFlag", ieFlag);
        map.put("customCode", customCode);
        map.put("dataStatus", StatusCode.QDSBCG);
        map.put("returnStatus", StatusCode.FX);

        map.put("start", start);
        map.put("length", length);
        map.put("end", end);

        List<ImpInventory> impInventoryList = null;
        String fileName;

        try {
            impInventoryList = this.commodityStatisticsService.queryCommodity(map);
            fileName = this.generateInventoryInfoExcel(impInventoryList, startFlightTimes, endFlightTimes);
        } catch (Exception e) {
            this.logger.error("商品统计下载时发生异常", e);
            return new ResponseData("商品统计下载时发生异常", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(fileName);
    }

    //导出excel
    private String generateInventoryInfoExcel(List<ImpInventory> list, String startFlightTimes, String endFlightTimes) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("商品统计下载");
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
        String[] head = new String[]{"序号", "商品品类", "商品编码", "商品数量", "商品总价", "币制", "税额"};

        HSSFCell cell;
        for (int i = 0; i < head.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head[i]);
            cell.setCellStyle(headStyle);
        }

        ImpInventory impInventory;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HSSFCell cell0, cell1, cell2, cell3, cell4, cell5, cell6;
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            impInventory = list.get(i);

            // 序号
            cell0 = row.createCell(0);
            cell0.setCellValue((i + 1));
            cell0.setCellStyle(style);

            // 商品品类
            cell1 = row.createCell(1);
            cell1.setCellValue(impInventory.getProduct_name());
            cell1.setCellStyle(style);

            // 商品编码
            cell2 = row.createCell(2);
            cell2.setCellValue(impInventory.getG_code());
            cell2.setCellStyle(style);

            // 商品数量
            cell3 = row.createCell(3);
            cell3.setCellValue(impInventory.getAmount());
            cell3.setCellStyle(style);

            // 商品总价
            cell4 = row.createCell(4);
            cell4.setCellValue(impInventory.getTotalPrice());
            cell4.setCellStyle(style);

            // 币制
            cell5 = row.createCell(5);
            cell5.setCellValue(impInventory.getCurrency());
            cell5.setCellStyle(style);

            // 税额
            cell6 = row.createCell(6);
            cell6.setCellValue(impInventory.getTotalTax());
            cell6.setCellStyle(style);

        }

        // 调整单元格宽度自适应
        for (int i = 0; i < head.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 第六步，将文件存到指定位置
        String fileName = startFlightTimes + "-" + DateTools.getDateTimeStr17String(new Date()) + "-" + endFlightTimes + ".xls";
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
