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

        // 物流企业名称
        validateField = impCBHeadVer.getLogistics_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "物流企业名称填写错误,不能填写“空”，“无”，“/”字符", "logistics_name")) {
            return verificationResult;
        }

        // 收件人
        validateField = impCBHeadVer.getConsingee();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收件人错误,不能填写“空”，“无”，“/”字符", "consingee")) {
            return verificationResult;
        }

        // 收件地址
        validateField = impCBHeadVer.getConsignee_address();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收件地址错误,不能填写“空”，“无”，“/”字符", "consignee_address")) {
            return verificationResult;
        }

        // 物流企业代码
        validateField = impCBHeadVer.getLogistics_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "物流企业代码错误，仅能填写半角英文，数字及符号", "logistics_code")) {
            return verificationResult;
        }

        // 提运单号
        validateField = impCBHeadVer.getBill_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "提运单号错误,仅能填写半角英文，数字及符号", "bill_no")) {
            return verificationResult;
        }

        // 运单编号
        validateField = impCBHeadVer.getLogistics_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "物流运单编号错误，仅能填写半角英文，数字及符号", "logistics_no")) {
            return verificationResult;
        }

        // 订单编号
        validateField = impCBHeadVer.getOrder_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "订单编号错误,仅能填写半角英文，数字及符号", "order_no")) {
            return verificationResult;
        }

        // 收货人电话
        validateField = impCBHeadVer.getConsignee_telephone();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "收货人电话错误，仅能填写半角英文，数字及符号", "consignee_telephone")) {
            return verificationResult;
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
