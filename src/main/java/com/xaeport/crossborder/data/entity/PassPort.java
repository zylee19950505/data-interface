package com.xaeport.crossborder.data.entity;

import java.util.List;

public class PassPort {

    private PassPortHead passPortHead;

    private List<PassPortAcmp> passPortAcmpList;

    public PassPortHead getPassPortHead() {
        return passPortHead;
    }

    public void setPassPortHead(PassPortHead passPortHead) {
        this.passPortHead = passPortHead;
    }

    public List<PassPortAcmp> getPassPortAcmpList() {
        return passPortAcmpList;
    }

    public void setPassPortAcmpList(List<PassPortAcmp> passPortAcmpList) {
        this.passPortAcmpList = passPortAcmpList;
    }
}
