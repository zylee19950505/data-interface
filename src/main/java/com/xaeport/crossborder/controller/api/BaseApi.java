package com.xaeport.crossborder.controller.api;

import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.security.SecurityUsers;
import com.xaeport.crossborder.service.sysmanage.RoleManageService;
import com.xaeport.crossborder.service.sysmanage.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xcp on 2017/7/20.
 */
@RequestMapping("/api")
public class BaseApi {


    @Autowired
    protected UserManageService userMaService;

    @Autowired
    protected RoleManageService roleMaService;

    /**
     * 返回数据方法
     *
     * @param result 返回结果（result: true/false）
     * @param rtnMsg 返回消息（msg: String）
     * @return
     */
    protected ResponseData rtnResponse(String result, String rtnMsg) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        rtnMap.put("result", result);
        rtnMap.put("msg", rtnMsg);
        return new ResponseData(rtnMap);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    protected Users getCurrentUsers() {
        SecurityUsers securityUsers = (SecurityUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = securityUsers.getId();
        Users currentUsers = userMaService.findUsersById(id);
        return currentUsers;
    }




}
