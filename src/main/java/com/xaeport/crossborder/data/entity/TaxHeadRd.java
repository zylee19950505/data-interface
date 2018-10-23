package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class TaxHeadRd {
    private String guid;//系统唯一序号
    private String private_guid;//报文GUID码
    private String return_time;//回执时间
    private String invt_no;//进境清单编号
    private String tax_no;//电子税单编号
    private String customs_tax;//应征关税
    private String value_added_tax;//应征增值税
    private String consumption_tax;//应征消费税
    private String status;//税单状态（1已生成，2已汇总，3作废）
    private String entduty_no;//缴款书编号
    private String note;//备注
    private String assure_code;//担保企业代码
    private String ebc_code;//电商企业代码
    private String logistics_code;//物流企业代码
    private String agent_code;//申报单位，10位海关码，或18位信用代码
    private String customs_code;//申报口岸
    private String order_no;//订单编号
    private String logistics_no;//物流运单编号
    private Date crt_tm;//创建时间
    private Date upd_tm;//更新时间

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPrivate_guid() {
        return private_guid;
    }

    public void setPrivate_guid(String private_guid) {
        this.private_guid = private_guid;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public String getInvt_no() {
        return invt_no;
    }

    public void setInvt_no(String invt_no) {
        this.invt_no = invt_no;
    }

    public String getTax_no() {
        return tax_no;
    }

    public void setTax_no(String tax_no) {
        this.tax_no = tax_no;
    }

    public String getCustoms_tax() {
        return customs_tax;
    }

    public void setCustoms_tax(String customs_tax) {
        this.customs_tax = customs_tax;
    }

    public String getValue_added_tax() {
        return value_added_tax;
    }

    public void setValue_added_tax(String value_added_tax) {
        this.value_added_tax = value_added_tax;
    }

    public String getConsumption_tax() {
        return consumption_tax;
    }

    public void setConsumption_tax(String consumption_tax) {
        this.consumption_tax = consumption_tax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntduty_no() {
        return entduty_no;
    }

    public void setEntduty_no(String entduty_no) {
        this.entduty_no = entduty_no;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAssure_code() {
        return assure_code;
    }

    public void setAssure_code(String assure_code) {
        this.assure_code = assure_code;
    }

    public String getEbc_code() {
        return ebc_code;
    }

    public void setEbc_code(String ebc_code) {
        this.ebc_code = ebc_code;
    }

    public String getLogistics_code() {
        return logistics_code;
    }

    public void setLogistics_code(String logistics_code) {
        this.logistics_code = logistics_code;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public String getCustoms_code() {
        return customs_code;
    }

    public void setCustoms_code(String customs_code) {
        this.customs_code = customs_code;
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
