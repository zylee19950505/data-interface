//package com.xaeport.crossborder.verification.chains.impl.logistics;
//
//
//
//import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
//import com.xaeport.crossborder.verification.chains.impl.FiledCheckTool;
//import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
//import com.xaeport.crossborder.verification.entity.VerificationResult;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by baozhe on 2017-8-09.
// * B类-正则校验-链节点
// * 用于验证字段符合某一正则表达式
// */
//public class logisticsRegularVerifyChain implements CrossBorderVerifyChain {
//
//    private static String numberStringReg = "[0-9A-za-z]*";
//    private static Pattern numberStringPattern = Pattern.compile(numberStringReg);
//    private static Matcher matcher;
//
//    @Override
//    public VerificationResult executeVerification(ImpCBHeadVer entryHeadVers) {
//        VerificationResult verificationResult = new VerificationResult();
//
//        // 货主单位名称
//        String validateField = entryHeadVers.getOwner_name();
//        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "货主单位名称填写错误", "owner_name")) {
//            return verificationResult;
//        }
//
//        // 收件人姓名
//        validateField = entryHeadVers.getReceive_name();
//        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收件人填写错误", "receive_name")) {
//            return verificationResult;
//        }
//
//        verificationResult.setErrorFlag(false);
//        return verificationResult;
//    }
//
//
//}
