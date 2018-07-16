package com.xaeport.crossborder.data.entity;


import java.util.List;

/**
 * 发送报文信息
 * Created by zwj on 2017/07/18.
 */
public class SignedData {
    private EnvelopInfo envelopInfo;
    private ImpOrderHead entryHead;
    private List<ImpOrderBody> entryList;
    private EntryDocu entryDocu;
    private String hashSign;
    private String SignerInfo;
    private ExpMftHead expMftHead;
    private List<ExpMftList> expMftLists;
    private String bzType;//业务类型


    public EnvelopInfo getEnvelopInfo() {
        return envelopInfo;
    }

    public void setEnvelopInfo(EnvelopInfo envelopInfo) {
        this.envelopInfo = envelopInfo;
    }

    public ImpOrderHead getEntryHead() {
        return entryHead;
    }

    public void setEntryHead(ImpOrderHead entryHead) {
        this.entryHead = entryHead;
    }

    public List<ImpOrderBody> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<ImpOrderBody> entryList) {
        this.entryList = entryList;
    }

    public EntryDocu getEntryDocu() {
        return entryDocu;
    }

    public void setEntryDocu(EntryDocu entryDocu) {
        this.entryDocu = entryDocu;
    }

    public String getHashSign() {
        return hashSign;
    }

    public void setHashSign(String hashSign) {
        this.hashSign = hashSign;
    }

    public String getSignerInfo() {
        return SignerInfo;
    }

    public void setSignerInfo(String signerInfo) {
        SignerInfo = signerInfo;
    }

    public ExpMftHead getExpMftHead() {
        return expMftHead;
    }

    public void setExpMftHead(ExpMftHead expMftHead) {
        this.expMftHead = expMftHead;
    }

    public List<ExpMftList> getExpMftLists() {
        return expMftLists;
    }

    public void setExpMftLists(List<ExpMftList> expMftLists) {
        this.expMftLists = expMftLists;
    }

    public String getBzType() {
        return bzType;
    }

    public void setBzType(String bzType) {
        this.bzType = bzType;
    }
}
