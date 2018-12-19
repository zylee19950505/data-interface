package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class CrtExitInventorySQLProvider extends BaseSQLProvider{

    //查询清单页面数据
    public String queryCrtEInventoryList(Map<String, String> paramMap) throws Exception {

        final String end = paramMap.get("end");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT t.bill_no," +
                        "t.guid," +
                        "t.order_no," +
                        "t.logistics_no," +
                        "t.invt_no," +
                        "t.ebp_name," +
                        "t.ebc_name," +
                        "t.logistics_name," +
                        "t.app_time," +
                        "t.return_status," +
                        "t.cop_no," +
                        "(select ss.status_name " +
                        "from t_status ss " +
                        "where ss.status_code = t.return_status) return_status_name");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE(splitJointIn("t.return_Status", returnStatus));
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.app_time desc ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.app_time desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    //查询清单页面数据总数
    public String queryCrtEInventoryCount(Map<String, String> paramMap) throws Exception {

        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE(splitJointIn("t.return_Status", returnStatus));
                }
            }
        }.toString();
    }

    public String queryGuids(String invtNos){
        return new SQL(){
            {
                SELECT("GUID");
                FROM("T_IMP_INVENTORY_HEAD");
                WHERE(splitJointIn("INVT_NO",invtNos));
            }
        }.toString();
    }

    public String queryImpInventoryBodyList(String dataList){
        return new SQL(){
            {
                SELECT("*");
                FROM("T_IMP_INVENTORY_BODY");
                WHERE(splitJointIn("HEAD_GUID ",dataList));
            }
        }.toString();
    }

    public String saveBondInvtBsc(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo){
        return new SQL(){
            {
                INSERT_INTO("T_BOND_INVT_BSC");
                if(!StringUtils.isEmpty(BondInvtBsc.get("id"))){
                    VALUES("id","#{BondInvtBsc.id}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bond_invt_no"))){
                    VALUES("bond_invt_no","#{BondInvtBsc.bond_invt_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("chg_tms_cnt"))){
                    VALUES("chg_tms_cnt","#{BondInvtBsc.chg_tms_cnt}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("invt_preent_no"))){
                    VALUES("invt_preent_no","#{BondInvtBsc.invt_preent_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("putrec_no"))){
                    VALUES("putrec_no","#{BondInvtBsc.putrec_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("etps_inner_invt_no"))){
                    VALUES("etps_inner_invt_no","#{BondInvtBsc.etps_inner_invt_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bizop_etps_sccd"))){
                    VALUES("bizop_etps_sccd","#{BondInvtBsc.bizop_etps_sccd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bizop_etpsno"))){
                    VALUES("bizop_etpsno","#{BondInvtBsc.bizop_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bizop_etps_nm"))){
                    VALUES("bizop_etps_nm","#{BondInvtBsc.bizop_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rvsngd_etps_sccd"))){
                    VALUES("rvsngd_etps_sccd","#{BondInvtBsc.rvsngd_etps_sccd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rcvgd_etpsno"))){
                    VALUES("rcvgd_etpsno","#{BondInvtBsc.rcvgd_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rcvgd_etps_nm"))){
                    VALUES("rcvgd_etps_nm","#{BondInvtBsc.rcvgd_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_etps_sccd"))){
                    VALUES("dcl_etps_sccd","#{BondInvtBsc.dcl_etps_sccd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_etpsno"))){
                    VALUES("dcl_etpsno","#{BondInvtBsc.dcl_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_etps_nm"))){
                    VALUES("dcl_etps_nm","#{BondInvtBsc.dcl_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("invt_dcl_time"))){
                    VALUES("invt_dcl_time","#{BondInvtBsc.invt_dcl_time}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("entry_dcl_time"))){
                    VALUES("entry_dcl_time","#{BondInvtBsc.entry_dcl_time}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("entry_no"))){
                    VALUES("entry_no","#{BondInvtBsc.entry_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_invt_no"))){
                    VALUES("rlt_invt_no","#{BondInvtBsc.rlt_invt_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_putrec_no"))){
                    VALUES("rlt_putrec_no","#{BondInvtBsc.rlt_putrec_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_no"))){
                    VALUES("rlt_entry_no","#{BondInvtBsc.rlt_entry_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_bizop_etps_sccd"))){
                    VALUES("rlt_entry_bizop_etps_sccd","#{BondInvtBsc.rlt_entry_bizop_etps_sccd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_bizop_etpsno"))){
                    VALUES("rlt_entry_bizop_etpsno","#{BondInvtBsc.rlt_entry_bizop_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_bizop_etps_nm"))){
                    VALUES("rlt_entry_bizop_etps_nm","#{BondInvtBsc.rlt_entry_bizop_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("impexp_portcd"))){
                    VALUES("impexp_portcd","#{BondInvtBsc.impexp_portcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_plc_cuscd"))){
                    VALUES("dcl_plc_cuscd","#{BondInvtBsc.dcl_plc_cuscd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("impexp_markcd"))){
                    VALUES("impexp_markcd","#{BondInvtBsc.impexp_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("mtpck_endprd_markcd"))){
                    VALUES("mtpck_endprd_markcd","#{BondInvtBsc.mtpck_endprd_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("supv_modecd"))){
                    VALUES("supv_modecd","#{BondInvtBsc.supv_modecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("trsp_modecd"))){
                    VALUES("trsp_modecd","#{BondInvtBsc.trsp_modecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("apply_no"))){
                    VALUES("apply_no","#{BondInvtBsc.apply_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("stship_trsarv_natcd"))){
                    VALUES("stship_trsarv_natcd","#{BondInvtBsc.stship_trsarv_natcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dclcus_flag"))){
                    VALUES("dclcus_flag","#{BondInvtBsc.dclcus_flag}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dclcus_typecd"))){
                    VALUES("dclcus_typecd","#{BondInvtBsc.dclcus_typecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("prevd_time"))){
                    VALUES("prevd_time","#{BondInvtBsc.prevd_time}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("formal_vrfded_time"))){
                    VALUES("formal_vrfded_time","#{BondInvtBsc.formal_vrfded_time}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("invt_iochkpt_stucd"))){
                    VALUES("invt_iochkpt_stucd","#{BondInvtBsc.invt_iochkpt_stucd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("vrfded_markcd"))){
                    VALUES("vrfded_markcd","#{BondInvtBsc.vrfded_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("invt_stucd"))){
                    VALUES("invt_stucd","#{BondInvtBsc.invt_stucd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("vrfded_modecd"))){
                    VALUES("vrfded_modecd","#{BondInvtBsc.vrfded_modecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("du_code"))){
                    VALUES("du_code","#{BondInvtBsc.du_code}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rmk"))){
                    VALUES("rmk","#{BondInvtBsc.rmk}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bond_invt_typecd"))){
                    VALUES("bond_invt_typecd","#{BondInvtBsc.bond_invt_typecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("entry_stucd"))){
                    VALUES("entry_stucd","#{BondInvtBsc.entry_stucd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("passport_used_typecd"))){
                    VALUES("passport_used_typecd","#{BondInvtBsc.passport_used_typecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("param1"))){
                    VALUES("param1","#{BondInvtBsc.param1}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("param2"))){
                    VALUES("param2","#{BondInvtBsc.param2}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("param3"))){
                    VALUES("param3","#{BondInvtBsc.param3}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("param4"))){
                    VALUES("param4","#{BondInvtBsc.param4}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("need_entry_modified"))){
                    VALUES("need_entry_modified","#{BondInvtBsc.need_entry_modified}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("levy_bl_amt"))){
                    VALUES("levy_bl_amt","#{BondInvtBsc.levy_bl_amt}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_typecd"))){
                    VALUES("dcl_typecd","#{BondInvtBsc.dcl_typecd}");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    VALUES("status","'BDDS2'");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    VALUES("crt_time","sysdate");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    VALUES("crt_user","#{userInfo.id}");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    VALUES("upd_time","sysdate");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    VALUES("upd_user","#{userInfo.id}");
                }
                if(!StringUtils.isEmpty(userInfo.getEnt_Id())){
                    VALUES("crt_ent_id","#{userInfo.ent_Id}");
                }
                if(!StringUtils.isEmpty(userInfo.getEnt_Name())){
                    VALUES("crt_ent_name","#{userInfo.ent_Name}");
                }
            }
        }.toString();
    }

    public String saveBondInvtDt(@Param("BondInvtDt") LinkedHashMap<String, String> BondInvtDt, @Param("userInfo") Users userInfo){
        return new SQL(){
            {
                INSERT_INTO("T_BOND_INVT_DT");
                if(!StringUtils.isEmpty(BondInvtDt.get("id"))){
                    VALUES("id","#{BondInvtDt.id}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("bond_invt_no"))){
                    VALUES("bond_invt_no","#{BondInvtDt.bond_invt_no}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("chg_tms_cnt"))){
                    VALUES("chg_tms_cnt","#{BondInvtDt.chg_tms_cnt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gds_seqno"))){
                    VALUES("gds_seqno","#{BondInvtDt.gds_seqno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("putrec_seqno"))){
                    VALUES("putrec_seqno","#{BondInvtDt.putrec_seqno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gds_mtno"))){
                    VALUES("gds_mtno","#{BondInvtDt.gds_mtno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gdecd"))){
                    VALUES("gdecd","#{BondInvtDt.gdecd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gds_nm"))){
                    VALUES("gds_nm","#{BondInvtDt.gds_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gds_spcf_model_desc"))){
                    VALUES("gds_spcf_model_desc","#{BondInvtDt.gds_spcf_model_desc}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("dcl_unitcd"))){
                    VALUES("dcl_unitcd","#{BondInvtDt.dcl_unitcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("lawf_unitcd"))){
                    VALUES("lawf_unitcd","#{BondInvtDt.lawf_unitcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("secd_lawf_unitcd"))){
                    VALUES("secd_lawf_unitcd","#{BondInvtDt.secd_lawf_unitcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("natcd"))){
                    VALUES("natcd","#{BondInvtDt.natcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("dcl_uprc_amt"))){
                    VALUES("dcl_uprc_amt","#{BondInvtDt.dcl_uprc_amt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("dcl_total_amt"))){
                    VALUES("dcl_total_amt","#{BondInvtDt.dcl_total_amt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("usd_stat_total_amt"))){
                    VALUES("usd_stat_total_amt","#{BondInvtDt.usd_stat_total_amt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("dcl_currcd"))){
                    VALUES("dcl_currcd","#{BondInvtDt.dcl_currcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("lawf_qty"))){
                    VALUES("lawf_qty","#{BondInvtDt.lawf_qty}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("secd_lawf_qty"))){
                    VALUES("secd_lawf_qty","#{BondInvtDt.secd_lawf_qty}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("wt_sf_val"))){
                    VALUES("wt_sf_val","#{BondInvtDt.wt_sf_val}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("fst_sf_val"))){
                    VALUES("fst_sf_val","#{BondInvtDt.fst_sf_val}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("secd_sf_val"))){
                    VALUES("secd_sf_val","#{BondInvtDt.secd_sf_val}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("dcl_qty"))){
                    VALUES("dcl_qty","#{BondInvtDt.dcl_qty}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gross_wt"))){
                    VALUES("gross_wt","#{BondInvtDt.gross_wt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("net_wt"))){
                    VALUES("net_wt","#{BondInvtDt.net_wt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("lvyrlf_modecd"))){
                    VALUES("lvyrlf_modecd","#{BondInvtDt.lvyrlf_modecd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("ucns_verno"))){
                    VALUES("ucns_verno","#{BondInvtDt.ucns_verno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("entry_gds_seqno"))){
                    VALUES("entry_gds_seqno","#{BondInvtDt.entry_gds_seqno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("apply_tb_seqno"))){
                    VALUES("apply_tb_seqno","#{BondInvtDt.apply_tb_seqno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("cly_markcd"))){
                    VALUES("cly_markcd","#{BondInvtDt.cly_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("rmk"))){
                    VALUES("rmk","#{BondInvtDt.rmk}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("destination_natcd"))){
                    VALUES("destination_natcd","#{BondInvtDt.destination_natcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("modf_markcd"))){
                    VALUES("modf_markcd","#{BondInvtDt.modf_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("head_etps_inner_invt_no"))){
                    VALUES("head_etps_inner_invt_no","#{BondInvtDt.head_etps_inner_invt_no}");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    VALUES("crt_time","sysdate");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    VALUES("crt_user","#{userInfo.id}");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    VALUES("upd_time","sysdate");
                }
                if(!StringUtils.isEmpty(userInfo.getId())){
                    VALUES("upd_user","#{userInfo.id}");
                }
            }
        }.toString();
    }

}
