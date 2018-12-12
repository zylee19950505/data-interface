package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class PassPortAcmp {

    private String id;
    private String passport_no;
    private String rtl_tb_typecd;
    private String rtl_no;
    private Date crt_time;
    private String crt_user;
    private Date upd_time;
    private String upd_user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassport_no() {
        return passport_no;
    }

    public void setPassport_no(String passport_no) {
        this.passport_no = passport_no;
    }

    public String getRtl_tb_typecd() {
        return rtl_tb_typecd;
    }

    public void setRtl_tb_typecd(String rtl_tb_typecd) {
        this.rtl_tb_typecd = rtl_tb_typecd;
    }

    public String getRtl_no() {
        return rtl_no;
    }

    public void setRtl_no(String rtl_no) {
        this.rtl_no = rtl_no;
    }

    public Date getCrt_time() {
        return crt_time;
    }

    public void setCrt_time(Date crt_time) {
        this.crt_time = crt_time;
    }

    public String getCrt_user() {
        return crt_user;
    }

    public void setCrt_user(String crt_user) {
        this.crt_user = crt_user;
    }

    public Date getUpd_time() {
        return upd_time;
    }

    public void setUpd_time(Date upd_time) {
        this.upd_time = upd_time;
    }

    public String getUpd_user() {
        return upd_user;
    }

    public void setUpd_user(String upd_user) {
        this.upd_user = upd_user;
    }
}
