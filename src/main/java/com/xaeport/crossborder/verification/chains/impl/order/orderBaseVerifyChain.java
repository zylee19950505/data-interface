//package com.xaeport.crossborder.verification.chains.impl.order;
//
//
//import com.xaeport.crossborder.data.LoadData;
//import com.xaeport.crossborder.tools.SpringUtils;
//import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
//import com.xaeport.crossborder.verification.entity.*;
//import com.xaeport.crossborder.verification.tools.VerificationResultUtil;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
///**
// * Created by baozhe on 2017-8-09.
// * B类-字段校验-链节点
// * 用于比较字段间数值一致性
// */
//public class orderBaseVerifyChain implements CrossBorderVerifyChain {
//
//    private LoadData loadData = SpringUtils.getBean(LoadData.class);
//
//    @Override
//    public VerificationResult executeVerification(ImpCBHeadVer entryHeadVers) {
//        VerificationResult verificationResult = new VerificationResult();
//
//        // 申报类型
//        String code = entryHeadVers.getEntry_type();
//
//        // 发件人国家
//        code = entryHeadVers.getSend_country();
//        if (!loadData.getCountryAreaMap().containsKey(code)) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 发件人国家不存在", "send_country");
//            return verificationResult;
//        }
//
//        // 校验表单内毛重小于净重  表头  表体
//        double grossWt = entryHeadVers.getGross_wt();
//        double netWt = entryHeadVers.getNet_wt();
//        if (grossWt < netWt) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 毛重不能小于净重", "gross_wt");
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
//        String gNo;// 商品序号
//        String codeTs;// 商品编码
//        String unit;// 申报计量单位
//        List<String> units;
//        // 表体校验
//        for (ImpCBBodyVer entryListVer : entryHeadVers.getImpInventoryBodyVerList()) {
//            gNo = String.valueOf(entryListVer.getG_no());
//
//            // 商品编码
//            codeTs = entryListVer.getCode_ts();
//            if(StringUtils.isEmpty(codeTs)){
//                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码不能为空", gNo), "code_ts", gNo);
//                return verificationResult;
//            }
//            if(!loadData.getPostalRateMap().containsKey(codeTs)){
//                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码不正确", gNo), "code_ts", gNo);
//                return verificationResult;
//            }
//
//        }
//
//        verificationResult.setErrorFlag(false);
//        return verificationResult;
//    }
//
//
//}
