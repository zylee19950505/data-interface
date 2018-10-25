package com.xaeport.crossborder.verification.chains.impl.inventory;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
import com.xaeport.crossborder.verification.entity.*;
import com.xaeport.crossborder.verification.tools.VerificationResultUtil;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by baozhe on 2017-8-09.
 * B类-字段校验-链节点
 * 用于比较字段间数值一致性
 */
public class inventoryBaseVerifyChain implements CrossBorderVerifyChain {

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

        // 发件人国家
        code = impCBHeadVer.getCountry();
        if (!loadData.getCountryAreaMap().containsKey(code)) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 起运国（地区）不存在", "country");
            return verificationResult;
        }

        // 校验表单内毛重小于净重
        double gross_weight = Double.parseDouble(impCBHeadVer.getGross_weight());
        double net_weight = Double.parseDouble(impCBHeadVer.getNet_weight());
        if (gross_weight < net_weight) {
            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 毛重不能小于净重", "gross_weight");
            return verificationResult;
        }

//        // 校验币制代码
//        code = impCBHeadVer.getCurrency();
//        if (!loadData.getCurrencyMap().containsKey(code)) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 币制不存在", "currency");
//            return verificationResult;
//        }
//
//        // 订购人证件类型
//        code = impCBHeadVer.getBuyer_id_type();
//        if (!loadData.getCertificateTypeMap().containsKey(code)) {
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 订购人证件类型不存在", "buyer_id_type");
//            return verificationResult;
//        }

//        // 包装种类
//        code = impCBHeadVer.getWrap_type();
//        if(!loadData.getPackTypeMap().containsKey(code)){
//            VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 包装种类代码不存在", "wrap_type");
//            return verificationResult;
//        }



        String g_num;// 商品序号
        String g_code;// 商品编码
        String unit1;// 第一法定计量单位
        String unit2;
        String g_code_unit1;// 第一法定计量单位
        String g_code_unit2;
        double qty;
        List<String> units;
        // 表体校验
        for (ImpCBBodyVer impCBBodyVer : impCBHeadVer.getImpCBBodyVerList()) {
            g_num = String.valueOf(impCBBodyVer.getG_num());

            // 商品编码
            g_code = impCBBodyVer.getG_code();
            if(StringUtils.isEmpty(g_code)){
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码不能为空", g_num), "g_code", g_num);
                return verificationResult;
            }
            if(!loadData.getProductCodeMap().containsKey(g_code)){
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码不正确", g_num), "g_code", g_num);
                return verificationResult;
            }

            // 申报计量单位
            validateField = impCBBodyVer.getUnit();
            if (!loadData.getUnitCodeMap().containsKey(validateField)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]申报计量单位不存在", g_num), "unit", g_num);
                return verificationResult;
            }

            g_code_unit1 = loadData.getProductCodeUnit1(g_code);// 第一(法定)计量单位
            g_code_unit2 = loadData.getProductCodeUnit2(g_code);// 第二(法定)计量单位

            /*
            * 申报计量单位 要么和第一计量单位一致，要么和第二计量单位一致
            * */
            if (StringUtils.isEmpty(g_code_unit2)) {
                if (!g_code_unit1.equals(validateField)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码与计量单位不匹配", g_num), "unit", g_num);
                    return verificationResult;
                }
            } else {
                if (!g_code_unit1.equals(validateField) && !g_code_unit2.equals(validateField)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码与计量单位不匹配", g_num), "unit", g_num);
                    return verificationResult;
                }
            }

            unit1 = impCBBodyVer.getUnit1();
            // 第一(法定)计量单位
            if (!loadData.getUnitCodeMap().containsKey(unit1)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第一(法定)计量单位错误", g_num), "unit1", g_num);
                return verificationResult;
            }
            if (!g_code_unit1.equals(unit1)) {
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第一(法定)计量单位错误", g_num), "unit1", g_num);
                return verificationResult;
            }
            // 第一(法定)数量
            if (!StringUtils.isEmpty(unit1)) {
                qty = Double.parseDouble(impCBBodyVer.getQty1());
                if (qty <= 0) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第一(法定)数量需大于0", g_num), "qty1", g_num);
                    return verificationResult;
                }
            }

            // 第二(法定)计量单位
            unit2 = impCBBodyVer.getUnit2();
            //  首先 第二计量单位可以为空  不校验
            //  其次 如果不为空 进行校验
            if (!StringUtils.isEmpty(g_code_unit2) && !StringUtils.isEmpty(unit2)) {
                if (!g_code_unit2.equals(unit2)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第二计量单位错误", g_num), "unit2", g_num);
                    return verificationResult;
                }
            } else {
                if (!StringUtils.isEmpty(unit2)) {
                    if (!unit2.equals(g_code_unit2)) {
                        VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第二计量单位错误", g_num), "unit2", g_num);
                        return verificationResult;
                    }
                }
            }
            if (!StringUtils.isEmpty(unit2)) {
                // 第二(法定)数量
                qty = Double.parseDouble(impCBBodyVer.getQty2());
                if (qty <= 0) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第二数量不能为空", g_num), "qty2", g_num);
                    return verificationResult;
                }
            }

            // 生产国别
            code = impCBBodyVer.getCountry();
            if(!loadData.getCountryAreaMap().containsKey(code)){
                VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]原产国（地区）不存在", g_num), "country", g_num);
                return verificationResult;
            }

        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }


}