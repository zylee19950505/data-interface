package com.xaeport.crossborder.controller.api.booksandrecords;

import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.BwlHeadType;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.service.booksandrecords.AccountRecordService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 跨境企业信息表
 */
@RestController
@RequestMapping("/accountrecord")
public class AccountRecordApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AccountRecordService accountRecordService;

    @RequestMapping(value = "/allaccountinfo", method = RequestMethod.GET)
    public ResponseData queryAllAccountsInfo(
            @RequestParam(required = false) String accountinfo
    ) {
        this.logger.debug(String.format("企业列表查询及条件检索条件[accountinfo: %s]", accountinfo));
        Map<String, String> paramMap = new HashMap<String, String>();
        Users user = this.getCurrentUsers();
        paramMap.put("accountinfo", accountinfo);
        paramMap.put("crt_ent_id",user.getEnt_Id());
        paramMap.put("crt_ent_name",user.getEnt_Name());
        try {
            List<BwlHeadType> allBooksInfo = this.accountRecordService.queryAllAccountsInfo(paramMap);
            return new ResponseData(allBooksInfo);
        } catch (Exception e) {
            return new ResponseData(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/crtaccountinfo", method = RequestMethod.POST)
    public ResponseData crtAccountInfo(
            @ModelAttribute BwlHeadType bwlHeadType, BindingResult bindingResult
    ) {
        Users user = this.getCurrentUsers();
        bwlHeadType.setCrt_user(user.getId());
        bwlHeadType.setUpd_user(user.getId());
        bwlHeadType.setCrt_ent_id(user.getEnt_Id());
        bwlHeadType.setCrt_ent_name(user.getEnt_Name());
        bwlHeadType.setChg_tms_cnt("0");
        String id = accountRecordService.crtAccountInfo(bwlHeadType);
        if (!StringUtils.isEmpty(id)) {
            return rtnResponse("true", "账册新增成功");
        }
        return rtnResponse("false", "账册新增失败");
    }

    @RequestMapping(value = "/accountupdate/{id}", method = RequestMethod.PUT)
    public ResponseData accountUpdate(
            @ModelAttribute BwlHeadType bwlHeadType, BindingResult bindingResult
    ) {
        Users user = this.getCurrentUsers();
        bwlHeadType.setUpd_user(user.getId());
        bwlHeadType.setUpd_time(new Date());
        bwlHeadType.setInput_date(new Date());
        String id = bwlHeadType.getId();
        if (StringUtils.isEmpty(id)) {
            return rtnResponse("false", "修改账册息时ID不能为空");
        }
        boolean updateFlag = accountRecordService.accountUpdate(bwlHeadType);
        if (updateFlag) {
            return rtnResponse("true", "账册信息修改成功");
        }
        return rtnResponse("false", "账册信息修改失败");
    }

    @RequestMapping(value = "/loadaccount/{id}", method = RequestMethod.GET)
    public ResponseData loadBooks(
            @PathVariable(name = "id") String id
    ) {
        BwlHeadType bwlHeadType = this.accountRecordService.getAccountById(id);
        return new ResponseData(bwlHeadType);
    }


    //查询企业所属账册编码参数表
    @RequestMapping(value = "/getemsnos", method = RequestMethod.GET)
    public ResponseData getSendName() {
        Map<String, String> map = new HashMap<>();
        map.put("ent_id", this.getCurrentUserEntId());
        map.put("ent_name", this.getCurrentUserEntName());
        List<BwlHeadType> bwlHeadTypeList;
        try {
            bwlHeadTypeList = this.accountRecordService.getEmsNos(map);
        } catch (Exception e) {
            this.logger.error("账册编号加载失败，entId =" + this.getCurrentUserEntId(), e);
            return new ResponseData("账册编号参数加载失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseData(bwlHeadTypeList);
    }


}
