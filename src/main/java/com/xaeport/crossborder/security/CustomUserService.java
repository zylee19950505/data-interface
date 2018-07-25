package com.xaeport.crossborder.security;

import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.Menu;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcp on 2017/07/18.
 */
@Component
public class CustomUserService implements UserDetailsService {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String id) { //重写loadUserByUsername 方法获得 userdetails 类型用户

        // 获取登陆认证用户信息
        Users users = this.authUserLogin(id);

        if (null == users) {
            throw new SwAuthenticationServiceException("用户不存在或企业信息被冻结");
        }

        //获取父级菜单信息
        List<Menu> subMenuList = this.getParentMenuList(users.getId());
        //获取子级菜单信息
        List<Menu> childMenuList = this.getChildMenuList(users.getId());

        Enterprise enterprise = this.getUserEnterprise(users.getEnt_Id());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。要把用户权限添加到authorities。
        authorities.add(new SimpleGrantedAuthority(users.getLoginName()));

        users.setSubMenuList(subMenuList);
        users.setChildMenuList(childMenuList);
        users.setEnterprise(enterprise);


        SecurityUsers securityUsers = new SecurityUsers(users);
        return securityUsers;
    }


    // 获取登陆认证用户信息
    private Users authUserLogin(String id) {
        return this.userService.getUserById(id);
    }

    private Enterprise getUserEnterprise(String enterpriseId) {
        return this.userService.getEnterpriseDetail(enterpriseId);
    }

    // 获取父级菜单列表
    private List<Menu> getParentMenuList(String id) {
        return this.userService.getRoleParentMenu(id);
    }

    // 获取子级菜单列表
    private List<Menu> getChildMenuList(String id) {
        return this.userService.getRoleChildMenu(id);
    }

}
