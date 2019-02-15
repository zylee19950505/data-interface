package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class EnterInventorySQLProvider extends BaseSQLProvider{


    public String queryEnterInventory(Map<String, String> paramMap){
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnDataStatus = paramMap.get("returnDataStatus");
        final String invtNo = paramMap.get("invtNo");
        final String start = paramMap.get("start");
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        return new SQL(){
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
                WHERE("t.FLAG = 'ENTER'");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.entry_dcl_time >= to_date( #{startFlightTimes} || '00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.status = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(returnDataStatus)) {
                    WHERE("t.return_status = #{return_status}");
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

    public String queryEnterInventoryCount(Map<String, String> paramMap){
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnDataStatus = paramMap.get("returnDataStatus");
        final String invtNo = paramMap.get("invtNo");
        final String start = paramMap.get("start");
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_BOND_INVT_BSC t");
                WHERE("t.FLAG = 'ENTER'");
                if (!roleId.equals("admin")) {
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.entry_dcl_time >= to_date( #{startFlightTimes} || '00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.status = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(returnDataStatus)) {
                    WHERE("t.return_status = #{returnDataStatus}");
                }
                if (!StringUtils.isEmpty(invtNo)) {
                    WHERE("t.bond_invt_no = #{invtNo}");
                }
            }
        }.toString();
    }

    /**
    * 删除
    * */
    public String queryDeleteDataByCode(Map<String, String> paramMap) throws Exception {
        final String etpsInnerInvtNo = paramMap.get("etpsInnerInvtNo");
        final String entId = paramMap.get("entId");
        final String status = paramMap.get("status");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_BOND_INVT_BSC t");
                if (!StringUtils.isEmpty(etpsInnerInvtNo)) {
                    String str = etpsInnerInvtNo.replace(",", "','");
                    WHERE("t.ETPS_INNER_INVT_NO in ('" + str + "')");
                }
                if (!StringUtils.isEmpty(entId)) WHERE("t.CRT_ENT_ID = #{entId}");
                if (!StringUtils.isEmpty(status)) WHERE("t.STATUS = #{status}");
            }
        }.toString();
    }

    /**
     * 提交海关
     */
    public String updateSubmitCustom(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        final String statusWhere = paramMap.get("statusWhere");
        final String status = paramMap.get("status");
        final String userId = paramMap.get("userId");
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC t");
                WHERE(splitJointIn("t.ETPS_INNER_INVT_NO", submitKeys));
                WHERE(splitJointIn("t.STATUS", statusWhere));
                SET("t.STATUS = #{status}");
                SET("t.INVT_DCL_TIME = sysdate");
                SET("t.UPD_TIME = sysdate");
                SET("t.UPD_USER = #{userId}");
            }
        }.toString();
    }
}
