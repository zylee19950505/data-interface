package com.xaeport.crossborder.convert.bondinven;


import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.BaseTransfer;
import com.xaeport.crossborder.data.entity.CEB621Message;
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
 * 进口保税清单报文生成
 */
@Component
public class BaseBondInvenXML {

    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    BondInvenXML bondInvenXML;
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
     * @param ceb621Message
     */
    public byte[] createXML(CEB621Message ceb621Message, String flag, String xmlHeadGuid) throws TransformerException {
        Document document = this.getDocument();
        Element rootElement = document.createElement("ceb:CEB621Message");

        rootElement.setAttribute("guid", xmlHeadGuid);
        rootElement.setAttribute("version", "1.0");
        rootElement.setAttribute("xmlns:ceb", "http://www.chinaport.gov.cn/ceb");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

        this.getInventory(document, ceb621Message, flag, rootElement);
        //添加<ceb:BaseTransfer>节点
        BaseTransfer baseTransfer = ceb621Message.getBaseTransfer();
        rootElement.appendChild(this.getBaseTransfer(document, baseTransfer));

        document.appendChild(rootElement);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");//缩进
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(os));//暂时输出到控制台了
        return os.toByteArray();
    }

    //创建<ceb:BaseTransfer> 节点
    private Element getBaseTransfer(Document document, BaseTransfer baseTransfer) {

        Element BaseTrElement = document.createElement("ceb:BaseTransfer");

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

        BaseTrElement.appendChild(copCode);
        BaseTrElement.appendChild(copName);
        BaseTrElement.appendChild(dxpMode);
        BaseTrElement.appendChild(dxpId);
        BaseTrElement.appendChild(note);

        return BaseTrElement;
    }

    /**
     * 构建InventoryHead 节点
     *
     * @param ceb621Message
     * @return
     */
    public Element getInventory(Document document, CEB621Message ceb621Message, String flag, Element rootElement) {
        switch (flag) {
            //生成保税清单xml报文
            case "BondInven": {
                this.bondInvenXML.getBondInvenHead(document, ceb621Message, rootElement);
                break;
            }
        }
        return rootElement;
    }

}
