package com.xaeport.crossborder.verification.chains.impl.inventory;

import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.FiledCheckTool;
import com.xaeport.crossborder.verification.entity.*;
import com.xaeport.crossborder.verification.tools.VerificationResultUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class inventoryRegularVerifyChain implements CrossBorderVerifyChain {

    private static String numberStringReg = "[0-9A-za-z]*";
    private static Pattern numberStringPattern = Pattern.compile(numberStringReg);
    private static Matcher matcher;

    @Override
    public VerificationResult executeVerification(ImpCBHeadVer impCBHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

        // 贸易方式
        validateField = impCBHeadVer.getTrade_mode();
        if (!FiledCheckTool.checkFiledPackByRegx(verificationResult, validateField, "贸易方式错误：只能填写9610或1210", "trade_mode")) {
            return verificationResult;
        }

        // 物流企业名称
        validateField = impCBHeadVer.getLogistics_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "物流企业名称错误：不能填写“空”，“无”，“/”字符", "logistics_name")) {
            return verificationResult;
        }

        // 电商平台名称
        validateField = impCBHeadVer.getEbp_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "电商平台名称错误：不能填写“空”，“无”，“/”字符", "ebp_name")) {
            return verificationResult;
        }

        // 电商企业名称
        validateField = impCBHeadVer.getEbc_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "电商企业名称错误：不能填写“空”，“无”，“/”字符", "ebc_name")) {
            return verificationResult;
        }

        // 订购人姓名
        validateField = impCBHeadVer.getBuyer_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "订购人姓名错误：不能填写“空”，“无”，“/”字符", "buyer_name")) {
            return verificationResult;
        }

        // 收件地址
        validateField = impCBHeadVer.getConsignee_address();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收件地址错误：不能填写“空”，“无”，“/”字符", "consignee_address")) {
            return verificationResult;
        }

        // 申报企业名称
        validateField = impCBHeadVer.getAgent_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "申报企业名称错误：不能填写“空”，“无”，“/”字符", "agent_name")) {
            return verificationResult;
        }

        // 订单编号
        validateField = impCBHeadVer.getOrder_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "订单编号错误：仅能填写半角英文，数字及符号", "order_no")) {
            return verificationResult;
        }

        // 运单编号
        validateField = impCBHeadVer.getLogistics_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "物流运单编号错误：仅能填写半角英文，数字及符号", "logistics_no")) {
            return verificationResult;
        }

        // 物流企业代码
        validateField = impCBHeadVer.getLogistics_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "物流企业代码错误：仅能填写半角英文，数字及符号", "logistics_code")) {
            return verificationResult;
        }

        // 电商平台代码
        validateField = impCBHeadVer.getEbp_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "电商平台代码错误：仅能填写半角英文，数字及符号", "ebp_code")) {
            return verificationResult;
        }

        // 电商企业代码
        validateField = impCBHeadVer.getEbc_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "电商企业代码错误：仅能填写半角英文，数字及符号", "ebc_code")) {
            return verificationResult;
        }

        // 担保企业编号
        validateField = impCBHeadVer.getAssure_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "担保企业编号错误：仅能填写半角英文，数字及符号", "assure_code")) {
            return verificationResult;
        }

        // 订购人身份证号
        validateField = impCBHeadVer.getBuyer_id_number();
        if (!FiledCheckTool.checkFiledIdCardRegx(verificationResult, validateField, "订购人身份证号错误：格式不符合规范", "buyer_id_number")) {
            return verificationResult;
        }

        // 订购人电话
        validateField = impCBHeadVer.getBuyer_telephone();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "订购人电话错误：仅能填写半角英文，数字及符号", "buyer_telephone")) {
            return verificationResult;
        }

        // 申报企业代码
        validateField = impCBHeadVer.getAgent_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "申报企业代码错误：仅能填写半角英文，数字及符号", "agent_code")) {
            return verificationResult;
        }

        // 航班航次号
        validateField = impCBHeadVer.getVoyage_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "航班航次号错误：仅能填写半角英文，数字及符号", "voyage_no")) {
            return verificationResult;
        }

        // 运输工具编号
        validateField = impCBHeadVer.getTraf_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "运输工具编号错误：仅能填写半角英文，数字及符号", "traf_no")) {
            return verificationResult;
        }

        // 提运单号
        validateField = impCBHeadVer.getBill_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "提运单号错误：仅能填写半角英文，数字及符号", "bill_no")) {
            return verificationResult;
        }


        String g_num;
        for (ImpCBBodyVer impCBBodyVer : impCBHeadVer.getImpCBBodyVerList()) {
            g_num = String.valueOf(impCBBodyVer.getG_num());

            // 商品名称
            validateField = impCBBodyVer.getG_name();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "商品名称错误：不能填写“空”，“无”，“/”字符", "g_name")) {
                return verificationResult;
            }

            //商品规格型号
            validateField = impCBBodyVer.getG_model();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "商品规格型号错误：不能填写“空”，“无”，“/”字符", "g_model")) {
                return verificationResult;
            }

        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
