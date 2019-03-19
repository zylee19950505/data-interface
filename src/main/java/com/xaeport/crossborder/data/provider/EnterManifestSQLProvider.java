package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.PassPortList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class EnterManifestSQLProvider extends BaseSQLProvider {


    public String queryEnterManifest(Map<String, String> paramMap){
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String bond_invt_no = paramMap.get("bond_invt_no");
        final String passport_declareStatus = paramMap.get("passport_declareStatus");
        final String passport_dataStatus = paramMap.get("passport_dataStatus");
        final String passport_no = paramMap.get("passport_no");
        final String start = paramMap.get("start");
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        return new SQL(){
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT " +
                        "t.ID," +
                        "t.SAS_PASSPORT_PREENT_NO," +
                        "t.PASSPORT_NO," +
                        "t.ETPS_PREENT_NO," +
                        "t.BOND_INVT_NO," +
                        "t.BIND_TYPECD," +
                        "t.STATUS," +
                        "t.DCL_TIME," +
                        "t.RETURN_STATUS," +
                        "t.RETURN_DATE," +
                        "t.RETURN_INFO");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.FLAG = 'ENTER'");
                if (!roleId.equals("admin")) {
                    WHERE("t.CRT_ENT_ID = #{entId}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.DCL_TIME >= to_date( #{startFlightTimes} || '00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(bond_invt_no)) {
                    WHERE("t.BOND_INVT_NO = #{BOND_INVT_NO}");
                }
                if (!StringUtils.isEmpty(passport_declareStatus)) {
                    WHERE("t.STATUS = #{passport_declareStatus}");
                }
                if (!StringUtils.isEmpty(passport_dataStatus)) {
                    WHERE("t.RETURN_STATUS = #{passport_dataStatus}");
                }
                if (!StringUtils.isEmpty(passport_no)) {
                    WHERE("t.PASSPORT_NO = #{passport_no}");
                }
                if (!"-1".equals(length)) {
                    ORDER_BY("t.crt_time desc ) f  )  WHERE rn between #{start} and #{length}");
                } else {
                    ORDER_BY("t.crt_time desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }


    public String queryEnterManifestCount(Map<String, String> paramMap){
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String bond_invt_no = paramMap.get("bond_invt_no");
        final String passport_declareStatus = paramMap.get("passport_declareStatus");
        final String passport_dataStatus = paramMap.get("passport_dataStatus");
        final String passport_no = paramMap.get("passport_no");
        final String start = paramMap.get("start");
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        return new SQL(){
            {
                SELECT("COUNT(1)");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.FLAG = 'ENTER'");
                if (!roleId.equals("admin")) {
                    WHERE("t.CRT_ENT_ID = #{entId}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.DCL_TIME >= to_date( #{startFlightTimes} || '00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(bond_invt_no)) {
                    WHERE("t.BOND_INVT_NO = #{BOND_INVT_NO}");
                }
                if (!StringUtils.isEmpty(passport_declareStatus)) {
                    WHERE("t.STATUS = #{passport_declareStatus}");
                }
                if (!StringUtils.isEmpty(passport_dataStatus)) {
                    WHERE("t.RETURN_STATUS = #{passport_dataStatus}");
                }
                if (!StringUtils.isEmpty(passport_no)) {
                    WHERE("t.PASSPORT_NO = #{passport_no}");
                }
            }
        }.toString();
    }

    public String  updateSubmitCustom(Map<String, String> paramMap){
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

    public String queryEnterManifestBindType(Map<String, String> paramMap){
        return new SQL(){
            {
                SELECT("t.BIND_TYPECD");
                SELECT("t.PASSPORT_NO");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.FLAG = 'ENTER'");
                WHERE("t.STATUS = #{status}");
                WHERE("t.ETPS_PREENT_NO = #{etps_preent_no}");
            }
        }.toString();
    }

    /**
     *查找关联单信息里的核注清单号
     * */
    public String queryEnterPassportAcmp(Map<String, String> paramMap){
        return new SQL(){
            {
                SELECT("t.RTL_NO");
                FROM("T_PASS_PORT_ACMP t");
                //WHERE("t.PASSPORT_NO = #{passport_no}");
                WHERE("t.HEAD_ETPS_PREENT_NO = #{etps_preent_no}");
            }
        }.toString();
    }

    /**
     *恢复关联单里核注清单的表头信息
     * */
    public String updateEnterBondInvtBsc(Map<String, String> paramMap){
        final String rtl_no = paramMap.get("rtl_no");
        return new SQL(){
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE(splitJointIn("t.BOND_INVT_NO",rtl_no));
                SET("t.USABLE_NM = t.ORIGINAL_NM");
                SET("t.UPD_USER = #{userId}");
                SET("t.UPD_TIME = sysdate");
            }
        }.toString();
    }

   /**
   * 查找关联单里核注清单的ETPS_INNER_INVT_NO
   * */
   public String queryEnterBondInvtDtID(Map<String, String> paramMap){
       final String rtl_no = paramMap.get("rtl_no");
       return new SQL(){
           {
               SELECT("t.ETPS_INNER_INVT_NO");
               FROM("T_BOND_INVT_BSC t");
               WHERE(splitJointIn("t.BOND_INVT_NO",rtl_no));
           }
       }.toString();
   }

    /**
     *恢复核注清单表体数据
     * */
    public String  updateEnterBondInvtDt(@Param("etps_invt_no") String etps_invt_no){
        return new SQL(){
            {
                UPDATE("T_BOND_INVT_DT t");
                WHERE("t.HEAD_ETPS_INNER_INVT_NO = #{etps_invt_no}");
                SET("t.SURPLUS_NM = t.DCL_QTY");
            }
        }.toString();
    }


    /**
     * 删除关联单信息
     * */
    public String deleteEnterPassportAcmp(Map<String, String> paramMap){
        final String etps_preent_no = paramMap.get("etps_preent_no");
        return new SQL(){
            {
                DELETE_FROM("T_PASS_PORT_ACMP t");
                WHERE("t.HEAD_ETPS_PREENT_NO = #{etps_preent_no}");
                //WHERE("t.PASSPORT_NO = #{passport_no}");
            }
        }.toString();
    }

    /**
     * 删除核放单信息
     * */
    public String  deleteEnterPassportHead(Map<String, String> paramMap){
        final String etps_preent_no = paramMap.get("etps_preent_no");
        return new SQL(){
            {
                DELETE_FROM("T_PASS_PORT_HEAD t");
                WHERE(splitJointIn("t.ETPS_PREENT_NO",etps_preent_no));
            }
        }.toString();
    }

    /**
     * 查找一单多车的表体
     * */
    public String queryEnterPassportList(Map<String, String> paramMap){
        return new SQL(){
            {
                //SELECT("t.PASSPORT_NO");
                SELECT("t.GDS_MTNO");
                SELECT("t.GDS_NM");
                SELECT("t.DCL_QTY");
                SELECT("t.BOND_INVT_NO");
                FROM("T_PASS_PORT_LIST t");
                WHERE("t.HEAD_ID = #{head_id}");
            }
        }.toString();
    }
    /**
     *修改核注清单的表体信息(一票多车情况)
     * */
    public String updateEnterBondInvtDtMoreCar(@Param("passPortList") PassPortList passPortList){
        return new SQL(){
            {
                UPDATE("T_BOND_INVT_DT t");
                WHERE("exists(select ETPS_INNER_INVT_NO from T_BOND_INVT_BSC where BOND_INVT_NO = #{passPortList.bond_invt_no} and ETPS_INNER_INVT_NO = t.HEAD_ETPS_INNER_INVT_NO)");
                WHERE("t.GDS_MTNO = #{passPortList.gds_mtNo}");
                WHERE("t.GDS_NM = #{passPortList.gds_nm}");
                SET("t.SURPLUS_NM = t.SURPLUS_NM + #{passPortList.dcl_qty}");
            }
        }.toString();
    }


    /**
     * 根据核放单去查找核注清单信息
     *
     * */
    public String  queryEnterBondInvtBsc(Map<String, String> paramMap){
        return new SQL(){
            {
                SELECT("t.BOND_INVT_NO");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.ETPS_PREENT_NO = #{etps_preent_no}");
            }
        }.toString();
    }

    /**
    * 修改核注清单表头信息(一票多车情况)
    * */
    public String updateEnterBondInvtBscMoreCar(Map<String, String> paramMap){
        return new SQL(){
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE("t.BOND_INVT_NO = #{bond_invt_no}");
                SET("t.USABLE_NM = t.USABLE_NM + #{dcl_qty}");
            }
        }.toString();
    }

    /**
    * 删除核放单表体
    * */
    public String  deleteEnterPassportList(Map<String, String> paramMap){
        return new SQL(){
            {
                DELETE_FROM("T_PASS_PORT_LIST t");
                WHERE("t.HEAD_ID = #{head_id}");
            }
        }.toString();
    }

    /**
     * 点击查看核放单详情(表头)
     * */
    public String getImpPassportHead(@Param("etps_preent_no") String etps_preent_no){
        return new SQL(){
            {
                SELECT("t.*");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.ETPS_PREENT_NO = #{etps_preent_no}");
            }
        }.toString();
    }

    /**
     * 点击查看核放单详情(表体)
     * */
    public String getImpPassportList(@Param("head_id") String head_id){
        return new SQL(){
            {
                SELECT("t.*");
                FROM("T_PASS_PORT_LIST t");
                WHERE("t.HEAD_ID = #{head_id}");
            }
        }.toString();
    }

    /**
    * 查找有无可生成报文的核放单
    * */
    public String findWaitGenerated(Map<String, String> paramMap){
        return new SQL(){
            {
                SELECT("t.*");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.STATUS = #{status}");
            }
        }.toString();
    }

    /**
     * 修改申报状态为已经申报
     * */
    public String updatePassPortHeadStatus(@Param("etpsPreentNo") String etpsPreentNo, @Param("rqhfdysb") String rqhfdysb){
        return new SQL(){
            {
                UPDATE("T_PASS_PORT_HEAD t");
                SET("t.STATUS = #{rqhfdysb}");
                WHERE("t.ETPS_PREENT_NO = #{etpsPreentNo}");
            }
        }.toString();
    }
    /**
    *一票一车和一车多票(查找关联单证)
    * */
    public String queryPassPortAcmpByHeadNo(@Param("etpsPreentNo") String etpsPreentNo){
        return new SQL(){
            {
                SELECT("t.*");
                FROM("T_PASS_PORT_ACMP t");
                WHERE("t.HEAD_ETPS_PREENT_NO = #{etpsPreentNo}");
            }
        }.toString();
    }

    /**
     * 一票多车(查询核放单表体)
     * */
    public String queryPassPortListByHeadNo(@Param("etpsPreentNo") String etpsPreentNo){
        return new SQL(){
            {
                SELECT("t.*");
                FROM("t.PASSPORT_NO = #{etpsPreentNo}");
                WHERE("T_PASS_PORT_LIST t");
            }
        }.toString();
    }
}
