package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class ImpRecInventory {

    private String id;//
    private String guid;//系统生成36 位唯一序号（英文字母大写）
    private String customs_code;//接受申报的海关关区代码。
    private String ebp_code;//电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。
    private String ebc_code;//电商企业的海关注册登记编号。
    private String agent_code;//申报单位的海关注册登记编号。
    private String cop_no;//企业内部标识单证的编号
    private String pre_no;//电子口岸标识单证的编号
    private String invt_no;//海关审核反馈清单的编号
    private String return_status;//操作结果（1电子口岸已暂存/2电子口岸申报中/3发送海关成功/4发送海关失败/100海关退单/120海关入库/300人工审核/399海关审结/800放行/899结关/500查验/501扣留移送通关/502扣留移送缉私/503扣留移送法规/599其它扣留/700退运）,若小于0数字表示处理异常回执
    private String return_time;//操作时间(格式：yyyyMMddHHmmssfff)
    private String return_info;//备注（如:退单原因）
    private Date crt_tm;//创建时间
    private Date upd_tm;//更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCustoms_code() {
        return customs_code;
    }

    public void setCustoms_code(String customs_code) {
        this.customs_code = customs_code;
    }

    public String getEbp_code() {
        return ebp_code;
    }

    public void setEbp_code(String ebp_code) {
        this.ebp_code = ebp_code;
    }

    public String getEbc_code() {
        return ebc_code;
    }

    public void setEbc_code(String ebc_code) {
        this.ebc_code = ebc_code;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public String getCop_no() {
        return cop_no;
    }

    public void setCop_no(String cop_no) {
        this.cop_no = cop_no;
    }

    public String getPre_no() {
        return pre_no;
    }

    public void setPre_no(String pre_no) {
        this.pre_no = pre_no;
    }

    public String getInvt_no() {
        return invt_no;
    }

    public void setInvt_no(String invt_no) {
        this.invt_no = invt_no;
    }

    public String getReturn_status() {
        return return_status;
    }

    public void setReturn_status(String return_status) {
        this.return_status = return_status;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public String getReturn_info() {
        return return_info;
    }

    public void setReturn_info(String return_info) {
        this.return_info = return_info;
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
