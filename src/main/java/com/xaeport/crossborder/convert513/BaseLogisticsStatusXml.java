package com.xaeport.crossborder.convert513;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.BaseTransfer;
import com.xaeport.crossborder.data.entity.BaseTransfer411;
import com.xaeport.crossborder.data.entity.CEB513Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;

/**
 * 生成新快件和舱单的公共部分
 * Created by zwj on 2017/07/18.
 */
@Component
public class BaseLogisticsStatusXml {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    LogisticsStatusXml logisticsStatusXml;
    @Autowired
    AppConfiguration appConfiguration;

    private DocumentBuilder getDocumentBuilder() {
        DocumentBuilder documentBuilder = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            documentBuilder = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            this.log.error("创建DocumentBulider失败", e);
        }
        return documentBuilder;
    }

    public Document getDocument() {
        DocumentBuilder documentBuilder = this.getDocumentBuilder();
        Document document = documentBuilder.newDocument();
        document.setXmlStandalone(false);

        return document;
    }

    /**
     * 创建clientDxp数据报文
     *
     * @param
     */
    public byte[] createXML(CEB513Message ceb513Message, String flag, String xmlHeadGuid) throws TransformerException {
        Document document = this.getDocument();
        Element rootElement = document.createElement("ceb:CEB513Message");

        rootElement.setAttribute("guid", xmlHeadGuid);
        rootElement.setAttribute("version", "1.0");
        rootElement.setAttribute("xmlns:ceb", "http://www.chinaport.gov.cn/ceb");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

        this.getLogistics(document, ceb513Message, flag, rootElement);

        BaseTransfer baseTransfer = ceb513Message.getBaseTransfer();//创建<ceb:BaseTransfer>节点。
        rootElement.appendChild(this.getBaseTransfer(document, baseTransfer));//在<ceb:BaseTransfer>节点下添加内容

        document.appendChild(rootElement);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");//换行
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");//缩进
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(os));//暂时输出到控制台了
        return os.toByteArray();
    }

    /**
     * 构建 BaseTransfer4 节点
     *
     * @param document
     * @param baseTransfer
     * @return
     */
    public Element getBaseTransfer(Document document, BaseTransfer baseTransfer) {

        Element baseTransfer513Element = document.createElement("ceb:BaseTransfer");

        Element copCode = document.createElement("ceb:copCode");
        copCode.setTextContent(baseTransfer.getCopCode());

        Element copName = document.createElement("ceb:copName");
        copName.setTextContent(baseTransfer.getCopName());

        Element dxpMode = document.createElement("ceb:dxpMode");
        dxpMode.setTextContent(baseTransfer.getDxpMode());

        Element dxpId = document.createElement("ceb:dxpId");
//        dxpId.setTextContent(baseTransfer.getDxpId());
        dxpId.setTextContent("DXPENT0000018755");

        Element note = document.createElement("ceb:note");
        note.setTextContent(baseTransfer.getNote());

        baseTransfer513Element.appendChild(copCode);
        baseTransfer513Element.appendChild(copName);
        baseTransfer513Element.appendChild(dxpMode);
        baseTransfer513Element.appendChild(dxpId);
        baseTransfer513Element.appendChild(note);
        return baseTransfer513Element;
    }

    /**
     * 构建Data 节点
     *
     * @param
     * @return
     */
    public Element getLogistics(Document document, CEB513Message ceb513Message, String flag, Element rootElement) {
        switch (flag) {
            //生成新快件 xml
            case "logisticsStatus": {
                this.logisticsStatusXml.getLogisticsList(document, rootElement, ceb513Message);
                break;
            }
        }
        return rootElement;
    }

}
