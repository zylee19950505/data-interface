package com.xaeport.crossborder.convert.payment;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.BaseTransfer411;
import com.xaeport.crossborder.data.entity.CEB411Message;
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
public class BasePaymentXml {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    PaymentXml paymentXml;
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
     * @param ceb411Message
     */
    public byte[] createXML(CEB411Message ceb411Message, String flag,String xmlHeadGuid) throws TransformerException {
        Document document = this.getDocument();
        Element rootElement = document.createElement("ceb:CEB411Message");

        rootElement.setAttribute("guid", xmlHeadGuid);
        rootElement.setAttribute("version", "1.0");
        rootElement.setAttribute("xmlns:ceb", "http://www.chinaport.gov.cn/ceb");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

        this.getPayment(document, ceb411Message, flag, rootElement);

        BaseTransfer411 baseTransfer411 = ceb411Message.getBaseTransfer411();
        rootElement.appendChild(this.getBaseTransfer(document, baseTransfer411));

        document.appendChild(rootElement);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");//缩进
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(os));//暂时输出到控制台了
        return os.toByteArray();
    }

    /**
     * 构建 BaseTransfer4 节点
     *
     * @param document
     * @param baseTransfer411
     * @return
     */
    public Element getBaseTransfer(Document document, BaseTransfer411 baseTransfer411) {

        Element baseTransfer411Element = document.createElement("ceb:BaseTransfer");

        Element copCode = document.createElement("ceb:copCode");
        copCode.setTextContent(baseTransfer411.getCopCode());

        Element copName = document.createElement("ceb:copName");
        copName.setTextContent(baseTransfer411.getCopName());

        Element dxpMode = document.createElement("ceb:dxpMode");
        dxpMode.setTextContent(baseTransfer411.getDxpMode());

        Element dxpId = document.createElement("ceb:dxpId");
//        dxpId.setTextContent(baseTransfer411.getDxpId());
        dxpId.setTextContent("DXPENT0000018755");

        Element note = document.createElement("ceb:note");
        note.setTextContent(baseTransfer411.getNote());

        baseTransfer411Element.appendChild(copCode);
        baseTransfer411Element.appendChild(copName);
        baseTransfer411Element.appendChild(dxpMode);
        baseTransfer411Element.appendChild(dxpId);
        baseTransfer411Element.appendChild(note);

        return baseTransfer411Element;
    }

    /**
     * 构建Data 节点
     *
     * @param ceb411Message
     * @return
     */
    public Element getPayment(Document document, CEB411Message ceb411Message, String flag, Element rootElement) {
        switch (flag) {
            //生成新快件 xml
            case "payment": {
                this.paymentXml.getPaymentList(document, rootElement, ceb411Message);
                break;
            }
        }
        return rootElement;
    }

}
