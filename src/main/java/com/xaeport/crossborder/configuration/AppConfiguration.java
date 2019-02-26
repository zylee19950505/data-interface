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
import java.util.Map;

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
    private String domain;
    private boolean entryDocu;
    private Map<String, String> modelFolder;
    private String agentExeFolder;
    private Map<String, String> xmlPath;
    private String receiptFolder;
    private String preprocessFolder;
    private String processFolder;
    private String stockbackupFolder;
    private String stockerrorFolder;
    private String backupFolder;
    private String errorFolder;
    private String senderId;
    private String receiverId;
    private String inputCompanyName;
    private String inputCompanyCo;
    private String inputno;
    private String account;
    private String password;

    public String getStockbackupFolder() {
        return stockbackupFolder;
    }

    public void setStockbackupFolder(String stockbackupFolder) {
        this.stockbackupFolder = stockbackupFolder;
    }

    public String getStockerrorFolder() {
        return stockerrorFolder;
    }

    public void setStockerrorFolder(String stockerrorFolder) {
        this.stockerrorFolder = stockerrorFolder;
    }

    public String getPreprocessFolder() {
        return preprocessFolder;
    }

    public void setPreprocessFolder(String preprocessFolder) {
        this.preprocessFolder = preprocessFolder;
    }

    public String getProcessFolder() {
        return processFolder;
    }

    public void setProcessFolder(String processFolder) {
        this.processFolder = processFolder;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isEntryDocu() {
        return entryDocu;
    }

    public void setEntryDocu(boolean entryDocu) {
        this.entryDocu = entryDocu;
    }

    public Map<String, String> getModelFolder() {
        return modelFolder;
    }

    public void setModelFolder(Map<String, String> modelFolder) {
        this.modelFolder = modelFolder;
    }

    public String getAgentExeFolder() {
        return agentExeFolder;
    }

    public void setAgentExeFolder(String agentExeFolder) {
        this.agentExeFolder = agentExeFolder;
    }

    public Map<String, String> getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(Map<String, String> xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getReceiptFolder() {
        return receiptFolder;
    }

    public void setReceiptFolder(String receiptFolder) {
        this.receiptFolder = receiptFolder;
    }

    public String getBackupFolder() {
        return backupFolder;
    }

    public void setBackupFolder(String backupFolder) {
        this.backupFolder = backupFolder;
    }

    public String getErrorFolder() {
        return errorFolder;
    }

    public void setErrorFolder(String errorFolder) {
        this.errorFolder = errorFolder;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getInputCompanyName() {
        return inputCompanyName;
    }

    public void setInputCompanyName(String inputCompanyName) {
        this.inputCompanyName = inputCompanyName;
    }

    public String getInputCompanyCo() {
        return inputCompanyCo;
    }

    public void setInputCompanyCo(String inputCompanyCo) {
        this.inputCompanyCo = inputCompanyCo;
    }

    public String getInputno() {
        return inputno;
    }

    public void setInputno(String inputno) {
        this.inputno = inputno;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
