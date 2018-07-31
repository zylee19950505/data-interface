package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class ImpRecPayment {
    private String id;//
    private String guid;//系统生成36 位唯一序号（英文字母大写）
    private String pay_code;//支付企业的海关注册登记编号。
    private String pay_transaction_id;//支付企业唯一的支付流水号。
    private String order_no;//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
    private String return_status;//操作结果（2电子口岸申报中/3发送海关成功/4发送海关失败/100海关退单/120海关入库）,若小于0 数字表示处理异常回执
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

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
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
