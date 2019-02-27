package com.xaeport.crossborder.data.entity;

//进口商品总值排序
public class ImpGoodsOrder {

    private String hsCode;//hs编码
    private String goodsName;//商品名称
    private double cargoValue;//货值

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getCargoValue() {
        return cargoValue;
    }

    public void setCargoValue(double cargoValue) {
        this.cargoValue = cargoValue;
    }
}
