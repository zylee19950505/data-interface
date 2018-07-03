package com.xaeport.crossborder.data.entity;

/**
 * Created by wx on 2018/4/4.
 */
public class MailDeclareGoods {
    private String id;
    private String mailDeclare;
    private String goodsName;
    private String goodsType;
    private String countryOrigin;
    private double quantity;
    private String unit;
    private double price;
    private double total;
    private String currency;
    private double rmbTotal;
    private String priceCertificate;
    private String proofMaterial;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMailDeclare() {
        return mailDeclare;
    }

    public void setMailDeclare(String mailDeclare) {
        this.mailDeclare = mailDeclare;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public void setCountryOrigin(String countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getRmbTotal() {
        return rmbTotal;
    }

    public void setRmbTotal(double rmbTotal) {
        this.rmbTotal = rmbTotal;
    }

    public String getPriceCertificate() {
        return priceCertificate;
    }

    public void setPriceCertificate(String priceCertificate) {
        this.priceCertificate = priceCertificate;
    }

    public String getProofMaterial() {
        return proofMaterial;
    }

    public void setProofMaterial(String proofMaterial) {
        this.proofMaterial = proofMaterial;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
