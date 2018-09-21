package com.xaeport.crossborder.data.entity;

import java.util.List;

public class ManifestData {

    private ManifestHead manifestHead;
    private List<CheckGoodsInfo> checkGoodsInfoList;

    public ManifestHead getManifestHead() {
        return manifestHead;
    }

    public void setManifestHead(ManifestHead manifestHead) {
        this.manifestHead = manifestHead;
    }

    public List<CheckGoodsInfo> getCheckGoodsInfoList() {
        return checkGoodsInfoList;
    }

    public void setCheckGoodsInfoList(List<CheckGoodsInfo> checkGoodsInfoList) {
        this.checkGoodsInfoList = checkGoodsInfoList;
    }
}
