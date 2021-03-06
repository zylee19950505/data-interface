package com.xaeport.crossborder.data.xml;

import com.xaeport.crossborder.tools.xml.RootXPath;
import com.xaeport.crossborder.tools.xml.XPath;
import com.xaeport.crossborder.tools.xml.XmlEntry;

/**
 * @Author BaoZhe
 * @Date 2019-04-26
 * @Version 1.0
 */
@RootXPath("DataList")
public class DataBody implements XmlEntry {

    private Double priceSum;

    @XPath("gnum")
    private String gnum;
    @XPath("itemName")
    private String itemName;
    @XPath("itemNo")
    private String itemNo;
    @XPath("gmodel")
    private String gmodel;
    @XPath("qty")
    private String qty;
    @XPath("unit")
    private String unit;
    @XPath("totalPrice")
    private String totalPrice;
    @XPath("currency")
    private String currency;
    @XPath("country")
    private String country;

    @XPath("GdsSeqno")
    private String gdsSeqno;// “1”序号，由1递增的自然数
    @XPath("GdsMtno")
    private String gdsMtno;// 商品料号
    @XPath("Gdecd")
    private String gdecd;// 商品编码
    @XPath("GdsNm")
    private String gdsNm;// 商品名称
    @XPath("GdsSpcfModelDesc")
    private String gdsSpcfModelDesc;// 规格型号
    @XPath("DclQty")
    private String dclQty;// 数量
    @XPath("DclUnitcd")
    private String dclUnitcd;// 计量单位
    @XPath("LawfQty")
    private String lawfQty;// 法定数量
    @XPath("LawfUnitcd")
    private String lawfUnitcd;// 法定单位
    @XPath("SecdLawfQty")
    private String secdLawfQty;// 选填，第二法定数量，第二计量单位填写时必填
    @XPath("SecdLawfUnitcd")
    private String secdLawfUnitcd;// 选填，第二计量单位
    @XPath("DclTotalAmt")
    private String dclTotalAmt;// 商品总价
    @XPath("DclCurrcd")
    private String dclCurrcd;// 币制
    @XPath("Natcd")
    private String natcd;// 原产国代码
    @XPath("LvyrlfModecd")
    private String lvyrlfModecd;// 征免方式代码
    @XPath("Rmk")
    private String rmk;// 备注,选填项

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

    public Double getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(Double priceSum) {
        this.priceSum = priceSum;
    }

    public String getGnum() {
        return gnum;
    }

    public void setGnum(String gnum) {
        this.gnum = gnum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getGmodel() {
        return gmodel;
    }

    public void setGmodel(String gmodel) {
        this.gmodel = gmodel;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getGdsSeqno() {
        return gdsSeqno;
    }

    public void setGdsSeqno(String gdsSeqno) {
        this.gdsSeqno = gdsSeqno;
    }

    public String getGdsMtno() {
        return gdsMtno;
    }

    public void setGdsMtno(String gdsMtno) {
        this.gdsMtno = gdsMtno;
    }

    public String getGdecd() {
        return gdecd;
    }

    public void setGdecd(String gdecd) {
        this.gdecd = gdecd;
    }

    public String getGdsNm() {
        return gdsNm;
    }

    public void setGdsNm(String gdsNm) {
        this.gdsNm = gdsNm;
    }

    public String getGdsSpcfModelDesc() {
        return gdsSpcfModelDesc;
    }

    public void setGdsSpcfModelDesc(String gdsSpcfModelDesc) {
        this.gdsSpcfModelDesc = gdsSpcfModelDesc;
    }

    public String getDclQty() {
        return dclQty;
    }

    public void setDclQty(String dclQty) {
        this.dclQty = dclQty;
    }

    public String getDclUnitcd() {
        return dclUnitcd;
    }

    public void setDclUnitcd(String dclUnitcd) {
        this.dclUnitcd = dclUnitcd;
    }

    public String getLawfQty() {
        return lawfQty;
    }

    public void setLawfQty(String lawfQty) {
        this.lawfQty = lawfQty;
    }

    public String getLawfUnitcd() {
        return lawfUnitcd;
    }

    public void setLawfUnitcd(String lawfUnitcd) {
        this.lawfUnitcd = lawfUnitcd;
    }

    public String getSecdLawfQty() {
        return secdLawfQty;
    }

    public void setSecdLawfQty(String secdLawfQty) {
        this.secdLawfQty = secdLawfQty;
    }

    public String getSecdLawfUnitcd() {
        return secdLawfUnitcd;
    }

    public void setSecdLawfUnitcd(String secdLawfUnitcd) {
        this.secdLawfUnitcd = secdLawfUnitcd;
    }

    public String getDclTotalAmt() {
        return dclTotalAmt;
    }

    public void setDclTotalAmt(String dclTotalAmt) {
        this.dclTotalAmt = dclTotalAmt;
    }

    public String getDclCurrcd() {
        return dclCurrcd;
    }

    public void setDclCurrcd(String dclCurrcd) {
        this.dclCurrcd = dclCurrcd;
    }

    public String getNatcd() {
        return natcd;
    }

    public void setNatcd(String natcd) {
        this.natcd = natcd;
    }

    public String getLvyrlfModecd() {
        return lvyrlfModecd;
    }

    public void setLvyrlfModecd(String lvyrlfModecd) {
        this.lvyrlfModecd = lvyrlfModecd;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }
}
