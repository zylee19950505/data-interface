package com.xaeport.crossborder.data.entity;

import java.util.List;

public class LogInvCombineMessage {

    private EnvelopInfo envelopInfo;
    private List<LogInvData> logInvDataList;

    public EnvelopInfo getEnvelopInfo() {
        return envelopInfo;
    }

    public void setEnvelopInfo(EnvelopInfo envelopInfo) {
        this.envelopInfo = envelopInfo;
    }

    public List<LogInvData> getLogInvDataList() {
        return logInvDataList;
    }

    public void setLogInvDataList(List<LogInvData> logInvDataList) {
        this.logInvDataList = logInvDataList;
    }
}
