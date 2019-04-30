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
public class DataHead  implements XmlEntry {

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
