package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExitLogSQLProvider extends BaseSQLProvider {

    public String getLogicDataByExitBondInvt(Map<String, String> map) {
        final String etps_inner_invt_no = map.get("etps_inner_invt_no");
        final String flag = map.get("flag");
        final String data_status = map.get("data_status");
        final String type = map.get("type");
        final String status = map.get("status");
        final String entId = map.get("entId");
        final String roleId = map.get("roleId");
        return new SQL() {
            {
                SELECT("t.id");
                SELECT("t.etps_inner_invt_no");
                SELECT("v.result vs_result");
                FROM("T_BOND_INVT_BSC t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS v on t.ID = v.CB_HEAD_ID and v.TYPE = #{type}");
                WHERE("t.status = #{data_status}");
                WHERE("t.flag = #{flag}");
                WHERE("v.status = #{status}");
                if (!roleId.equals("admin")) {
                    WHERE("t.crt_ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(etps_inner_invt_no)) {
                    WHERE("t.etps_inner_invt_no = #{etps_inner_invt_no}");
                }
                ORDER_BY("t.crt_time desc");
                ORDER_BY("t.etps_inner_invt_no asc");
            }
        }.toString();
    }

    public String getLogicDataByExitPassPort(Map<String, String> map) {
        final String etps_preent_no = map.get("etps_preent_no");
        final String flag = map.get("flag");
        final String data_status = map.get("data_status");
        final String type = map.get("type");
        final String status = map.get("status");
        final String entId = map.get("entId");
        final String roleId = map.get("roleId");
        return new SQL() {
            {
                SELECT("t.id");
                SELECT("t.etps_preent_no");
                SELECT("v.result vs_result");
                FROM("T_PASS_PORT_HEAD t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS v on t.ID = v.CB_HEAD_ID and v.TYPE = #{type}");
                WHERE("t.status = #{data_status}");
                WHERE("t.flag = #{flag}");
                WHERE("v.status = #{status}");
                if (!roleId.equals("admin")) {
                    WHERE("t.crt_ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(etps_preent_no)) {
                    WHERE("t.etps_preent_no = #{etps_preent_no}");
                }
                ORDER_BY("t.crt_time desc");
                ORDER_BY("t.etps_preent_no asc");
            }
        }.toString();
    }

    public String updateEnterBondInvtBscLogic(
            @Param("bondInvtBsc") LinkedHashMap<String, String> bondInvtBsc,
            @Param("users") Users users
    ) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC");
                WHERE("ETPS_INNER_INVT_NO = #{bondInvtBsc.etps_inner_invt_no}");
                SET("STATUS = 'BDDS0'");
                if (!StringUtils.isEmpty(bondInvtBsc.get("bond_invt_no"))) {
                    SET("BOND_INVT_NO = #{bondInvtBsc.bond_invt_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("invt_preent_no"))) {
                    SET("INVT_PREENT_NO = #{bondInvtBsc.invt_preent_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("putrec_no"))) {
                    SET("PUTREC_NO = #{bondInvtBsc.putrec_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("bizop_etpsno"))) {
                    SET("BIZOP_ETPSNO = #{bondInvtBsc.bizop_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("bizop_etps_nm"))) {
                    SET("BIZOP_ETPS_NM = #{bondInvtBsc.bizop_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("dcl_etpsno"))) {
                    SET("DCL_ETPSNO = #{bondInvtBsc.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("dcl_etps_nm"))) {
                    SET("DCL_ETPS_NM = #{bondInvtBsc.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("rcvgd_etpsno"))) {
                    SET("RCVGD_ETPSNO = #{bondInvtBsc.rcvgd_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("rcvgd_etps_nm"))) {
                    SET("RCVGD_ETPS_NM = #{bondInvtBsc.rcvgd_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("impexp_portcd"))) {
                    SET("IMPEXP_PORTCD = #{bondInvtBsc.impexp_portcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("dcl_plc_cuscd"))) {
                    SET("DCL_PLC_CUSCD = #{bondInvtBsc.dcl_plc_cuscd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("impexp_markcd"))) {
                    SET("IMPEXP_MARKCD = #{bondInvtBsc.impexp_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("mtpck_endprd_markcd"))) {
                    SET("MTPCK_ENDPRD_MARKCD = #{bondInvtBsc.mtpck_endprd_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("supv_modecd"))) {
                    SET("SUPV_MODECD = #{bondInvtBsc.supv_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("trsp_modecd"))) {
                    SET("TRSP_MODECD = #{bondInvtBsc.trsp_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("dclcus_flag"))) {
                    SET("DCLCUS_FLAG = #{bondInvtBsc.dclcus_flag}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("stship_trsarv_natcd"))) {
                    SET("STSHIP_TRSARV_NATCD = #{bondInvtBsc.stship_trsarv_natcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("bond_invt_typecd"))) {
                    SET("BOND_INVT_TYPECD = #{bondInvtBsc.bond_invt_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("dcl_typecd"))) {
                    SET("DCL_TYPECD = #{bondInvtBsc.dcl_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.get("rmk"))) {
                    SET("RMK = #{bondInvtBsc.rmk}");
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


    public String updateEnterBondInvtDtLogic(
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
