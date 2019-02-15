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
    private Map<String, BaseParserNew> mapNew;

    @PostConstruct
    public void initParser() {
        map = new HashMap<>();
        mapNew = new HashMap<>();
        map.put("ceb312", new Ceb312Parser());//跨境订单回执报文
        map.put("ceb412", new Ceb412Parser());//跨境支付单回执报文
        map.put("ceb512", new Ceb512Parser());//跨境运单回执报文
        map.put("ceb514", new Ceb514Parser());//跨境运单状态回执报文
        map.put("ceb622", new Ceb622Parser());//跨境清单回执报文
        map.put("ceb712", new Ceb712Parser());//跨境入库明细单回执报文
        map.put("CheckGoodsInfo", new CheckGoodsInfoParser());//跨境预订数据报文
        map.put("TAX", new Ceb816Parser());//跨境电子税单报文
        map.put("inv201msg", new Inv201MsgParser());//保税核注清单报文回执
        map.put("inv201chk", new Inv201ChkParser());//保税核注清单审核回执
        map.put("inv202customs", new Inv202CustomsParser());//保税核注清单生成报关单回执
        map.put("sas221msg", new Sas221MsgParser());//保税核放单报文回执
        map.put("sas221chk", new Sas221ChkParser());//保税核放单审核回执
        map.put("sas223", new Sas223Parser());//保税核放单过卡回执
        mapNew.put("invCommon", new invCommonParser());//保税（核注清单/核放单）数据中心回执
    }

    public BaseParser getParser(String parserType) {
        BaseParser baseParser = null;
        if (!CollectionUtils.isEmpty(map)) {
            baseParser = map.get(parserType);
        }
        return baseParser;
    }

    public BaseParserNew getParserNew(String parserType) {
        BaseParserNew baseParserNew = null;
        if (!CollectionUtils.isEmpty(mapNew)) {
            baseParserNew = mapNew.get(parserType);
        }
        return baseParserNew;
    }

    public void setParser(String psrserType, BaseParser parser) {
        if (!CollectionUtils.isEmpty(map)) {
            map.put(psrserType, parser);
        }
    }

}
