package com.xaeport.crossborder.data.entity;

import java.util.List;

public class CEB621Message {
    private List<InventoryHead> inventoryHeadList;
    private ImpInventoryHead impInventoryHead;
    private List<ImpInventoryBody> impInventoryBodyList;
    private BaseTransfer baseTransfer;
    private Signature signature;

    public List<InventoryHead> getInventoryHeadList() {
        return inventoryHeadList;
    }

    public void setInventoryHeadList(List<InventoryHead> inventoryHeadList) {
        this.inventoryHeadList = inventoryHeadList;
    }

    public ImpInventoryHead getImpInventoryHead() {
        return impInventoryHead;
    }

    public void setImpInventoryHead(ImpInventoryHead impInventoryHead) {
        this.impInventoryHead = impInventoryHead;
    }

    public List<ImpInventoryBody> getImpInventoryBodyList() {
        return impInventoryBodyList;
    }

    public void setImpInventoryBodyList(List<ImpInventoryBody> impInventoryBodyList) {
        this.impInventoryBodyList = impInventoryBodyList;
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
