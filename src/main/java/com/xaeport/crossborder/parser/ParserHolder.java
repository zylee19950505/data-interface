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
        map.put("ceb312", new Ceb312Parser());//订单回执报文
        map.put("ceb412", new Ceb412Parser());//支付单回执报文
        map.put("ceb512", new Ceb512Parser());//运单回执报文
        map.put("ceb514", new Ceb514Parser());//运单状态回执报文
        map.put("ceb622", new Ceb622Parser());//清单回执报文
        map.put("ceb712", new Ceb712Parser());//入库明细单回执报文
        map.put("CheckGoodsInfo", new CheckGoodsInfoParser());//核放单预订数据报文
        map.put("TAX", new Ceb816Parser());//电子税单报文
        map.put("inv201msg", new Inv201MsgParser());//核注清单报文回执
        map.put("inv201chk", new Inv201ChkParser());//核注清单审核回执
        map.put("inv202customs", new Inv202CustomsParser());//核注清单生成报关单回执
        map.put("sas221msg", new Sas221MsgParser());//核放单报文回执
        map.put("sas221chk", new Sas221ChkParser());//核放单审核回执
        mapNew.put("invCommon", new invCommonParser());//核注清单处理成功回执
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
