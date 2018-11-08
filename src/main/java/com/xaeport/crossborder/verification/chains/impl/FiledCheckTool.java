package com.xaeport.crossborder.verification.chains.impl;

import com.xaeport.crossborder.verification.entity.VerificationResult;
import com.xaeport.crossborder.verification.tools.VerificationResultUtil;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FiledCheckTool {

    private static String meaninglessReg = "空|/|无";
    private static String phoneNumReg = "^[0-9\\-\\|]+$";//电话号码的正则表达式
    private static String letterReg = "[a-zA-Z]";//字母的正则表达式
    private static String numReg = "[0-9]+$";//数字的正则表达式
    private static String ChineseReg = "[\u4E00-\u9FA5]+";//中文正则表达式
    private static String halfAngleSymbolReg = "^[\\u0000-\\u00FF]+$";//半角符号正则表达式
    private static String idCardReg = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
//    private static String angleSymbolReg = "[\\uFF00-\\uFFFF]+$";//全角符号正则表达式

    private static Pattern meaninglessPattern = Pattern.compile(meaninglessReg);
    private static Pattern phoneNumPattern = Pattern.compile(phoneNumReg);
    private static Pattern letterPattern = Pattern.compile(letterReg);
    private static Pattern numPattern = Pattern.compile(numReg);
    private static Pattern ChinesePattern = Pattern.compile(ChineseReg);
    private static Pattern halfAngleSymbolPattern = Pattern.compile(halfAngleSymbolReg);
    private static Pattern idCardPattern = Pattern.compile(idCardReg);
//    private static Pattern angleSymbolPattern = Pattern.compile(angleSymbolReg);

    private static Matcher matcher;
    private static Matcher matcher2;

    //验证表头字段  不能为（空，/，无）
    public static boolean checkFiledByRegx(VerificationResult verificationResult, String validateField, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        matcher = meaninglessPattern.matcher(validateField.trim());
        boolean isMatcher = matcher.matches();
        if (isMatcher) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        return true;
    }

    //验证表头身份证号码格式
    public static boolean checkFiledIdCardRegx(VerificationResult verificationResult, String validateField, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        matcher = idCardPattern.matcher(validateField.trim());
        boolean isMatcher = matcher.matches();
        if (!isMatcher) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        return true;
    }

    //验证表头字段 贸易方式是否正确（9610或1210）
    public static boolean checkFiledPackByRegx(VerificationResult verificationResult, String validateField, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        if ((validateField.trim()).equals("9610") || (validateField.trim()).equals("1210")) {
            return true;
        }else {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
    }

    //验证表头字段  必须含有中文
    public static boolean checkFiledChineseRegx(VerificationResult verificationResult, String validateField, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        matcher = ChinesePattern.matcher(validateField.trim());
        boolean isMatcher = matcher.find();
        if (!isMatcher) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        return true;
    }

    //验证表头字段  仅限中文
    public static boolean checkFiledOnlyChineseRegx(VerificationResult verificationResult, String validateField, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        matcher = ChinesePattern.matcher(validateField.trim());
        boolean isMatcher = matcher.matches();
        if (!isMatcher) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        return true;
    }

    //验证表头字段  电话号码的格式（数字，|,-）
    public static boolean checkFiledByPhoneRegx(VerificationResult verificationResult, String validateField, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        matcher = phoneNumPattern.matcher(validateField.trim());
        boolean isMatcher = matcher.matches();
        if (!isMatcher) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        return true;
    }

    //验证表头字段  仅限英文及符号
    public static boolean checkFiledEngSymbolRegx(VerificationResult verificationResult, String validateField, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        matcher = halfAngleSymbolPattern.matcher(validateField.trim());
        matcher2 = numPattern.matcher(validateField.trim());
        boolean isMatcher_one = matcher.matches();
        boolean isMatcher_two = matcher2.find();

        if (isMatcher_one == true && isMatcher_two == false) {
            return true;
        } else {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
    }

    //验证表头字段  仅限英文，数字及符号（半角符号）
    public static boolean checkFiledEngSymbolNumRegx(VerificationResult verificationResult, String validateField, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        matcher = halfAngleSymbolPattern.matcher(validateField.trim());
        boolean isMatcher = matcher.matches();
        if (!isMatcher) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        return true;
    }

    //验证表头字段  必须含有英文，其余为半角符号
    public static boolean checkFiledContainEngRegx(VerificationResult verificationResult, String validateField, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
        matcher = halfAngleSymbolPattern.matcher(validateField.trim());
        matcher2 = letterPattern.matcher(validateField.trim());
        boolean isMatcher_one = matcher.matches();
        boolean isMatcher_two = matcher2.find();
        if (isMatcher_one == true && isMatcher_two == true) {
            return true;
        } else {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, String.format("表头: %s", errorMsg), field);
            return false;
        }
    }

    //验证表体字段  不能为（空，/，无）
    public static boolean checkFiledByRegx(VerificationResult verificationResult, String validateField, String gNo, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]%s", gNo, errorMsg), field, gNo);
            return false;
        }
        matcher = meaninglessPattern.matcher(validateField.trim());
        boolean isMatcher = matcher.matches();
        if (isMatcher) {
            VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]%s", gNo, errorMsg), field, gNo);
            return false;
        }
        return true;
    }

    //验证表体字段  必须含有中文字符
    public static boolean checkFiledListCineseByRegx(VerificationResult verificationResult, String validateField, String gNo, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]%s", gNo, errorMsg), field, gNo);
            return false;
        }
        matcher = ChinesePattern.matcher(validateField.trim());
        boolean isMatcher = matcher.find();
        if (!isMatcher) {
            VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]%s", gNo, errorMsg), field, gNo);
            return false;
        }
        return true;
    }

    //验证表体字段  仅限输入英文及符号
    public static boolean checkFiledListEngSymbolRegx(VerificationResult verificationResult, String validateField, String gNo, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]%s", gNo, errorMsg), field, gNo);
            return false;
        }
        matcher = halfAngleSymbolPattern.matcher(validateField.trim());
        matcher2 = numPattern.matcher(validateField.trim());
        boolean isMatcher_one = matcher.matches();
        boolean isMatcher_two = matcher2.find();
        if (isMatcher_one == true && isMatcher_two == false) {
            return true;
        } else {
            VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]%s", gNo, errorMsg), field, gNo);
            return false;
        }
    }

    //验证表体字段  仅限输入英文,数字及符号（半角符号）
    public static boolean checkFiledListEngSymbolNumRegx(VerificationResult verificationResult, String validateField, String gNo, String errorMsg, String field) {
        if (StringUtils.isEmpty(validateField) || StringUtils.isEmpty(validateField.trim())) {
            VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]%s", gNo, errorMsg), field, gNo);
            return false;
        }
        matcher = halfAngleSymbolPattern.matcher(validateField.trim());
        boolean isMatcher = matcher.matches();
        if (!isMatcher) {
            VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]%s", gNo, errorMsg), field, gNo);
            return false;
        }
        return true;
    }

}
