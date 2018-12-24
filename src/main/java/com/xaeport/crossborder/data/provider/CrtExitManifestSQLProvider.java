package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

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
            }
        }.toString();
    }

}
