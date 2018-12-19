package com.xaeport.crossborder.controller.api.bondedIEnter;

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
import com.xaeport.crossborder.service.bondedIEnter.CrtEnterInventoryService;
import com.xaeport.crossborder.tools.DownloadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crtEnterInven")
public class CrtEnterInventoryApi extends BaseApi {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    CrtEnterInventoryService crtEnterInventoryService;

    /**
     * 新快件上传
     *
     * @param file // 上传的文件
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseData MultipartFile(
            @RequestParam(value = "file", required = false) MultipartFile file,//出口国际邮件模板,
            HttpServletRequest request
    ) {
        if (file == null) return new ResponseData("请选择需要导入的文件");
        HttpSession httpSession = request.getSession();

        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) return new ResponseData("导入文件不为excel文件，请重新选择");
        if (file.getSize() > (5 * 1024 * 1024)) return new ResponseData("文件大小超过5M,请重新选择文件");

        //获取企业信息
        Users user = this.getCurrentUsers();
        InputStream inputStream;
        ReadExcel readExcel = new ReadExcel();
        Map<String, Object> map;

        Map<String, String> rtnMap = new HashMap<String, String>();

        String bondInvtNo;
        long starTime = System.currentTimeMillis();

        try {
            inputStream = file.getInputStream();
            String type = "enterInventory";
            map = readExcel.readExcelData(inputStream, fileName, type);
            if (CollectionUtils.isEmpty(map)) return new ResponseData(String.format("导入<%s>为空", fileName));
            if (map.containsKey("error")) {
                httpSession.removeAttribute("importTime");
                rtnMap.put("result", "false");
                rtnMap.put("msg", map.get("error").toString());
                return new ResponseData(rtnMap);
            } else {
                List<List<String>> excelDataList = (List<List<String>>) map.get("excelData");
                if (excelDataList.size() <= 1) return new ResponseData(String.format("导入<%s>中数据为空", fileName));

                Map<String, Object> excelMap;
                ExcelData excelData = ExcelDataInstance.getExcelDataObject(type);
                excelMap = excelData.getExcelData(excelDataList);
                bondInvtNo = this.crtEnterInventoryService.createEnterInventory(excelMap, user);//数据创建对应的数据
                if (bondInvtNo != "") {
                    this.log.info("入库耗时" + (System.currentTimeMillis() - starTime));
                    httpSession.removeAttribute("importTime");
                    rtnMap.put("result", "true");
                    rtnMap.put("msg", "跨境电子商务进口订单导入成功");
                    rtnMap.put("data", bondInvtNo);
                    return new ResponseData(rtnMap);
                } else {
                    httpSession.removeAttribute("importTime");
                    rtnMap.put("result", "false");
                    rtnMap.put("msg", "入库失败");
                    return new ResponseData(rtnMap);
                }
            }


        } catch (IOException e) {
            this.log.error(String.format("导入文件模板错误，文件名:%s", fileName), e);
            httpSession.removeAttribute("importTime");
            rtnMap.put("result", "false");
            rtnMap.put("msg", "导入文件模板错误");
            return new ResponseData(rtnMap);
        } catch (Exception r) {
            this.log.error(String.format("导入文件失败，文件名:%s", fileName), r);
            httpSession.removeAttribute("importTime");
            rtnMap.put("result", "false");
            rtnMap.put("msg", "入库失败");
            return new ResponseData(rtnMap);
        }
    }


    /**
     * excel 跨境电子商务新建核放单模板下载
     */
    @RequestMapping(value = "/downloadFile")
    public void excelModelDownload(HttpServletResponse response,
                                   @RequestParam(value = "type") String type) {
        Map<String, String> map = appConfiguration.getModelFolder();
        String filePath = map.get(type);
        File file = new File(filePath);
        DownloadUtils.download(response, file, SystemConstants.HTTP_CONTENT_TYPE_EXCEL);
    }

}
