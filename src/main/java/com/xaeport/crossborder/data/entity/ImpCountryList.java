package com.xaeport.crossborder.data.entity;

public class ImpCountryList {

    private String countryCode;//国家代码
    private String countryName;//国家名称
    private double cargoValue;//货值
    private int detailedCount;//清单量


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public double getCargoValue() {
        return cargoValue;
    }

    public void setCargoValue(double cargoValue) {
        this.cargoValue = cargoValue;
    }

    public int getDetailedCount() {
        return detailedCount;
    }

    public void setDetailedCount(int detailedCount) {
        this.detailedCount = detailedCount;
    }
}
