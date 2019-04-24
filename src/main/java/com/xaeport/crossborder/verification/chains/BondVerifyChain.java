package com.xaeport.crossborder.verification.chains;

import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;

public interface BondVerifyChain {
    VerificationResult executeBondVerification(ImpBDHeadVer impBDHeadVer);
}
