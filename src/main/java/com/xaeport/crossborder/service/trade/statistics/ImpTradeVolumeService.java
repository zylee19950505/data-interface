package com.xaeport.crossborder.service.trade.statistics;

import com.xaeport.crossborder.data.entity.ImpTradeVolumeList;
import com.xaeport.crossborder.data.mapper.ImpTradeVolumeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ImpTradeVolumeService {


    @Autowired
    ImpTradeVolumeMapper impTradeVolumeMapper;

    public List<ImpTradeVolumeList> queryImpTradeVolumeList(Map<String, String> paramMap) {
        return this.impTradeVolumeMapper.queryImpTradeVolumeList(paramMap);

    }
}
