package com.xaeport.crossborder.data.entity;

import java.util.List;

public class CEB711Message {
    private ImpDeliveryHead impDeliveryHead;
    private List<ImpDeliveryBody> impDeliveryBodyList;
    private BaseTransfer baseTransfer;
    private Signature signature;

    public ImpDeliveryHead getImpDeliveryHead() {
        return impDeliveryHead;
    }

    public void setImpDeliveryHead(ImpDeliveryHead impDeliveryHead) {
        this.impDeliveryHead = impDeliveryHead;
    }

    public List<ImpDeliveryBody> getImpDeliveryBodyList() {
        return impDeliveryBodyList;
    }

    public void setImpDeliveryBodyList(List<ImpDeliveryBody> impDeliveryBodyList) {
        this.impDeliveryBodyList = impDeliveryBodyList;
    }

    public BaseTransfer getBaseTransfer() {
        return baseTransfer;
    }

    public void setBaseTransfer(BaseTransfer baseTransfer) {
        this.baseTransfer = baseTransfer;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }
}
