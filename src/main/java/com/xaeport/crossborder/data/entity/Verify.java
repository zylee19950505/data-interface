package com.xaeport.crossborder.data.entity;

import java.util.Date;

/**
 * 校验实体 verify_status 和 verify_record 公用
 * Created by xcp on 2017/7/25.
 */
public class Verify {

    private String vs_id;
    private String vr_id;
    private String cb_verify_no;
    private String cb_head_id;
    private String bill_no;
    private String order_no;
    private String idCard;
    private String name;
    private String type;
    private String code;
    private String status;
    private String result;
    private Date create_time;
    private Date update_time;
    private String entry_message;
    private String enterprise_id;
    private String enterprise_name;
    private String receiveName;
    private String sendId;
    private String reMark;

    public String getVs_id() {
        return vs_id;
    }

    public void setVs_id(String vs_id) {
        this.vs_id = vs_id;
    }

    public String getVr_id() {
        return vr_id;
    }

    public void setVr_id(String vr_id) {
        this.vr_id = vr_id;
    }

    public String getCb_verify_no() {
        return cb_verify_no;
    }

    public void setCb_verify_no(String cb_verify_no) {
        this.cb_verify_no = cb_verify_no;
    }

    public String getCb_head_id() {
        return cb_head_id;
    }

    public void setCb_head_id(String cb_head_id) {
        this.cb_head_id = cb_head_id;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getEntry_message() {
        return entry_message;
    }

    public void setEntry_message(String entry_message) {
        this.entry_message = entry_message;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
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

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }
}
