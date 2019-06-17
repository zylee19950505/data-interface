package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class DclEtps {

    private String id;//唯一Id
    private String dcl_etps_name;//申报企业名称
    private String dcl_etps_customs_code;//申报企业海关编码
    private String dcl_etps_credit_code;//申报企业社会信用代码
    private String dcl_etps_ic_no;//申报企业IC卡号
    private String dcl_etps_port;//申报企业主管海关
    private String ent_id;//创建企业ID
    private String ent_customs_code;//创建企业海关编码
    private Date create_time;//创建时间
    private String select_priority;//选择优先级

    public String getSelect_priority() {
        return select_priority;
    }

    public void setSelect_priority(String select_priority) {
        this.select_priority = select_priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDcl_etps_name() {
        return dcl_etps_name;
    }

    public void setDcl_etps_name(String dcl_etps_name) {
        this.dcl_etps_name = dcl_etps_name;
    }

    public String getDcl_etps_customs_code() {
        return dcl_etps_customs_code;
    }

    public void setDcl_etps_customs_code(String dcl_etps_customs_code) {
        this.dcl_etps_customs_code = dcl_etps_customs_code;
    }

    public String getDcl_etps_credit_code() {
        return dcl_etps_credit_code;
    }

    public void setDcl_etps_credit_code(String dcl_etps_credit_code) {
        this.dcl_etps_credit_code = dcl_etps_credit_code;
    }

    public String getDcl_etps_ic_no() {
        return dcl_etps_ic_no;
    }

    public void setDcl_etps_ic_no(String dcl_etps_ic_no) {
        this.dcl_etps_ic_no = dcl_etps_ic_no;
    }

    public String getDcl_etps_port() {
        return dcl_etps_port;
    }

    public void setDcl_etps_port(String dcl_etps_port) {
        this.dcl_etps_port = dcl_etps_port;
    }

    public String getEnt_id() {
        return ent_id;
    }

    public void setEnt_id(String ent_id) {
        this.ent_id = ent_id;
    }

    public String getEnt_customs_code() {
        return ent_customs_code;
    }

    public void setEnt_customs_code(String ent_customs_code) {
        this.ent_customs_code = ent_customs_code;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
