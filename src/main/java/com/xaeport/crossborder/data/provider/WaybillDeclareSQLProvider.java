package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class WaybillDeclareSQLProvider extends BaseSQLProvider{

    public String queryWaybillDeclareDataList(Map<String, String> paramMap) throws Exception{
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String logisticsNo = paramMap.get("logisticsNo");
        final String logisticsStatus = paramMap.get("logisticsStatus");
        final String end = paramMap.get("end");
        return new SQL(){
            {
                SELECT("* from ( select rownum rn ,f.* from ( " +
                        " select t.LOGISTICS_NO," +
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

    public String queryWaybillDeclareCount(Map<String, String> paramMap) throws Exception{
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String logisticsNo = paramMap.get("logisticsNo");
        final String logisticsStatus = paramMap.get("logisticsStatus");
        return new SQL(){
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_LOGISTICS t");
                if(!StringUtils.isEmpty(logisticsNo)){
                    WHERE("t.LOGISTICS_NO = #{logisticsNo}");
                }
                if (!StringUtils.isEmpty(logisticsStatus)){
                    WHERE("t.DATA_STATUS = #{logisticsStatus}");
                }
                if(!StringUtils.isEmpty(startFlightTimes)){
                    WHERE("t.APP_TIME >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if(!StringUtils.isEmpty(endFlightTimes)){
                    WHERE("t.APP_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
            }
        }.toString();

    }

}
