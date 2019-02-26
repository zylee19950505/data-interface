package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class BondInvtDt {

    private String id;//ID
    private String bond_invt_no;//保税清单编号
    private String chg_tms_cnt;//变更次数
    private int gds_seqno;//商品序号
    private int putrec_seqno;//备案序号
    private String gds_mtno;//商品料号
    private String gdecd;//商品编码
    private String gds_nm;//商品名称
    private String gds_spcf_model_desc;//商品规格型号描述
    private String dcl_unitcd;//申报计量单位

    private String lawf_unitcd;//法定计量单位
    private String secd_lawf_unitcd;//法定第二计量
    private String natcd;//原产国(地区)
    private String dcl_uprc_amt;//申报单价金额
    private String dcl_total_amt;//申报总金额
    private String usd_stat_total_amt;//美元统计总金额 按清单申报日期取汇率折算返填
    private String dcl_currcd;//申报币制代码
    private String lawf_qty;//法定数量
    private String secd_lawf_qty;//第二法定数量
    private String wt_sf_val;//重量比例因子值

    private String fst_sf_val;//第一比例因子
    private String secd_sf_val;//第二比例因子
    private String dcl_qty;//申报数量
    private String gross_wt;//毛重量
    private String net_wt;//净重量
    private String lvyrlf_modecd;//征减免方式代码
    private String ucns_verno;//账册备案单耗明细版本号
    private String entry_gds_seqno;//报关单商品序号 需报关的清单，为必填项
    private String apply_tb_seqno;//申请表序号 流转类专用。用于建立清单商品与流转申请表商品之间的关系
    private String cly_markcd;//归类标记代码

    private String rmk;//备注
    private String destination_natcd;//最终目的国（地区） 参照国别代码表(COUNTRY)
    private String modf_markcd;//修改标志 0-未修改 1-修改 2-删除 3-增加。备用字段，企业自主发起变更、删除功能转用，此功能明年上线。
    private Date crt_time;//创建时间
    private String crt_user;//创建人
    private Date upd_time;//更新时间
    private String upd_user;//更新人
    private String head_etps_inner_invt_no;//表头唯一关联码
    private String surplus_nm;//剩余数量
    private double quantity;//申报数量
    private String Usecd;//用途代码

    public String getUsecd() {
        return Usecd;
    }

    public void setUsecd(String usecd) {
        Usecd = usecd;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getSurplus_nm() {
        return surplus_nm;
    }

    public void setSurplus_nm(String surplus_nm) {
        this.surplus_nm = surplus_nm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBond_invt_no() {
        return bond_invt_no;
    }

    public void setBond_invt_no(String bond_invt_no) {
        this.bond_invt_no = bond_invt_no;
    }

    public String getChg_tms_cnt() {
        return chg_tms_cnt;
    }

    public void setChg_tms_cnt(String chg_tms_cnt) {
        this.chg_tms_cnt = chg_tms_cnt;
    }

    public int getGds_seqno() {
        return gds_seqno;
    }

    public void setGds_seqno(int gds_seqno) {
        this.gds_seqno = gds_seqno;
    }

    public int getPutrec_seqno() {
        return putrec_seqno;
    }

    public void setPutrec_seqno(int putrec_seqno) {
        this.putrec_seqno = putrec_seqno;
    }

    public String getGds_mtno() {
        return gds_mtno;
    }

    public void setGds_mtno(String gds_mtno) {
        this.gds_mtno = gds_mtno;
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

    public String getGds_spcf_model_desc() {
        return gds_spcf_model_desc;
    }

    public void setGds_spcf_model_desc(String gds_spcf_model_desc) {
        this.gds_spcf_model_desc = gds_spcf_model_desc;
    }

    public String getDcl_unitcd() {
        return dcl_unitcd;
    }

    public void setDcl_unitcd(String dcl_unitcd) {
        this.dcl_unitcd = dcl_unitcd;
    }

    public String getLawf_unitcd() {
        return lawf_unitcd;
    }

    public void setLawf_unitcd(String lawf_unitcd) {
        this.lawf_unitcd = lawf_unitcd;
    }

    public String getSecd_lawf_unitcd() {
        return secd_lawf_unitcd;
    }

    public void setSecd_lawf_unitcd(String secd_lawf_unitcd) {
        this.secd_lawf_unitcd = secd_lawf_unitcd;
    }

    public String getNatcd() {
        return natcd;
    }

    public void setNatcd(String natcd) {
        this.natcd = natcd;
    }

    public String getDcl_uprc_amt() {
        return dcl_uprc_amt;
    }

    public void setDcl_uprc_amt(String dcl_uprc_amt) {
        this.dcl_uprc_amt = dcl_uprc_amt;
    }

    public String getDcl_total_amt() {
        return dcl_total_amt;
    }

    public void setDcl_total_amt(String dcl_total_amt) {
        this.dcl_total_amt = dcl_total_amt;
    }

    public String getUsd_stat_total_amt() {
        return usd_stat_total_amt;
    }

    public void setUsd_stat_total_amt(String usd_stat_total_amt) {
        this.usd_stat_total_amt = usd_stat_total_amt;
    }

    public String getDcl_currcd() {
        return dcl_currcd;
    }

    public void setDcl_currcd(String dcl_currcd) {
        this.dcl_currcd = dcl_currcd;
    }

    public String getLawf_qty() {
        return lawf_qty;
    }

    public void setLawf_qty(String lawf_qty) {
        this.lawf_qty = lawf_qty;
    }

    public String getSecd_lawf_qty() {
        return secd_lawf_qty;
    }

    public void setSecd_lawf_qty(String secd_lawf_qty) {
        this.secd_lawf_qty = secd_lawf_qty;
    }

    public String getWt_sf_val() {
        return wt_sf_val;
    }

    public void setWt_sf_val(String wt_sf_val) {
        this.wt_sf_val = wt_sf_val;
    }

    public String getFst_sf_val() {
        return fst_sf_val;
    }

    public void setFst_sf_val(String fst_sf_val) {
        this.fst_sf_val = fst_sf_val;
    }

    public String getSecd_sf_val() {
        return secd_sf_val;
    }

    public void setSecd_sf_val(String secd_sf_val) {
        this.secd_sf_val = secd_sf_val;
    }

    public String getDcl_qty() {
        return dcl_qty;
    }

    public void setDcl_qty(String dcl_qty) {
        this.dcl_qty = dcl_qty;
    }

    public String getGross_wt() {
        return gross_wt;
    }

    public void setGross_wt(String gross_wt) {
        this.gross_wt = gross_wt;
    }

    public String getNet_wt() {
        return net_wt;
    }

    public void setNet_wt(String net_wt) {
        this.net_wt = net_wt;
    }

    public String getLvyrlf_modecd() {
        return lvyrlf_modecd;
    }

    public void setLvyrlf_modecd(String lvyrlf_modecd) {
        this.lvyrlf_modecd = lvyrlf_modecd;
    }

    public String getUcns_verno() {
        return ucns_verno;
    }

    public void setUcns_verno(String ucns_verno) {
        this.ucns_verno = ucns_verno;
    }

    public String getEntry_gds_seqno() {
        return entry_gds_seqno;
    }

    public void setEntry_gds_seqno(String entry_gds_seqno) {
        this.entry_gds_seqno = entry_gds_seqno;
    }

    public String getApply_tb_seqno() {
        return apply_tb_seqno;
    }

    public void setApply_tb_seqno(String apply_tb_seqno) {
        this.apply_tb_seqno = apply_tb_seqno;
    }

    public String getCly_markcd() {
        return cly_markcd;
    }

    public void setCly_markcd(String cly_markcd) {
        this.cly_markcd = cly_markcd;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public String getDestination_natcd() {
        return destination_natcd;
    }

    public void setDestination_natcd(String destination_natcd) {
        this.destination_natcd = destination_natcd;
    }

    public String getModf_markcd() {
        return modf_markcd;
    }

    public void setModf_markcd(String modf_markcd) {
        this.modf_markcd = modf_markcd;
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
