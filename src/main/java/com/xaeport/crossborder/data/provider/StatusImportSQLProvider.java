package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpLogisticsStatus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class StatusImportSQLProvider {

    /*
     * 导入插入impLogisticsStatus表数据
     */
    public String updateImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus) throws Exception {
        return new SQL() {
            {
                UPDATE("T_IMP_LOGISTICS t");
                WHERE("t.LOGISTICS_NO = #{impLogisticsStatus.logistics_no}");
                if(StringUtils.isEmpty(impLogisticsStatus.getApp_status())){
                    SET("t.app_status = #{impLogisticsStatus.app_status}");
                }
                if(StringUtils.isEmpty(impLogisticsStatus.getApp_type())){
                    SET("t.app_type = #{impLogisticsStatus.app_type}");
                }
                if(StringUtils.isEmpty(impLogisticsStatus.getLogistics_status())){
                    SET("t.logistics_status = #{impLogisticsStatus.logistics_status}");
                }
//                if(!StringUtils.isEmpty(impLogisticsStatus.getLogistics_time())){
//                    SET("t.logistics_time = to_date(to_char(#{impLogisticsStatus.logistics_time_char},'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')");
//                }
                if(!StringUtils.isEmpty(impLogisticsStatus.getLogistics_time_char())){
                    SET("t.logistics_time_char = #{impLogisticsStatus.logistics_time_char}");
                }
                SET("t.upd_id = #{impLogisticsStatus.upd_id}");
                SET("t.upd_tm = sysdate");
                SET("t.data_status = #{impLogisticsStatus.data_status}");
            }
        }.toString();
    }

    /*
    * 查询导入的运单装填是否有对应的运单
    * */
    public String isEmptyLogisticsNo(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus) throws Exception {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_IMP_LOGISTICS t");
                WHERE("t.LOGISTICS_NO = #{impLogisticsStatus.logistics_no}");
            }
        }.toString();
    }

    /*
    * 判断运单是否申报成功,是否有回执
    * */
    public String getLogisticsSuccess(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus) {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_IMP_LOGISTICS t");
                WHERE("t.LOGISTICS_NO = #{impLogisticsStatus.logistics_no}");
                WHERE("t.RETURN_STATUS is not null");
                WHERE("t.DATA_STATUS in ('CBDS42','CBDS5','CBDS52')");
            }
        }.toString();
    }

}
