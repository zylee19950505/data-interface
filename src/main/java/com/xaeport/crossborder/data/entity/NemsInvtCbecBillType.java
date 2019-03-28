package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class NemsInvtCbecBillType {

    private String id;//ID
    private int no;
    private String seq_no;//预录入统一编号
    private String bond_invt_no;//核注清单编号
    private String cbec_bill_no;//电商清单编号
    private Date crt_time;//
    private String crt_user;//
    private Date upd_time;//
    private String upd_user;//
    private String head_etps_inner_invt_no;//表头唯一关联码
    private String bill_no;//提运单号
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(String seq_no) {
        this.seq_no = seq_no;
    }

    public String getBond_invt_no() {
        return bond_invt_no;
    }

    public void setBond_invt_no(String bond_invt_no) {
        this.bond_invt_no = bond_invt_no;
    }

    public String getCbec_bill_no() {
        return cbec_bill_no;
    }

    public void setCbec_bill_no(String cbec_bill_no) {
        this.cbec_bill_no = cbec_bill_no;
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

    public String getHead_etps_inner_invt_no() {
        return head_etps_inner_invt_no;
    }

    public void setHead_etps_inner_invt_no(String head_etps_inner_invt_no) {
        this.head_etps_inner_invt_no = head_etps_inner_invt_no;
    }
}

