package com.xaeport.crossborder.data.entity;

/*
* 核放单表体
* */
public class PassPortList {

    private String id;
    private String head_id;//关联核放单
    private String seq_no;//核放单预录入编号
    private String passPort_no;//核放单编号
    private Integer passPort_seqNo;//序号
    private String gds_mtNo;//商品料号,根据关联单证商品序号自动返填
    private String gdecd;//商品编码,根据关联单证商品序号自动返填
    private String gds_nm;//商品名称,根据关联单证商品序号自动返填
    private Double gross_wt;//货物毛重
    private Double net_wt;//货物净重
    private String rlt_gds_seqNo;//关联商品序号,关联单证对应商品的序号，类型非卡口登记货物时，关联商品序号也必填
    private String dcl_unitcd;//申报单位代码
    private Integer dcl_qty;//申报数量,根据关联单证商品序号自动返填
    private String rmk;//备注
    private String bond_invt_no;//相关核注清单编号
    private Double quantity;

    public String getBond_invt_no() {
        return bond_invt_no;
    }

    public void setBond_invt_no(String bond_invt_no) {
        this.bond_invt_no = bond_invt_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHead_id() {
        return head_id;
    }

    public void setHead_id(String head_id) {
        this.head_id = head_id;
    }

    public String getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(String seq_no) {
        this.seq_no = seq_no;
    }

    public String getPassPort_no() {
        return passPort_no;
    }

    public void setPassPort_no(String passPort_no) {
        this.passPort_no = passPort_no;
    }

    public Integer getPassPort_seqNo() {
        return passPort_seqNo;
    }

    public void setPassPort_seqNo(Integer passPort_seqNo) {
        this.passPort_seqNo = passPort_seqNo;
    }

    public String getGds_mtNo() {
        return gds_mtNo;
    }

    public void setGds_mtNo(String gds_mtNo) {
        this.gds_mtNo = gds_mtNo;
    }

    public String getGdecd() {
        return gdecd;
    }

    public void setGdecd(String gdecd) {
        this.gdecd = gdecd;
    }

    public String getGds_nm() {
        return gds_nm;
    }

    public void setGds_nm(String gds_nm) {
        this.gds_nm = gds_nm;
    }

    public Double getGross_wt() {
        return gross_wt;
    }

    public void setGross_wt(Double gross_wt) {
        this.gross_wt = gross_wt;
    }

    public Double getNet_wt() {
        return net_wt;
    }

    public void setNet_wt(Double net_wt) {
        this.net_wt = net_wt;
    }

    public String getRlt_gds_seqNo() {
        return rlt_gds_seqNo;
    }

    public void setRlt_gds_seqNo(String rlt_gds_seqNo) {
        this.rlt_gds_seqNo = rlt_gds_seqNo;
    }

    public String getDcl_unitcd() {
        return dcl_unitcd;
    }

    public void setDcl_unitcd(String dcl_unitcd) {
        this.dcl_unitcd = dcl_unitcd;
    }

    public Integer getDcl_qty() {
        return dcl_qty;
    }

    public void setDcl_qty(Integer dcl_qty) {
        this.dcl_qty = dcl_qty;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
