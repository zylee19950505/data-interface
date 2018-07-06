package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.SysLog;
import com.xaeport.crossborder.data.provider.EntManageProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */
@Mapper
public interface EntManageMapper {

    //系统日志—查询数据
    @SelectProvider(type = EntManageProvider.class, method = "querySysLogData")
    List<Map<String, String>> querySysLogData(Map<String, String> paramMap);

    //系统日志—查询总数
    @SelectProvider(type = EntManageProvider.class, method = "querySysLogCount")
    int querySysLogCount(Map<String, String> paramMap);

    //保存系统日志
    @SelectProvider(type =EntManageProvider.class,method = "saveByHiber")
	void saveByHiber(@Param("sysLog") SysLog sysLog);
}
