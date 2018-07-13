package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class WaybillImportSQLProvider extends BaseSQLProvider{
    /*
     * 导入插入impLogistics表数据
     */
    public String insertImpLogistics(@Param("impLogistics") ImpLogistics impLogistics) throws Exception {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_LOGISTICS");
                if (!StringUtils.isEmpty(impLogistics.getGuid())) {
                    VALUES("guid", "#{impLogistics.guid}");
                }
                if (!StringUtils.isEmpty(impLogistics.getApp_type())) {
                    VALUES("app_type", "#{impLogistics.app_type}");
                }
                if (!StringUtils.isEmpty(impLogistics.getApp_time())) {
                    VALUES("app_time", "#{impLogistics.app_time}");
                }
                if (!StringUtils.isEmpty(impLogistics.getApp_status())) {
                    VALUES("app_status", "#{impLogistics.app_status}");
                }
                if (!StringUtils.isEmpty(impLogistics.getLogistics_code())) {
                    VALUES("logistics_code", "#{impLogistics.logistics_code}");
                }
                if (!StringUtils.isEmpty(impLogistics.getLogistics_name())) {
                    VALUES("logistics_name", "#{impLogistics.logistics_name}");
                }
                if (!StringUtils.isEmpty(impLogistics.getLogistics_no())) {
                    VALUES("logistics_no", "#{impLogistics.logistics_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getBill_no())) {
                    VALUES("bill_no", "#{impLogistics.bill_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getFreight())) {
                    VALUES("freight", "#{impLogistics.freight}");
                }
                if (!StringUtils.isEmpty(impLogistics.getInsured_fee())) {
                    VALUES("insured_fee", "#{impLogistics.insured_fee}");
                }
                if (!StringUtils.isEmpty(impLogistics.getCurrency())) {
                    VALUES("currency", "#{impLogistics.currency}");
                }
                if (!StringUtils.isEmpty(impLogistics.getWeight())) {
                    VALUES("weight", "#{impLogistics.weight}");
                }
                if (!StringUtils.isEmpty(impLogistics.getPack_no())) {
                    VALUES("pack_no", "#{impLogistics.pack_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getGoods_info())) {
                    VALUES("goods_info", "#{impLogistics.goods_info}");
                }
                if (!StringUtils.isEmpty(impLogistics.getConsingee())) {
                    VALUES("consingee", "#{impLogistics.consingee}");
                }
                if (!StringUtils.isEmpty(impLogistics.getConsignee_address())) {
                    VALUES("consignee_address", "#{impLogistics.consignee_address}");
                }
                if (!StringUtils.isEmpty(impLogistics.getConsignee_telephone())) {
                    VALUES("consignee_telephone", "#{impLogistics.consignee_telephone}");
                }
                if (!StringUtils.isEmpty(impLogistics.getNote())) {
                    VALUES("note", "#{impLogistics.note}");
                }
                if (!StringUtils.isEmpty(impLogistics.getData_status())) {
                    VALUES("data_status", "#{impLogistics.data_status}");
                }
                if (!StringUtils.isEmpty(impLogistics.getCrt_id())) {
                    VALUES("crt_id", "#{impLogistics.crt_id}");
                }
                if (!StringUtils.isEmpty(impLogistics.getCrt_tm())) {
                    VALUES("crt_tm", "#{impLogistics.crt_tm}");
                }
                if (!StringUtils.isEmpty(impLogistics.getUpd_id())) {
                    VALUES("upd_id", "#{impLogistics.upd_id}");
                }
                if (!StringUtils.isEmpty(impLogistics.getUpd_tm())) {
                    VALUES("upd_tm", "#{impLogistics.upd_tm}");
                }

            }
        }.toString();
    }


    /*
     * 查询有无重复订单号表头信息
     */
    public String isRepeatLogisticsNo(@Param("impLogistics") ImpLogistics impLogistics) throws Exception {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_IMP_LOGISTICS t");
                WHERE("t.logistics_no = #{impLogistics.logistics_no}");
            }
        }.toString();
    }
    
}
