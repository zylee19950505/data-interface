package com.xaeport.crossborder.convert621.generate;


import com.xaeport.crossborder.data.entity.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 生成快件申报
 * Created by zwj on 2017/07/18.
 */
@Component
public class DetailDeclareXML {
    private Log log = LogFactory.getLog(this.getClass());

    public Element getEntryHead(Document document, CEB621Message ceb621Message) {
        Element entryHead = document.createElement("ceb:InventoryHead");
        ImpInventoryHead InventoryHead = ceb621Message.getImpInventoryHead();
        List<Element> list = new ArrayList<>();
        if (InventoryHead != null) {
            this.getEntryHeadChildren(document, ceb621Message, list);
        }
        for (int i = 0; i < list.size(); i++) {
            entryHead.appendChild(list.get(i));
        }
        return entryHead;
    }

    /**
     * 构建EntryHead 节点
     */
    public void getEntryHeadChildren(Document document, CEB621Message ceb621Message, List<Element> list) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfSfm = new SimpleDateFormat("yyyyMMddhhmmss");
        //<ceb:guid>
        Element guid = document.createElement("ceb:guid");
        guid.setTextContent(ceb621Message.getImpInventoryHead().getGuid());
        list.add(guid);
        //<ceb:appType>
        Element appType = document.createElement("ceb:appType");
        appType.setTextContent(ceb621Message.getImpInventoryHead().getApp_type());
        list.add(appType);
        //<ceb:appTime>
        Element appTime = document.createElement("ceb:appTime");
        if (!StringUtils.isEmpty(ceb621Message.getImpInventoryHead().getApp_time())){
            appTime.setTextContent(sdfSfm.format(ceb621Message.getImpInventoryHead().getApp_time()));
        }
        list.add(appTime);
        //<ceb:appStatus>
        Element appStatus = document.createElement("ceb:appStatus");
        appStatus.setTextContent(ceb621Message.getImpInventoryHead().getApp_status());
        list.add(appStatus);
        //<ceb:orderNo>
        Element orderNo = document.createElement("ceb:orderNo");
        orderNo.setTextContent(ceb621Message.getImpInventoryHead().getOrder_no());
        list.add(orderNo);
        //<ceb:ebpCode>
        Element ebpCode = document.createElement("ceb:ebpCode");
        ebpCode.setTextContent(ceb621Message.getImpInventoryHead().getEbp_code());
        list.add(ebpCode);
        //<ceb:ebpName>
        Element ebpName = document.createElement("ceb:ebpName");
        ebpName.setTextContent(ceb621Message.getImpInventoryHead().getEbp_name());
        list.add(ebpName);
        //<ceb:ebcCode>
        Element ebcCode = document.createElement("ceb:ebcCode");
        ebcCode.setTextContent(ceb621Message.getImpInventoryHead().getEbc_code());
        list.add(ebcCode);
        //<ceb:ebcName>
        Element ebcName = document.createElement("ceb:ebcName");
        ebcName.setTextContent(ceb621Message.getImpInventoryHead().getEbc_name());
        list.add(ebcName);
        //<ceb:logisticsNo>
        Element logisticsNo = document.createElement("ceb:logisticsNo");
        logisticsNo.setTextContent(ceb621Message.getImpInventoryHead().getLogistics_no());
        list.add(logisticsNo);
        //<ceb:logisticsCode>
        Element logisticsCode = document.createElement("ceb:logisticsCode");
        logisticsCode.setTextContent(ceb621Message.getImpInventoryHead().getLogistics_code());
        list.add(logisticsCode);
        //<ceb:logisticsName>
        Element logisticsName = document.createElement("ceb:logisticsName");
        logisticsName.setTextContent(ceb621Message.getImpInventoryHead().getLogistics_name());
        list.add(logisticsName);
        //<ceb:copNo>
        Element copNo = document.createElement("ceb:copNo");
        copNo.setTextContent(ceb621Message.getImpInventoryHead().getCop_no());
        list.add(copNo);
        //<ceb:preNo>
        Element preNo = document.createElement("ceb:preNo");
        preNo.setTextContent(ceb621Message.getImpInventoryHead().getPre_no());
        list.add(preNo);
        //<ceb:assureCode>
        Element assureCode = document.createElement("ceb:assureCode");
        assureCode.setTextContent(ceb621Message.getImpInventoryHead().getAssure_code());
        list.add(assureCode);
        //<ceb:emsNo>
        Element emsNo = document.createElement("ceb:emsNo");
        emsNo.setTextContent(ceb621Message.getImpInventoryHead().getEms_no());
        list.add(emsNo);
        //<ceb:invtNo>
        Element invtNo = document.createElement("ceb:invtNo");
        invtNo.setTextContent(ceb621Message.getImpInventoryHead().getInvt_no());
        list.add(invtNo);
        //<ceb:ieFlag>
        Element ieFlag = document.createElement("ceb:ieFlag");
        ieFlag.setTextContent(ceb621Message.getImpInventoryHead().getIe_flag());
        list.add(ieFlag);
        //<ceb:declTime>
        Element declTime = document.createElement("ceb:declTime");
        declTime.setTextContent(sdf.format(new Date()));
        list.add(declTime);
        //<ceb:customsCode>
        Element customsCode = document.createElement("ceb:customsCode");
        customsCode.setTextContent(ceb621Message.getImpInventoryHead().getCustoms_code());
        list.add(customsCode);
        //<ceb:portCode>
        Element portCode = document.createElement("ceb:portCode");
        portCode.setTextContent(ceb621Message.getImpInventoryHead().getPort_code());
        list.add(portCode);
        //<ceb:ieDate>
        Element ieDate = document.createElement("ceb:ieDate");
        ieDate.setTextContent(sdfSfm.format(ceb621Message.getImpInventoryHead().getIe_date()));
        list.add(ieDate);
        //<ceb:buyerIdType>
        Element buyerIdType = document.createElement("ceb:buyerIdType");
        buyerIdType.setTextContent(ceb621Message.getImpInventoryHead().getBuyer_id_type());
        list.add(buyerIdType);
        //<ceb:buyerIdNumber>
        Element buyerIdNumber = document.createElement("ceb:buyerIdNumber");
        buyerIdNumber.setTextContent(ceb621Message.getImpInventoryHead().getBuyer_id_number());
        list.add(buyerIdNumber);
        //<ceb:buyerName>
        Element buyerName = document.createElement("ceb:buyerName");
        buyerName.setTextContent(ceb621Message.getImpInventoryHead().getBuyer_name());
        list.add(buyerName);
        //<ceb:buyerTelephone>
        Element buyerTelephone = document.createElement("ceb:buyerTelephone");
        buyerTelephone.setTextContent(ceb621Message.getImpInventoryHead().getBuyer_telephone());
        list.add(buyerTelephone);
        //<ceb:consigneeAddress>
        Element consigneeAddress = document.createElement("ceb:consigneeAddress");
        consigneeAddress.setTextContent(ceb621Message.getImpInventoryHead().getConsignee_address());
        list.add(consigneeAddress);
        //<ceb:agentCode>
        Element agentCode = document.createElement("ceb:agentCode");
        agentCode.setTextContent(ceb621Message.getImpInventoryHead().getAgent_code());
        list.add(agentCode);
        //<ceb:agentName>
        Element agentName = document.createElement("ceb:agentName");
        agentName.setTextContent(ceb621Message.getImpInventoryHead().getAgent_name());
        list.add(agentName);
        //<ceb:areaCode>
        Element areaCode = document.createElement("ceb:areaCode");
        areaCode.setTextContent(ceb621Message.getImpInventoryHead().getArea_code());
        list.add(areaCode);
        //<ceb:areaName>
        Element areaName = document.createElement("ceb:areaName");
        areaName.setTextContent(ceb621Message.getImpInventoryHead().getArea_name());
        list.add(areaName);
        //<ceb:tradeMode>
        Element tradeMode = document.createElement("ceb:tradeMode");
        tradeMode.setTextContent(ceb621Message.getImpInventoryHead().getTrade_mode());
        list.add(tradeMode);
        //<ceb:trafMode>
        Element trafMode = document.createElement("ceb:trafMode");
        trafMode.setTextContent(ceb621Message.getImpInventoryHead().getTraf_mode());
        list.add(trafMode);
        //<ceb:trafNo>
        Element trafNo = document.createElement("ceb:trafNo");
        trafNo.setTextContent(ceb621Message.getImpInventoryHead().getTraf_no());
        list.add(trafNo);
        //<ceb:voyageNo>
        Element voyageNo = document.createElement("ceb:voyageNo");
        voyageNo.setTextContent(ceb621Message.getImpInventoryHead().getVoyage_no());
        list.add(voyageNo);
        //<ceb:billNo>
        Element billNo = document.createElement("ceb:billNo");
        billNo.setTextContent(ceb621Message.getImpInventoryHead().getBill_no());
        list.add(billNo);
        //<ceb:loctNo>
        Element loctNo = document.createElement("ceb:loctNo");
        loctNo.setTextContent(ceb621Message.getImpInventoryHead().getLoct_no());
        list.add(loctNo);
        //<ceb:licenseNo>
        Element licenseNo = document.createElement("ceb:licenseNo");
        licenseNo.setTextContent(ceb621Message.getImpInventoryHead().getLicense_no());
        list.add(licenseNo);
        //<ceb:country>
        Element country = document.createElement("ceb:country");
        country.setTextContent(ceb621Message.getImpInventoryHead().getCountry());
        list.add(country);
        //<ceb:freight>
        Element freight = document.createElement("ceb:freight");
        freight.setTextContent(ceb621Message.getImpInventoryHead().getFreight());
        list.add(freight);
        //<ceb:insuredFee>
        Element insuredFee = document.createElement("ceb:insuredFee");
        insuredFee.setTextContent(ceb621Message.getImpInventoryHead().getInsured_fee());
        list.add(insuredFee);
        //<ceb:currency>
        Element currency = document.createElement("ceb:currency");
        currency.setTextContent(ceb621Message.getImpInventoryHead().getCurrency());
        list.add(currency);
        //<ceb:wrapType>
        Element wrapType = document.createElement("ceb:wrapType");
        wrapType.setTextContent(ceb621Message.getImpInventoryHead().getWrap_type());
        list.add(wrapType);
        //<ceb:packNo>
        Element packNo = document.createElement("ceb:packNo");
        packNo.setTextContent(ceb621Message.getImpInventoryHead().getPack_no());
        list.add(packNo);
        //<ceb:grossWeight>
        Element grossWeight = document.createElement("ceb:grossWeight");
        grossWeight.setTextContent(ceb621Message.getImpInventoryHead().getGross_weight());
        list.add(grossWeight);
        //<ceb:netWeight>
        Element netWeight = document.createElement("ceb:netWeight");
        netWeight.setTextContent(ceb621Message.getImpInventoryHead().getNet_weight());
        list.add(netWeight);
        //<ceb:note>
        Element note = document.createElement("ceb:note");
        note.setTextContent(ceb621Message.getImpInventoryHead().getNote());
        list.add(note);
    }

    /**
     * 构建EntryList 节点
     */
    public void getEntryList(Document document, Element ceborderheadEl, CEB621Message ceb621Message) {
       //未写
        List<ImpInventoryBody> inventoryBodyList = ceb621Message.getImpInventoryBodyList();
        ImpInventoryHead inventoryHead = ceb621Message.getImpInventoryHead();

        Element InventoryBodyElement ;
        Element gnum ;
        Element itemRecordNo ;
        Element itemNo ;
        Element itemName ;
        Element gcode ;
        Element gname ;
        Element gmodel ;
        Element barCode ;
        Element country ;
        Element currency ;
        Element qty ;
        Element unit ;
        Element qty1 ;
        Element unit1 ;
        Element qty2 ;
        Element unit2 ;
        Element price ;
        Element totalPrice ;


        Element note ;
        for (int i = 0; i <inventoryBodyList.size() ; i++) {
            InventoryBodyElement = document.createElement("ceb:InventoryList");

            gnum = document.createElement("ceb:gnum");
            gnum.setTextContent(String.valueOf(inventoryBodyList.get(i).getG_num()));

            itemRecordNo = document.createElement("ceb:itemRecordNo");
            itemRecordNo.setTextContent(inventoryBodyList.get(i).getItem_record_no());

            itemNo = document.createElement("ceb:itemNo");
            itemNo.setTextContent(inventoryBodyList.get(i).getItem_no());

            itemName = document.createElement("ceb:itemName");
            itemName.setTextContent(inventoryBodyList.get(i).getItem_name());

            gcode = document.createElement("ceb:gcode");
            gcode.setTextContent(inventoryBodyList.get(i).getG_code());

            gname = document.createElement("ceb:gname");
            gname.setTextContent(inventoryBodyList.get(i).getG_name());

            gmodel = document.createElement("ceb:gmodel");
            gmodel.setTextContent(inventoryBodyList.get(i).getG_model());

            barCode = document.createElement("ceb:barCode");
            barCode.setTextContent(inventoryBodyList.get(i).getBar_code());

            country = document.createElement("ceb:country");
            country.setTextContent(inventoryBodyList.get(i).getCountry());

            currency = document.createElement("ceb:currency");
            currency.setTextContent(inventoryBodyList.get(i).getCurrency());

            qty = document.createElement("ceb:qty");
            qty.setTextContent(inventoryBodyList.get(i).getQty());

            unit = document.createElement("ceb:unit");
            unit.setTextContent(inventoryBodyList.get(i).getUnit());

            qty1 = document.createElement("ceb:qty1");
            qty1.setTextContent(inventoryBodyList.get(i).getQty1());

            unit1 = document.createElement("ceb:unit1");
            unit1.setTextContent(inventoryBodyList.get(i).getUnit1());

            qty2 = document.createElement("ceb:qty2");
            qty2.setTextContent(inventoryBodyList.get(i).getQty2());

            unit2 = document.createElement("ceb:unit2");
            unit2.setTextContent(inventoryBodyList.get(i).getUnit2());

            price = document.createElement("ceb:price");
            price.setTextContent(inventoryBodyList.get(i).getPrice());

            totalPrice = document.createElement("ceb:totalPrice");
            totalPrice.setTextContent(inventoryBodyList.get(i).getTotal_price());


            note = document.createElement("ceb:note");
            note.setTextContent(inventoryBodyList.get(i).getNote());

            InventoryBodyElement.appendChild(gnum);
            InventoryBodyElement.appendChild(itemRecordNo);
            InventoryBodyElement.appendChild(itemNo);
            InventoryBodyElement.appendChild(itemName);
            InventoryBodyElement.appendChild(gcode);
            InventoryBodyElement.appendChild(gname);
            InventoryBodyElement.appendChild(gmodel);
            InventoryBodyElement.appendChild(barCode);
            InventoryBodyElement.appendChild(country);
            InventoryBodyElement.appendChild(currency);
            InventoryBodyElement.appendChild(qty);
            InventoryBodyElement.appendChild(unit);
            InventoryBodyElement.appendChild(qty1);
            InventoryBodyElement.appendChild(unit1);
            InventoryBodyElement.appendChild(qty2);
            InventoryBodyElement.appendChild(unit2);
            InventoryBodyElement.appendChild(price);
            InventoryBodyElement.appendChild(totalPrice);

            ceborderheadEl.appendChild(InventoryBodyElement);
        }
    }

    //创建<ceb:BaseTransfer> 节点
	public Element getBaseTransfer(Element baseTrElement, Document document, CEB621Message ceb621Message) {
        BaseTransfer baseTransfer = ceb621Message.getBaseTransfer();
        Element copCodElement = document.createElement("ceb:copCode");
        copCodElement.setTextContent(baseTransfer.getCopCode());
        baseTrElement.appendChild(copCodElement);
        Element copNameElement = document.createElement("ceb:copName");
        copNameElement.setTextContent(baseTransfer.getCopName());
        baseTrElement.appendChild(copNameElement);
        Element dxpModeElement = document.createElement("ceb:dxpMode");
        dxpModeElement.setTextContent(baseTransfer.getDxpMode());
        baseTrElement.appendChild(dxpModeElement);
        Element dxpIdElement = document.createElement("ceb:dxpId");
        dxpIdElement.setTextContent(baseTransfer.getDxpId());
        baseTrElement.appendChild(dxpIdElement);
        Element noteElement = document.createElement("ceb:note");
        noteElement.setTextContent(baseTransfer.getNote());
        baseTrElement.appendChild(noteElement);
        return baseTrElement;
	}
}
