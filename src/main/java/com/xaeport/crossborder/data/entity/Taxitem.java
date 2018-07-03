package com.xaeport.crossborder.data.entity;

import java.util.Date;

/**
 * Created by wx on 2018/4/4.
 */
public class Taxitem {
    private String id;
    private String mailno;
    private String goodsname;
    private double goodscount;
    private double goodsprice;
    private double totalprice;
    private double taxrate;
    private double tax;
    private int state;
    private String creatorid;
    private Date createtime;
    private String updatorid;
    private Date updatetime;
    private String receiptsno;
    private double weight ;
    private int taxstatus;
    private String remark;
    private String taxcode;
    private String goodstype;
    private String unit;
    private String declareid;
    private String brandname;
    private double goodstotal;
    private String issetrate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public double getGoodscount() {
        return goodscount;
    }

    public void setGoodscount(double goodscount) {
        this.goodscount = goodscount;
    }

    public double getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(double goodsprice) {
        this.goodsprice = goodsprice;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public double getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(double taxrate) {
        this.taxrate = taxrate;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdatorid() {
        return updatorid;
    }

    public void setUpdatorid(String updatorid) {
        this.updatorid = updatorid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getReceiptsno() {
        return receiptsno;
    }

    public void setReceiptsno(String receiptsno) {
        this.receiptsno = receiptsno;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getTaxstatus() {
        return taxstatus;
    }

    public void setTaxstatus(int taxstatus) {
        this.taxstatus = taxstatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaxcode() {
        return taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    public String getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDeclareid() {
        return declareid;
    }

    public void setDeclareid(String declareid) {
        this.declareid = declareid;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public double getGoodstotal() {
        return goodstotal;
    }

    public void setGoodstotal(double goodstotal) {
        this.goodstotal = goodstotal;
    }

    public String getIssetrate() {
        return issetrate;
    }

    public void setIssetrate(String issetrate) {
        this.issetrate = issetrate;
    }
}
