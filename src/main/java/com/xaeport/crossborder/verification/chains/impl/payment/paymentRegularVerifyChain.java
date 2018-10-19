//package com.xaeport.crossborder.verification.chains.impl.payment;
//
//import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
//import com.xaeport.crossborder.verification.chains.impl.FiledCheckTool;
//import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
//import com.xaeport.crossborder.verification.entity.VerificationResult;
//import com.xaeport.crossborder.verification.tools.VerificationResultUtil;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by baozhe on 2017-8-09.
// * B类-正则校验-链节点
// * 用于验证字段符合某一正则表达式
// */
//public class paymentRegularVerifyChain implements CrossBorderVerifyChain {
//
//    private static String numberStringReg = "[0-9A-za-z]*";
//    private static Pattern numberStringPattern = Pattern.compile(numberStringReg);
//    private static Matcher matcher;
//
//    @Override
//    public VerificationResult executeVerification(ImpCBHeadVer entryHeadVers) {
//        VerificationResult verificationResult = new VerificationResult();
//
//        // 是含有木质包装
//        validateField = entryHeadVers.getWood_wrap();
//        if (!FiledCheckTool.checkFiledPackByRegx(verificationResult, validateField, "是否含木质包装填写格式错误，只能选择是（1）或否（0）", "wood_wrap")) {
//            return verificationResult;
//        }
//
//        // 是否为旧物品
//        validateField = entryHeadVers.getGoods_userd();
//        if (!FiledCheckTool.checkFiledPackByRegx(verificationResult, validateField, "是否为旧物品填写格式错误，只能选择是（1）或否（0）", "goods_userd")) {
//            return verificationResult;
//        }
//
//        // 是否为低温运输
//        validateField = entryHeadVers.getLow_temp_trans();
//        if (!FiledCheckTool.checkFiledPackByRegx(verificationResult, validateField, "是否为低温运输填写格式错误，只能选择是（1）或否（0）", "low_temp_trans")) {
//            return verificationResult;
//        }
//
//        // 收发件人证件号
//        validateField = entryHeadVers.getSend_id();
//        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收发件人证件号填写错误", "send_id")) {
//            return verificationResult;
//        }
//
//        matcher = numberStringPattern.matcher(validateField.trim());
//        if (!matcher.matches()) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "收发件人证件号填写错误", "send_id");
//            return verificationResult;
//        }
//
//        // 收件人城市格式校验（必须含有中文）
//        validateField = entryHeadVers.getReceive_city();
//        if (!FiledCheckTool.checkFiledChineseRegx(verificationResult, validateField, "收件人城市格式错误，必须含有中文", "receive_city")) {
//            return verificationResult;
//        }
//
//        // 收件人电话格式校验
//        validateField = entryHeadVers.getReceive_tel_no();
//        if (!FiledCheckTool.checkFiledByPhoneRegx(verificationResult, validateField, "收件人电话号码格式错误，仅限阿拉伯数字，“-”和“|”", "receive_tel_no")) {
//            return verificationResult;
//        }
//
//
//        verificationResult.setErrorFlag(false);
//        return verificationResult;
//    }
//
//
//}
