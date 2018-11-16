package com.xaeport.crossborder.convert511;


import com.xaeport.crossborder.data.entity.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 生成支付单报文
 * Created by zwj on 2017/07/18.
 */
@Component
public class LogisticsXml {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 构建EntryList 节点
     */
    public void getLogisticsList(Document document, Element rootElement, CEB511Message ceb511Message) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        List<LogisticsHead> logisticsHeadsList = ceb511Message.getLogisticsHeadList();

        Element Logistics;
        Element LogisticsHead;
        Element guid;
        Element appType;
        Element appTime;
        Element appStatus;
        Element logisticsCode;
        Element logisticsName;
        Element logisticsNo;
        Element orderNo;
        Element billNo;
        Element freight;
        Element insuredFee;
        Element currency;
        Element weight;
        Element packNo;
        Element goodsInfo;
        Element consignee;
        Element consigneeAddress;
        Element consigneeTelephone;
        Element note;

        for (int i = 0; i < logisticsHeadsList.size(); i++) {

            Logistics = document.createElement("ceb:Logistics");
            LogisticsHead = document.createElement("ceb:LogisticsHead");

            guid = document.createElement("ceb:guid");
            guid.setTextContent(logisticsHeadsList.get(i).getGuid());

            appType = document.createElement("ceb:appType");
            appType.setTextContent(logisticsHeadsList.get(i).getAppType());

            appTime = document.createElement("ceb:appTime");
            appTime.setTextContent(logisticsHeadsList.get(i).getAppTime());

            appStatus = document.createElement("ceb:appStatus");
            appStatus.setTextContent(logisticsHeadsList.get(i).getAppStatus());

            logisticsCode = document.createElement("ceb:logisticsCode");
            logisticsCode.setTextContent(logisticsHeadsList.get(i).getLogisticsCode());

            logisticsName = document.createElement("ceb:logisticsName");
            logisticsName.setTextContent(logisticsHeadsList.get(i).getLogisticsName());

            logisticsNo = document.createElement("ceb:logisticsNo");
            logisticsNo.setTextContent(logisticsHeadsList.get(i).getLogisticsNo());

            orderNo = document.createElement("ceb:orderNo");
            orderNo.setTextContent(logisticsHeadsList.get(i).getOrderNo());

//            billNo = document.createElement("ceb:billNo");
//            billNo.setTextContent(logisticsHeadsList.get(i).getBillNo());

            freight = document.createElement("ceb:freight");
            freight.setTextContent(logisticsHeadsList.get(i).getFreight());

            insuredFee = document.createElement("ceb:insuredFee");
            insuredFee.setTextContent(logisticsHeadsList.get(i).getInsuredFee());

            currency = document.createElement("ceb:currency");
            currency.setTextContent(logisticsHeadsList.get(i).getCurrency());

            weight = document.createElement("ceb:weight");
            weight.setTextContent(logisticsHeadsList.get(i).getWeight());

            packNo = document.createElement("ceb:packNo");
            packNo.setTextContent(logisticsHeadsList.get(i).getPackNo());

            goodsInfo = document.createElement("ceb:goodsInfo");
            goodsInfo.setTextContent(logisticsHeadsList.get(i).getGoodsInfo());

            consignee = document.createElement("ceb:consignee");
            consignee.setTextContent(logisticsHeadsList.get(i).getConsingee());

            consigneeAddress = document.createElement("ceb:consigneeAddress");
            consigneeAddress.setTextContent(logisticsHeadsList.get(i).getConsigneeAddress());

            consigneeTelephone = document.createElement("ceb:consigneeTelephone");
            consigneeTelephone.setTextContent(logisticsHeadsList.get(i).getConsigneeTelephone());

            note = document.createElement("ceb:note");
            note.setTextContent(logisticsHeadsList.get(i).getNote());

            LogisticsHead.appendChild(guid);
            LogisticsHead.appendChild(appType);
            LogisticsHead.appendChild(appTime);
            LogisticsHead.appendChild(appStatus);
            LogisticsHead.appendChild(logisticsCode);
            LogisticsHead.appendChild(logisticsName);
            LogisticsHead.appendChild(logisticsNo);
            LogisticsHead.appendChild(orderNo);
//            LogisticsHead.appendChild(billNo);
            LogisticsHead.appendChild(freight);
            LogisticsHead.appendChild(insuredFee);
            LogisticsHead.appendChild(currency);
            LogisticsHead.appendChild(weight);
            LogisticsHead.appendChild(packNo);
            LogisticsHead.appendChild(goodsInfo);
            LogisticsHead.appendChild(consignee);
            LogisticsHead.appendChild(consigneeAddress);
            LogisticsHead.appendChild(consigneeTelephone);
            LogisticsHead.appendChild(note);

            Logistics.appendChild(LogisticsHead);

            rootElement.appendChild(Logistics);

        }
    }

}
