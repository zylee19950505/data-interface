package com.xaeport.crossborder.security;

import com.alibaba.druid.support.json.JSONUtils;
import com.xaeport.crossborder.data.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 会话超时处理
 * Created by xcp on 2017/07/18.
 */
@Service
public class AjaxAwareAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public AjaxAwareAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String ajaxHeader = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        if (isAjax) {
            HashMap<String, String> responseData = new HashMap<>();
            responseData.put("message", "会话超时，请重新登录");
            responseData.put("login", "/login");

            String responseJSON = JSONUtils.toJSONString(new ResponseData(responseData, HttpStatus.UNAUTHORIZED).getBody());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().write(responseJSON);
            response.flushBuffer();

        } else {
            super.commence(request, response, authException);
        }
    }
}
