package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class DetailDeclareSQLProvider extends BaseSQLProvider{

    public String queryInventoryDeclareList(Map<String, String> paramMap) throws Exception {

        final String orderNo = paramMap.get("orderNo");
        final String end = paramMap.get("end");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");

        return new SQL() {
            {
                SELECT(
                        " * from ( select rownum rn, f.* from ( " +
                                " SELECT * ");
                FROM("T_IMP_INVENTORY_HEAD t");
                if(!roleId.equals("admin")){
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.order_no = #{orderNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.app_time >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.crt_tm desc ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.crt_tm desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    public String queryInventoryDeclareCount(Map<String, String> paramMap) throws Exception {

        final String orderNo = paramMap.get("orderNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_INVENTORY_HEAD t");
                if(!roleId.equals("admin")){
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.ORDER_NO = #{orderNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.app_time >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
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
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE(splitJointIn("t.ORDER_NO", submitKeys));
                WHERE(splitJointIn("t.DATA_STATUS", dataStatusWhere));
                SET("t.data_status = #{dataStatus}");
                SET("t.APP_TIME = sysdate");
                SET("t.upd_tm = sysdate");
                SET("t.upd_id = #{currentUserId}");
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
                SELECT("ORDER_NO");
                SELECT("EBP_CODE");
                SELECT("EBP_NAME");
                SELECT("EBC_CODE");
                SELECT("EBC_NAME");
                SELECT("LOGISTICS_NO");
                SELECT("LOGISTICS_CODE");
                SELECT("LOGISTICS_NAME");
                SELECT("COP_NO");
                SELECT("PRE_NO");
                SELECT("ASSURE_CODE");
                SELECT("EMS_NO");
                SELECT("INVT_NO");
                SELECT("IE_FLAG");
                SELECT("DECL_TIME");
                SELECT("CUSTOMS_CODE");
                SELECT("PORT_CODE");
                SELECT("IE_DATE");
                SELECT("BUYER_ID_TYPE");
                SELECT("BUYER_ID_NUMBER");
                SELECT("BUYER_NAME");
                SELECT("BUYER_TELEPHONE");
                SELECT("CONSIGNEE_ADDRESS");
                SELECT("AGENT_CODE");
                SELECT("AGENT_NAME");
                SELECT("AREA_CODE");
                SELECT("AREA_NAME");
                SELECT("TRADE_MODE");
                SELECT("TRAF_MODE");
                SELECT("TRAF_NO");
                SELECT("VOYAGE_NO");
                SELECT("BILL_NO");
                SELECT("LOCT_NO");
                SELECT("LICENSE_NO");
                SELECT("COUNTRY");
                SELECT("FREIGHT");
                SELECT("INSURED_FEE");
                SELECT("CURRENCY");
                SELECT("WRAP_TYPE");
                SELECT("PACK_NO");
                SELECT("GROSS_WEIGHT");
                SELECT("NET_WEIGHT");
                SELECT("NOTE");
                SELECT("DATA_STATUS");
                SELECT("CRT_ID");
                /*SELECT("to_char(CRT_TM,'YYYYMMDDhhmmss') as crt_tm");*/
                SELECT("CRT_TM");
                SELECT("UPD_ID");
                /* SELECT("to_char(UPD_TM,'YYYYMMDDhhmmss') as upd_tm");*/
                SELECT("UPD_TM");
                SELECT("RETURN_STATUS");
                FROM("T_IMP_INVENTORY_HEAD toh");
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
    public String querydetailDeclareListByGuid(@Param("headGuid")String headGuid){

        return new SQL(){
            {
                SELECT("G_NUM");
                SELECT("HEAD_GUID");
                SELECT("ORDER_NO");
                SELECT("ITEM_NO");
                SELECT("ITEM_NAME");
                SELECT("ITEM_RECORD_NO");
                SELECT("G_CODE");
                SELECT("G_NAME");
                SELECT("G_MODEL");
                SELECT("BAR_CODE");
                SELECT("COUNTRY");
                SELECT("CURRENCY");
                SELECT("QTY");
                SELECT("QTY1");
                SELECT("QTY2");
                SELECT("UNIT");
                SELECT("UNIT1");
                SELECT("UNIT2");
                SELECT("TOTAL_PRICE");
                SELECT("NOTE");
                FROM("T_IMP_INVENTORY_BODY tob");
                WHERE("tob.HEAD_GUID = #{headGuid}");
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
    /*
     * 修改订单状态
     * updateEntryHeadOrderStatus
     * */
    public String updateEntryHeadDetailStatus(@Param("headGuid") String headGuid,@Param("QDYSB") String QDYSB){
        return new SQL(){
            {
                UPDATE("T_IMP_INVENTORY_HEAD toh");
                WHERE("toh.GUID = #{headGuid}");
                SET("toh.DATA_STATUS = #{QDYSB}");
                SET("toh.upd_tm=sysdate");
            }
        }.toString();
    }
}
