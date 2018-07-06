package com.xaeport.crossborder.service.sysmanage;

import com.xaeport.crossborder.data.entity.SysLog;
import com.xaeport.crossborder.data.mapper.EntManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wx on 2018/4/27.
 */
@Service
public class SysLogSerivce {

    @Autowired
    EntManageMapper sysLogMapper;

    /*
     * 查看系统日志
     */
    public List<Map<String, String>> querySysLog(Map<String, String> paramMap) {
        List<Map<String, String>> sysLogList = this.sysLogMapper.querySysLogData(paramMap);
        return sysLogList;
    }


    /*
     * 系统日志总数
     */
    public int querySysLogCount(Map<String,String> paramMap){
        return this.sysLogMapper.querySysLogCount(paramMap);
    }


    /*
    * 保存系统日志
    * */
    public void saveByHiber(SysLog sysLog) {
        sysLogMapper.saveByHiber(sysLog);
    }
}
