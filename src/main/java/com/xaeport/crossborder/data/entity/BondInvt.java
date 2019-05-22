package com.xaeport.crossborder.data.entity;

import java.util.List;

public class BondInvt {

    private BondInvtBsc bondInvtBsc;
    private List<BondInvtDt> bondInvtDtList;
    private Verify verify;

    public Verify getVerify() {
        return verify;
    }

    public void setVerify(Verify verify) {
        this.verify = verify;
    }

    public BondInvtBsc getBondInvtBsc() {
        return bondInvtBsc;
    }

    public void setBondInvtBsc(BondInvtBsc bondInvtBsc) {
        this.bondInvtBsc = bondInvtBsc;
    }

    public List<BondInvtDt> getBondInvtDtList() {
        return bondInvtDtList;
    }

    public void setBondInvtDtList(List<BondInvtDt> bondInvtDtList) {
        this.bondInvtDtList = bondInvtDtList;
    }
}
