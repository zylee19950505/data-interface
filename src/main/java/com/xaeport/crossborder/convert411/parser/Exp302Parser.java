package com.xaeport.crossborder.convert411.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/18.
 */
public class Exp302Parser extends BaseParser {
    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 解析exp302报文
     *
     * @param expPath
     * @return
     */
    public  Map<String, List<List<Map<String, String>>>> expParser(byte[]  expPath, String... nodes) throws DocumentException, IOException {
        Map<String, List<List<Map<String, String>>>> map = new LinkedHashMap<>();
        return this.Dom4JXml(expPath, map,nodes);
    }

}
