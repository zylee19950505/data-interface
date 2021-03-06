package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class Enterprise {

    private String id;//唯一ID主键
    private String ent_name;//企业名称
    private String ent_code;//企业代码
    private String ent_legal;//企业法人
    private String ent_phone;//企业电话
    private String ent_unique_code;//企业唯一编号
    private String org_code;//企业组织机构代码
    private String business_code;//企业工商营业执照号
    private String tax_code;//企业税务登记代码
    private String credit_code;//企业统一社会信用代码
    private String ent_type;//企业类别
    private String ent_nature;//企业性质
    private String port;//主管海关
    private String customs_code;//海关10位代码
    private String ent_classify;//企业分类
    private Date crt_tm;//创建时间
    private Date upd_tm;//更新时间
    private String crt_id;//创建人
    private String status;//企业状态
    private String upd_id;//更新人
    private String ent_status;//企业状态（1 OR 0）
    private String port_str;//主管海关
    private String dxp_id;//企业dxp_id
    private String ent_business_type;//企业业务类型
    private String brevity_code;//企业简码
    private String declare_ent_name;//申报企业名称
    private String declare_ent_code;//申报企业海关注册编码
    private String assure_ent_name;//担保企业名称
    private String assure_ent_code;//担保企业海关注册编码
    private String portCnName;//主管海关中文名称
    private String area_code;//区内企业编码
    private String area_name;//区内企业名称

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getPortCnName() {
        return portCnName;
    }

    public void setPortCnName(String portCnName) {
        this.portCnName = portCnName;
    }

    public String getBrevity_code() {
        return brevity_code;
    }

    public void setBrevity_code(String brevity_code) {
        this.brevity_code = brevity_code;
    }

    public String getDeclare_ent_name() {
        return declare_ent_name;
    }

    public void setDeclare_ent_name(String declare_ent_name) {
        this.declare_ent_name = declare_ent_name;
    }

    public String getDeclare_ent_code() {
        return declare_ent_code;
    }

    public void setDeclare_ent_code(String declare_ent_code) {
        this.declare_ent_code = declare_ent_code;
    }


    public String getAssure_ent_name() {
        return assure_ent_name;
    }

    public void setAssure_ent_name(String assure_ent_name) {
        this.assure_ent_name = assure_ent_name;
    }

    public String getAssure_ent_code() {
        return assure_ent_code;
    }

    public void setAssure_ent_code(String assure_ent_code) {
        this.assure_ent_code = assure_ent_code;
    }

    public String getDxp_id() {
        return dxp_id;
    }

    public void setDxp_id(String dxp_id) {
        this.dxp_id = dxp_id;
    }

    public String getPort_str() {
        return port_str;
    }

    public void setPort_str(String port_str) {
        this.port_str = port_str;
    }

    public String getEnt_status() {
        return ent_status;
    }

    public void setEnt_status(String ent_status) {
        this.ent_status = ent_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnt_name() {
        return ent_name;
    }

    public void setEnt_name(String ent_name) {
        this.ent_name = ent_name;
    }

    public String getEnt_code() {
        return ent_code;
    }

    public void setEnt_code(String ent_code) {
        this.ent_code = ent_code;
    }

    public String getEnt_legal() {
        return ent_legal;
    }

    public void setEnt_legal(String ent_legal) {
        this.ent_legal = ent_legal;
    }

    public String getEnt_phone() {
        return ent_phone;
    }

    public void setEnt_phone(String ent_phone) {
        this.ent_phone = ent_phone;
    }

    public String getEnt_unique_code() {
        return ent_unique_code;
    }

    public void setEnt_unique_code(String ent_unique_code) {
        this.ent_unique_code = ent_unique_code;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getBusiness_code() {
        return business_code;
    }

    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }

    public String getTax_code() {
        return tax_code;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }

    public String getCredit_code() {
        return credit_code;
    }

    public void setCredit_code(String credit_code) {
        this.credit_code = credit_code;
    }

    public String getEnt_type() {
        return ent_type;
    }

    public void setEnt_type(String ent_type) {
        this.ent_type = ent_type;
    }

    public String getEnt_nature() {
        return ent_nature;
    }

    public void setEnt_nature(String ent_nature) {
        this.ent_nature = ent_nature;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCustoms_code() {
        return customs_code;
    }

    public void setCustoms_code(String customs_code) {
        this.customs_code = customs_code;
    }

    public String getEnt_classify() {
        return ent_classify;
    }

    public void setEnt_classify(String ent_classify) {
        this.ent_classify = ent_classify;
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

    public String getCrt_id() {
        return crt_id;
    }

    public void setCrt_id(String crt_id) {
        this.crt_id = crt_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpd_id() {
        return upd_id;
    }

    public void setUpd_id(String upd_id) {
        this.upd_id = upd_id;
    }

    public String getEnt_business_type() {
        return ent_business_type;
    }

    public void setEnt_business_type(String ent_business_type) {
        this.ent_business_type = ent_business_type;
    }
}
