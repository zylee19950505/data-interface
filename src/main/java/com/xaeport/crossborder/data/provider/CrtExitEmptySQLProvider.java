package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.PassPortHead;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class CrtExitEmptySQLProvider extends BaseSQLProvider {

    public String querExitEmptyPassportList(Map<String, String> paramMap) {

        final String vehicle_no = paramMap.get("vehicle_no");
        final String start = paramMap.get("start");
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT " +
                        "t.ID," +
                        "t.PASSPORT_NO," +
                        "t.ETPS_PREENT_NO," +
                        "t.BOND_INVT_NO," +
                        "t.VEHICLE_NO," +
                        "t.STATUS," +
                        "t.CRT_TIME");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.FLAG = 'EXIT'");
                if (!roleId.equals("admin")) {
                    WHERE("t.CRT_ENT_ID = #{entId}");
                }
                if (!StringUtils.isEmpty(vehicle_no)) {
                    WHERE("t.VEHICLE_NO = #{vehicle_no}");
                }
                WHERE("t.PASSPORT_TYPECD = #{passport_typecd}");
                if (!"-1".equals(length)) {
                    ORDER_BY("t.crt_time desc ) f  )  WHERE rn between #{start} and #{length}");
                } else {
                    ORDER_BY("t.crt_time desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    public String queryExitEmptyPassportCount(Map<String, String> paramMap) {

        final String vehicle_no = paramMap.get("vehicle_no");
        final String start = paramMap.get("start");
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        return new SQL() {
            {
                SELECT("count(1) count");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.FLAG = 'EXIT'");
                if (!roleId.equals("admin")) {
                    WHERE("t.CRT_ENT_ID = #{entId}");
                }
                if (!StringUtils.isEmpty(vehicle_no)) {
                    WHERE("t.VEHICLE_NO = #{vehicle_no}");
                }
                WHERE("t.PASSPORT_TYPECD = #{passport_typecd}");
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

    public String saveExitEmptyInfo(@Param("passPortHead") PassPortHead passPortHead) {
        return new SQL() {
            {
                INSERT_INTO("T_PASS_PORT_HEAD t");
                VALUES("t.crt_time", "sysdate");
                if (!StringUtils.isEmpty(passPortHead.getBusiness_type())) {
                    VALUES("t.business_type", "#{passPortHead.business_type}");
                }
                if (!StringUtils.isEmpty(passPortHead.getId())) {
                    VALUES("t.id", "#{passPortHead.id}");
                }
                if (!StringUtils.isEmpty(passPortHead.getEtps_preent_no())) {
                    VALUES("t.etps_preent_no", "#{passPortHead.etps_preent_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getBond_invt_no())) {
                    VALUES("t.bond_invt_no", "#{passPortHead.bond_invt_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getMaster_cuscd())) {
                    VALUES("t.master_cuscd", "#{passPortHead.master_cuscd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getBind_typecd())) {
                    VALUES("t.bind_typecd", "#{passPortHead.bind_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getAreain_etpsno())) {
                    VALUES("t.areain_etpsno", "#{passPortHead.areain_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.getAreain_etps_nm())) {
                    VALUES("t.areain_etps_nm", "#{passPortHead.areain_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.getAreain_oriact_no())) {
                    VALUES("t.areain_oriact_no", "#{passPortHead.areain_oriact_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_no())) {
                    VALUES("t.vehicle_no", "#{passPortHead.vehicle_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_ic_no())) {
                    VALUES("t.vehicle_ic_no", "#{passPortHead.vehicle_ic_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_wt())) {
                    VALUES("t.vehicle_wt", "#{passPortHead.vehicle_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_frame_wt())) {
                    VALUES("t.vehicle_frame_wt", "#{passPortHead.vehicle_frame_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getContainer_type())) {
                    VALUES("t.container_type", "#{passPortHead.container_type}");
                }
                if (!StringUtils.isEmpty(passPortHead.getContainer_wt())) {
                    VALUES("t.container_wt", "#{passPortHead.container_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getRlt_tb_typecd())) {
                    VALUES("t.rlt_tb_typecd", "#{passPortHead.rlt_tb_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getPassport_typecd())) {
                    VALUES("t.passport_typecd", "#{passPortHead.passport_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getRlt_no())) {
                    VALUES("t.rlt_no", "#{passPortHead.rlt_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etpsno())) {
                    VALUES("t.dcl_etpsno", "#{passPortHead.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etps_nm())) {
                    VALUES("t.dcl_etps_nm", "#{passPortHead.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_er_conc())) {
                    VALUES("t.dcl_er_conc", "#{passPortHead.dcl_er_conc}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_code())) {
                    VALUES("t.input_code", "#{passPortHead.input_code}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_name())) {
                    VALUES("t.input_name", "#{passPortHead.input_name}");
                }
                if (!StringUtils.isEmpty(passPortHead.getCrt_user())) {
                    VALUES("t.crt_user", "#{passPortHead.crt_user}");
                }
                if (!StringUtils.isEmpty(passPortHead.getCrt_ent_id())) {
                    VALUES("t.crt_ent_id", "#{passPortHead.crt_ent_id}");
                }
                if (!StringUtils.isEmpty(passPortHead.getCrt_ent_name())) {
                    VALUES("t.crt_ent_name", "#{passPortHead.crt_ent_name}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_wt())) {
                    VALUES("t.total_wt", "#{passPortHead.total_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_gross_wt())) {
                    VALUES("t.total_gross_wt", "#{passPortHead.total_gross_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_net_wt())) {
                    VALUES("t.total_net_wt", "#{passPortHead.total_net_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_typecd())) {
                    VALUES("t.dcl_typecd", "#{passPortHead.dcl_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getIo_typecd())) {
                    VALUES("t.io_typecd", "#{passPortHead.io_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getFlag())) {
                    VALUES("t.flag", "#{passPortHead.flag}");
                }
                if (!StringUtils.isEmpty(passPortHead.getStatus())) {
                    VALUES("t.status", "#{passPortHead.status}");
                }
            }
        }.toString();
    }

    public String deleteExitEmpty(
            @Param("submitKeys") String submitKeys
    ) {
        return new SQL() {
            {
                DELETE_FROM("T_PASS_PORT_HEAD t");
                WHERE(splitJointIn("ETPS_PREENT_NO", submitKeys));
            }
        }.toString();
    }

    public String updatePassport(@Param("passPortHead") PassPortHead passPortHead) {
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_HEAD t");
                WHERE("t.ETPS_PREENT_NO = #{passPortHead.etps_preent_no}");
                if (!StringUtils.isEmpty(passPortHead.getVehicle_no())) {
                    SET("t.vehicle_no = #{passPortHead.vehicle_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_ic_no())) {
                    SET("t.vehicle_ic_no = #{passPortHead.vehicle_ic_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_frame_wt())) {
                    SET("t.vehicle_frame_wt = #{passPortHead.vehicle_frame_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_wt())) {
                    SET("t.vehicle_wt = #{passPortHead.vehicle_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getContainer_type())) {
                    SET("t.container_type = #{passPortHead.container_type}");
                }
                if (!StringUtils.isEmpty(passPortHead.getContainer_wt())) {
                    SET("t.container_wt = #{passPortHead.container_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_gross_wt())) {
                    SET("t.total_gross_wt = #{passPortHead.total_gross_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_er_conc())) {
                    SET("t.dcl_er_conc = #{passPortHead.dcl_er_conc}");
                }
                if (!StringUtils.isEmpty(passPortHead.getMaster_cuscd())) {
                    SET("t.master_cuscd = #{passPortHead.master_cuscd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_wt())) {
                    SET("t.total_wt = #{passPortHead.total_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etpsno())) {
                    SET("t.dcl_etpsno = #{passPortHead.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etps_nm())) {
                    SET("t.dcl_etps_nm = #{passPortHead.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_code())) {
                    SET("t.input_code = #{passPortHead.input_code}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_name())) {
                    SET("t.input_name = #{passPortHead.input_name}");
                }
                if (!StringUtils.isEmpty(passPortHead.getAreain_etpsno())) {
                    SET("t.areain_etpsno = #{passPortHead.areain_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.getAreain_etps_nm())) {
                    SET("t.areain_etps_nm = #{passPortHead.areain_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.getUpd_user())) {
                    SET("t.upd_user = #{passPortHead.upd_user}");
                }
                if (!StringUtils.isEmpty(passPortHead.getStatus())) {
                    SET("t.status = #{passPortHead.status}");
                }
                SET("t.upd_time = sysdate");
            }
        }.toString();
    }

}
