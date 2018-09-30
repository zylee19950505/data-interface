package com.xaeport.crossborder.convert311;


import com.xaeport.crossborder.data.entity.*;
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
public class OrderXml {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 构建EntryHead 节点
     */
    public void getEntryHead(Document document, CEB311Message ceb311Message, Element rootElement) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        List<OrderHead> orderHeadList = ceb311Message.getOrderHeadList();

        Element Order;
        Element OrderHead;
        Element guid;
        Element appType;
        Element appTime;
        Element appStatus;
        Element orderType;
        Element orderNo;
        Element ebpCode;
        Element ebpName;
        Element ebcCode;
        Element ebcName;
        Element goodsValue;
        Element freight;
        Element discount;
        Element taxTotal;
        Element acturalPaid;
        Element currency;
        Element buyerRegNo;
        Element buyerName;
        Element buyerTelePhone;
        Element buyerIdType;
        Element buyerIdNumber;
        Element payCode;
        Element payName;
        Element payTransactionId;
        Element batchNumbers;
        Element consignee;
        Element consigneeTelephone;
        Element consigneeAddress;
//        Element consigneeDistrict;
        Element note;

        for (int i = 0; i < orderHeadList.size(); i++) {
            Order = document.createElement("ceb:Order");
            OrderHead = document.createElement("ceb:OrderHead");

            guid = document.createElement("ceb:guid");
            guid.setTextContent(orderHeadList.get(i).getGuid());

            appType = document.createElement("ceb:appType");
            appType.setTextContent(orderHeadList.get(i).getAppType());

            appTime = document.createElement("ceb:appTime");
            appTime.setTextContent(orderHeadList.get(i).getAppTime());

            appStatus = document.createElement("ceb:appStatus");
            appStatus.setTextContent(orderHeadList.get(i).getAppStatus());

            orderType = document.createElement("ceb:orderType");
            orderType.setTextContent(orderHeadList.get(i).getOrderType());

            orderNo = document.createElement("ceb:orderNo");
            orderNo.setTextContent(orderHeadList.get(i).getOrderNo());

            ebpCode = document.createElement("ceb:ebpCode");
            ebpCode.setTextContent(orderHeadList.get(i).getEbpCode());

            ebpName = document.createElement("ceb:ebpName");
            ebpName.setTextContent(orderHeadList.get(i).getEbpName());

            ebcCode = document.createElement("ceb:ebcCode");
            ebcCode.setTextContent(orderHeadList.get(i).getEbcCode());

            ebcName = document.createElement("ceb:ebcName");
            ebcName.setTextContent(orderHeadList.get(i).getEbcName());

            goodsValue = document.createElement("ceb:goodsValue");
            goodsValue.setTextContent(orderHeadList.get(i).getGoodsValue());

            freight = document.createElement("ceb:freight");
            freight.setTextContent(orderHeadList.get(i).getFreight());

            discount = document.createElement("ceb:discount");
            discount.setTextContent(orderHeadList.get(i).getDiscount());

            taxTotal = document.createElement("ceb:taxTotal");
            taxTotal.setTextContent(orderHeadList.get(i).getTaxTotal());

            acturalPaid = document.createElement("ceb:acturalPaid");
            acturalPaid.setTextContent(orderHeadList.get(i).getActuralPaid());

            currency = document.createElement("ceb:currency");
            currency.setTextContent(orderHeadList.get(i).getCurrency());

            buyerRegNo = document.createElement("ceb:buyerRegNo");
            buyerRegNo.setTextContent(orderHeadList.get(i).getBuyerRegNo());

            buyerName = document.createElement("ceb:buyerName");
            buyerName.setTextContent(orderHeadList.get(i).getBuyerName());

            buyerTelePhone = document.createElement("ceb:buyerTelephone");
            buyerTelePhone.setTextContent(orderHeadList.get(i).getBuyerTelePhone());

            buyerIdType = document.createElement("ceb:buyerIdType");
            buyerIdType.setTextContent(orderHeadList.get(i).getBuyerIdType());

            buyerIdNumber = document.createElement("ceb:buyerIdNumber");
            buyerIdNumber.setTextContent(orderHeadList.get(i).getBuyerIdNumber());

            payCode = document.createElement("ceb:payCode");
            payCode.setTextContent(orderHeadList.get(i).getPayCode());

            payName = document.createElement("ceb:payName");
            payName.setTextContent(orderHeadList.get(i).getPayName());

            payTransactionId = document.createElement("ceb:payTransactionId");
            payTransactionId.setTextContent(orderHeadList.get(i).getPayTransactionId());

            batchNumbers = document.createElement("ceb:batchNumbers");
            batchNumbers.setTextContent(orderHeadList.get(i).getBatchNumbers());

            consignee = document.createElement("ceb:consignee");
            consignee.setTextContent(orderHeadList.get(i).getConsignee());

            consigneeTelephone = document.createElement("ceb:consigneeTelephone");
            consigneeTelephone.setTextContent(orderHeadList.get(i).getConsigneeTelephone());

            consigneeAddress = document.createElement("ceb:consigneeAddress");
            consigneeAddress.setTextContent(orderHeadList.get(i).getConsigneeAddress());

            //暂时去除此节点
//            consigneeDistrict = document.createElement("ceb:consigneeDistrict");
//            consigneeDistrict.setTextContent(orderHeadList.get(i).getConsigneeDistrict());

            note = document.createElement("ceb:note");
            note.setTextContent(orderHeadList.get(i).getNote());

            OrderHead.appendChild(guid);
            OrderHead.appendChild(appType);
            OrderHead.appendChild(appTime);
            OrderHead.appendChild(appStatus);
            OrderHead.appendChild(orderType);
            OrderHead.appendChild(orderNo);
            OrderHead.appendChild(payTransactionId);
            OrderHead.appendChild(orderNo);
            OrderHead.appendChild(ebpCode);
            OrderHead.appendChild(ebpName);
            OrderHead.appendChild(ebcCode);
            OrderHead.appendChild(ebcName);
            OrderHead.appendChild(goodsValue);
            OrderHead.appendChild(freight);
            OrderHead.appendChild(discount);
            OrderHead.appendChild(taxTotal);
            OrderHead.appendChild(acturalPaid);
            OrderHead.appendChild(currency);
            OrderHead.appendChild(buyerRegNo);
            OrderHead.appendChild(buyerName);
            OrderHead.appendChild(buyerTelePhone);
            OrderHead.appendChild(buyerIdType);
            OrderHead.appendChild(buyerIdNumber);
            OrderHead.appendChild(payCode);
            OrderHead.appendChild(payName);
            OrderHead.appendChild(payTransactionId);
            OrderHead.appendChild(batchNumbers);
            OrderHead.appendChild(consignee);
            OrderHead.appendChild(consigneeTelephone);
            OrderHead.appendChild(consigneeAddress);
//            OrderHead.appendChild(consigneeDistrict);
            OrderHead.appendChild(note);

            String headGuid = orderHeadList.get(i).getGuid();

            Order.appendChild(OrderHead);
            this.getEntryList(document, ceb311Message, headGuid, Order);

            rootElement.appendChild(Order);

        }
    }

    /**
     * 构建EntryList 节点
     */
    public void getEntryList(Document document, CEB311Message ceb311Message, String headGuid, Element Order) {
        List<ImpOrderBody> impOrderBodyList = ceb311Message.getImpOrderBodyList();

        Element OrderList = null;
        Element gnum;
        Element itemNo;
        Element itemName;
        Element itemDescribe;
        Element barCode;
        Element unit;
        Element gModel;
        Element qty;
        Element price;
        Element totalPrice;
        Element currency;
        Element country;
        Element note;

        for (int i = 0; i < impOrderBodyList.size(); i++) {

            if ((impOrderBodyList.get(i).getHead_guid()).equals(headGuid)) {
                OrderList = document.createElement("ceb:OrderList");

                gnum = document.createElement("ceb:gnum");
                gnum.setTextContent(String.valueOf(impOrderBodyList.get(i).getG_num()));

                itemNo = document.createElement("ceb:itemNo");
                itemNo.setTextContent(StringUtils.isEmpty(impOrderBodyList.get(i).getItem_No()) ? "" : impOrderBodyList.get(i).getItem_No());

                itemName = document.createElement("ceb:itemName");
                itemName.setTextContent(impOrderBodyList.get(i).getItem_Name());

//                itemDescribe = document.createElement("ceb:itemDescribe");
//                itemDescribe.setTextContent(StringUtils.isEmpty(impOrderBodyList.get(i).getItem_Describe()) ? "" : impOrderBodyList.get(i).getItem_Describe());

                barCode = document.createElement("ceb:barCode");
                barCode.setTextContent(impOrderBodyList.get(i).getBar_Code());

                unit = document.createElement("ceb:unit");
                unit.setTextContent(impOrderBodyList.get(i).getUnit());

                gModel = document.createElement("ceb:gmodel");
                gModel.setTextContent(impOrderBodyList.get(i).getG_Model());

                qty = document.createElement("ceb:qty");
                qty.setTextContent(impOrderBodyList.get(i).getQty());

                price = document.createElement("ceb:price");
                price.setTextContent(impOrderBodyList.get(i).getPrice());

                totalPrice = document.createElement("ceb:totalPrice");
                totalPrice.setTextContent(impOrderBodyList.get(i).getTotal_Price());

                currency = document.createElement("ceb:currency");
                currency.setTextContent(impOrderBodyList.get(i).getCurrency());

                country = document.createElement("ceb:country");
                country.setTextContent(impOrderBodyList.get(i).getCountry());

                note = document.createElement("ceb:note");
                note.setTextContent(StringUtils.isEmpty(impOrderBodyList.get(i).getNote()) ? "" : impOrderBodyList.get(i).getNote());

                OrderList.appendChild(gnum);
                OrderList.appendChild(itemNo);
                OrderList.appendChild(itemName);
//                OrderList.appendChild(itemDescribe);
                OrderList.appendChild(barCode);
                OrderList.appendChild(unit);
                OrderList.appendChild(gModel);
                OrderList.appendChild(qty);
                OrderList.appendChild(price);
                OrderList.appendChild(totalPrice);
                OrderList.appendChild(currency);
                OrderList.appendChild(country);
                OrderList.appendChild(note);

                Order.appendChild(OrderList);
            }

        }

    }


}
