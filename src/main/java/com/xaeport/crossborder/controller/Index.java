package com.xaeport.crossborder.controller;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.security.SecurityUsers;
import com.xaeport.crossborder.tools.SCaptcha;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 主页跳转
 * Created by xcp on 2017/07/18.
 */
@Controller
public class Index extends BaseController {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private AppConfiguration appConfiguration;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = ("/"), method = RequestMethod.GET)
    public String home(Model model) {
        ServletContext context = this.appConfiguration.getServletContext();
        SecurityUsers user = (SecurityUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("serverHost", appConfiguration.getDomain());
        model.addAttribute("serverPort", serverPort);
        user.setPassword("******");
        model.addAttribute("user", user);
        model.addAttribute("subMenuList", user.getSubMenuList());
        model.addAttribute("childMenuList", user.getChildMenuList());
        model.addAttribute("pageName", this.appConfiguration.getSystemName());
        model.addAttribute("basePath", "/" + context.getContextPath());
        return "admin";
    }


    @RequestMapping(value = ("/login"), method = RequestMethod.GET)
    public String index(Model model) {
        ServletContext context = this.appConfiguration.getServletContext();
        model.addAttribute("pageName", this.appConfiguration.getSystemName());
        model.addAttribute("basePath", "/" + context.getContextPath());
        return "index";
    }

    @RequestMapping(value = "/login/getAuthCode")
    public void getAuthCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        SCaptcha instance = new SCaptcha();
//        Cookie cookie = new Cookie("scaptcha", instance.getCode());
//        cookie.setMaxAge(1800);
//        response.addCookie(cookie);
        session.setAttribute("authCode", instance.getCode());
        try {
            instance.write(response.getOutputStream());
        } catch (IOException e) {
            this.log.error("获取登录验证码异常", e);
        }
    }

}
