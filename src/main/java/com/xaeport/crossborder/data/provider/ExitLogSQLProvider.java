package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class ExitLogSQLProvider extends BaseSQLProvider{

    public String getLogicDataByExitBondInvt(Map<String, String> map) {
        final String etps_inner_invt_no = map.get("etps_inner_invt_no");
        final String data_status = map.get("data_status");
        final String type = map.get("type");
        final String status = map.get("status");
        final String entId = map.get("entId");
        final String roleId = map.get("roleId");
        return new SQL() {
            {
                SELECT("t.id");
                SELECT("t.etps_inner_invt_no");
                SELECT("v.result vs_result");
                FROM("T_BOND_INVT_BSC t");
                LEFT_OUTER_JOIN("T_VERIFY_STATUS v on t.ID = v.CB_HEAD_ID and v.TYPE = #{type}");
                WHERE("t.status = #{data_status}");
                WHERE("v.status = #{status}");
                if(!roleId.equals("admin")){
                    WHERE("t.crt_ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(etps_inner_invt_no)) {
                    WHERE("t.etps_inner_invt_no = #{etps_inner_invt_no}");
                }
                ORDER_BY("t.crt_time desc");
                ORDER_BY("t.etps_inner_invt_no asc");
            }
        }.toString();
    }

}
