package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpDeliveryHead;
import com.xaeport.crossborder.data.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class DeliveryDeclareSQLProvider extends BaseSQLProvider {

//    public String findLogisticsData(final Map<String, String> paramMap) {
//        final String dataStatus = paramMap.get("dataStatus");
//        return new SQL() {
//            {
//                SELECT("LOGISTICS_CODE," +
//                        "LOGISTICS_NAME," +
//                        "LOGISTICS_NO," +
//                        "VOYAGE_NO," +
//                        "BILL_NO," +
//                        "NOTE," +
//                        "ENT_ID," +
//                        "ENT_NAME," +
//                        "ENT_CUSTOMS_CODE," +
//                        "GUID");
//                FROM("T_IMP_LOGISTICS t");
//                WHERE("data_Status = #{dataStatus}");
//                WHERE("IS_DELIVERY IS NULL");
//                WHERE("rownum <= 100");
//                ORDER_BY("t.CRT_TM asc,t.LOGISTICS_NO asc");
//
//            }
//        }.toString();
//    }
//
//    public String updateLogistics(String guid) {
//        return new SQL() {
//            {
//                UPDATE("T_IMP_LOGISTICS t");
//                WHERE("t.guid = #{guid}");
//                SET("t.IS_DELIVERY = 'Y'");
//            }
//        }.toString();
//    }

    public String queryDeliveryDeclareList(Map<String, String> paramMap) throws Exception {

        final String billNo = paramMap.get("billNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String dataStatus = paramMap.get("dataStatus");

        return new SQL() {
            {
                SELECT("BILL_NO");
                SELECT("APP_TIME");
                SELECT("count(LOGISTICS_NO) as asscount");
                SELECT("LOGISTICS_CODE");
                SELECT("LOGISTICS_NAME");
                SELECT("DATA_STATUS");
                SELECT("RETURN_STATUS");
                SELECT("RETURN_INFO");
                SELECT("RETURN_TIME");
                FROM("T_IMP_DELIVERY_HEAD t");
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE(splitJointIn("t.DATA_STATUS", dataStatus));
                }
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.BILL_NO = #{billNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.CRT_TM >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.CRT_TM <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                GROUP_BY("BILL_NO,APP_TIME,LOGISTICS_CODE,LOGISTICS_NAME,DATA_STATUS,RETURN_STATUS,RETURN_INFO,RETURN_TIME");
                ORDER_BY("t.BILL_NO,t.APP_TIME desc");
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


    //根据入库明细单状态查找数据
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
//                WHERE("rownum <= 1000");
                ORDER_BY("t.CRT_TM asc,t.LOGISTICS_NO asc");
            }
        }.toString();
    }

    //从预订数据获取能够生成入库明细单的数据
    public String findCheckGoodsInfo() {
        return new SQL() {
            {
                SELECT("GUID");
                SELECT("TOTAL_LOGISTICS_NO");
                SELECT("LOGISTICS_NO");
                SELECT("ORDER_NO");
                SELECT("LOGISTICS_CODE");
                SELECT("LOGISTICS_NAME");
                FROM("T_CHECK_GOODS_INFO t");
                WHERE("STATUS in ('23','24')");
                WHERE("IS_DELIVERY IS NULL");
                WHERE("rownum <= 100");
                ORDER_BY("t.CRT_TM asc,t.LOGISTICS_NO asc");
            }
        }.toString();
    }

    /*
     * 入库明细单申报--提交海关操作
     * */
    public String updateSubmitCustom(Map<String, String> paramMap) {
        final String submitKeys = paramMap.get("submitKeys");
        final String dataStatus = paramMap.get("dataStatus");
        final String dataStatusWhere = paramMap.get("dataStatusWhere");
        final String userId = paramMap.get("userId");
        return new SQL() {
            {
                UPDATE("T_IMP_DELIVERY_HEAD t");
                WHERE(splitJointIn("t.BILL_NO", submitKeys));
                WHERE(splitJointIn("t.DATA_STATUS", dataStatusWhere));
                SET("t.data_status = #{dataStatus}");
                SET("t.upd_tm = sysdate");
                SET("t.APP_TIME = sysdate");
                SET("t.upd_id = #{userId}");

            }
        }.toString();
    }

    //提交后海关操作后进行的入库明细单数据补充
    public String setDeliveryData(@Param("enterprise") Enterprise enterprise, @Param("submitKeys") String submitKeys) {
        return new SQL() {
            {
                UPDATE("T_IMP_DELIVERY_HEAD t");
                WHERE("DATA_STATUS = 'CBDS7'");
                WHERE(splitJointIn("t.BILL_NO", submitKeys));
                SET("t.OPERATOR_CODE = #{enterprise.customs_code}");
                SET("t.OPERATOR_NAME = #{enterprise.ent_name}");
                SET("t.ENT_ID = #{enterprise.id}");
                SET("t.ENT_NAME = #{enterprise.ent_name}");
                SET("t.ENT_CUSTOMS_CODE = #{enterprise.customs_code}");
            }
        }.toString();
    }

    //更新预定数据状态为“已生成入库明细单”
    public String updateCheckGoodsInfoStatus(String guid) {
        return new SQL() {
            {
                UPDATE("T_CHECK_GOODS_INFO t");
                WHERE("t.GUID = #{guid}");
                SET("t.IS_DELIVERY = 'Y'");
            }
        }.toString();
    }

    //插入入库明细单数据
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

    //根据运单表航班号更新运单的航班号
    public String updateDeliveryByLogistics(@Param("billNo") String billNo,@Param("voyage") String voyage) {
        return new SQL() {
            {
                UPDATE("T_IMP_DELIVERY_HEAD");
                WHERE("DATA_STATUS = 'CBDS7'");
                WHERE("BILL_NO = #{billNo}");
                SET("VOYAGE_NO = #{voyage}");
            }
        }.toString();
    }

    //查询需要填充数据的入库明细单项
    public String querydeliverytofill(String billNodata) {
        return new SQL() {
            {
                SELECT("BILL_NO");
                SELECT("LOGISTICS_CODE");
                SELECT("LOGISTICS_NAME");
                SELECT("VOYAGE_NO");
                FROM("T_IMP_DELIVERY_HEAD");
                WHERE("DATA_STATUS = 'CBDS7'");
                WHERE(splitJointIn("BILL_NO", billNodata));
                GROUP_BY("BILL_NO,LOGISTICS_CODE,LOGISTICS_NAME,VOYAGE_NO");
            }
        }.toString();
    }

    //填充入库明细单航班航次数据
    public String fillDeliveryInfo(
            @Param("deliveryHead") LinkedHashMap<String, String> deliveryHead,
            @Param("userInfo") Users userInfo
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_DELIVERY_HEAD");
                WHERE("BILL_NO = #{deliveryHead.bill_no}");
                WHERE("LOGISTICS_CODE = #{deliveryHead.logistics_code}");
                WHERE("LOGISTICS_NAME = #{deliveryHead.logistics_name}");
                WHERE("DATA_STATUS = 'CBDS7'");
                SET("VOYAGE_NO = #{deliveryHead.voyage_no}");
                SET("UPD_ID = #{userInfo.id}");
                SET("UPD_TM = sysdate");
            }
        }.toString();
    }

    //查询航班号为空的入库明细单数据项
    public String queryDeliveryByEmptyVoyage(String billNos) {
        return new SQL() {
            {
                SELECT("BILL_NO");
                FROM("T_IMP_DELIVERY_HEAD");
                WHERE("DATA_STATUS = 'CBDS7'");
                WHERE(splitJointIn("BILL_NO", billNos));
                WHERE("VOYAGE_NO IS NULL");
                GROUP_BY("BILL_NO,LOGISTICS_CODE,LOGISTICS_NAME");
            }
        }.toString();
    }

}
