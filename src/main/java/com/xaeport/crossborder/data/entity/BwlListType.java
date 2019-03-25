package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class BwlListType {

    private String id;//ID
    private String bws_no;//仓库账册号
    private String chg_tms_cnt;//变更次数 内网交接返填
    private String gds_seqno;//商品序号 自然流水号
    private Date in_date;//最近入仓(核增）日期 最近一次入仓(区）时间,系统自动返填
    private String gds_mtno;//商品料号 记账时由核注清单返填
    private String gdecd;//商品编码 记账时由核注清单返填
    private String gds_nm;//商品名称 记账时由核注清单返填
    private String gds_spcf_model_desc;//商品规格型号 记账时由核注清单返填
    private String natcd;//国别代码

    private String dcl_unitcd;//申报计量单位代码 记账时由核注清单返填
    private String lawf_unitcd;//法定计量单位代码 记账时由核注清单返填
    private String secd_lawf_unitcd;//第二法定计量单位代码 记账时由核注清单返填
    private String dcl_uprc_amt;//申报单价金额 记账时由核注清单返填，设备账册必须填写
    private String dcl_currcd;//申报币制代码 记账时由核注清单返填
    private String avg_price;//平均美元单价 每次总价变化时自动计算：总价/库存
    private String total_amt;//库存美元总价 每次核注(增减）时，同时核注(增减）金额
    private String in_qty;//入仓数量
    private String in_lawf_qty;//入仓法定数量
    private String in_secd_lawf_qty;//第二入仓法定数量

    private String actl_inc_qty;//实增数量
    private String actl_redc_qty;//实减数量
    private String prevd_inc_qty;//预增数量
    private String prevd_redc_qty;//预减数量
    private Date out_date;//最近出仓(区）日期 出仓(区）核注清单核注时系统自动反填
    private Date limit_date;//存储(监管）期限 保税仓为核增时间+1年；出口仓为核增时间+6个月；物流中心为核增时间+2年；设备账册为核增时间+N年，N人工设定
    private String in_type;//设备入区方式代码 记账式系统自动返填,1:一线入区、2：二线入区、3:结转入区
    private String invt_no;//记账清单编号 记账时系统自动返填
    private String invt_g_no;//记账清单商品序号 记账时系统自动返填
    private String cusm_exe_markcd;//海关执行标记代码 1-正常执行 2-恢复执行 3-暂停变更 4-暂停进出口 5-暂停进口 6-暂停出口 7-全部暂停，默认为1

    private String rmk;//备注
    private String modf_markcd;//修改标记
    private Date crt_time;//创建时间
    private String crt_user;//创建人
    private Date upd_time;//更新时间
    private String upd_user;//更新人
    private double norm_qty;//申报数量和法定数量之比
    private String bizop_etpsno;//经营企业编号 关联海关参数库

    private double surplus;//剩余量
    private String dcl_unitcd_name;//申报计量单位中文名称

    private double inQty;
    private double actlRedcQty;
    private double prevdRedcQty;

    public double getNorm_qty() {
        return norm_qty;
    }

    public void setNorm_qty(double norm_qty) {
        this.norm_qty = norm_qty;
    }


    public String getBizop_etpsno() {
        return bizop_etpsno;
    }

    public void setBizop_etpsno(String bizop_etpsno) {
        this.bizop_etpsno = bizop_etpsno;
    }

    public double getInQty() {
        return inQty;
    }

    public void setInQty(double inQty) {
        this.inQty = inQty;
    }

    public double getActlRedcQty() {
        return actlRedcQty;
    }

    public void setActlRedcQty(double actlRedcQty) {
        this.actlRedcQty = actlRedcQty;
    }

    public double getPrevdRedcQty() {
        return prevdRedcQty;
    }

    public void setPrevdRedcQty(double prevdRedcQty) {
        this.prevdRedcQty = prevdRedcQty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBws_no() {
        return bws_no;
    }

    public void setBws_no(String bws_no) {
        this.bws_no = bws_no;
    }

    public String getChg_tms_cnt() {
        return chg_tms_cnt;
    }

    public void setChg_tms_cnt(String chg_tms_cnt) {
        this.chg_tms_cnt = chg_tms_cnt;
    }

    public String getGds_seqno() {
        return gds_seqno;
    }

    public void setGds_seqno(String gds_seqno) {
        this.gds_seqno = gds_seqno;
    }

    public Date getIn_date() {
        return in_date;
    }

    public void setIn_date(Date in_date) {
        this.in_date = in_date;
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

    public String getNatcd() {
        return natcd;
    }

    public void setNatcd(String natcd) {
        this.natcd = natcd;
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

    public String getDcl_uprc_amt() {
        return dcl_uprc_amt;
    }

    public void setDcl_uprc_amt(String dcl_uprc_amt) {
        this.dcl_uprc_amt = dcl_uprc_amt;
    }

    public String getDcl_currcd() {
        return dcl_currcd;
    }

    public void setDcl_currcd(String dcl_currcd) {
        this.dcl_currcd = dcl_currcd;
    }

    public String getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(String avg_price) {
        this.avg_price = avg_price;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public String getIn_qty() {
        return in_qty;
    }

    public void setIn_qty(String in_qty) {
        this.in_qty = in_qty;
    }

    public String getIn_lawf_qty() {
        return in_lawf_qty;
    }

    public void setIn_lawf_qty(String in_lawf_qty) {
        this.in_lawf_qty = in_lawf_qty;
    }

    public String getIn_secd_lawf_qty() {
        return in_secd_lawf_qty;
    }

    public void setIn_secd_lawf_qty(String in_secd_lawf_qty) {
        this.in_secd_lawf_qty = in_secd_lawf_qty;
    }

    public String getActl_inc_qty() {
        return actl_inc_qty;
    }

    public void setActl_inc_qty(String actl_inc_qty) {
        this.actl_inc_qty = actl_inc_qty;
    }

    public String getActl_redc_qty() {
        return actl_redc_qty;
    }

    public void setActl_redc_qty(String actl_redc_qty) {
        this.actl_redc_qty = actl_redc_qty;
    }

    public String getPrevd_inc_qty() {
        return prevd_inc_qty;
    }

    public void setPrevd_inc_qty(String prevd_inc_qty) {
        this.prevd_inc_qty = prevd_inc_qty;
    }

    public String getPrevd_redc_qty() {
        return prevd_redc_qty;
    }

    public void setPrevd_redc_qty(String prevd_redc_qty) {
        this.prevd_redc_qty = prevd_redc_qty;
    }

    public Date getOut_date() {
        return out_date;
    }

    public void setOut_date(Date out_date) {
        this.out_date = out_date;
    }

    public Date getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(Date limit_date) {
        this.limit_date = limit_date;
    }

    public String getIn_type() {
        return in_type;
    }

    public void setIn_type(String in_type) {
        this.in_type = in_type;
    }

    public String getInvt_no() {
        return invt_no;
    }

    public void setInvt_no(String invt_no) {
        this.invt_no = invt_no;
    }

    public String getInvt_g_no() {
        return invt_g_no;
    }

    public void setInvt_g_no(String invt_g_no) {
        this.invt_g_no = invt_g_no;
    }

    public String getCusm_exe_markcd() {
        return cusm_exe_markcd;
    }

    public void setCusm_exe_markcd(String cusm_exe_markcd) {
        this.cusm_exe_markcd = cusm_exe_markcd;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
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

    public double getSurplus() {
        return surplus;
    }

    public void setSurplus(double surplus) {
        this.surplus = surplus;
    }

    public String getDcl_unitcd_name() {
        return dcl_unitcd_name;
    }

    public void setDcl_unitcd_name(String dcl_unitcd_name) {
        this.dcl_unitcd_name = dcl_unitcd_name;
    }
}
