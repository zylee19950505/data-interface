package com.xaeport.crossborder.controller.api.bondedIEnter;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
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
     * @param file       // 上传的文件
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseData MultipartFile(
            @RequestParam(value = "file", required = false) MultipartFile file,//出口国际邮件模板,
            HttpServletRequest request
    ) {
        if (file == null) return new ResponseData("请选择需要导入的文件");
        HttpSession httpSession = request.getSession();

        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith("xls")&&!fileName.endsWith("xlsx")) return new ResponseData("导入文件不为excel文件，请重新选择");
        if (file.getSize()>(5 * 1024 * 1024)) return new ResponseData("文件大小超过5M,请重新选择文件");

        InputStream inputStream;
        ReadExcel readExcel = new ReadExcel();
        Map<String,Object> map;
        int flag;
        long starTime = System.currentTimeMillis();

        try {
            inputStream = file.getInputStream();
            String type = "enterInventory";
            map = readExcel.readExcelData(inputStream,fileName,type);
            if (CollectionUtils.isEmpty(map)) return new ResponseData(String.format("导入<%s>为空", fileName));
            if (map.containsKey("error")){
                httpSession.removeAttribute("importTime");
                return new ResponseData(map.get("error"));
            }else{

            }



        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){

        }


        return new ResponseData();
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
