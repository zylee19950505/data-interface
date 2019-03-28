package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.NemsInvtCbecBillType;
import com.xaeport.crossborder.data.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.security.access.method.P;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class CrtExitInventorySQLProvider extends BaseSQLProvider {

    public String queryEbusinessEnt(Map<String, String> paramMap) throws Exception {

        final String port = paramMap.get("port");
        final String roleId = paramMap.get("roleId");

        return new SQL() {
            {
                SELECT("t.ENT_NAME");
                SELECT("t.CUSTOMS_CODE");
                FROM("T_ENTERPRISE t");
                WHERE("t.ENT_BUSINESS_TYPE = 'E-business'");
                if (!roleId.equals("admin")) {
                    if (port.equals("9007")) {
                        WHERE("t.PORT = '9009'");
                    }
                    if (port.equals("9013")) {
                        WHERE("t.PORT = '9013'");
                    }
                }
            }
        }.toString();
    }

    //查询保税清单页面数据
    public String queryCrtEInventoryData(Map<String, String> paramMap) throws Exception {

        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String returnStatus = paramMap.get("returnStatus");
        final String businessType = paramMap.get("businessType");
        final String port = paramMap.get("port");
        final String ebcCode = paramMap.get("ebcCode");
        final String billNo = paramMap.get("billNo");

        return new SQL() {
            {
                SELECT("t.BILL_NO");
                SELECT("t.EBC_CODE");
                SELECT("t.RETURN_STATUS");
                SELECT("COUNT(t.ORDER_NO) asscount");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.TRADE_MODE = '1210'");
                WHERE("t.IS_BOND_INVT_EXIT is null");
                WHERE("t.BUSINESS_TYPE = #{businessType}");
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.BILL_NO = #{billNo}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.RETURN_STATUS = #{returnStatus}");
                }
                if (!roleId.equals("admin")) {
                    if (port.equals("9013")) {
                        WHERE("t.CUSTOMS_CODE = '9013'");
                    }
                    if (port.equals("9007")) {
                        WHERE("t.CUSTOMS_CODE = '9009'");
                    }
                }
                if (!StringUtils.isEmpty(ebcCode)) {
                    WHERE("t.EBC_CODE = #{ebcCode}");
                }
                GROUP_BY("t.BILL_NO,t.EBC_CODE,t.RETURN_STATUS");
            }
        }.toString();
    }

    //查询保税清单页面数据
    public String queryCrtEInventoryList(Map<String, String> paramMap) throws Exception {

        final String end = paramMap.get("end");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String returnStatus = paramMap.get("returnStatus");
        final String businessType = paramMap.get("businessType");
        final String port = paramMap.get("port");
        final String ebcCode = paramMap.get("ebcCode");
        final String billNo = paramMap.get("billNo");

        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT t.bill_no," +
                        "t.guid," +
                        "t.order_no," +
                        "t.logistics_no," +
                        "t.invt_no," +
                        "t.app_time," +
                        "t.return_status," +
                        "t.cop_no");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.TRADE_MODE = '1210'");
                WHERE("t.IS_BOND_INVT_EXIT is null");
                WHERE("t.BUSINESS_TYPE = #{businessType}");
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.bill_no = #{billNo}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.return_Status = #{returnStatus}");
                }
                if (!roleId.equals("admin")) {
                    if (port.equals("9013")) {
                        WHERE("t.CUSTOMS_CODE = '9013'");
                    }
                    if (port.equals("9007")) {
                        WHERE("t.CUSTOMS_CODE = '9009'");
                    }
                }
                if (!StringUtils.isEmpty(ebcCode)) {
                    WHERE("t.EBC_CODE = #{ebcCode}");
                }
                ORDER_BY("t.CRT_TM ) f  )  WHERE rn between #{start} and #{end}");
            }
        }.toString();
    }

    //查询保税清单页面数据总数
    public String queryCrtEInventoryCount(Map<String, String> paramMap) throws Exception {

        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String returnStatus = paramMap.get("returnStatus");
        final String businessType = paramMap.get("businessType");
        final String port = paramMap.get("port");
        final String ebcCode = paramMap.get("ebcCode");
        final String billNo = paramMap.get("billNo");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.TRADE_MODE = '1210'");
                WHERE("t.IS_BOND_INVT_EXIT is null");
                WHERE("t.BUSINESS_TYPE = #{businessType}");
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.bill_no = #{billNo}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.return_Status = #{returnStatus}");
                }
                if (!roleId.equals("admin")) {
                    if (port.equals("9013")) {
                        WHERE("t.CUSTOMS_CODE = '9013'");
                    }
                    if (port.equals("9007")) {
                        WHERE("t.CUSTOMS_CODE = '9009'");
                    }
                }
                if (!StringUtils.isEmpty(ebcCode)) {
                    WHERE("t.EBC_CODE = #{ebcCode}");
                }
            }
        }.toString();
    }

    public String queryGuidByBillNos(Map<String, String> paramMap) throws Exception {

        final String entId = paramMap.get("ent_id");
        final String roleId = paramMap.get("roleId");
        final String returnStatus = paramMap.get("returnStatus");
        final String businessType = paramMap.get("businessType");
        final String port = paramMap.get("port");
        final String ebcCode = paramMap.get("ebcCode");
        final String billNo = paramMap.get("billNo");

        return new SQL() {
            {
                SELECT("t.BILL_NO");
                SELECT("t.EBC_CODE");
                SELECT("t.RETURN_STATUS");
                SELECT("COUNT(t.ORDER_NO) asscount");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.TRADE_MODE = '1210'");
                WHERE("t.IS_BOND_INVT_EXIT is null");
                WHERE("t.BUSINESS_TYPE = #{businessType}");
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE(splitJointIn("t.BILL_NO", billNo));
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.RETURN_STATUS = #{returnStatus}");
                }
                if (!roleId.equals("admin")) {
                    if (port.equals("9013")) {
                        WHERE("t.CUSTOMS_CODE = '9013'");
                    }
                    if (port.equals("9007")) {
                        WHERE("t.CUSTOMS_CODE = '9009'");
                    }
                }
                if (!StringUtils.isEmpty(ebcCode)) {
                    WHERE("t.EBC_CODE = #{ebcCode}");
                }
                GROUP_BY("t.BILL_NO,t.EBC_CODE,t.RETURN_STATUS");
            }
        }.toString();
    }

    public String queryListByBillNos(Map<String, String> paramMap) throws Exception {

        final String entId = paramMap.get("ent_id");
        final String roleId = paramMap.get("roleId");
        final String port = paramMap.get("port");
        final String businessType = paramMap.get("businessType");
        final String returnStatus = paramMap.get("returnStatus");
        final String ebcCode = paramMap.get("ebcCode");
        final String billNo = paramMap.get("billNo");

        return new SQL() {
            {
                SELECT("t.BILL_NO");
                SELECT("t.INVT_NO");
                SELECT("t.EBC_CODE");
                SELECT("t.RETURN_STATUS");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.TRADE_MODE = '1210'");
                WHERE("t.IS_BOND_INVT_EXIT = 'Y'");
                WHERE("t.BUSINESS_TYPE = #{businessType}");
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE(splitJointIn("t.BILL_NO", billNo));
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.RETURN_STATUS = #{returnStatus}");
                }
                if (!roleId.equals("admin")) {
                    if (port.equals("9013")) {
                        WHERE("t.CUSTOMS_CODE = '9013'");
                    }
                    if (port.equals("9007")) {
                        WHERE("t.CUSTOMS_CODE = '9009'");
                    }
                }
                if (!StringUtils.isEmpty(ebcCode)) {
                    WHERE("t.EBC_CODE = #{ebcCode}");
                }
            }
        }.toString();
    }

    public String queryInvtNos(String guids) throws Exception {
        return new SQL() {
            {
                SELECT("*");
                FROM("T_IMP_INVENTORY_HEAD");
                WHERE(splitJointIn("GUID", guids));
            }
        }.toString();
    }

    public String updateInventoryDataByBondInvt(
            @Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc,
            @Param("ebcCode") String ebcCode,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE(splitJointIn("t.BILL_NO", BondInvtBsc.get("invt_no")));
                WHERE("t.TRADE_MODE = '1210'");
                WHERE("t.IS_BOND_INVT_EXIT is null");
                WHERE("t.BUSINESS_TYPE = 'BONDINVEN'");
                WHERE("t.RETURN_STATUS = '800'");
                if (!(userInfo.getRoleId()).equals("admin")) {
                    if ((userInfo.getPort()).equals("9013")) {
                        WHERE("t.CUSTOMS_CODE = '9013'");
                    }
                    if ((userInfo.getPort()).equals("9007")) {
                        WHERE("t.CUSTOMS_CODE = '9009'");
                    }
                }
                if (!StringUtils.isEmpty(ebcCode)) {
                    WHERE("t.EBC_CODE = #{ebcCode}");
                }
                SET("IS_BOND_INVT_EXIT = 'Y'");
            }
        }.toString();
    }

    public String saveBondInvtBsc(
            @Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_BOND_INVT_BSC");
                VALUES("flag", "'EXIT'");
//                if (!StringUtils.isEmpty(BondInvtBsc.get("invt_no"))) {
//                    VALUES("invt_no", "#{BondInvtBsc.invt_no}");
//                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("id"))) {
                    VALUES("id", "#{BondInvtBsc.id}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("bond_invt_no"))) {
                    VALUES("bond_invt_no", "#{BondInvtBsc.bond_invt_no}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("chg_tms_cnt"))) {
                    VALUES("chg_tms_cnt", "#{BondInvtBsc.chg_tms_cnt}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("invt_preent_no"))) {
                    VALUES("invt_preent_no", "#{BondInvtBsc.invt_preent_no}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("putrec_no"))) {
                    VALUES("putrec_no", "#{BondInvtBsc.putrec_no}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("etps_inner_invt_no"))) {
                    VALUES("etps_inner_invt_no", "#{BondInvtBsc.etps_inner_invt_no}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("bizop_etps_sccd"))) {
                    VALUES("bizop_etps_sccd", "#{BondInvtBsc.bizop_etps_sccd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("bizop_etpsno"))) {
                    VALUES("bizop_etpsno", "#{BondInvtBsc.bizop_etpsno}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("bizop_etps_nm"))) {
                    VALUES("bizop_etps_nm", "#{BondInvtBsc.bizop_etps_nm}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("rvsngd_etps_sccd"))) {
                    VALUES("rvsngd_etps_sccd", "#{BondInvtBsc.rvsngd_etps_sccd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("rcvgd_etpsno"))) {
                    VALUES("rcvgd_etpsno", "#{BondInvtBsc.rcvgd_etpsno}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("rcvgd_etps_nm"))) {
                    VALUES("rcvgd_etps_nm", "#{BondInvtBsc.rcvgd_etps_nm}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("dcl_etps_sccd"))) {
                    VALUES("dcl_etps_sccd", "#{BondInvtBsc.dcl_etps_sccd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("dcl_etpsno"))) {
                    VALUES("dcl_etpsno", "#{BondInvtBsc.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("dcl_etps_nm"))) {
                    VALUES("dcl_etps_nm", "#{BondInvtBsc.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("invt_dcl_time"))) {
                    VALUES("invt_dcl_time", "#{BondInvtBsc.invt_dcl_time}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("entry_dcl_time"))) {
                    VALUES("entry_dcl_time", "#{BondInvtBsc.entry_dcl_time}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("entry_no"))) {
                    VALUES("entry_no", "#{BondInvtBsc.entry_no}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("rlt_invt_no"))) {
                    VALUES("rlt_invt_no", "#{BondInvtBsc.rlt_invt_no}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("rlt_putrec_no"))) {
                    VALUES("rlt_putrec_no", "#{BondInvtBsc.rlt_putrec_no}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_no"))) {
                    VALUES("rlt_entry_no", "#{BondInvtBsc.rlt_entry_no}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_bizop_etps_sccd"))) {
                    VALUES("rlt_entry_bizop_etps_sccd", "#{BondInvtBsc.rlt_entry_bizop_etps_sccd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_bizop_etpsno"))) {
                    VALUES("rlt_entry_bizop_etpsno", "#{BondInvtBsc.rlt_entry_bizop_etpsno}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("rlt_entry_bizop_etps_nm"))) {
                    VALUES("rlt_entry_bizop_etps_nm", "#{BondInvtBsc.rlt_entry_bizop_etps_nm}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("impexp_portcd"))) {
                    VALUES("impexp_portcd", "#{BondInvtBsc.impexp_portcd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("dcl_plc_cuscd"))) {
                    VALUES("dcl_plc_cuscd", "#{BondInvtBsc.dcl_plc_cuscd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("impexp_markcd"))) {
                    VALUES("impexp_markcd", "#{BondInvtBsc.impexp_markcd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("mtpck_endprd_markcd"))) {
                    VALUES("mtpck_endprd_markcd", "#{BondInvtBsc.mtpck_endprd_markcd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("supv_modecd"))) {
                    VALUES("supv_modecd", "#{BondInvtBsc.supv_modecd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("trsp_modecd"))) {
                    VALUES("trsp_modecd", "#{BondInvtBsc.trsp_modecd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("apply_no"))) {
                    VALUES("apply_no", "#{BondInvtBsc.apply_no}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("stship_trsarv_natcd"))) {
                    VALUES("stship_trsarv_natcd", "#{BondInvtBsc.stship_trsarv_natcd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("dclcus_flag"))) {
                    VALUES("dclcus_flag", "#{BondInvtBsc.dclcus_flag}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("dclcus_typecd"))) {
                    VALUES("dclcus_typecd", "#{BondInvtBsc.dclcus_typecd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("prevd_time"))) {
                    VALUES("prevd_time", "#{BondInvtBsc.prevd_time}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("formal_vrfded_time"))) {
                    VALUES("formal_vrfded_time", "#{BondInvtBsc.formal_vrfded_time}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("invt_iochkpt_stucd"))) {
                    VALUES("invt_iochkpt_stucd", "#{BondInvtBsc.invt_iochkpt_stucd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("vrfded_markcd"))) {
                    VALUES("vrfded_markcd", "#{BondInvtBsc.vrfded_markcd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("invt_stucd"))) {
                    VALUES("invt_stucd", "#{BondInvtBsc.invt_stucd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("vrfded_modecd"))) {
                    VALUES("vrfded_modecd", "#{BondInvtBsc.vrfded_modecd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("du_code"))) {
                    VALUES("du_code", "#{BondInvtBsc.du_code}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("rmk"))) {
                    VALUES("rmk", "#{BondInvtBsc.rmk}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("bond_invt_typecd"))) {
                    VALUES("bond_invt_typecd", "#{BondInvtBsc.bond_invt_typecd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("entry_stucd"))) {
                    VALUES("entry_stucd", "#{BondInvtBsc.entry_stucd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("passport_used_typecd"))) {
                    VALUES("passport_used_typecd", "#{BondInvtBsc.passport_used_typecd}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("param1"))) {
                    VALUES("param1", "#{BondInvtBsc.param1}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("param2"))) {
                    VALUES("param2", "#{BondInvtBsc.param2}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("param3"))) {
                    VALUES("param3", "#{BondInvtBsc.param3}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("param4"))) {
                    VALUES("param4", "#{BondInvtBsc.param4}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("need_entry_modified"))) {
                    VALUES("need_entry_modified", "#{BondInvtBsc.need_entry_modified}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("levy_bl_amt"))) {
                    VALUES("levy_bl_amt", "#{BondInvtBsc.levy_bl_amt}");
                }
                if (!StringUtils.isEmpty(BondInvtBsc.get("dcl_typecd"))) {
                    VALUES("dcl_typecd", "#{BondInvtBsc.dcl_typecd}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("status", "'BDDS2'");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("crt_time", "sysdate");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("crt_user", "#{userInfo.id}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("upd_time", "sysdate");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("upd_user", "#{userInfo.id}");
                }
                if (!StringUtils.isEmpty(userInfo.getEnt_Id())) {
                    VALUES("crt_ent_id", "#{userInfo.ent_Id}");
                }
                if (!StringUtils.isEmpty(userInfo.getEnt_Name())) {
                    VALUES("crt_ent_name", "#{userInfo.ent_Name}");
                }
            }
        }.toString();
    }

    public String saveNemsInvtCbecBillType(
            @Param("nemsInvtCbecBillType") NemsInvtCbecBillType nemsInvtCbecBillType,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_NEMS_INVT_CBEC_BILL_TYPE");
                if (!StringUtils.isEmpty(nemsInvtCbecBillType.getId())) {
                    VALUES("id", "#{nemsInvtCbecBillType.id}");
                }
                if (!StringUtils.isEmpty(nemsInvtCbecBillType.getNo())) {
                    VALUES("no", "#{nemsInvtCbecBillType.no}");
                }
                if (!StringUtils.isEmpty(nemsInvtCbecBillType.getSeq_no())) {
                    VALUES("seq_no", "#{nemsInvtCbecBillType.seq_no}");
                }
                if (!StringUtils.isEmpty(nemsInvtCbecBillType.getBond_invt_no())) {
                    VALUES("bond_invt_no", "#{nemsInvtCbecBillType.bond_invt_no}");
                }
                if (!StringUtils.isEmpty(nemsInvtCbecBillType.getBill_no())) {
                    VALUES("BILL_NO", "#{nemsInvtCbecBillType.bill_no}");
                }
                if (!StringUtils.isEmpty(nemsInvtCbecBillType.getCbec_bill_no())) {
                    VALUES("cbec_bill_no", "#{nemsInvtCbecBillType.cbec_bill_no}");
                }
                if (!StringUtils.isEmpty(nemsInvtCbecBillType.getHead_etps_inner_invt_no())) {
                    VALUES("head_etps_inner_invt_no", "#{nemsInvtCbecBillType.head_etps_inner_invt_no}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("crt_time", "sysdate");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("crt_user", "#{userInfo.id}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("upd_time", "sysdate");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("upd_user", "#{userInfo.id}");
                }
            }
        }.toString();
    }

}
