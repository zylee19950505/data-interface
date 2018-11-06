package com.xaeport.crossborder.verification.chains.impl.order;

import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.FiledCheckTool;
import com.xaeport.crossborder.verification.entity.ImpCBBodyVer;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class orderRegularVerifyChain implements CrossBorderVerifyChain {

    private static String numberStringReg = "[0-9A-za-z]*";
    private static Pattern numberStringPattern = Pattern.compile(numberStringReg);
    private static Matcher matcher;

    @Override
    public VerificationResult executeVerification(ImpCBHeadVer impCBHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

//        // 贸易方式
//        validateField = impCBHeadVer.getTrade_mode();
//        if (!FiledCheckTool.checkFiledPackByRegx(verificationResult, validateField, "贸易方式填写错误，只能填写9610或1210", "trade_mode")) {
//            return verificationResult;
//        }

        String g_num;
        for (ImpCBBodyVer impCBBodyVer : impCBHeadVer.getImpCBBodyVerList()) {
            g_num = String.valueOf(impCBBodyVer.getG_num());
            // 商品名称
            validateField = impCBBodyVer.getItem_name();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "商品名称错误", "item_Name")) {
                return verificationResult;
            }
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
