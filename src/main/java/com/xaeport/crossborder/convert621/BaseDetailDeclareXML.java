package com.xaeport.crossborder.convert621;


import com.xaeport.crossborder.configuration.AppConfiguration;
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
 *
 */
@Component
public class BaseDetailDeclareXML {

    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    DetailDeclareXML detailDeclareXML;
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
    public byte[] createXML(CEB621Message ceb621Message, String flag) throws TransformerException {
        Document document = this.getDocument();
        Element rootElement = document.createElement("ceb:CEB621Message");
        rootElement.setAttribute("guid",ceb621Message.getImpInventoryHead().getGuid());
        rootElement.setAttribute("version","1.0");
        rootElement.setAttribute("xmlns:ceb","http://www.chinaport.gov.cn/ceb");
        rootElement.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");

        rootElement.appendChild(this.getData(document, ceb621Message, flag));
        //添加<ceb:BaseTransfer>节点
        rootElement.appendChild(this.getBaseTransfer(document, ceb621Message, flag));

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
    private Element getBaseTransfer(Document document, CEB621Message ceb621Message, String flag) {
        Element BaseTrElement = document.createElement("ceb:BaseTransfer");
        switch (flag) {
            //清单
            case "DetailDeclare":{
                this.detailDeclareXML.getBaseTransfer(BaseTrElement,document,ceb621Message);
                break;
            }
        }
        return BaseTrElement;
    }


    /**
     * 构建ceb:Order节点
     *
     * @param document
     * @param ceb621Message
     * @return
     */
    /*此节点未使用,*/
    public Element getDataInfo(Document document, CEB621Message ceb621Message, String flag) {
        Element ceborderElement = document.createElement("ceb:Inventory");
        ceborderElement.appendChild(this.getData(document, ceb621Message, flag));
        return ceborderElement;
    }

    /**
     * 构建InventoryHead 节点
     *
     * @param ceb621Message
     * @return
     */
    public Element getData(Document document, CEB621Message ceb621Message, String flag) {
        Element ceborderheadEl = document.createElement("ceb:Inventory");
        switch (flag) {
            //生成订单 .xml
            case "DetailDeclare": {
                ceborderheadEl.appendChild(this.detailDeclareXML.getEntryHead(document, ceb621Message));
                this.detailDeclareXML.getEntryList(document, ceborderheadEl, ceb621Message);
                break;
            }
        }
        return ceborderheadEl;
    }

}
