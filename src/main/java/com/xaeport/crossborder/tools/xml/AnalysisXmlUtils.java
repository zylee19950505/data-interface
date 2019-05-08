package com.xaeport.crossborder.tools.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.StringReader;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author BaoZhe
 * @Date 2019-03-29
 * @Version 1.0
 */
public class AnalysisXmlUtils {

    private static Log log = LogFactory.getLog(AnalysisXmlUtils.class);


    /**
     * 将XMl报文转换为实体
     *
     * @Author BaoZhe
     * @Date 2019-03-29
     * @Version 1.0
     */
    public static <T> List<T> convertXmlToType(String xmlContent, Class<T> clazz) {
        StringReader stringReader = new StringReader(xmlContent);
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            // 加载XML数据
            document = reader.read(stringReader);
        } catch (DocumentException e) {
            log.error("Xml转换为Document对象时发生异常", e);
        }
        List<T> objects = analysisSubNode(document, clazz);
        if (StringUtils.isEmpty(objects)) {
            return null;
        }
        return objects;
    }

    private static <T> List<T> analysisSubNode(Node document, Class<T> clazz) {
        List<T> objects = new ArrayList<>();

        RootXPath rootXPath = clazz.getAnnotation(RootXPath.class);
        String rootXPathStr = rootXPath.value();

        // 定位根节点
        List<Node> rootSubNodes = document.selectNodes(rootXPathStr);
        Field[] clazzFields = clazz.getDeclaredFields();

        XPath xPath;
        Class fieldType;
        Method method;
        T object;
        Object parameterObject;
        ParameterizedType parameterizedType;
        Type genericType;
        List<?> paramList = null;
        String methodName, subNodePath;
        for (Node node : rootSubNodes) {
            try {
                // 初始化数据对象
                object = clazz.newInstance();
            } catch (InstantiationException e) {
                log.error("Xml实体对象初始化失败[class=" + clazz + "]", e);
                return null;
            } catch (IllegalAccessException e) {
                log.error("非法访问Xml实体对象[class=" + clazz + "]", e);
                return null;
            }
            for (Field field : clazzFields) {
                parameterObject = null;
                xPath = field.getAnnotation(XPath.class);
                if (StringUtils.isEmpty(xPath)) {
                    continue;
                }
                // 查找节点
                subNodePath = xPath.value();
//                log.debug("子路径为： " + subNodePath);
                Node subNode = node.selectSingleNode(subNodePath);
                if (StringUtils.isEmpty(subNode)) {
                    continue;
                }
                // 字段类型
                fieldType = field.getType();
                // 设置setter方法
                try {
                    methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    method = clazz.getMethod(methodName, fieldType);
                } catch (NoSuchMethodException e) {
                    log.error("Xml实体对象[class=" + clazz + "]未找到字段[field=" + field.getName() + "]的setter方法", e);
                    continue;
                }
                // 判断字段类型并分别处置
                if (fieldType == String.class) {
                    parameterObject = subNode.getText();
                } else if (XmlEntry.class.isAssignableFrom(fieldType)) {
                    // 为单个嵌套对象时
                    paramList = analysisSubNode(node, fieldType);
                    if (!CollectionUtils.isEmpty(paramList)) {
                        parameterObject = paramList.get(0);
                    }
                } else if (List.class.isAssignableFrom(fieldType)) {
                    // 为集合型嵌套对象时
                    genericType = field.getGenericType();
                    if (StringUtils.isEmpty(genericType)) {
                        log.error(" 调用Xml实体对象[class=" + clazz + "]集合字段[field=" + field.getName() + "]无泛型设置，无法进行后续处理");
                        continue;
                    }
                    if (genericType instanceof ParameterizedType) {
                        parameterizedType = (ParameterizedType) genericType;
                        paramList = analysisSubNode(node, (Class<?>) parameterizedType.getActualTypeArguments()[0]);
                    }
                    if (!CollectionUtils.isEmpty(paramList)) {
                        parameterObject = paramList;
                    }
                }
                try {
                    // 执行泛型方法
                    method.invoke(object, parameterObject);
                } catch (IllegalAccessException e) {
                    log.error("非法访问Xml实体对象[class=" + clazz + "]字段[field=" + field.getName() + "]的setter方法", e);
                    continue;
                } catch (InvocationTargetException e) {
                    log.error(" 调用Xml实体对象[class=" + clazz + "]字段[field=" + field.getName() + "]的setter方法失败", e);
                    continue;
                }
            }
            objects.add(object);
        }
        return objects;
    }


}
