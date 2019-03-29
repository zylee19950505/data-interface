//package com.xaeport.crossborder.controller.api.parametermanage;
//
//
//import com.xaeport.crossborder.controller.api.BaseApi;
//import com.xaeport.crossborder.data.ResponseData;
//import com.xaeport.crossborder.data.entity.ImpInventory;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.NoSuchAlgorithmException;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 运输工具字典API
// * Created by xcp on 2017/7/20.
// */
//@RestController
//public class DeclareEntApi extends BaseApi {
//    private Log log = LogFactory.getLog(this.getClass());
//
//    @Autowired
//    ImpInventory sendNameService;
//
//
//    // 发件人信息查询
//    @RequestMapping(value = "/sendName", method = RequestMethod.GET)
//    public ResponseData getTrafList(
//            @RequestParam String send_name
//    ) {
//        Map<String, String> map = new HashMap<>();
//        map.put("send_name", send_name);
//        map.put("ent_id", this.getCurrentUserEnterpriseId());
//        List<SendReceiveName> sendReceiveNames;
//        try {
//            sendReceiveNames = this.sendNameService.querySendNameInfo(map);
//        } catch (Exception e) {
//            this.log.error("发件人信息查询失败，ent_id=" + this.getCurrentUserEnterpriseId(), e);
//            return new ResponseData("发件人信息查询失败", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseData(sendReceiveNames);
//    }
//
//    //导入界面加载B类进口发件人数据
//    @RequestMapping(value = "/getSendName", method = RequestMethod.GET)
//    public ResponseData getSendName() {
//        Map<String, String> map = new HashMap<>();
//        map.put("entry_type", SystemConstant.ENTRY_TYPE_B);
//        map.put("i_e_flag", SystemConstant.IE_FLAG_I);
//        map.put("ent_id", this.getCurrentUserEnterpriseId());
//        List<SendReceiveName> sendReceiveNameList;
//        try {
//            sendReceiveNameList = this.sendNameService.getSendName(map);
//        } catch (Exception e) {
//            this.log.error("发件人信息加载失败，entId =" + this.getCurrentUserEnterpriseId(), e);
//            return new ResponseData("发件人信息参数加载失败", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseData(sendReceiveNameList);
//    }
//
//    // 发件人信息删除
//    @RequestMapping(value = "/sendName/{sn_id}", method = RequestMethod.DELETE)
//    public ResponseData deleteTraf(@PathVariable(name = "sn_id") String sn_id) {
//        try {
//            this.sendNameService.deleteSendNameInfo(sn_id);
//        } catch (Exception e) {
//            this.log.error("删除发件人信息失败，sn_id=" + sn_id, e);
//            return new ResponseData("删除发件人信息失败", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseData(1);
//    }
//
//    // 发件人新增
//    @RequestMapping(value = "/sendNameAdd", method = RequestMethod.POST)
//    public ResponseData createTraf(
//            @RequestParam String entry_type,
//            @RequestParam String i_e_flag,
//            @RequestParam String send_name,
//            @RequestParam String send_name_en
//    ) throws NoSuchAlgorithmException, SQLException {
//        if (StringUtils.isEmpty(entry_type)) {
//            return new ResponseData("报关类型", HttpStatus.FORBIDDEN);
//        }
//        if (StringUtils.isEmpty(i_e_flag)) {
//            return new ResponseData("进出口", HttpStatus.FORBIDDEN);
//        }
//        if (StringUtils.isEmpty(send_name)) {
//            return new ResponseData("发件人名称", HttpStatus.FORBIDDEN);
//        }
//        if (StringUtils.isEmpty(send_name_en)) {
//            return new ResponseData("英文发件人名称", HttpStatus.FORBIDDEN);
//        }
//
//        String SnId = IdUtils.getUUId();
//        SendReceiveName sendReceiveName = new SendReceiveName();
//        try {
//            sendReceiveName.setSn_id(SnId);
//            sendReceiveName.setEntry_type(entry_type);
//            sendReceiveName.setI_e_flag(i_e_flag);
//            sendReceiveName.setSend_name(send_name);
//            sendReceiveName.setSend_name_en(send_name_en);
//            sendReceiveName.setEnt_id(this.getCurrentUsers().getEnterpriseId());
////            sendReceiveName.setEnt_name(this);
////            sendReceiveName.setEnt_customs_code(this);
//            sendReceiveName.setCreate_time(new Date());
//            this.sendNameService.createSendName(sendReceiveName);
//        } catch (Exception e) {
//            this.log.error("新增发件人信息失败", e);
//            return new ResponseData("新增发件人信息失败", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseData();
//    }
//
//
//}
