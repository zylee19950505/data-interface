package com.xaeport.crossborder.verification.chains.impl.payment;

import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.FiledCheckTool;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;
import com.xaeport.crossborder.verification.tools.VerificationResultUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class paymentRegularVerifyChain implements CrossBorderVerifyChain {

    private static String numberStringReg = "[0-9A-za-z]*";
    private static Pattern numberStringPattern = Pattern.compile(numberStringReg);
    private static Matcher matcher;

    @Override
    public VerificationResult executeVerification(ImpCBHeadVer impCBHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

        // 支付人真实姓名
        validateField = impCBHeadVer.getPayer_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "支付人姓名不能为空,/,无", "payer_name")) {
            return verificationResult;
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
