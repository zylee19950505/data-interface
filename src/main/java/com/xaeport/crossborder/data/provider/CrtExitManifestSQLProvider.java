package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class CrtExitManifestSQLProvider {

    //查询出区核注清单数据
    public String queryEInventoryList(Map<String, String> paramMap) throws Exception {

        final String end = paramMap.get("end");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String status = paramMap.get("status");
        final String returnStatus = paramMap.get("returnStatus");
        final String bondInvtNo = paramMap.get("bondInvtNo");

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
                if (!StringUtils.isEmpty(status)) {
                    WHERE("t.status = #{status}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.return_status = #{returnStatus}");
                }
                if (!StringUtils.isEmpty(bondInvtNo)) {
                    WHERE("t.bond_invt_no = #{bondInvtNo}");
                }
                WHERE("t.PASSPORT_USED_TYPECD is null");
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
        final String status = paramMap.get("status");
        final String returnStatus = paramMap.get("returnStatus");
        final String bondInvtNo = paramMap.get("bondInvtNo");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_BOND_INVT_BSC t");
                WHERE("t.FLAG = 'EXIT'");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(status)) {
                    WHERE("t.status = #{status}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.return_status = #{returnStatus}");
                }
                if (!StringUtils.isEmpty(bondInvtNo)) {
                    WHERE("t.bond_invt_no = #{bondInvtNo}");
                }
                WHERE("t.PASSPORT_USED_TYPECD is null");
            }
        }.toString();
    }

    //修改核注清单数据状态
    public String updateBondInvtStatus(
            @Param("passPortHead") LinkedHashMap<String, String> passPortHead
    ) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE("t.BOND_INVT_NO = #{passPortHead.bond_invt_no}");
                SET("t.PASSPORT_USED_TYPECD = '3'");
            }
        }.toString();
    }

    public String savePassPortHead(
            @Param("passPortHead") LinkedHashMap<String, String> passPortHead,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_PASS_PORT_HEAD");
                if (!StringUtils.isEmpty(passPortHead.get("id"))) {
                    VALUES("id", "#{passPortHead.id}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("passport_no"))) {
                    VALUES("passport_no", "#{passPortHead.passport_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("rlt_tb_typecd"))) {
                    VALUES("rlt_tb_typecd", "#{passPortHead.rlt_tb_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("rlt_no"))) {
                    VALUES("rlt_no", "#{passPortHead.rlt_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("dcl_typecd"))) {
                    VALUES("dcl_typecd", "#{passPortHead.dcl_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("master_cuscd"))) {
                    VALUES("master_cuscd", "#{passPortHead.master_cuscd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("dcl_etpsno"))) {
                    VALUES("dcl_etpsno", "#{passPortHead.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("dcl_etps_nm"))) {
                    VALUES("dcl_etps_nm", "#{passPortHead.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("input_code"))) {
                    VALUES("input_code", "#{passPortHead.input_code}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("input_name"))) {
                    VALUES("input_name", "#{passPortHead.input_name}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("areain_oriact_no"))) {
                    VALUES("areain_oriact_no", "#{passPortHead.areain_oriact_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("io_typecd"))) {
                    VALUES("io_typecd", "#{passPortHead.io_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("vehicle_no"))) {
                    VALUES("vehicle_no", "#{passPortHead.vehicle_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("vehicle_wt"))) {
                    VALUES("vehicle_wt", "#{passPortHead.vehicle_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("vehicle_frame_wt"))) {
                    VALUES("vehicle_frame_wt", "#{passPortHead.vehicle_frame_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("container_type"))) {
                    VALUES("container_type", "#{passPortHead.container_type}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("container_wt"))) {
                    VALUES("container_wt", "#{passPortHead.container_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("total_wt"))) {
                    VALUES("total_wt", "#{passPortHead.total_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("passport_typecd"))) {
                    VALUES("passport_typecd", "#{passPortHead.passport_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("bind_typecd"))) {
                    VALUES("bind_typecd", "#{passPortHead.bind_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("areain_etpsno"))) {
                    VALUES("areain_etpsno", "#{passPortHead.areain_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("areain_etps_nm"))) {
                    VALUES("areain_etps_nm", "#{passPortHead.areain_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("dcl_er_conc"))) {
                    VALUES("dcl_er_conc", "#{passPortHead.dcl_er_conc}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("total_gross_wt"))) {
                    VALUES("total_gross_wt", "#{passPortHead.total_gross_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("total_net_wt"))) {
                    VALUES("total_net_wt", "#{passPortHead.total_net_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("vehicle_ic_no"))) {
                    VALUES("vehicle_ic_no", "#{passPortHead.vehicle_ic_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("rmk"))) {
                    VALUES("rmk", "#{passPortHead.rmk}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("etps_preent_no"))) {
                    VALUES("etps_preent_no", "#{passPortHead.etps_preent_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("bond_invt_no"))) {
                    VALUES("bond_invt_no", "#{passPortHead.bond_invt_no}");
                }

                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("status", "'BDDS4'");
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

    public String savePassPortAcmpList(
            @Param("passPortHead") LinkedHashMap<String, String> passPortHead,
            @Param("passPortAcmp") LinkedHashMap<String, String> passPortAcmp,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_PASS_PORT_ACMP");
                if (!StringUtils.isEmpty(passPortAcmp.get("id"))) {
                    VALUES("id", "#{passPortAcmp.id}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.get("head_etps_preent_no"))) {
                    VALUES("head_etps_preent_no", "#{passPortAcmp.head_etps_preent_no}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.get("no"))) {
                    VALUES("no", "#{passPortAcmp.no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("rlt_tb_typecd"))) {
                    VALUES("rtl_tb_typecd", "#{passPortHead.rlt_tb_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("rlt_no"))) {
                    VALUES("rtl_no", "#{passPortHead.rlt_no}");
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
