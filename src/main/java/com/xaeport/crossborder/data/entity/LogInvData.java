package com.xaeport.crossborder.data.entity;

import java.util.List;

public class LogInvData {

    private LogInvHead logInvHead;
    private List<LogInvList> logInvLists;

    public LogInvHead getLogInvHead() {
        return logInvHead;
    }

    public void setLogInvHead(LogInvHead logInvHead) {
        this.logInvHead = logInvHead;
    }

    public List<LogInvList> getLogInvLists() {
        return logInvLists;
    }

    public void setLogInvLists(List<LogInvList> logInvLists) {
        this.logInvLists = logInvLists;
    }
}
