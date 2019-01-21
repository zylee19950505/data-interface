package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class RecBondInvtInvAppr {

    private String guid;
    private String inv_preent_no;
    private String business_id;
    private String entry_seq_no;
    private String manage_result;
    private Date create_date;
    private String reason;
    private Date crt_tm;
    private Date upd_tm;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getInv_preent_no() {
        return inv_preent_no;
    }

    public void setInv_preent_no(String inv_preent_no) {
        this.inv_preent_no = inv_preent_no;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getEntry_seq_no() {
        return entry_seq_no;
    }

    public void setEntry_seq_no(String entry_seq_no) {
        this.entry_seq_no = entry_seq_no;
    }

    public String getManage_result() {
        return manage_result;
    }

    public void setManage_result(String manage_result) {
        this.manage_result = manage_result;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCrt_tm() {
        return crt_tm;
    }

    public void setCrt_tm(Date crt_tm) {
        this.crt_tm = crt_tm;
    }

    public Date getUpd_tm() {
        return upd_tm;
    }

    public void setUpd_tm(Date upd_tm) {
        this.upd_tm = upd_tm;
    }
}
