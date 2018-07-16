package com.xaeport.crossborder.data.entity;

/**
 * entrylist   分单（表体）
 * Created by zwj on 2017/07/18.
 */
public class EntryList {
    private String entrylist_id;
    private String entryhead_id;
    private String ass_bill_no;//分运单号码
    private Integer g_no;//商品序号
    private String optype;//操作类型
    private String code_ts;//商品编号（行邮税号）
    private String g_name;//商品名称（B类叫：物品名称）
    private String g_model;//商品规格、型号
    private String origin_country;//产销国
    private String origin_country_name;//产销国名称
    private String trade_curr;//成交币制
    //private String exchange_rate;
    private double trade_total;//成交总价
    private double decl_price;//申报单价
    private double decl_total;//申报总价
    private String use_to;//用途
    private String duty_mode;//征减免税方式
    private double g_qty;//申报数量
    private String g_unit;//申报计量单位
    private double qty_1;//第一（法定）数量
    private String unit_1;//第一(法定)计量单位
    private double qty_2;//第二数量
    private String unit_2;//第二计量单位
    private double g_grosswt;//商品毛重
    private double g_netwt;//商品净重
    private double tax_estimate;//税收估算
    private double real_duty;//实征关税额
    private double real_tax;//实征增值税额
    private double real_reg;//实征消费税额
    private double real_anti;//实征反倾销税额
    private double real_rsv1;//实征反补贴税款
    private double real_rsv2;//实征废旧基金
    private double real_ncad;//实征行邮税

    public String getEntrylist_id() {
        return entrylist_id;
    }

    public void setEntrylist_id(String entrylist_id) {
        this.entrylist_id = entrylist_id;
    }

    public String getEntryhead_id() {
        return entryhead_id;
    }

    public void setEntryhead_id(String entryhead_id) {
        this.entryhead_id = entryhead_id;
    }

    public String getAss_bill_no() {
        return ass_bill_no;
    }

    public void setAss_bill_no(String ass_bill_no) {
        this.ass_bill_no = ass_bill_no;
    }

    public Integer getG_no() {
        return g_no;
    }

    public void setG_no(Integer g_no) {
        this.g_no = g_no;
    }

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype;
    }

    public String getCode_ts() {
        return code_ts;
    }

    public void setCode_ts(String code_ts) {
        this.code_ts = code_ts;
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

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }

    public String getOrigin_country_name() {
        return origin_country_name;
    }

    public void setOrigin_country_name(String origin_country_name) {
        this.origin_country_name = origin_country_name;
    }

    public String getTrade_curr() {
        return trade_curr;
    }

    public void setTrade_curr(String trade_curr) {
        this.trade_curr = trade_curr;
    }

    public double getTrade_total() {
        return trade_total;
    }

    public void setTrade_total(double trade_total) {
        this.trade_total = trade_total;
    }

    public double getDecl_price() {
        return decl_price;
    }

    public void setDecl_price(double decl_price) {
        this.decl_price = decl_price;
    }

    public double getDecl_total() {
        return decl_total;
    }

    public void setDecl_total(double decl_total) {
        this.decl_total = decl_total;
    }

    public String getUse_to() {
        return use_to;
    }

    public void setUse_to(String use_to) {
        this.use_to = use_to;
    }

    public String getDuty_mode() {
        return duty_mode;
    }

    public void setDuty_mode(String duty_mode) {
        this.duty_mode = duty_mode;
    }

    public double getG_qty() {
        return g_qty;
    }

    public void setG_qty(double g_qty) {
        this.g_qty = g_qty;
    }

    public String getG_unit() {
        return g_unit;
    }

    public void setG_unit(String g_unit) {
        this.g_unit = g_unit;
    }

    public double getQty_1() {
        return qty_1;
    }

    public void setQty_1(double qty_1) {
        this.qty_1 = qty_1;
    }

    public String getUnit_1() {
        return unit_1;
    }

    public void setUnit_1(String unit_1) {
        this.unit_1 = unit_1;
    }

    public double getQty_2() {
        return qty_2;
    }

    public void setQty_2(double qty_2) {
        this.qty_2 = qty_2;
    }

    public String getUnit_2() {
        return unit_2;
    }

    public void setUnit_2(String unit_2) {
        this.unit_2 = unit_2;
    }

    public double getG_grosswt() {
        return g_grosswt;
    }

    public void setG_grosswt(double g_grosswt) {
        this.g_grosswt = g_grosswt;
    }

    public double getG_netwt() {
        return g_netwt;
    }

    public void setG_netwt(double g_netwt) {
        this.g_netwt = g_netwt;
    }

    public double getTax_estimate() {
        return tax_estimate;
    }

    public void setTax_estimate(double tax_estimate) {
        this.tax_estimate = tax_estimate;
    }

    public double getReal_duty() {
        return real_duty;
    }

    public void setReal_duty(double real_duty) {
        this.real_duty = real_duty;
    }

    public double getReal_tax() {
        return real_tax;
    }

    public void setReal_tax(double real_tax) {
        this.real_tax = real_tax;
    }

    public double getReal_reg() {
        return real_reg;
    }

    public void setReal_reg(double real_reg) {
        this.real_reg = real_reg;
    }

    public double getReal_anti() {
        return real_anti;
    }

    public void setReal_anti(double real_anti) {
        this.real_anti = real_anti;
    }

    public double getReal_rsv1() {
        return real_rsv1;
    }

    public void setReal_rsv1(double real_rsv1) {
        this.real_rsv1 = real_rsv1;
    }

    public double getReal_rsv2() {
        return real_rsv2;
    }

    public void setReal_rsv2(double real_rsv2) {
        this.real_rsv2 = real_rsv2;
    }

    public double getReal_ncad() {
        return real_ncad;
    }

    public void setReal_ncad(double real_ncad) {
        this.real_ncad = real_ncad;
    }
}
