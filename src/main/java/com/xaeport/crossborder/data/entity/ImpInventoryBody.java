package com.xaeport.crossborder.data.entity;

//进口清单表体
public class ImpInventoryBody {
    private int g_num;//序号: 从1开始连续序号，与关联的电子订单表体序号一一对应。
    private String head_guid;//表头编号: 企业系统生成36 位唯一序号（英文字母大写）
    private String order_no;//清单编号: 海关接受申报生成的清单编号。
    private String item_record_no;//账册备案料号: 保税进口必填
    private String item_no;//企业商品货号: 电商企业自定义的商品货号（SKU）。
    private String item_name;//企业商品品名: 交易平台销售商品的中文名称。
    private String g_code;//商品编码: 按商品分类编码规则确定的进出口商品的商品编号，分为商品编号和附加编号，其中商品编号栏应填报《中华人民共和国进出口税则》8位税则号列，附加编号应填报商品编号，附加编号第9、10位。
    private String g_name;//商品名称: 商品名称应据实填报，与电子订单一致。
    private String g_model;//商品规格型号: 满足海关归类、审价以及监管的要求为准。包括：品牌、规格、型号等。
    private String bar_code;//条形码: 商品条形码一般由前缀部分、制造厂商代码、商品代码和校验码组成。
    private String country;//原产国（地区）: 填写海关标准的参数代码，参照《JGS-20 海关业务代码集》- 国家（地区）代码表。
    private String currency;//限定为人民币，填写142。
    private double quantity;//申报数量
    private String qty;//商品实际数量
    private String qty1;//按照商品编码规则对应的法定计量单位的实际数量填写。
    private String qty2;//第二法定数量
    private String unit;//海关标准的参数代码海关标准的参数代码《JGS-20 海关业务代码集》- 计量单位代码
    private String unit1;//海关标准的参数代码《JGS-20海关业务代码集》- 计量单位代码
    private String unit2;//海关标准的参数代码《JGS-20海关业务代码集》- 计量单位代码
    private String price;//商品单价。赠品单价填写为 0。
    private String total_price;//商品总价，等于单价乘以数量。
    private String note;//促销活动，商品单价偏离市场价格的，可以在此说明。
    private String customs_tax;//应征关税
    private String value_added_tax;//应征增值税
    private String consumption_tax;//应征消费税
    private String writing_mode;

    public String getWriting_mode() {
        return writing_mode;
    }

    public void setWriting_mode(String writing_mode) {
        this.writing_mode = writing_mode;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getItem_record_no() {
        return item_record_no;
    }

    public void setItem_record_no(String item_record_no) {
        this.item_record_no = item_record_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getG_code() {
        return g_code;
    }

    public void setG_code(String g_code) {
        this.g_code = g_code;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getG_model() {
        return g_model;
    }

    public void setG_model(String g_model) {
        this.g_model = g_model;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQty1() {
        return qty1;
    }

    public void setQty1(String qty1) {
        this.qty1 = qty1;
    }

    public String getQty2() {
        return qty2;
    }

    public void setQty2(String qty2) {
        this.qty2 = qty2;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
