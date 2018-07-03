package com.xaeport.crossborder.tools;

import org.springframework.stereotype.Component;

/**
 * Created by baozhe on 2016-11-03.
 */

@Component
public class SystemSetting {
    private String uploadPath;

    private String httpClinetUrl;

    public String getUploadPath(){
        return uploadPath;
    }

    public void setUploadPath(String uploadPath){
        this.uploadPath=uploadPath;
    }

    public String getHttpClinetUrl() {
        return httpClinetUrl;
    }

    public void setHttpClinetUrl(String httpClinetUrl) {
        this.httpClinetUrl = httpClinetUrl;
    }
}
