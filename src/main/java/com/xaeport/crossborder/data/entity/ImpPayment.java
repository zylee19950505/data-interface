package com.xaeport.crossborder.data.entity;

import java.util.Date;

/*
* 进口支付单管理实体类
* 创建时间：07/10
* 创建人：赵文枫
* */
public class ImpPayment {
    private String guid;//企业系统生成36 位唯一序号（英文字母大写）
    private String app_type;//企业报送类型。1-新增2-变更3-删除。默认为1。
    private Date app_time;//企业报送时间。格式:YYYYMMDDhhmmss。
    private String app_status;//业务状态:1-暂存,2-申报,默认为2。
    private String pay_code;//支付企业的海关注册登记编号。
    private String pay_name;//支付企业在海关注册登记的名称。
    private String pay_transaction_id;//支付企业唯一的支付流水号。
    private String order_no;//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
    private String ebp_code;//电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。
    private String ebp_name;//电商平台的海关注册登记名称；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台名称为准。
    private String payer_id_type;//1-身份证,2-其它。限定为身份证，填写1。
    private String payer_id_number;//支付人的身份证件号码。
    private String payer_name;//支付人的真实姓名。
    private String telephone;//支付人的电话号码。
    private String amount_paid;//支付金额。
    private String currency;//限定为人民币，填写142。
    private Date pay_time;//支付时间， 格式:YYYYMMDDhhmmss。
    private String note;//备注
    private String data_status;//数据状态
    private String crt_id;//创建人ID
    private Date crt_tm;//创建时间
    private String upd_id;//更新人ID
    private Date upd_tm;//更新时间
    private String return_status;//回执状态
    private String return_info;//回执备注信息
    private String return_time;//回执时间
    private String ent_id;//导入企业ID码
    private String ent_name;//导入企业名称
    private String ent_customs_code;//导入企业海关十位
    private String return_status_name;//回执状态对应中文意思

    public String getReturn_status_name() {
        return return_status_name;
    }

    public void setReturn_status_name(String return_status_name) {
        this.return_status_name = return_status_name;
    }

    public String getReturn_status() {
        return return_status;
    }

    public void setReturn_status(String return_status) {
        this.return_status = return_status;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public Date getApp_time() {
        return app_time;
    }

    public void setApp_time(Date app_time) {
        this.app_time = app_time;
    }

    public String getApp_status() {
        return app_status;
    }

    public void setApp_status(String app_status) {
        this.app_status = app_status;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getPay_transaction_id() {
        return pay_transaction_id;
    }

    public void setPay_transaction_id(String pay_transaction_id) {
        this.pay_transaction_id = pay_transaction_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getEbp_code() {
        return ebp_code;
    }

    public void setEbp_code(String ebp_code) {
        this.ebp_code = ebp_code;
    }

    public String getEbp_name() {
        return ebp_name;
    }

    public void setEbp_name(String ebp_name) {
        this.ebp_name = ebp_name;
    }

    public String getPayer_id_type() {
        return payer_id_type;
    }

    public void setPayer_id_type(String payer_id_type) {
        this.payer_id_type = payer_id_type;
    }

    public String getPayer_id_number() {
        return payer_id_number;
    }

    public void setPayer_id_number(String payer_id_number) {
        this.payer_id_number = payer_id_number;
    }

    public String getPayer_name() {
        return payer_name;
    }

    public void setPayer_name(String payer_name) {
        this.payer_name = payer_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getData_status() {
        return data_status;
    }

    public void setData_status(String data_status) {
        this.data_status = data_status;
    }

    public String getCrt_id() {
        return crt_id;
    }

    public void setCrt_id(String crt_id) {
        this.crt_id = crt_id;
    }

    public Date getCrt_tm() {
        return crt_tm;
    }

    public void setCrt_tm(Date crt_tm) {
        this.crt_tm = crt_tm;
    }

    public String getUpd_id() {
        return upd_id;
    }

    public void setUpd_id(String upd_id) {
        this.upd_id = upd_id;
    }

    public Date getUpd_tm() {
        return upd_tm;
    }

    public void setUpd_tm(Date upd_tm) {
        this.upd_tm = upd_tm;
    }

    public String getReturn_info() {
        return return_info;
    }

    public void setReturn_info(String return_info) {
        this.return_info = return_info;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public String getEnt_id() {
        return ent_id;
    }

    public void setEnt_id(String ent_id) {
        this.ent_id = ent_id;
    }

    public String getEnt_name() {
        return ent_name;
    }

    public void setEnt_name(String ent_name) {
        this.ent_name = ent_name;
    }

    public String getEnt_customs_code() {
        return ent_customs_code;
    }

    public void setEnt_customs_code(String ent_customs_code) {
        this.ent_customs_code = ent_customs_code;
    }
}
