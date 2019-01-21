package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class RecBondInvtCommon {

    private String guid;
    private String seq_no;
    private String etps_preent_no;
    private String check_info;
    private String deal_flag;
    private Date crt_tm;
    private Date upd_tm;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(String seq_no) {
        this.seq_no = seq_no;
    }

    public String getEtps_preent_no() {
        return etps_preent_no;
    }

    public void setEtps_preent_no(String etps_preent_no) {
        this.etps_preent_no = etps_preent_no;
    }

    public String getCheck_info() {
        return check_info;
    }

    public void setCheck_info(String check_info) {
        this.check_info = check_info;
    }

    public String getDeal_flag() {
        return deal_flag;
    }

    public void setDeal_flag(String deal_flag) {
        this.deal_flag = deal_flag;
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
