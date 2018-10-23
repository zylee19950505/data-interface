package com.xaeport.crossborder.parser;

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
        map.put("ceb312", new Ceb312Parser());//订单回执报文
        map.put("ceb412", new Ceb412Parser());//支付单回执报文
        map.put("ceb512", new Ceb512Parser());//运单回执报文
        map.put("ceb514", new Ceb514Parser());//运单状态回执报文
        map.put("ceb622", new Ceb622Parser());//清单回执报文
        map.put("ceb712", new Ceb712Parser());//入库明细单回执报文
        map.put("CheckGoodsInfo", new CheckGoodsInfoParser());//核放单预订数据报文
        map.put("TAX", new Ceb816Parser());//电子税单报文
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
