package com.xaeport.crossborder.convertManifest;


import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
import java.text.SimpleDateFormat;

/**
 *
 */
@Component
public class BaseManifestXML {

    private Log log = LogFactory.getLog(this.getClass());
    @Autowired
    ManifestDeclareXML manifestDeclareXML;
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
     */
    public byte[] createXML(CEBManifestMessage cebManifestMessage) throws TransformerException {
        Document document = this.getDocument();
        Element rootElement = document.createElement("DTC_Message");
        MessageHead messageHead = cebManifestMessage.getMessageHead();
        rootElement.appendChild(this.getMessageHead(document, messageHead));
        ManifestHead manifestHead = cebManifestMessage.getManifestHead();
        rootElement.appendChild(this.getManifestHead(document, manifestHead));

        document.appendChild(rootElement);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");//换行
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");//缩进
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(os));//暂时输出到控制台了
        return os.toByteArray();

    }

    //811的体节点
    private Node getManifestHead(Document document, ManifestHead manifestHead) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");

        Element messageBody = document.createElement("MessageBody");

        Element DTCFlow = document.createElement("DTCFlow");

        Element manifest_head = document.createElement("MANIFEST_HEAD");

        Element auto_id = document.createElement("AUTO_ID");
        auto_id.setTextContent(manifestHead.getAuto_id());

        Element manifest_no = document.createElement("MANIFEST_NO");
        manifest_no.setTextContent(manifestHead.getManifest_no());

        Element biz_type = document.createElement("BIZ_TYPE");
        biz_type.setTextContent(manifestHead.getBiz_type());

        Element biz_mode = document.createElement("BIZ_MODE");
        biz_mode.setTextContent(manifestHead.getBiz_mode());

        Element i_e_flag = document.createElement("I_E_FLAG");
        i_e_flag.setTextContent(manifestHead.getI_e_flag());

        Element i_e_mark = document.createElement("I_E_MARK");
        i_e_mark.setTextContent(manifestHead.getI_e_mark());

        Element start_land = document.createElement("START_LAND");
        start_land.setTextContent(manifestHead.getStart_land());

        Element goal_land = document.createElement("GOAL_LAND");
        goal_land.setTextContent(manifestHead.getGoal_land());

        Element car_no = document.createElement("CAR_NO");
        car_no.setTextContent(manifestHead.getCar_no());

        Element car_wt = document.createElement("CAR_WT");
        car_wt.setTextContent(manifestHead.getCar_wt());

        Element ic_code = document.createElement("IC_CODE");
        ic_code.setTextContent(manifestHead.getIc_code());

        Element goods_wt = document.createElement("GOODS_WT");
        goods_wt.setTextContent(manifestHead.getGoods_wt());

        Element fact_weight = document.createElement("FACT_WEIGHT");
        fact_weight.setTextContent(manifestHead.getFact_weight());

        Element pack_no = document.createElement("PACK_NO");
        pack_no.setTextContent(manifestHead.getPack_no());

        Element m_status = document.createElement("M_STATUS");
        m_status.setTextContent(manifestHead.getM_status());

        Element b_status = document.createElement("B_STATUS");
        b_status.setTextContent(manifestHead.getB_status());

        Element status = document.createElement("STATUS");
        status.setTextContent(manifestHead.getStatus());

        Element port_status = document.createElement("PORT_STATUS");
        port_status.setTextContent(manifestHead.getPort_status());

        Element app_person = document.createElement("APP_PERSON");
        app_person.setTextContent(manifestHead.getApp_person());

        Element app_date = document.createElement("APP_DATE");
        app_date.setTextContent(sdf.format(manifestHead.getApp_date()));

        Element input_code = document.createElement("INPUT_CODE");
        input_code.setTextContent(manifestHead.getInput_code());

        Element input_name = document.createElement("INPUT_NAME");
        input_name.setTextContent(manifestHead.getInput_name());

        Element trade_code = document.createElement("TRADE_CODE");
        trade_code.setTextContent(manifestHead.getTrade_code());

        Element trade_name = document.createElement("TRADE_NAME");
        trade_name.setTextContent(manifestHead.getInput_name());

        Element region_code = document.createElement("REGION_CODE");
        region_code.setTextContent(manifestHead.getRegion_code());

        Element customs_code = document.createElement("CUSTOMS_CODE");
        customs_code.setTextContent(manifestHead.getCustoms_code());

        Element note = document.createElement("NOTE");
        note.setTextContent(manifestHead.getNote());

        Element extend_field_3 = document.createElement("EXTEND_FIELD_3");
        extend_field_3.setTextContent(manifestHead.getExtend_field_3());

        Element platfrom = document.createElement("PLATFROM");
        platfrom.setTextContent(manifestHead.getPlat_from());

        manifest_head.appendChild(auto_id);
        manifest_head.appendChild(manifest_no);
        manifest_head.appendChild(biz_type);
        manifest_head.appendChild(biz_mode);
        manifest_head.appendChild(i_e_flag);
        manifest_head.appendChild(i_e_mark);
        manifest_head.appendChild(start_land);
        manifest_head.appendChild(goal_land);
        manifest_head.appendChild(car_no);
        manifest_head.appendChild(car_wt);
        manifest_head.appendChild(ic_code);
        manifest_head.appendChild(goods_wt);
        manifest_head.appendChild(fact_weight);
        manifest_head.appendChild(pack_no);
        manifest_head.appendChild(m_status);
        manifest_head.appendChild(b_status);
        manifest_head.appendChild(status);
        manifest_head.appendChild(port_status);
        manifest_head.appendChild(app_person);
        manifest_head.appendChild(app_date);
        manifest_head.appendChild(input_code);
        manifest_head.appendChild(input_name);
        manifest_head.appendChild(trade_code);
        manifest_head.appendChild(trade_name);
        manifest_head.appendChild(region_code);
        manifest_head.appendChild(customs_code);
        manifest_head.appendChild(note);
        manifest_head.appendChild(extend_field_3);
        manifest_head.appendChild(platfrom);


        DTCFlow.appendChild(manifest_head);
        messageBody.appendChild(DTCFlow);
        return messageBody;
    }

    //811的头结点
    private Element getMessageHead(Document document, MessageHead messageHead) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        Element messageHeadElement = document.createElement("MessageHead");

        Element messageType = document.createElement("MessageType");
        messageType.setTextContent(messageHead.getMessageType());

        Element MessageId = document.createElement("MessageId");
        MessageId.setTextContent(messageHead.getMessageId());

        Element MessageTime = document.createElement("MessageTime");
        MessageTime.setTextContent(sdf.format(messageHead.getMessageTime()));

        Element SenderId = document.createElement("SenderId");
        SenderId.setTextContent(messageHead.getSenderId());

        Element SenderAddress = document.createElement("SenderAddress");
        SenderAddress.setTextContent(messageHead.getSenderAddress());

        Element ReceiverId = document.createElement("ReceiverId");
        ReceiverId.setTextContent(messageHead.getReceiveId());

        Element ReceiverAddress = document.createElement("ReceiverAddress");
        ReceiverAddress.setTextContent(messageHead.getReceiveAddress());

        Element PlatFormNo = document.createElement("PlatFormNo");
        PlatFormNo.setTextContent(messageHead.getPlatFormNo());

        Element CustomCode = document.createElement("CustomCode");
        CustomCode.setTextContent(messageHead.getCustomCode());

        Element SeqNo = document.createElement("SeqNo");
        SeqNo.setTextContent(messageHead.getSeqNo());

        messageHeadElement.appendChild(messageType);
        messageHeadElement.appendChild(MessageId);
        messageHeadElement.appendChild(MessageTime);
        messageHeadElement.appendChild(SenderId);
        messageHeadElement.appendChild(SenderAddress);
        messageHeadElement.appendChild(ReceiverId);
        messageHeadElement.appendChild(ReceiverAddress);
        messageHeadElement.appendChild(PlatFormNo);
        messageHeadElement.appendChild(CustomCode);
        messageHeadElement.appendChild(SeqNo);

        return messageHeadElement;
    }


}
