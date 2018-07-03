package com.xaeport.crossborder.data.entity;

public class Goods {
    private String goodsName;
    private double quantiity;
    private String unit;
    private double price;
    private double total;
    private double parcelweight;
    private String goodsType;
    private String taxrate;
    private double tax;
    private String remark;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getQuantiity() {
        return quantiity;
    }

    public void setQuantiity(double quantiity) {
        this.quantiity = quantiity;
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

    public double getParcelweight() {
        return parcelweight;
    }

    public void setParcelweight(double parcelweight) {
        this.parcelweight = parcelweight;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(String taxrate) {
        this.taxrate = taxrate;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
