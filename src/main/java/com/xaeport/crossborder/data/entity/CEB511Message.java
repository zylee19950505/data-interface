package com.xaeport.crossborder.data.entity;

import java.util.List;

public class CEB511Message {

    private ImpLogistics impLogistics;
    private List<ImpLogistics> impLogisticsList;
    private BaseTransfer411 baseTransfer411;//xml中BaseTransfer节点中的子节点
    private Signature411 signature411;//xml中固定的子节点
    private List<Logistics> LogisticsList; //加入运单头部
    private List<LogisticsHead> LogisticsHeadList;//加入运单LogisticsHead实体类。

    public ImpLogistics getImpLogistics() {
        return impLogistics;
    }

    public void setImpLogistics(ImpLogistics impLogistics) {
        this.impLogistics = impLogistics;
    }

    public List<ImpLogistics> getImpLogisticsList() {
        return impLogisticsList;
    }

    public void setImpLogisticsList(List<ImpLogistics> impLogisticsList) {
        this.impLogisticsList = impLogisticsList;
    }

    public BaseTransfer411 getBaseTransfer411() {
        return baseTransfer411;
    }

    public void setBaseTransfer411(BaseTransfer411 baseTransfer411) {
        this.baseTransfer411 = baseTransfer411;
    }


    public Signature411 getSignature411() {
        return signature411;
    }

    public void setSignature411(Signature411 signature411) {
        this.signature411 = signature411;
    }

    public List<Logistics> getLogisticsList() {
        return LogisticsList;
    }

    public void setLogisticsList(List<Logistics> logisticsList) {
        LogisticsList = logisticsList;
    }

    public List<LogisticsHead> getLogisticsHeadList() {
        return LogisticsHeadList;
    }

    public void setLogisticsHeadList(List<LogisticsHead> logisticsHeadList) {
        LogisticsHeadList = logisticsHeadList;
    }
}
