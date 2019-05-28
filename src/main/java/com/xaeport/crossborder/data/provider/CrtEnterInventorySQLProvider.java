package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.entity.Users;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.LinkedHashMap;
import java.util.Map;

public class CrtEnterInventorySQLProvider {

    //预先给数据表体里插入数据
    public String insertEnterInventoryDt(@Param("bondInvtDt") BondInvtDt bondInvtDt) throws Exception {
        return new SQL() {
            {
                INSERT_INTO("T_BOND_INVT_DT t");
                VALUES("id", "#{bondInvtDt.id}");
                if (!StringUtils.isEmpty(bondInvtDt.getHead_etps_inner_invt_no())) {
                    VALUES("head_etps_inner_invt_no", "#{bondInvtDt.head_etps_inner_invt_no}");
                }
                if (!StringUtils.isEmpty(String.valueOf(bondInvtDt.getGds_seqno()))) {
                    VALUES("gds_seqno", "#{bondInvtDt.gds_seqno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGds_mtno())) {
                    VALUES("gds_mtno", "#{bondInvtDt.gds_mtno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGdecd())) {
                    VALUES("gdecd", "#{bondInvtDt.gdecd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGds_nm())) {
                    VALUES("gds_nm", "#{bondInvtDt.gds_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGds_spcf_model_desc())) {
                    VALUES("gds_spcf_model_desc", "#{bondInvtDt.gds_spcf_model_desc}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_unitcd())) {
                    VALUES("dcl_unitcd", "#{bondInvtDt.dcl_unitcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getLawf_unitcd())) {
                    VALUES("lawf_unitcd", "#{bondInvtDt.lawf_unitcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getSecd_lawf_unitcd())) {
                    VALUES("secd_lawf_unitcd", "#{bondInvtDt.secd_lawf_unitcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getNatcd())) {
                    VALUES("natcd", "#{bondInvtDt.natcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGross_wt())) {
                    VALUES("GROSS_WT", "#{bondInvtDt.gross_wt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getNet_wt())) {
                    VALUES("NET_WT", "#{bondInvtDt.net_wt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_uprc_amt())) {
                    VALUES("DCL_UPRC_AMT", "#{bondInvtDt.dcl_uprc_amt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_total_amt())) {
                    VALUES("dcl_total_amt", "#{bondInvtDt.dcl_total_amt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getUsd_stat_total_amt())) {
                    VALUES("usd_stat_total_amt", "#{bondInvtDt.usd_stat_total_amt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getLawf_qty())) {
                    VALUES("lawf_qty", "#{bondInvtDt.lawf_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getSecd_lawf_qty())) {
                    VALUES("secd_lawf_qty", "#{bondInvtDt.secd_lawf_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_qty())) {
                    VALUES("dcl_qty", "#{bondInvtDt.dcl_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_qty())) {
                    VALUES("surplus_nm", "#{bondInvtDt.dcl_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getUsecd())) {
                    VALUES("usecd", "#{bondInvtDt.usecd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getEc_customs_code())) {
                    VALUES("EC_CUSTOMS_CODE", "#{bondInvtDt.ec_customs_code}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_currcd())) {
                    VALUES("DCL_CURRCD", "#{bondInvtDt.dcl_currcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDestination_natcd())) {
                    VALUES("DESTINATION_NATCD", "#{bondInvtDt.destination_natcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getModf_markcd())) {
                    VALUES("MODF_MARKCD", "#{bondInvtDt.modf_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getLvyrlf_modecd())) {
                    VALUES("lvyrlf_modecd", "#{bondInvtDt.lvyrlf_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getRmk())) {
                    VALUES("rmk", "#{bondInvtDt.rmk}");
                }
                if (!StringUtils.isEmpty(String.valueOf(bondInvtDt.getEntry_gds_seqno()))) {
                    VALUES("ENTRY_GDS_SEQNO", "#{bondInvtDt.entry_gds_seqno}");
                }
            }
        }.toString();
    }

    //预先给数据表头里插入数据
    public String insertEnterInventoryBsc(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc) throws Exception {
        return new SQL() {
            {
                INSERT_INTO("T_BOND_INVT_BSC t");
                VALUES("id", "#{bondInvtBsc.id}");
                if (!StringUtils.isEmpty(bondInvtBsc.getBusiness_type())) {
                    VALUES("BUSINESS_TYPE", "#{bondInvtBsc.business_type}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getEtps_inner_invt_no())) {
                    VALUES("etps_inner_invt_no", "#{bondInvtBsc.etps_inner_invt_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCrt_ent_id())) {
                    VALUES("crt_ent_id", "#{bondInvtBsc.crt_ent_id}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCrt_ent_name())) {
                    VALUES("crt_ent_name", "#{bondInvtBsc.crt_ent_name}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBizop_etpsno())) {
                    VALUES("bizop_etpsno", "#{bondInvtBsc.bizop_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBizop_etps_nm())) {
                    VALUES("bizop_etps_nm", "#{bondInvtBsc.bizop_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_etpsno())) {
                    VALUES("dcl_etpsno", "#{bondInvtBsc.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_etps_nm())) {
                    VALUES("dcl_etps_nm", "#{bondInvtBsc.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_plc_cuscd())) {
                    VALUES("dcl_plc_cuscd", "#{bondInvtBsc.dcl_plc_cuscd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRcvgd_etpsno())) {
                    VALUES("rcvgd_etpsno", "#{bondInvtBsc.rcvgd_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRcvgd_etps_nm())) {
                    VALUES("rcvgd_etps_nm", "#{bondInvtBsc.rcvgd_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCrt_user())) {
                    VALUES("crt_user", "#{bondInvtBsc.crt_user}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getOriginal_nm().toString())) {
                    VALUES("ORIGINAL_NM", "#{bondInvtBsc.original_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getUsable_nm().toString())) {
                    VALUES("USABLE_NM", "#{bondInvtBsc.usable_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBound_nm().toString())) {
                    VALUES("BOUND_NM", "#{bondInvtBsc.bound_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getPutrec_no())) {
                    VALUES("PUTREC_NO", "#{bondInvtBsc.putrec_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getEc_customs_code())) {
                    VALUES("EC_CUSTOMS_CODE", "#{bondInvtBsc.ec_customs_code}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getImpexp_markcd())) {
                    VALUES("IMPEXP_MARKCD", "#{bondInvtBsc.impexp_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getMtpck_endprd_markcd())) {
                    VALUES("MTPCK_ENDPRD_MARKCD", "#{bondInvtBsc.mtpck_endprd_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getSupv_modecd())) {
                    VALUES("SUPV_MODECD", "#{bondInvtBsc.supv_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDclcus_flag())) {
                    VALUES("DCLCUS_FLAG", "#{bondInvtBsc.dclcus_flag}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDclcus_typecd())) {
                    VALUES("DCLCUS_TYPECD", "#{bondInvtBsc.dclcus_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBond_invt_typecd())) {
                    VALUES("BOND_INVT_TYPECD", "#{bondInvtBsc.bond_invt_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_typecd())) {
                    VALUES("DCL_TYPECD", "#{bondInvtBsc.dcl_typecd}");
                }
                VALUES("crt_time", "sysdate");
            }
        }.toString();
    }

    //查询入区核注清单校验状态表数据
    public String queryLogicVerify(Map<String, String> paramMap) {
        final String etps_inner_invt_no = paramMap.get("etps_inner_invt_no");
        return new SQL() {
            {
                SELECT("t.CB_HEAD_ID");
                SELECT("t.STATUS");
                SELECT("t.RESULT");
                FROM("T_VERIFY_STATUS t");
                WHERE("t.ORDER_NO = #{etps_inner_invt_no}");
                WHERE("t.STATUS = 'N'");
            }
        }.toString();
    }

    //查询预存的表头数据
    public String queryEnterInventoryBsc(Map<String, String> paramMap) {
        final String etps_inner_invt_no = paramMap.get("etps_inner_invt_no");
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_BOND_INVT_BSC t");
                WHERE("t.etps_inner_invt_no = #{etps_inner_invt_no}");
            }
        }.toString();
    }

    //查询预存的表体数据
    public String queryEnterInventoryDt(Map<String, String> paramMap) throws Exception {
        final String etps_inner_invt_no = paramMap.get("etps_inner_invt_no");
        return new SQL() {
            {
                SELECT("t.GDS_SEQNO");
                SELECT("t.PUTREC_SEQNO");
                SELECT("t.GDS_MTNO");
                SELECT("t.GDECD");
                SELECT("t.GDS_NM");
                SELECT("t.GDS_SPCF_MODEL_DESC");
                SELECT("t.DCL_QTY");
                SELECT("t.DCL_UNITCD");
                SELECT("t.LAWF_QTY");
                SELECT("t.LAWF_UNITCD");
                SELECT("t.SECD_LAWF_QTY");
                SELECT("t.SECD_LAWF_UNITCD");
                SELECT("t.DCL_UPRC_AMT");
                SELECT("t.DCL_TOTAL_AMT");
                SELECT("t.DCL_CURRCD");
                SELECT("t.NATCD");
                SELECT("t.LVYRLF_MODECD");
                SELECT("t.USD_STAT_TOTAL_AMT");
                FROM("T_BOND_INVT_DT t");
                WHERE("t.head_etps_inner_invt_no = #{etps_inner_invt_no}");
            }
        }.toString();
    }

    //保存修改添加表头后的数据
    public String updateEnterInvtHead(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE("t.ETPS_INNER_INVT_NO = #{bondInvtBsc.etps_inner_invt_no}");
                SET("t.CHG_TMS_CNT = #{bondInvtBsc.chg_tms_cnt}");
                if (!StringUtils.isEmpty(bondInvtBsc.getUpd_user())) {
                    SET("t.UPD_USER = #{bondInvtBsc.upd_user}");
                    SET("t.UPD_TIME = sysdate");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCrt_ent_id())) {
                    SET("t.CRT_ENT_ID = #{bondInvtBsc.crt_ent_id}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCrt_ent_name())) {
                    SET("t.CRT_ENT_NAME = #{bondInvtBsc.crt_ent_name}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getStatus())) {
                    SET("t.STATUS = #{bondInvtBsc.status}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getFlag())) {
                    SET("t.FLAG = #{bondInvtBsc.flag}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBizop_etpsno())) {
                    SET("t.BIZOP_ETPSNO = #{bondInvtBsc.bizop_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBizop_etps_nm())) {
                    SET("t.BIZOP_ETPS_NM = #{bondInvtBsc.bizop_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_etpsno())) {
                    SET("t.DCL_ETPSNO = #{bondInvtBsc.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_etps_nm())) {
                    SET("t.DCL_ETPS_NM = #{bondInvtBsc.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getPutrec_no())) {
                    SET("t.PUTREC_NO = #{bondInvtBsc.putrec_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRcvgd_etpsno())) {
                    SET("t.RCVGD_ETPSNO = #{bondInvtBsc.rcvgd_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRcvgd_etps_nm())) {
                    SET("t.RCVGD_ETPS_NM = #{bondInvtBsc.rcvgd_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getImpexp_portcd())) {
                    SET("t.IMPEXP_PORTCD = #{bondInvtBsc.impexp_portcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_plc_cuscd())) {
                    SET("t.DCL_PLC_CUSCD = #{bondInvtBsc.dcl_plc_cuscd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getImpexp_markcd())) {
                    SET("t.IMPEXP_MARKCD = #{bondInvtBsc.impexp_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getMtpck_endprd_markcd())) {
                    SET("t.MTPCK_ENDPRD_MARKCD = #{bondInvtBsc.mtpck_endprd_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getSupv_modecd())) {
                    SET("t.SUPV_MODECD = #{bondInvtBsc.supv_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getTrsp_modecd())) {
                    SET("t.TRSP_MODECD = #{bondInvtBsc.trsp_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDclcus_flag())) {
                    SET("t.DCLCUS_FLAG = #{bondInvtBsc.dclcus_flag}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBond_invt_typecd())) {
                    SET("t.BOND_INVT_TYPECD = #{bondInvtBsc.bond_invt_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDclcus_typecd())) {
                    SET("t.DCLCUS_TYPECD = #{bondInvtBsc.dclcus_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_typecd())) {
                    SET("t.DCL_TYPECD = #{bondInvtBsc.dcl_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getStship_trsarv_natcd())) {
                    SET("t.STSHIP_TRSARV_NATCD = #{bondInvtBsc.stship_trsarv_natcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCorr_entry_dcl_etps_sccd())) {
                    SET("t.CORR_ENTRY_DCL_ETPS_SCCD = #{bondInvtBsc.corr_entry_dcl_etps_sccd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCorr_entry_dcl_etps_no())) {
                    SET("t.CORR_ENTRY_DCL_ETPS_NO = #{bondInvtBsc.corr_entry_dcl_etps_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCorr_entry_dcl_etps_nm())) {
                    SET("t.CORR_ENTRY_DCL_ETPS_NM = #{bondInvtBsc.corr_entry_dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDec_type())) {
                    SET("t.DEC_TYPE = #{bondInvtBsc.dec_type}");
                }
            }
        }.toString();
    }

    public String deleteEnterInvenBsc(@Param("etps_inner_invt_no") String etps_inner_invt_no) {
        return new SQL() {
            {
                DELETE_FROM("T_BOND_INVT_BSC t");
                WHERE("t.ETPS_INNER_INVT_NO = #{etps_inner_invt_no}");
            }
        }.toString();
    }

    public String deleteEnterInvenDt(@Param("etps_inner_invt_no") String etps_inner_invt_no) {
        return new SQL() {
            {
                DELETE_FROM("T_BOND_INVT_DT t");
                WHERE("t.HEAD_ETPS_INNER_INVT_NO = #{etps_inner_invt_no}");
            }
        }.toString();
    }


    public String getMaxGdsSeqno(@Param("customs_code") String customs_code) {
        return new SQL() {
            {
                SELECT("MAX(t.GDS_SEQNO)");
                FROM("T_BWL_LIST_TYPE t");
                WHERE("t.BIZOP_ETPSNO = #{customs_code}");
            }
        }.toString();
    }

    public String updateEnterInvtBody(
            @Param("bondInvtDt") LinkedHashMap<String, String> bondInvtDt,
            @Param("users") Users users
    ) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_DT");
                WHERE("HEAD_ETPS_INNER_INVT_NO = #{bondInvtDt.head_etps_inner_invt_no}");
                WHERE("GDS_SEQNO = #{bondInvtDt.gds_seqno}");
                if (!StringUtils.isEmpty(bondInvtDt.get("putrec_seqno"))) {
                    SET("PUTREC_SEQNO = #{bondInvtDt.putrec_seqno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("gds_mtno"))) {
                    SET("GDS_MTNO = #{bondInvtDt.gds_mtno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("gdecd"))) {
                    SET("GDECD = #{bondInvtDt.gdecd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("gds_nm"))) {
                    SET("GDS_NM = #{bondInvtDt.gds_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("gds_spcf_model_desc"))) {
                    SET("GDS_SPCF_MODEL_DESC = #{bondInvtDt.gds_spcf_model_desc}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("dcl_unitcd"))) {
                    SET("DCL_UNITCD = #{bondInvtDt.dcl_unitcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("lawf_unitcd"))) {
                    SET("LAWF_UNITCD = #{bondInvtDt.lawf_unitcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("secd_lawf_unitcd"))) {
                    SET("SECD_LAWF_UNITCD = #{bondInvtDt.secd_lawf_unitcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("natcd"))) {
                    SET("NATCD = #{bondInvtDt.natcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("dcl_uprc_amt"))) {
                    SET("DCL_UPRC_AMT = #{bondInvtDt.dcl_uprc_amt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("dcl_total_amt"))) {
                    SET("DCL_TOTAL_AMT = #{bondInvtDt.dcl_total_amt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("usd_stat_total_amt"))) {
                    SET("USD_STAT_TOTAL_AMT = #{bondInvtDt.usd_stat_total_amt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("dcl_currcd"))) {
                    SET("DCL_CURRCD = #{bondInvtDt.dcl_currcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("lawf_qty"))) {
                    SET("LAWF_QTY = #{bondInvtDt.lawf_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("secd_lawf_qty"))) {
                    SET("SECD_LAWF_QTY = #{bondInvtDt.secd_lawf_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("wt_sf_val"))) {
                    SET("WT_SF_VAL = #{bondInvtDt.wt_sf_val}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("fst_sf_val"))) {
                    SET("FST_SF_VAL = #{bondInvtDt.fst_sf_val}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("secd_sf_val"))) {
                    SET("SECD_SF_VAL = #{bondInvtDt.secd_sf_val}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("dcl_qty"))) {
                    SET("DCL_QTY = #{bondInvtDt.dcl_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("gross_wt"))) {
                    SET("GROSS_WT = #{bondInvtDt.gross_wt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("net_wt"))) {
                    SET("NET_WT = #{bondInvtDt.net_wt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("lvyrlf_modecd"))) {
                    SET("LVYRLF_MODECD = #{bondInvtDt.lvyrlf_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("ucns_verno"))) {
                    SET("UCNS_VERNO = #{bondInvtDt.ucns_verno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("entry_gds_seqno"))) {
                    SET("ENTRY_GDS_SEQNO = #{bondInvtDt.entry_gds_seqno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("apply_tb_seqno"))) {
                    SET("APPLY_TB_SEQNO = #{bondInvtDt.apply_tb_seqno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("cly_markcd"))) {
                    SET("CLY_MARKCD = #{bondInvtDt.cly_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("rmk"))) {
                    SET("RMK = #{bondInvtDt.rmk}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("destination_natcd"))) {
                    SET("DESTINATION_NATCD = #{bondInvtDt.destination_natcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.get("modf_markcd"))) {
                    SET("MODF_MARKCD = #{bondInvtDt.modf_markcd}");
                }
                if (!StringUtils.isEmpty(users.getId())) {
                    SET("UPD_TIME = sysdate");
                }
                if (!StringUtils.isEmpty(users.getId())) {
                    SET("UPD_USER = #{users.id}");
                }
            }
        }.toString();
    }


}
