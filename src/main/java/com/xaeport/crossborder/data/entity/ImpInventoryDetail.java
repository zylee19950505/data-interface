package com.xaeport.crossborder.data.entity;

import java.util.List;

public class ImpInventoryDetail {

    private ImpInventoryHead impInventoryHead;
    private List<ImpInventoryBody> impInventoryBodies;
    private Verify verify;

    public ImpInventoryHead getImpInventoryHead() {
        return impInventoryHead;
    }

    public void setImpInventoryHead(ImpInventoryHead impInventoryHead) {
        this.impInventoryHead = impInventoryHead;
    }

    public List<ImpInventoryBody> getImpInventoryBodies() {
        return impInventoryBodies;
    }

    public void setImpInventoryBodies(List<ImpInventoryBody> impInventoryBodies) {
        this.impInventoryBodies = impInventoryBodies;
    }

    public Verify getVerify() {
        return verify;
    }

    public void setVerify(Verify verify) {
        this.verify = verify;
    }
}
