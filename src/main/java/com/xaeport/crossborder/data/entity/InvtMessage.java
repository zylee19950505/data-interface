package com.xaeport.crossborder.data.entity;

import java.util.List;

public class InvtMessage {

    private InvtHeadType invtHeadType;
    private List<ExitInvtListType> exitInvtListTypeList;
    private List<InvtListType> invtListTypeList;
    private String operCusRegCode;
    private String sysId;

    public List<ExitInvtListType> getExitInvtListTypeList() {
        return exitInvtListTypeList;
    }

    public void setExitInvtListTypeList(List<ExitInvtListType> exitInvtListTypeList) {
        this.exitInvtListTypeList = exitInvtListTypeList;
    }

    public InvtHeadType getInvtHeadType() {
        return invtHeadType;
    }

    public void setInvtHeadType(InvtHeadType invtHeadType) {
        this.invtHeadType = invtHeadType;
    }

    public List<InvtListType> getInvtListTypeList() {
        return invtListTypeList;
    }

    public void setInvtListTypeList(List<InvtListType> invtListTypeList) {
        this.invtListTypeList = invtListTypeList;
    }

    public String getOperCusRegCode() {
        return operCusRegCode;
    }

    public void setOperCusRegCode(String operCusRegCode) {
        this.operCusRegCode = operCusRegCode;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
}
