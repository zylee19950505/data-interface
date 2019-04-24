package com.xaeport.crossborder.verification.chains.impl.bondinvt;

import com.xaeport.crossborder.verification.chains.BondVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.FiledCheckTool;
import com.xaeport.crossborder.verification.entity.ImpBDBodyVer;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;
import org.springframework.util.CollectionUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class bondInvtRegularChain implements BondVerifyChain {

    private static String numberStringReg = "[0-9A-za-z]*";
    private static Pattern numberStringPattern = Pattern.compile(numberStringReg);
    private static Matcher matcher;

    @Override
    public VerificationResult executeBondVerification(ImpBDHeadVer impBDHeadVer) {
        VerificationResult verificationResult = new VerificationResult();

        String validateField = null;

        // 经营企业名称
        validateField = impBDHeadVer.getBizop_etps_nm();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "经营企业名称错误：不能填写“空”，“无”，“/”字符", "bizop_etps_nm")) {
            return verificationResult;
        }

        // 收货企业名称
        validateField = impBDHeadVer.getRcvgd_etps_nm();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收货企业名称错误：不能填写“空”，“无”，“/”字符", "rcvgd_etps_nm")) {
            return verificationResult;
        }

        if (!CollectionUtils.isEmpty(impBDHeadVer.getImpBDBodyVerList())) {
            // 商品序号
            String gds_seqno;
            //表体校验
            for (ImpBDBodyVer impBDBodyVer : impBDHeadVer.getImpBDBodyVerList()) {
                gds_seqno = String.valueOf(impBDBodyVer.getGds_seqno());

                // 商品名称
                validateField = impBDBodyVer.getGds_nm();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "商品名称错误：不能填写“空”，“无”，“/”字符", "gds_nm")) {
                    return verificationResult;
                }

                //商品规格型号
                validateField = impBDBodyVer.getGds_spcf_model_desc();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "商品规格型号错误：不能填写“空”，“无”，“/”字符", "gds_spcf_model_desc")) {
                    return verificationResult;
                }
            }
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }

}
