package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.ImpLogisticsStatus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class StatusImportSQLProvider {

    /*
     * 导入插入impLogisticsStatus表数据
     */
    public String insertImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus) throws Exception {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_LOGISTICS_STATUS");
                if (!StringUtils.isEmpty(impLogisticsStatus.getGuid())) {
                    VALUES("guid", "#{impLogisticsStatus.guid}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getApp_type())) {
                    VALUES("app_type", "#{impLogisticsStatus.app_type}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getApp_time())) {
                    VALUES("app_time", "#{impLogisticsStatus.app_time}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getApp_status())) {
                    VALUES("app_status", "#{impLogisticsStatus.app_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_code())) {
                    VALUES("logistics_code", "#{impLogisticsStatus.logistics_code}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_name())) {
                    VALUES("logistics_name", "#{impLogisticsStatus.logistics_name}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_no())) {
                    VALUES("logistics_no", "#{impLogisticsStatus.logistics_no}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_status())) {
                    VALUES("logistics_status", "#{impLogisticsStatus.logistics_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_time())) {
                    VALUES("logistics_time", "#{impLogisticsStatus.logistics_time}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getNote())) {
                    VALUES("note", "#{impLogisticsStatus.note}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getData_status())) {
                    VALUES("data_status", "#{impLogisticsStatus.data_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getCrt_id())) {
                    VALUES("crt_id", "#{impLogisticsStatus.crt_id}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getCrt_tm())) {
                    VALUES("crt_tm", "#{impLogisticsStatus.crt_tm}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getUpd_id())) {
                    VALUES("upd_id", "#{impLogisticsStatus.upd_id}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getUpd_tm())) {
                    VALUES("upd_tm", "#{impLogisticsStatus.upd_tm}");
                }

            }
        }.toString();
    }


    /*
     * 查询有无重复订单号表头信息
     */
    public String isRepeatLogisticsStatusNo(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus) throws Exception {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_IMP_LOGISTICS_STATUS t");
                WHERE("t.logistics_no = #{impLogisticsStatus.logistics_no}");
            }
        }.toString();
    }

}
