package com.xaeport.crossborder.controller;

import org.apache.commons.logging.Log;

import java.io.File;

/**
 * controller Base 父类
 * Created by xcp on 2017/07/18.
 */
public class BaseController {
    protected final String key = "%@!!#!$";
    protected final String defaultPassword = "123456";


    //校验模版是否存在
    protected boolean verifyTemplate(String templateName, File templatesFolder, Log log) {

        if (templateName.contains("..")) {
            log.warn("疑似非法的模版文件请求：" + templateName);
            return false;
        }
        templateName += ".html";
        File templateFile = new File(templatesFolder, templateName);
        return templateFile.exists();
    }
}
