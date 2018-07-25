package com.xaeport.crossborder.convert511.generate;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.BaseTransfer411;
import com.xaeport.crossborder.data.entity.CEB511Message;
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
public class BaseLogisticsXml {
    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    LogisticsXml logisticsXml;
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
    public byte[] createXML(CEB511Message ceb511Message, String flag, String xmlHeadGuid) throws TransformerException {
        Document document = this.getDocument();
        Element rootElement = document.createElement("ceb:CEB511Message");

        rootElement.setAttribute("guid", xmlHeadGuid);
        rootElement.setAttribute("version", "1.0");
        rootElement.setAttribute("xmlns:ceb", "http://www.chinaport.gov.cn/ceb");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

        ceb511Message.setBaseTransfer411(this.baseTransfer411());
        this.getLogistics(document, ceb511Message, flag, rootElement);

        BaseTransfer411 baseTransfer411 = ceb511Message.getBaseTransfer411();//创建<ceb:BaseTransfer>节点。
        rootElement.appendChild(this.getBaseTransfer(document, baseTransfer411));//在<ceb:BaseTransfer>节点下添加内容

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
        dxpId.setTextContent(baseTransfer411.getDxpId());

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
     * @param
     * @return
     */
    public Element getLogistics(Document document, CEB511Message ceb511Message, String flag, Element rootElement) {
        switch (flag) {
            //生成新快件 xml
            case "logistics": {
//                Data.appendChild(this.waybill.getEntryHead(document, ceb411Message));
                this.logisticsXml.getLogisticsList(document, rootElement, ceb511Message);
                break;
            }
            case "shipingBill": {
            }
        }
        return rootElement;
    }

    public BaseTransfer411 baseTransfer411(){
        BaseTransfer411 baseTransfer411 = new BaseTransfer411();
        baseTransfer411.setCopCode("1101180326");
        baseTransfer411.setCopName("物流企业");
        baseTransfer411.setDxpId("EXP2016522002580001");
        baseTransfer411.setDxpMode("DXP");
        baseTransfer411.setNote("test");
        return baseTransfer411;
    }
}
