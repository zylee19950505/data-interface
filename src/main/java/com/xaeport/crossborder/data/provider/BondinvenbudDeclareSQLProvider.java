package com.xaeport.crossborder.data.provider;


import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class BondinvenbudDeclareSQLProvider extends BaseSQLProvider{

    public String queryBondinvenbudDeclareList(Map<String, String> paramMap) throws Exception {
        final String billNo = paramMap.get("billNo");
        final String end = paramMap.get("end");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT("BILL_NO");
                SELECT("(select max(APP_TIME) from T_IMP_INVENTORY_HEAD t2 where t2.bill_no = t.bill_no) as APP_TIME");
                SELECT("(select count(1) from T_IMP_INVENTORY_HEAD tt where tt.bill_no = t.bill_no) as sum");
                SELECT("count(1) as asscount");
                SELECT("DATA_STATUS");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.WRITING_MODE IS NULL");
                WHERE("t.BUSINESS_TYPE = #{business_type}");
                if(!roleId.equals("admin")){
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(dataStatus)){
                    WHERE(splitJointIn("t.DATA_STATUS",dataStatus));
                }
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.bill_no = #{billNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.crt_tm >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.crt_tm <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                GROUP_BY("BILL_NO,DATA_STATUS");
                ORDER_BY("t.BILL_NO");
            }
        }.toString();
    }

    /*
     * 提交海关清单
     */
    public String updateSubmitCustom(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        final String dataStatusWhere = paramMap.get("dataStatusWhere");
        final String dataStatus = paramMap.get("dataStatus");
        final String userId = paramMap.get("userId");
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE(splitJointIn("t.BILL_NO", submitKeys));
                WHERE(splitJointIn("t.DATA_STATUS", dataStatusWhere));
                SET("t.data_status = #{dataStatus}");
                SET("t.APP_TIME = sysdate");
                SET("t.upd_tm = sysdate");
                SET("t.upd_id = #{userId}");
            }
        }.toString();
    }
}
