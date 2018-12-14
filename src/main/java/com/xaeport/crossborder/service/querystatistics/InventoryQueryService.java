package com.xaeport.crossborder.service.querystatistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.mapper.QueryStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryQueryService {

    @Autowired
    QueryStatisticsMapper queryStatisticsMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    /*
     * 查询清单数据
     */
    public List<ImpInventoryHead> queryInventoryQueryList(Map<String, String> paramMap) throws Exception {
        return this.queryStatisticsMapper.queryInventoryQueryList(paramMap);
    }

    /*
     * 查询清单总数
     */
    public Integer queryInventoryQueryCount(Map<String, String> paramMap) throws Exception {
        return this.queryStatisticsMapper.queryInventoryQueryCount(paramMap);
    }

    /*
     * 查询导出excel表格数据
     */
    public List<ImpInventory> queryInventoryExcelList(Map<String, String> paramMap) throws Exception {
        return this.queryStatisticsMapper.queryInventoryExcelList(paramMap);
    }

    /*
     * 查询电商企业
     */
    public List<Enterprise> queryEbusinessEnt() throws Exception {
        return this.queryStatisticsMapper.queryEbusinessEnt();
    }


}
