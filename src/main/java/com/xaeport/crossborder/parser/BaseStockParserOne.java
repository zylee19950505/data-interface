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

public abstract class BaseStockParserOne {

    private final Log logger = LogFactory.getLog(this.getClass());

    public abstract Map<String, List<List<Map<String, String>>>> expParserOne(byte[] expPath, String... nodes) throws IOException, DocumentException;

    /**
     * 解析回执报文
     *
     * @param xmlPath
     * @param map
     * @return
     */
    protected Map<String, List<List<Map<String, String>>>> Dom4JXmlOne(byte[] xmlPath, Map<String, List<List<Map<String, String>>>> map, String... nodes) throws DocumentException, IOException {
        Charset charset = FileUtils.detectCharset(xmlPath);
        SAXReader saxReader = new SAXReader();
        InputStream byteInputStream = new ByteArrayInputStream(xmlPath);
        saxReader.setEncoding(charset.toString());
        Document document = saxReader.read(byteInputStream);
        Element element = document.getRootElement();
        if (null != nodes && nodes.length > 0) {
            for (String node : nodes) {
                this.parserChildrenOne(element, map, node);
            }
        }
        return map;
    }

    private void parserChildrenOne(Element element, Map<String, List<List<Map<String, String>>>> map, String node) {
        Iterator it = element.elementIterator();
        List<List<Map<String, String>>> result = new ArrayList<>();
        while (it.hasNext()) {
            Element nodeElement = (Element) it.next();
            String nodeName1 = nodeElement.getName();
            Iterator itt = nodeElement.elementIterator();
            while (itt.hasNext()) {
                Element nodeElement1 = (Element) itt.next();
                String nodeName2 = nodeElement1.getName();
                Iterator ittt = nodeElement1.elementIterator();
                if (nodeName2.equals(node)) {
                    List<Map<String, String>> list = new ArrayList<>();
                    while (ittt.hasNext()) {
                        Map<String, String> nodeMap = new HashMap<>();
                        Element resultElement = (Element) ittt.next();
                        nodeMap.put(resultElement.getName(), resultElement.getStringValue());
                        list.add(nodeMap);
                    }
                    result.add(list);
                    map.put(node, result);
                }
                this.parserChildrenOne(nodeElement1, map, node);//递归
            }
        }
    }

}
