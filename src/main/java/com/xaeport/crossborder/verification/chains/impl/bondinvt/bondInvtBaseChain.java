package com.xaeport.crossborder.verification.chains.impl.bondinvt;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.chains.BondVerifyChain;
import com.xaeport.crossborder.verification.entity.ImpBDBodyVer;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;
import com.xaeport.crossborder.verification.tools.VerificationResultUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class bondInvtBaseChain implements BondVerifyChain {

    private LoadData loadData = SpringUtils.getBean(LoadData.class);

    @Override
    public VerificationResult executeBondVerification(ImpBDHeadVer impBDHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

        // 申报类型
        String code = impBDHeadVer.getBusiness_type();
        if (!LoadData.entryTypeList.contains(code)) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 申报类型错误", "business_type");
            return verificationResult;
        }

        if (!CollectionUtils.isEmpty(impBDHeadVer.getImpBDBodyVerList())) {
            // 商品序号
            String gds_seqno;
            double dcl_qty;
            // 表体校验
            for (ImpBDBodyVer impBDBodyVer : impBDHeadVer.getImpBDBodyVerList()) {
                gds_seqno = String.valueOf(impBDBodyVer.getGds_seqno());

                // 申报计量单位
                validateField = impBDBodyVer.getDcl_unitcd();
                if (!loadData.getUnitCodeMap().containsKey(validateField)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]申报计量单位不存在", gds_seqno), "dcl_unitcd", gds_seqno);
                    return verificationResult;
                }

                // 申报数量
                validateField = impBDBodyVer.getDcl_qty();
                if (!StringUtils.isEmpty(validateField)) {
                    dcl_qty = Double.parseDouble(validateField);
                    if (dcl_qty <= 0) {
                        VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]数量需大于0", gds_seqno), "dcl_qty", gds_seqno);
                        return verificationResult;
                    }
                }

                // 最终目的国
                validateField = impBDBodyVer.getDestination_natcd();
                if (!loadData.getCountryAreaMap().containsKey(validateField)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]原产国（地区）不存在", gds_seqno), "destination_natcd", gds_seqno);
                    return verificationResult;
                }
            }
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }

}
