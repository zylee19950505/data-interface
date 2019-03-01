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

    public List<ImpTradeVolumeList> queryImpTradeVolumeEChart(Map<String, String> paramMap) {
        //截取年
       String startFlightyear = String.valueOf(Integer.valueOf(paramMap.get("endFlightTimes").substring(0,4))-1);
       String stratFlightMouth = paramMap.get("endFlightTimes").substring(4);
       String startFlightTimes = startFlightyear+stratFlightMouth;
       paramMap.put("startFlightTimes",startFlightTimes);
       return this.impTradeVolumeMapper.queryImpTradeVolumeList(paramMap);
    }
}
