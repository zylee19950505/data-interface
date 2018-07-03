package com.xaeport.crossborder.data.entity;

import java.util.Date;

/**
 * Created by wx on 2018/4/4.
 */
public class SweepCode {
    private String id;
    private String mailNo;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
