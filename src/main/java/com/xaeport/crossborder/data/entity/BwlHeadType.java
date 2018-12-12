package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class BwlHeadType {

    private String id;//ID
    private String bws_no;//仓库账册号
    private String chg_tms_cnt;//变更次数
    private String etps_preent_no;//统一编号
    private String dcl_typecd;//申报类型代码
    private String bwl_typecd;//区域场所类别
    private String master_cuscd;//主管海关代码
    private String bizop_etpsno;//经营企业编号
    private String bizop_etps_nm;//经营企业名称
    private String bizop_etps_sccd;//经营企业社会信用代码

    private String house_no;//仓库编号
    private String house_nm;//仓库名称
    private String dcl_etpsno;//申报企业编号
    private String dcl_etps_nm;//申报企业名称
    private String dcl_etps_sccd;//申报企业社会信用代码
    private String dcl_etps_typecd;//申报单位类型代码
    private String contact_er;//联系人
    private String contact_tele;//联系电话
    private String house_typecd;//企业类型代码
    private String house_area;//仓库面积

    private String house_volume;//仓库容积
    private String house_address;//仓库地址
    private Date dcl_time;//申报时间
    private Date input_date;//录入日期
    private String tax_typecd;//退税标志代码
    private Date putrec_appr_time;//备案批准时间
    private Date chg_appr_time;//变更批准时间
    private Date finish_valid_date;//结束有效日期
    private String pause_chg_markcd;//暂停变更标记代码
    private String emapv_stucd;//审核状态代码

    private String dcl_markcd;//申报标志代码
    private String append_typecd;//记账模式代码
    private String rmk;//备注
    private String owner_system;//所属系统
    private String usage_typecd;//账册用途
    private String status;//数据状态
    private String return_status;//回执状态
    private Date return_date;//回执时间
    private Date crt_time;//创建时间
    private String crt_user;//创建人
    private Date upd_time;//更新时间
    private String upd_user;//更新人

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

    public String getEtps_preent_no() {
        return etps_preent_no;
    }

    public void setEtps_preent_no(String etps_preent_no) {
        this.etps_preent_no = etps_preent_no;
    }

    public String getDcl_typecd() {
        return dcl_typecd;
    }

    public void setDcl_typecd(String dcl_typecd) {
        this.dcl_typecd = dcl_typecd;
    }

    public String getBwl_typecd() {
        return bwl_typecd;
    }

    public void setBwl_typecd(String bwl_typecd) {
        this.bwl_typecd = bwl_typecd;
    }

    public String getMaster_cuscd() {
        return master_cuscd;
    }

    public void setMaster_cuscd(String master_cuscd) {
        this.master_cuscd = master_cuscd;
    }

    public String getBizop_etpsno() {
        return bizop_etpsno;
    }

    public void setBizop_etpsno(String bizop_etpsno) {
        this.bizop_etpsno = bizop_etpsno;
    }

    public String getBizop_etps_nm() {
        return bizop_etps_nm;
    }

    public void setBizop_etps_nm(String bizop_etps_nm) {
        this.bizop_etps_nm = bizop_etps_nm;
    }

    public String getBizop_etps_sccd() {
        return bizop_etps_sccd;
    }

    public void setBizop_etps_sccd(String bizop_etps_sccd) {
        this.bizop_etps_sccd = bizop_etps_sccd;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getHouse_nm() {
        return house_nm;
    }

    public void setHouse_nm(String house_nm) {
        this.house_nm = house_nm;
    }

    public String getDcl_etpsno() {
        return dcl_etpsno;
    }

    public void setDcl_etpsno(String dcl_etpsno) {
        this.dcl_etpsno = dcl_etpsno;
    }

    public String getDcl_etps_nm() {
        return dcl_etps_nm;
    }

    public void setDcl_etps_nm(String dcl_etps_nm) {
        this.dcl_etps_nm = dcl_etps_nm;
    }

    public String getDcl_etps_sccd() {
        return dcl_etps_sccd;
    }

    public void setDcl_etps_sccd(String dcl_etps_sccd) {
        this.dcl_etps_sccd = dcl_etps_sccd;
    }

    public String getDcl_etps_typecd() {
        return dcl_etps_typecd;
    }

    public void setDcl_etps_typecd(String dcl_etps_typecd) {
        this.dcl_etps_typecd = dcl_etps_typecd;
    }

    public String getContact_er() {
        return contact_er;
    }

    public void setContact_er(String contact_er) {
        this.contact_er = contact_er;
    }

    public String getContact_tele() {
        return contact_tele;
    }

    public void setContact_tele(String contact_tele) {
        this.contact_tele = contact_tele;
    }

    public String getHouse_typecd() {
        return house_typecd;
    }

    public void setHouse_typecd(String house_typecd) {
        this.house_typecd = house_typecd;
    }

    public String getHouse_area() {
        return house_area;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public String getHouse_volume() {
        return house_volume;
    }

    public void setHouse_volume(String house_volume) {
        this.house_volume = house_volume;
    }

    public String getHouse_address() {
        return house_address;
    }

    public void setHouse_address(String house_address) {
        this.house_address = house_address;
    }

    public Date getDcl_time() {
        return dcl_time;
    }

    public void setDcl_time(Date dcl_time) {
        this.dcl_time = dcl_time;
    }

    public Date getInput_date() {
        return input_date;
    }

    public void setInput_date(Date input_date) {
        this.input_date = input_date;
    }

    public String getTax_typecd() {
        return tax_typecd;
    }

    public void setTax_typecd(String tax_typecd) {
        this.tax_typecd = tax_typecd;
    }

    public Date getPutrec_appr_time() {
        return putrec_appr_time;
    }

    public void setPutrec_appr_time(Date putrec_appr_time) {
        this.putrec_appr_time = putrec_appr_time;
    }

    public Date getChg_appr_time() {
        return chg_appr_time;
    }

    public void setChg_appr_time(Date chg_appr_time) {
        this.chg_appr_time = chg_appr_time;
    }

    public Date getFinish_valid_date() {
        return finish_valid_date;
    }

    public void setFinish_valid_date(Date finish_valid_date) {
        this.finish_valid_date = finish_valid_date;
    }

    public String getPause_chg_markcd() {
        return pause_chg_markcd;
    }

    public void setPause_chg_markcd(String pause_chg_markcd) {
        this.pause_chg_markcd = pause_chg_markcd;
    }

    public String getEmapv_stucd() {
        return emapv_stucd;
    }

    public void setEmapv_stucd(String emapv_stucd) {
        this.emapv_stucd = emapv_stucd;
    }

    public String getDcl_markcd() {
        return dcl_markcd;
    }

    public void setDcl_markcd(String dcl_markcd) {
        this.dcl_markcd = dcl_markcd;
    }

    public String getAppend_typecd() {
        return append_typecd;
    }

    public void setAppend_typecd(String append_typecd) {
        this.append_typecd = append_typecd;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public String getOwner_system() {
        return owner_system;
    }

    public void setOwner_system(String owner_system) {
        this.owner_system = owner_system;
    }

    public String getUsage_typecd() {
        return usage_typecd;
    }

    public void setUsage_typecd(String usage_typecd) {
        this.usage_typecd = usage_typecd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturn_status() {
        return return_status;
    }

    public void setReturn_status(String return_status) {
        this.return_status = return_status;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
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
