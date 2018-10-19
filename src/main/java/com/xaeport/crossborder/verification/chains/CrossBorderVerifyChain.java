package com.xaeport.crossborder.verification.chains;


import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;

public interface CrossBorderVerifyChain {

    VerificationResult executeVerification(ImpCBHeadVer impCBHeadVer);

}
