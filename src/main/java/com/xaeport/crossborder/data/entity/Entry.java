package com.xaeport.crossborder.data.entity;

import java.util.List;

/**
 * Created by xcp on 2017/7/27.
 */
public class Entry {
    public ImpOrderHead impOrderHead;
    public List<ImpOrderBody> bodyLists;
    public Verify verify;

    public ImpOrderHead getImpOrderHead() {
        return impOrderHead;
    }

    public void setImpOrderHead(ImpOrderHead impOrderHead) {
        this.impOrderHead = impOrderHead;
    }

    public List<ImpOrderBody> getBodyLists() {
        return bodyLists;
    }

    public void setBodyLists(List<ImpOrderBody> bodyLists) {
        this.bodyLists = bodyLists;
    }

    public Verify getVerify() {
        return verify;
    }

    public void setVerify(Verify verify) {
        this.verify = verify;
    }
}
