package com.xaeport.crossborder.convert.exitbondinvt;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.InvtMessage;
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
public class EBaseBondInvtXML {

    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    EBondInvtXML eBondInvtXML;
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
     * @param invtMessage
     */
    public byte[] createXML(InvtMessage invtMessage, String flag, String xmlName) throws TransformerException {
        Document document = this.getDocument();
        Element rootElement = document.createElement("Signature");

        rootElement.setAttribute("schemaLocation", "http://www.chinaport.gov.cn/sas SAS101.xsd");
        rootElement.setAttribute("xmlns:sas", "http://www.chinaport.gov.cn/sas");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

        Element SignedInfo = document.createElement("SignedInfo");
        Element SignatureValue = document.createElement("SignatureValue");
        Element KeyInfo = document.createElement("KeyInfo");
        Element Object = document.createElement("Object");
        Object.setAttribute("Id", "String");

        //设置SignedInfo节点数据
        this.getSignedInfo(document, SignedInfo);
        //设置KeyInfo节点数据
        this.getKeyInfo(document, KeyInfo);
        //设置package节点数据
        Element Package = document.createElement("Package");
        this.getEnvelopInfo(document, Package);
        this.getDataInfo(document, invtMessage, flag, Package);
        Object.appendChild(Package);

        //追加母节点Signature子节点数据
        rootElement.appendChild(SignedInfo);
        rootElement.appendChild(SignatureValue);
        rootElement.appendChild(KeyInfo);
        rootElement.appendChild(Object);

        document.appendChild(rootElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");//缩进
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(os));//暂时输出到控制台了
        return os.toByteArray();
    }

    //创建SignedInfo报文节点
    private Element getSignedInfo(Document document, Element SignedInfo) {
        Element CanonicalizationMethod = document.createElement("CanonicalizationMethod");
        CanonicalizationMethod.setAttribute("Algorithm", "http://www.w3.org/TR/2001/REC-xml-c14n-20010315");

        Element SignatureMethod = document.createElement("SignatureMethod");
        SignatureMethod.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#rsa-sha1");

        Element Reference = document.createElement("Reference");
        Reference.setAttribute("URI", "String");

        Element DigestMethod = document.createElement("DigestMethod");
        DigestMethod.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#sha1");
        Element DigestValue = document.createElement("DigestValue");

        Reference.appendChild(DigestMethod);
        Reference.appendChild(DigestValue);

        SignedInfo.appendChild(CanonicalizationMethod);
        SignedInfo.appendChild(SignatureMethod);
        SignedInfo.appendChild(Reference);

        return SignedInfo;
    }

    //创建KeyInfo报文节点
    private Element getKeyInfo(Document document, Element KeyInfo) {
        Element KeyName = document.createElement("KeyName");
        KeyName.setTextContent("aa");
        KeyInfo.appendChild(KeyName);
        return KeyInfo;
    }

    //创建EnvelopInfo报文节点
    private Element getEnvelopInfo(Document document, Element Package) {
        Element EnvelopInfo = document.createElement("EnvelopInfo");

        Element version = document.createElement("version");
        version.setTextContent("1.0");

        Element message_id = document.createElement("message_id");
        message_id.setTextContent("XAHZ900818I000000290201812110000000002");

        Element file_name = document.createElement("file_name");
        file_name.setTextContent("XAHZ900818I000000290201812110000000002.zip");

        Element message_type = document.createElement("message_type");
        message_type.setTextContent("INV101");

        Element sender_id = document.createElement("sender_id");
        sender_id.setTextContent("DXPENT0000018755");

        Element receiver_id = document.createElement("receiver_id");
        receiver_id.setTextContent("DXPEDCSAS0000001");

        Element send_time = document.createElement("send_time");
        send_time.setTextContent("2018-12-11T09:02:35");

        Element Ic_Card = document.createElement("Ic_Card");
        Ic_Card.setTextContent("8600000198447");

        EnvelopInfo.appendChild(version);
        EnvelopInfo.appendChild(message_id);
        EnvelopInfo.appendChild(file_name);
        EnvelopInfo.appendChild(message_type);
        EnvelopInfo.appendChild(sender_id);
        EnvelopInfo.appendChild(receiver_id);
        EnvelopInfo.appendChild(send_time);
        EnvelopInfo.appendChild(Ic_Card);
        Package.appendChild(EnvelopInfo);
        return Package;
    }

    /**
     * 构建DataInfo节点
     *
     * @param invtMessage
     * @return package
     */
    public Element getDataInfo(Document document, InvtMessage invtMessage, String flag, Element Package) {
        switch (flag) {
            //生成出区核注清单xml报文
            case "EBondInvt": {
                this.eBondInvtXML.getDataInfo(document, invtMessage, Package);
                break;
            }
        }
        return Package;
    }

}
