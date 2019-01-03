package com.xaeport.crossborder.parser;

import com.xaeport.crossborder.configuration.SystemConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/18.
 */
@Component
public class ParserHolder {
    private Map<String, BaseParser> map;

    @PostConstruct
    public void initParser() {
        map = new HashMap<>();
        map.put(SystemConstants.RECEIPT_REPORT_TYPE_ORDER, new CebParser());//订单回执报文
        map.put(SystemConstants.RECEIPT_REPORT_TYPE_PAYMENT, new CebParser());//支付单回执报文
        map.put(SystemConstants.RECEIPT_REPORT_TYPE_LOGISTICS, new CebParser());//运单回执报文
        map.put(SystemConstants.RECEIPT_REPORT_TYPE_LOGISTICS_STATUS, new CebParser());//运单状态回执报文
        map.put(SystemConstants.RECEIPT_REPORT_TYPE_INVENTORY, new CebParser());//清单回执报文
        map.put(SystemConstants.RECEIPT_REPORT_TYPE_DELIVERY, new CebParser());//入库明细单回执报文
        map.put(SystemConstants.RECEIPT_REPORT_TYPE_SUBSCRIPTION, new CebParser());//核放单预订数据报文
        map.put(SystemConstants.RECEIPT_REPORT_TYPE_TAX, new CebParser());//电子税单报文
    }

    public BaseParser getParser(String parserType) {
        BaseParser baseParser = null;
        if (!CollectionUtils.isEmpty(map)) {
            baseParser = map.get(parserType);
        }
        return baseParser;
    }

    public void setParser(String psrserType, BaseParser parser) {
        if (!CollectionUtils.isEmpty(map)) {
            map.put(psrserType, parser);
        }
    }

}
