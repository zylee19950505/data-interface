package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.SysLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */
public class EntManageProvider extends BaseSQLProvider {

    //系统日志—查询数据
    public String querySysLogData(Map<String,String> paramMap) throws Exception{
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String module = paramMap.get("module");
        return new SQL(){
            {
                SELECT(" * from ( select rownum rn , t.* from ( " +
                        "select t.*" );
                FROM("t_syslog t");
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.operatTime >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.operatTime <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(module)) {
                    WHERE("t.module = #{module}");
                }
                ORDER_BY("t.userId desc ) t ) where rn between #{start} and #{end} ");
            }
        }.toString();
    }


    //系统日志—查询总数
    public String querySysLogCount(Map<String,String> paramMap) throws Exception{
        final String startFlightTimes = paramMap.get("startFlightTimes");
        final String endFlightTimes = paramMap.get("endFlightTimes");
        final String module = paramMap.get("module");
        return new SQL(){
            {
                SELECT(" count(1) from ( select t.userId" );
                FROM("t_syslog t");
                if (!StringUtils.isEmpty(startFlightTimes)) {
                    WHERE("t.operatTime >= to_date(#{startFlightTimes}||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(endFlightTimes)) {
                    WHERE("t.operatTime <= to_date(#{endFlightTimes}||'23:59:59','yyyy-MM-dd hh24:mi:ss')");
                }
                if (!StringUtils.isEmpty(module)) {
                    WHERE("t.module = #{module}");
                }
                ORDER_BY("t.userId desc )");
            }
        }.toString();
    }

    //系统日志-保存系统日志
    public String saveByHiber(@Param("sysLog")SysLog sysLog)throws Exception{
       return new SQL(){
            {
                INSERT_INTO("t_SYSLOG");
                if (!StringUtils.isEmpty(sysLog.getId())){
                    VALUES("id","#{sysLog.id}");
                }
                if (!StringUtils.isEmpty(sysLog.getUserId())){
                    VALUES("userId","#{sysLog.userId}");
                }
                if (!StringUtils.isEmpty(sysLog.getIp())) {
                    VALUES("ip", "#{sysLog.ip}");
                }
                if (!StringUtils.isEmpty(sysLog.getModule())) {
                    VALUES("module", "#{sysLog.module}");
                }
                if (!StringUtils.isEmpty(sysLog.getType())) {
                    VALUES("type", "#{sysLog.type}");
                }
                if (!StringUtils.isEmpty(sysLog.getOperatTime())) {
                    VALUES("operatTime", "#{sysLog.operatTime}");
                }
                if (!StringUtils.isEmpty(sysLog.getNote())) {
                    VALUES("note", "#{sysLog.note}");
                }
                if (!StringUtils.isEmpty(sysLog.getCreatorId())) {
                    VALUES("creatorId", "#{sysLog.creatorId}");
                }
                if (!StringUtils.isEmpty(sysLog.getCreateTime())) {
                    VALUES("createTime", "#{sysLog.createTime}");
                }
                if (!StringUtils.isEmpty(sysLog.getUpdatorId())) {
                    VALUES("updatorId", "#{sysLog.updatorId}");
                }
                if (!StringUtils.isEmpty(sysLog.getUpdateTime())) {
                    VALUES("updateTime", "#{sysLog.updateTime}");
                }
            }
        }.toString();
    }

}
