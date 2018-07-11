package com.xaeport.crossborder.data.entity;

/**
 * Created by Administrator on 2017/10/24.
 */
public class ProduecCode {
    private String customsCode;
    private String productName;
    private String unit1;
    private String unit2;
    private String importDutiesPreference;
    private String importDutiesGeneral;
    private double addedTax;
    private String consumptionTax;
    private String regulatoryConditions;

    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public String getUnit2() {
        return unit2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public String getImportDutiesPreference() {
        return importDutiesPreference;
    }

    public void setImportDutiesPreference(String importDutiesPreference) {
        this.importDutiesPreference = importDutiesPreference;
    }

    public String getImportDutiesGeneral() {
        return importDutiesGeneral;
    }

    public void setImportDutiesGeneral(String importDutiesGeneral) {
        this.importDutiesGeneral = importDutiesGeneral;
    }

    public double getAddedTax() {
        return addedTax;
    }

    public void setAddedTax(double addedTax) {
        this.addedTax = addedTax;
    }

    public String getConsumptionTax() {
        return consumptionTax;
    }

    public void setConsumptionTax(String consumptionTax) {
        this.consumptionTax = consumptionTax;
    }

    public String getRegulatoryConditions() {
        return regulatoryConditions;
    }

    public void setRegulatoryConditions(String regulatoryConditions) {
        this.regulatoryConditions = regulatoryConditions;
    }
}
