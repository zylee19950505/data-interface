package com.xaeport.crossborder.data.provider;


import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class OrderDeclareSQLProvider extends BaseSQLProvider {

    /*
     * 订单申报数据查询
	 */
    public String queryOrderDeclareList(Map<String, Object> paramMap) throws Exception {
        final String orderNo = paramMap.get("orderNo").toString();
        final String startFlightTimes = paramMap.get("startFlightTimes").toString();
        final String endFlightTimes = paramMap.get("endFlightTimes").toString();
        final String start = paramMap.get("start").toString();
        final String length = paramMap.get("length").toString();

        return new SQL() {
            {
                SELECT(" * from ( select w.*, ROWNUM AS rn from ( " +
                        " select * from T_IMP_ORDER_HEAD th ");
                WHERE("1=1");
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("th.order_no = #{orderNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE(" th.app_time >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE(" th.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(length)) {
                    ORDER_BY("th.crt_tm desc ) w  )   WHERE rn >= #{start} AND rn < #{start} + #{length} ");
                } else {
                    ORDER_BY("th.crt_tm desc ) w  )   WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    /*
     * 订单申报总数查询
     */
    public String queryOrderDeclareCount(Map<String, Object> paramMap) throws Exception {

        final String orderNo = paramMap.get("orderNo").toString();
        final String startFlightTimes = paramMap.get("startFlightTimes").toString();
        final String endFlightTimes = paramMap.get("endFlightTimes").toString();

        String orderNoStr = "";
        if (!StringUtils.isEmpty(orderNo)) {
            orderNoStr = " AND th.order_no = #{orderNo}";
        }
        String startTimesStr = "";
        if (!StringUtils.isEmpty(startFlightTimes)) {
            startTimesStr = " AND th.app_time >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')";
        }
        String endTimesStr = "";
        if (!StringUtils.isEmpty(endFlightTimes)) {
            endTimesStr = " AND th.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')";
        }
        return " select COUNT(*) count FROM t_imp_order_head th where 1=1 " + startTimesStr + endTimesStr;

    }

    /*
    * 订单申报--提交海关updateSubmitCustom
    * */
    public String updateSubmitCustom(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        final String opStatusWhere = paramMap.get("opStatusWhere");
        final String entryType = paramMap.get("entryType");//申报类型
        final String idCardValidate = paramMap.get("idCardValidate");//身份证验证通过
        final String ieFlag = paramMap.get("ieFlag");//进出口
        return new SQL(){
            {
                UPDATE("T_IMP_ORDER_HEAD th");
                WHERE(splitJointIn("th.ORDER_NO", submitKeys));
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
                    /*SELECT("to_char(APP_TIME,'YYYYMMDDhhmmss') as app_time");*/
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
                    /*SELECT("to_char(CRT_TM,'YYYYMMDDhhmmss') as crt_tm");*/
                    SELECT("CRT_TM");
                    SELECT("UPD_ID");
                   /* SELECT("to_char(UPD_TM,'YYYYMMDDhhmmss') as upd_tm");*/
                    SELECT("UPD_TM");
                    SELECT("RETURN_STATUS");
                    FROM("T_IMP_ORDER_HEAD toh");
                    WHERE("1=1");
                    if(!StringUtils.isEmpty(dataStatus)){
                        WHERE("toh.DATA_STATUS = #{dataStatus}");
                        ORDER_BY("toh.CRT_TM asc");
                        ORDER_BY("toh.GUID asc");
                        ORDER_BY("toh.ORDER_NO asc");
                    }
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

