package com.xaeport.crossborder.data.provider;


import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class PaymentDeclareSQLProvider extends BaseSQLProvider {

    /*
     * 支付单申报数据查询
	 */
    public String queryPaymentDeclareList(Map<String, String> paramMap) throws Exception {

        final String orderNo = paramMap.get("orderNo");
        final String payTransactionId = paramMap.get("payTransactionId");
        final String end = paramMap.get("end");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");

        return new SQL() {
            {
                SELECT(
                        " * from ( select rownum rn, f.* from ( " +
                                " SELECT " +
                                "    t.PAY_TRANSACTION_ID," +
                                "    t.GUID," +
                                "    t.ORDER_NO," +
                                "    t.PAY_NAME," +
                                "    t.EBP_NAME," +
                                "    t.PAYER_NAME," +
                                "    t.AMOUNT_PAID," +
                                "    t.PAY_TIME," +
                                "    t.NOTE," +
                                "    t.RETURN_STATUS," +
                                "    t.RETURN_INFO," +
                                "    t.RETURN_TIME," +
                                "    t.DATA_STATUS");
                FROM("T_IMP_PAYMENT t");
                if(!roleId.equals("admin")){
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.order_no = #{orderNo}");
                }
                if (!StringUtils.isEmpty(payTransactionId)) {
                    WHERE("t.PAY_TRANSACTION_ID = #{payTransactionId}");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.crt_tm desc ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.crt_tm desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    /*
     * 支付单申报总数查询
     */
    public String queryPaymentDeclareCount(Map<String, String> paramMap) throws Exception {

        final String orderNo = paramMap.get("orderNo");
        final String payTransactionId = paramMap.get("payTransactionId");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_PAYMENT t");
                if(!roleId.equals("admin")){
                    WHERE("t.ent_id = #{entId}");
                }
                if (!StringUtils.isEmpty(orderNo)) {
                    WHERE("t.ORDER_NO = #{orderNo}");
                }
                if (!StringUtils.isEmpty(payTransactionId)) {
                    WHERE("t.PAY_TRANSACTION_ID = #{payTransactionId}");
                }
            }
        }.toString();
    }

    /*
     * 提交海关
     */
    public String updateSubmitCustom(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        final String dataStatusWhere = paramMap.get("dataStatusWhere");
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                UPDATE("T_IMP_PAYMENT t");
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
     * 生产支付单报文数据查询
     */
    public String findWaitGenerated(final Map<String, String> paramMap) {
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT("GUID,APP_TYPE,APP_TIME,APP_STATUS,PAY_CODE,PAY_NAME,PAY_TRANSACTION_ID");
                SELECT("ORDER_NO,EBP_CODE,EBP_NAME,PAYER_ID_TYPE,PAYER_ID_NUMBER,PAYER_NAME");
                SELECT("TELEPHONE,to_char(AMOUNT_PAID,'FM999999999990.00000') as AMOUNT_PAID,CURRENCY,PAY_TIME");
                SELECT("NOTE,DATA_STATUS,CRT_ID,CRT_TM,UPD_ID,UPD_TM,RETURN_STATUS");
                FROM("T_IMP_PAYMENT t");
                WHERE("data_Status = #{dataStatus}");
                WHERE("rownum<=100");
                ORDER_BY("t.CRT_TM asc,t.ORDER_NO asc");
            }
        }.toString();
    }

    /*
     * 修改支付单状态为支付单已申报
     */
    public String updateImpPaymentStatus(@Param("guid") String guid,@Param("CBDS31") String CBDS31){
        return new SQL(){
            {
                UPDATE("T_IMP_PAYMENT t");
                WHERE("t.GUID = #{guid}");
                SET("t.DATA_STATUS = 'CBDS31'");
                SET("t.upd_tm = sysdate");
            }
        }.toString();
    }
    public String queryPaymentById(@Param("paytransactionid") String paytransactionid) throws Exception {

        return new SQL() {
            {
                SELECT( "" +
                        "    t.GUID," +
                        "    t.PAY_TRANSACTION_ID," +
                        "    t.ORDER_NO," +
                        "    t.PAY_CODE," +
                        "    t.PAY_NAME," +
                        "    t.EBP_CODE," +
                        "    t.EBP_NAME," +
                        "    t.AMOUNT_PAID," +
                        "    t.PAYER_ID_TYPE," +
                        "    t.PAYER_ID_NUMBER," +
                        "    t.PAYER_NAME," +
                        "    t.PAY_TIME," +
                        "    t.DATA_STATUS," +
                        "    t.NOTE");
                FROM("T_IMP_PAYMENT t");
                if (!StringUtils.isEmpty(paytransactionid)) {
                    WHERE("t.PAY_TRANSACTION_ID = #{paytransactionid}");
                }
            }
        }.toString();
    }
    /*
    * queryCompany(@Param("crtId") String crtId)
    * 根据用户id查找企业id,根据企业id查找企业信息
    * */
    public String queryCompany(@Param("crtId") String crtId){
        return new SQL(){
            {
                SELECT("te.CUSTOMS_CODE as copCode");
                SELECT("te.ENT_NAME as copName");
                SELECT("'DXP' as dxpMode");
                SELECT("te.DXP_ID as dxpId");
                SELECT("te.note as note");
                FROM("T_ENTERPRISE te,T_USERS tu");
                WHERE("tu.id=#{crtId} and tu.ent_id = te.id");
            }
        }.toString();

    }

}
