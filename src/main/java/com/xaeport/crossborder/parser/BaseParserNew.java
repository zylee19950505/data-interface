package com.xaeport.crossborder.parser;


import com.xaeport.crossborder.tools.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Administrator on 2017/7/18.
 */
public abstract class BaseParserNew {
    private final Log logger = LogFactory.getLog(this.getClass());
    public abstract Map<String, List<Map<String, String>>> expParserNew(byte[] expPath, String... nodes) throws IOException, DocumentException;
    /**
     * 解析回执报文
     *
     * @param xmlPath
     * @param map
     * @return
     */
    protected Map<String, List<Map<String, String>>> Dom4JXmlNew(byte[]  xmlPath, Map<String, List<Map<String, String>>> map, String... nodes) throws DocumentException, IOException {
            Charset charset = FileUtils.detectCharset(xmlPath);
            SAXReader saxReader = new SAXReader();
            InputStream byteInputStream = new ByteArrayInputStream(xmlPath);
            saxReader.setEncoding(charset.toString());
            Document document =  saxReader.read(byteInputStream);
            Element element = document.getRootElement();
            if (null != nodes && nodes.length > 0) {
                for (String node : nodes) {
                    this.parserChildrenNew(element, map, node);
                }
            }
        return map;
    }
    private void parserChildrenNew(Element element, Map<String, List<Map<String, String>>> map, String node) {
        Iterator it = element.elementIterator();
        List<Map<String, String>> result = new ArrayList<>();
        while (it.hasNext()) {
            Element nodeElement = (Element) it.next();
            String nodeName = nodeElement.getName();
            Iterator itt = nodeElement.elementIterator();
            if (nodeName.equals(node)) {
//                while (itt.hasNext()) {
                    Map<String, String> nodeMap = new HashMap<>();
//                    Element resultElement = (Element) itt.next();
                    nodeMap.put(nodeElement.getName(), nodeElement.getStringValue());
                    result.add(nodeMap);
//                }
                map.put(node, result);
            }
            this.parserChildrenNew(nodeElement, map, node);//递归
        }
    }
}
