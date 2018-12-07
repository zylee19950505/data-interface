//package com.xaeport.crossborder.controller.api.booksAndRecords;
//
//import com.xaeport.crossborder.controller.api.BaseApi;
//import com.xaeport.crossborder.data.ResponseData;
//import com.xaeport.crossborder.data.entity.Enterprise;
//import com.xaeport.crossborder.data.entity.Users;
//import com.xaeport.crossborder.service.infomanage.EnterpriseService;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///*
// * 跨境企业信息表
// */
//@RestController
//@RequestMapping("/enterprise")
//public class AccountRecordApi extends BaseApi{
//
//    private Log log = LogFactory.getLog(this.getClass());
//
//    @Autowired
//    EnterpriseService enterpriseService;
//
//
//    @RequestMapping("/load")
//    public ResponseData loadEnterprise(){
//        Users user = this.getCurrentUsers();
//        String entId = user.getEnt_Id();
//        Enterprise enterprise = this.enterpriseService.getEnterpriseDetail(entId);
//        return new ResponseData(enterprise);
//    }
//
//
//}
