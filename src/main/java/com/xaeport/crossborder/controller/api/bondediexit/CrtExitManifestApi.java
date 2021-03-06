package com.xaeport.crossborder.controller.api.bondediexit;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondediexit.CrtExitManifestService;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/bondediexit")
public class CrtExitManifestApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    CrtExitManifestService crtExitManifestService;

    /*
     *  出区核注清单数据查询
     */
    @RequestMapping(value = "/queryCrtExitManifest", method = RequestMethod.GET)
    public ResponseData queryDeliveryDeclare(
            @RequestParam(required = false) String bondInvtNo,
            @RequestParam(required = false) String returnStatus,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("查询出区核注清单数据条件参数:[bondInvtNo:%s,returnStatus:%s]", bondInvtNo, returnStatus));
        Map<String, String> paramMap = new HashMap<String, String>();

        String startStr = request.getParameter("start");
        String length = request.getParameter("length");
        String extra_search = request.getParameter("extra_search");
        String draw = request.getParameter("draw");
        String start = String.valueOf((Integer.parseInt(startStr) + 1));
        String end = String.valueOf((Integer.parseInt(startStr) + Integer.parseInt(length)));

        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("end", end);
        paramMap.put("extra_search", extra_search);

        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());
        paramMap.put("bondInvtNo", bondInvtNo);
        paramMap.put("returnStatus", returnStatus);
        paramMap.put("status", StatusCode.CQHZQDSBCG);

        DataList<BondInvtBsc> dataList;
        List<BondInvtBsc> resultList;
        try {
            //查询列表
            resultList = this.crtExitManifestService.queryEInventoryList(paramMap);
            //查询总数
            Integer count = this.crtExitManifestService.queryEInventoryCount(paramMap);
            dataList = new DataList<>();
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
        } catch (Exception e) {
            this.logger.error("出区核放单—获取出区核注清单数据失败", e);
            return new ResponseData("出区核放单—获取出区核注清单数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dataList);
    }

    //查询该核注清单已生成核放单信息
    @RequestMapping(value = "/querybondinvtisrepeat", method = RequestMethod.GET)
    public ResponseData queryBondinvtIsRepeat(
            @RequestParam(required = false) String submitKeys
    ) {
        Map<String, String> map = new HashMap<>();
        List<BondInvtBsc> bondInvtBscList;
        map.put("submitKeys", submitKeys);
        Integer count = 0;
        String passportUsedTypecd;
        try {
            bondInvtBscList = crtExitManifestService.queryBondinvtIsRepeat(map);
            for (BondInvtBsc bondInvtBsc : bondInvtBscList) {
                passportUsedTypecd = StringUtils.isEmpty(bondInvtBsc.getPassport_used_typecd()) ? "1" : bondInvtBsc.getPassport_used_typecd();
                if (passportUsedTypecd.equals("3")) {
                    count = 1;
                    break;
                }
            }
        } catch (Exception e) {
            this.logger.error("查询出区核注清单数据失败", e);
            return new ResponseData("查询出区核注清单数据失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(count);
    }

    /**
     * 新建出区核放单（预先插入获取数据）
     **/
    @RequestMapping(value = "/crtexitmanifest", method = RequestMethod.GET)
    public ResponseData saveSubmitCustom(
            @RequestParam(required = false) String dataInfo
    ) {
        Users users = this.getCurrentUsers();
        Map<String, String> paramMap = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateNowStr = sdf.format(date);
        Users userInfo = this.getCurrentUsers();

        paramMap.put("bond_invt_no", dataInfo);
        paramMap.put("ent_id", users.getEnt_Id());
        paramMap.put("input_name", users.getEnt_Name());
        paramMap.put("input_code", users.getEnt_Customs_Code());
        paramMap.put("etps_preent_no", "HFD" + users.getEnt_Customs_Code() + "E" + dateNowStr + (IdUtils.getShortUUId()).substring(0, 4));

        PassPort passPort = new PassPort();
        PassPortHead passPortHead;
        List<PassPortAcmp> passPortAcmpList;
        try {
            //查询列表
            passPortHead = this.crtExitManifestService.queryPassPortHead(paramMap);
            passPortAcmpList = this.crtExitManifestService.queryPassPortAcmpList(paramMap);
            passPort.setPassPortHead(passPortHead);
            passPort.setPassPortAcmpList(passPortAcmpList);
            this.crtExitManifestService.insertPassHeadData(passPortHead, passPortAcmpList, userInfo, dataInfo);
        } catch (Exception e) {
            this.logger.error("新建出区核放单数据失败", e);
            return new ResponseData("获取出区核放单数据错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(passPort);

    }

    //保存更新出区核放单信息
    @RequestMapping(value = "/saveExitManifest", method = RequestMethod.POST)
    public ResponseData saveManifestInfo(
            @Param("entryJson") String entryJson
    ) {
        //出区核放单json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);
        //出区核放单表头
        LinkedHashMap<String, String> passPortHead = (LinkedHashMap<String, String>) object.get("passPortHead");
        //出区核放单表体
        ArrayList<LinkedHashMap<String, String>> passPortAcmpList = (ArrayList<LinkedHashMap<String, String>>) object.get("passPortAcmpList");

        Users userInfo = this.getCurrentUsers();
        Map<String, String> map = new HashMap<>();
        try {
            // 保存详情信息
            map = this.crtExitManifestService.saveExitManifest(passPortHead, passPortAcmpList, userInfo);
        } catch (Exception e) {
            logger.error("保存出区核放单时发生异常", e);
            map.put("result", "false");
            map.put("msg", "保存出区核放单信息时发生异常");
        }
        return new ResponseData(map);
    }


}
