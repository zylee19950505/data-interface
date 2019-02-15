package com.xaeport.crossborder.parser;

import com.xaeport.crossborder.data.status.ReceiptType;
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
        map.put(ReceiptType.KJDD, new CebParser());//跨境订单回执报文
        map.put(ReceiptType.KJZFD, new CebParser());//跨境支付单回执报文
        map.put(ReceiptType.KJYD, new CebParser());//跨境运单回执报文
        map.put(ReceiptType.KJYDZT, new CebParser());//跨境运单状态回执报文
        map.put(ReceiptType.KJQD, new CebParser());//跨境清单回执报文
        map.put(ReceiptType.KJRKMXD, new CebParser());//跨境入库明细单回执报文
        map.put(ReceiptType.KJYDSJ, new CebParser());//跨境预订数据报文
        map.put(ReceiptType.KJSD, new CebParser());//跨境电子税单报文
        map.put(ReceiptType.BSHZQDSH, new CebParser());//保税核注清单报文回执
        map.put("inv201chk", new CebParser());//保税核注清单审核回执
        map.put(ReceiptType.BSHZQDBGD, new CebParser());//保税核注清单生成报关单回执
        map.put(ReceiptType.BSHFDSH, new CebParser());//保税核放单报文回执
        map.put("sas221chk", new CebParser());//保税核放单审核回执
        map.put(ReceiptType.BSHFDGK, new CebParser());//保税核放单过卡回执
        mapNew.put(ReceiptType.BSSJZX, new invCommonParser());//保税（核注清单/核放单）数据中心回执
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
