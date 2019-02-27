package com.xaeport.crossborder.data.entity;



//进口贸易额
public class ImpTradeVolumeList {

    private String statisticsDate;//年月  统计时间
    private double cargoValue;//货值
    private int detailedCount;//清单量

    public String getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(String statisticsDate) {
        this.statisticsDate = statisticsDate;
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
