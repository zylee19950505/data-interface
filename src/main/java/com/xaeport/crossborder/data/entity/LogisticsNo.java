package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class LogisticsNo {

    private String id;
    private String logistics_type;
    private String logistics_no;
    private Date crt_tm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogistics_type() {
        return logistics_type;
    }

    public void setLogistics_type(String logistics_type) {
        this.logistics_type = logistics_type;
    }

    public String getLogistics_no() {
        return logistics_no;
    }

    public void setLogistics_no(String logistics_no) {
        this.logistics_no = logistics_no;
    }

    public Date getCrt_tm() {
        return crt_tm;
    }

    public void setCrt_tm(Date crt_tm) {
        this.crt_tm = crt_tm;
    }
}
