package com.xaeport.crossborder.data.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ImpGoodsOrderSQLProvider {

    public String queryImpGoodsOrderList(Map<String, String> paramMap){
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String customsCode = paramMap.get("customsCode");
        final String tradeMode = paramMap.get("tradeMode");
        final String start = paramMap.get("start");
        final String length = paramMap.get("length");


        return new SQL(){
            {
                SELECT(" * from ( select w.*, ROWNUM AS rn from ( " +
                        "select iib.G_CODE hsCode," +
                        "iib.G_NAME goodsName," +
                        "sum(iib.TOTAL_PRICE)/10000 cargoValue");
                FROM("T_IMP_INVENTORY_BODY iib");
                WHERE("1=1");

                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("exists(select GUID from T_IMP_INVENTORY_HEAD iih where iih.guid = iib.HEAD_GUID and iih.app_time >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss'))");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("exists(select GUID from T_IMP_INVENTORY_HEAD iih where iih.guid = iib.HEAD_GUID and iih.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss'))");
                }
                if (!StringUtils.isEmpty(customsCode)){
                    WHERE("exists(select GUID from T_IMP_INVENTORY_HEAD iih where iih.guid = iib.HEAD_GUID and iih.CUSTOMS_CODE = #{customsCode})");
                }
                if (!StringUtils.isEmpty(tradeMode)){
                    WHERE("exists(select GUID from T_IMP_INVENTORY_HEAD iih where iih.guid = iib.HEAD_GUID and iih.TRADE_MODE = #{tradeMode})");
                }
                GROUP_BY("iib.G_NAME,iib.G_CODE");
                if (!"-1".equals(length)) {
                    ORDER_BY("iib.G_NAME,iib.G_CODE) w  )   WHERE rn >= #{start} AND rn < #{start} + #{length} AND rn <= 1000");
                } else {
                    ORDER_BY("iib.G_NAME,iib.G_CODE) w  )   WHERE rn >= #{start} AND rn <= 1000");
                }
            }
        }.toString();
    }

    public String queryImpGoodsOrderListCount(Map<String, String> paramMap){
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String customsCode = paramMap.get("customsCode");
        final String tradeMode = paramMap.get("tradeMode");
        return new SQL(){
            {
                SELECT(" count(1) from ( select w.*, ROWNUM AS rn from ( " +
                        "select " +
                        " iib.G_NAME goodsName ");
                FROM("T_IMP_INVENTORY_BODY iib");
                WHERE("1=1");

                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("exists(select GUID from T_IMP_INVENTORY_HEAD iih where iih.guid = iib.HEAD_GUID and iih.app_time >= to_date(#{startFlightTimes}||'00:00:00','yyyy-MM-dd hh24:mi:ss'))");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("exists(select GUID from T_IMP_INVENTORY_HEAD iih where iih.guid = iib.HEAD_GUID and iih.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss'))");
                }
                if (!StringUtils.isEmpty(customsCode)){
                    WHERE("exists(select GUID from T_IMP_INVENTORY_HEAD iih where iih.guid = iib.HEAD_GUID and iih.CUSTOMS_CODE = #{customsCode})");
                }
                if (!StringUtils.isEmpty(tradeMode)){
                    WHERE("exists(select GUID from T_IMP_INVENTORY_HEAD iih where iih.guid = iib.HEAD_GUID and iih.TRADE_MODE = #{tradeMode})");
                }
                GROUP_BY("iib.G_NAME,iib.G_CODE");
                ORDER_BY("iib.G_NAME,iib.G_CODE) w  )   WHERE rn <= 1000");


            }
        }.toString();
    }
}
