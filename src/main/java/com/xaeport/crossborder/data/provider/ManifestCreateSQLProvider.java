package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.ManifestHead;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ManifestCreateSQLProvider extends BaseSQLProvider {

    public String queryCheckGoodsInfoList(Map<String, String> paramMap) throws Exception {
        final String billNo = paramMap.get("billNo");
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
//        final String returnStatus = paramMap.get("returnStatus");

        return new SQL() {
            {
                SELECT("TOTAL_LOGISTICS_NO");
                SELECT("(select count(entry_id) from T_CHECK_GOODS_INFO tt where tt.TOTAL_LOGISTICS_NO = t.TOTAL_LOGISTICS_NO) totalSum");
                SELECT("count(entry_id) releaseSum");
                SELECT("sum(t.GROSS_WT) grossWtSum");
                SELECT("sum(t.NET_WT) netWtSum");
                SELECT("sum(t.GOODS_VALUE) goodsValueSum");
                FROM("T_CHECK_GOODS_INFO t");
                WHERE("t.STATUS = '800'");
                WHERE("t.IS_MANIFEST = 'N'");
                if (!StringUtils.isEmpty(billNo)) {
                    WHERE("t.TOTAL_LOGISTICS_NO = #{billNo}");
                }
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.CRT_TM >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.CRT_TM <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                GROUP_BY("TOTAL_LOGISTICS_NO");
            }
        }.toString();
    }

    public String queryCheckGoodsList(Map<String, String> paramMap) throws Exception {
        final String totalLogisticsNo = paramMap.get("totalLogisticsNo");
        return new SQL() {
            {
                SELECT("TOTAL_LOGISTICS_NO");
                SELECT("(select count(entry_id) from T_CHECK_GOODS_INFO tt where tt.TOTAL_LOGISTICS_NO = t.TOTAL_LOGISTICS_NO) totalSum");
                SELECT("count(entry_id) releaseSum");
                SELECT("sum(t.GROSS_WT) grossWtSum");
                SELECT("sum(t.NET_WT) netWtSum");
                SELECT("sum(t.GOODS_VALUE) goodsValueSum");
                FROM("T_CHECK_GOODS_INFO t");
                WHERE("t.STATUS = '800'");
                WHERE("t.IS_MANIFEST = 'N'");
                if (!StringUtils.isEmpty(totalLogisticsNo)) {
                    WHERE(splitJointIn("t.TOTAL_LOGISTICS_NO", totalLogisticsNo));
                }
                GROUP_BY("TOTAL_LOGISTICS_NO");
            }
        }.toString();
    }

    public String queryManifestSum(Map<String, String> paramMap) throws Exception {
        final String userId = paramMap.get("userId");
        final String entId = paramMap.get("entId");
        final String roleId = paramMap.get("roleId");
        final String totalLogisticsNo = paramMap.get("totalLogisticsNo");

        return new SQL() {
            {
                SELECT("count(t.entry_id) releaseSum");
                SELECT("sum(t.GROSS_WT) grossWtSum");
                SELECT("sum(t.NET_WT) netWtSum");
                SELECT("sum(t.GOODS_VALUE) goodsValueSum");
                FROM("T_CHECK_GOODS_INFO t");
                if (!StringUtils.isEmpty(totalLogisticsNo)) {
                    WHERE(splitJointIn("t.TOTAL_LOGISTICS_NO", totalLogisticsNo));
                }
                WHERE("t.STATUS = '800'");
                WHERE("t.IS_MANIFEST = 'N'");
            }
        }.toString();
    }

    public String updateCheckGoodsData(@Param("manifestNo") String manifestNo, @Param("bill_nos") String bill_nos) {
        return new SQL() {
            {
                UPDATE("T_CHECK_GOODS_INFO t");
                if (!StringUtils.isEmpty(bill_nos)) {
                    WHERE(splitJointIn("t.TOTAL_LOGISTICS_NO", bill_nos));
                }
                WHERE("t.STATUS = '800'");
                WHERE("t.IS_MANIFEST = 'N'");
                if (!StringUtils.isEmpty(manifestNo)) {
                    SET("t.MANIFEST_NO = #{manifestNo}");
                }
                SET("t.IS_MANIFEST = 'Y'");
            }
        }.toString();
    }

    public String saveManifest(LinkedHashMap<String, String> entryHead) {
        return new SQL() {
            {
                INSERT_INTO("T_MANIFEST_HEAD t");
                if (!StringUtils.isEmpty(entryHead.get("auto_id"))) {
                    VALUES("t.auto_id", "#{auto_id}");
                }
                if (!StringUtils.isEmpty(entryHead.get("bill_nos"))) {
                    VALUES("t.bill_nos", "#{bill_nos}");
                }

                if (!StringUtils.isEmpty(entryHead.get("manifest_no"))) {
                    VALUES("t.manifest_no", "#{manifest_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("customs_code"))) {
                    VALUES("t.customs_code", "#{customs_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("biz_type"))) {
                    VALUES("t.biz_type", "#{biz_type}");
                }
                if (!StringUtils.isEmpty(entryHead.get("biz_mode"))) {
                    VALUES("t.biz_mode", "#{biz_mode}");
                }
                if (!StringUtils.isEmpty(entryHead.get("i_e_flag"))) {
                    VALUES("t.i_e_flag", "#{i_e_flag}");
                }
                if (!StringUtils.isEmpty(entryHead.get("i_e_mark"))) {
                    VALUES("t.i_e_mark", "#{i_e_mark}");
                }

                if (!StringUtils.isEmpty(entryHead.get("trade_mode"))) {
                    VALUES("t.trade_mode", "#{trade_mode}");
                }
                if (!StringUtils.isEmpty(entryHead.get("delivery_way"))) {
                    VALUES("t.delivery_way", "#{delivery_way}");
                }

                if (!StringUtils.isEmpty(entryHead.get("start_land"))) {
                    VALUES("t.start_land", "#{start_land}");
                }
                if (!StringUtils.isEmpty(entryHead.get("goal_land"))) {
                    VALUES("t.goal_land", "#{goal_land}");
                }

                if (!StringUtils.isEmpty(entryHead.get("goods_wt"))) {
                    VALUES("t.goods_wt", "#{goods_wt}");
                }
                if (!StringUtils.isEmpty(entryHead.get("fact_weight"))) {
                    VALUES("t.fact_weight", "#{fact_weight}");
                }
                if (!StringUtils.isEmpty(entryHead.get("pack_no"))) {
                    VALUES("t.pack_no", "#{pack_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("sum_goods_value"))) {
                    VALUES("t.sum_goods_value", "#{sum_goods_value}");
                }

                if (!StringUtils.isEmpty(entryHead.get("m_status"))) {
                    VALUES("t.m_status", "#{m_status}");
                }
                if (!StringUtils.isEmpty(entryHead.get("b_status"))) {
                    VALUES("t.b_status", "#{b_status}");
                }
                if (!StringUtils.isEmpty(entryHead.get("status"))) {
                    VALUES("t.status", "#{status}");
                }
                if (!StringUtils.isEmpty(entryHead.get("port_status"))) {
                    VALUES("t.port_status", "#{port_status}");
                }

                if (!StringUtils.isEmpty(entryHead.get("input_name"))) {
                    VALUES("t.input_name", "#{input_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("input_code"))) {
                    VALUES("t.input_code", "#{input_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("trade_name"))) {
                    VALUES("t.trade_name", "#{trade_name}");
                }
                if (!StringUtils.isEmpty(entryHead.get("trade_code"))) {
                    VALUES("t.trade_code", "#{trade_code}");
                }

                if (!StringUtils.isEmpty(entryHead.get("app_person"))) {
                    VALUES("t.app_person", "#{app_person}");
                }
                if (!StringUtils.isEmpty(entryHead.get("region_code"))) {
                    VALUES("t.region_code", "#{region_code}");
                }
                if (!StringUtils.isEmpty(entryHead.get("plat_from"))) {
                    VALUES("t.plat_from", "#{plat_from}");
                }
                if (!StringUtils.isEmpty(entryHead.get("note"))) {
                    VALUES("t.note", "#{note}");
                }
                if (!StringUtils.isEmpty(entryHead.get("extend_field_3"))) {
                    VALUES("t.extend_field_3", "#{extend_field_3}");
                }

                if (!StringUtils.isEmpty(entryHead.get("car_no"))) {
                    VALUES("t.car_no", "#{car_no}");
                }
                if (!StringUtils.isEmpty(entryHead.get("car_wt"))) {
                    VALUES("t.car_wt", "#{car_wt}");
                }
                if (!StringUtils.isEmpty(entryHead.get("ic_code"))) {
                    VALUES("t.ic_code", "#{ic_code}");
                }

                VALUES("t.data_status","'CBDS8'");

                VALUES("t.app_date", "sysdate");

                VALUES("t.create_time", "sysdate");


            }
        }.toString();
    }

}
