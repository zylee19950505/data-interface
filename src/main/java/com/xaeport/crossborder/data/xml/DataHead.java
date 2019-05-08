package com.xaeport.crossborder.data.xml;

import com.xaeport.crossborder.tools.xml.RootXPath;
import com.xaeport.crossborder.tools.xml.XPath;
import com.xaeport.crossborder.tools.xml.XmlEntry;

/**
 * @Author BaoZhe
 * @Date 2019-04-26
 * @Version 1.0
 */
@RootXPath("Head")
public class DataHead implements XmlEntry {

    @XPath("orderid")
    private String orderid;
    @XPath("tradeMode")
    private String tradeMode;
    @XPath("orderNo")
    private String orderNo;
    @XPath("batchNumbers")
    private String batchNumbers;
    @XPath("ebpCode")
    private String ebpCode;
    @XPath("ebpName")
    private String ebpName;
    @XPath("portCode")
    private String portCode;
    @XPath("buyerRegNo")
    private String buyerRegNo;
    @XPath("buyerName")
    private String buyerName;
    @XPath("buyerIdNumber")
    private String buyerIdNumber;
    @XPath("buyerTelephone")
    private String buyerTelephone;
    @XPath("consignee")
    private String consignee;
    @XPath("consigneeTelephone")
    private String consigneeTelephone;
    @XPath("consigneeAddress")
    private String consigneeAddress;
    @XPath("insuredFee")
    private String insuredFee;
    @XPath("freight")
    private String freight;
    @XPath("discount")
    private String discount;
    @XPath("taxTotal")
    private String taxTotal;
    @XPath("grossWeight")
    private String grossWeight;
    @XPath("netWeight")
    private String netWeight;


    @XPath("EtpsInnerInvtNo")
    private String etpsInnerInvtNo;// 必填项，企业内部编码:HZQD+海关十位+进出口标志（I
    @XPath("PutrecNo")
    private String putrecNo;// 必填项，账册编号
    @XPath("DclPlcCuscd")
    private String dclPlcCuscd;// 必填项，主管海关
    @XPath("ImpexpPortcd")
    private String impexpPortcd;// 进出境关别
    @XPath("DclEtpsSccd")
    private String dclEtpsSccd;// 申报企业社会信用代码
    @XPath("DclEtpsno")
    private String dclEtpsno;// 申报企业海关十位
    @XPath("DclEtpsNm")
    private String dclEtpsNm;// 申报企业名称
    @XPath("DclcusTypecd")
    private String dclcusTypecd;// 2-对应报关
    @XPath("DecType")
    private String decType;// 报关单类型代码
    @XPath("ImpexpMarkcd")
    private String impexpMarkcd;// 入区为"I";出区为"E"
    @XPath("TrspModecd")
    private String trspModecd;// 运输方式代码
    @XPath("StshipTrsarvNatcd")
    private String stshipTrsarvNatcd;// 启运国代码
    @XPath("CorrEntryDclEtpsSccd")
    private String corrEntryDclEtpsSccd;// 对应报关单申报企业社会信用代码
    @XPath("CorrEntryDclEtpsNo")
    private String corrEntryDclEtpsNo;// 对应报关单申报企业海关十位
    @XPath("CorrEntryDclEtpsNm")
    private String corrEntryDclEtpsNm;// 对应报关单申报企业


    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTradeMode() {
        return tradeMode;
    }

    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBatchNumbers() {
        return batchNumbers;
    }

    public void setBatchNumbers(String batchNumbers) {
        this.batchNumbers = batchNumbers;
    }

    public String getEbpCode() {
        return ebpCode;
    }

    public void setEbpCode(String ebpCode) {
        this.ebpCode = ebpCode;
    }

    public String getEbpName() {
        return ebpName;
    }

    public void setEbpName(String ebpName) {
        this.ebpName = ebpName;
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getBuyerRegNo() {
        return buyerRegNo;
    }

    public void setBuyerRegNo(String buyerRegNo) {
        this.buyerRegNo = buyerRegNo;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerIdNumber() {
        return buyerIdNumber;
    }

    public void setBuyerIdNumber(String buyerIdNumber) {
        this.buyerIdNumber = buyerIdNumber;
    }

    public String getBuyerTelephone() {
        return buyerTelephone;
    }

    public void setBuyerTelephone(String buyerTelephone) {
        this.buyerTelephone = buyerTelephone;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeTelephone() {
        return consigneeTelephone;
    }

    public void setConsigneeTelephone(String consigneeTelephone) {
        this.consigneeTelephone = consigneeTelephone;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getInsuredFee() {
        return insuredFee;
    }

    public void setInsuredFee(String insuredFee) {
        this.insuredFee = insuredFee;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(String taxTotal) {
        this.taxTotal = taxTotal;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getEtpsInnerInvtNo() {
        return etpsInnerInvtNo;
    }

    public void setEtpsInnerInvtNo(String etpsInnerInvtNo) {
        this.etpsInnerInvtNo = etpsInnerInvtNo;
    }

    public String getPutrecNo() {
        return putrecNo;
    }

    public void setPutrecNo(String putrecNo) {
        this.putrecNo = putrecNo;
    }

    public String getDclPlcCuscd() {
        return dclPlcCuscd;
    }

    public void setDclPlcCuscd(String dclPlcCuscd) {
        this.dclPlcCuscd = dclPlcCuscd;
    }

    public String getImpexpPortcd() {
        return impexpPortcd;
    }

    public void setImpexpPortcd(String impexpPortcd) {
        this.impexpPortcd = impexpPortcd;
    }

    public String getDclEtpsSccd() {
        return dclEtpsSccd;
    }

    public void setDclEtpsSccd(String dclEtpsSccd) {
        this.dclEtpsSccd = dclEtpsSccd;
    }

    public String getDclEtpsno() {
        return dclEtpsno;
    }

    public void setDclEtpsno(String dclEtpsno) {
        this.dclEtpsno = dclEtpsno;
    }

    public String getDclEtpsNm() {
        return dclEtpsNm;
    }

    public void setDclEtpsNm(String dclEtpsNm) {
        this.dclEtpsNm = dclEtpsNm;
    }

    public String getDclcusTypecd() {
        return dclcusTypecd;
    }

    public void setDclcusTypecd(String dclcusTypecd) {
        this.dclcusTypecd = dclcusTypecd;
    }

    public String getDecType() {
        return decType;
    }

    public void setDecType(String decType) {
        this.decType = decType;
    }

    public String getImpexpMarkcd() {
        return impexpMarkcd;
    }

    public void setImpexpMarkcd(String impexpMarkcd) {
        this.impexpMarkcd = impexpMarkcd;
    }

    public String getTrspModecd() {
        return trspModecd;
    }

    public void setTrspModecd(String trspModecd) {
        this.trspModecd = trspModecd;
    }

    public String getStshipTrsarvNatcd() {
        return stshipTrsarvNatcd;
    }

    public void setStshipTrsarvNatcd(String stshipTrsarvNatcd) {
        this.stshipTrsarvNatcd = stshipTrsarvNatcd;
    }

    public String getCorrEntryDclEtpsSccd() {
        return corrEntryDclEtpsSccd;
    }

    public void setCorrEntryDclEtpsSccd(String corrEntryDclEtpsSccd) {
        this.corrEntryDclEtpsSccd = corrEntryDclEtpsSccd;
    }

    public String getCorrEntryDclEtpsNo() {
        return corrEntryDclEtpsNo;
    }

    public void setCorrEntryDclEtpsNo(String corrEntryDclEtpsNo) {
        this.corrEntryDclEtpsNo = corrEntryDclEtpsNo;
    }

    public String getCorrEntryDclEtpsNm() {
        return corrEntryDclEtpsNm;
    }

    public void setCorrEntryDclEtpsNm(String corrEntryDclEtpsNm) {
        this.corrEntryDclEtpsNm = corrEntryDclEtpsNm;
    }
}
