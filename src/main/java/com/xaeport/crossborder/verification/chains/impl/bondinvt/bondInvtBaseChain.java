package com.xaeport.crossborder.verification.chains.impl.bondinvt;

import com.xaeport.crossborder.configuration.SystemConstants;
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

        if (impBDHeadVer.getFlag().equals(SystemConstants.BSCQ)) {
            // 运抵国（地区）
            validateField = impBDHeadVer.getStship_trsarv_natcd();
            if (!validateField.equals("142")) {
                VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 出区只允许为142（中国）", "stship_trsarv_natcd");
                return verificationResult;
            }
        }


        if (!CollectionUtils.isEmpty(impBDHeadVer.getImpBDBodyVerList())) {
            String gds_seqno;// 商品序号
//            String gds_mtno;//商品料号
            String gdecd;//商品编码
            String dcl_currcd;//申报币制代码
            String lvyrlf_modecd;//征减免方式代码
            String natcd;//原产国(地区)

//            String dcl_unitcd;//申报计量单位
//            String dclUnitcd;
            double dcl_qty;//申报数量
            String lawf_unitcd;//法定计量单位
            String lawfUnitcd;
            double lawf_qty;//法定数量
            String secd_lawf_unitcd;//第二法定计量
            String secdLawfUnitcd;
            double secd_lawf_qty;//第二法定数量

            // 表体校验
            for (ImpBDBodyVer impBDBodyVer : impBDHeadVer.getImpBDBodyVerList()) {
                gds_seqno = String.valueOf(impBDBodyVer.getGds_seqno());

                // 商品编码
                gdecd = impBDBodyVer.getGdecd();
                if (StringUtils.isEmpty(gdecd)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码不能为空", gds_seqno), "gdecd", gds_seqno);
                    return verificationResult;
                }
                if (!loadData.getProductCodeMap().containsKey(gdecd)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]商品编码不正确", gds_seqno), "gdecd", gds_seqno);
                    return verificationResult;
                }

                // 原产国（地区）
                natcd = impBDBodyVer.getNatcd();
                if (!loadData.getCountryAreaMap().containsKey(natcd)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]原产国（地区）不存在", gds_seqno), "natcd", gds_seqno);
                    return verificationResult;
                }

                // 申报币制代码
                dcl_currcd = impBDBodyVer.getDcl_currcd();
                if (!loadData.getCurrencyMap().containsKey(dcl_currcd)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]申报币制代码不存在", gds_seqno), "dcl_currcd", gds_seqno);
                    return verificationResult;
                }

                // 征减免方式代码
                lvyrlf_modecd = impBDBodyVer.getLvyrlf_modecd();
                if (!loadData.getTaxReliefsModeMap().containsKey(lvyrlf_modecd)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]征减免方式代码不存在", gds_seqno), "lvyrlf_modecd", gds_seqno);
                    return verificationResult;
                }

                // 最终目的国
                validateField = impBDBodyVer.getDestination_natcd();
                if ("142" == validateField) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]最终目的国不存在", gds_seqno), "destination_natcd", gds_seqno);
                    return verificationResult;
                }

                // 申报计量单位
                validateField = impBDBodyVer.getDcl_unitcd();
                if (!loadData.getUnitCodeMap().containsKey(validateField)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]申报单位不存在", gds_seqno), "dcl_unitcd", gds_seqno);
                    return verificationResult;
                }

                // 申报数量
                validateField = impBDBodyVer.getDcl_qty();
                if (!StringUtils.isEmpty(validateField)) {
                    dcl_qty = Double.parseDouble(validateField);
                    if (dcl_qty <= 0) {
                        VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]申报数量需大于0", gds_seqno), "dcl_qty", gds_seqno);
                        return verificationResult;
                    }
                }

                lawf_unitcd = loadData.getProductCodeUnit1(gdecd);// 第一(法定)计量单位
                secd_lawf_unitcd = loadData.getProductCodeUnit2(gdecd);// 第二(法定)计量单位


                lawfUnitcd = impBDBodyVer.getLawf_unitcd();
                // 第一(法定)计量单位
                if (!loadData.getUnitCodeMap().containsKey(lawfUnitcd)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]法定计量单位错误", gds_seqno), "lawf_unitcd", gds_seqno);
                    return verificationResult;
                }
                if (!lawf_unitcd.equals(lawfUnitcd)) {
                    VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]法定计量单位错误", gds_seqno), "lawf_unitcd", gds_seqno);
                    return verificationResult;
                }
                // 第一(法定)数量
                if (!StringUtils.isEmpty(lawfUnitcd)) {
                    lawf_qty = Double.parseDouble(impBDBodyVer.getLawf_qty());
                    if (lawf_qty <= 0) {
                        VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]法定数量需大于0", gds_seqno), "lawf_qty", gds_seqno);
                        return verificationResult;
                    }
                }

                // 第二(法定)计量单位
                secdLawfUnitcd = impBDBodyVer.getSecd_lawf_unitcd();
                //  首先 第二计量单位可以为空  不校验
                //  其次 如果不为空 进行校验
                if (!StringUtils.isEmpty(secd_lawf_unitcd) && !StringUtils.isEmpty(secdLawfUnitcd)) {
                    if (!secd_lawf_unitcd.equals(secdLawfUnitcd)) {
                        VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第二法定单位错误", gds_seqno), "secd_lawf_unitcd", gds_seqno);
                        return verificationResult;
                    }
                } else {
                    if (!StringUtils.isEmpty(secdLawfUnitcd)) {
                        if (!secdLawfUnitcd.equals(secd_lawf_unitcd)) {
                            VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第二法定单位错误", gds_seqno), "secd_lawf_unitcd", gds_seqno);
                            return verificationResult;
                        }
                    }
                }

                if (!StringUtils.isEmpty(secdLawfUnitcd)) {
                    // 第二(法定)数量
                    secd_lawf_qty = Double.parseDouble(impBDBodyVer.getSecd_lawf_qty());
                    if (secd_lawf_qty <= 0) {
                        VerificationResultUtil.setEntryListErrorResult(verificationResult, String.format("表体: [商品序号：%s]第二法定数量不能为空", gds_seqno), "secd_lawf_qty", gds_seqno);
                        return verificationResult;
                    }
                }


            }
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }

}
