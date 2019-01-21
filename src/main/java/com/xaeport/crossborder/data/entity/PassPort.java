package com.xaeport.crossborder.data.entity;

import java.util.List;

public class PassPort {

    private PassPortHead passPortHead;

    private List<PassPortAcmp> passPortAcmpList;

    private List<PassPortList> passPortList;


    public List<PassPortList> getPassPortList() {
        return passPortList;
    }

    public void setPassPortList(List<PassPortList> passPortList) {
        this.passPortList = passPortList;
    }

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
