package com.xaeport.crossborder.service;


import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.Menu;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zwj on 2017/07/18.
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<Users> getUserInfoList() {
        return userMapper.getUserInfoList();
    }

    public Users getUserById(String id) {
        return this.userMapper.getUserById(id);
    }

    public Users authUserLogin(String id,String loginName) {
        return this.userMapper.authUserLogin(id,loginName);
    }

    public Enterprise getEnterpriseDetail(String enterpriseId) {
        return this.userMapper.getEnterpriseDetail(enterpriseId);
    }

    public Integer changePassword(String newPassword, Date updateTime, String userId) {
        return this.userMapper.changePassword(newPassword, updateTime, userId);
    }

    public List<Menu> getRoleParentMenu(String id) {
        return this.userMapper.getRoleParentMenu(id);
    }

    public List<Menu> getRoleChildMenu(String id) {
        return this.userMapper.getRoleChildMenu(id);
    }


}
