package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

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
}
