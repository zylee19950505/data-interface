package com.xaeport.crossborder.verification.chains.impl.bondorder;

import com.xaeport.crossborder.verification.chains.BondVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.FiledCheckTool;
import com.xaeport.crossborder.verification.entity.ImpBDBodyVer;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class bondOrderRegularChain implements BondVerifyChain {


    private static String numberStringReg = "[0-9A-za-z]*";
    private static Pattern numberStringPattern = Pattern.compile(numberStringReg);
    private static Matcher matcher;

    @Override
    public VerificationResult executeBondVerification(ImpBDHeadVer impBDHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

        // 商品批次号
        validateField = impBDHeadVer.getBill_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "商品批次号错误：仅能填写半角英文，数字及符号", "bill_No")) {
            return verificationResult;
        }

        // 订单编号
        validateField = impBDHeadVer.getOrder_no();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "订单编号错误：仅能填写半角英文，数字及符号", "order_No")) {
            return verificationResult;
        }

        // 电商平台代码
        validateField = impBDHeadVer.getEbp_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "电商平台代码错误：仅能填写半角英文，数字及符号", "ebp_Code")) {
            return verificationResult;
        }

        // 电商平台名称
        validateField = impBDHeadVer.getEbp_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "电商平台名称错误：不能填写“空”，“无”，“/”字符", "ebp_Name")) {
            return verificationResult;
        }

        // 电商企业代码
        validateField = impBDHeadVer.getEbc_code();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "电商企业代码错误：仅能填写半角英文，数字及符号", "ebc_Code")) {
            return verificationResult;
        }

        // 电商企业名称
        validateField = impBDHeadVer.getEbc_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "电商企业名称错误：不能填写“空”，“无”，“/”字符", "ebc_Name")) {
            return verificationResult;
        }

        // 订购人注册号
        validateField = impBDHeadVer.getBuyer_reg_no();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "订购人注册号错误：不能填写“空”，“无”，“/”字符", "buyer_Reg_No")) {
            return verificationResult;
        }

        // 订购人身份证号
        validateField = impBDHeadVer.getBuyer_id_number();
        if (!FiledCheckTool.checkFiledIdCardRegx(verificationResult, validateField, "订购人身份证号错误：格式不符合规范", "buyer_Id_Number")) {
            return verificationResult;
        }

        // 订购人姓名
        validateField = impBDHeadVer.getBuyer_name();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "订购人姓名错误：不能填写“空”，“无”，“/”字符", "buyer_Name")) {
            return verificationResult;
        }

        // 订购人电话
        validateField = impBDHeadVer.getBuyer_telephone();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "订购人电话错误：不能填写“空”，“无”，“/”字符", "buyer_TelePhone")) {
            return verificationResult;
        }

        // 收件人
        validateField = impBDHeadVer.getConsignee();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收件人错误：不能填写“空”，“无”，“/”字符", "consignee")) {
            return verificationResult;
        }

        // 收件人电话
        validateField = impBDHeadVer.getConsignee_telephone();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收件人电话错误：不能填写“空”，“无”，“/”字符", "consignee_Telephone")) {
            return verificationResult;
        }

        // 收件地址
        validateField = impBDHeadVer.getConsignee_address();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收件地址错误：不能填写“空”，“无”，“/”字符", "consignee_Address")) {
            return verificationResult;
        }

        // 订购人电话
        validateField = impBDHeadVer.getBuyer_telephone();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "订购人电话错误：仅能填写半角英文，数字及符号", "buyer_TelePhone")) {
            return verificationResult;
        }

        // 收件人电话
        validateField = impBDHeadVer.getConsignee_telephone();
        if (!FiledCheckTool.checkFiledEngSymbolNumRegx(verificationResult, validateField, "收件人电话错误：仅能填写半角英文，数字及符号", "consignee_Telephone")) {
            return verificationResult;
        }

        // 毛重
        validateField = impBDHeadVer.getGross_weight();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "毛重错误：此项为必填项", "gross_weight")) {
            return verificationResult;
        }

        // 净重
        validateField = impBDHeadVer.getNet_weight();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "净重错误：此项为必填项", "net_weight")) {
            return verificationResult;
        }

        String g_num;
        for (ImpBDBodyVer impBDBodyVer : impBDHeadVer.getImpBDBodyVerList()) {
            g_num = String.valueOf(impBDBodyVer.getG_num());

            //序号
            validateField = g_num;
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "序号错误：不能填写“空”，“无”，“/”字符", "g_num")) {
                return verificationResult;
            }

            // 商品名称
            validateField = impBDBodyVer.getItem_name();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "商品名称错误：不能填写“空”，“无”，“/”字符", "item_Name")) {
                return verificationResult;
            }

            if (impBDHeadVer.getTrade_mode() == "1210") {
                // 商品货号
                validateField = impBDBodyVer.getItem_no();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "商品货号错误：不能填写“空”，“无”，“/”字符", "item_No")) {
                    return verificationResult;
                }
            }

            //商品规格型号
            validateField = impBDBodyVer.getG_model();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "商品规格型号错误：不能填写“空”，“无”，“/”字符", "g_Model")) {
                return verificationResult;
            }

            //数量
            validateField = impBDBodyVer.getQty();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "数量错误：不能填写“空”，“无”，“/”字符", "qty")) {
                return verificationResult;
            }

            //计量单位
            validateField = impBDBodyVer.getUnit();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "计量单位错误：不能填写“空”，“无”，“/”字符", "unit")) {
                return verificationResult;
            }

            //币制
            validateField = impBDBodyVer.getCurrency();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "币制错误：不能填写“空”，“无”，“/”字符", "currency")) {
                return verificationResult;
            }

            //原产国
            validateField = impBDBodyVer.getCountry();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "原产国错误：不能填写“空”，“无”，“/”字符", "country")) {
                return verificationResult;
            }

            //商品单价
            validateField = impBDBodyVer.getPrice();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "商品单价错误：不能填写“空”，“无”，“/”字符", "price")) {
                return verificationResult;
            }

            //商品总价
            validateField = impBDBodyVer.getTotal_price();
            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, g_num, "商品总价错误：不能填写“空”，“无”，“/”字符", "total_Price")) {
                return verificationResult;
            }

        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }

}
