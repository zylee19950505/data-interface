package com.xaeport.crossborder.data.entity;

import java.util.List;

public class ExitBondInvt {

    private BondInvtBsc bondInvtBsc;

    private List<NemsInvtCbecBillType> nemsInvtCbecBillTypeList;

    public BondInvtBsc getBondInvtBsc() {
        return bondInvtBsc;
    }

    public void setBondInvtBsc(BondInvtBsc bondInvtBsc) {
        this.bondInvtBsc = bondInvtBsc;
    }

    public List<NemsInvtCbecBillType> getNemsInvtCbecBillTypeList() {
        return nemsInvtCbecBillTypeList;
    }

    public void setNemsInvtCbecBillTypeList(List<NemsInvtCbecBillType> nemsInvtCbecBillTypeList) {
        this.nemsInvtCbecBillTypeList = nemsInvtCbecBillTypeList;
    }
}
