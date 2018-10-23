package com.xaeport.crossborder.controller.api.detaillistmanage;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryDetail;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.detaillistmanage.DetailQueryService;
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

@RestController
@RequestMapping("/api/detailManage")
public class DetailQueryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DetailQueryService detailQueryService;
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    LoadData loadData;

    /*
 * 邮件申报查询
 */
    @RequestMapping("/queryDetailQuery")
    public ResponseData queryOrderDeclare(
            @RequestParam(required = false) String startFlightTimes,
            @RequestParam(required = false) String endFlightTimes,
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String logisticsNo,
            @RequestParam(required = false) String returnStatus,
            //gName
            @RequestParam(required = false) String gName,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询邮件申报条件参数:[startFlightTimes:%s,endFlightTimes:%s,orderNo:%s]", startFlightTimes, endFlightTimes, orderNo));
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
        paramMap.put("billNo", billNo);
        paramMap.put("orderNo", orderNo);
        paramMap.put("logisticsNo", logisticsNo);
//        paramMap.put("gName", gName);
        //分页参数
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);

        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());
        //类型参数
        paramMap.put("dataStatus", StatusCode.QDSBCG);
        if (!StringUtils.isEmpty(returnStatus)) {
            paramMap.put("returnStatus", returnStatus);
        } else {
            paramMap.put("returnStatus", "");
        }

        //更新人
        DataList<ImpInventory> dataList = null;
        List<ImpInventory> resultList = null;
        try {
            //查询列表
            resultList = this.detailQueryService.queryInventoryQueryList(paramMap);
            //查询总数
            Integer count = this.detailQueryService.queryInventoryQueryCount(paramMap);
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

    /*
* 点击查看邮件详情
* */
    @RequestMapping("/seeOrderDetail")
    public ResponseData seeOrderDetail(
            @RequestParam(required = false) String guid
    ) {
        if (StringUtils.isEmpty(guid)) return new ResponseData("订单为空", HttpStatus.FORBIDDEN);
        this.logger.debug(String.format("查询清单条件参数:[guid:%s]", guid));
        ImpInventoryDetail impInventoryDetail;
        try {
            impInventoryDetail = detailQueryService.getImpInventoryDetail(guid);
        } catch (Exception e) {
            this.logger.error("查询分单信息失败，entryHeadId=" + guid, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(impInventoryDetail);
    }

    @RequestMapping("/seeInventoryRec")
    public ResponseData seeInventoryRec(
            @RequestParam(required = false) String guid
    ) {
        if (StringUtils.isEmpty(guid)) return new ResponseData("订单为空", HttpStatus.FORBIDDEN);
        this.logger.debug(String.format("查询清单条件参数:[guid:%s]", guid));
        ImpInventoryHead impInventoryHead;
        try {
            impInventoryHead = detailQueryService.getImpInventoryRec(guid);
        } catch (Exception e) {
            this.logger.error("查询回执信息失败，entryHeadId=" + guid, e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(impInventoryHead);
    }

    //保存清单信息
    @RequestMapping("/saveInventoryDetail")
    public ResponseData saveInventoryDetail(@Param("entryJson") String entryJson) {
        //清单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        // 清单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");

        // 清单表体
        ArrayList<LinkedHashMap<String, String>> entryLists = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");

        Map<String, String> rtnMap = new HashMap<>();
        try {
            // 保存详情信息
            rtnMap = detailQueryService.saveInventoryDetail(entryHead, entryLists);
        } catch (Exception e) {
            logger.error("保存清单详细信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存清单详细信息时发生异常");
        }
        return new ResponseData(rtnMap);
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

    //四通单据数据下载
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public ResponseData SameDocumentsDownLoad(
            @RequestParam String startFlightTimes,
            @RequestParam String endFlightTimes,
            @RequestParam String billNo,
            @RequestParam String orderNo,
            @RequestParam String logisticsNo,
            @RequestParam String returnStatus,
            @RequestParam String gName,
            @RequestParam String startStr,
            @RequestParam String length

    ) {
        Map<String, String> map = new HashMap<String, String>();
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        map.put("startFlightTimes", StringUtils.isEmpty(startFlightTimes) ? null : startFlightTimes);
        map.put("endFlightTimes", StringUtils.isEmpty(endFlightTimes) ? null : endFlightTimes);
        map.put("billNo", billNo);
        map.put("orderNo", orderNo);
        map.put("logisticsNo", logisticsNo);
        map.put("gName", gName);
        map.put("returnStatus", returnStatus);
        map.put("dataStatus", StatusCode.QDSBCG);

        map.put("start", start);
        map.put("length", length);
        map.put("end", end);
        map.put("entId", this.getCurrentUserEntId());
        map.put("roleId", this.getCurrentUserRoleId());

        List<ImpInventory> impInventoryList = null;
        String fileName;

        try {
            impInventoryList = this.detailQueryService.queryInventoryExcelList(map);
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
        String[] head = new String[]{"序号", "订单编号", "运单编号", "订购人姓名", "订购人身份证号码", "订购人电话", "收件地址"};

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

            // 订单编号
            cell1 = row.createCell(1);
            cell1.setCellValue(impInventory.getOrder_no());
            cell1.setCellStyle(style);

            // 运单编号
            cell2 = row.createCell(2);
            cell2.setCellValue(impInventory.getLogistics_no());
            cell2.setCellStyle(style);

            // 订购人姓名
            cell3 = row.createCell(3);
            cell3.setCellValue(impInventory.getBuyer_name());
            cell3.setCellStyle(style);

            // 订购人身份证号码
            cell4 = row.createCell(4);
            cell4.setCellValue(impInventory.getBuyer_id_number());
            cell4.setCellStyle(style);

            // 订购人电话
            cell5 = row.createCell(5);
            cell5.setCellValue(impInventory.getBuyer_telephone());
            cell5.setCellStyle(style);

            // 收件地址
            cell6 = row.createCell(6);
            cell6.setCellValue(impInventory.getConsignee_address());
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
            this.logger.error("生成清单数据下载excel失败，filePath=" + filePath, e);
        }
        return fileName;
    }

}
