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

        // 系统唯一序号
        validateField = impBDHeadVer.getEtps_inner_invt_no();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "企业内部编码错误：不能填写“空”，“无”，“/”字符", "etps_inner_invt_no")) {
            return verificationResult;
        }

        // 备案编号
        validateField = impBDHeadVer.getPutrec_no();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "备案编号错误：不能填写“空”，“无”，“/”字符", "putrec_no")) {
            return verificationResult;
        }

        // 主管海关代码
        validateField = impBDHeadVer.getDcl_plc_cuscd();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "主管海关代码错误：不能填写“空”，“无”，“/”字符", "dcl_plc_cuscd")) {
            return verificationResult;
        }

        // 进境关别代码
        validateField = impBDHeadVer.getImpexp_portcd();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "进境关别代码错误：不能填写“空”，“无”，“/”字符", "impexp_portcd")) {
            return verificationResult;
        }

        // 经营企业编号
        validateField = impBDHeadVer.getBizop_etpsno();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "经营企业编号错误：不能填写“空”，“无”，“/”字符", "bizop_etpsno")) {
            return verificationResult;
        }

        // 经营企业名称
        validateField = impBDHeadVer.getBizop_etps_nm();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "经营企业名称错误：不能填写“空”，“无”，“/”字符", "bizop_etps_nm")) {
            return verificationResult;
        }

        // 收货企业编号
        validateField = impBDHeadVer.getRcvgd_etpsno();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收货企业编号错误：不能填写“空”，“无”，“/”字符", "rcvgd_etpsno")) {
            return verificationResult;
        }

        // 收货企业名称
        validateField = impBDHeadVer.getRcvgd_etps_nm();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "收货企业名称错误：不能填写“空”，“无”，“/”字符", "rcvgd_etps_nm")) {
            return verificationResult;
        }

        // 申报企业编号
        validateField = impBDHeadVer.getDcl_etpsno();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "申报企业编号错误：不能填写“空”，“无”，“/”字符", "dcl_etpsno")) {
            return verificationResult;
        }

        // 申报企业名称
        validateField = impBDHeadVer.getDcl_etps_nm();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "申报企业名称错误：不能填写“空”，“无”，“/”字符", "dcl_etps_nm")) {
            return verificationResult;
        }

        // 进出口标记代码
        validateField = impBDHeadVer.getImpexp_markcd();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "进出口标记代码错误：不能填写“空”，“无”，“/”字符", "impexp_markcd")) {
            return verificationResult;
        }

        // 料件成品标记代码
        validateField = impBDHeadVer.getMtpck_endprd_markcd();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "料件成品标记代码错误：不能填写“空”，“无”，“/”字符", "mtpck_endprd_markcd")) {
            return verificationResult;
        }

        // 监管方式代码
        validateField = impBDHeadVer.getSupv_modecd();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "监管方式代码错误：不能填写“空”，“无”，“/”字符", "supv_modecd")) {
            return verificationResult;
        }

        // 运输方式代码
        validateField = impBDHeadVer.getTrsp_modecd();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "运输方式代码错误：不能填写“空”，“无”，“/”字符", "trsp_modecd")) {
            return verificationResult;
        }

        // 是否报关标志
        validateField = impBDHeadVer.getDclcus_flag();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "是否报关标志错误：不能填写“空”，“无”，“/”字符", "dclcus_flag")) {
            return verificationResult;
        }

        // 报关类型代码
        validateField = impBDHeadVer.getDclcus_typecd();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "报关类型代码错误：不能填写“空”，“无”，“/”字符", "dclcus_typecd")) {
            return verificationResult;
        }

        // 报关单类型
        validateField = impBDHeadVer.getDec_type();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "报关单类型错误：不能填写“空”，“无”，“/”字符", "dec_type")) {
            return verificationResult;
        }

        // 清单类型
        validateField = impBDHeadVer.getBond_invt_typecd();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "清单类型错误：不能填写“空”，“无”，“/”字符", "invt_type")) {
            return verificationResult;
        }

        // 起运/运抵国（地区）
        validateField = impBDHeadVer.getStship_trsarv_natcd();
        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "起运/运抵国（地区）错误：不能填写“空”，“无”，“/”字符", "stship_trsarv_natcd")) {
            return verificationResult;
        }

//        // 对应报关单申报单位社会信用代码
//        validateField = impBDHeadVer.getCorr_entry_dcl_etps_sccd();
//        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "对应报关单申报单位社会信用代码错误：不能填写“空”，“无”，“/”字符", "corr_entry_dcl_etps_sccd")) {
//            return verificationResult;
//        }
//
//        // 对应报关单申报单位编号
//        validateField = impBDHeadVer.getCorr_entry_dcl_etps_no();
//        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "对应报关单申报单位编号错误：不能填写“空”，“无”，“/”字符", "corr_entry_dcl_etps_no")) {
//            return verificationResult;
//        }
//
//        // 对应报关单申报单位名称
//        validateField = impBDHeadVer.getCorr_entry_dcl_etps_nm();
//        if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, "对应报关单申报单位名称错误：不能填写“空”，“无”，“/”字符", "corr_entry_dcl_etps_nm")) {
//            return verificationResult;
//        }

        if (!CollectionUtils.isEmpty(impBDHeadVer.getImpBDBodyVerList())) {
            // 商品序号
            String gds_seqno;
            //表体校验
            for (ImpBDBodyVer impBDBodyVer : impBDHeadVer.getImpBDBodyVerList()) {
                gds_seqno = String.valueOf(impBDBodyVer.getGds_seqno());

                // 商品序号
                validateField = gds_seqno;
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "商品序号错误：不能填写“空”，“无”，“/”字符", "gds_seqno")) {
                    return verificationResult;
                }

                //商品料号
                validateField = impBDBodyVer.getGds_mtno();
                if (!FiledCheckTool.checkFiledListEngSymbolNumRegx(verificationResult, validateField, gds_seqno, "商品料号错误：商品料号只能为英文、数字或特殊符号", "gds_mtno")) {
                    return verificationResult;
                }

                // 商品编码
                validateField = impBDBodyVer.getGdecd();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "商品编码错误：不能填写“空”，“无”，“/”字符", "gdecd")) {
                    return verificationResult;
                }

                // 商品名称
                validateField = impBDBodyVer.getGds_nm();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "商品名称错误：不能填写“空”，“无”，“/”字符", "gds_nm")) {
                    return verificationResult;
                }

                //规格型号
                validateField = impBDBodyVer.getGds_spcf_model_desc();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "规格型号错误：不能填写“空”，“无”，“/”字符", "gds_spcf_model_desc")) {
                    return verificationResult;
                }

                //申报数量
                validateField = impBDBodyVer.getDcl_qty();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "申报数量错误：不能填写“空”，“无”，“/”字符", "dcl_qty")) {
                    return verificationResult;
                }

                //申报单位
                validateField = impBDBodyVer.getDcl_unitcd();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "申报单位错误：不能填写“空”，“无”，“/”字符", "dcl_unitcd")) {
                    return verificationResult;
                }

                //法定数量
                validateField = impBDBodyVer.getLawf_qty();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "法定数量错误：不能填写“空”，“无”，“/”字符", "lawf_qty")) {
                    return verificationResult;
                }

                //法定单位
                validateField = impBDBodyVer.getLawf_unitcd();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "法定单位错误：不能填写“空”，“无”，“/”字符", "lawf_unitcd")) {
                    return verificationResult;
                }

                //总价
                validateField = impBDBodyVer.getDcl_total_amt();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "总价错误：不能填写“空”，“无”，“/”字符", "dcl_total_amt")) {
                    return verificationResult;
                }

                //币制
                validateField = impBDBodyVer.getDcl_currcd();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "币制错误：不能填写“空”，“无”，“/”字符", "dcl_currcd")) {
                    return verificationResult;
                }

                //原产国（地区）
                validateField = impBDBodyVer.getNatcd();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "原产国（地区）错误：不能填写“空”，“无”，“/”字符", "natcd")) {
                    return verificationResult;
                }

                //征免方式
                validateField = impBDBodyVer.getLvyrlf_modecd();
                if (!FiledCheckTool.checkFiledByRegx(verificationResult, validateField, gds_seqno, "征免方式错误：不能填写“空”，“无”，“/”字符", "lvyrlf_modecd")) {
                    return verificationResult;
                }

            }
        }

        verificationResult.setErrorFlag(false);
        return verificationResult;
    }

}
