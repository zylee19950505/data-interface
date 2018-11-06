package com.xaeport.crossborder.verification.chains.impl.order;


import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
import com.xaeport.crossborder.verification.entity.*;
import com.xaeport.crossborder.verification.tools.VerificationResultUtil;
import org.springframework.util.StringUtils;

import java.util.List;

public class orderBaseVerifyChain implements CrossBorderVerifyChain {

    private LoadData loadData = SpringUtils.getBean(LoadData.class);

    @Override
    public VerificationResult executeVerification(ImpCBHeadVer impCBHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

        // 申报类型
        String code = impCBHeadVer.getBusiness_type();
        if (!LoadData.entryTypeList.contains(code)) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 申报类型错误", "business_type");
            return verificationResult;
        }

        String g_num;// 商品序号
        double qty;
        List<String> units;
        // 表体校验
        for (ImpCBBodyVer impCBBodyVer : impCBHeadVer.getImpCBBodyVerList()) {
            g_num = String.valueOf(impCBBodyVer.getG_num());

            // 申报计量单位
            validateField = impCBBodyVer.getUnit();
            if (!loadData.getUnitCodeMap().containsKey(validateField)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]申报计量单位不存在", g_num), "unit", g_num);
                return verificationResult;
            }

            // 生产国别
            code = impCBBodyVer.getCountry();
            if (!loadData.getCountryAreaMap().containsKey(code)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]原产国（地区）不存在", g_num), "country", g_num);
                return verificationResult;
            }

        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
