package com.xaeport.crossborder.service.logic;

import com.xaeport.crossborder.data.entity.VerifyBondHead;
import com.xaeport.crossborder.data.mapper.ExitLogMapper;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExitLogService {

    private Log log = LogFactory.getLog(this.getClass());


    @Autowired
    ExitLogMapper exitLogMapper;

    public List<VerifyBondHead> getLogicDataByExitBondInvt(Map<String,String> map){
        return exitLogMapper.getLogicDataByExitBondInvt(map);
    }

    public List<VerifyBondHead> getLogicDataByExitPassPort(Map<String,String> map){
        return exitLogMapper.getLogicDataByExitPassPort(map);
    }

}
