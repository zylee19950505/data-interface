package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.PassPortAcmp;
import com.xaeport.crossborder.data.entity.PassPortHead;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class CrtEnterManifestSQLProvider extends BaseSQLProvider{


    public String queryCrtEnterManifestList(Map<String, String> paramMap){
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");
        final String recordDataStatus = paramMap.get("recordDataStatus");
        final String invtNo = paramMap.get("invtNo");

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
                        "t.ORIGINAL_NM," +
                        "t.USABLE_NM," +
                        "t.BOUND_NM," +
                        "t.RETURN_INFO");
                FROM("T_BOND_INVT_BSC t");
                WHERE("t.FLAG = 'Enter'");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.status = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(recordDataStatus)) {
                    WHERE("t.return_status = #{recordDataStatus}");
                }
                if (!StringUtils.isEmpty(invtNo)) {
                    WHERE("t.bond_invt_no = #{invtNo}");
                }
                if (!"-1".equals(length)) {
                    ORDER_BY("t.crt_time desc ) f  )  WHERE rn between #{start} and #{length}");
                } else {
                    ORDER_BY("t.crt_time desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    public String queryCrtEnterManifestCount(Map<String, String> paramMap){
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");
        final String recordDataStatus = paramMap.get("recordDataStatus");
        final String invtNo = paramMap.get("invtNo");

        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_BOND_INVT_BSC t");
                WHERE("t.FLAG = 'Enter'");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.status = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(recordDataStatus)) {
                    WHERE("t.return_status = #{recordDataStatus}");
                }
                if (!StringUtils.isEmpty(invtNo)) {
                    WHERE("t.bond_invt_no = #{invtNo}");
                }
            }
        }.toString();
    }

    public String createEnterManifest(@Param("passPortHead") PassPortHead passPortHead){
        return new SQL(){
            {
                INSERT_INTO("T_PASS_PORT_HEAD t");
                if (!StringUtils.isEmpty(passPortHead.getId())){
                    VALUES("t.id","#{passPortHead.id}");
                }

                //预先模拟核放单编号
                if (!StringUtils.isEmpty(passPortHead.getPassport_no())){
                    VALUES("t.passport_no","#{passPortHead.passport_no}");
                }

                if (!StringUtils.isEmpty(passPortHead.getEtps_preent_no())){
                    VALUES("t.etps_preent_no","#{passPortHead.etps_preent_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getBond_invt_no())){
                    VALUES("t.bond_invt_no","#{passPortHead.bond_invt_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getMaster_cuscd())){
                    VALUES("t.master_cuscd","#{passPortHead.master_cuscd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getBind_typecd())){
                    VALUES("t.bind_typecd","#{passPortHead.bind_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getRlt_tb_typecd())){
                    VALUES("t.rlt_tb_typecd","#{passPortHead.rlt_tb_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getRlt_no())){
                    VALUES("t.rlt_no","#{passPortHead.rlt_no}");
                }



                if (!StringUtils.isEmpty(passPortHead.getDcl_etpsno())){
                    VALUES("t.dcl_etpsno","#{passPortHead.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etps_nm())){
                    VALUES("t.dcl_etps_nm","#{passPortHead.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_code())){
                    VALUES("t.input_code","#{passPortHead.input_code}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_name())){
                    VALUES("t.input_name","#{passPortHead.input_name}");
                }
                if (!StringUtils.isEmpty(passPortHead.getCrt_user())){
                    VALUES("t.crt_user","#{passPortHead.crt_user}");
                }
                VALUES("t.crt_time","sysdate");
                if (!StringUtils.isEmpty(passPortHead.getCrt_ent_id())){
                    VALUES("t.crt_ent_id","#{passPortHead.crt_ent_id}");
                }
                if (!StringUtils.isEmpty(passPortHead.getCrt_ent_name())){
                    VALUES("t.crt_ent_name","#{passPortHead.crt_ent_name}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_gross_wt())){
                    VALUES("t.total_gross_wt","#{passPortHead.total_gross_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_net_wt())){
                    VALUES("t.total_net_wt","#{passPortHead.total_net_wt}");
                }

            }
        }.toString();
    }


    public String updateEnterInventory(Map<String, String> paramMap){
        return new SQL(){
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE("t.BOND_INVT_NO = #{invtNo}");
                SET("t.USABLE_NM = 0");
                SET("t.UPD_USER = #{upd_user}");
                SET("t.UPD_TIME = sysdate");
            }
        }.toString();
    }

    /**
    * 查询核放单表体
    * */
    public String queryInventoryList(@Param("invtNo") String invtNo){
        return new SQL(){
            {
                SELECT("t.*");
                FROM("T_BOND_INVT_DT t");
                WHERE("t.HEAD_ETPS_INNER_INVT_NO = (select ETPS_INNER_INVT_NO from T_BOND_INVT_BSC where BOND_INVT_NO = #{invtNo})");
            }
        }.toString();
    }

    /**
    * 创建关联单
    * */
    public String createPassPortAcmp(@Param("passPortAcmp") PassPortAcmp passPortAcmp){
        return new SQL(){
            {
                INSERT_INTO("T_PASS_PORT_ACMP t");
                if (!StringUtils.isEmpty(passPortAcmp.getId())){
                    VALUES("t.id","#{passPortAcmp.id}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getPassport_no())){
                    VALUES("t.passport_no","#{passPortAcmp.passport_no}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getRtl_tb_typecd())){
                    VALUES("t.rtl_tb_typecd","#{passPortAcmp.rtl_tb_typecd}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getRtl_no())){
                    VALUES("t.rtl_no","#{passPortAcmp.rtl_no}");
                }
            }
        }.toString();
    }

    /**
    *查询一车一单和一车多单的表头信息
    * */
    public String queryManifestOneCar(@Param("bond_invt_no") String bond_invt_no){
        return new SQL(){
            {
                SELECT("t.ETPS_PREENT_NO");
                SELECT("t.BOND_INVT_NO");
                SELECT("t.MASTER_CUSCD");
                SELECT("t.BIND_TYPECD");
                SELECT("t.TOTAL_GROSS_WT");
                SELECT("t.TOTAL_NET_WT");
                SELECT("t.DCL_ETPSNO");
                SELECT("t.DCL_ETPS_NM");
                SELECT("t.INPUT_CODE");
                SELECT("t.INPUT_NAME");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.BOND_INVT_NO = #{bond_invt_no}");
            }
        }.toString();
    }

    /**
     *
     * 一车一单和一车多单时的修改核放单头信息
    * */
    public String updateEnterManifestDetailOneCar(@Param("passPortHead") PassPortHead passPortHead){
        return new SQL(){
            {
                UPDATE("T_PASS_PORT_HEAD t");
                WHERE("t.BOND_INVT_NO = #{passPortHead.bond_invt_no}");
                if (!StringUtils.isEmpty(passPortHead.getEtps_preent_no())){
                    SET("t.etps_preent_no = #{passPortHead.etps_preent_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getMaster_cuscd())){
                    SET("t.master_cuscd = #{passPortHead.master_cuscd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getBind_typecd())){
                    SET("t.bind_typecd = #{passPortHead.bind_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getAreain_etpsno())){
                    SET("t.areain_etpsno = #{passPortHead.areain_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.getAreain_etps_nm())){
                    SET("t.areain_etps_nm = #{passPortHead.areain_etps_nm}");
                }



                if (!StringUtils.isEmpty(passPortHead.getVehicle_no())){
                    SET("t.vehicle_no = #{passPortHead.vehicle_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_wt())){
                    SET("t.vehicle_wt = #{passPortHead.vehicle_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_frame_wt())){
                    SET("t.vehicle_frame_wt = #{passPortHead.vehicle_frame_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getContainer_type())){
                    SET("t.container_type = #{passPortHead.container_type}");
                }
                if (!StringUtils.isEmpty(passPortHead.getContainer_wt())){
                    SET("t.container_wt = #{passPortHead.container_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_wt())){
                    SET("t.total_wt = #{passPortHead.total_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_gross_wt())){
                    SET("t.total_gross_wt = #{passPortHead.total_gross_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_net_wt())){
                    SET("t.total_net_wt = #{passPortHead.total_net_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_er_conc())){
                    SET("t.dcl_er_conc = #{passPortHead.dcl_er_conc}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etpsno())){
                    SET("t.dcl_etpsno = #{passPortHead.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etps_nm())){
                    SET("t.dcl_etps_nm = #{passPortHead.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_code())){
                    SET("t.input_code = #{passPortHead.input_code}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_name())){
                    SET("t.input_name = #{passPortHead.input_name}");
                }
                if (!StringUtils.isEmpty(passPortHead.getUpd_user())){
                    SET("t.upd_user = #{passPortHead.upd_user}");
                }
                SET("t.upd_time = sysdate");
            }
        }.toString();
    }
}
