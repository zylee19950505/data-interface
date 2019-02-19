package com.xaeport.crossborder.controller.api.trade.statistics;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trade")
public class TradeStatisticsApi  extends BaseApi {

    private Log logger = LogFactory.getLog(this.getClass());

    @RequestMapping("/imp/trade/volume")
    public ResponseData searchImportTradeVolume(){


        return null;
    }

}
