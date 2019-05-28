package com.xaeport.crossborder.controller.api.bondedienter;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.BondInvt;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.data.ExcelDataInstance;
import com.xaeport.crossborder.excel.read.ReadExcel;
import com.xaeport.crossborder.service.bondedienter.CrtEnterInventoryService;
import com.xaeport.crossborder.tools.DownloadUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/api/crtEnterInven")
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
            @RequestParam(value = "file", required = false) MultipartFile file,
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
        String etps_inner_invt_no;
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
                if (excelMap.containsKey("error")) {
                    httpSession.removeAttribute("importTime");
                    rtnMap.put("result", "false");
                    rtnMap.put("msg", "入库失败,电商海关编码不一致!");
                    return new ResponseData(rtnMap);
                }
                etps_inner_invt_no = this.crtEnterInventoryService.createEnterInventory(excelMap, user);//数据创建对应的数据
                if (etps_inner_invt_no != "") {
                    this.log.info("入库耗时" + (System.currentTimeMillis() - starTime));
                    httpSession.removeAttribute("importTime");
                    rtnMap.put("result", "true");
                    rtnMap.put("msg", "跨境电子商务进口订单导入成功");
                    rtnMap.put("data", etps_inner_invt_no);
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
     * 新建入区核注清单
     */
    @RequestMapping(value = "seeEnterInventoryDetail")
    public ResponseData seeEnterInventoryDetail(
            @RequestParam(required = false) String etps_inner_invt_no
    ) {
        if (StringUtils.isEmpty(etps_inner_invt_no))
            return new ResponseData("etpsInnerInvtNo为空,查询失败", HttpStatus.FORBIDDEN);
        this.log.debug(String.format("新建入区核注清单条件参数:[etps_inner_invt_no:%s]", etps_inner_invt_no));
        BondInvt dataList;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("etps_inner_invt_no", etps_inner_invt_no);
        try {
            dataList = this.crtEnterInventoryService.seeEnterInventoryDetail(paramMap);
        } catch (Exception e) {
            this.log.error("查询新建的入区核注清单", e);
            return new ResponseData("查询新建的入区核注清单", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }

    /**
     * 新建入区核注清单的保存
     */
    @RequestMapping(value = "saveEnterInvDetail")
    public ResponseData saveEnterInvDetail(
            @Param("entryJson") String entryJson
    ) {
        //数据整体封装JSON
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        //入区核注清单表头信息
        LinkedHashMap<String, String> bondInvtHead = (LinkedHashMap<String, String>) object.get("entryHead");
        // 入区核注清单表体信息
        ArrayList<LinkedHashMap<String, String>> bondInvtDts = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");

        String dcl_etps_nm = (String) object.get("dcl_etps_nm");
        bondInvtHead.put("dcl_etps_nm", dcl_etps_nm);

        Map<String, String> rtnMap = new HashMap<>();
        Users users = this.getCurrentUsers();
        try {
            // 保存表头信息
            rtnMap = this.crtEnterInventoryService.updateEnterInventoryDetail(bondInvtHead, bondInvtDts, users);
        } catch (Exception e) {
            log.error("保存入区核注清单表头时异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存入区核注清单表头时异常");
        }
        return new ResponseData(rtnMap);
    }



    /**
     * 取消时返回并删除入区核注清单
     */
    @RequestMapping(value = "/deleteEnterInven/{etps_inner_invt_no}", method = RequestMethod.DELETE)
    public ResponseData deleteEnterInven(
            @PathVariable(value = "etps_inner_invt_no") String etps_inner_invt_no
    ) {
        this.log.debug(String.format("取消并删除[etps_inner_invt_no:%s]", etps_inner_invt_no));
        this.crtEnterInventoryService.deleteEnterInven(etps_inner_invt_no);
        return new ResponseData();
    }

    /**
     * excel 入区核注清单模板下载
     */
    @RequestMapping(value = "/downloadFile")
    public void excelModelDownload(
            HttpServletResponse response,
            @RequestParam(value = "type") String type
    ) {
        Map<String, String> map = appConfiguration.getModelFolder();
        String filePath = map.get(type);
        File file = new File(filePath);
        DownloadUtils.download(response, file, SystemConstants.HTTP_CONTENT_TYPE_EXCEL);
    }

}

