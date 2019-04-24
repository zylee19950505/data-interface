package com.xaeport.crossborder.verification.chains.impl.bondinven;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.chains.BondVerifyChain;
import com.xaeport.crossborder.verification.entity.ImpBDBodyVer;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;
import com.xaeport.crossborder.verification.tools.VerificationResultUtil;
import org.springframework.util.StringUtils;

public class bondinvenBaseChain implements BondVerifyChain {

    private LoadData loadData = SpringUtils.getBean(LoadData.class);

    @Override
    public VerificationResult executeBondVerification(ImpBDHeadVer impBDHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

        // 申报类型
        validateField = impBDHeadVer.getBusiness_type();
        if (!LoadData.entryTypeList.contains(validateField)) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 申报类型错误", "business_type");
            return verificationResult;
        }

        // 申报海关代码
        validateField = impBDHeadVer.getCustoms_code();
        if (!loadData.getCustomsMap().containsKey(validateField)) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 申报海关代码不存在", "customs_code");
            return verificationResult;
        }

        // 口岸海关代码
        validateField = impBDHeadVer.getPort_code();
        if (!loadData.getCustomsMap().containsKey(validateField)) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 口岸海关代码不存在", "port_code");
            return verificationResult;
        }

        // 运输方式
        validateField = impBDHeadVer.getTraf_mode();
        if (!loadData.getTrafModeMap().containsKey(validateField)) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 运输方式代码不存在", "traf_mode");
            return verificationResult;
        }

        // 包装种类
        validateField = impBDHeadVer.getWrap_type();
        if (!StringUtils.isEmpty(validateField)) {
            if (!loadData.getPackTypeMap().containsKey(validateField)) {
                VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 包装种类代码不存在", "wrap_type");
                return verificationResult;
            }
        }

        // 起运国（地区）
        validateField = impBDHeadVer.getCountry();
        if (!loadData.getCountryAreaMap().containsKey(validateField)) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 起运国（地区）不存在", "country");
            return verificationResult;
        }

        // 校验表单内毛重小于净重
        double gross_weight = Double.parseDouble(impBDHeadVer.getGross_weight());
        double net_weight = Double.parseDouble(impBDHeadVer.getNet_weight());
        if (net_weight > gross_weight) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 净重不能大于毛重", "net_weight");
            return verificationResult;
        }

//        // 校验币制代码
//        code = impBDHeadVer.getCurrency();
//        if (!loadData.getCurrencyMap().containsKey(code)) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 币制不存在", "currency");
//            return verificationResult;
//        }
//
//        // 订购人证件类型
//        code = impBDHeadVer.getBuyer_id_type();
//        if (!loadData.getCertificateTypeMap().containsKey(code)) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 订购人证件类型不存在", "buyer_id_type");
//            return verificationResult;
//        }

        String g_num;// 商品序号
        String g_code;// 商品编码
        String unit1;// 第一法定计量单位
        String unit2;
        String g_code_unit1;// 第一法定计量单位
        String g_code_unit2;
        double qty;
        // 表体校验
        for (ImpBDBodyVer impBDBodyVer : impBDHeadVer.getImpBDBodyVerList()) {
            g_num = String.valueOf(impBDBodyVer.getG_num());

            // 商品编码
            g_code = impBDBodyVer.getG_code();
            if (StringUtils.isEmpty(g_code)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码不能为空", g_num), "g_code", g_num);
                return verificationResult;
            }
            if (!loadData.getProductCodeMap().containsKey(g_code)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码不正确", g_num), "g_code", g_num);
                return verificationResult;
            }

            // 原产国（地区）
            validateField = impBDBodyVer.getCountry();
            if (!loadData.getCountryAreaMap().containsKey(validateField)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]原产国（地区）不存在", g_num), "country", g_num);
                return verificationResult;
            }

            // 数量
            validateField = impBDBodyVer.getQty();
            if (!StringUtils.isEmpty(validateField)) {
                qty = Double.parseDouble(validateField);
                if (qty <= 0) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]数量需大于0", g_num), "g_qty", g_num);
                    return verificationResult;
                }
            }

            // 申报计量单位
            validateField = impBDBodyVer.getUnit();
            if (!loadData.getUnitCodeMap().containsKey(validateField)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]申报计量单位不存在", g_num), "g_unit", g_num);
                return verificationResult;
            }

            g_code_unit1 = loadData.getProductCodeUnit1(g_code);// 第一(法定)计量单位
            g_code_unit2 = loadData.getProductCodeUnit2(g_code);// 第二(法定)计量单位

//            /*
//            * 申报计量单位 要么和第一计量单位一致，要么和第二计量单位一致
//            * */
//            if (StringUtils.isEmpty(g_code_unit2)) {
//                if (!g_code_unit1.equals(validateField)) {
//                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码与计量单位不匹配", g_num), "g_unit", g_num);
//                    return verificationResult;
//                }
//            } else {
//                if (!g_code_unit1.equals(validateField) && !g_code_unit2.equals(validateField)) {
//                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码与计量单位不匹配", g_num), "g_unit", g_num);
//                    return verificationResult;
//                }
//            }

            unit1 = impBDBodyVer.getUnit1();
            // 第一(法定)计量单位
            if (!loadData.getUnitCodeMap().containsKey(unit1)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第一(法定)计量单位错误", g_num), "unit_1", g_num);
                return verificationResult;
            }
            if (!g_code_unit1.equals(unit1)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第一(法定)计量单位错误", g_num), "unit_1", g_num);
                return verificationResult;
            }
            // 第一(法定)数量
            if (!StringUtils.isEmpty(unit1)) {
                qty = Double.parseDouble(impBDBodyVer.getQty1());
                if (qty <= 0) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第一(法定)数量需大于0", g_num), "qty_1", g_num);
                    return verificationResult;
                }
            }

            // 第二(法定)计量单位
            unit2 = impBDBodyVer.getUnit2();
            //  首先 第二计量单位可以为空  不校验
            //  其次 如果不为空 进行校验
            if (!StringUtils.isEmpty(g_code_unit2) && !StringUtils.isEmpty(unit2)) {
                if (!g_code_unit2.equals(unit2)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第二计量单位错误", g_num), "unit_2", g_num);
                    return verificationResult;
                }
            } else {
                if (!StringUtils.isEmpty(unit2)) {
                    if (!unit2.equals(g_code_unit2)) {
                        VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第二计量单位错误", g_num), "unit_2", g_num);
                        return verificationResult;
                    }
                }
            }
            if (!StringUtils.isEmpty(unit2)) {
                // 第二(法定)数量
                qty = Double.parseDouble(impBDBodyVer.getQty2());
                if (qty <= 0) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第二数量不能为空", g_num), "qty_2", g_num);
                    return verificationResult;
                }
            }

        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}
