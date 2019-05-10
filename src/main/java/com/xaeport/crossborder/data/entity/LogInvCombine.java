package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class LogInvCombine {

    private String id;
    private String bill_no;
    private String order_no;
    private String logistics_no;
    private String order_mark;
    private String logistics_mark;
    private Date crt_tm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLogistics_no() {
        return logistics_no;
    }

    public void setLogistics_no(String logistics_no) {
        this.logistics_no = logistics_no;
    }

    public String getOrder_mark() {
        return order_mark;
    }

    public void setOrder_mark(String order_mark) {
        this.order_mark = order_mark;
    }

    public String getLogistics_mark() {
        return logistics_mark;
    }

    public void setLogistics_mark(String logistics_mark) {
        this.logistics_mark = logistics_mark;
    }

    public Date getCrt_tm() {
        return crt_tm;
    }

    public void setCrt_tm(Date crt_tm) {
        this.crt_tm = crt_tm;
    }
}
