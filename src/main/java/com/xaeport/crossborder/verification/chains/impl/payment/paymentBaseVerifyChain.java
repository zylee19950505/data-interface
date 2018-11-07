package com.xaeport.crossborder.verification.chains.impl.payment;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;
import com.xaeport.crossborder.verification.tools.VerificationResultUtil;

public class paymentBaseVerifyChain implements CrossBorderVerifyChain {

    private LoadData loadData = SpringUtils.getBean(LoadData.class);

    @Override
    public VerificationResult executeVerification(ImpCBHeadVer impCBHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

        // 申报类型
        String code = impCBHeadVer.getBusiness_type();
        if (!LoadData.entryTypeList.contains(code)) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 申报类型错误", "business_type");
            return verificationResult;
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
