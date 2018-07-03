package com.xaeport.crossborder.data.entity;

/**
 * Created by wx on 2018/5/4.
 */
public class MonthStatistics {
    private String taxTotal;
    private String taxCount;
    private String taxList;
    private String postHandTax;
    private String treasury;

    public String getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(String taxTotal) {
        this.taxTotal = taxTotal;
    }

    public String getTaxCount() {
        return taxCount;
    }

    public void setTaxCount(String taxCount) {
        this.taxCount = taxCount;
    }

    public String getTaxList() {
        return taxList;
    }

    public void setTaxList(String taxList) {
        this.taxList = taxList;
    }

    public String getPostHandTax() {
        return postHandTax;
    }

    public void setPostHandTax(String postHandTax) {
        this.postHandTax = postHandTax;
    }

    public String getTreasury() {
        return treasury;
    }

    public void setTreasury(String treasury) {
        this.treasury = treasury;
    }
}
