package com.xaeport.crossborder.convert711;


import com.xaeport.crossborder.data.entity.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 生成快件申报
 * Created by zwj on 2017/07/18.
 */
@Component
public class DeliveryDeclareXML {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 构建EntryHead 节点
     */
    public void getEntryHead(Document document, CEB711Message ceb711Message, Element rootElement) {

        ImpDeliveryHead impDeliveryHead = ceb711Message.getImpDeliveryHead();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Element Delivery;
        Element DeliveryHead;
        Element guid;
        Element appType;
        Element appTime;
        Element appStatus;
        Element customsCode;
        Element copNo;
        Element preNo;
        Element rkdNo;
        Element operatorCode;
        Element operatorName;
        Element ieFlag;
        Element trafMode;
        Element trafNo;
        Element voyageNo;
        Element billNo;
        Element logisticsCode;
        Element logisticsName;
        Element unloadLocation;
        Element note;

        Delivery = document.createElement("ceb:Delivery");

        DeliveryHead = document.createElement("ceb:DeliveryHead");

        guid = document.createElement("ceb:guid");
        guid.setTextContent(impDeliveryHead.getGuid());

        appType = document.createElement("ceb:appType");
        appType.setTextContent(impDeliveryHead.getApp_type());

        appTime = document.createElement("ceb:appTime");
        appTime.setTextContent(sdf.format(impDeliveryHead.getApp_time()));

        appStatus = document.createElement("ceb:appStatus");
        appStatus.setTextContent(impDeliveryHead.getApp_status());

        customsCode = document.createElement("ceb:customsCode");
        customsCode.setTextContent(impDeliveryHead.getCustoms_code());

        copNo = document.createElement("ceb:copNo");
        copNo.setTextContent(impDeliveryHead.getCop_no());

        preNo = document.createElement("ceb:preNo");
        preNo.setTextContent(impDeliveryHead.getPre_no());

        rkdNo = document.createElement("ceb:rkdNo");
        rkdNo.setTextContent(impDeliveryHead.getRkd_no());

        operatorCode = document.createElement("ceb:operatorCode");
        operatorCode.setTextContent(impDeliveryHead.getOperator_code());

        operatorName = document.createElement("ceb:operatorName");
        operatorName.setTextContent(impDeliveryHead.getOperator_name());

        ieFlag = document.createElement("ceb:ieFlag");
        ieFlag.setTextContent(impDeliveryHead.getIe_flag());

        trafMode = document.createElement("ceb:trafMode");
        trafMode.setTextContent(impDeliveryHead.getTraf_mode());

        trafNo = document.createElement("ceb:trafNo");
        trafNo.setTextContent(impDeliveryHead.getTraf_no());

        voyageNo = document.createElement("ceb:voyageNo");
        voyageNo.setTextContent(impDeliveryHead.getVoyage_no());

        billNo = document.createElement("ceb:billNo");
        billNo.setTextContent(impDeliveryHead.getBill_no());

        logisticsCode = document.createElement("ceb:logisticsCode");
        logisticsCode.setTextContent(impDeliveryHead.getLogistics_code());

        logisticsName = document.createElement("ceb:logisticsName");
        logisticsName.setTextContent(impDeliveryHead.getLogistics_name());

        unloadLocation = document.createElement("ceb:unloadLocation");
        unloadLocation.setTextContent(impDeliveryHead.getUnload_location());

        note = document.createElement("ceb:note");
        note.setTextContent(impDeliveryHead.getNote());

        DeliveryHead.appendChild(guid);
        DeliveryHead.appendChild(appType);
        DeliveryHead.appendChild(appTime);
        DeliveryHead.appendChild(appStatus);
        DeliveryHead.appendChild(customsCode);
        DeliveryHead.appendChild(copNo);
        DeliveryHead.appendChild(preNo);
        DeliveryHead.appendChild(rkdNo);
        DeliveryHead.appendChild(operatorCode);
        DeliveryHead.appendChild(operatorName);
        DeliveryHead.appendChild(ieFlag);
        DeliveryHead.appendChild(trafMode);
        DeliveryHead.appendChild(trafNo);
        DeliveryHead.appendChild(voyageNo);
        DeliveryHead.appendChild(billNo);
        DeliveryHead.appendChild(logisticsCode);
        DeliveryHead.appendChild(logisticsName);
        DeliveryHead.appendChild(unloadLocation);
        DeliveryHead.appendChild(note);

        Delivery.appendChild(DeliveryHead);
        this.getEntryList(document, ceb711Message, Delivery);

        rootElement.appendChild(Delivery);

    }

    /**
     * 构建EntryList 节点
     */
    public void getEntryList(Document document, CEB711Message ceb711Message, Element Delivery) {
        //未写
        List<ImpDeliveryBody> impDeliveryBodyList = ceb711Message.getImpDeliveryBodyList();

        Element DeliveryList;
        Element gnum;
        Element logisticsNo;
        Element note;

        for (int i = 0; i < impDeliveryBodyList.size(); i++) {

            DeliveryList = document.createElement("ceb:DeliveryList");

            gnum = document.createElement("ceb:gnum");
            gnum.setTextContent(String.valueOf(impDeliveryBodyList.get(i).getG_num()));

            logisticsNo = document.createElement("ceb:logisticsNo");
            logisticsNo.setTextContent(impDeliveryBodyList.get(i).getLogistics_no());

            note = document.createElement("ceb:note");
            note.setTextContent(impDeliveryBodyList.get(i).getNote());

            DeliveryList.appendChild(gnum);
            DeliveryList.appendChild(logisticsNo);
            DeliveryList.appendChild(note);

            Delivery.appendChild(DeliveryList);
        }
    }


}
