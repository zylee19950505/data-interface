package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class StockControlSQLProvider extends BaseSQLProvider{

    public String queryStockControlData(Map<String, String> paramMap) {

        final String gds_seqno = paramMap.get("gds_seqno");
        final String gds_mtno = paramMap.get("gds_mtno");
        final String gdecd = paramMap.get("gdecd");
        final String gds_nm = paramMap.get("gds_nm");
        final String entId = paramMap.get("entId");
        final String surplus = paramMap.get("surplus");

        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT * ");
                FROM("( SELECT " +
                        "GDS_SEQNO," +
                        "GDS_MTNO," +
                        "GDECD," +
                        "GDS_NM," +
                        "DCL_UNITCD," +
                        "(select unit_name from t_unit_code where unit_code = dcl_unitcd) as DCL_UNITCD_NAME," +
                        "(IN_QTY - ACTL_REDC_QTY - PREVD_REDC_QTY) as SURPLUS " +
                        "FROM T_BWL_LIST_TYPE " +
                        "WHERE BWS_NO in (SELECT BWS_NO FROM T_BWL_HEAD_TYPE WHERE CRT_ENT_ID = #{entId}) )");
                if (!StringUtils.isEmpty(surplus)) {
                    WHERE("SURPLUS >= #{surplus}");
                }
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

    public String queryStockControlCount(Map<String, String> paramMap) {

        final String gds_seqno = paramMap.get("gds_seqno");
        final String gds_mtno = paramMap.get("gds_mtno");
        final String gdecd = paramMap.get("gdecd");
        final String gds_nm = paramMap.get("gds_nm");
        final String entId = paramMap.get("entId");
        final String surplus = paramMap.get("surplus");

        return new SQL() {
            {

                SELECT("COUNT(1)");
                FROM("( SELECT " +
                        "GDS_SEQNO," +
                        "GDS_MTNO," +
                        "GDECD," +
                        "GDS_NM," +
                        "DCL_UNITCD," +
                        "(IN_QTY - ACTL_REDC_QTY - PREVD_REDC_QTY) as SURPLUS " +
                        "FROM T_BWL_LIST_TYPE " +
                        "WHERE BWS_NO in (SELECT BWS_NO FROM T_BWL_HEAD_TYPE WHERE CRT_ENT_ID = #{entId}) )");
                if (!StringUtils.isEmpty(surplus)) {
                    WHERE("SURPLUS >= #{surplus}");
                }
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
