package com.xaeport.crossborder.data.entity;

import java.util.List;

public class PassPortMessage {

    private PassportHeadXml passportHeadXml;
    private List<PassportAcmpXml> passportAcmpXmlList;
    private List<PassPortListXml> passPortListXmlList;
    private String OperCusRegCode;


    public List<PassPortListXml> getPassPortListXmlList() {
        return passPortListXmlList;
    }

    public void setPassPortListXmlList(List<PassPortListXml> passPortListXmlList) {
        this.passPortListXmlList = passPortListXmlList;
    }

    public PassportHeadXml getPassportHeadXml() {
        return passportHeadXml;
    }

    public void setPassportHeadXml(PassportHeadXml passportHeadXml) {
        this.passportHeadXml = passportHeadXml;
    }

    public List<PassportAcmpXml> getPassportAcmpXmlList() {
        return passportAcmpXmlList;
    }

    public void setPassportAcmpXmlList(List<PassportAcmpXml> passportAcmpXmlList) {
        this.passportAcmpXmlList = passportAcmpXmlList;
    }

    public String getOperCusRegCode() {
        return OperCusRegCode;
    }

    public void setOperCusRegCode(String operCusRegCode) {
        OperCusRegCode = operCusRegCode;
    }
}
