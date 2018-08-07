package com.xaeport.crossborder.data.entity;

import java.util.List;

public class CEB513Message {

    private ImpLogisticsStatus impLogisticsStatus;
    private List<ImpLogisticsStatus> impLogisticsList;
    private BaseTransfer baseTransfer;//xml中BaseTransfer节点中的子节点
    private Signature411 signature411;//xml中固定的子节点
    private List<LogisticsStatus> LogisticsStatusList; //加入运单状态头部
    private List<LogisticsStatusHead> LogisticsStatusHeadList;//加入运单状态LogisticsStatusHead实体类。

    public ImpLogisticsStatus getImpLogisticsStatus() {
        return impLogisticsStatus;
    }

    public void setImpLogisticsStatus(ImpLogisticsStatus impLogisticsStatus) {
        this.impLogisticsStatus = impLogisticsStatus;
    }

    public List<ImpLogisticsStatus> getImpLogisticsList() {
        return impLogisticsList;
    }

    public void setImpLogisticsList(List<ImpLogisticsStatus> impLogisticsList) {
        this.impLogisticsList = impLogisticsList;
    }

    public BaseTransfer getBaseTransfer() {
        return baseTransfer;
    }

    public void setBaseTransfer(BaseTransfer baseTransfer) {
        this.baseTransfer = baseTransfer;
    }

    public Signature411 getSignature411() {
        return signature411;
    }

    public void setSignature411(Signature411 signature411) {
        this.signature411 = signature411;
    }

    public List<LogisticsStatus> getLogisticsStatusList() {
        return LogisticsStatusList;
    }

    public void setLogisticsStatusList(List<LogisticsStatus> logisticsStatusList) {
        LogisticsStatusList = logisticsStatusList;
    }

    public List<LogisticsStatusHead> getLogisticsStatusHeadList() {
        return LogisticsStatusHeadList;
    }

    public void setLogisticsStatusHeadList(List<LogisticsStatusHead> logisticsStatusHeadList) {
        LogisticsStatusHeadList = logisticsStatusHeadList;
    }
}
