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
public class invCommonParser extends BaseParserNew {
    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 解析 CommonResponeMessage 报文数据
     */
    public Map<String, List<Map<String, String>>> expParserNew(byte[] expPath, String... nodes) throws DocumentException, IOException {
        Map<String, List<Map<String, String>>> map = new LinkedHashMap<>();
        return this.Dom4JXmlNew(expPath, map, nodes);
    }

}
