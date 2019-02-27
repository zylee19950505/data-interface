package com.xaeport.crossborder.service.trade.statistics;


import com.xaeport.crossborder.data.entity.EnterpriseBillQuantity;
import com.xaeport.crossborder.data.mapper.EntrypriseBillQuantityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EntrypriseBillQuantityService {

    @Autowired
    EntrypriseBillQuantityMapper entrypriseBillQuantityMapper;

    public List<EnterpriseBillQuantity> queryEnterpriseBillQuantityList(Map<String, String> paramMap) {
        return this.entrypriseBillQuantityMapper.queryEnterpriseBillQuantityList(paramMap);
    }
}
