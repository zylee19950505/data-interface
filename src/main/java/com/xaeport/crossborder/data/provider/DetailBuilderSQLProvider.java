package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
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
                if (!StringUtils.isEmpty(paramMap.get("billNo"))){
                    WHERE("til.BILL_NO = #{billNo}");
                }
                if (!StringUtils.isEmpty(paramMap.get("orderNo"))){
                    WHERE("til.ORDER_NO = #{orderNo}");
                }
                if (!StringUtils.isEmpty(paramMap.get("logisticsNo"))){
                    WHERE("til.LOGISTICS_NO = #{logisticsNo}");
                }
                if (!StringUtils.isEmpty(paramMap.get("dataStatus"))){
                    if ("QDKSC".equals(paramMap.get("dataStatus"))){
                        WHERE("tb.DATASTATUS is null");

                    }else{
                        WHERE("tb.DATASTATUS = #{dataStatus}");
                    }
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
                if (!StringUtils.isEmpty(paramMap.get("billNo"))){
                    WHERE("til.BILL_NO = #{billNo}");
                }
                if (!StringUtils.isEmpty(paramMap.get("orderNo"))){
                    WHERE("til.ORDER_NO = #{orderNo}");
                }
                if (!StringUtils.isEmpty(paramMap.get("logisticsNo"))){
                    WHERE("til.LOGISTICS_NO = #{logisticsNo}");
                }
                if (!StringUtils.isEmpty(paramMap.get("dataStatus"))){
                    if ("QDKSC".equals(paramMap.get("dataStatus"))){
                        WHERE("tb.DATASTATUS is null");

                    }else{
                        WHERE("tb.DATASTATUS = #{dataStatus}");
                    }
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

    public String updateBuilderCache(Map<String, String> map){
        return new SQL(){
            {
                UPDATE("T_BUILDER_CACHE t");
                SET("t.DATASTATUS = #{dataStatus}");
                SET("t.CRT_TIME = sysdate");
                WHERE("t.ORDER_NO = #{orderNo}");
            }
        }.toString();
    }

}
