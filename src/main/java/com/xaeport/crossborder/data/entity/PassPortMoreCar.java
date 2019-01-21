package com.xaeport.crossborder.data.entity;

import java.util.List;
import java.util.Map;

public class PassPortMoreCar {

    private PassPortHead passPortHead;
    private List<BondInvtDt> bondInvtDtList;
    private Map<String,String> gds_mtno;
    private Map<String,String> gds_nm;

    public PassPortHead getPassPortHead() {
        return passPortHead;
    }

    public void setPassPortHead(PassPortHead passPortHead) {
        this.passPortHead = passPortHead;
    }

    public List<BondInvtDt> getBondInvtDtList() {
        return bondInvtDtList;
    }

    public void setBondInvtDtList(List<BondInvtDt> bondInvtDtList) {
        this.bondInvtDtList = bondInvtDtList;
    }

    public Map<String, String> getGds_mtno() {
        return gds_mtno;
    }

    public void setGds_mtno(Map<String, String> gds_mtno) {
        this.gds_mtno = gds_mtno;
    }

    public Map<String, String> getGds_nm() {
        return gds_nm;
    }

    public void setGds_nm(Map<String, String> gds_nm) {
        this.gds_nm = gds_nm;
    }
}
