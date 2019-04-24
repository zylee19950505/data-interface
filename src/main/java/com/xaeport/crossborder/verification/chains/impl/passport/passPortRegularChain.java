package com.xaeport.crossborder.verification.chains.impl.passport;

import com.xaeport.crossborder.verification.chains.BondVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.FiledCheckTool;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class passPortRegularChain implements BondVerifyChain {


    private static String numberStringReg = "[0-9A-za-z]*";
    private static Pattern numberStringPattern = Pattern.compile(numberStringReg);
    private static Matcher matcher;

    @Override
    public VerificationResult executeBondVerification(ImpBDHeadVer impBDHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

        // 经营企业名称
        validateField = impBDHeadVer.getAreain_etps_nm();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "区内企业名称错误：不能填写“空”，“无”，“/”字符", "areain_etps_nm")) {
            return verificationResult;
        }

        // 收货企业名称
        validateField = impBDHeadVer.getDcl_etps_nm();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "申报企业名称错误：不能填写“空”，“无”，“/”字符", "dcl_etps_nm")) {
            return verificationResult;
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
