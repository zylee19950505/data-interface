package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.SysLog;
import com.xaeport.crossborder.data.provider.SysLogProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/4/27.
 */
@Mapper
public interface SysLogMapper {

    //系统日志—查询数据
    @SelectProvider(type = SysLogProvider.class, method = "querySysLogData")
    List<Map<String, String>> querySysLogData(Map<String, String> paramMap);

    //系统日志—查询总数
    @SelectProvider(type = SysLogProvider.class, method = "querySysLogCount")
    int querySysLogCount(Map<String, String> paramMap);

    //保存系统日志
    @SelectProvider(type =SysLogProvider.class,method = "saveByHiber")
	void saveByHiber(@Param("sysLog") SysLog sysLog);
}
