package com.xaeport.crossborder.data.entity;

public class ImpPaymentDetail {

    private ImpPayment impPayment;
    private Verify verify;

    public ImpPayment getImpPayment() {
        return impPayment;
    }

    public void setImpPayment(ImpPayment impPayment) {
        this.impPayment = impPayment;
    }

    public Verify getVerify() {
        return verify;
    }

    public void setVerify(Verify verify) {
        this.verify = verify;
    }
}
