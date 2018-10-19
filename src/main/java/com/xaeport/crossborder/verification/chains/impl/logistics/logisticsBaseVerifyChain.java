//package com.xaeport.crossborder.verification.chains.impl.logistics;
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
//public class logisticsBaseVerifyChain implements CrossBorderVerifyChain {
//
//    private LoadData loadData = SpringUtils.getBean(LoadData.class);
//    @Override
//    public VerificationResult executeVerification(ImpCBHeadVer entryHeadVers) {
//        VerificationResult verificationResult = new VerificationResult();
//
//        // 申报类型
//        String code = entryHeadVers.getEntry_type();
//        if (!LoadData.entryTypeList.contains(code)) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 申报类型错误", "entry_type");
//            return verificationResult;
//        }
//
//        // 校验币制代码
//        code = entryHeadVers.getCurr_code();
//        if (!loadData.getCurrencyMap().containsKey(code)) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 币制不存在", "curr_code");
//            return verificationResult;
//        }
//
//        verificationResult.setErrorFlag(false);
//        return verificationResult;
//    }
//
//
//}
