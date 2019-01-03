package com.xaeport.crossborder.service.booksandrecords;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.BwlListType;
import com.xaeport.crossborder.data.mapper.StockControlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockControlService {

    @Autowired
    StockControlMapper stockControlMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    public List<BwlListType> queryStockControlData(Map<String, String> paramMap) {
        return this.stockControlMapper.queryStockControlData(paramMap);
    }

    public Integer queryStockControlCount(Map<String, String> paramMap) {
        return this.stockControlMapper.queryStockControlCount(paramMap);
    }

}
