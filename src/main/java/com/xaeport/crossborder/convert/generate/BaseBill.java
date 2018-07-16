package com.xaeport.crossborder.convert.generate;


import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.EnvelopInfo;
import com.xaeport.crossborder.data.entity.SignedData;
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
public class BaseBill {
    private Log log = LogFactory.getLog(this.getClass());
    //@Autowired
   // Waybill waybill;
    @Autowired
    AppConfiguration appConfiguration;
   /* @Autowired
    ShippingBill shippingBill;*/

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
     * @param signedData
     */
    public byte[] createXML(SignedData signedData, String flag) throws TransformerException {
        Document document = this.getDocument();
        Element rootElement = document.createElement("Package");
        EnvelopInfo envelopInfo = signedData.getEnvelopInfo();
        if (envelopInfo != null) {
            rootElement.appendChild(this.getEnvelopInfo(document, envelopInfo));
        }
        rootElement.appendChild(this.getDataInfo(document, signedData, flag));

        document.appendChild(rootElement);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(os));//暂时输出到控制台了
        return os.toByteArray();
    }

    /**
     * 创建EnvelopInfo 节点
     *
     * @param document
     * @param envelopInfo
     * @return
     */
    public Element getEnvelopInfo(Document document, EnvelopInfo envelopInfo) {
        Element envelopInfoElement = document.createElement("EnvelopInfo");

        Element version = document.createElement("version");
        version.setTextContent(envelopInfo.getVersion());

        Element messageId = document.createElement("message_id");
        messageId.setTextContent(envelopInfo.getMessage_id());

        Element fileName = document.createElement("file_name");
        fileName.setTextContent(envelopInfo.getFile_name());

        Element messageType = document.createElement("message_type");
        messageType.setTextContent(envelopInfo.getMessage_type());

        Element senderId = document.createElement("sender_id");
        senderId.setTextContent(envelopInfo.getSender_id());

        Element receiverId = document.createElement("receiver_id");
        receiverId.setTextContent(envelopInfo.getReceiver_id());

        Element sendTime = document.createElement("send_time");
        sendTime.setTextContent(envelopInfo.getSend_time());

        envelopInfoElement.appendChild(version);
        envelopInfoElement.appendChild(messageId);
        envelopInfoElement.appendChild(fileName);
        envelopInfoElement.appendChild(messageType);
        envelopInfoElement.appendChild(senderId);
        envelopInfoElement.appendChild(receiverId);
        envelopInfoElement.appendChild(sendTime);

        return envelopInfoElement;
    }


    /**
     * 构建DataInfo 节点
     *
     * @param document
     * @param signedData
     * @return
     */
    public Element getDataInfo(Document document, SignedData signedData, String flag) {
        Element dataInfo = document.createElement("DataInfo");
        Element signedDataElement = document.createElement("SignedData");
        signedDataElement.appendChild(this.getData(document, signedData, flag));
        String sign = signedData.getHashSign();
        if (sign != null) {
            Element hashSign = document.createElement("HashSign");
            hashSign.setTextContent(sign);
            signedDataElement.appendChild(hashSign);
        }

        String signInfo = signedData.getSignerInfo();
        if (signInfo != null) {
            Element signerInfo = document.createElement("SignerInfo");
            signerInfo.setTextContent(sign);
            signedDataElement.appendChild(signerInfo);
        }

        dataInfo.appendChild(signedDataElement);

        return dataInfo;
    }

    /**
     * 构建Data 节点
     *
     * @param signedData
     * @return
     */
    public Element getData(Document document, SignedData signedData, String flag) {
        Element Data = document.createElement("Data");
        Element bzTypeElement = document.createElement(signedData.getBzType());
        bzTypeElement.setAttribute("xmlns", "http://www.chinaport.gov.cn/Exp");
        switch (flag) {
            //生成新快件 xml
            case "wayBill": {
              //  bzTypeElement.appendChild(this.waybill.getEntryHead(document, signedData));
               // this.waybill.getEntryList(document, bzTypeElement, signedData);
                //是否将EntryDocu节点组装
                if (appConfiguration.isEntryDocu()) {
               //     bzTypeElement.appendChild(this.waybill.getEntryDocu(document, signedData));
                }
                break;
            }
            case "shipingBill": {
                //生成舱单 xml
              /*  bzTypeElement.appendChild(this.shippingBill.getExpMftHead(document, signedData));
                this.shippingBill.getExpMftList(document, bzTypeElement, signedData);
                break;*/
            }
        }
        Data.appendChild(bzTypeElement);
        return Data;
    }
}
