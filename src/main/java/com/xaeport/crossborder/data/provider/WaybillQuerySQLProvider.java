package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class WaybillQuerySQLProvider {

    public String queryWaybillQueryDataList(Map<String, String> paramMap) throws Exception{
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String logisticsNo = paramMap.get("logisticsNo");
        final String logisticsStatus = paramMap.get("logisticsStatus");
        final String end = paramMap.get("end");
        return new SQL(){
            {
                SELECT("* from ( select rownum rn ,f.* from ( " +
                        " select " +
                        " t.GUID," +
                        " t.LOGISTICS_NO," +
                        " t.LOGISTICS_NAME,"+
                        " t.CONSINGEE,"+
                        " t.CONSIGNEE_TELEPHONE," +
                        " t.CONSIGNEE_ADDRESS," +
                        " t.DATA_STATUS," +
                        " t.APP_TIME" );
                FROM("T_IMP_LOGISTICS t");
                if(!StringUtils.isEmpty(logisticsNo)){
                    WHERE("t.logistics_no = #{logisticsNo}");
                }
                if (!StringUtils.isEmpty(logisticsStatus)){
                    WHERE("t.data_status = #{logisticsStatus}");
                }
                if(!StringUtils.isEmpty(startFlightTimes)){
                    WHERE("t.app_time >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if(!StringUtils.isEmpty(endFlightTimes)){
                    WHERE("t.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.app_time desc ) f  ) WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.app_time desc ) f  ) WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    public String queryWaybillQueryCount(Map<String, String> paramMap) throws Exception{
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String logisticsNo = paramMap.get("logisticsNo");
        final String logisticsStatus = paramMap.get("logisticsStatus");
        return new SQL(){
            {
                SELECT("count(1)");
                FROM("T_IMP_LOGISTICS t");
                if(!StringUtils.isEmpty(logisticsNo)){
                    WHERE("t.logistics_no = #{logisticsNo}");
                }
                if (!StringUtils.isEmpty(logisticsStatus)){
                    WHERE("t.data_status = #{logisticsStatus}");
                }
                if(!StringUtils.isEmpty(startFlightTimes)){
                    WHERE("t.app_time >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if(!StringUtils.isEmpty(endFlightTimes)){
                    WHERE("t.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
            }
        }.toString();

    }
    public String waybillQueryById(Map<String,String> paramMap){
        final String guid = paramMap.get("guid");
        final String logisticsno = paramMap.get("logisticsno");

        return new SQL(){
            {
                SELECT("t.GUID as guid");
                SELECT("t.APP_TYPE as appType");
                SELECT("t.APP_TIME as appTime");
                SELECT("t.APP_STATUS as appStatus");
                SELECT("t.LOGISTICS_CODE as logisticscode");
                SELECT("t.LOGISTICS_NAME as logisticsname");
                SELECT("t.LOGISTICS_NO as logisticsno");
                SELECT("t.BILL_NO as billno");
                SELECT("t.FREIGHT as freight");
                SELECT("t.INSURED_FEE as insuredfee");
                SELECT("t.CURRENCY as currency");
                SELECT("t.WEIGHT as weight");
                SELECT("t.PACK_NO as packno");
                SELECT("t.GOODS_INFO as goodsinfo");
                SELECT("t.CONSINGEE as consingee");
                SELECT("t.CONSIGNEE_ADDRESS as consigneeaddress");
                SELECT("t.CONSIGNEE_TELEPHONE as consigneetelephone");
                SELECT("t.NOTE as note");
                SELECT("t.DATA_STATUS as dataStatus");
                SELECT("t.CRT_ID as crtId");
                SELECT("t.CRT_TM as crtTm");
                SELECT("t.UPD_ID as updId");
                SELECT("t.UPD_TM as updTm");
                FROM("T_IMP_LOGISTICS t");
                if (!StringUtils.isEmpty(guid)){
                    WHERE("t.GUID = #{guid}");
                }
               /* if (!StringUtils.isEmpty(logisticsno)){
                    WHERE("t.ORDER_NO = #{logisticsno}");
                }*/
            }
        }.toString();
    }

    public String updateBillHead(LinkedHashMap<String, String> entryHead){
        return new SQL(){
            {
                UPDATE("T_IMP_LOGISTICS t");
                WHERE("t.GUID = #{entryhead_guid}");
                if (!StringUtils.isEmpty(entryHead.get("logistics_code"))){
                    SET("t.LOGISTICS_CODE = #{logistics_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("logistics_name"))){
                    SET("t.LOGISTICS_NAME = #{logistics_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("consingee"))){
                    SET("t.CONSINGEE = #{consingee}");
                }
                if (!StringUtils.isEmpty(entryHead.get("consignee_telephone"))){
                    SET("t.CONSIGNEE_TELEPHONE = #{consignee_telephone}");
                }
                if (!StringUtils.isEmpty(entryHead.get("consignee_address"))){
                    SET("t.CONSIGNEE_ADDRESS = #{consignee_address}");
                }
                if (!StringUtils.isEmpty(entryHead.get("freight"))){
                    SET("t.FREIGHT = #{freight}");
                }
                if (!StringUtils.isEmpty(entryHead.get("insured_fee"))){
                    SET("t.INSURED_FEE = #{insured_fee}");
                }
                if (!StringUtils.isEmpty(entryHead.get("pack_no"))){
                    SET("t.PACK_NO = #{pack_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("weight"))){
                    SET("t.WEIGHT = #{weight}");
                }
                if (!StringUtils.isEmpty(entryHead.get("note"))){
                    SET("t.NOTE = #{note}");
                }
            }
        }.toString();
    }
}
