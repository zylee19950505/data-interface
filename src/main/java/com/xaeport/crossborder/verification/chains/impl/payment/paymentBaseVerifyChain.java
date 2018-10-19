//package com.xaeport.crossborder.verification.chains.impl.payment;
//
//import com.xaeport.crossborder.data.LoadData;
//import com.xaeport.crossborder.tools.SpringUtils;
//import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
//import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
//import com.xaeport.crossborder.verification.entity.VerificationResult;
//import com.xaeport.crossborder.verification.tools.VerificationResultUtil;
//
///**
// * Created by baozhe on 2017-8-09.
// * B类-字段校验-链节点
// * 用于比较字段间数值一致性
// */
//public class paymentBaseVerifyChain implements CrossBorderVerifyChain {
//
//    private LoadData loadData = SpringUtils.getBean(LoadData.class);
//    @Override
//    public VerificationResult executeVerification(ImpCBHeadVer entryHeadVers) {
//        VerificationResult verificationResult = new VerificationResult();
//
//        // 校验币制代码
//        code = entryHeadVers.getCurr_code();
//        if (!loadData.getCurrencyMap().containsKey(code)) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 币制不存在", "curr_code");
//            return verificationResult;
//        }
//
//        // 收发件人证件类型
//        code = entryHeadVers.getSend_id_type();
//        if (!loadData.getCertificateTypeMap().containsKey(code)) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 收发件人证件类型不存在", "send_id_type");
//            return verificationResult;
//        }
//
//        // 包装种类
//        code = entryHeadVers.getWrap_type();
//        if(!loadData.getPackTypeMap().containsKey(code)){
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 包装种类不存在", "wrap_type");
//            return verificationResult;
//        }
//
//        verificationResult.setErrorFlag(false);
//        return verificationResult;
//    }
//
//
//}
