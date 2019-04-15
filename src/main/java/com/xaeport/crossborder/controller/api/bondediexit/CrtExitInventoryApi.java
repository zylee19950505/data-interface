package com.xaeport.crossborder.controller.api.bondediexit;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.service.bondediexit.CrtExitInventoryService;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/bondediexit")
public class CrtExitInventoryApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CrtExitInventoryService crtExitInventoryService;

    /*
     *  保税清单数据查询
     */
    @RequestMapping(value = "/querycrtexitinventory", method = RequestMethod.GET)
    public ResponseData queryCrtExitInventory(
            @RequestParam(required = false) String billNo,
            @RequestParam(required = false) String returnStatus,
            @RequestParam(required = false) String customCode,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询进口保税清单数据参数:[returnStatus:%s]", returnStatus));
        Map<String, String> paramMap = new HashMap<String, String>();

//        String startStr = request.getParameter("start");
//        String length = request.getParameter("length");
//        String extra_search = request.getParameter("extra_search");
//        String draw = request.getParameter("draw");
//        String start = String.valueOf((Integer.parseInt(startStr) + 1));
//        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        Users users = this.getCurrentUsers();

//        paramMap.put("start", start);
//        paramMap.put("length", length);
//        paramMap.put("end", end);
//        paramMap.put("extra_search", extra_search);
        paramMap.put("entId", users.getEnt_Id());
        paramMap.put("roleId", users.getRoleId());
        paramMap.put("port", users.getPort());

        paramMap.put("billNo", billNo);
        paramMap.put("returnStatus", returnStatus);
        paramMap.put("ebcCode", customCode);
        paramMap.put("businessType", SystemConstants.T_IMP_BOND_INVEN);

//        DataList<ImpInventory> dataList;
        List<ImpInventory> resultList;
        try {
//            //查询列表
//            resultList = this.crtExitInventoryService.queryCrtEInventoryList(paramMap);
//            //查询总数
//            Integer count = this.crtExitInventoryService.queryCrtEInventoryCount(paramMap);
//            dataList = new DataList<>();
//            dataList.setDraw(draw);
//            dataList.setData(resultList);
//            dataList.setRecordsTotal(count);
//            dataList.setRecordsFiltered(count);

            resultList = this.crtExitInventoryService.queryCrtEInventoryData(paramMap);

        } catch (Exception e) {
            this.logger.error("查询进口保税清单数据失败", e);
            return new ResponseData("查询进口保税清单数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(resultList);
    }

    /**
     * 新建出区核注清单数据
     **/
    @RequestMapping(value = "/crtexitinventory", method = RequestMethod.GET)
    public ResponseData crtexitinventory(
            @RequestParam(required = false) String dataInfo,
            @RequestParam(required = false) String customsCode
    ) {
        Users users = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateNowStr = sdf.format(date);

        paramMap.put("roleId", users.getRoleId());
        paramMap.put("port", users.getPort());
        paramMap.put("businessType", SystemConstants.T_IMP_BOND_INVEN);
        paramMap.put("returnStatus", "800");
        paramMap.put("ebcCode", customsCode);
        paramMap.put("billNo", dataInfo);

        paramMap.put("ent_id", users.getEnt_Id());
        paramMap.put("ent_customs_code", users.getEnt_Customs_Code());
        paramMap.put("credit_code", users.getCredit_code());
        paramMap.put("etps_inner_invt_no", "HZQD" + users.getEnt_Customs_Code() + "E" + dateNowStr + (IdUtils.getShortUUId()).substring(0, 4));

        ExitBondInvt exitBondInvt = new ExitBondInvt();
        BondInvtBsc bondInvtBsc;
        List<NemsInvtCbecBillType> nemsInvtCbecBillTypeList;
        try {
            //查询列表
            bondInvtBsc = this.crtExitInventoryService.queryBondInvtBsc(paramMap);
            nemsInvtCbecBillTypeList = this.crtExitInventoryService.queryNemsInvtCbecBillTypeList(paramMap);
            exitBondInvt.setBondInvtBsc(bondInvtBsc);
            exitBondInvt.setNemsInvtCbecBillTypeList(nemsInvtCbecBillTypeList);
        } catch (Exception e) {
            this.logger.error("获取出区核注清单数据失败", e);
            return new ResponseData("获取出区核注清单数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(exitBondInvt);

    }

    //保存出区核注清单信息
    @RequestMapping("/saveExitInventory")
    public ResponseData saveManifestInfo(@Param("entryJson") String entryJson) {
        //出区核注清单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        //出区核注清单表头
        LinkedHashMap<String, String> BondInvtBsc = (LinkedHashMap<String, String>) object.get("BondInvtBsc");
        //出区核注清单表体
        ArrayList<LinkedHashMap<String, String>> nemsInvtCbecBillTypeList = (ArrayList<LinkedHashMap<String, String>>) object.get("nemsInvtCbecBillTypeList");
        //电商企业编码
        String ebcCode = (String) object.get("customsCode");

        Users userInfo = this.getCurrentUsers();
        Map<String, String> map = new HashMap<>();
        try {
            // 保存详情信息
            map = this.crtExitInventoryService.saveExitBondInvt(BondInvtBsc, nemsInvtCbecBillTypeList, userInfo, ebcCode);
        } catch (Exception e) {
            logger.error("保存出区核注清单时发生异常", e);
            map.put("result", "false");
            map.put("msg", "保存出区核注清单时发生异常");
        }
        return new ResponseData(map);
    }

    @RequestMapping(value = "/EbusinessEnt")
    public ResponseData queryEbusinessEnt() {
        List<Enterprise> enterpriseList;
        Users users = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();

        paramMap.put("port", users.getPort());
        paramMap.put("roleId", users.getRoleId());
        try {
            enterpriseList = this.crtExitInventoryService.queryEbusinessEnt(paramMap);
        } catch (Exception e) {
            this.logger.error("查询电商企业数据失败", e);
            return new ResponseData("查询电商企业数据失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(enterpriseList);
    }


}
