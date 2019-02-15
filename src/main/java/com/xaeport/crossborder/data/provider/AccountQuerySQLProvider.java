package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class AccountQuerySQLProvider extends BaseSQLProvider {

    public String queryBwlListTypeData(Map<String, String> paramMap) {

        final String gds_seqno = paramMap.get("gds_seqno");
        final String gds_mtno = paramMap.get("gds_mtno");
        final String gdecd = paramMap.get("gdecd");
        final String gds_nm = paramMap.get("gds_nm");
        final String entId = paramMap.get("entId");

        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT GDS_SEQNO,GDS_MTNO,GDECD,GDS_NM");
                FROM("T_BWL_LIST_TYPE");
                WHERE("BWS_NO in (SELECT BWS_NO FROM T_BWL_HEAD_TYPE WHERE CRT_ENT_ID = #{entId})");
                if (!StringUtils.isEmpty(gds_seqno)) {
                    WHERE("GDS_SEQNO = #{gds_seqno}");
                }
                if (!StringUtils.isEmpty(gds_mtno)) {
                    WHERE("GDS_MTNO = #{gds_mtno}");
                }
                if (!StringUtils.isEmpty(gdecd)) {
                    WHERE("GDECD = #{gdecd}");
                }
                if (!StringUtils.isEmpty(gds_nm)) {
                    WHERE("GDS_NM = #{gds_nm}");
                }
                ORDER_BY("GDS_SEQNO ) f  )  WHERE rn between #{start} and #{end}");
            }
        }.toString();
    }

    public String queryBwlListTypeCount(Map<String, String> paramMap) {

        final String gds_seqno = paramMap.get("gds_seqno");
        final String gds_mtno = paramMap.get("gds_mtno");
        final String gdecd = paramMap.get("gdecd");
        final String gds_nm = paramMap.get("gds_nm");
        final String entId = paramMap.get("entId");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_BWL_LIST_TYPE");
                WHERE("BWS_NO in (SELECT BWS_NO FROM T_BWL_HEAD_TYPE WHERE CRT_ENT_ID = #{entId})");
                if (!StringUtils.isEmpty(gds_seqno)) {
                    WHERE("GDS_SEQNO = #{gds_seqno}");
                }
                if (!StringUtils.isEmpty(gds_mtno)) {
                    WHERE("GDS_MTNO = #{gds_mtno}");
                }
                if (!StringUtils.isEmpty(gdecd)) {
                    WHERE("GDECD = #{gdecd}");
                }
                if (!StringUtils.isEmpty(gds_nm)) {
                    WHERE("GDS_NM = #{gds_nm}");
                }
            }
        }.toString();
    }

}
