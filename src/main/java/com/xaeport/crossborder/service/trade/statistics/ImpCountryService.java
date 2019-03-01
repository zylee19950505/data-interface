package com.xaeport.crossborder.service.trade.statistics;

import com.xaeport.crossborder.data.entity.ImpCountryList;
import com.xaeport.crossborder.data.mapper.ImpCountryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ImpCountryService {

    @Autowired
    ImpCountryMapper impCountryMapper;

    public List<ImpCountryList> queryImpCountryList(Map<String, String> paramMap) {
        return this.impCountryMapper.queryImpCountryList(paramMap);
    }


    public List<ImpCountryList> queryImpCountryEChart(Map<String, String> paramMap) {
        return this.impCountryMapper.queryImpCountryEChart(paramMap);
    }
}
