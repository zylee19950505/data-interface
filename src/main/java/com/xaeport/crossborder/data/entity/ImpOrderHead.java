package com.xaeport.crossborder.data.entity;

import java.util.Date;
//进口订单表体
public class ImpOrderHead {
    private String guid;//企业系统生成36 位唯一序号（英文字母大写）
    private String app_Type;//企业报送类型。1-新增2-变更3-删除。默认为1。
    private Date app_Time;//企业报送时间。格式:YYYYMMDDhhmmss。
    private String app_Status;//业务状态:1-暂存,2-申报,默认为2。
    private String order_Type;//电子订单类型：I进口
    private String order_No;//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
    private String ebp_Code;//电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。
    private String ebp_Name;//电商平台的海关注册登记名称；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台名称为准。
    private String ebc_Code;//电商企业的海关注册登记编号。
    private String ebc_Name;//电商企业的海关注册登记名称。
    private String goods_Value;//商品实际成交价，含非现金抵扣金额。
    private String freight;//不包含在商品价格中的运杂费，无则填写"0"。
    private String discount;//使用积分、虚拟货币、代金券等非现金支付金额，无则填写"0"。
    private String tax_Total;//企业预先代扣的税款金额，无则填写0
    private String actural_Paid;//商品价格+运杂费+代扣税款-非现金抵扣金额，与支付凭证的支付金额一致。
    private String currency;//限定为人民币，填写142。
    private String buyer_Reg_No;//订购人的交易平台注册号。
    private String buyer_Name;//订购人的真实姓名。
    private String buyer_Id_Type;//1-身份证,2-其它。限定为身份证，填写1。
    private String buyer_Id_Number;//订购人的身份证件号码。
    private String pay_Code;//支付企业的海关注册登记编号。
    private String payName;//支付企业在海关注册登记的企业名称。
    private String pay_Transaction_Id;//支付企业唯一的支付流水号。
    private String batch_Numbers;//商品批次号。
    private String consignee;//收货人姓名，必须与电子运单的收货人姓名一致。
    private String consignee_Telephone;//收货人联系电话，必须与电子运单的收货人电话一致
    private String consignee_Address;//"收货地址，必须与电子运单的收货地址一致。"
    private String consignee_Ditrict;//参照国家统计局公布的国家行政区划标准填制。
    private String note;//备注
    private String crt_id;//创建人ID
    private Date crt_tm;//创建时间
    private String upd_id;//更新人ID
    private Date upd_tm;//更新时间
    private String data_status;//数据状态
    private String return_status;//回执状态
    private String return_time;//回执时间
    private String return_info;//回执备注信息

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

    public String getApp_Type() {
        return app_Type;
    }

    public void setApp_Type(String app_Type) {
        this.app_Type = app_Type;
    }

    public Date getApp_Time() {
        return app_Time;
    }

    public void setApp_Time(Date app_Time) {
        this.app_Time = app_Time;
    }

    public String getApp_Status() {
        return app_Status;
    }

    public void setApp_Status(String app_Status) {
        this.app_Status = app_Status;
    }

    public String getOrder_Type() {
        return order_Type;
    }

    public void setOrder_Type(String order_Type) {
        this.order_Type = order_Type;
    }

    public String getOrder_No() {
        return order_No;
    }

    public void setOrder_No(String order_No) {
        this.order_No = order_No;
    }

    public String getEbp_Code() {
        return ebp_Code;
    }

    public void setEbp_Code(String ebp_Code) {
        this.ebp_Code = ebp_Code;
    }

    public String getEbp_Name() {
        return ebp_Name;
    }

    public void setEbp_Name(String ebp_Name) {
        this.ebp_Name = ebp_Name;
    }

    public String getEbc_Code() {
        return ebc_Code;
    }

    public void setEbc_Code(String ebc_Code) {
        this.ebc_Code = ebc_Code;
    }

    public String getEbc_Name() {
        return ebc_Name;
    }

    public void setEbc_Name(String ebc_Name) {
        this.ebc_Name = ebc_Name;
    }

    public String getGoods_Value() {
        return goods_Value;
    }

    public void setGoods_Value(String goods_Value) {
        this.goods_Value = goods_Value;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTax_Total() {
        return tax_Total;
    }

    public void setTax_Total(String tax_Total) {
        this.tax_Total = tax_Total;
    }

    public String getActural_Paid() {
        return actural_Paid;
    }

    public void setActural_Paid(String actural_Paid) {
        this.actural_Paid = actural_Paid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBuyer_Reg_No() {
        return buyer_Reg_No;
    }

    public void setBuyer_Reg_No(String buyer_Reg_No) {
        this.buyer_Reg_No = buyer_Reg_No;
    }

    public String getBuyer_Name() {
        return buyer_Name;
    }

    public void setBuyer_Name(String buyer_Name) {
        this.buyer_Name = buyer_Name;
    }

    public String getBuyer_Id_Type() {
        return buyer_Id_Type;
    }

    public void setBuyer_Id_Type(String buyer_Id_Type) {
        this.buyer_Id_Type = buyer_Id_Type;
    }

    public String getBuyer_Id_Number() {
        return buyer_Id_Number;
    }

    public void setBuyer_Id_Number(String buyer_Id_Number) {
        this.buyer_Id_Number = buyer_Id_Number;
    }

    public String getPay_Code() {
        return pay_Code;
    }

    public void setPay_Code(String pay_Code) {
        this.pay_Code = pay_Code;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPay_Transaction_Id() {
        return pay_Transaction_Id;
    }

    public void setPay_Transaction_Id(String pay_Transaction_Id) {
        this.pay_Transaction_Id = pay_Transaction_Id;
    }

    public String getBatch_Numbers() {
        return batch_Numbers;
    }

    public void setBatch_Numbers(String batch_Numbers) {
        this.batch_Numbers = batch_Numbers;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_Telephone() {
        return consignee_Telephone;
    }

    public void setConsignee_Telephone(String consignee_Telephone) {
        this.consignee_Telephone = consignee_Telephone;
    }

    public String getConsignee_Address() {
        return consignee_Address;
    }

    public void setConsignee_Address(String consignee_Address) {
        this.consignee_Address = consignee_Address;
    }

    public String getConsignee_Ditrict() {
        return consignee_Ditrict;
    }

    public void setConsignee_Ditrict(String consignee_Ditrict) {
        this.consignee_Ditrict = consignee_Ditrict;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getData_status() {
        return data_status;
    }

    public void setData_status(String data_status) {
        this.data_status = data_status;
    }
}
