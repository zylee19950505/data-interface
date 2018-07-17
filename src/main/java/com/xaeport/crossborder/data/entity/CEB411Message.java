package com.xaeport.crossborder.data.entity;

import java.util.List;

public class CEB411Message {

    private List<ImpPayment> impPaymentList;
    private BaseTransfer411 baseTransfer411;
    private List<Payment> paymentList;
    private Signature411 signature411;

    public List<ImpPayment> getImpPaymentList() {
        return impPaymentList;
    }

    public void setImpPaymentList(List<ImpPayment> impPaymentList) {
        this.impPaymentList = impPaymentList;
    }

    public BaseTransfer411 getBaseTransfer411() {
        return baseTransfer411;
    }

    public void setBaseTransfer411(BaseTransfer411 baseTransfer411) {
        this.baseTransfer411 = baseTransfer411;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public Signature411 getSignature411() {
        return signature411;
    }

    public void setSignature411(Signature411 signature411) {
        this.signature411 = signature411;
    }
}
