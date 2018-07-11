package com.xaeport.crossborder.data.entity;

public class ImpOrderBody {
    private int g_num;//从1开始的递增序号。
    private String head_guid;//出口电子订单表头系统唯一序号
    private String order_No;//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
    private String item_No;//电商企业自定义的商品货号（SKU）。
    private String item_Name;//交易平台销售商品的中文名称。
    private String item_Describe;//交易平台销售商品的描述信息。
    private String bar_Code;//商品条形码一般由前缀部分、制造厂商代码、商品代码和校验码组成。
    private String unit;//海关标准的参数代码海关标准的参数代码《JGS-20 海关业务代码集》- 计量单位代码
    private String qty;//商品实际数量
    private String price;//商品单价。赠品单价填写为 0。
    private String total_Price;//商品总价，等于单价乘以数量。
    private String currency;//限定为人民币，填写142。
    private String country;//填写海关标准的参数代码，参照《JGS-20 海关业务代码集》- 国家（地区）代码表。
    private String note;//促销活动，商品单价偏离市场价格的，可以在此说明。

    public int getG_num() {
        return g_num;
    }

    public void setG_num(int g_num) {
        this.g_num = g_num;
    }

    public String getHead_guid() {
        return head_guid;
    }

    public void setHead_guid(String head_guid) {
        this.head_guid = head_guid;
    }

    public String getOrder_No() {
        return order_No;
    }

    public void setOrder_No(String order_No) {
        this.order_No = order_No;
    }

    public String getItem_No() {
        return item_No;
    }

    public void setItem_No(String item_No) {
        this.item_No = item_No;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public String getItem_Describe() {
        return item_Describe;
    }

    public void setItem_Describe(String item_Describe) {
        this.item_Describe = item_Describe;
    }

    public String getBar_Code() {
        return bar_Code;
    }

    public void setBar_Code(String bar_Code) {
        this.bar_Code = bar_Code;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_Price() {
        return total_Price;
    }

    public void setTotal_Price(String total_Price) {
        this.total_Price = total_Price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
