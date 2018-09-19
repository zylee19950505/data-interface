package com.xaeport.crossborder.data.provider;


import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class OrderDeclareSQLProvider extends BaseSQLProvider {

    /*
     * 订单申报数据查询
	 */
    public String queryOrderDeclareList(Map<String, String> paramMap) throws Exception {
        //final String orderNo = paramMap.get("orderNo");
        final String billNo = paramMap.get("billNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");

        return new SQL() {
            {
//                SELECT("t.bill_no," +
//                        "    ( " +
//                        "        SELECT" +
//                        "            COUNT(1)" +
//                        "        FROM" +
//                        "            t_imp_order_head t2" +
//                        "        WHERE" +
//                        "            t2.bill_no = t.bill_no" +
//                        "    ) totalCount," +
//                        "    (select max(t3.app_time) from t_imp_order_head t3 where t3.BILL_NO=t.BILL_NO and t3.DATA_STATUS=t.DATA_STATUS) appTime," +
//                        "    t.data_status," +
//                        "    (" +
//                        "        SELECT" +
//                        "            COUNT(1)" +
//                        "        FROM" +
//                        "            t_imp_order_head t4" +
//                        "        WHERE" +
//                        "            t4.DATA_STATUS = t.DATA_STATUS and t4.bill_no = t.BILL_NO " +
//                        "            and (t4.DATA_STATUS like 'CBDS2%' or t4.DATA_STATUS = 'CBDS1')" +
//                        "    ) count" );
                SELECT("t.bill_no");
                SELECT("(select max(APP_TIME) from T_IMP_ORDER_HEAD t2 where t2.bill_no = t.bill_no) as appTime");
                SELECT("(select count(1) from T_IMP_ORDER_HEAD tt where tt.bill_no = t.bill_no) as totalCount");
                SELECT("count(1) as count");
                SELECT("t.data_status");
                FROM("T_IMP_ORDER_HEAD t");
                if(!roleId.equals("admin")){
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(billNo)){
                    WHERE("t.bill_no = #{billNo}");
                }
                if (!StringUtils.isEmpty(dataStatus)){
                    WHERE("t.DATA_STATUS = #{dataStatus}");
                }
                if(!StringUtils.isEmpty(startFlightTimes)){
                    WHERE("t.CRT_TM >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if(!StringUtils.isEmpty(endFlightTimes)){
                    WHERE("t.CRT_TM <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                GROUP_BY("t.bill_no,t.data_status");
                ORDER_BY("t.bill_no asc");
            }
        }.toString();
    }

    /*
     * 订单申报总数查询
     */
    public String queryOrderDeclareCount(Map<String, Object> paramMap) throws Exception {

        final String orderNo = paramMap.get("orderNo").toString();
        final String billNo = paramMap.get("billNo").toString();
        final String startFlightTimes = paramMap.get("startFlightTimes").toString();
        final String endFlightTimes = paramMap.get("endFlightTimes").toString();
        final String entId = paramMap.get("entId").toString();
        final String roleId = paramMap.get("roleId").toString();
        final String dataStatus = paramMap.get("dataStatus").toString();


        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_ORDER_HEAD th");
                WHERE("1=1");
                if(!roleId.equals("admin")){
                    WHERE("th.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(dataStatus)){
                    WHERE(splitJointIn("th.DATA_STATUS",dataStatus));
                }
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("th.order_no = #{orderNo}");
                }
                if (!StringUtils.isEmpty(billNo)){
                    WHERE("th.bill_no = #{billNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE(" th.crt_tm >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE(" th.crt_tm <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
            }
        }.toString();

    }

    /*
    * 订单申报--提交海关updateSubmitCustom
    * */
    public String updateSubmitCustom(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        final String opStatusWhere = paramMap.get("opStatusWhere");
       // final String entryType = paramMap.get("entryType");//申报类型
       // final String idCardValidate = paramMap.get("idCardValidate");//身份证验证通过
       // final String ieFlag = paramMap.get("ieFlag");//进出口
        return new SQL(){
            {
                UPDATE("T_IMP_ORDER_HEAD th");
                WHERE(splitJointIn("th.bill_no", submitKeys));
                WHERE(splitJointIn("th.DATA_STATUS", opStatusWhere));
                //身份证验证通过?
                //进出口?电子订单类型
                //申报类型?
                SET("th.data_status=#{opStatus}");
                SET("th.upd_tm=sysdate");
                SET("th.APP_TIME=sysdate");
                SET("th.upd_id=#{currentUserId}");

            }
        }.toString();
    }

    /*
    * 根据订单状态查找数据
    * findWaitGenerated
    * */
    public String findWaitGenerated(Map<String,String> paramMap){
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL(){
                {
                    SELECT("GUID");
                    SELECT("APP_TYPE");
                    SELECT("APP_TIME");
                    SELECT("APP_STATUS");
                    SELECT("ORDER_TYPE");
                    SELECT("ORDER_NO");
                    SELECT("EBP_CODE");
                    SELECT("EBP_NAME");
                    SELECT("EBC_CODE");
                    SELECT("EBC_NAME");
                    SELECT("to_char(GOODS_VALUE) as goods_value");
                    SELECT("to_char(FREIGHT) as freight");
                    SELECT("to_char(DISCOUNT) as discount");
                    SELECT("to_char(TAX_TOTAL) as tax_total");
                    SELECT("to_char(ACTURAL_PAID) as actural_paid");
                    SELECT("CURRENCY");
                    SELECT("BUYER_REG_NO");
                    SELECT("BUYER_NAME");
                    SELECT("BUYER_TELEPHONE");
                    SELECT("BUYER_ID_TYPE");
                    SELECT("BUYER_ID_NUMBER");
                    SELECT("PAY_CODE");
                    SELECT("PAYNAME");
                    SELECT("PAY_TRANSACTION_ID");
                    SELECT("BATCH_NUMBERS");
                    SELECT("CONSIGNEE");
                    SELECT("CONSIGNEE_TELEPHONE");
                    SELECT("CONSIGNEE_ADDRESS");
                    SELECT("CONSIGNEE_DITRICT");
                    SELECT("NOTE");
                    SELECT("DATA_STATUS");
                    SELECT("CRT_ID");
                    SELECT("CRT_TM");
                    SELECT("UPD_ID");
                    SELECT("UPD_TM");
                    SELECT("RETURN_STATUS");
                    SELECT("ENT_ID");
                    SELECT("ENT_NAME");
                    SELECT("ENT_CUSTOMS_CODE");
                    FROM("T_IMP_ORDER_HEAD t");
                    WHERE("DATA_STATUS = #{dataStatus}");
                    WHERE("rownum <= 100");
                    ORDER_BY("t.CRT_TM asc,t.ORDER_NO asc");
            }
        }.toString();
    }

    /*
     * 根据订单状态查找数据
     * findWaitGeneratedByXml
     * */
    public String findWaitGeneratedByXml(Map<String,String> paramMap){
        final String dataStatus = paramMap.get("dataStatus");
        final String ent_id = paramMap.get("ent_id");
        return new SQL(){
            {
                SELECT("GUID");
                SELECT("APP_TYPE");
                SELECT("APP_TIME");
                SELECT("APP_STATUS");
                SELECT("ORDER_TYPE");
                SELECT("ORDER_NO");
                SELECT("EBP_CODE");
                SELECT("EBP_NAME");
                SELECT("EBC_CODE");
                SELECT("EBC_NAME");
                SELECT("to_char(GOODS_VALUE) as goods_value");
                SELECT("to_char(FREIGHT) as freight");
                SELECT("to_char(DISCOUNT) as discount");
                SELECT("to_char(TAX_TOTAL) as tax_total");
                SELECT("to_char(ACTURAL_PAID) as actural_paid");
                SELECT("CURRENCY");
                SELECT("BUYER_REG_NO");
                SELECT("BUYER_NAME");
                SELECT("BUYER_TELEPHONE");
                SELECT("BUYER_ID_TYPE");
                SELECT("BUYER_ID_NUMBER");
                SELECT("PAY_CODE");
                SELECT("PAYNAME");
                SELECT("PAY_TRANSACTION_ID");
                SELECT("BATCH_NUMBERS");
                SELECT("CONSIGNEE");
                SELECT("CONSIGNEE_TELEPHONE");
                SELECT("CONSIGNEE_ADDRESS");
                SELECT("CONSIGNEE_DITRICT");
                SELECT("NOTE");
                SELECT("DATA_STATUS");
                SELECT("CRT_ID");
                SELECT("CRT_TM");
                SELECT("UPD_ID");
                SELECT("UPD_TM");
                SELECT("RETURN_STATUS");
                SELECT("ENT_ID");
                SELECT("ENT_NAME");
                SELECT("ENT_CUSTOMS_CODE");
                FROM("T_IMP_ORDER_HEAD t");
                WHERE("DATA_STATUS = #{dataStatus}");
                WHERE("ENT_ID = #{ent_id}");
                ORDER_BY("t.CRT_TM asc,t.ORDER_NO asc");
            }
        }.toString();
    }

    /*
     * 根据订单状态查找数据
     * findWaitGeneratedByXmlCount
     * */
    public String findWaitGeneratedByXmlCount(Map<String,String> paramMap){
        final String dataStatus = paramMap.get("dataStatus");
        final String ent_id = paramMap.get("ent_id");
        return new SQL(){
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_ORDER_HEAD t");
                WHERE("DATA_STATUS = #{dataStatus}");
                WHERE("ENT_ID = #{ent_id}");
            }
        }.toString();
    }

    /*
    * 根据id查找
    * queryOrderListByGuid
    * */
    public String queryOrderListByGuid(@Param("headGuid")String headGuid){

        return new SQL(){
            {
                SELECT("G_NUM");
                SELECT("HEAD_GUID");
                SELECT("ORDER_NO");
                SELECT("ITEM_NO");
                SELECT("ITEM_NAME");
                SELECT("G_MODEL");
                SELECT("ITEM_DESCRIBE");
                SELECT("BAR_CODE");
                SELECT("UNIT");
                SELECT("QTY");
                SELECT("PRICE");
                SELECT("TOTAL_PRICE");
                SELECT("CURRENCY");
                SELECT("COUNTRY");
                SELECT("NOTE");
                FROM("T_IMP_ORDER_BODY tob");
                WHERE("tob.HEAD_GUID = #{headGuid}");
            }
        }.toString();
    }

    /*
    * 修改订单状态
    * updateEntryHeadOrderStatus
    * */
    public String updateEntryHeadOrderStatus(@Param("headGuid") String headGuid,@Param("ddysb") String ddysb){
        return new SQL(){
            {
                UPDATE("T_IMP_ORDER_HEAD toh");
                WHERE("toh.GUID = #{headGuid}");
                SET("toh.DATA_STATUS = #{ddysb}");
                SET("toh.upd_tm=sysdate");
            }
        }.toString();
    }

    /*
    * 根据创建人id查找企业id
    * */
    public String queryEntId(@Param("crt_id") String crt_id){
        return new SQL(){
            {
                SELECT("t.ent_id");
                FROM("T_USERS t");
                WHERE("t.ID = #{crt_id}");
            }
        }.toString();
    }

    /*
    * 根据企业id查找企业信息
    * */
    public String queryCompany(@Param("ent_id") String ent_id){
        return new SQL(){
            {
                SELECT("t.CUSTOMS_CODE as copCode");
                SELECT("t.ENT_NAME as copName");
                SELECT("'DXP' as dxpMode");
                SELECT("t.DXP_ID as dxpId");
                SELECT("t.note as note");
                FROM("T_ENTERPRISE t");
                WHERE("t.id = #{ent_id}");
            }
        }.toString();

    }
}

