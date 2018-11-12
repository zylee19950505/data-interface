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

        // 电商平台名称
        validateField = impCBHeadVer.getEbp_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "电商平台名称错误,不能填写“空”，“无”，“/”字符", "ebp_Name")) {
            return verificationResult;
        }

        // 电商企业名称
        validateField = impCBHeadVer.getEbc_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "电商企业名称错误,不能填写“空”，“无”，“/”字符", "ebc_Name")) {
            return verificationResult;
        }

        // 订购人注册号
        validateField = impCBHeadVer.getBuyer_reg_no();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "订购人注册号错误,不能填写“空”，“无”，“/”字符", "buyer_Reg_No")) {
            return verificationResult;
        }

        // 订购人姓名
        validateField = impCBHeadVer.getBuyer_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "订购人姓名错误,不能填写“空”，“无”，“/”字符", "buyer_Name")) {
            return verificationResult;
        }

        // 订购人电话
        validateField = impCBHeadVer.getBuyer_telephone();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "订购人电话错误,不能填写“空”，“无”，“/”字符", "buyer_TelePhone")) {
            return verificationResult;
        }

        // 收件人
        validateField = impCBHeadVer.getConsignee();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收件人错误,不能填写“空”，“无”，“/”字符", "consignee")) {
            return verificationResult;
        }

        // 收件人电话
        validateField = impCBHeadVer.getConsignee_telephone();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收件人电话错误,不能填写“空”，“无”，“/”字符", "consignee_Telephone")) {
            return verificationResult;
        }

        // 收件地址
        validateField = impCBHeadVer.getConsignee_address();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收件地址错误,不能填写“空”，“无”，“/”字符", "consignee_Address")) {
            return verificationResult;
        }

        // 订单编号
        validateField = impCBHeadVer.getOrder_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "订单编号错误,仅能填写半角英文，数字及符号", "order_No")) {
            return verificationResult;
        }

        // 电商平台代码
        validateField = impCBHeadVer.getEbp_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "电商平台代码错误,仅能填写半角英文，数字及符号", "ebp_Code")) {
            return verificationResult;
        }

        // 电商企业代码
        validateField = impCBHeadVer.getEbc_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "电商企业代码错误,仅能填写半角英文，数字及符号", "ebc_Code")) {
            return verificationResult;
        }

        // 提运单号
        validateField = impCBHeadVer.getBill_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "提运单号错误,仅能填写半角英文，数字及符号", "bill_No")) {
            return verificationResult;
        }

        // 订购人电话
        validateField = impCBHeadVer.getBuyer_telephone();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "订购人电话错误,仅能填写半角英文，数字及符号", "buyer_TelePhone")) {
            return verificationResult;
        }

        // 收件人电话
        validateField = impCBHeadVer.getConsignee_telephone();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "收件人电话错误,仅能填写半角英文，数字及符号", "consignee_Telephone")) {
            return verificationResult;
        }

        // 订购人身份证号
        validateField = impCBHeadVer.getBuyer_id_number();
        if (!FiledCheckTool.checkFiledIdCardRegx(verificationResult, validateField, "订购人身份证号错误，格式不符合规范", "buyer_Id_Number")) {
            return verificationResult;
        }

        String g_num;
        for (ImpCBBodyVer impCBBodyVer : impCBHeadVer.getImpCBBodyVerList()) {
            g_num = String.valueOf(impCBBodyVer.getG_num());
            // 商品名称
            validateField = impCBBodyVer.getItem_name();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "商品名称错误,不能填写“空”，“无”，“/”字符", "item_Name")) {
                return verificationResult;
            }

            //商品规格型号
            validateField = impCBBodyVer.getG_model();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "商品规格型号错误,不能填写“空”，“无”，“/”字符", "g_Model")) {
                return verificationResult;
            }

        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
