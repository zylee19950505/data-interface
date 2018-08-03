package com.xaeport.crossborder.convert411.parser;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * exp解析
 * Created by Administrator on 2017/7/24.
 */
@Component
public class ExpParser {

    private final ParserHolder parserHolder;

    @Autowired
    public ExpParser(ParserHolder parserHolder) {
        this.parserHolder = parserHolder;
    }

    /**
     * 解析报文获取报文类型
     *
     * @param expPath 报文文件
     */
    private String getExpType(byte[] expPath) throws IOException, DocumentException {
        //String type = "CEB412";
        String type = "CEB622";
//        Map<String, List<List<Map<String, String>>>> map = this.parserHolder.getParser("ceb412").expParser(expPath, "CEB412Message");
//        List<Map<String, String>> listEnvelopInfo = map.get("CEB412Message").get(0);
//        for (Map<String, String> maplist : listEnvelopInfo) {
//            if (maplist.containsKey("message_type")) {
//                type = maplist.get("message_type");
//                break;
//            }
//        }
        return type;
    }

    public Map expParser(byte[] expPath) throws IOException, DocumentException {
        Map<String, Object> mapData = new HashMap<>();
        String type = this.getExpType(expPath);
        mapData.put("type", type);
        Map<String, List<List<Map<String, String>>>> map = null;
        switch (type) {
            case "CEB312"://订单回执报文
                map = this.parserHolder.getParser("ceb312").expParser(expPath, "OrderReturn");
                break;
            case "CEB412"://支付单回执报文
                map = this.parserHolder.getParser("ceb412").expParser(expPath, "PaymentReturn");
                break;
            case "CEB512"://运单回执报文
                map = this.parserHolder.getParser("ceb512").expParser(expPath, "LogisticsReturn");
                break;
            case "CEB514"://运单状态回执报文
                map = this.parserHolder.getParser("ceb514").expParser(expPath, "LogisticsStatusReturn");
                break;
            case "CEB622"://清单回执报文
                map = this.parserHolder.getParser("ceb622").expParser(expPath, "InventoryReturn");
                break;
        }
        mapData.put("Receipt", map);
        return mapData;
    }

}
