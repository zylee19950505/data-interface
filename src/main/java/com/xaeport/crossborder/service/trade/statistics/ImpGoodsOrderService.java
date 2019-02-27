package com.xaeport.crossborder.service.trade.statistics;


import com.xaeport.crossborder.data.entity.ImpGoodsOrder;
import com.xaeport.crossborder.data.mapper.ImpGoodsOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ImpGoodsOrderService {

    @Autowired
    ImpGoodsOrderMapper impGoodsOrderMapper;


    public List<ImpGoodsOrder> queryImpGoodsOrderList(Map<String, String> paramMap) {
        return this.impGoodsOrderMapper.queryImpGoodsOrderList(paramMap);
    }


    public Integer queryImpGoodsOrderListCount(Map<String, String> paramMap) {
        return this.impGoodsOrderMapper.queryImpGoodsOrderListCount(paramMap);
    }
}
