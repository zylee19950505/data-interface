package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.PassPortAcmp;
import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class CrtExitManifestSQLProvider extends BaseSQLProvider {

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
                    WHERE("t.CRT_ENT_ID = #{entId}");
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

    //查询出区核注清单数据总数
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
                    WHERE("t.CRT_ENT_ID = #{entId}");
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

    public String queryBondInvtBscList(@Param("bond_invt_no") String bond_invt_no) {
        return new SQL() {
            {
                SELECT("*");
                FROM("T_BOND_INVT_BSC");
                WHERE(splitJointIn("BOND_INVT_NO", bond_invt_no));
            }
        }.toString();
    }

    public String queryBondinvtIsRepeat(Map<String, String> map) {
        String submitKeys = map.get("submitKeys");

        return new SQL() {
            {
                SELECT("ETPS_INNER_INVT_NO");
                SELECT("BOND_INVT_NO");
                SELECT("PASSPORT_USED_TYPECD");
                FROM("T_BOND_INVT_BSC");
                WHERE(splitJointIn("BOND_INVT_NO", submitKeys));
            }
        }.toString();
    }

    //更新核注清单表状态
    public String updateBondInvt(
            @Param("bond_invt_no") String bond_invt_no
    ) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE(splitJointIn("t.BOND_INVT_NO ", bond_invt_no));
                SET("t.PASSPORT_USED_TYPECD = '3'");
            }
        }.toString();
    }

    //创建插入出区核放单表头
    public String insertPassPortHead(
            @Param("passPortHead") PassPortHead passPortHead,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_PASS_PORT_HEAD");
                VALUES("status", "'INIT'");
                VALUES("FLAG", "'EXIT'");
                if (!StringUtils.isEmpty(passPortHead.getBusiness_type())) {
                    VALUES("business_type", "#{passPortHead.business_type}");
                }
                if (!StringUtils.isEmpty(passPortHead.getId())) {
                    VALUES("id", "#{passPortHead.id}");
                }
                if (!StringUtils.isEmpty(passPortHead.getAreain_oriact_no())) {
                    VALUES("areain_oriact_no", "#{passPortHead.areain_oriact_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etpsno())) {
                    VALUES("dcl_etpsno", "#{passPortHead.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etps_nm())) {
                    VALUES("dcl_etps_nm", "#{passPortHead.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.getMaster_cuscd())) {
                    VALUES("master_cuscd", "#{passPortHead.master_cuscd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_code())) {
                    VALUES("input_code", "#{passPortHead.input_code}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_name())) {
                    VALUES("input_name", "#{passPortHead.input_name}");
                }
                if (!StringUtils.isEmpty(passPortHead.getEtps_preent_no())) {
                    VALUES("etps_preent_no", "#{passPortHead.etps_preent_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getBond_invt_no())) {
                    VALUES("bond_invt_no", "#{passPortHead.bond_invt_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getRlt_no())) {
                    VALUES("rlt_no", "#{passPortHead.rlt_no}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("CRT_USER", "#{userInfo.id}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("CRT_TIME", "sysdate");
                }
                if (!StringUtils.isEmpty(userInfo.getEnt_Id())) {
                    VALUES("CRT_ENT_ID", "#{userInfo.ent_Id}");
                }
                if (!StringUtils.isEmpty(userInfo.getEnt_Name())) {
                    VALUES("CRT_ENT_NAME", "#{userInfo.ent_Name}");
                }
            }
        }.toString();
    }

    //创建插入出区核放单表体
    public String insertPassPortAcmp(
            @Param("passPortAcmp") PassPortAcmp passPortAcmp,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_PASS_PORT_ACMP");
                if (!StringUtils.isEmpty(passPortAcmp.getId())) {
                    VALUES("id", "#{passPortAcmp.id}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getNo())) {
                    VALUES("no", "#{passPortAcmp.no}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getRtl_tb_typecd())) {
                    VALUES("rtl_tb_typecd", "#{passPortAcmp.rtl_tb_typecd}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getRtl_no())) {
                    VALUES("rtl_no", "#{passPortAcmp.rtl_no}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getHead_etps_preent_no())) {
                    VALUES("head_etps_preent_no", "#{passPortAcmp.head_etps_preent_no}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("CRT_USER", "#{userInfo.id}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    VALUES("CRT_TIME", "sysdate");
                }
            }
        }.toString();
    }

//    //更新核注清单表状态
//    public String updateBondInvtStatus(
//            @Param("passPortHead") LinkedHashMap<String, String> passPortHead
//    ) {
//        return new SQL() {
//            {
//                UPDATE("T_BOND_INVT_BSC t");
//                WHERE(splitJointIn("t.BOND_INVT_NO ", passPortHead.get("bond_invt_no")));
//                SET("t.PASSPORT_USED_TYPECD = '3'");
//            }
//        }.toString();
//    }

    //保存并更新出区核放单表头
    public String savePassPortHead(
            @Param("passPortHead") LinkedHashMap<String, String> passPortHead,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_HEAD");
                if (!StringUtils.isEmpty(passPortHead.get("id"))) {
                    WHERE("id = #{passPortHead.id}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("etps_preent_no"))) {
                    WHERE("etps_preent_no = #{passPortHead.etps_preent_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("passport_no"))) {
                    SET("passport_no = #{passPortHead.passport_no}");
                }
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
                if (!StringUtils.isEmpty(passPortHead.get("bond_invt_no"))) {
                    SET("bond_invt_no = #{passPortHead.bond_invt_no}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("status = 'BDDS4'");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("crt_time = sysdate");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("crt_user = #{userInfo.id}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("upd_time = sysdate");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("upd_user = #{userInfo.id}");
                }
                if (!StringUtils.isEmpty(userInfo.getEnt_Id())) {
                    SET("crt_ent_id = #{userInfo.ent_Id}");
                }
                if (!StringUtils.isEmpty(userInfo.getEnt_Name())) {
                    SET("crt_ent_name = #{userInfo.ent_Name}");
                }
            }
        }.toString();
    }

    //保存并更新出区核放单表体
    public String savePassPortAcmpList(
            @Param("passPortHead") LinkedHashMap<String, String> passPortHead,
            @Param("passPortAcmp") LinkedHashMap<String, String> passPortAcmp,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_ACMP");
                if (!StringUtils.isEmpty(passPortAcmp.get("id"))) {
                    WHERE("id = #{passPortAcmp.id}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.get("head_etps_preent_no"))) {
                    WHERE("head_etps_preent_no = #{passPortAcmp.head_etps_preent_no}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.get("no"))) {
                    WHERE("no = #{passPortAcmp.no}");
                }
                if (!StringUtils.isEmpty(passPortHead.get("rlt_tb_typecd"))) {
                    SET("rtl_tb_typecd = #{passPortHead.rlt_tb_typecd}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.get("rlt_no"))) {
                    SET("rtl_no = #{passPortHead.rlt_no}");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("crt_time = sysdate");
                }
                if (!StringUtils.isEmpty(userInfo.getId())) {
                    SET("crt_user = #{userInfo.id}");
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

}
