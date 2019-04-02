package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.DclEtps;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class DeclareEntSQLProvider extends BaseSQLProvider {

    public String queryDclEtpsList(Map<String, String> map) {
        final String dcl_etps_name = map.get("dcl_etps_name");
        final String ent_id = map.get("ent_id");
        final String role_id = map.get("role_id");
        return new SQL() {
            {
                SELECT("*");
                FROM("T_DCL_ETPS t");
                if (!StringUtils.isEmpty(dcl_etps_name)) {
                    WHERE("t.DCL_ETPS_NAME like '%'||#{dcl_etps_name}||'%'");
                }
                if (!"admin".equals(role_id)) {
                    WHERE("t.ENT_ID = #{ent_id}");
                }
                ORDER_BY("DCL_ETPS_NAME");
            }
        }.toString();
    }

    public String deleteDcletps(String id) {
        return new SQL() {
            {
                DELETE_FROM("T_DCL_ETPS t");
                WHERE("t.id = #{id}");
            }
        }.toString();
    }

    public String createDcletps(@Param("dclEtps") DclEtps dclEtps) {
        return new SQL() {
            {
                INSERT_INTO("T_DCL_ETPS");
                if (!StringUtils.isEmpty(dclEtps.getId())) {
                    VALUES("ID", "#{dclEtps.id}");
                }
                if (!StringUtils.isEmpty(dclEtps.getDcl_etps_name())) {
                    VALUES("DCL_ETPS_NAME", "#{dclEtps.dcl_etps_name}");
                }
                if (!StringUtils.isEmpty(dclEtps.getDcl_etps_customs_code())) {
                    VALUES("DCL_ETPS_CUSTOMS_CODE", "#{dclEtps.dcl_etps_customs_code}");
                }
                if (!StringUtils.isEmpty(dclEtps.getDcl_etps_credit_code())) {
                    VALUES("DCL_ETPS_CREDIT_CODE", "#{dclEtps.dcl_etps_credit_code}");
                }
                if (!StringUtils.isEmpty(dclEtps.getDcl_etps_ic_no())) {
                    VALUES("DCL_ETPS_IC_NO", "#{dclEtps.dcl_etps_ic_no}");
                }
                if (!StringUtils.isEmpty(dclEtps.getDcl_etps_port())) {
                    VALUES("DCL_ETPS_PORT", "#{dclEtps.dcl_etps_port}");
                }
                if (!StringUtils.isEmpty(dclEtps.getEnt_id())) {
                    VALUES("ENT_ID", "#{dclEtps.ent_id}");
                }
                if (!StringUtils.isEmpty(dclEtps.getEnt_customs_code())) {
                    VALUES("ENT_CUSTOMS_CODE", "#{dclEtps.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(dclEtps.getCreate_time())) {
                    VALUES("CREATE_TIME", "#{dclEtps.create_time}");
                }
            }
        }.toString();
    }

}
