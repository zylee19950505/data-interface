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
        String type = "";
        Map<String, List<List<Map<String, String>>>> map = this.parserHolder.getParser("exp310").expParser(expPath, "EnvelopInfo");
        List<Map<String, String>> listEnvelopInfo = map.get("EnvelopInfo").get(0);
        for (Map<String, String> maplist : listEnvelopInfo) {
            if (maplist.containsKey("message_type")) {
                type = maplist.get("message_type");
                break;
            }
        }
        return type;
    }

    public Map expParser(byte[] expPath) throws IOException, DocumentException {
        Map<String, Object> mapData = new HashMap<>();
        String type = this.getExpType(expPath);
        mapData.put("type", type);
        Map<String, List<List<Map<String, String>>>> map = null;
        switch (type) {
            case "EXP310"://税费回执
                map = this.parserHolder.getParser("exp310").expParser(expPath, "EnvelopInfo", "EntryDuty");
                break;
            case "EXP312"://舱单回执
                map = this.parserHolder.getParser("exp312").expParser(expPath, "EnvelopInfo", "ExpMftHead", "ExpMftList");
                break;
            case "EXP302"://申报回值
                map = this.parserHolder.getParser("exp302").expParser(expPath, "EnvelopInfo", "EntryHead");
                break;
        }
        mapData.put("Receipt", map);
        return mapData;
    }

}