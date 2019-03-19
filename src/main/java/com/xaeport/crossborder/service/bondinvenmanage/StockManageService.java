package com.xaeport.crossborder.service.bondinvenmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.BwlListType;
import com.xaeport.crossborder.data.mapper.StockManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockManageService {

    @Autowired
    StockManageMapper stockManageMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    //查询账册表体数据
    public List<BwlListType> queryStockControlData(Map<String, String> paramMap) {
        return this.stockManageMapper.queryStockControlData(paramMap);
    }

    //查询账册表体数据总数
    public Integer queryStockControlCount(Map<String, String> paramMap) {
        return this.stockManageMapper.queryStockControlCount(paramMap);
    }

}
