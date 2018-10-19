//package com.xaeport.crossborder.verification.chains.impl.order;
//
//
//
//import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
//import com.xaeport.crossborder.verification.chains.impl.FiledCheckTool;
//import com.xaeport.crossborder.verification.entity.ImpCBBodyVer;
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
//public class orderRegularVerifyChain implements CrossBorderVerifyChain {
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
//        // 收件人城市格式校验（必须含有中文）
//        validateField = entryHeadVers.getReceive_city();
//        if (!FiledCheckTool.checkFiledChineseRegx(verificationResult, validateField, "收件人城市格式错误，必须含有中文", "receive_city")) {
//            return verificationResult;
//        }
//
//        String gNo;
//        for (ImpCBBodyVer entryListVer : entryHeadVers.getEntryListVers()) {
//            gNo = String.valueOf(entryListVer.getG_no());
//            // 商品名称
//            validateField = entryListVer.getG_name();
//            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gNo, "商品名称错误", "g_name")) {
//                return verificationResult;
//            }
//            // 规格/型号
//            validateField = entryListVer.getG_model();
//            if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gNo, "规格/型号填写错误", "g_model")) {
//                return verificationResult;
//            }
//            // 英文商品名称
//            validateField = entryListVer.getG_name_en();
//            if (!FiledCheckTool.checkFiledListEngSymbolNumRegx(verificationResult, validateField, gNo, "英文商品名称格式错误，仅限英文，数字及符号", "g_name_en")) {
//                return verificationResult;
//            }
//        }
//
//        verificationResult.setErrorFlag(false);
//        return verificationResult;
//    }
//
//
//}
