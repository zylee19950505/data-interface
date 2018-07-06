package com.xaeport.crossborder.service.sysmanage;

import com.xaeport.crossborder.data.entity.UserRole;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.UserManageMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */
@Service
public class UserManageService {
    private Log log = LogFactory.getLog(this.getClass());

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);

    @Autowired
    UserManageMapper userMaMapper;

    /*
 * 用户管理—根据用户Id获取用户详细信息
 */
    public Users findUsersById(String id){
        Users users = this.userMaMapper.findUsersById(id);
        return users;
    }

    /*
     * 用户管理—获取全部用户信息
     */
    public List<Map<String,String>> userList(Map<String,String> paramMap){
        return this.userMaMapper.userList(paramMap);
    }

    /**
     * 用户管理—根据用户ID获取用户详细信息
     */
    public Map<String, String> userDetail(String id) {
        return this.userMaMapper.userDetail(id);
    }

    /*
     * 用户管理—根据用户id删除用户
     */
    public void userDelete(String id){
        this.log.debug(String.format("根据用户ID[id: %s]删除用户", id));
        // 删除用户信息
        this.userMaMapper.userDelete(id);
        this.log.debug(String.format("删除用户信息[id: %s]", id));

    }

    /*
     * 用户管理—根据用户id重置密码
     */
    public boolean userReset(Users securityUsers, String id, String password) {

        this.log.debug("用户密码重置前加密");
        String encrypt = passwordEncoder.encode(password);

        this.log.debug(String.format("重置用户[id: %s]密码", id));
        return this.userMaMapper.updatePwById(id, encrypt);
    }

    /**
     * 用户管理—创建用户信息
     */
    public boolean userCreate(Users securityUsers, Users users,UserRole userRole) {
        String pwd = users.getPassword();
        String encrypt = passwordEncoder.encode(pwd);// 密码加密

//        users.setId(id);
        String id = securityUsers.getId();
        users.setCreatorId(id);
        users.setUpdatorId(id);
        users.setPassword(encrypt);
        users.setCreateTime(new Date());
        users.setUpdateTime(new Date());

        int number = 1;
        userRole.setId(IdUtils.getUUId());
        userRole.setCreatorId(id);
        userRole.setUpdatorId(id);
        userRole.setState(number);
        userRole.setCreateTime(new Date());
        userRole.setUpdateTime(new Date());

        this.log.debug(String.format("创建用户账号[id: %s]", users.getId()));
        int insCount = this.userMaMapper.insertUser(users);
        int insCount2 = this.userMaMapper.insertUserRole(userRole);
        return insCount + insCount2 > 0;
    }

    /**
     * 用户管理—用户信息修改
     */
    public boolean userEdit(Users securityUsers, Users users,UserRole userRole) {
        users.setUpdateTime(new Date());
        int insCount = this.userMaMapper.updateUser(users);
        userRole.setUpdateTime(new Date());
        int insCount1 = this.userMaMapper.updateUserRole(userRole);
        this.log.debug(String.format("用户信息[id: %s]修改结果为: %s", users.getId(),userRole.getUserInfoId() ,(insCount + insCount1 > 0)));
        return insCount + insCount1 > 0;
    }

    /**
     * 用户管理—获取角色信息下拉选框数据
     */
    public List<Map<String, String>> roleSelectList() {
        return this.userMaMapper.roleSelectList();
    }

    /**
     * 用户管理—验证用户账号重复
     */
    public boolean idRepeat(String id) {
        int repeatCount = this.userMaMapper.idRepeat(id);
        this.log.debug(String.format("验证账号[id: %s]重复数量为[repeatCount: %s]", id, repeatCount));
        return repeatCount > 0;
    }
    
}
