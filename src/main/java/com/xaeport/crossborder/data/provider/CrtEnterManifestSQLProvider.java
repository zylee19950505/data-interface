package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.PassPortAcmp;
import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.PassPortList;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.LinkedHashMap;
import java.util.Map;

public class CrtEnterManifestSQLProvider extends BaseSQLProvider {


    public String queryCrtEnterManifestList(Map<String, String> paramMap) {
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
                WHERE("t.FLAG = 'ENTER'");
                WHERE("t.USABLE_NM > 0");
                if (!roleId.equals("admin")) {
                    WHERE("t.CRT_ENT_ID = #{entId}");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.RETURN_STATUS = #{dataStatus}");
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

    public String queryCrtEnterManifestCount(Map<String, String> paramMap) {
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
                WHERE("t.FLAG = 'ENTER'");
                if (!roleId.equals("admin")) {
                    WHERE("t.CRT_ENT_ID = #{entId}");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.RETURN_STATUS = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(invtNo)) {
                    WHERE("t.bond_invt_no = #{invtNo}");
                }
            }
        }.toString();
    }

    public String createEnterManifest(@Param("passPortHead") PassPortHead passPortHead) {
        return new SQL() {
            {
                INSERT_INTO("T_PASS_PORT_HEAD t");
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
                if (!StringUtils.isEmpty(passPortHead.getVehicle_wt())) {
                    VALUES("t.vehicle_wt", "#{passPortHead.vehicle_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_frame_wt())) {
                    VALUES("t.vehicle_frame_wt", "#{passPortHead.vehicle_frame_wt}");
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
                VALUES("t.crt_time", "sysdate");
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

    public String queryEnterInvtory(@Param("invtNo") String invtNo) {
        return new SQL() {
            {
                SELECT("t.GDS_SEQNO");
                SELECT("t.GDS_MTNO");
                SELECT("t.GDECD");
                SELECT("t.GDS_NM");
                SELECT("t.DCL_QTY");
                SELECT("t.GROSS_WT");
                SELECT("t.NET_WT");
                FROM("T_BOND_INVT_DT t");
                WHERE("t.HEAD_ETPS_INNER_INVT_NO = (select ETPS_INNER_INVT_NO from T_BOND_INVT_BSC where BOND_INVT_NO = #{invtNo})");
            }
        }.toString();
    }


    public String updateEnterInventory(Map<String, String> paramMap) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE("t.BOND_INVT_NO = #{invtNo}");
                SET("t.USABLE_NM = 0");
                SET("t.UPD_USER = #{upd_user}");
                SET("t.UPD_TIME = sysdate");
            }
        }.toString();
    }

    public String updateEnterInventoryMoreCar(Map<String, String> paramMap) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE("t.BOND_INVT_NO = #{invtNo}");
                SET("t.USABLE_NM = t.USABLE_NM - #{editBoundNm}");
                SET("t.UPD_USER = #{upd_user}");
                SET("t.UPD_TIME = sysdate");
            }
        }.toString();
    }

    /**
     * 查询核放单表体
     */
    public String queryInventoryList(@Param("invtNo") String invtNo) {
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_BOND_INVT_DT t");
                WHERE("exists(select ETPS_INNER_INVT_NO from T_BOND_INVT_BSC where BOND_INVT_NO = #{invtNo} and ETPS_INNER_INVT_NO = t.HEAD_ETPS_INNER_INVT_NO)");
            }
        }.toString();
    }

    /**
     * 创建关联单
     */
    public String createPassPortAcmp(@Param("passPortAcmp") PassPortAcmp passPortAcmp) {
        return new SQL() {
            {
                INSERT_INTO("T_PASS_PORT_ACMP t");
                if (!StringUtils.isEmpty(passPortAcmp.getId())) {
                    VALUES("t.id", "#{passPortAcmp.id}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getPassport_no())) {
                    VALUES("t.passport_no", "#{passPortAcmp.passport_no}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getRtl_tb_typecd())) {
                    VALUES("t.rtl_tb_typecd", "#{passPortAcmp.rtl_tb_typecd}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getRtl_no())) {
                    VALUES("t.rtl_no", "#{passPortAcmp.rtl_no}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getHead_etps_preent_no())) {
                    VALUES("t.head_etps_preent_no", "#{passPortAcmp.head_etps_preent_no}");
                }
                if (!StringUtils.isEmpty(passPortAcmp.getCrt_user())) {
                    VALUES("t.crt_user", "#{passPortAcmp.crt_user}");
                    VALUES("t.crt_time", "sysdate");
                }
            }
        }.toString();
    }

    /**
     * 查询一车一单和一车多单的表头信息
     */
    public String queryEnterManifestOneCar(Map<String, String> paramMap) {
        return new SQL() {
            {
                SELECT("t.ETPS_PREENT_NO");
                SELECT("t.ID");
                SELECT("t.BOND_INVT_NO");
                SELECT("t.MASTER_CUSCD");
                SELECT("t.BIND_TYPECD");
                SELECT("t.TOTAL_WT");
                SELECT("t.TOTAL_GROSS_WT");
                SELECT("t.TOTAL_NET_WT");
                SELECT("t.DCL_ETPSNO");
                SELECT("t.DCL_ETPS_NM");
                SELECT("t.INPUT_CODE");
                SELECT("t.INPUT_NAME");
                SELECT("t.AREAIN_ETPSNO");
                SELECT("t.AREAIN_ETPS_NM");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.BOND_INVT_NO = #{bond_invt_no}");
                WHERE("t.ETPS_PREENT_NO = #{etps_preent_no}");
            }
        }.toString();
    }

    /**
     * 一车一单和一车多单时的修改核放单头信息
     */
    public String updateEnterManifestDetailOneCar(@Param("passPortHead") PassPortHead passPortHead) {
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_HEAD t");
                WHERE("t.BOND_INVT_NO = #{passPortHead.bond_invt_no}");
                WHERE("t.ETPS_PREENT_NO = #{passPortHead.etps_preent_no}");
                if (!StringUtils.isEmpty(passPortHead.getMaster_cuscd())) {
                    SET("t.master_cuscd = #{passPortHead.master_cuscd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getBind_typecd())) {
                    SET("t.bind_typecd = #{passPortHead.bind_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getAreain_etpsno())) {
                    SET("t.areain_etpsno = #{passPortHead.areain_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.getAreain_etps_nm())) {
                    SET("t.areain_etps_nm = #{passPortHead.areain_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_no())) {
                    SET("t.vehicle_no = #{passPortHead.vehicle_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_wt())) {
                    SET("t.vehicle_wt = #{passPortHead.vehicle_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_frame_wt())) {
                    SET("t.vehicle_frame_wt = #{passPortHead.vehicle_frame_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getContainer_type())) {
                    SET("t.container_type = #{passPortHead.container_type}");
                }
                if (!StringUtils.isEmpty(passPortHead.getContainer_wt())) {
                    SET("t.container_wt = #{passPortHead.container_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_wt())) {
                    SET("t.total_wt = #{passPortHead.total_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_gross_wt())) {
                    SET("t.total_gross_wt = #{passPortHead.total_gross_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getTotal_net_wt())) {
                    SET("t.total_net_wt = #{passPortHead.total_net_wt}");
                }
                if (!StringUtils.isEmpty(passPortHead.getPassport_typecd())) {
                    SET("t.passport_typecd = #{passPortHead.passport_typecd}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_er_conc())) {
                    SET("t.dcl_er_conc = #{passPortHead.dcl_er_conc}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etpsno())) {
                    SET("t.dcl_etpsno = #{passPortHead.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(passPortHead.getDcl_etps_nm())) {
                    SET("t.dcl_etps_nm = #{passPortHead.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(passPortHead.getVehicle_ic_no())) {
                    SET("t.vehicle_ic_no = #{passPortHead.vehicle_ic_no}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_code())) {
                    SET("t.input_code = #{passPortHead.input_code}");
                }
                if (!StringUtils.isEmpty(passPortHead.getInput_name())) {
                    SET("t.input_name = #{passPortHead.input_name}");
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

    /**
     * 插入核放单表体信息
     */
    public String createEnterManifestList(@Param("passPortList") PassPortList passPortList) {
        return new SQL() {
            {
                INSERT_INTO("T_PASS_PORT_LIST t");
                VALUES("t.id", "#{passPortList.id}");
                if (!StringUtils.isEmpty(passPortList.getHead_id())) {
                    VALUES("t.head_id", "#{passPortList.head_id}");
                }
                if (!StringUtils.isEmpty(String.valueOf(passPortList.getPassPort_seqNo()))) {
                    VALUES("t.passPort_seqNo", "#{passPortList.passPort_seqNo}");
                }
                if (!StringUtils.isEmpty(passPortList.getGds_mtNo())) {
                    VALUES("t.gds_mtNo", "#{passPortList.gds_mtNo}");
                }
                if (!StringUtils.isEmpty(passPortList.getGdecd())) {
                    VALUES("t.gdecd", "#{passPortList.gdecd}");
                }
                if (!StringUtils.isEmpty(passPortList.getGds_nm())) {
                    VALUES("t.gds_nm", "#{passPortList.gds_nm}");
                }
                if (!StringUtils.isEmpty(String.valueOf(passPortList.getGross_wt()))) {
                    VALUES("t.gross_wt", "#{passPortList.gross_wt}");
                }
                if (!StringUtils.isEmpty(String.valueOf(passPortList.getNet_wt()))) {
                    VALUES("t.net_wt", "#{passPortList.net_wt}");
                }
                if (!StringUtils.isEmpty(passPortList.getDcl_unitcd())) {
                    VALUES("t.dcl_unitcd", "#{passPortList.dcl_unitcd}");
                }
                if (!StringUtils.isEmpty(String.valueOf(passPortList.getDcl_qty()))) {
                    VALUES("t.dcl_qty", "#{passPortList.dcl_qty}");
                }
            }
        }.toString();
    }


    public String querySelectBondDtList(Map<String, String> paramMap) {
        final String gdsMtno = paramMap.get("selectGdsmtno");
        final String gdsNm = paramMap.get("selectGdsnm");

        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_BOND_INVT_DT t");
                WHERE("exists(select ETPS_INNER_INVT_NO from T_BOND_INVT_BSC where BOND_INVT_NO = #{bond_invt_no} and ETPS_INNER_INVT_NO = t.HEAD_ETPS_INNER_INVT_NO)");

                if (!StringUtils.isEmpty(gdsMtno) && (!StringUtils.isEmpty(gdsNm))) {
                    WHERE("(" + splitJointIn("t.GDS_MTNO", gdsMtno) + " or " + splitJointIn("t.GDS_NM", gdsNm) + ")");
                }
                if (!StringUtils.isEmpty(gdsMtno) && StringUtils.isEmpty(gdsNm)) {
                    WHERE(splitJointIn("t.GDS_MTNO", gdsMtno));
                }
                if (StringUtils.isEmpty(gdsMtno) && !StringUtils.isEmpty(gdsNm)) {
                    WHERE(splitJointIn("t.GDS_NM", gdsNm));
                }
            }
        }.toString();
    }


    /**
     * 一票多车更新表头数据
     */
    public String updatePassPortHead(LinkedHashMap<String, String> entryHead) {
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_HEAD t");
                WHERE("t.ETPS_PREENT_NO = #{etps_preent_no}");
                WHERE("t.BOND_INVT_NO = #{bond_invt_no}");
                if (!StringUtils.isEmpty(entryHead.get("container_type"))) {
                    SET("t.container_type = #{container_type}");
                }
                if (!StringUtils.isEmpty(entryHead.get("areain_etpsno"))) {
                    SET("t.areain_etpsno = #{areain_etpsno}");
                }
                if (!StringUtils.isEmpty(entryHead.get("areain_etps_nm"))) {
                    SET("t.areain_etps_nm = #{areain_etps_nm}");
                }
                if (!StringUtils.isEmpty(entryHead.get("vehicle_no"))) {
                    SET("t.vehicle_no = #{vehicle_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("vehicle_wt"))) {
                    SET("t.vehicle_wt = #{vehicle_wt}");
                }
                if (!StringUtils.isEmpty(entryHead.get("vehicle_frame_wt"))) {
                    SET("t.vehicle_frame_wt = #{vehicle_frame_wt}");
                }
                if (!StringUtils.isEmpty(entryHead.get("container_wt"))) {
                    SET("t.container_wt = #{container_wt}");
                }
                if (!StringUtils.isEmpty(entryHead.get("total_wt"))) {
                    SET("t.total_wt = #{total_wt}");
                }
                if (!StringUtils.isEmpty(entryHead.get("total_gross_wt"))) {
                    SET("t.total_gross_wt = #{total_gross_wt}");
                }
                if (!StringUtils.isEmpty(entryHead.get("total_net_wt"))) {
                    SET("t.total_net_wt = #{total_net_wt}");
                }
                if (!StringUtils.isEmpty(entryHead.get("passport_typecd"))) {
                    SET("t.passport_typecd = #{passport_typecd}");
                }
                if (!StringUtils.isEmpty(entryHead.get("dcl_er_conc"))) {
                    SET("t.dcl_er_conc = #{dcl_er_conc}");
                }
                if (!StringUtils.isEmpty(entryHead.get("dcl_etpsno"))) {
                    SET("t.dcl_etpsno = #{dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(entryHead.get("dcl_etps_nm"))) {
                    SET("t.dcl_etps_nm = #{dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(entryHead.get("dcl_etpsno"))) {
                    SET("t.input_code = #{dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(entryHead.get("dcl_etps_nm"))) {
                    SET("t.input_name = #{dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(entryHead.get("vehicle_ic_no"))) {
                    SET("t.vehicle_ic_no = #{vehicle_ic_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("rmk"))) {
                    SET("t.rmk = #{rmk}");
                }
                if (!StringUtils.isEmpty(entryHead.get("upd_user"))) {
                    SET("t.upd_user = #{upd_user}");
                }
                if (!StringUtils.isEmpty(entryHead.get("upd_user"))) {
                    SET("t.upd_time = sysdate");
                }
            }
        }.toString();
    }

    /**
     * 创建核放单表体数据
     */
    public String crtPassPortList(LinkedHashMap<String, String> entryList) {
        return new SQL() {
            {
                INSERT_INTO("T_PASS_PORT_LIST t");
                VALUES("t.ID", "#{passPortListId}");
                VALUES("t.HEAD_ID", "#{head_id}");
                VALUES("t.GDS_MTNO", "#{gds_mtno}");
                VALUES("t.GDECD", "#{gdecd}");
                VALUES("t.GDS_NM", "#{gds_nm}");
                VALUES("t.GROSS_WT", "#{gross_wt}");
                VALUES("t.NET_WT", "#{net_wt}");
                VALUES("t.DCL_UNITCD", "#{crt_user}");
                VALUES("t.DCL_QTY", "#{surplus_nm}");
                VALUES("t.BOND_INVT_NO", "#{bond_invt_no}");
            }
        }.toString();
    }

    public String updateEnterBondInvtDt(LinkedHashMap<String, String> entryList) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_DT t");
                WHERE("t.ID = #{id}");
                SET("t.SURPLUS_NM = t.SURPLUS_NM - #{surplus_nm}");
            }
        }.toString();
    }

    public String querypassPortListNm(@Param("bond_invt_no") String bond_invt_no) {
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_PASS_PORT_LIST t");
                WHERE("t.BOND_INVT_NO = #{bond_invt_no}");
            }
        }.toString();
    }

    public String queryBondBscNm(@Param("bond_invt_no") String bond_invt_no) {
        return new SQL() {
            {
                SELECT("t.ORIGINAL_NM");
                FROM("T_BOND_INVT_BSC t");
                WHERE("t.BOND_INVT_NO = #{bond_invt_no}");
            }
        }.toString();
    }

    public String queryBondDtList(@Param("bond_invt_no") String bond_invt_no) {
        return new SQL() {
            {
                SELECT("t.GROSS_WT");
                SELECT("t.NET_WT");
                SELECT("t.DCL_QTY");
                FROM("T_BOND_INVT_DT t");
                WHERE("exists(select ETPS_INNER_INVT_NO from T_BOND_INVT_BSC where BOND_INVT_NO = #{bond_invt_no} and ETPS_INNER_INVT_NO = t.HEAD_ETPS_INNER_INVT_NO)");
            }
        }.toString();
    }

    /**
     * 取消核放单
     */
    public String canelEnterManifestDetail(Map<String, String> paramMap) {
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_HEAD t");
                WHERE("t.ETPS_PREENT_NO = #{etps_preent_no}");
                WHERE("t.BOND_INVT_NO = #{bond_invt_no}");
                SET("t.STATUS = #{status}");
            }
        }.toString();
    }

    /**
     * 查询申报企业信息
     */
    public String queryDclEtpsMsg(@Param("invtNo") String invtNo) {
        return new SQL() {
            {
                SELECT("t.DCL_ETPSNO");
                SELECT("t.DCL_ETPS_NM");
                FROM("T_BOND_INVT_BSC t");
                WHERE("t.BOND_INVT_NO = #{invtNo}");
            }
        }.toString();
    }
}
