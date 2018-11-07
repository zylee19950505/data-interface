package com.xaeport.crossborder.service.logic;

import com.xaeport.crossborder.data.entity.ImpCrossBorderHead;
import com.xaeport.crossborder.data.mapper.LogicalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogicalService {

    @Autowired
    LogicalMapper logicalMapper;

    public List<ImpCrossBorderHead> getOrderLogicData(Map<String,String> map){
        return logicalMapper.getOrderLogicData(map);
    }

    public List<ImpCrossBorderHead> getInventoryLogicData(Map<String,String> map){
        return logicalMapper.getInventoryLogicData(map);
    }

    public List<ImpCrossBorderHead> getPaymentLogicData(Map<String,String> map){
        return logicalMapper.getPaymentLogicData(map);
    }

    public List<ImpCrossBorderHead> getLogisticsLogicData(Map<String,String> map){
        return logicalMapper.getLogisticsLogicData(map);
    }

}
