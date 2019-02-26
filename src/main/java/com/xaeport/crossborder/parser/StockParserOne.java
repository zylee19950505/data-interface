package com.xaeport.crossborder.parser;

import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StockParserOne extends BaseStockParserOne {
    @Override
    public Map<String, List<List<Map<String, String>>>> expParserOne(byte[] expPath, String... nodes) throws IOException, DocumentException {
        Map<String, List<List<Map<String, String>>>> map = new LinkedHashMap<>();
        return this.Dom4JXmlOne(expPath, map, nodes);
    }
}
