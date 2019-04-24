package com.xaeport.crossborder.verification.entity;

import com.xaeport.crossborder.data.entity.VerifyBondHead;

import java.util.List;

public class ImpBDHeadVer extends VerifyBondHead{

    private List<ImpBDBodyVer> impBDBodyVerList;

    public List<ImpBDBodyVer> getImpBDBodyVerList() {
        return impBDBodyVerList;
    }

    public void setImpBDBodyVerList(List<ImpBDBodyVer> impBDBodyVerList) {
        this.impBDBodyVerList = impBDBodyVerList;
    }
}
