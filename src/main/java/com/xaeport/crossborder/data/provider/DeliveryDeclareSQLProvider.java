package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpDeliveryHead;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class DeliveryDeclareSQLProvider extends BaseSQLProvider {

    public String queryDeliveryDeclareList(Map<String, String> paramMap) throws Exception {

        final String billNo = paramMap.get("billNo");
        final String end = paramMap.get("end");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");

        return new SQL() {
            {
                SELECT(
                        " * " +
                                "from ( select rownum rn, f.* from ( " +
                                " SELECT * ");
                FROM("T_IMP_DELIVERY_HEAD t");
//                if (!roleId.equals("admin")) {
//                    WHERE("t.ent_id = #{entId}");
//                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE(splitJointIn("t.DATA_STATUS", dataStatus));
                }
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.BILL_NO = #{billNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.crt_tm >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.crt_tm <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.crt_tm desc ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.crt_tm desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    public String queryDeliveryDeclareCount(Map<String, String> paramMap) throws Exception {

        final String billNo = paramMap.get("billNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_DELIVERY_HEAD t");
//                if (!roleId.equals("admin")) {
//                    WHERE("t.ent_id = #{entId}");
//                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE(splitJointIn("t.DATA_STATUS", dataStatus));
                }
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.BILL_NO = #{billNo}");
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
 * 根据订单状态查找数据
 * findWaitGenerated
 * */
    public String findWaitGenerated(Map<String, String> paramMap) {
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT("GUID");
                SELECT("APP_TYPE");
                SELECT("APP_TIME");
                SELECT("APP_STATUS");
                SELECT("CUSTOMS_CODE");
                SELECT("COP_NO");
                SELECT("PRE_NO");
                SELECT("RKD_NO");
                SELECT("OPERATOR_CODE");
                SELECT("OPERATOR_NAME");
                SELECT("IE_FLAG");
                SELECT("TRAF_MODE");
                SELECT("TRAF_NO");
                SELECT("VOYAGE_NO");
                SELECT("BILL_NO");
                SELECT("LOGISTICS_CODE");
                SELECT("LOGISTICS_NAME");
                SELECT("UNLOAD_LOCATION");
                SELECT("NOTE");
                SELECT("DATA_STATUS");
                SELECT("CRT_ID");
                SELECT("UPD_ID");
                SELECT("ENT_ID");
                SELECT("ENT_NAME");
                SELECT("ENT_CUSTOMS_CODE");
                SELECT("LOGISTICS_NO");
                FROM("T_IMP_DELIVERY_HEAD t");
                WHERE("DATA_STATUS = #{dataStatus}");
                WHERE("rownum <= 1000");
                ORDER_BY("t.CRT_TM asc,t.LOGISTICS_NO asc");
            }
        }.toString();
    }

    public String findLogisticsData(final Map<String, String> paramMap) {
        final String dataStatus = paramMap.get("dataStatus");
        return new SQL() {
            {
                SELECT("LOGISTICS_CODE," +
                        "LOGISTICS_NAME," +
                        "LOGISTICS_NO," +
                        "BILL_NO," +
                        "NOTE," +
                        "ENT_ID," +
                        "ENT_NAME," +
                        "ENT_CUSTOMS_CODE," +
                        "GUID");
                FROM("T_IMP_LOGISTICS t");
                WHERE("data_Status = #{dataStatus}");
                WHERE("IS_DELIVERY IS NULL");
                WHERE("rownum <= 100");
                ORDER_BY("t.CRT_TM asc,t.LOGISTICS_NO asc");

            }
        }.toString();
    }

    /*
     * 入库明细单申报--提交海关updateSubmitCustom
     * */
    public String updateSubmitCustom(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        final String dataStatus = paramMap.get("dataStatus");
        final String dataStatusWhere = paramMap.get("dataStatusWhere");
        final String userId = paramMap.get("userId");
        return new SQL() {
            {
                UPDATE("T_IMP_DELIVERY_HEAD t");
                WHERE(splitJointIn("t.LOGISTICS_NO", submitKeys));
                WHERE(splitJointIn("t.DATA_STATUS", dataStatusWhere));
                SET("t.data_status = #{dataStatus}");
                SET("t.upd_tm = sysdate");
                SET("t.APP_TIME = sysdate");
                SET("t.upd_id = #{userId}");

            }
        }.toString();
    }

    public String setDeliveryData(@Param("enterprise") Enterprise enterprise, @Param("submitKeys") String submitKeys) {
        return new SQL() {
            {
                UPDATE("T_IMP_DELIVERY_HEAD t");
                WHERE(splitJointIn("t.LOGISTICS_NO", submitKeys));
                SET("t.OPERATOR_CODE = #{enterprise.customs_code}");
                SET("t.OPERATOR_NAME = #{enterprise.ent_name}");
                SET("t.ENT_ID = #{enterprise.id}");
                SET("t.ENT_NAME = #{enterprise.ent_name}");
                SET("t.ENT_CUSTOMS_CODE = #{enterprise.customs_code}");
            }
        }.toString();
    }


    public String updateLogistics(String guid) {
        return new SQL() {
            {
                UPDATE("T_IMP_LOGISTICS t");
                WHERE("t.guid = #{guid}");
                SET("t.IS_DELIVERY = 'Y'");
            }
        }.toString();
    }

    public String insertImpDelivery(@Param("impDeliveryHead") ImpDeliveryHead impDeliveryHead) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_DELIVERY_HEAD");
                if (!StringUtils.isEmpty(impDeliveryHead.getGuid())) {
                    VALUES("GUID", "#{impDeliveryHead.guid}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getApp_type())) {
                    VALUES("APP_TYPE", "#{impDeliveryHead.app_type}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getApp_time())) {
                    VALUES("APP_TIME", "#{impDeliveryHead.app_time}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getApp_status())) {
                    VALUES("APP_STATUS", "#{impDeliveryHead.app_status}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getCustoms_code())) {
                    VALUES("CUSTOMS_CODE", "#{impDeliveryHead.customs_code}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getCop_no())) {
                    VALUES("COP_NO", "#{impDeliveryHead.cop_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getPre_no())) {
                    VALUES("PRE_NO", "#{impDeliveryHead.pre_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getRkd_no())) {
                    VALUES("RKD_NO", "#{impDeliveryHead.rkd_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getOperator_code())) {
                    VALUES("OPERATOR_CODE", "#{impDeliveryHead.operator_code}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getOperator_name())) {
                    VALUES("OPERATOR_NAME", "#{impDeliveryHead.operator_name}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getIe_flag())) {
                    VALUES("IE_FLAG", "#{impDeliveryHead.ie_flag}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getTraf_mode())) {
                    VALUES("TRAF_MODE", "#{impDeliveryHead.traf_mode}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getTraf_no())) {
                    VALUES("TRAF_NO", "#{impDeliveryHead.traf_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getVoyage_no())) {
                    VALUES("VOYAGE_NO", "#{impDeliveryHead.voyage_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getBill_no())) {
                    VALUES("BILL_NO", "#{impDeliveryHead.bill_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getLogistics_code())) {
                    VALUES("LOGISTICS_CODE", "#{impDeliveryHead.logistics_code}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getLogistics_name())) {
                    VALUES("LOGISTICS_NAME", "#{impDeliveryHead.logistics_name}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getUnload_location())) {
                    VALUES("UNLOAD_LOCATION", "#{impDeliveryHead.unload_location}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getNote())) {
                    VALUES("NOTE", "#{impDeliveryHead.note}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getData_status())) {
                    VALUES("DATA_STATUS", "#{impDeliveryHead.data_status}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getCrt_id())) {
                    VALUES("CRT_ID", "#{impDeliveryHead.crt_id}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impDeliveryHead.crt_tm}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getUpd_id())) {
                    VALUES("UPD_ID", "#{impDeliveryHead.upd_id}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getUpd_tm())) {
                    VALUES("UPD_TM", "#{impDeliveryHead.upd_tm}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{impDeliveryHead.return_status}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{impDeliveryHead.return_info}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{impDeliveryHead.return_time}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getEnt_id())) {
                    VALUES("ENT_ID", "#{impDeliveryHead.ent_id}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getEnt_name())) {
                    VALUES("ENT_NAME", "#{impDeliveryHead.ent_name}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getEnt_customs_code())) {
                    VALUES("ENT_CUSTOMS_CODE", "#{impDeliveryHead.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getLogistics_no())) {
                    VALUES("LOGISTICS_NO", "#{impDeliveryHead.logistics_no}");
                }

            }
        }.toString();
    }

    /*
     * 根据企业id查找企业信息
     * */
    public String queryCompany(@Param("ent_id") String ent_id) {
        return new SQL() {
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

    //修改入库明细单状态
    public String updateDeliveryStatus(@Param("guid") String guid, @Param("dataStatus") String dataStatus) {
        return new SQL() {
            {
                UPDATE("T_IMP_DELIVERY_HEAD t");
                WHERE("t.guid = #{guid}");
                SET("t.data_status = #{dataStatus}");
                SET("t.upd_tm = sysdate");
            }
        }.toString();
    }

}
