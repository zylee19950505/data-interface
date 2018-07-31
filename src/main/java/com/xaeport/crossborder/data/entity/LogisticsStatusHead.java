package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class LogisticsStatusHead {
    private String guid;//企业系统生成36 位唯一序号（英文字母大写）
    private String appType;//企业报送类型。1-新增2-变更3-删除。默认为1。
    private String appTime;//企业报送时间。格式:YYYYMMDDhhmmss。
    private String appStatus;//业务状态:1-暂存,2-申报,默认为2。
    private String logisticsCode;//物流企业的海关注册登记编号。
    private String logisticsName;//物流企业在海关注册登记的名称。
    private String logisticsNo;//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
    private String logisticsStatus;//物流签收状态，限定S
    private Date logisticsTime;//物流状态发生的实际时间。格式:YYYYMMDDhhmmss。
    private String note;//备注

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public Date getLogisticsTime() {
        return logisticsTime;
    }

    public void setLogisticsTime(Date logisticsTime) {
        this.logisticsTime = logisticsTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
