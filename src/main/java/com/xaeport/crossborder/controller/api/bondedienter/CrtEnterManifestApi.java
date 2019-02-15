package com.xaeport.crossborder.controller.api.bondedienter;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.service.bondedIExit.CrtEnterManifestService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
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

            //查找可用的大于0,和enter的
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
     * 创建核放单(点击创建就存储数据,一车一票和一车多票直接保存关联数据)
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

        //一车一票和一车多票是否需要原有数量是否等于申报数量(前端已经判断,但是为了防止多人操作)

        //一票多车时需要判断是否是最后一车,进行四舍五入(毛重和净重)

        List<BondInvtDt> bondInvtDtList = new ArrayList<>();

        //1.先查询出表体内容(循环)
        //bondInvtDtList = this.crtEnterManifestService.queryInventoryList(invtNo);
        try {
            if (!"YPDC".equals(bind_typecd)){
                //一车一票   和一车多票可以合用
                //保存核放单数据
                rtnMap = this.crtEnterManifestService.createEnterManifest(paramMap,user,rtnMap);

                //将核注清单的数量变了
                // this.crtEnterManifestService.updateEnterInventory(paramMap,user);
            }else{
                //一票多车
                rtnMap = this.crtEnterManifestService.createEnterManifest(paramMap,user, rtnMap);

               /* bondInvtDtList = this.crtEnterManifestService.queryInventoryList(invtNo);
                List<PassPortList> passPortLists = new ArrayList<>();
                for (BondInvtDt bondInvtDt:bondInvtDtList) {
                    PassPortList passPortList = new PassPortList();
                    passPortList.setId(IdUtils.getUUId());
                }*/
            }

        } catch (Exception e) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "创建核放单出现异常");
            this.logger.error("保存失败",e);
            return new ResponseData(rtnMap);
        }
        rtnMap.put("result", "true");
        return new ResponseData(rtnMap);
    }

    /**
     * 预创建核放单
     * readyCreateEnterManifest(不保存数据库)
     * */
    /*@RequestMapping(value = "/readyCreateEnterManifest",method = RequestMethod.GET)
    public ResponseData readyCreateEnterManifest(
            @RequestParam(required = false) String bond_invt_no,
            @RequestParam(required = false) String editBoundNm,//绑定数量
            @RequestParam(required = false) String bind_typecd,//绑定类型
            HttpServletRequest request
    ) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (StringUtils.isEmpty(bond_invt_no) ||StringUtils.isEmpty(bind_typecd) ){
            rtnMap.put("result", "false");
            rtnMap.put("msg", "数据为空");
            return new ResponseData(rtnMap);
        }
        //获取企业信息
        Users user = this.getCurrentUsers();

        //创建核放单
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("invtNo",bond_invt_no);
        paramMap.put("editBoundNm",editBoundNm);
        paramMap.put("bind_typecd",bind_typecd);

        PassPort passPort = new PassPort();
        *//*if (!"YPDC".equals(bind_typecd)){
            passPort = this.crtEnterManifestService.readyCreateEnterManifest(paramMap,user);

        }else{
            //一票多车
            passPort = this.crtEnterManifestService.readyCreateEnterManifest(paramMap,user);
        }*//*
        passPort = this.crtEnterManifestService.readyCreateEnterManifest(paramMap,user);
        return new ResponseData(passPort);
    }*/

    /**
    * 一车一单和一车多单的查询核放单表头数据
    * */
    @RequestMapping("/queryEnterManifestOneCar")
    public ResponseData queryEnterManifestOneCar(
            @RequestParam(required = false) String bond_invt_no,
            @RequestParam(required = false) String bind_typecd,
            @RequestParam(required = false) String etps_preent_no
    ){
        this.logger.debug(String.format("查询非一票多车表头数据:[bond_invt_no:%s,bind_typecd:%s,etps_preent_no:%s]", bond_invt_no,bind_typecd,etps_preent_no));
        //去查询
        PassPortHead passPortHead = new PassPortHead();
        Map<String,String> paramMap = new HashMap<String,String>();

        if (StringUtils.isEmpty(bond_invt_no) || StringUtils.isEmpty(bind_typecd) || StringUtils.isEmpty(etps_preent_no)){
            paramMap.put("result", "false");
            paramMap.put("msg", "数据为空");
            return new ResponseData(paramMap);
        }else {
            paramMap.put("bond_invt_no",bond_invt_no);
            paramMap.put("bind_typecd",bind_typecd);
            paramMap.put("etps_preent_no",etps_preent_no);
        }
        try {
            passPortHead = this.crtEnterManifestService.queryEnterManifestOneCar(paramMap);
        } catch (Exception e) {
            this.logger.error("查询失败",e);
        }
        return  new ResponseData(passPortHead);
    }

    /**
     * 一单多车查询表头数据和加载商品料号数据和商品名称数据
     * */
    @RequestMapping("/queryEnterManifestMoreCar")
    public ResponseData queryEnterManifestMoreCar(
            @RequestParam(required = false) String bond_invt_no,
            @RequestParam(required = false) String bind_typecd,
            @RequestParam(required = false) String etps_preent_no,
            @RequestParam(required = false) String editBoundNm
    ){
        this.logger.debug(String.format("查询一票多车表头数据:[bond_invt_no:%s,bind_typecd:%s,etps_preent_no:%s,editBoundNm:%s]", bond_invt_no,bind_typecd,etps_preent_no,editBoundNm));
        //去查询

        Map<String,String> paramMap = new HashMap<String,String>();

        if (StringUtils.isEmpty(bond_invt_no) || StringUtils.isEmpty(bind_typecd) || StringUtils.isEmpty(etps_preent_no)){
            paramMap.put("result", "false");
            paramMap.put("msg", "数据为空");
            return new ResponseData(paramMap);
        }else {
            paramMap.put("bond_invt_no",bond_invt_no);
            paramMap.put("bind_typecd",bind_typecd);
            paramMap.put("etps_preent_no",etps_preent_no);
            paramMap.put("editBoundNm",editBoundNm);
        }
        PassPortMoreCar passPortMoreCar = new PassPortMoreCar();
        PassPortHead passPortHead = new PassPortHead();
        List<BondInvtDt> bondInvtDtList = new ArrayList<>();
        Map<String,String> gds_mtno = new HashMap<>();
        Map<String,String> gds_nm = new HashMap<>();
        try {
            passPortHead = this.crtEnterManifestService.queryEnterManifestOneCar(paramMap);
            bondInvtDtList = this.crtEnterManifestService.queryInventoryList(bond_invt_no);
            if (!CollectionUtils.isEmpty(bondInvtDtList)){
                for (BondInvtDt bondInvtDt: bondInvtDtList) {
                    gds_mtno.put(bondInvtDt.getGds_mtno(),bondInvtDt.getGds_mtno());
                    gds_nm.put(bondInvtDt.getGds_nm(),bondInvtDt.getGds_nm());
                }
            }else {
                this.logger.error("该核注清单表体无数据");
                return new ResponseData("该核注清单表体无数据",HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            this.logger.error("查询失败",e);
            return new ResponseData("查询失败",HttpStatus.BAD_REQUEST);
        }
        passPortMoreCar.setPassPortHead(passPortHead);
        //passPortMoreCar.setBondInvtDtList(bondInvtDtList);
        passPortMoreCar.setGds_mtno(gds_mtno);
        passPortMoreCar.setGds_nm(gds_nm);
        return  new ResponseData(passPortMoreCar);
    }
    /**
     * 一票多车的根据商品名称和商品料号进行查询
     * */
    @RequestMapping("/querySelectBondDtList")
    public ResponseData querySelectBondDtList(
            @RequestParam(required = false) String bond_invt_no,
            @RequestParam(required = false) String etps_preent_no,
            @RequestParam(required = false) String selectGdsmtno,
            @RequestParam(required = false) String selectGdsnm
    ){
        this.logger.debug(String.format("一票多车的根据商品名称和商品料号进行查询:[bond_invt_no:%s,etps_preent_no:%s,selectGdsmtno:%s,selectGdsnm:%s]", bond_invt_no,etps_preent_no,selectGdsmtno,selectGdsnm));
        //去查询
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("bond_invt_no",bond_invt_no);
        paramMap.put("etps_preent_no",etps_preent_no);
        paramMap.put("selectGdsmtno",selectGdsmtno);
        paramMap.put("selectGdsnm",selectGdsnm);

        List<BondInvtDt> bondInvtDtList = new ArrayList<>();

        try {
            bondInvtDtList = this.crtEnterManifestService.querySelectBondDtList(paramMap);
        } catch (Exception e) {
            this.logger.error("查询失败",e);
            return new ResponseData("查询失败",HttpStatus.BAD_REQUEST);
        }
        return  new ResponseData(bondInvtDtList);
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
            // 保存表头信息和关联单信息
            rtnMap = crtEnterManifestService.updateEnterManifestDetailOneCar(object,users);
        } catch (Exception e) {
            this.logger.error("保存入区核放单表头信息时发生异常", e);
            rtnMap.put("result", "false");
            rtnMap.put("msg", "保存入区核放单表头信息时发生异常");
        }
        return new ResponseData(rtnMap);
    }
    /**
     * 一票多车的保存
     * saveManifestDetailMoreCar
    * */
    @RequestMapping(value = "saveManifestDetailMoreCar")
    public ResponseData saveManifestDetailMoreCar(@Param("entryJson")String entryJson){
//订单信息json信息
        LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) JSONUtils.parse(entryJson);

        // 订单表头
        LinkedHashMap<String, String> entryHead = (LinkedHashMap<String, String>) object.get("entryHead");

        // 订单表体
        ArrayList<LinkedHashMap<String, String>> entryLists = (ArrayList<LinkedHashMap<String, String>>) object.get("entryList");

        //申报数量(是否为最后一单)
        String editBoundNm = object.get("editBoundNm").toString();

        Map<String, String> rtnMap = new HashMap<>();
        List<PassPortList> passPortListlist = new ArrayList<PassPortList>();
        //查询核放单表体数据里的数量相加;
        passPortListlist = this.crtEnterManifestService.querypassPortListNm(entryHead.get("bond_invt_no"));
        //用核放单的申报总数-核放单数量相加的值 ?= 当前申报的数量
        int sumDclqty = 0;//总数量
        double grossWt = 0;
        double netWt = 0;
        for (PassPortList passPortList:passPortListlist) {
            sumDclqty += passPortList.getDcl_qty();
            grossWt += passPortList.getGross_wt()*passPortList.getDcl_qty();
            netWt += passPortList.getNet_wt()*passPortList.getDcl_qty();
        }

        int bondBscDclqty = this.crtEnterManifestService.queryBondBscNm(entryHead.get("bond_invt_no"));

        Users users = this.getCurrentUsers();
        if ((bondBscDclqty-sumDclqty) == Integer.parseInt(editBoundNm)){
            //等于  为最后一单
            //查询这个核注清单的总重量
            List<BondInvtDt> bondInvtDts = new ArrayList<BondInvtDt>();
            bondInvtDts = this.crtEnterManifestService.queryBondDtList(entryHead.get("bond_invt_no"));
            double totalGrossWt = 0;
            double totalNetWt = 0;
            for (BondInvtDt bondInvtDt : bondInvtDts) {
                totalGrossWt += Double.parseDouble(bondInvtDt.getGross_wt())*Integer.parseInt(bondInvtDt.getDcl_qty());
                totalNetWt += Double.parseDouble(bondInvtDt.getNet_wt())*Integer.parseInt(bondInvtDt.getDcl_qty());
            }
            //最后一单补差值
            entryHead.put("total_gross_wt", String.valueOf(totalGrossWt-grossWt));
            entryHead.put("total_net_wt", String.valueOf(totalGrossWt-grossWt));
            rtnMap = crtEnterManifestService.saveManifestDetailMoreCar(entryHead, entryLists,users);
        }else{
            //不等于 不是最后一单
            try {
                // 保存订单详情信息
                rtnMap = crtEnterManifestService.saveManifestDetailMoreCar(entryHead, entryLists,users);
            } catch (Exception e) {
                logger.error("保存一票多车信息时发生异常", e);
                rtnMap.put("result", "false");
                rtnMap.put("msg", "保存一票多车信息时发生异常");
            }
        }
        return new ResponseData(rtnMap);
    }
}
