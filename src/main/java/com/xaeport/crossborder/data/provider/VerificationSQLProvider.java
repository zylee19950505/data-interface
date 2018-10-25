package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.StatusRecord;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.status.VerifyType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class VerificationSQLProvider extends BaseSQLProvider {

    public String unverifiedByInventoryHead() {
        return new SQL() {
            {
                SELECT("GUID,APP_TYPE,APP_STATUS,EBP_CODE,EBP_NAME,EBC_CODE,EBC_NAME");
                SELECT("ORDER_NO,LOGISTICS_NO,COP_NO,PRE_NO,EMS_NO,INVT_NO");
                SELECT("LOGISTICS_CODE,LOGISTICS_NAME,ASSURE_CODE,IE_FLAG");
                SELECT("CUSTOMS_CODE,PORT_CODE,CONSIGNEE_ADDRESS");
                SELECT("AGENT_CODE,AGENT_NAME,AREA_CODE,AREA_NAME,TRADE_MODE,TRAF_MODE,TRAF_NO");
                SELECT("BILL_NO,VOYAGE_NO,BUSINESS_TYPE");
                SELECT("LOCT_NO,LICENSE_NO,COUNTRY,CURRENCY,WRAP_TYPE,PACK_NO,NOTE");
                SELECT("ENT_ID,ENT_NAME,ENT_CUSTOMS_CODE");
                SELECT("BUYER_ID_TYPE,BUYER_ID_NUMBER,BUYER_NAME,BUYER_TELEPHONE");
                SELECT("APP_TIME,DECL_TIME,IE_DATE");
                SELECT("to_char(FREIGHT,'FM999999999990.00000') as FREIGHT");
                SELECT("to_char(INSURED_FEE,'FM999999999990.00000') as INSURED_FEE");
                SELECT("to_char(GROSS_WEIGHT,'FM999999999990.00000') as GROSS_WEIGHT");
                SELECT("to_char(NET_WEIGHT,'FM999999999990.00000') as NET_WEIGHT");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.DATA_STATUS = 'CBDS1' ");
                WHERE("not exists(SELECT vs.ORDER_NO from T_VERIFY_STATUS vs WHERE vs.BILL_NO = t.BILL_NO and vs.ORDER_NO = t.ORDER_NO and vs.CB_HEAD_ID = t.GUID and vs.TYPE = 'LOGIC')");
                WHERE("ROWNUM <= 1000");
                ORDER_BY("t.CRT_TM asc,t.ORDER_NO asc");
            }
        }.toString();
    }

    public String unverifiedByInventoryBody(final Map<String, String> paramMap) {
        final String headGuids = paramMap.get("headGuids");
        return new SQL() {
            {
                SELECT("G_NUM");
                SELECT("HEAD_GUID");
                SELECT("ORDER_NO");
                SELECT("ITEM_RECORD_NO");
                SELECT("ITEM_NO");
                SELECT("ITEM_NAME");
                SELECT("G_CODE");
                SELECT("G_NAME");
                SELECT("G_MODEL");
                SELECT("BAR_CODE");
                SELECT("COUNTRY");
                SELECT("CURRENCY");
                SELECT("to_char(QTY,'FM999999999990.00000') as QTY");
                SELECT("to_char(QTY1,'FM999999999990.00000') as QTY1");
                SELECT("to_char(QTY2,'FM999999999990.00000') as QTY2");
                SELECT("UNIT");
                SELECT("UNIT1");
                SELECT("UNIT2");
                SELECT("to_char(PRICE,'FM999999999990.00000') as PRICE");
                SELECT("to_char(TOTAL_PRICE,'FM999999999990.00000') as TOTAL_PRICE");
                SELECT("NOTE");
                FROM("T_IMP_INVENTORY_BODY t");
                WHERE(splitJointIn("t.HEAD_GUID", headGuids));
            }
        }.toString();
    }

    public String insertVerifyStatus(Verify verify) {
        return new SQL() {
            {
                INSERT_INTO("T_VERIFY_STATUS");
                if (!StringUtils.isEmpty(verify.getVs_id())) {
                    VALUES("VS_ID", "#{vs_id}");
                }
                if (!StringUtils.isEmpty(verify.getCb_verify_no())) {
                    VALUES("CB_VERIFY_NO", "#{cb_verify_no}");
                }
                if (!StringUtils.isEmpty(verify.getCb_head_id())) {
                    VALUES("CB_HEAD_ID", "#{cb_head_id}");
                }
                if (!StringUtils.isEmpty(verify.getBill_no())) {
                    VALUES("BILL_NO", "#{bill_no}");
                }
                if (!StringUtils.isEmpty(verify.getOrder_no())) {
                    VALUES("ORDER_NO", "#{order_no}");
                }
                if (!StringUtils.isEmpty(verify.getType())) {
                    VALUES("TYPE", "#{type}");
                }
                if (!StringUtils.isEmpty(verify.getCode())) {
                    VALUES("CODE", "#{code}");
                }
                if (!StringUtils.isEmpty(verify.getStatus())) {
                    VALUES("STATUS", "#{status}");
                }
                if (!StringUtils.isEmpty(verify.getResult())) {
                    VALUES("RESULT", "#{result}");
                }
                if (!StringUtils.isEmpty(verify.getCreate_time())) {
                    VALUES("CREATE_TIME", "#{create_time}");
                }
                if (!StringUtils.isEmpty(verify.getUpdate_time())) {
                    VALUES("UPDATE_TIME", "#{update_time}");
                }
            }
        }.toString();
    }

    public String insertVerifyRecordByVerify(Verify verify) {
        return new SQL() {
            {
                INSERT_INTO("T_VERIFY_RECORD");
                if (!StringUtils.isEmpty(verify.getVr_id())) {
                    VALUES("VR_ID", "#{vr_id}");
                }
                if (!StringUtils.isEmpty(verify.getCb_verify_no())) {
                    VALUES("CB_VERIFY_NO", "#{cb_verify_no}");
                }
                if (!StringUtils.isEmpty(verify.getCb_head_id())) {
                    VALUES("CB_HEAD_ID", "#{cb_head_id}");
                }
                if (!StringUtils.isEmpty(verify.getBill_no())) {
                    VALUES("BILL_NO", "#{bill_no}");
                }
                if (!StringUtils.isEmpty(verify.getOrder_no())) {
                    VALUES("ORDER_NO", "#{order_no}");
                }
                if (!StringUtils.isEmpty(verify.getType())) {
                    VALUES("TYPE", "#{type}");
                }
                if (!StringUtils.isEmpty(verify.getCode())) {
                    VALUES("CODE", "#{code}");
                }
                if (!StringUtils.isEmpty(verify.getStatus())) {
                    VALUES("STATUS", "#{status}");
                }
                if (!StringUtils.isEmpty(verify.getResult())) {
                    VALUES("RESULT", "#{result}");
                }
                if (!StringUtils.isEmpty(verify.getEntry_message())) {
                    VALUES("ENTRY_MESSAGE", "#{entry_message}");
                }
                if (!StringUtils.isEmpty(verify.getEnterprise_id())) {
                    VALUES("ENTERPRISE_ID", "#{enterprise_id}");
                }
                if (!StringUtils.isEmpty(verify.getEnterprise_name())) {
                    VALUES("ENTERPRISE_NAME", "#{enterprise_name}");
                }
                if (!StringUtils.isEmpty(verify.getCreate_time())) {
                    VALUES("CREATE_TIME", "#{create_time}");
                }
                if (!StringUtils.isEmpty(verify.getReMark())) {
                    VALUES("REMARK", "#{remark}");
                }
            }
        }.toString();
    }

    public String insertStatusRecord(@Param("statusRecord") StatusRecord statusRecord) {
        return new SQL() {
            {
                INSERT_INTO("T_STATUS_RECORD");
                if (!StringUtils.isEmpty(statusRecord.getSr_id())) {
                    VALUES("SR_ID", "#{statusRecord.sr_id}");
                }
                if (!StringUtils.isEmpty(statusRecord.getStatus_code())) {
                    VALUES("STATUS_CODE", "#{statusRecord.status_code}");
                }
                if (!StringUtils.isEmpty(statusRecord.getBelong())) {
                    VALUES("BELONG", "#{statusRecord.belong}");
                }
                if (!StringUtils.isEmpty(statusRecord.getOdd_no())) {
                    VALUES("ODD_NO", "#{statusRecord.odd_no}");
                }
                if (!StringUtils.isEmpty(statusRecord.getCreate_time())) {
                    VALUES("CREATE_TIME", "#{statusRecord.create_time}");
                }
                if (!StringUtils.isEmpty(statusRecord.getNotes())) {
                    VALUES("NOTES", "#{statusRecord.notes}");
                }
            }
        }.toString();
    }

    public String updateEntryHeadStatus(@Param("guid") String guid, @Param("status") String status) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE("t.guid = #{guid}");
                if (!StringUtils.isEmpty(status)) {
                    SET("t.data_status = #{status}");
                }
            }
        }.toString();
    }

}