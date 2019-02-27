package com.xaeport.crossborder.data.entity;


//企业清单量

public class EnterpriseBillQuantity {

    private  String entCustomsCode;//海关十位
    private  String creditCode;//统一社会信用代码
    private  String entName;//企业名称
    private  String iInventoryValue;//进口清单量
    private  String eInventoryValue;//出口清单量
    private  String inventoryValue;//进出口清单量


    public String getEntCustomsCode() {
        return entCustomsCode;
    }

    public void setEntCustomsCode(String entCustomsCode) {
        this.entCustomsCode = entCustomsCode;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getiInventoryValue() {
        return iInventoryValue;
    }

    public void setiInventoryValue(String iInventoryValue) {
        this.iInventoryValue = iInventoryValue;
    }

    public String geteInventoryValue() {
        return eInventoryValue;
    }

    public void seteInventoryValue(String eInventoryValue) {
        this.eInventoryValue = eInventoryValue;
    }

    public String getInventoryValue() {
        return inventoryValue;
    }

    public void setInventoryValue(String inventoryValue) {
        this.inventoryValue = inventoryValue;
    }
}
