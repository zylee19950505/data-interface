package com.xaeport.crossborder.parser;

import com.xaeport.crossborder.tools.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

public abstract class BaseStockParserTwo {

    public abstract Map<String, List<Map<String, List<Map<String, String>>>>> expStockParser(byte[] expPath, String node) throws IOException, DocumentException;

    protected Map<String, List<Map<String, List<Map<String, String>>>>> Dom4JStockXml(byte[] xmlPath, Map<String, List<Map<String, List<Map<String, String>>>>> map, String node) throws DocumentException, IOException {
        Charset charset = FileUtils.detectCharset(xmlPath);
        SAXReader saxReader = new SAXReader();
        InputStream byteInputStream = new ByteArrayInputStream(xmlPath);
        saxReader.setEncoding(charset.toString());
        Document document = saxReader.read(byteInputStream);
        Element element = document.getRootElement();
        if (null != node) {
            this.stockParserChildren(element, map, node);
        }
        return map;
    }

    private void stockParserChildren(Element element, Map<String, List<Map<String, List<Map<String, String>>>>> map, String node) {
        Iterator it = element.elementIterator();
        List<Map<String, List<Map<String, String>>>> result = new ArrayList<>();
        String Head = null;
        String List = null;

        switch (node) {
            case "Order":
                Head = "OrderHead";
                List = "OrderList";
                break;
            case "Inventory":
                Head = "InventoryHead";
                List = "InventoryList";
                break;
            case "Delivery":
                Head = "DeliveryHead";
                List = "DeliveryList";
                break;
        }

        while (it.hasNext()) {// ceb:Order

            Element nodeElement1 = (Element) it.next();
            Iterator itt = nodeElement1.elementIterator();

            while (itt.hasNext()) {//ceb:OrderHead  OR  ceb:OrderList
                Element nodeElement2 = (Element) itt.next();
                Iterator ittt = nodeElement2.elementIterator();
                String nodeNamed = nodeElement2.getName();

                List<Map<String, String>> OrderHeads = new ArrayList<>();
                List<Map<String, String>> OrderLists = new ArrayList<>();
                Map<String, List<Map<String, String>>> TypeMap = new HashMap<>();

                if (nodeNamed.equals(Head)) {
                    while (ittt.hasNext()) {//ceb:OrderHead
                        Map<String, String> nodeMap = new HashMap<>();
                        Element resultElement = (Element) ittt.next();
                        nodeMap.put(resultElement.getName(), resultElement.getStringValue());
                        OrderHeads.add(nodeMap);
                    }
                    TypeMap.put(Head, OrderHeads);
                    result.add(TypeMap);
                    map.put(node, result);
                }

                if (nodeNamed.equals(List)) {
                    while (ittt.hasNext()) {//ceb:OrderList
                        Map<String, String> nodeMap = new HashMap<>();
                        Element resultElement = (Element) ittt.next();
                        nodeMap.put(resultElement.getName(), resultElement.getStringValue());
                        OrderLists.add(nodeMap);
                    }
                    TypeMap.put(List, OrderLists);
                    result.add(TypeMap);
                    map.put(node, result);
                }

            }
            this.stockParserChildren(nodeElement1, map, node);//递归
        }
    }

}
