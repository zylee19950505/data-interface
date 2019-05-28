package com.xaeport.crossborder.verification.chains.impl.bondorder;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.chains.BondVerifyChain;
import com.xaeport.crossborder.verification.entity.ImpBDBodyVer;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;
import com.xaeport.crossborder.verification.tools.VerificationResultUtil;
import org.springframework.util.StringUtils;

import java.util.List;

public class bondOrderBaseChain implements BondVerifyChain{

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

        String g_num;// 商品序号
        double qty;
        List<String> units;
        // 表体校验
        for (ImpBDBodyVer impBDBodyVer : impBDHeadVer.getImpBDBodyVerList()) {
            g_num = String.valueOf(impBDBodyVer.getG_num());

            // 申报计量单位
            validateField = impBDBodyVer.getUnit();
            if (!loadData.getUnitCodeMap().containsKey(validateField)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]申报计量单位不存在", g_num), "unit", g_num);
                return verificationResult;
            }

            // 数量
            validateField = impBDBodyVer.getQty();
            if (!StringUtils.isEmpty(validateField)) {
                qty = Double.parseDouble(validateField);
                if (qty <= 0) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]数量需大于0", g_num), "qty", g_num);
                    return verificationResult;
                }
            }

            // 生产国别
            code = impBDBodyVer.getCountry();
            if (!loadData.getCountryAreaMap().containsKey(code)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]原产国（地区）不存在", g_num), "country", g_num);
                return verificationResult;
            }

            // 校验表单内毛重、净重大于0，并且净重小于毛重
            double gross_weight = Double.parseDouble(impBDHeadVer.getGross_weight());
            double net_weight = Double.parseDouble(impBDHeadVer.getNet_weight());
            if (net_weight <= 0) {
                VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 净重必须大于0", "net_weight");
                return verificationResult;
            }

            if (gross_weight <= 0) {
                VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 毛重必须大于0", "gross_weight");
                return verificationResult;
            }

            if (net_weight > gross_weight) {
                VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 净重不能大于毛重", "net_weight");
                return verificationResult;
            }
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
