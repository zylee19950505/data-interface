package com.xaeport.crossborder.convert311;


import com.xaeport.crossborder.data.entity.BaseTransfer;
import com.xaeport.crossborder.data.entity.CEB311Message;
import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成快件申报
 * Created by zwj on 2017/07/18.
 */
@Component
public class Waybill {
    private Log log = LogFactory.getLog(this.getClass());

    public Element getEntryHead(Document document, CEB311Message ceb311Message) {
        Element entryHead = document.createElement("ceb:OrderHead");
        ImpOrderHead orderHead = ceb311Message.getOrderHead();
        List<Element> list = new ArrayList<>();
        if (orderHead != null) {
            this.getEntryHeadChildren(document, ceb311Message, list);
        }
        for (int i = 0; i < list.size(); i++) {
            entryHead.appendChild(list.get(i));
        }
        return entryHead;
    }

    /**
     * 构建EntryHead 节点
     */
    public void getEntryHeadChildren(Document document, CEB311Message ceb311Message, List<Element> list) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfSfm = new SimpleDateFormat("yyyyMMddhhmmss");
        //<ceb:guid>
        Element guid = document.createElement("ceb:guid");
        guid.setTextContent(ceb311Message.getOrderHead().getGuid());
        list.add(guid);
        //<ceb:appType>
        Element appType = document.createElement("ceb:appType");
        appType.setTextContent(ceb311Message.getOrderHead().getApp_Type());
        list.add(appType);
        //<ceb:appTime>
        Element appTime = document.createElement("ceb:appTime");
        if (!StringUtils.isEmpty(ceb311Message.getOrderHead().getApp_Time())){
            appTime.setTextContent(sdfSfm.format(ceb311Message.getOrderHead().getApp_Time()));
        }
        list.add(appTime);
        //<ceb:appStatus>
        Element appStatus = document.createElement("ceb:appStatus");
        appStatus.setTextContent(ceb311Message.getOrderHead().getApp_Status());
        list.add(appStatus);
        //<ceb:orderType>
        Element orderType = document.createElement("ceb:orderType");
        orderType.setTextContent(ceb311Message.getOrderHead().getOrder_Type());
        list.add(orderType);
        //<ceb:orderNo>
        Element orderNo = document.createElement("ceb:orderNo");
        orderNo.setTextContent(ceb311Message.getOrderHead().getOrder_No());
        list.add(orderNo);
        //<ceb:ebpCode>
        Element ebpCode = document.createElement("ceb:ebpCode");
        ebpCode.setTextContent(ceb311Message.getOrderHead().getEbp_Code());
        list.add(ebpCode);
        //<ceb:ebpName>
        Element ebpName = document.createElement("ceb:ebpName");
        ebpName.setTextContent(ceb311Message.getOrderHead().getEbp_Name());
        list.add(ebpName);
        //<ceb:ebpCode>
        Element ebcCode = document.createElement("ceb:ebcCode");
        ebcCode.setTextContent(ceb311Message.getOrderHead().getEbc_Code());
        list.add(ebcCode);
        //<ceb:ebcName>
        Element ebcName = document.createElement("ceb:ebcName");
        ebcName.setTextContent(ceb311Message.getOrderHead().getEbc_Name());
        list.add(ebcName);
        //<ceb:goodsValue>
        Element goodsValue = document.createElement("ceb:goodsValue");
        goodsValue.setTextContent(ceb311Message.getOrderHead().getGoods_Value());
        list.add(goodsValue);
        //<ceb:freight>
        Element freight = document.createElement("ceb:freight");
        freight.setTextContent(ceb311Message.getOrderHead().getFreight());
        list.add(freight);
        //<ceb:discount>
        Element discount = document.createElement("ceb:discount");
        discount.setTextContent(ceb311Message.getOrderHead().getDiscount());
        list.add(discount);
        //<ceb:taxTotal>
        Element taxTotal = document.createElement("ceb:taxTotal");
        taxTotal.setTextContent(ceb311Message.getOrderHead().getTax_Total());
        list.add(taxTotal);
        //<ceb:acturalPaid>
        Element acturalPaid = document.createElement("ceb:acturalPaid");
        acturalPaid.setTextContent(ceb311Message.getOrderHead().getActural_Paid());
        list.add(acturalPaid);
        //<ceb:currency>
        Element currency = document.createElement("ceb:currency");
        currency.setTextContent(ceb311Message.getOrderHead().getCurrency());
        list.add(currency);
        //<ceb:buyerRegNo>
        Element buyerRegNo = document.createElement("ceb:buyerRegNo");
        buyerRegNo.setTextContent(ceb311Message.getOrderHead().getBuyer_Reg_No());
        list.add(buyerRegNo);
        //<ceb:buyerName>
        Element buyerName = document.createElement("ceb:buyerName");
        buyerName.setTextContent(ceb311Message.getOrderHead().getBuyer_Name());
        list.add(buyerName);
        //<ceb:buyerIdType>
        Element buyerIdType = document.createElement("ceb:buyerIdType");
        buyerIdType.setTextContent(ceb311Message.getOrderHead().getBuyer_Id_Type());
        list.add(buyerIdType);
        //<ceb:buyerIdNumber>
        Element buyerIdNumber = document.createElement("ceb:buyerIdNumber");
        buyerIdNumber.setTextContent(ceb311Message.getOrderHead().getBuyer_Id_Number());
        list.add(buyerIdNumber);
        //<ceb:payCode>
        Element payCode = document.createElement("ceb:payCode");
        payCode.setTextContent(ceb311Message.getOrderHead().getPay_Code());
        list.add(payCode);
        //<ceb:payName>
        Element payName = document.createElement("ceb:payName");
        payName.setTextContent(ceb311Message.getOrderHead().getPayName());
        list.add(payName);
        //<ceb:payTransactionId>
        Element payTransactionId = document.createElement("ceb:payTransactionId");
        payTransactionId.setTextContent(ceb311Message.getOrderHead().getPay_Transaction_Id());
        list.add(payTransactionId);
        //<ceb:batchNumbers>
        Element batchNumbers = document.createElement("ceb:batchNumbers");
        batchNumbers.setTextContent(ceb311Message.getOrderHead().getBatch_Numbers());
        list.add(batchNumbers);
        //<ceb:consignee>
        Element consignee = document.createElement("ceb:consignee");
        consignee.setTextContent(ceb311Message.getOrderHead().getConsignee());
        list.add(consignee);
        //<ceb:consigneeTelephone>
        Element consigneeTelephone = document.createElement("ceb:consigneeTelephone");
        consigneeTelephone.setTextContent(ceb311Message.getOrderHead().getConsignee_Telephone());
        list.add(consigneeTelephone);
        //<ceb:consigneeAddress>
        Element consigneeAddress = document.createElement("ceb:consigneeAddress");
        consigneeAddress.setTextContent(ceb311Message.getOrderHead().getConsignee_Address());
        list.add(consigneeAddress);
        //<ceb:consigneeDistrict>
        Element consigneeDistrict = document.createElement("ceb:consigneeDistrict");
        consigneeDistrict.setTextContent(ceb311Message.getOrderHead().getConsignee_Ditrict());
        list.add(consigneeDistrict);
        //<ceb:note>
        Element note = document.createElement("ceb:note");
        note.setTextContent(ceb311Message.getOrderHead().getNote());
        list.add(note);
    }

    /**
     * 构建EntryList 节点
     */
    public void getEntryList(Document document, Element ceborderheadEl, CEB311Message ceb311Message) {
       //未写
        List<ImpOrderBody> orderBodyList = ceb311Message.getOrderBodyList();
        ImpOrderHead orderHead = ceb311Message.getOrderHead();
        Element orderBodyElement ;
        Element gnum ;
        Element itemNo ;
        Element itemName ;
        Element itemDescribe ;
        Element barCode ;
        Element unit ;
        Element qty ;
        Element price ;
        Element totalPrice ;
        Element currency ;
        Element country ;
        Element note ;
        for (int i = 0; i <orderBodyList.size() ; i++) {
            orderBodyElement = document.createElement("ceb:OrderList");

            gnum = document.createElement("ceb:gnum");
            gnum.setTextContent(String.valueOf(orderBodyList.get(i).getG_num()));

            itemNo = document.createElement("ceb:itemNo");
            itemNo.setTextContent(orderBodyList.get(i).getItem_No());

            itemName = document.createElement("ceb:itemName");
            itemName.setTextContent(orderBodyList.get(i).getItem_Name());

            itemDescribe = document.createElement("ceb:itemDescribe");
            itemDescribe.setTextContent(orderBodyList.get(i).getItem_Describe());

            barCode = document.createElement("ceb:barCode");
            barCode.setTextContent(orderBodyList.get(i).getBar_Code());

            unit = document.createElement("ceb:unit");
            unit.setTextContent(orderBodyList.get(i).getUnit());

            qty = document.createElement("ceb:qty");
            qty.setTextContent(orderBodyList.get(i).getQty());

            price = document.createElement("ceb:price");
            price.setTextContent(orderBodyList.get(i).getPrice());

            totalPrice = document.createElement("ceb:totalPrice");
            totalPrice.setTextContent(orderBodyList.get(i).getTotal_Price());

            currency = document.createElement("ceb:currency");
            currency.setTextContent(orderBodyList.get(i).getCurrency());

            country = document.createElement("ceb:country");
            country.setTextContent(orderBodyList.get(i).getCountry());

            note = document.createElement("ceb:note");
            note.setTextContent(orderBodyList.get(i).getNote());

            orderBodyElement.appendChild(gnum);
            orderBodyElement.appendChild(itemNo);
            orderBodyElement.appendChild(itemName);
            orderBodyElement.appendChild(itemDescribe);
            orderBodyElement.appendChild(barCode);
            orderBodyElement.appendChild(unit);
            orderBodyElement.appendChild(qty);
            orderBodyElement.appendChild(price);
            orderBodyElement.appendChild(totalPrice);
            orderBodyElement.appendChild(currency);
            orderBodyElement.appendChild(country);
            orderBodyElement.appendChild(note);
            ceborderheadEl.appendChild(orderBodyElement);
        }
    }

    //创建<ceb:BaseTransfer> 节点
	public Element getBaseTransfer(Element baseTrElement, Document document, CEB311Message ceb311Message) {
        BaseTransfer baseTransfer = ceb311Message.getBaseTransfer();
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
