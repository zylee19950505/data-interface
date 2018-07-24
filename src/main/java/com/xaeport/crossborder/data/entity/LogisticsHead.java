package com.xaeport.crossborder.data.entity;

public class LogisticsHead {
    private String guid;//企业系统生成36 位唯一序号（英文字母大写）
    private String appType;//企业报送类型。1-新增2-变更3-删除。默认为1。
    private String appTime;//企业报送时间。格式:YYYYMMDDhhmmss。
    private String appStatus;//业务状态:1-暂存,2-申报,默认为2。
    private String logisticsCode;//物流企业的海关注册登记编号。
    private String logisticsName;//物流企业在海关注册登记的名称。
    private String logisticsNo;//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
    private String billNo;//直购进口为海运提单、空运总单或汽车载货清单
    private String freight;//商品运输费用，无则填0。
    private String insuredFee;//商品保险费用，无则填0。
    private String currency;//限定为人民币，填写142。
    private String weight;//单位为千克。
    private String packNo;//单个运单下包裹数，限定为1。
    private String goodsInfo;//配送的商品信息，包括商品名称、数量等。
    private String consignee;//收货人姓名。
    private String consigneeAddress;//收货地址。
    private String consigneeTelephone;//收货人电话号码。
    private String note;//备注


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getInsuredFee() {
        return insuredFee;
    }

    public void setInsuredFee(String insuredFee) {
        this.insuredFee = insuredFee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPackNo() {
        return packNo;
    }

    public void setPackNo(String packNo) {
        this.packNo = packNo;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeTelephone() {
        return consigneeTelephone;
    }

    public void setConsigneeTelephone(String consigneeTelephone) {
        this.consigneeTelephone = consigneeTelephone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
