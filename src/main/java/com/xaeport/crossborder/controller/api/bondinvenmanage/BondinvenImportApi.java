package com.xaeport.crossborder.controller.api.bondinvenmanage;

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
import com.xaeport.crossborder.service.bondinvenmanage.BondinvenImportService;
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
@RequestMapping("/bondinvenmanage/bondinvenimport")
public class BondinvenImportApi extends BaseApi {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    BondinvenImportService bondinvenImportService;

    /**
     * 保税清单excel新快件上传
     *
     * @param importTime // 进口时间
     * @param emsNo      // 账册编号
     * @param file       // 上传的文件
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseData MultipartFile(
            @RequestParam(value = "billNo", required = false) String billNo,//提运单号
            @RequestParam(value = "importTime", required = false) String importTime,//进口时间
            @RequestParam(value = "emsNo", required = false) String emsNo,//账册编号
            @RequestParam(value = "file", required = false) MultipartFile file,//出口国际邮件模板
            HttpServletRequest request
    ) {
        HttpSession httpSession = request.getSession();
        if (billNo.isEmpty()) return new ResponseData("提运单号不能为空");
        if (importTime.isEmpty()) return new ResponseData("进口时间不能为空");
        if (emsNo.isEmpty()) return new ResponseData("账册编号不能为空");
        if (file == null) return new ResponseData("请选择要导入的文件");

        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) return new ResponseData("导入文件不为excel文件，请重新选择");

        if (billNo.length() > 37) return new ResponseData("提运单号长度超过37位，请重新输入");
        if (file.getSize() > (5 * 1024 * 1024)) return new ResponseData("文件大小超过5M，请重新选择文件");

        //获取企业信息
        Users user = this.getCurrentUsers();

        int curValue = this.getHashingValue(String.format("%s%s%s", importTime, emsNo, user.getId()));
        if (this.isRepeatSubmit(httpSession.getAttribute("importTime"), curValue)) return new ResponseData("不允许重复提交");
        httpSession.setAttribute("importTime", this.getHashingValue(String.format("%s%s%s", importTime, emsNo, user.getId())));

        InputStream inputStream;
        ReadExcel readExcel = new ReadExcel();
        Map<String, Object> map;
        int flag;
        long startTime = System.currentTimeMillis();
        try {
            inputStream = file.getInputStream();
            String type = "bondInven";
            map = readExcel.readExcelData(inputStream, fileName, type);//读取excel数据
            if (CollectionUtils.isEmpty(map)) return new ResponseData(String.format("导入<%s>为空", fileName));//获取excel为空
            if (map.containsKey("error")) {
                httpSession.removeAttribute("importTime");
                return new ResponseData(map.get("error"));//返回excel校验的错误信息
            } else {
                List<List<String>> excelDataList = (List<List<String>>) map.get("excelData");//返回excel的正确数据
                if (excelDataList.size() <= 1) return new ResponseData(String.format("导入<%s>中数据为空", fileName));

                Map<String, Object> excelMap;
                ExcelData excelData = ExcelDataInstance.getExcelDataObject(type);
                excelMap = excelData.getExcelData(excelDataList);

                String orderNoCount = this.bondinvenImportService.getOrderNoCount(excelMap);
                if (!orderNoCount.equals("0")) {
                    httpSession.removeAttribute("importTime");
                    return new ResponseData("订单号【" + orderNoCount + "】不能重复");
                }

                flag = this.bondinvenImportService.createBondInvenForm(excelMap, importTime, user, emsNo, billNo);//数据创建对应的数据
                if (flag == 0) {
                    this.log.info("入库耗时" + (System.currentTimeMillis() - startTime));
                    httpSession.removeAttribute("importTime");
                    return new ResponseData(String.format("跨境电子进口保税清单导入成功！"));
                } else if (flag == 3) {
                    httpSession.removeAttribute("importTime");
                    return new ResponseData("该账号库存余量不足");
                } else {
                    httpSession.removeAttribute("importTime");
                    return new ResponseData("入库失败");
                }
            }
        } catch (IOException e) {
            this.log.error(String.format("导入文件模板错误，文件名:%s", fileName), e);
            httpSession.removeAttribute("importTime");
            return new ResponseData("导入文件模板错误");
        } catch (Exception r) {
            this.log.error(String.format("导入文件失败，文件名:%s", fileName), r);
            httpSession.removeAttribute("importTime");
            return new ResponseData("入库失败");
        }

    }

    /**
     * excel 跨境电子进口保税清单模板下载
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
