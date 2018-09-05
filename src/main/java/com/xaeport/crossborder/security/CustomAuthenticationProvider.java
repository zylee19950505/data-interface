package com.xaeport.crossborder.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Created by xcp on 2017/8/1.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // 自定义登陆验证 Bean
    @Autowired
    CustomUserService customUserService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();  // 如上面的介绍，这里通过authentication.getDetails()获取详细信息
        String fillCode = details.getFillCode();
        String authCode = details.getAuthCode();

        UserDetails userDetails = customUserService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new SwBadCredentialsException("用户不存在或企业信息被冻结");
        }

        //加密过程在这里体现
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new SwBadCredentialsException("用户名或密码错误");
        }
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (StringUtils.isEmpty(fillCode) || StringUtils.isEmpty(authCode)) throw new AuthCodeException("验证码错误");
        if (fillCode.equalsIgnoreCase(authCode)) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        } else {
            throw new AuthCodeException("验证码错误");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
//    @Override
//    public boolean supports(Class<?> arg0) {
//        return true;
//    }

}
