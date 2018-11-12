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

        // 支付企业名称
        validateField = impCBHeadVer.getPay_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "支付企业名称错误，不能填写“空”，“无”，“/”字符", "pay_name")) {
            return verificationResult;
        }

        // 电商平台代码
        validateField = impCBHeadVer.getEbp_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "电商平台代码错误，不能填写“空”，“无”，“/”字符", "ebp_name")) {
            return verificationResult;
        }

        // 支付人真实姓名
        validateField = impCBHeadVer.getPayer_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "支付人姓名错误，不能填写“空”，“无”，“/”字符", "payer_name")) {
            return verificationResult;
        }

        // 订单编号
        validateField = impCBHeadVer.getOrder_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "订单编号错误,仅能填写半角英文，数字及符号", "order_no")) {
            return verificationResult;
        }

        // 支付企业代码
        validateField = impCBHeadVer.getPay_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "支付企业代码错误,仅能填写半角英文，数字及符号", "pay_code")) {
            return verificationResult;
        }

        // 支付交易编码
        validateField = impCBHeadVer.getPay_transaction_id();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "支付交易编码错误,仅能填写半角英文，数字及符号", "pay_transaction_id")) {
            return verificationResult;
        }

        // 电商平台代码
        validateField = impCBHeadVer.getEbp_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "电商平台代码错误,仅能填写半角英文，数字及符号", "ebp_code")) {
            return verificationResult;
        }

        // 支付人身份证号
        validateField = impCBHeadVer.getPayer_id_number();
        if (!FiledCheckTool.checkFiledIdCardRegx(verificationResult, validateField, "支付人身份证号错误，格式不符合规范", "payer_id_number")) {
            return verificationResult;
        }

        // 支付时间
        validateField = impCBHeadVer.getPay_time_char();
        if (!FiledCheckTool.checkFiledTimeRegx(verificationResult, validateField, "支付时间错误，格式不符合规范", "pay_time")) {
            return verificationResult;
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
