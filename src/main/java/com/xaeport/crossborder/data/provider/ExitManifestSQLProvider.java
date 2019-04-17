package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExitManifestSQLProvider extends BaseSQLProvider {

    public String queryExitManifestData(Map<String, String> paramMap) {
        final String end = paramMap.get("end");
        final String endId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dcl_time = paramMap.get("dcl_time");
        final String status = paramMap.get("status");
        final String return_status = paramMap.get("return_status");
        final String passport_no = paramMap.get("passport_no");
        final String rlt_no = paramMap.get("rlt_no");

        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        "SELECT " +
                        "t.SAS_PASSPORT_PREENT_NO," +
                        "t.PASSPORT_NO," +
                        "t.RLT_NO," +
                        "t.STATUS," +
                        "t.DCL_TIME," +
                        "t.RETURN_STATUS," +
                        "t.RETURN_DATE," +
                        "t.RETURN_INFO," +
                        "t.ETPS_PREENT_NO," +
                        "t.BOND_INVT_NO");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("FLAG = 'EXIT'");
                if (!roleId.equals("admin")) {
                    WHERE("t.CRT_ENT_ID = #{entId}");
                }
                if (!StringUtils.isEmpty(dcl_time)) {
                    WHERE("t.dcl_time >= to_date(#{dcl_time} || '00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(status)) {
                    WHERE("t.status = #{status}");
                } else {
                    WHERE(splitJointIn("t.status", status));
                }
                if (!StringUtils.isEmpty(return_status)) {
                    WHERE("t.return_status = #{return_status}");
                }
                if (!StringUtils.isEmpty(passport_no)) {
                    WHERE("t.passport_no = #{passport_no}");
                }
                if (!StringUtils.isEmpty(rlt_no)) {
                    WHERE("t.rlt_no = #{rlt_no}");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.crt_time desc ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.crt_time desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    public String queryExitManifestCount(Map<String, String> paramMap) {

        final String endId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dcl_time = paramMap.get("dcl_time");
        final String status = paramMap.get("status");
        final String return_status = paramMap.get("return_status");
        final String passport_no = paramMap.get("passport_no");
        final String rlt_no = paramMap.get("rlt_no");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("FLAG = 'EXIT'");
                if (!roleId.equals("admin")) {
                    WHERE("t.CRT_ENT_ID = #{entId}");
                }
                if (!StringUtils.isEmpty(dcl_time)) {
                    WHERE("t.dcl_time >= to_date(#{dcl_time} || '00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(status)) {
                    WHERE("t.status = #{status}");
                } else {
                    WHERE(splitJointIn("t.status", status));
                }
                if (!StringUtils.isEmpty(return_status)) {
                    WHERE("t.return_status = #{return_status}");
                }
                if (!StringUtils.isEmpty(passport_no)) {
                    WHERE("t.passport_no = #{passport_no}");
                }
                if (!StringUtils.isEmpty(rlt_no)) {
                    WHERE("t.rlt_no = #{rlt_no}");
                }
            }
        }.toString();
    }

    public String updateSubmitCustom(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        final String statusWhere = paramMap.get("statusWhere");
        final String status = paramMap.get("status");
        final String userId = paramMap.get("userId");
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_HEAD t");
                WHERE(splitJointIn("t.ETPS_PREENT_NO", submitKeys));
                WHERE(splitJointIn("t.STATUS", statusWhere));
                SET("t.STATUS = #{status}");
                SET("t.DCL_TIME = sysdate");
                SET("t.UPD_TIME = sysdate");
                SET("t.UPD_USER = #{userId}");
            }
        }.toString();
    }

    public String queryDataFull(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        return new SQL() {
            {
                SELECT("STATUS");
                FROM("T_PASS_PORT_HEAD");
                WHERE(splitJointIn("ETPS_PREENT_NO", submitKeys));
            }
        }.toString();
    }

    public String queryPassPortHeadList(Map<String, String> paramMap) throws Exception {
        final String etps_preent_no = paramMap.get("etps_preent_no");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_PASS_PORT_HEAD");
                if (!StringUtils.isEmpty(etps_preent_no)) {
                    WHERE(splitJointIn("ETPS_PREENT_NO", etps_preent_no));
                }
            }
        }.toString();
    }

    public String queryPassPortAcmpList(Map<String, String> paramMap) throws Exception {
        final String etps_preent_no = paramMap.get("etps_preent_no");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_PASS_PORT_ACMP");
                if (!StringUtils.isEmpty(etps_preent_no)) {
                    WHERE(splitJointIn("HEAD_ETPS_PREENT_NO", etps_preent_no));
                }
            }
        }.toString();
    }

    public String queryPassPortHead(Map<String, String> paramMap) throws Exception {
        final String etps_preent_no = paramMap.get("etps_preent_no");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_PASS_PORT_HEAD");
                if (!StringUtils.isEmpty(etps_preent_no)) {
                    WHERE(splitJointIn("ETPS_PREENT_NO", etps_preent_no));
                }
            }
        }.toString();
    }

    public String queryPassPortAcmp(Map<String, String> paramMap) throws Exception {
        final String etps_preent_no = paramMap.get("etps_preent_no");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_PASS_PORT_ACMP");
                if (!StringUtils.isEmpty(etps_preent_no)) {
                    WHERE(splitJointIn("HEAD_ETPS_PREENT_NO", etps_preent_no));
                }
            }
        }.toString();
    }

    public String updateBondInvtBsc(String BondInvtNos) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE(splitJointIn("BOND_INVT_NO", BondInvtNos));
                SET("t.PASSPORT_USED_TYPECD = ''");
            }
        }.toString();
    }

    public String updatePassPortHead(
            @Param("passPortHead") LinkedHashMap<String, String> passPortHead,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_HEAD t");
                WHERE("ETPS_PREENT_NO = #{passPortHead.etps_preent_no}");
                SET("t.STATUS = 'BDDS4'");
                if (!StringUtils.isEmpty(passPortHead.get("rlt_tb_typecd"))) {
                    SET("rlt_tb_typecd = #{passPortHead.rlt_tb_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("rlt_no"))) {
                    SET("rlt_no = #{passPortHead.rlt_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("dcl_typecd"))) {
                    SET("dcl_typecd = #{passPortHead.dcl_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("master_cuscd"))) {
                    SET("master_cuscd = #{passPortHead.master_cuscd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("dcl_etpsno"))) {
                    SET("dcl_etpsno = #{passPortHead.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("dcl_etps_nm"))) {
                    SET("dcl_etps_nm = #{passPortHead.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("dcl_etpsno"))) {
                    SET("input_code = #{passPortHead.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("dcl_etps_nm"))) {
                    SET("input_name = #{passPortHead.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("areain_oriact_no"))) {
                    SET("areain_oriact_no = #{passPortHead.areain_oriact_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("io_typecd"))) {
                    SET("io_typecd = #{passPortHead.io_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("vehicle_no"))) {
                    SET("vehicle_no = #{passPortHead.vehicle_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("vehicle_wt"))) {
                    SET("vehicle_wt = #{passPortHead.vehicle_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("vehicle_frame_wt"))) {
                    SET("vehicle_frame_wt = #{passPortHead.vehicle_frame_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("container_type"))) {
                    SET("container_type = #{passPortHead.container_type}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("container_wt"))) {
                    SET("container_wt = #{passPortHead.container_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("total_wt"))) {
                    SET("total_wt = #{passPortHead.total_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("passport_typecd"))) {
                    SET("passport_typecd = #{passPortHead.passport_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("bind_typecd"))) {
                    SET("bind_typecd = #{passPortHead.bind_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("areain_etpsno"))) {
                    SET("areain_etpsno = #{passPortHead.areain_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("areain_etps_nm"))) {
                    SET("areain_etps_nm = #{passPortHead.areain_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("dcl_er_conc"))) {
                    SET("dcl_er_conc = #{passPortHead.dcl_er_conc}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("total_gross_wt"))) {
                    SET("total_gross_wt = #{passPortHead.total_gross_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("total_net_wt"))) {
                    SET("total_net_wt = #{passPortHead.total_net_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("vehicle_ic_no"))) {
                    SET("vehicle_ic_no = #{passPortHead.vehicle_ic_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("rmk"))) {
                    SET("rmk = #{passPortHead.rmk}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("upd_time = sysdate");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("upd_user = #{userInfo.id}");
                }
            }
        }.toString();
    }

    public String updatePassPortAcmp(
            @Param("passPortHead") LinkedHashMap<String, String> passPortHead,
            @Param("passPortAcmpList") LinkedHashMap<String, String> passPortAcmpList,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_ACMP");
                WHERE("HEAD_ETPS_PREENT_NO = #{passPortHead.etps_preent_no}");
                if (!StringUtils.isEmpty(passPortAcmpList.get("rlt_tb_typecd"))) {
                    SET("RTL_TB_TYPECD = #{passPortAcmpList.rlt_tb_typecd}");
                }
                if (!StringUtils.isEmpty(passPortAcmpList.get("rlt_no"))) {
                    SET("RTL_NO = #{passPortAcmpList.rlt_no}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("UPD_TIME = sysdate");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("UPD_USER = #{userInfo.id}");
                }
            }
        }.toString();
    }


    public String findWaitGenerated(Map<String, String> paramMap) {
        final String status = paramMap.get("status");
        return new SQL() {
            {
                SELECT("PASSPORT_TYPECD");
                SELECT("MASTER_CUSCD");
                SELECT("DCL_TYPECD");
                SELECT("IO_TYPECD");
                SELECT("BIND_TYPECD");
                SELECT("RLT_TB_TYPECD");
                SELECT("RLT_NO");
                SELECT("AREAIN_ETPSNO");
                SELECT("AREAIN_ETPS_NM");
                SELECT("VEHICLE_NO");
                SELECT("VEHICLE_IC_NO");
                SELECT("VEHICLE_WT");
                SELECT("VEHICLE_FRAME_WT");
                SELECT("CONTAINER_WT");
                SELECT("CONTAINER_TYPE");
                SELECT("TOTAL_WT");
                SELECT("TOTAL_GROSS_WT");
                SELECT("TOTAL_NET_WT");
                SELECT("DCL_TIME");
                SELECT("DCL_ER_CONC");
                SELECT("DCL_ETPSNO");
                SELECT("DCL_ETPS_NM");
                SELECT("INPUT_CODE");
                SELECT("INPUT_NAME");
                SELECT("ETPS_PREENT_NO");
                SELECT("CRT_USER");
                SELECT("CRT_ENT_ID");
                SELECT("CRT_ENT_NAME");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.status = #{status}");
                WHERE("rownum <= 100");
                ORDER_BY("t.CRT_TIME asc,t.ETPS_PREENT_NO asc");
            }
        }.toString();
    }

    public String updatePassPortHeadStatus(@Param("etpsPreentNo") String etpsPreentNo, @Param("status") String status) {
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_HEAD t");
                WHERE("t.ETPS_PREENT_NO = #{etpsPreentNo}");
                SET("t.STATUS = #{status}");
                SET("t.UPD_TIME = sysdate");
            }
        }.toString();
    }

    public String queryPassPortAcmpByHeadNo(@Param("etpsPreentNo") String etpsPreentNo) {

        return new SQL() {
            {
                SELECT("ID");
                SELECT("PASSPORT_NO");
                SELECT("RTL_TB_TYPECD");
                SELECT("RTL_NO");
                SELECT("HEAD_ETPS_PREENT_NO");
                FROM("T_PASS_PORT_ACMP t");
                WHERE("t.HEAD_ETPS_PREENT_NO = #{etpsPreentNo}");
            }
        }.toString();
    }

}
