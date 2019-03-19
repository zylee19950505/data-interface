package com.xaeport.crossborder.controller.api.sysmanage;

import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.BwlHeadType;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.service.sysmanage.BooksManageSerivce;
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

/**
 * Created by wx on 2018/4/27.
 */

@RestController
@RequestMapping(value = "/booksManage")
public class BooksManageApi extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    BooksManageSerivce booksManageSerivce;

    //账册所有信息数据
    @RequestMapping(value = "/allBooksInfo", method = RequestMethod.GET)
    public ResponseData queryAllEntInfo(
            @RequestParam(required = false) String booksInfo
    ) {
        this.logger.debug(String.format("企业列表查询及条件检索条件[booksInfo: %s]", booksInfo));

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("booksInfo", booksInfo);

        try {
            List<BwlHeadType> allBooksInfo = this.booksManageSerivce.queryAllBooksInfo(paramMap);
            return new ResponseData(allBooksInfo);
        } catch (Exception e) {
            return new ResponseData(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/crtBooksInfo", method = RequestMethod.POST)
    public ResponseData crtBooksInfo(
            @ModelAttribute BwlHeadType bwlHeadType, BindingResult bindingResult
    ) {
        Users user = this.getCurrentUsers();
        bwlHeadType.setCrt_user(user.getId());
        bwlHeadType.setUpd_user(user.getId());
        bwlHeadType.setCrt_ent_id(user.getEnt_Id());
        bwlHeadType.setCrt_ent_name(user.getEnt_Name());
        bwlHeadType.setChg_tms_cnt("0");
        String id = booksManageSerivce.crtBooksInfo(bwlHeadType);
        if (!StringUtils.isEmpty(id)) {
            return rtnResponse("true", "账册新增成功");
        }
        return rtnResponse("false", "账册新增失败");
    }


    @RequestMapping(value = "/booksUpdate/{id}", method = RequestMethod.PUT)
    public ResponseData entEdit(
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
        boolean updateFlag = booksManageSerivce.updateBooks(bwlHeadType);
        if (updateFlag) {
            return rtnResponse("true", "账册信息修改成功");
        }
        return rtnResponse("false", "账册信息修改失败");
    }

    @RequestMapping(value = "/loadBooks/{id}", method = RequestMethod.GET)
    public ResponseData loadBooks(
            @PathVariable(name = "id") String id
    ) {
        BwlHeadType bwlHeadType = this.booksManageSerivce.getBooksById(id);
        return new ResponseData(bwlHeadType);
    }


}
