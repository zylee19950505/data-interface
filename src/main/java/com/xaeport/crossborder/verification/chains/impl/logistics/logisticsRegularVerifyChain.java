package com.xaeport.crossborder.verification.chains.impl.logistics;

import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.FiledCheckTool;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class logisticsRegularVerifyChain implements CrossBorderVerifyChain {

    private static String numberStringReg = "[0-9A-za-z]*";
    private static Pattern numberStringPattern = Pattern.compile(numberStringReg);
    private static Matcher matcher;

    @Override
    public VerificationResult executeVerification(ImpCBHeadVer impCBHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

        // 收货人电话
        validateField = impCBHeadVer.getConsignee_telephone();
        if (!FiledCheckTool.checkFiledByPhoneRegx(verificationResult, validateField, "收货人电话格式不正确，必须为数字，-，|", "consignee_telephone")) {
            return verificationResult;
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
