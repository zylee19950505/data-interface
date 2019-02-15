package com.xaeport.crossborder.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2019/01/14.
 */
public class Sas221ChkParser extends BaseParser {
    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 解析 保税核放单审核回执
     */
    public Map<String, List<List<Map<String, String>>>> expParser(byte[] expPath, String... nodes) throws DocumentException, IOException {
        Map<String, List<List<Map<String, String>>>> map = new LinkedHashMap<>();
        return this.Dom4JXml(expPath, map, nodes);
    }
}