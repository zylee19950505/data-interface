package com.xaeport.crossborder.controller.api.waybillmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.data.ExcelDataInstance;
import com.xaeport.crossborder.excel.read.ReadExcel;
import com.xaeport.crossborder.service.waybillmanage.StatusImportService;
import com.xaeport.crossborder.tools.DownloadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statusImport")
public class StatusImportApi extends BaseApi {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    StatusImportService statusImportService;

    /**
     * 新快件上传
     *
     * @param statusFile // 上传的文件
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseData MultipartFile(@RequestParam(value = "statusFile", required = false) MultipartFile statusFile,//出口国际邮件模板
                                      HttpServletRequest request
    ) {
        HttpSession httpSession = request.getSession();
        if (statusFile == null) return new ResponseData("请选择要导入的文件");

        String fileName = statusFile.getOriginalFilename();
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) return new ResponseData("导入文件不为excel文件，请重新选择");

        if (statusFile.getSize() > (5 * 1024 * 1024)) return new ResponseData("文件大小超过5M，请重新选择文件");

        //获取企业信息
        Users user = this.getCurrentUsers();

        int curValue = this.getHashingValue(String.format("%s", user.getId()));
        if (this.isRepeatSubmit(httpSession.getAttribute("userIdCode"), curValue)) return new ResponseData("不允许重复提交");
        httpSession.setAttribute("userIdCode", this.getHashingValue(String.format("%s", user.getId())));

        InputStream inputStream;
        ReadExcel readExcel = new ReadExcel();
        Map<String, Object> map;
        int flag;
        long startTime = System.currentTimeMillis();
        try {
            inputStream = statusFile.getInputStream();
            String type = "waybillStatus";
            map = readExcel.readExcelData(inputStream, fileName, type);//读取excel数据
            if (CollectionUtils.isEmpty(map)) return new ResponseData(String.format("导入<%s>为空", fileName));//获取excel为空
            if (map.containsKey("error")) {
                httpSession.removeAttribute("userIdCode");
                return new ResponseData(map.get("error"));//返回excel校验的错误信息
            } else {
                List<List<String>> excelDataList = (List<List<String>>) map.get("excelData");//返回excel的正确数据
                if (excelDataList.size() <= 1) return new ResponseData(String.format("导入<%s>中数据为空", fileName));

                Map<String, Object> excelMap;
                ExcelData excelData = ExcelDataInstance.getExcelDataObject(type);
                excelMap = excelData.getExcelData(excelDataList);

                //判断这个物流运单号在运单表里是否存在,
                String logisticsNo = this.statusImportService.getLogisticsNoCount(excelMap);
                if (!"true".equals(logisticsNo)) {
                    httpSession.removeAttribute("userIdCode");
                    return new ResponseData("物流运单编号【" + logisticsNo + "】不存在,请检查");
                }

                String logisticsNoCount = this.statusImportService.getLogisticsStatusNoCount(excelMap);
                if (!logisticsNoCount.equals("0")) {
                    httpSession.removeAttribute("userIdCode");
                    return new ResponseData("物流运单编号【" + logisticsNoCount + "】不能重复");
                }

                //判断运单是否申报成功,是否有回执
                String logisticsSuccess = this.statusImportService.getLogisticsSuccess(excelMap);
                if (!"true".equals(logisticsSuccess)) {
                    //这个没变的话,就说明没有收到回执,或者状态还没有变为CBDS41
                    httpSession.removeAttribute("userIdCode");
                    return new ResponseData("物流运单编号【" + logisticsSuccess + "】申报未成功或者未收到回执");//申报未成功或者未收到回执
                }

                flag = this.statusImportService.createWaybillForm(excelMap, user);//数据创建对应的数据
                if (flag == 0) {
                    this.log.info("入库耗时" + (System.currentTimeMillis() - startTime));
                    //有回执,也申报成功了,就把运单状态改为CBDS5
                    this.statusImportService.updateLogisticsStatus(excelMap);
                    this.statusImportService.updateLogistics(excelMap);
                    httpSession.removeAttribute("userIdCode");
                    return new ResponseData(String.format("跨境电子商务运单状态导入成功！"));
                } else if (flag == 1) {
                    httpSession.removeAttribute("userIdCode");
                    return new ResponseData("同一批物流运单编号不可重复！");
                } else {
                    httpSession.removeAttribute("userIdCode");
                    return new ResponseData("入库失败");
                }

            }
        } catch (IOException e) {
            this.log.error(String.format("导入文件模板错误，文件名:%s", fileName), e);
            httpSession.removeAttribute("userIdCode");
            return new ResponseData("导入文件模板错误");
        } catch (Exception r) {
            this.log.error(String.format("导入文件失败，文件名:%s", fileName), r);
            httpSession.removeAttribute("userIdCode");
            return new ResponseData("入库失败");
        }

    }

    /**
     * excel 跨境电子商务进口订单模板下载
     */
    @RequestMapping(value = "/downloadFile")
    public void excelModelDownload(HttpServletResponse response,
                                   @RequestParam(value = "type") String type) {
        Map<String, String> map = appConfiguration.getModelFolder();
        String filePath = map.get(type);
        File file = new File(filePath);
        DownloadUtils.download(response, file, SystemConstants.HTTP_CONTENT_TYPE_EXCEL);
    }

    private int getHashingValue(String str) {
        return str.hashCode();
    }

    //用于判断同一批订单号、进口时间，企业在导入时发生重复
    private boolean isRepeatSubmit(Object preValue, int curValue) {
        boolean flag;
        if (StringUtils.isEmpty(preValue)) {
            return false;
        }
        String str = preValue.toString();
        int value = Integer.parseInt(str);
        flag = value == curValue;
        return flag;
    }

}
