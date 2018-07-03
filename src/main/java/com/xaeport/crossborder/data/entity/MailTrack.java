package com.xaeport.crossborder.data.entity;

import java.util.Date;

/**
 * Created by wx on 2018/4/4.
 */
public class MailTrack {
    private String id;
    private String mailNo;
    private String detainNo;
    private String event;
    private int state;
    private String creatorId;
    private Date createTime;
    private String updatorId;
    private Date updateTime;
    private String mailCodeId;

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

    public String getDetainNo() {
        return detainNo;
    }

    public void setDetainNo(String detainNo) {
        this.detainNo = detainNo;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMailCodeId() {
        return mailCodeId;
    }

    public void setMailCodeId(String mailCodeId) {
        this.mailCodeId = mailCodeId;
    }
}
