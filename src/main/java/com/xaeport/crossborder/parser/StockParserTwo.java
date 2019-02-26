package com.xaeport.crossborder.parser;

import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StockParserTwo extends BaseStockParserTwo {

    @Override
    public Map<String, List<Map<String, List<Map<String, String>>>>> expStockParser(byte[] expPath, String node) throws IOException, DocumentException {
        Map<String, List<Map<String, List<Map<String, String>>>>> map = new LinkedHashMap<>();
        return this.Dom4JStockXml(expPath, map, node);
    }

}
