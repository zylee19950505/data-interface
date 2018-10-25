package com.xaeport.crossborder.controller.api.querystatistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.queryStatistics.InventoryQueryService;
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
@RequestMapping("/api/queryStatistics")
public class InventoryQueryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    InventoryQueryService inventoryQueryService;
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    LoadData loadData;

    @RequestMapping("/queryInventory")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String ieFlag,
            @RequestParam(required = false) String entId,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String invtNo,
            @RequestParam(required = false) String gName,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,invtNo:%s]", startFlightTimes, endFlightTimes, invtNo));
        Map<String, String> paramMap = new HashMap<String, String>();
        //查询参数
        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("startFlightTimes", startFlightTimes);
        paramMap.put("endFlightTimes", endFlightTimes);
        paramMap.put("ieFlag", ieFlag);
        paramMap.put("entId", entId);
        paramMap.put("billNo", billNo);
        paramMap.put("invtNo", invtNo);
        paramMap.put("gName", gName);
        paramMap.put("dataStatus", StatusCode.QDSBCG);
        paramMap.put("returnStatus", StatusCode.FX);

        //分页参数
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);

        //更新人
        DataList<ImpInventoryHead> dataList = null;
        List<ImpInventoryHead> resultList = null;
        try {
            //查询列表
            resultList = this.inventoryQueryService.queryInventoryQueryList(paramMap);
            //查询总数
            Integer count = this.inventoryQueryService.queryInventoryQueryCount(paramMap);
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

    @RequestMapping(value = "/EbusinessEnt")
    public ResponseData queryEbusinessEnt() {
        List<Enterprise> enterpriseList;
        try {
            enterpriseList = this.inventoryQueryService.queryEbusinessEnt();
        } catch (Exception e) {
            this.logger.error("查询电商企业数据失败", e);
            return new ResponseData("查询电商企业数据失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(enterpriseList);
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
            @RequestParam String entId,
            @RequestParam String billNo,
            @RequestParam String invtNo,
            @RequestParam String gName,

            @RequestParam String startStr,
            @RequestParam String length

    ) {
        Map<String, String> map = new HashMap<String, String>();
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        map.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        map.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);
        map.put("ieFlag", ieFlag);
        map.put("entId", entId);
        map.put("billNo", billNo);
        map.put("invtNo", invtNo);
        map.put("gName", gName);
        map.put("dataStatus", StatusCode.QDSBCG);
        map.put("returnStatus", StatusCode.FX);

        map.put("start", start);
        map.put("length", length);
        map.put("end", end);

        List<ImpInventory> impInventoryList = null;
        String fileName;

        try {
            impInventoryList = this.inventoryQueryService.queryInventoryExcelList(map);
            fileName = this.generateInventoryInfoExcel(impInventoryList, startFlightTimes, endFlightTimes);
        } catch (Exception e) {
            this.logger.error("清单数据下载时发生异常", e);
            return new ResponseData("清单数据下载时发生异常", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(fileName);
    }

    //导出excel
    private String generateInventoryInfoExcel(List<ImpInventory> list, String startFlightTimes, String endFlightTimes) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("清单数据下载");
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
        String[] head = new String[]{"序号", "清单编号", "提运单号", "运单编号", "申报日期", "总税额", "毛重", "净重", "订购人姓名", "订购人身份证号码", "订购人电话", "收件地址", "商品名称", "商品单价", "商品总价", "单个税额"};

        HSSFCell cell;
        for (int i = 0; i < head.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head[i]);
            cell.setCellStyle(headStyle);
        }

        ImpInventory impInventory;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HSSFCell cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15;
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            impInventory = list.get(i);

            // 序号
            cell0 = row.createCell(0);
            cell0.setCellValue((i + 1));
            cell0.setCellStyle(style);

            // 清单编号
            cell1 = row.createCell(1);
            cell1.setCellValue(impInventory.getInvt_no());
            cell1.setCellStyle(style);

            // 提运单号
            cell2 = row.createCell(2);
            cell2.setCellValue(impInventory.getBill_no());
            cell2.setCellStyle(style);

            // 运单编号
            cell3 = row.createCell(3);
            cell3.setCellValue(impInventory.getLogistics_no());
            cell3.setCellStyle(style);

            // 申报日期
            cell4 = row.createCell(4);
            cell4.setCellValue(sdf.format(impInventory.getApp_time()));
            cell4.setCellStyle(style);

            // 总税额
            cell5 = row.createCell(5);
            cell5.setCellValue(impInventory.getTotal_tax());
            cell5.setCellStyle(style);

            // 毛重
            cell6 = row.createCell(6);
            cell6.setCellValue(impInventory.getGross_weight());
            cell6.setCellStyle(style);

            // 净重
            cell7 = row.createCell(7);
            cell7.setCellValue(impInventory.getNet_weight());
            cell7.setCellStyle(style);

            // 订购人姓名
            cell8 = row.createCell(8);
            cell8.setCellValue(impInventory.getBuyer_name());
            cell8.setCellStyle(style);

            // 订购人身份证号码
            cell9 = row.createCell(9);
            cell9.setCellValue(impInventory.getBuyer_id_number());
            cell9.setCellStyle(style);

            // 订购人电话
            cell10 = row.createCell(10);
            cell10.setCellValue(impInventory.getBuyer_telephone());
            cell10.setCellStyle(style);

            // 收件地址
            cell11 = row.createCell(11);
            cell11.setCellValue(impInventory.getConsignee_address());
            cell11.setCellStyle(style);

            // 商品名称
            cell12 = row.createCell(12);
            cell12.setCellValue(impInventory.getG_name());
            cell12.setCellStyle(style);

            // 商品单价
            cell13 = row.createCell(13);
            cell13.setCellValue(impInventory.getPrice());
            cell13.setCellStyle(style);

            // 商品总价
            cell14 = row.createCell(14);
            cell14.setCellValue(impInventory.getTotal_price());
            cell14.setCellStyle(style);

            // 单个税额
            cell15 = row.createCell(15);
            cell15.setCellValue(impInventory.getSingle_tax());
            cell15.setCellStyle(style);

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
            this.logger.error("生成清单数据下载excel失败，filePath=" + filePath, e);
        }
        return fileName;
    }

}
