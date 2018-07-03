package com.xaeport.crossborder.security;


import com.xaeport.crossborder.data.entity.Users;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证成功回调处理器
 * Created by xcp on 2017/07/18.
 */
@Service
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Log log = LogFactory.getLog(this.getClass());

   /* @Autowired
    protected SaveLog saveLog;*/

    // 验证成功回调
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Users user = (Users) authentication.getPrincipal();
        this.log.debug("登陆成功：" + user.getLoginName() + ";" + user.getPhone() + ";");
//        String authCode = (String) request.getSession().getAttribute("authCode");
//        String code = request.getParameter("vCode");
        this.log.debug("登陆IP:" + getIpAddress(request));
        //登录成功,保存系统日志
        request.getSession().setAttribute("loginUser",user);
     /*   saveLog.saveSystemLog(SystemLogEnum.XTDL.getMenuCode(), SystemLogEnum.METHOD_LOGIN);*/
        super.onAuthenticationSuccess(request, response, authentication);

    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_Client_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
