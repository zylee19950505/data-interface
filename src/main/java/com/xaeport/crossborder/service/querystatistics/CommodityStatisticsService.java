package com.xaeport.crossborder.service.querystatistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.mapper.QueryStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommodityStatisticsService {

    @Autowired
    QueryStatisticsMapper queryStatisticsMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    /*
     * 查询清单数据
     */
    public List<ImpInventory> queryCommodity(Map<String, String> paramMap) throws Exception {
        return this.queryStatisticsMapper.queryCommodity(paramMap);
    }


}
