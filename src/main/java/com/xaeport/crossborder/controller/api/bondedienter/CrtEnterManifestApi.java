package com.xaeport.crossborder.controller.api.bondedIEnter;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.service.bondedIExit.CrtEnterManifestService;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/crtEnterManifest")
public class CrtEnterManifestApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CrtEnterManifestService crtEnterManifestService;

    /*
     *  新建入区核放单
     */
    @RequestMapping("/queryCrtEnterManifest")
    public ResponseData queryCrtEnterManifest(
            @RequestParam(required = false) String dataStatus,
            @RequestParam(required = false) String recordDataStatus,
            @RequestParam(required = false) String invtNo,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String length,
            @RequestParam(required = false) String draw,
            HttpServletRequest request
    ) {
        this.logger.debug(String.format("新建入区核放单查询条件参数:[invtNo:%s]", invtNo));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("invtNo", invtNo);
        paramMap.put("dataStatus", dataStatus);
        paramMap.put("recordDataStatus", recordDataStatus);
        paramMap.put("start", start);
        paramMap.put("length", length);
        paramMap.put("draw", draw);
        paramMap.put("entId", this.getCurrentUserEntId());
        paramMap.put("roleId", this.getCurrentUserRoleId());

        DataList<BondInvtBsc> dataList = new DataList<>();
        List<BondInvtBsc> resultList = new ArrayList<BondInvtBsc>();
        try {
            //查询列表
            resultList = this.crtEnterManifestService.queryCrtEnterManifestList(paramMap);
            Integer count = this.crtEnterManifestService.queryCrtEnterManifestCount(paramMap);
            dataList.setDraw(draw);
            dataList.setData(resultList);
            dataList.setRecordsTotal(count);
            dataList.setRecordsFiltered(count);
            return new ResponseData(dataList);
        } catch (Exception e) {
            this.logger.error("查询入区核放单数据失败", e);
            return new ResponseData("查询入区核放单数据失败", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 创建核放单
     * */
    @RequestMapping(value = "/createEnterManifest",method = RequestMethod.POST)
    public ResponseData createEnterManifest(
            @RequestParam(required = false) String invtNo,
            @RequestParam(required = false) String editBoundNm,//绑定数量
            @RequestParam(required = false) String bind_typecd,//绑定类型
            HttpServletRequest request
    ) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (StringUtils.isEmpty(invtNo) ||StringUtils.isEmpty(bind_typecd) ){
            rtnMap.put("result", "false");
            rtnMap.put("msg", "数据为空");
            return new ResponseData(rtnMap);
        }
        //获取企业信息
        Users user = this.getCurrentUsers();

        //创建核放单
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("invtNo",invtNo);
        paramMap.put("editBoundNm",editBoundNm);
        paramMap.put("bind_typecd",bind_typecd);

        List<BondInvtDt> bondInvtDtList = new ArrayList<>();

        //1.先查询出表体内容(循环)
        //bondInvtDtList = this.crtEnterManifestService.queryInventoryList(invtNo);
        try {
            //一车一票   和一车多票还可以合用
                //保存核放单数据
                this.crtEnterManifestService.createEnterManifest(paramMap,user);


                //一车一单和一车多单 创建核放单关联单证 核放单编号和关联单证编号
                PassPortAcmp passPortAcmp = new PassPortAcmp();
                //查询核放单编号
                String passPortNo =  this.crtEnterManifestService.queryEnterManifestPassPortNo(invtNo);
                passPortAcmp.setPassport_no(passPortNo);
                passPortAcmp.setRtl_tb_typecd("1");
                passPortAcmp.setRtl_no(invtNo);
                passPortAcmp.setId(IdUtils.getUUId());
                //创建关联单号
                this.crtEnterManifestService.createPassPortAcmp(passPortAcmp);
                //将核注清单的数量变了
               // this.crtEnterManifestService.updateEnterInventory(paramMap,user);

        } catch (Exception e) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "创建核放单出现异常");
            this.logger.error("保存失败",e);
            return new ResponseData(rtnMap);
        }
        rtnMap.put("result", "true");
        return new ResponseData(rtnMap);
    }


    /*
    * 一车一单和一车多单
    * */
    @RequestMapping("/queryManifestOneCar")
    public ResponseData queryManifestOneCar(
            @RequestParam(required = false) String bond_invt_no
    ){
        this.logger.debug(String.format("新建入区核放单查询条件参数:[bondInvtNo:%s]", bond_invt_no));
        //去查询
        PassPortHead passPortHead = new PassPortHead();
        try {
            passPortHead = this.crtEnterManifestService.queryManifestOneCar(bond_invt_no);
        } catch (Exception e) {
            this.logger.error("查询失败",e);
        }
        return  new ResponseData(passPortHead);
    }


    /**
    * 一车多单,一车一单的保存
    * */
    @RequestMapping(value = "saveEnterManifestDetailOneCar")
    public ResponseData saveEnterManifestDetailOneCar(@Param("entryJson")String entryJson){
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        //表头信息
        //LinkedHashMap<String, String> passPortHead = (LinkedHashMap<String, String>) object.get("passPortHead");
        Map<String, String> rtnMap = new HashMap<>();
        Users users = this.getCurrentUsers();
        try {
            // 保存表头信息
            rtnMap = crtEnterManifestService.updateEnterManifestDetailOneCar(object,users);
        } catch (Exception e) {
            this.logger.error("保存入区核放单表头信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存入区核放单表头信息时发生异常");
        }
        return new ResponseData(rtnMap);
    }
}
