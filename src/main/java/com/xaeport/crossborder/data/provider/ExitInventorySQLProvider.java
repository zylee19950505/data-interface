package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExitInventorySQLProvider extends BaseSQLProvider {


    //查询出区核注清单数据
    public String queryEInventoryList(Map<String, String> paramMap) throws Exception {

        final String end = paramMap.get("end");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String entry_dcl_time = paramMap.get("entry_dcl_time");
        final String status = paramMap.get("status");
        final String return_status = paramMap.get("return_status");
        final String bond_invt_no = paramMap.get("bond_invt_no");

        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT " +
                        "t.ID," +
                        "t.ETPS_INNER_INVT_NO," +
                        "t.BOND_INVT_NO," +
                        "t.STATUS," +
                        "t.ENTRY_DCL_TIME," +
                        "t.RETURN_STATUS," +
                        "t.RETURN_TIME," +
                        "t.RETURN_INFO");
                FROM("T_BOND_INVT_BSC t");
                WHERE("t.FLAG = 'EXIT'");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(entry_dcl_time)) {
                    WHERE("t.entry_dcl_time >= to_date( #{entry_dcl_time} || '00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(status)) {
                    WHERE("t.status = #{status}");
                }
                if (!StringUtils.isEmpty(return_status)) {
                    WHERE("t.return_status = #{return_status}");
                }
                if (!StringUtils.isEmpty(bond_invt_no)) {
                    WHERE("t.bond_invt_no = #{bond_invt_no}");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.crt_time desc ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.crt_time desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    //查询出区核注清单总数
    public String queryEInventoryCount(Map<String, String> paramMap) throws Exception {

        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String entry_dcl_time = paramMap.get("entry_dcl_time");
        final String status = paramMap.get("status");
        final String return_status = paramMap.get("return_status");
        final String bond_invt_no = paramMap.get("bond_invt_no");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_BOND_INVT_BSC t");
                WHERE("t.FLAG = 'EXIT'");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(entry_dcl_time)) {
                    WHERE("t.entry_dcl_time >= to_date( #{entry_dcl_time} || '00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(status)) {
                    WHERE("t.status = #{status}");
                }
                if (!StringUtils.isEmpty(return_status)) {
                    WHERE("t.return_status = #{return_status}");
                }
                if (!StringUtils.isEmpty(bond_invt_no)) {
                    WHERE("t.bond_invt_no = #{bond_invt_no}");
                }
            }
        }.toString();
    }

    /*
     * 提交海关清单
     */
    public String updateSubmitCustom(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        final String statusWhere = paramMap.get("statusWhere");
        final String status = paramMap.get("status");
        final String userId = paramMap.get("userId");
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE(splitJointIn("t.ETPS_INNER_INVT_NO", submitKeys));
                WHERE(splitJointIn("t.STATUS", statusWhere));
                SET("t.STATUS = #{status}");
                SET("t.INVT_DCL_TIME = sysdate");
                SET("t.UPD_TIME = sysdate");
                SET("t.UPD_USER = #{userId}");
            }
        }.toString();
    }

    public String queryBondInvtBsc(Map<String, String> paramMap) {

        final String etpsInnerInvtNo = paramMap.get("etpsInnerInvtNo");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_BOND_INVT_BSC");
                WHERE("ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}");
            }
        }.toString();
    }

    public String queryBondInvtDtList(Map<String, String> paramMap) {

        final String etpsInnerInvtNo = paramMap.get("etpsInnerInvtNo");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_BOND_INVT_DT");
                WHERE("HEAD_ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}");
            }
        }.toString();
    }


    public String updateBondInvtBsc(
            @Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC");
                WHERE("ETPS_INNER_INVT_NO = #{BondInvtBsc.etps_inner_invt_no}");
                SET("STATUS = 'BDDS2'");
                if(!StringUtils.isEmpty(BondInvtBsc.get("bond_invt_no"))){
                    SET("BOND_INVT_NO = #{BondInvtBsc.bond_invt_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("invt_preent_no"))){
                    SET("INVT_PREENT_NO = #{BondInvtBsc.invt_preent_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("putrec_no"))){
                    SET("PUTREC_NO = #{BondInvtBsc.putrec_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bizop_etpsno"))){
                    SET("BIZOP_ETPSNO = #{BondInvtBsc.bizop_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bizop_etps_nm"))){
                    SET("BIZOP_ETPS_NM = #{BondInvtBsc.bizop_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_etpsno"))){
                    SET("DCL_ETPSNO = #{BondInvtBsc.dcl_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_etps_nm"))){
                    SET("DCL_ETPS_NM = #{BondInvtBsc.dcl_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rcvgd_etpsno"))){
                    SET("RCVGD_ETPSNO = #{BondInvtBsc.rcvgd_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rcvgd_etps_nm"))){
                    SET("RCVGD_ETPS_NM = #{BondInvtBsc.rcvgd_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_plc_cuscd"))){
                    SET("DCL_PLC_CUSCD = #{BondInvtBsc.dcl_plc_cuscd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("impexp_markcd"))){
                    SET("IMPEXP_MARKCD = #{BondInvtBsc.impexp_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("mtpck_endprd_markcd"))){
                    SET("MTPCK_ENDPRD_MARKCD = #{BondInvtBsc.mtpck_endprd_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("supv_modecd"))){
                    SET("SUPV_MODECD = #{BondInvtBsc.supv_modecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("trsp_modecd"))){
                    SET("TRSP_MODECD = #{BondInvtBsc.trsp_modecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dclcus_flag"))){
                    SET("DCLCUS_FLAG = #{BondInvtBsc.dclcus_flag}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("stship_trsarv_natcd"))){
                    SET("STSHIP_TRSARV_NATCD = #{BondInvtBsc.stship_trsarv_natcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bond_invt_typecd"))){
                    SET("BOND_INVT_TYPECD = #{BondInvtBsc.bond_invt_typecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_typecd"))){
                    SET("DCL_TYPECD = #{BondInvtBsc.dcl_typecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rmk"))){
                    SET("RMK = #{BondInvtBsc.rmk}");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    SET("UPD_TIME = sysdate");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    SET("UPD_USER = #{userInfo.id}");
                }
            }
        }.toString();
    }

    public String updateBondInvtDt(
            @Param("BondInvtDt") LinkedHashMap<String, String> BondInvtDt,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_DT");
                WHERE("HEAD_ETPS_INNER_INVT_NO = #{BondInvtDt.etps_inner_invt_no}");
                WHERE("GDS_SEQNO = #{BondInvtDt.body_gds_seqno}");
                if(!StringUtils.isEmpty(BondInvtDt.get("body_putrec_seqno"))){
                    SET("PUTREC_SEQNO = #{BondInvtDt.body_putrec_seqno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("body_gds_mtno"))){
                    SET("GDS_MTNO = #{BondInvtDt.body_gds_mtno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("body_gdecd"))){
                    SET("GDECD = #{BondInvtDt.body_gdecd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("body_gds_nm"))){
                    SET("GDS_NM = #{BondInvtDt.body_gds_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("body_gds_spcf_model_desc"))){
                    SET("GDS_SPCF_MODEL_DESC = #{BondInvtDt.body_gds_spcf_model_desc}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("body_dcl_unitcd"))){
                    SET("DCL_UNITCD = #{BondInvtDt.body_dcl_unitcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("body_dcl_qty"))){
                    SET("DCL_QTY = #{BondInvtDt.body_dcl_qty}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("body_dcl_uprc_amt"))){
                    SET("DCL_UPRC_AMT = #{BondInvtDt.body_dcl_uprc_amt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("body_dcl_total_amt"))){
                    SET("DCL_TOTAL_AMT = #{BondInvtDt.body_dcl_total_amt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("body_dcl_currcd"))){
                    SET("DCL_CURRCD = #{BondInvtDt.body_dcl_currcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("body_usd_stat_total_amt"))){
                    SET("USD_STAT_TOTAL_AMT = #{BondInvtDt.body_usd_stat_total_amt}");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    SET("UPD_TIME = sysdate");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    SET("UPD_USER = #{userInfo.id}");
                }
            }
        }.toString();
    }

    public String updateBondInvtBscByInvtDt(
            @Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC");
                WHERE("ETPS_INNER_INVT_NO = #{BondInvtBsc.etps_inner_invt_no}");
                SET("STATUS = 'BDDS2'");
                SET("UPD_TIME = sysdate");
                SET("UPD_USER = #{userInfo.id}");
            }
        }.toString();
    }

    public String queryDeleteDataByCode(Map<String, String> paramMap) throws Exception {
        final String etpsInnerInvtNo = paramMap.get("etpsInnerInvtNo");
        final String entId = paramMap.get("entId");
        final String status = paramMap.get("status");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_BOND_INVT_BSC t");
                if (!StringUtils.isEmpty(etpsInnerInvtNo)) {
                    String str = etpsInnerInvtNo.replace(",", "','");
                    WHERE("t.ETPS_INNER_INVT_NO in ('" + str + "')");
                }
                if (!StringUtils.isEmpty(entId)) WHERE("t.CRT_ENT_ID = #{entId}");
                if (!StringUtils.isEmpty(status)) WHERE("t.STATUS = #{status}");
            }
        }.toString();
    }


}
