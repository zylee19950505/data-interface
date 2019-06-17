package com.xaeport.crossborder.controller.api.parametermanage;


import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DclEtps;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.service.parametermanage.DeclareEntService;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 申报企业管理
 * Created by lzy on 2019/04/02.
 */
@RestController
public class DeclareEntApi extends BaseApi {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    DeclareEntService declareEntService;

    // 申报企业信息查询
    @RequestMapping(value = "/dcletpsQuery", method = RequestMethod.GET)
    public ResponseData getTrafList(
            @RequestParam String dcl_etps_name
    ) {
        Map<String, String> map = new HashMap<>();
        Users users = this.getCurrentUsers();
        map.put("dcl_etps_name", dcl_etps_name);
        map.put("ent_id", users.getEnt_Id());
        map.put("role_id", users.getRoleId());
        List<DclEtps> dclEtpsList;
        try {
            dclEtpsList = this.declareEntService.queryDclEtpsList(map);
        } catch (Exception e) {
            this.log.error("查询申报企业失败，ent_id=" + this.getCurrentUserEntId(), e);
            return new ResponseData("查询申报企业失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dclEtpsList);
    }

    // 设置企业信息为默认值
    @RequestMapping(value = "/dcletpsSetDefault/{id}", method = RequestMethod.POST)
    public ResponseData dcletpsSetDefault(
            @RequestParam String id,
            @RequestParam String ent_id
    ) {
        try {
            this.declareEntService.dcletpsSetDefault(id, ent_id);
        } catch (Exception e) {
            this.log.error("设置默认申报企业信息失败，id=" + id, e);
            return new ResponseData("设置默认申报企业信息失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(1);
    }

    // 申报企业删除
    @RequestMapping(value = "/dcletpsDelete/{id}", method = RequestMethod.DELETE)
    public ResponseData deleteTraf(
            @PathVariable(name = "id") String id
    ) {
        try {
            this.declareEntService.deleteDcletps(id);
        } catch (Exception e) {
            this.log.error("删除申报企业信息失败，id=" + id, e);
            return new ResponseData("删除申报企业信息失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(1);
    }

    // 申报企业新增
    @RequestMapping(value = "/dcletpsAdd", method = RequestMethod.POST)
    public ResponseData createTraf(
            @RequestParam String dcl_etps_name,
            @RequestParam String dcl_etps_customs_code,
            @RequestParam String dcl_etps_credit_code,
            @RequestParam String dcl_etps_ic_no,
            @RequestParam String dcl_etps_port
    ) throws NoSuchAlgorithmException, SQLException {
        if (StringUtils.isEmpty(dcl_etps_name)) {
            return new ResponseData("企业名称", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(dcl_etps_customs_code)) {
            return new ResponseData("海关编码", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(dcl_etps_credit_code)) {
            return new ResponseData("社会信用代码", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(dcl_etps_ic_no)) {
            return new ResponseData("IC卡号", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(dcl_etps_port)) {
            return new ResponseData("主管海关", HttpStatus.FORBIDDEN);
        }

        String id = IdUtils.getUUId();
        DclEtps dclEtps = new DclEtps();
        try {
            dclEtps.setId(id);
            dclEtps.setDcl_etps_name(dcl_etps_name);
            dclEtps.setDcl_etps_customs_code(dcl_etps_customs_code);
            dclEtps.setDcl_etps_credit_code(dcl_etps_credit_code);
            dclEtps.setDcl_etps_ic_no(dcl_etps_ic_no);
            dclEtps.setDcl_etps_port(dcl_etps_port);
            dclEtps.setEnt_id(this.getCurrentUserEntId());
            dclEtps.setEnt_customs_code(this.getCurrentCustomsCode());
            dclEtps.setCreate_time(new Date());
            this.declareEntService.createDcletps(dclEtps);
        } catch (Exception e) {
            this.log.error("新增申报企业信息失败", e);
            return new ResponseData("新增申报企业信息失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData();
    }

    // 发件人信息查询
    @RequestMapping(value = "/getDclEtps", method = RequestMethod.GET)
    public ResponseData getTrafList() {
        Map<String, String> map = new HashMap<>();
        map.put("ent_id", this.getCurrentUserEntId());
        List<DclEtps> dclEtpsList;
        try {
            dclEtpsList = this.declareEntService.queryDclEtpsLists(map);
        } catch (Exception e) {
            this.log.error("查询申报企业参数失败，ent_id=" + this.getCurrentUserEntId(), e);
            return new ResponseData("查询申报企业参数失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(dclEtpsList);
    }

}
