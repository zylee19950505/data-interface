package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class RecPassPortHdeAppr {

    private String guid;
    private String etps_preent_no;
    private String business_id;
    private String tms_cnt;
    private String typecd;
    private String manage_result;
    private Date manage_date;
    private String rmk;
    private Date crt_tm;
    private Date upd_tm;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getEtps_preent_no() {
        return etps_preent_no;
    }

    public void setEtps_preent_no(String etps_preent_no) {
        this.etps_preent_no = etps_preent_no;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getTms_cnt() {
        return tms_cnt;
    }

    public void setTms_cnt(String tms_cnt) {
        this.tms_cnt = tms_cnt;
    }

    public String getTypecd() {
        return typecd;
    }

    public void setTypecd(String typecd) {
        this.typecd = typecd;
    }

    public String getManage_result() {
        return manage_result;
    }

    public void setManage_result(String manage_result) {
        this.manage_result = manage_result;
    }

    public Date getManage_date() {
        return manage_date;
    }

    public void setManage_date(Date manage_date) {
        this.manage_date = manage_date;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
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
