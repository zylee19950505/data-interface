package com.xaeport.crossborder.parser;

import com.xaeport.crossborder.data.status.StockMsgType;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExpParserStock {
    private final ParserHolder parserHolder;

    @Autowired
    public ExpParserStock(ParserHolder parserHolder) {
        this.parserHolder = parserHolder;
    }

    private String getStockType(byte[] expPath) throws IOException, DocumentException {
        String type = "";
        String sample = new String(expPath, "UTF-8").trim();
        //判断回执报文类型
        if (sample.contains("<ceb:CEB311Message")) {//跨境订单报文
            type = StockMsgType.CB_DD;
        } else if (sample.contains("<ceb:CEB411Message")) {//跨境支付单报文
            type = StockMsgType.CB_ZFD;
        } else if (sample.contains("<ceb:CEB511Message")) {//跨境运单报文
            type = StockMsgType.CB_YD;
        } else if (sample.contains("<ceb:CEB513Message")) {//跨境运单状态报文
            type = StockMsgType.CB_YDZT;
        } else if (sample.contains("<ceb:CEB621Message")) {//跨境清单报文
            type = StockMsgType.CB_QD;
        }
        return type;
    }

    public String extractByPattern(Pattern pattern, String content) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return matcher.group(1);
        return "";
    }

    //生成报文类型，并解析报文节点
    public Map stockExpParser(byte[] expPath) throws IOException, DocumentException {
        Map<String, Object> mapData = new HashMap<>();
        // byte[] -> String
        String xmlContent = new String(expPath, "UTF-8").trim();
        // 获取data段数据
        String dataContent = this.extractByPattern(Pattern.compile("<Data>(.*?)</Data>"),xmlContent);

        byte[] base64Bytes = Base64.decodeBase64(dataContent);
        String type = this.getStockType(base64Bytes);
        mapData.put("type", type);
        Map<String, List<List<Map<String, String>>>> map = null;
        Map<String, List<Map<String, List<Map<String, String>>>>> stockMap = null;
        switch (type) {
            case StockMsgType.CB_DD://跨境订单报文
                stockMap = this.parserHolder.getStockParserTwo(StockMsgType.CB_DD).expStockParser(base64Bytes, "Order");
                break;
            case StockMsgType.CB_QD://跨境清单报文
                stockMap = this.parserHolder.getStockParserTwo(StockMsgType.CB_QD).expStockParser(base64Bytes, "Inventory");
                break;
            case StockMsgType.CB_ZFD://跨境支付单报文
                map = this.parserHolder.getStockParserOne(StockMsgType.CB_ZFD).expParserOne(base64Bytes, "PaymentHead");
                break;
            case StockMsgType.CB_YD://跨境运单报文
                map = this.parserHolder.getStockParserOne(StockMsgType.CB_YD).expParserOne(base64Bytes, "LogisticsHead");
                break;
            case StockMsgType.CB_YDZT://跨境运单状态报文
                map = this.parserHolder.getParser(StockMsgType.CB_YDZT).expParser(base64Bytes, "LogisticsStatus");
                break;
        }
        mapData.put("Receipt", map);
        if (!StringUtils.isEmpty(stockMap)) mapData.put("Receipt", stockMap);
        return mapData;
    }

}
