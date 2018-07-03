package com.xaeport.crossborder.configuration;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 应用配置
 * Created by zwj on 2017/7/18.
 */
@Configuration
@ConfigurationProperties(value = "appConfig")
public class AppConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware, ServletContextAware {
    private static File baseFolder;

    static {
        baseFolder = getBaseFolderFromClass(AppConfiguration.class);
    }

    private ApplicationContext applicationContext;
    private ServletContext servletContext;
    private String systemName;
    private String downloadFolder;

    public String getDownloadFolder() {
        return downloadFolder;
    }

    public void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }

    public AppConfiguration() {
        super();
    }

    public static void setBaseFolder(File folder) {
        baseFolder = folder;
    }

    public static File getBaseFolder() {
        return baseFolder;
    }

    public static void setBaseFolder(Class z) {
        baseFolder = getBaseFolderFromClass(z);
    }


    public static File getBaseFolderFromClass(Class z) {
        String path = z.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        if (path.startsWith("file:"))
            path = path.substring(5);
        int jarFileIndex = path.indexOf("!");
        if (jarFileIndex > -1)
            path = path.substring(0, jarFileIndex);
        File folder = new File(path);
        if (folder.isFile())
            folder = folder.getParentFile();
        return folder;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

}
