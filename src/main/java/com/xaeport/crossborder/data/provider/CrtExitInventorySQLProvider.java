package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
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

    public String saveBondInvtDt(LinkedHashMap<String, String> BondInvtDt){
        return new SQL(){
            {
                INSERT_INTO("T_BOND_INVT_DT");
                if(!StringUtils.isEmpty(BondInvtDt.get("id"))){
                    VALUES("id","#{id}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("bond_invt_no"))){
                    VALUES("bond_invt_no","#{bond_invt_no}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("chg_tms_cnt"))){
                    VALUES("chg_tms_cnt","#{chg_tms_cnt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gds_seqno"))){
                    VALUES("gds_seqno","#{gds_seqno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("putrec_seqno"))){
                    VALUES("putrec_seqno","#{putrec_seqno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gds_mtno"))){
                    VALUES("gds_mtno","#{gds_mtno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gdecd"))){
                    VALUES("gdecd","#{gdecd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gds_nm"))){
                    VALUES("gds_nm","#{gds_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gds_spcf_model_desc"))){
                    VALUES("gds_spcf_model_desc","#{gds_spcf_model_desc}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("dcl_unitcd"))){
                    VALUES("dcl_unitcd","#{dcl_unitcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("lawf_unitcd"))){
                    VALUES("lawf_unitcd","#{lawf_unitcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("secd_lawf_unitcd"))){
                    VALUES("secd_lawf_unitcd","#{secd_lawf_unitcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("natcd"))){
                    VALUES("natcd","#{natcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("dcl_uprc_amt"))){
                    VALUES("dcl_uprc_amt","#{dcl_uprc_amt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("dcl_total_amt"))){
                    VALUES("dcl_total_amt","#{dcl_total_amt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("usd_stat_total_amt"))){
                    VALUES("usd_stat_total_amt","#{usd_stat_total_amt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("dcl_currcd"))){
                    VALUES("dcl_currcd","#{dcl_currcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("lawf_qty"))){
                    VALUES("lawf_qty","#{lawf_qty}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("secd_lawf_qty"))){
                    VALUES("secd_lawf_qty","#{secd_lawf_qty}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("wt_sf_val"))){
                    VALUES("wt_sf_val","#{wt_sf_val}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("fst_sf_val"))){
                    VALUES("fst_sf_val","#{fst_sf_val}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("secd_sf_val"))){
                    VALUES("secd_sf_val","#{secd_sf_val}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("dcl_qty"))){
                    VALUES("dcl_qty","#{dcl_qty}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("gross_wt"))){
                    VALUES("gross_wt","#{gross_wt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("net_wt"))){
                    VALUES("net_wt","#{net_wt}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("lvyrlf_modecd"))){
                    VALUES("lvyrlf_modecd","#{lvyrlf_modecd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("ucns_verno"))){
                    VALUES("ucns_verno","#{ucns_verno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("entry_gds_seqno"))){
                    VALUES("entry_gds_seqno","#{entry_gds_seqno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("apply_tb_seqno"))){
                    VALUES("apply_tb_seqno","#{apply_tb_seqno}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("cly_markcd"))){
                    VALUES("cly_markcd","#{cly_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("rmk"))){
                    VALUES("rmk","#{rmk}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("destination_natcd"))){
                    VALUES("destination_natcd","#{destination_natcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("modf_markcd"))){
                    VALUES("modf_markcd","#{modf_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("crt_time"))){
                    VALUES("crt_time","#{crt_time}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("crt_user"))){
                    VALUES("crt_user","#{crt_user}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("upd_time"))){
                    VALUES("upd_time","#{upd_time}");
                }
                if(!StringUtils.isEmpty(BondInvtDt.get("upd_user"))){
                    VALUES("upd_user","#{upd_user}");
                }
            }
        }.toString();
    }

    public String saveBondInvtBsc(LinkedHashMap<String, String> BondInvtBsc){
        return new SQL(){
            {
                INSERT_INTO("T_BOND_INVT_BSC");
                if(!StringUtils.isEmpty(BondInvtBsc.get("id"))){
                    VALUES("id","#{id}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bond_invt_no"))){
                    VALUES("bond_invt_no","#{bond_invt_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("chg_tms_cnt"))){
                    VALUES("chg_tms_cnt","#{chg_tms_cnt}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("invt_preent_no"))){
                    VALUES("invt_preent_no","#{invt_preent_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("putrec_no"))){
                    VALUES("putrec_no","#{putrec_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("etps_inner_invt_no"))){
                    VALUES("etps_inner_invt_no","#{etps_inner_invt_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bizop_etps_sccd"))){
                    VALUES("bizop_etps_sccd","#{bizop_etps_sccd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bizop_etpsno"))){
                    VALUES("bizop_etpsno","#{bizop_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bizop_etps_nm"))){
                    VALUES("bizop_etps_nm","#{bizop_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rvsngd_etps_sccd"))){
                    VALUES("rvsngd_etps_sccd","#{rvsngd_etps_sccd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rcvgd_etpsno"))){
                    VALUES("rcvgd_etpsno","#{rcvgd_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rcvgd_etps_nm"))){
                    VALUES("rcvgd_etps_nm","#{rcvgd_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_etps_sccd"))){
                    VALUES("dcl_etps_sccd","#{dcl_etps_sccd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_etpsno"))){
                    VALUES("dcl_etpsno","#{dcl_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_etps_nm"))){
                    VALUES("dcl_etps_nm","#{dcl_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("invt_dcl_time"))){
                    VALUES("invt_dcl_time","#{invt_dcl_time}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("entry_dcl_time"))){
                    VALUES("entry_dcl_time","#{entry_dcl_time}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("entry_no"))){
                    VALUES("entry_no","#{entry_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_invt_no"))){
                    VALUES("rlt_invt_no","#{rlt_invt_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_putrec_no"))){
                    VALUES("rlt_putrec_no","#{rlt_putrec_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_no"))){
                    VALUES("rlt_entry_no","#{rlt_entry_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_bizop_etps_sccd"))){
                    VALUES("rlt_entry_bizop_etps_sccd","#{rlt_entry_bizop_etps_sccd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_bizop_etpsno"))){
                    VALUES("rlt_entry_bizop_etpsno","#{rlt_entry_bizop_etpsno}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_bizop_etps_nm"))){
                    VALUES("rlt_entry_bizop_etps_nm","#{rlt_entry_bizop_etps_nm}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("impexp_portcd"))){
                    VALUES("impexp_portcd","#{impexp_portcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_plc_cuscd"))){
                    VALUES("dcl_plc_cuscd","#{dcl_plc_cuscd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("impexp_markcd"))){
                    VALUES("impexp_markcd","#{impexp_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("mtpck_endprd_markcd"))){
                    VALUES("mtpck_endprd_markcd","#{mtpck_endprd_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("supv_modecd"))){
                    VALUES("supv_modecd","#{supv_modecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("trsp_modecd"))){
                    VALUES("trsp_modecd","#{trsp_modecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("apply_no"))){
                    VALUES("apply_no","#{apply_no}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("stship_trsarv_natcd"))){
                    VALUES("stship_trsarv_natcd","#{stship_trsarv_natcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dclcus_flag"))){
                    VALUES("dclcus_flag","#{dclcus_flag}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dclcus_typecd"))){
                    VALUES("dclcus_typecd","#{dclcus_typecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("prevd_time"))){
                    VALUES("prevd_time","#{prevd_time}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("formal_vrfded_time"))){
                    VALUES("formal_vrfded_time","#{formal_vrfded_time}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("invt_iochkpt_stucd"))){
                    VALUES("invt_iochkpt_stucd","#{invt_iochkpt_stucd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("vrfded_markcd"))){
                    VALUES("vrfded_markcd","#{vrfded_markcd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("invt_stucd"))){
                    VALUES("invt_stucd","#{invt_stucd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("vrfded_modecd"))){
                    VALUES("vrfded_modecd","#{vrfded_modecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("du_code"))){
                    VALUES("du_code","#{du_code}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("rmk"))){
                    VALUES("rmk","#{rmk}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("bond_invt_typecd"))){
                    VALUES("bond_invt_typecd","#{bond_invt_typecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("entry_stucd"))){
                    VALUES("entry_stucd","#{entry_stucd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("passport_used_typecd"))){
                    VALUES("passport_used_typecd","#{passport_used_typecd}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("param1"))){
                    VALUES("param1","#{param1}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("param2"))){
                    VALUES("param2","#{param2}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("param3"))){
                    VALUES("param3","#{param3}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("param4"))){
                    VALUES("param4","#{param4}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("need_entry_modified"))){
                    VALUES("need_entry_modified","#{need_entry_modified}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("levy_bl_amt"))){
                    VALUES("levy_bl_amt","#{levy_bl_amt}");
                }
                if(!StringUtils.isEmpty(BondInvtBsc.get("dcl_typecd"))){
                    VALUES("dcl_typecd","#{dcl_typecd}");
                }
            }
        }.toString();
    }

}
