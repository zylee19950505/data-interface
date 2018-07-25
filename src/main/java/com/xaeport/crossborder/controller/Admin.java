package com.xaeport.crossborder.controller;


import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.security.SecurityUsers;
import com.xaeport.crossborder.security.SwAuthenticationServiceException;
import com.xaeport.crossborder.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 页面跳转管理
 * Created by xcp on 2017/07/18.
 */
@Controller
@RequestMapping("/admin")
public class Admin extends BaseController {
    private Log log = LogFactory.getLog(this.getClass());
    private File templatesFolder;

    @Autowired
    private AppConfiguration appConfiguration;
    @Value("${server.port}")
    private String serverPort;
    @Autowired
    UserService userService;

    @PostConstruct
    private void init() {
        this.templatesFolder = new File(appConfiguration.getBaseFolder(), "templates");
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam(name = "p") String page, @RequestParam(required = false) String id, Model model) throws FileNotFoundException {
        ServletContext context = this.appConfiguration.getServletContext();
        if (!this.verifyTemplate(page, this.templatesFolder, log)) {
            this.log.error("找不到请求的模版文件：" + page);
            throw new FileNotFoundException();
        }
        SecurityUsers user = (SecurityUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("serverPort", serverPort);
        model.addAttribute("user", user);
        model.addAttribute("subMenuList", user.getSubMenuList());
        model.addAttribute("childMenuList", user.getChildMenuList());
        model.addAttribute("pageName", this.appConfiguration.getSystemName());
        model.addAttribute("basePath", "/" + context.getContextPath());
        model.addAttribute("id", id);
        return page;
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    public String page(@RequestParam(name = "p") String page, @RequestParam(required = false) String id, Model model) throws FileNotFoundException {
        String pageName = "page/" + page;
        if (!this.verifyTemplate(pageName, this.templatesFolder, log)) {
            this.log.error("找不到请求的模版文件：" + page);
            throw new FileNotFoundException();
        }
        SecurityUsers securityUsers = (SecurityUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 获取登陆认证用户信息
        Users users = userService.getUserById(securityUsers.getId());
        if (null == users) {
            throw new SwAuthenticationServiceException("用户不存在或企业信息被冻结");
        }

        ServletContext context = this.appConfiguration.getServletContext();
        model.addAttribute("pageName", this.appConfiguration.getSystemName());
        model.addAttribute("basePath", "/" + context.getContextPath());
        model.addAttribute("id", id);
        return pageName;
    }

}
