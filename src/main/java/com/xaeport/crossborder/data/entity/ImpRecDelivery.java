package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class ImpRecDelivery {
    private String id;
    private String guid;
    private String bill_no;
    private String customs_code;
    private String operator_code;
    private String cop_no;
    private String pre_no;
    private String rkd_no;
    private String return_status;
    private String return_time;
    private String return_info;
    private Date crt_tm;
    private Date upd_tm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getCustoms_code() {
        return customs_code;
    }

    public void setCustoms_code(String customs_code) {
        this.customs_code = customs_code;
    }

    public String getOperator_code() {
        return operator_code;
    }

    public void setOperator_code(String operator_code) {
        this.operator_code = operator_code;
    }

    public String getCop_no() {
        return cop_no;
    }

    public void setCop_no(String cop_no) {
        this.cop_no = cop_no;
    }

    public String getPre_no() {
        return pre_no;
    }

    public void setPre_no(String pre_no) {
        this.pre_no = pre_no;
    }

    public String getRkd_no() {
        return rkd_no;
    }

    public void setRkd_no(String rkd_no) {
        this.rkd_no = rkd_no;
    }

    public String getReturn_status() {
        return return_status;
    }

    public void setReturn_status(String return_status) {
        this.return_status = return_status;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public String getReturn_info() {
        return return_info;
    }

    public void setReturn_info(String return_info) {
        this.return_info = return_info;
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
