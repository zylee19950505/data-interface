package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class EEmptyPassportSQLProvider {

    //查询申报中的数据（待生成报文数据）
    public String findWaitGenerated(Map<String, String> paramMap) {
        final String status = paramMap.get("status");

        return new SQL() {
            {
                SELECT("*");
                FROM("T_PASS_PORT_HEAD t");
                WHERE("t.status = #{status}");
            }
        }.toString();
    }

    //修改申报中数据为已申报
    public String updatePassportStatus(@Param("etpsPreentNo") String etpsPreentNo, @Param("status") String status) {
        return new SQL() {
            {
                UPDATE("T_PASS_PORT_HEAD t");
                SET("t.STATUS = #{status}");
                WHERE("t.ETPS_PREENT_NO = #{etpsPreentNo}");
            }
        }.toString();
    }

}
