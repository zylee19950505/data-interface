package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class DetailDeclareSQLProvider extends BaseSQLProvider{

    public String queryInventoryDeclareList(Map<String, String> paramMap) throws Exception {
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

    public String queryInventoryDeclareCount(Map<String, String> paramMap) throws Exception {

        final String billNo = paramMap.get("billNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.WRITING_MODE IS NULL");
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
                SELECT("CRT_TM");
                SELECT("UPD_ID");
                SELECT("UPD_TM");
                SELECT("RETURN_STATUS");
                SELECT("ENT_ID");
                SELECT("ENT_NAME");
                SELECT("ENT_CUSTOMS_CODE");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("DATA_STATUS = #{dataStatus}");
                WHERE("rownum <= 100");
                ORDER_BY("t.CRT_TM asc,t.ORDER_NO asc");
            }
        }.toString();
    }

    /*
     * 根据清单状态查找数据
     * findWaitGenerated
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
                SELECT("CRT_TM");
                SELECT("UPD_ID");
                SELECT("UPD_TM");
                SELECT("RETURN_STATUS");
                SELECT("ENT_ID");
                SELECT("ENT_NAME");
                SELECT("ENT_CUSTOMS_CODE");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("DATA_STATUS = #{dataStatus}");
                WHERE("ENT_ID = #{ent_id}");
                ORDER_BY("t.CRT_TM asc,t.ORDER_NO asc");
            }
        }.toString();
    }

    /*
     * 根据清单状态查找数据
     * findWaitGeneratedByXmlCount
     * */
    public String findWaitGeneratedByXmlCount(Map<String,String> paramMap){
        final String dataStatus = paramMap.get("dataStatus");
        final String ent_id = paramMap.get("ent_id");
        return new SQL(){
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("DATA_STATUS = #{dataStatus}");
                WHERE("ENT_ID = #{ent_id}");
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
                SELECT("PRICE");
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
