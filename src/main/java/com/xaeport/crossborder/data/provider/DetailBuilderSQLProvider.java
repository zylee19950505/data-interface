package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class DetailBuilderSQLProvider extends BaseSQLProvider{


    public String queryBuilderDetailList(Map<String, String> paramMap){
        final String length = paramMap.get("length");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        return new SQL(){
            {
                SELECT(" * from ( select w.*, ROWNUM AS rn from ( "+
                        "select til.logistics_no,"+
                        "til.data_status as logisticsStatus,"+
                        "tio.order_no,"+
                        "tio.data_status as orderStatus,tb.dataStatus");
                FROM("T_IMP_ORDER_HEAD tio left JOIN T_IMP_LOGISTICS til on(tio.order_no = til.order_no) left join T_BUILDER_CACHE tb on(tio.order_no = tb.order_no)");
                WHERE("til.order_no is not null and tio.BUSINESS_TYPE = #{business_type}");
                WHERE("til.logistics_no is not null");
                if (!roleId.equals("admin")) {
                    WHERE("tio.ent_id = #{entId}");
                }
                if (!"-1".equals(length)) {
                    ORDER_BY("tio.UPD_TM desc ) w  )   WHERE rn >= #{start} AND rn < #{start} + #{length} ");
                } else {
                    ORDER_BY("tio.UPD_TM desc ) w  )   WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    public String queryBuilderDetailCount(Map<String, String> paramMap){
        final String roleId = paramMap.get("roleId");
        return new SQL(){
            {
                SELECT("count(1) count");
                FROM("T_IMP_ORDER_HEAD tio left JOIN T_IMP_LOGISTICS til on(tio.order_no = til.order_no) left join T_BUILDER_CACHE tb on(tio.order_no = tb.order_no)");
                WHERE("til.order_no is not null and tio.BUSINESS_TYPE = #{business_type}");
                WHERE("til.logistics_no is not null");
                if (!roleId.equals("admin")) {
                    WHERE("tio.ent_id = #{entId}");
                }
            }
        }.toString();
    }


    public String insertBuilderCache(Map<String, String> map){
        return new SQL(){
            {
                INSERT_INTO("T_BUILDER_CACHE");
                VALUES("ID", "#{id}");
                VALUES("ORDER_NO", "#{orderNo}");
                VALUES("DATASTATUS", "#{dataStatus}");
                VALUES("CRT_TIME", "sysdate");
            }
        }.toString();
    }

}
