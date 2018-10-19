package com.xaeport.crossborder.data.entity;

import java.util.Date;

/**
 * Created by baozhe on 2017-7-25.
 */
public class StatusRecord {
    private String sr_id;
    private String status_code;
    private String belong;
    private String odd_no;
    private Date create_time;
    private String notes;

    public String getSr_id() {
        return sr_id;
    }

    public void setSr_id(String sr_id) {
        this.sr_id = sr_id;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getOdd_no() {
        return odd_no;
    }

    public void setOdd_no(String odd_no) {
        this.odd_no = odd_no;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
