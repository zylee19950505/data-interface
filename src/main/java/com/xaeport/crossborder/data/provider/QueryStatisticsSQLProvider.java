package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class QueryStatisticsSQLProvider extends BaseSQLProvider {

    public String queryEbusinessEnt() throws Exception {
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_ENTERPRISE t");
                WHERE("t.ENT_BUSINESS_TYPE = 'E-business'");
                ORDER_BY("t.CRT_TM desc");
            }
        }.toString();
    }

    //贸易国统计数据
    public String queryTradeCountryData(Map<String, String> paramMap) throws Exception {

        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String ieFlag = paramMap.get("ieFlag");
        final String entId = paramMap.get("entId");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT("(SELECT a.CA_CN_NAME FROM T_COUNTRY_AREA a WHERE a.CA_CODE = t.COUNTRY) country," +
                        "count(t.INVT_NO) amount," +
                        "sum(t.TOTAL_PRICES) totalPrice," +
                        "sum(t.GROSS_WEIGHT) totalGrossWeight," +
                        "sum(t.NET_WEIGHT) totalNetWeight," +
                        "(SELECT c.CURR_CN_NAME FROM T_CURRENCY c WHERE c.CURR_CODE = t.CURRENCY) currency," +
                        "sum(t.VALUE_ADDED_TAX) totalTax");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!StringUtils.isEmpty(ieFlag)) {
                    WHERE("t.IE_FLAG = #{ieFlag}");
                }
                if (!StringUtils.isEmpty(entId)) {
                    WHERE("t.ENT_ID = #{entId}");
                } else {
                    WHERE("t.ENT_ID in (SELECT tt.ID FROM T_ENTERPRISE tt WHERE tt.ENT_BUSINESS_TYPE = 'E-business')");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.DATA_STATUS = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.RETURN_STATUS = #{returnStatus}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.APP_TIME >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.APP_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                GROUP_BY("t.COUNTRY,t.CURRENCY");
            }
        }.toString();
    }

    //通关统计数据
    public String queryCustoms(Map<String, String> paramMap) throws Exception {

        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String ieFlag = paramMap.get("ieFlag");
        final String entId = paramMap.get("entId");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT("t.ENT_NAME," +
                        "t.ENT_CUSTOMS_CODE," +
                        "sum(t.TOTAL_PRICES) totalPrice," +
                        "(select c.curr_cn_name from t_currency c where c.curr_code = t.CURRENCY) currency," +
                        "count(distinct t.BILL_NO) billNoCount," +
                        "count(t.INVT_NO) amount," +
                        "sum(t.GROSS_WEIGHT) totalGrossWeight," +
                        "sum(t.VALUE_ADDED_TAX) totalTax");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!StringUtils.isEmpty(ieFlag)) {
                    WHERE("t.IE_FLAG = #{ieFlag}");
                }
                if (!StringUtils.isEmpty(entId)) {
                    WHERE("t.ENT_ID = #{entId}");
                } else {
                    WHERE("t.ENT_ID in (SELECT tt.ID FROM T_ENTERPRISE tt WHERE tt.ENT_BUSINESS_TYPE = 'E-business')");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.DATA_STATUS = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.RETURN_STATUS = #{returnStatus}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.APP_TIME >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.APP_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                GROUP_BY("t.ENT_NAME,t.ENT_CUSTOMS_CODE,t.CURRENCY");
            }
        }.toString();
    }

    //商品统计数据
    public String queryCommodity(Map<String, String> paramMap) throws Exception {

        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String ieFlag = paramMap.get("ieFlag");
        final String entId = paramMap.get("entId");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT("(select tt.product_name from T_PRODUCTCODE tt where tt.CUSTOMS_CODE = t.G_CODE) product_name," +
                        "t.G_CODE," +
                        "count(t.head_guid) amount," +
                        "sum(t.TOTAL_PRICE) totalPrice," +
                        "(select c.curr_cn_name from t_currency c where c.curr_code = t.CURRENCY) currency," +
                        "sum(t.VALUE_ADDED_TAX) totalTax");
                FROM("T_IMP_INVENTORY_BODY t,T_IMP_INVENTORY_HEAD h");
                WHERE("t.HEAD_GUID = h.GUID");
                if (!StringUtils.isEmpty(ieFlag)) {
                    WHERE("h.IE_FLAG = #{ieFlag}");
                }
                if (!StringUtils.isEmpty(entId)) {
                    WHERE("h.ENT_ID = #{entId}");
                } else {
                    WHERE("h.ENT_ID in (SELECT tt.ID FROM T_ENTERPRISE tt WHERE tt.ENT_BUSINESS_TYPE = 'E-business')");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("h.DATA_STATUS = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("h.RETURN_STATUS = #{returnStatus}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("h.APP_TIME >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("h.APP_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                GROUP_BY("t.G_CODE,t.CURRENCY");
                ORDER_BY("totalPrice DESC");
            }
        }.toString();
    }

    //查询清单页面数据
    public String queryInventoryQueryList(Map<String, String> paramMap) throws Exception {

        final String end = paramMap.get("end");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String ieFlag = paramMap.get("ieFlag");
        final String entId = paramMap.get("entId");
        final String billNo = paramMap.get("billNo");
        final String invtNo = paramMap.get("invtNo");
        final String gName = paramMap.get("gName");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT " +
                        "t.guid," +
                        "t.invt_no," +
                        "t.bill_no," +
                        "t.logistics_no," +
                        "t.app_time," +
                        "t.value_added_tax," +
                        "t.gross_weight," +
                        "t.net_weight," +
                        "t.TOTAL_PRICES total_prices," +
                        "t.buyer_name");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!StringUtils.isEmpty(ieFlag)) {
                    WHERE("t.IE_FLAG = #{ieFlag}");
                }
                if (!StringUtils.isEmpty(entId)) {
                    WHERE("t.ENT_ID = #{entId}");
                } else {
                    WHERE("t.ENT_ID in (SELECT tt.ID FROM T_ENTERPRISE tt WHERE tt.ENT_BUSINESS_TYPE = 'E-business')");
                }
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.BILL_NO = #{billNo}");
                }
                if (!StringUtils.isEmpty(invtNo)) {
                    WHERE("t.INVT_NO = #{invtNo}");
                }
                if (!StringUtils.isEmpty(gName)) {
                    WHERE("t.guid in ( select tt.HEAD_GUID from T_IMP_INVENTORY_BODY tt where tt.g_name like '%'||#{gName}||'%' )");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.DATA_STATUS = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.RETURN_STATUS = #{returnStatus}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.app_time >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.app_time <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.APP_TIME desc ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.APP_TIME desc ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

    //查询清单页面数据总数
    public String queryInventoryQueryCount(Map<String, String> paramMap) throws Exception {

        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String ieFlag = paramMap.get("ieFlag");
        final String entId = paramMap.get("entId");
        final String billNo = paramMap.get("billNo");
        final String invtNo = paramMap.get("invtNo");
        final String gName = paramMap.get("gName");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_INVENTORY_HEAD t");
                if (!StringUtils.isEmpty(ieFlag)) {
                    WHERE("t.IE_FLAG = #{ieFlag}");
                }
                if (!StringUtils.isEmpty(entId)) {
                    WHERE("t.ENT_ID = #{entId}");
                } else {
                    WHERE("t.ENT_ID in (SELECT tt.ID FROM T_ENTERPRISE tt WHERE tt.ENT_BUSINESS_TYPE = 'E-business')");
                }
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.BILL_NO = #{billNo}");
                }
                if (!StringUtils.isEmpty(invtNo)) {
                    WHERE("t.INVT_NO = #{invtNo}");
                }
                if (!StringUtils.isEmpty(gName)) {
                    WHERE("t.guid in ( select tt.HEAD_GUID from T_IMP_INVENTORY_BODY tt where tt.g_name like '%'||#{gName}||'%' )");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.DATA_STATUS = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.RETURN_STATUS = #{returnStatus}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.APP_TIME >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.APP_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
            }
        }.toString();
    }

    //查询清单excel下载数据
    public String queryInventoryExcelList(Map<String, String> paramMap) throws Exception {

        final String end = paramMap.get("end");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String ieFlag = paramMap.get("ieFlag");
        final String entId = paramMap.get("entId");
        final String billNo = paramMap.get("billNo");
        final String invtNo = paramMap.get("invtNo");
        final String gName = paramMap.get("gName");
        final String dataStatus = paramMap.get("dataStatus");
        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT(" * from ( select rownum rn, f.* from ( " +
                        " SELECT " +
                        "t.GUID," +
                        "t.INVT_NO," +
                        "t.BILL_NO," +
                        "t.LOGISTICS_NO," +
                        "t.APP_TIME," +
                        "t.VALUE_ADDED_TAX TOTAL_TAX," +
                        "t.GROSS_WEIGHT," +
                        "t.NET_WEIGHT," +
                        "t.BUYER_NAME," +
                        "t.BUYER_ID_NUMBER," +
                        "t.BUYER_TELEPHONE," +
                        "t.CONSIGNEE_ADDRESS," +
                        "tt.G_NAME," +
                        "tt.PRICE," +
                        "tt.TOTAL_PRICE," +
                        "tt.VALUE_ADDED_TAX SINGLE_TAX");
                FROM("T_IMP_INVENTORY_HEAD t");
                LEFT_OUTER_JOIN("T_IMP_INVENTORY_BODY tt ON t.GUID = tt.HEAD_GUID");
                if (!StringUtils.isEmpty(ieFlag)) {
                    WHERE("t.IE_FLAG = #{ieFlag}");
                }
                if (!StringUtils.isEmpty(entId)) {
                    WHERE("t.ENT_ID = #{entId}");
                } else {
                    WHERE("t.ENT_ID in (SELECT tt.ID FROM T_ENTERPRISE tt WHERE tt.ENT_BUSINESS_TYPE = 'E-business')");
                }
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.BILL_NO = #{billNo}");
                }
                if (!StringUtils.isEmpty(invtNo)) {
                    WHERE("t.INVT_NO = #{invtNo}");
                }
                if (!StringUtils.isEmpty(gName)) {
                    WHERE("t.GUID in ( select tt.HEAD_GUID from T_IMP_INVENTORY_BODY tt where tt.g_name like '%'||#{gName}||'%' )");
                }
                if (!StringUtils.isEmpty(dataStatus)) {
                    WHERE("t.DATA_STATUS = #{dataStatus}");
                }
                if (!StringUtils.isEmpty(returnStatus)) {
                    WHERE("t.RETURN_STATUS = #{returnStatus}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.APP_TIME >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.APP_TIME <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!"-1".equals(end)) {
                    ORDER_BY("t.APP_TIME DESC,t.BILL_NO,t.INVT_NO ) f  )  WHERE rn between #{start} and #{end}");
                } else {
                    ORDER_BY("t.APP_TIME DESC,t.BILL_NO,t.INVT_NO ) f  )  WHERE rn >= #{start}");
                }
            }
        }.toString();
    }

}
