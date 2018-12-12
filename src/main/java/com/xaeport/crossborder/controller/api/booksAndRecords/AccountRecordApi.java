package com.xaeport.crossborder.controller.api.booksAndRecords;

import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.BwlHeadType;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.service.booksAndRecords.AccountRecordService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * 跨境企业信息表
 */
@RestController
@RequestMapping("/booksAndRecords")
public class AccountRecordApi extends BaseApi{

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AccountRecordService accountRecordService;

    @RequestMapping(value = "/crtAccountRecord",method = RequestMethod.POST)
    public ResponseData crtAccountRecord(
            @ModelAttribute BwlHeadType bwlHeadType
    ){
//        ResponseData responseData = checkAccountData(bwlHeadType, false);
//        if(responseData != null){
//            return responseData;
//        }
        String id = accountRecordService.crtAccountRecord(bwlHeadType);
        if(!StringUtils.isEmpty(id)){
            return rtnResponse("true","备案添加成功");
        }
        return rtnResponse("false","备案添加失败");
    }

    private ResponseData checkAccountData(BwlHeadType bwlHeadType,boolean isUpdate){
        try{
            if (StringUtils.isEmpty(bwlHeadType.getAppend_typecd())) {
                return rtnResponse("false", "Append_typecd不能为空！");
            }

            if (StringUtils.isEmpty(bwlHeadType.getBizop_etps_nm())) {
                return rtnResponse("false", "Bizop_etps_nm不能为空！");
            }
        }catch (Exception e){
            logger.error(String.format("企业新增校验时发生异常导致失败[enterprise_name: %s]", bwlHeadType.getAppend_typecd()), e);
            return rtnResponse("false", "账册备案新增校验时发生异常导致失败");
        }
        return null;
    }


}
