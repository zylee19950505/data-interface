package com.xaeport.crossborder.data.entity;

import java.util.Date;

/**
 * 校验实体 verify_status 和 verify_record 公用
 * Created by xcp on 2017/7/25.
 */
public class Verify {

    private String vsId;
    private String vrId;
    private String orderNo;
    private String entryHeadId;
    private String billNo;
    private String assBillNo;
    private String idCard;
    private String name;
    private String type;
    private String code;
    private String status;
    private String result;
    private Date createTime;
    private Date updateTime;
    private String entryMessage;
    private String enterpriseId;
    private String receiveName;
    private String sendId;

    public String getVsId() {
        return vsId;
    }

    public void setVsId(String vsId) {
        this.vsId = vsId;
    }

    public String getVrId() {
        return vrId;
    }

    public void setVrId(String vrId) {
        this.vrId = vrId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getEntryHeadId() {
        return entryHeadId;
    }

    public void setEntryHeadId(String entryHeadId) {
        this.entryHeadId = entryHeadId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getAssBillNo() {
        return assBillNo;
    }

    public void setAssBillNo(String assBillNo) {
        this.assBillNo = assBillNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEntryMessage() {
        return entryMessage;
    }

    public void setEntryMessage(String entryMessage) {
        this.entryMessage = entryMessage;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }
}
