package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpLogisticsStatus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusImportSQLProvider {

    /*
     * 导入插入impLogisticsStatus表数据
     */
    public String insertImpLogisticsStatus(@Param("impLogisticsStatus")ImpLogistics impLogisticsStatus) throws Exception {

        return new SQL() {
            {
                UPDATE("T_IMP_LOGISTICS t");
                WHERE("t.LOGISTICS_NO = #{impLogisticsStatus.logistics_no}");
                SET("t.app_status = #{impLogisticsStatus.app_status}");
                SET("t.app_type = #{impLogisticsStatus.app_type}");
                SET("t.logistics_status = #{impLogisticsStatus.logistics_status}");

                //SET("t.logistics_time = to_date(to_char(to_Timestamp(#{impLogisticsStatus.logistics_time},'yyyy-MM-dd hh24:mi:ss.ff'),'yyyy-MM-dd hh24:mi:ss'),'yyyy-MM-dd hh24:mi:ss')");
                SET("t.logistics_time = to_date(to_char(#{impLogisticsStatus.logistics_time},'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')");
                SET("t.upd_id = #{impLogisticsStatus.upd_id}");
                SET("t.upd_tm = sysdate");
                SET("t.data_status = #{impLogisticsStatus.data_status}");

            }
        }.toString();
    }


    /*
     * 查询有无重复订单号表头信息
     */
    public String isRepeatLogisticsStatusNo(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus) throws Exception {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_IMP_LOGISTICS t");
                WHERE("t.logistics_no = #{impLogisticsStatus.logistics_no}");
            }
        }.toString();
    }

    /*
    * 查询导入的运单装填是否有对应的运单
    * */
    public String  isEmptyLogisticsNo(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus)throws Exception{
        return new SQL(){
            {
                SELECT("count(1)");
                FROM("T_IMP_LOGISTICS t");
                WHERE("t.LOGISTICS_NO = #{impLogisticsStatus.logistics_no}");
            }
        }.toString();
    }

    /*
    * //判断运单是否申报成功,是否有回执
    * */
    public String  getLogisticsSuccess(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus){
        return new SQL(){
            {
                SELECT("count(1)");
                FROM("T_IMP_LOGISTICS t");
                WHERE("t.LOGISTICS_NO = #{impLogisticsStatus.logistics_no}");
                WHERE("t.RETURN_STATUS is not null");
                WHERE("t.DATA_STATUS = 'CBDS41'");
            }
        }.toString();
    }

    /*
    * 有回执,申报成功后把运单状态改为CBDS5
    * */
    public String updateLogisticsStatus(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus){
        return new SQL(){
            {
                UPDATE("T_IMP_LOGISTICS t");
                WHERE("t.LOGISTICS_NO = #{impLogisticsStatus.logistics_no}");
              /*  WHERE("t.RETURN_STATUS is not null");
                WHERE("t.DATA_STATUS = 'CBDS41'");*/
                SET("t.DATA_STATUS = 'CBDS5'");
            }
        }.toString();
    }
    /*
    *
    * */
    public String updateLogistics(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus){
        return new SQL(){
            {
                UPDATE("T_IMP_LOGISTICS t");
                WHERE("t.LOGISTICS_NO = #{impLogisticsStatus.logistics_no}");
                //能解析回执的时候放开
               // WHERE("t.RETURN_STATUS is not null");
                WHERE("t.DATA_STATUS = 'CBDS41'");
                SET("t.DATA_STATUS = 'CBDS5'");
            }
        }.toString();
    }
}
