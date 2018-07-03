package com.xaeport.crossborder.security;


import com.xaeport.crossborder.data.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 用户工具子类 实现 UserDetails
 * Created by xcp on 2017/07/18.
 */
public class SecurityUsers extends Users implements UserDetails {

    SecurityUsers(Users users) {
        if (users != null) {
            this.setUserType(users.getUserType());
            this.setId(users.getId());
            this.setIc(users.getIc());
            this.setLoginName(users.getLoginName());
            this.setPhone(users.getPhone());
            this.setEmail(users.getEmail());
            this.setPassword(users.getPassword());
            this.setState(users.getState());
            this.setCreatorId(users.getCreatorId());
            this.setCreateTime(users.getCreateTime());
            this.setUpdatorId(users.getUpdatorId());
            this.setUpdateTime(users.getUpdateTime());
            this.setSubMenuList(users.getSubMenuList());
            this.setChildMenuList(users.getChildMenuList());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String role = this.getUserType();
        if (!StringUtils.isEmpty(role)) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
            authorities.add(authority);
        }
        return authorities;
    }


    @Override
    public String getUsername() {
        return super.getLoginName();
    }

   /* @Override
    public String getAccount() {
        return super.getPhone();
    }*/

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
