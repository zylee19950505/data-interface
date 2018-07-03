package com.xaeport.crossborder.data.entity;

/**
 * Created by wx on 2018/4/4.
 */
public class Currency {
    private String id;
    private String currency;
    private String currencyCode;
    private String countryOrigin;
    private String countryAbbreviation;
    private int isDefaultOptions;
    private int mSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public void setCountryOrigin(String countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    public String getCountryAbbreviation() {
        return countryAbbreviation;
    }

    public void setCountryAbbreviation(String countryAbbreviation) {
        this.countryAbbreviation = countryAbbreviation;
    }

    public int getIsDefaultOptions() {
        return isDefaultOptions;
    }

    public void setIsDefaultOptions(int isDefaultOptions) {
        this.isDefaultOptions = isDefaultOptions;
    }

    public int getmSort() {
        return mSort;
    }

    public void setmSort(int mSort) {
        this.mSort = mSort;
    }
}
