package com.xaeport.crossborder.verification.entity;

import com.xaeport.crossborder.data.entity.ImpCrossBorderHead;

import java.util.List;

//通用跨境业务表头数据结构
public class ImpCBHeadVer extends ImpCrossBorderHead{

    private List<ImpCBBodyVer> impCBBodyVerList;

    public List<ImpCBBodyVer> getImpCBBodyVerList() {
        return impCBBodyVerList;
    }

    public void setImpCBBodyVerList(List<ImpCBBodyVer> impCBBodyVerList) {
        this.impCBBodyVerList = impCBBodyVerList;
    }
}
