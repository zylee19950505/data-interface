package com.xaeport.crossborder.data.entity;

public class TaxListRd {

    private String head_guid;//表头GUID码
    private String g_num;//商品项号，从1开始连续序号
    private String g_code;//海关商品编号（10位）
    private String tax_price;//完税总价格
    private String customs_tax;//应征关税
    private String value_added_tax;//应征增值税
    private String consumption_tax;//应征消费税

    public String getHead_guid() {
        return head_guid;
    }

    public void setHead_guid(String head_guid) {
        this.head_guid = head_guid;
    }

    public String getG_num() {
        return g_num;
    }

    public void setG_num(String g_num) {
        this.g_num = g_num;
    }

    public String getG_code() {
        return g_code;
    }

    public void setG_code(String g_code) {
        this.g_code = g_code;
    }

    public String getTax_price() {
        return tax_price;
    }

    public void setTax_price(String tax_price) {
        this.tax_price = tax_price;
    }

    public String getCustoms_tax() {
        return customs_tax;
    }

    public void setCustoms_tax(String customs_tax) {
        this.customs_tax = customs_tax;
    }

    public String getValue_added_tax() {
        return value_added_tax;
    }

    public void setValue_added_tax(String value_added_tax) {
        this.value_added_tax = value_added_tax;
    }

    public String getConsumption_tax() {
        return consumption_tax;
    }

    public void setConsumption_tax(String consumption_tax) {
        this.consumption_tax = consumption_tax;
    }
}
