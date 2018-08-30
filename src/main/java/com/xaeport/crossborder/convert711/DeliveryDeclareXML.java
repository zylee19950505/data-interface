//package com.xaeport.crossborder.convert711;
//
//
//import com.xaeport.crossborder.data.entity.*;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
//import java.util.List;
//
///**
// * 生成快件申报
// * Created by zwj on 2017/07/18.
// */
//@Component
//public class DeliveryDeclareXML {
//
//    private Log log = LogFactory.getLog(this.getClass());
//
//    /**
//     * 构建EntryHead 节点
//     */
//    public void getEntryHead(Document document, CEB711Message ceb711Message, Element rootElement) {
//
//        ImpDeliveryHead impDeliveryHead = ceb711Message.getImpDeliveryHead();
//
//        Element Inventory;
//        Element InventoryHead;
//        Element guid;
//        Element appType;
//        Element appTime;
//        Element appStatus;
//        Element orderNo;
//        Element ebpCode;
//        Element ebpName;
//        Element ebcCode;
//        Element ebcName;
//        Element logisticsNo;
//        Element logisticsCode;
//        Element logisticsName;
//        Element copNo;
//        Element preNo;
//        Element assureCode;
//        Element emsNo;
//        Element invtNo;
//        Element ieFlag;
//        Element declTime;
//        Element customsCode;
//        Element portCode;
//        Element ieDate;
//        Element buyerIdType;
//        Element buyerIdNumber;
//        Element buyerName;
//        Element buyerTelephone;
//        Element consigneeAddress;
//        Element agentCode;
//        Element agentName;
//        Element areaCode;
//        Element areaName;
//        Element tradeMode;
//        Element trafMode;
//
//            Inventory = document.createElement("ceb:Inventory");
//            InventoryHead = document.createElement("ceb:InventoryHead");
//
//            guid = document.createElement("ceb:guid");
//            guid.setTextContent(impDeliveryHeadList.getGuid());
//
//            appType = document.createElement("ceb:appType");
//            appType.setTextContent(impDeliveryHeadList.getAppType());
//
//            appTime = document.createElement("ceb:appTime");
//            appTime.setTextContent(impDeliveryHeadList.get(i).getAppTime());
//
//            appStatus = document.createElement("ceb:appStatus");
//            appStatus.setTextContent(impDeliveryHeadList.get(i).getAppStatus());
//
//            orderNo = document.createElement("ceb:orderNo");
//            orderNo.setTextContent(impDeliveryHeadList.get(i).getOrderNo());
//
//            ebpCode = document.createElement("ceb:ebpCode");
//            ebpCode.setTextContent(impDeliveryHeadList.get(i).getEbpCode());
//
//            ebpName = document.createElement("ceb:ebpName");
//            ebpName.setTextContent(impDeliveryHeadList.get(i).getEbpName());
//
//            ebcCode = document.createElement("ceb:ebcCode");
//            ebcCode.setTextContent(impDeliveryHeadList.get(i).getEbcCode());
//
//            ebcName = document.createElement("ceb:ebcName");
//            ebcName.setTextContent(impDeliveryHeadList.get(i).getEbcName());
//
//            logisticsNo = document.createElement("ceb:logisticsNo");
//            logisticsNo.setTextContent(impDeliveryHeadList.get(i).getLogisticsNo());
//
//            logisticsCode = document.createElement("ceb:logisticsCode");
//            logisticsCode.setTextContent(impDeliveryHeadList.get(i).getLogisticsCode());
//
//            logisticsName = document.createElement("ceb:logisticsName");
//            logisticsName.setTextContent(impDeliveryHeadList.get(i).getLogisticsName());
//
//            copNo = document.createElement("ceb:copNo");
//            copNo.setTextContent(impDeliveryHeadList.get(i).getCopNo());
//
//            preNo = document.createElement("ceb:preNo");
//            preNo.setTextContent(impDeliveryHeadList.get(i).getPreNo());
//
//            assureCode = document.createElement("ceb:assureCode");
//            assureCode.setTextContent(impDeliveryHeadList.get(i).getAssureCode());
//
//            emsNo = document.createElement("ceb:emsNo");
//            emsNo.setTextContent(impDeliveryHeadList.get(i).getEmsNo());
//
//            invtNo = document.createElement("ceb:invtNo");
//            invtNo.setTextContent(impDeliveryHeadList.get(i).getInvtNo());
//
//            ieFlag = document.createElement("ceb:ieFlag");
//            ieFlag.setTextContent(inventoryHeads.get(i).getIeFlag());
//
//            declTime = document.createElement("ceb:declTime");
//            declTime.setTextContent(inventoryHeads.get(i).getDeclTime());
//
//            customsCode = document.createElement("ceb:customsCode");
//            customsCode.setTextContent(inventoryHeads.get(i).getCustomsCode());
//
//            portCode = document.createElement("ceb:portCode");
//            portCode.setTextContent(inventoryHeads.get(i).getPortCode());
//
//            ieDate = document.createElement("ceb:ieDate");
//            ieDate.setTextContent(inventoryHeads.get(i).getIeDate());
//
//            buyerIdType = document.createElement("ceb:buyerIdType");
//            buyerIdType.setTextContent(inventoryHeads.get(i).getBuyerIdType());
//
//            buyerIdNumber = document.createElement("ceb:buyerIdNumber");
//            buyerIdNumber.setTextContent(inventoryHeads.get(i).getBuyerIdNumber());
//
//            buyerName = document.createElement("ceb:buyerName");
//            buyerName.setTextContent(inventoryHeads.get(i).getBuyerName());
//
//            buyerTelephone = document.createElement("ceb:buyerTelephone");
//            buyerTelephone.setTextContent(inventoryHeads.get(i).getBuyerTelephone());
//
//            consigneeAddress = document.createElement("ceb:consigneeAddress");
//            consigneeAddress.setTextContent(inventoryHeads.get(i).getConsigneeAddress());
//
//            agentCode = document.createElement("ceb:agentCode");
//            agentCode.setTextContent(inventoryHeads.get(i).getAgentCode());
//
//            agentName = document.createElement("ceb:agentName");
//            agentName.setTextContent(inventoryHeads.get(i).getAgentName());
//
//            areaCode = document.createElement("ceb:areaCode");
//            areaCode.setTextContent(inventoryHeads.get(i).getAreaCode());
//
//            areaName = document.createElement("ceb:areaName");
//            areaName.setTextContent(inventoryHeads.get(i).getAgentName());
//
//            tradeMode = document.createElement("ceb:tradeMode");
//            tradeMode.setTextContent(inventoryHeads.get(i).getTradeMode());
//
//            trafMode = document.createElement("ceb:trafMode");
//            trafMode.setTextContent(inventoryHeads.get(i).getTrafMode());
//
//            InventoryHead.appendChild(guid);
//            InventoryHead.appendChild(appType);
//            InventoryHead.appendChild(appTime);
//            InventoryHead.appendChild(appStatus);
//            InventoryHead.appendChild(orderNo);
//            InventoryHead.appendChild(ebpCode);
//            InventoryHead.appendChild(ebpName);
//            InventoryHead.appendChild(ebcCode);
//            InventoryHead.appendChild(ebcName);
//            InventoryHead.appendChild(logisticsNo);
//            InventoryHead.appendChild(logisticsCode);
//            InventoryHead.appendChild(logisticsName);
//            InventoryHead.appendChild(copNo);
//            InventoryHead.appendChild(preNo);
//            InventoryHead.appendChild(assureCode);
//            InventoryHead.appendChild(emsNo);
//            InventoryHead.appendChild(invtNo);
//            InventoryHead.appendChild(ieFlag);
//            InventoryHead.appendChild(declTime);
//            InventoryHead.appendChild(customsCode);
//            InventoryHead.appendChild(portCode);
//            InventoryHead.appendChild(ieDate);
//            InventoryHead.appendChild(buyerIdType);
//            InventoryHead.appendChild(buyerIdNumber);
//            InventoryHead.appendChild(buyerName);
//            InventoryHead.appendChild(buyerTelephone);
//            InventoryHead.appendChild(consigneeAddress);
//            InventoryHead.appendChild(agentCode);
//            InventoryHead.appendChild(agentName);
//            InventoryHead.appendChild(areaCode);
//            InventoryHead.appendChild(areaName);
//            InventoryHead.appendChild(tradeMode);
//            InventoryHead.appendChild(trafMode);
//            InventoryHead.appendChild(trafNo);
//            InventoryHead.appendChild(voyageNo);
//            InventoryHead.appendChild(billNo);
//            InventoryHead.appendChild(loctNo);
//            InventoryHead.appendChild(licenseNo);
//            InventoryHead.appendChild(country);
//            InventoryHead.appendChild(freight);
//            InventoryHead.appendChild(insuredFee);
//            InventoryHead.appendChild(currency);
//            InventoryHead.appendChild(packNo);
//            InventoryHead.appendChild(grossWeight);
//            InventoryHead.appendChild(netWeight);
//            InventoryHead.appendChild(note);
//
//            String headGuid = inventoryHeads.getGuid();
//
//            Inventory.appendChild(InventoryHead);
//            this.getEntryList(document, ceb711Message, headGuid, Inventory);
//
//            rootElement.appendChild(Inventory);
//
//    }
//
//    /**
//     * 构建EntryList 节点
//     */
//    public void getEntryList(Document document, CEB711Message ceb711Message, String headGuid, Element Inventory) {
//        //未写
//        List<ImpDeliveryBody> impDeliveryBodyList = ceb711Message.getImpDeliveryBodyList();
//
//        Element InventoryList;
//        Element gnum;
//        Element itemRecordNo;
//        Element itemNo;
//        Element itemName;
//        Element gcode;
//        Element gname;
//        Element gmodel;
//        Element barCode;
//        Element country;
//        Element currency;
//        Element qty;
//        Element unit;
//        Element qty1;
//        Element unit1;
//        Element qty2;
//        Element unit2;
//        Element price;
//        Element totalPrice;
//        Element note;
//
//        for (int i = 0; i < impDeliveryBodyList.size(); i++) {
//
//                InventoryList = document.createElement("ceb:InventoryList");
//
//                gnum = document.createElement("ceb:gnum");
//                gnum.setTextContent(String.valueOf(inventoryBodyList.get(i).getG_num()));
//
//                itemRecordNo = document.createElement("ceb:itemRecordNo");
//                itemRecordNo.setTextContent(inventoryBodyList.get(i).getItem_record_no());
//
//                itemNo = document.createElement("ceb:itemNo");
//                itemNo.setTextContent(inventoryBodyList.get(i).getItem_no());
//
//                itemName = document.createElement("ceb:itemName");
//                itemName.setTextContent(inventoryBodyList.get(i).getItem_name());
//
//                gcode = document.createElement("ceb:gcode");
//                gcode.setTextContent(inventoryBodyList.get(i).getG_code());
//
//                gname = document.createElement("ceb:gname");
//                gname.setTextContent(inventoryBodyList.get(i).getG_name());
//
//                gmodel = document.createElement("ceb:gmodel");
//                gmodel.setTextContent(inventoryBodyList.get(i).getG_model());
//
//                barCode = document.createElement("ceb:barCode");
//                barCode.setTextContent(inventoryBodyList.get(i).getBar_code());
//
//                country = document.createElement("ceb:country");
//                country.setTextContent(inventoryBodyList.get(i).getCountry());
//
//                currency = document.createElement("ceb:currency");
//                currency.setTextContent(inventoryBodyList.get(i).getCurrency());
//
//                qty = document.createElement("ceb:qty");
//                qty.setTextContent(inventoryBodyList.get(i).getQty());
//
//                unit = document.createElement("ceb:unit");
//                unit.setTextContent(inventoryBodyList.get(i).getUnit());
//
//                qty1 = document.createElement("ceb:qty1");
//                qty1.setTextContent(inventoryBodyList.get(i).getQty1());
//
//                unit1 = document.createElement("ceb:unit1");
//                unit1.setTextContent(inventoryBodyList.get(i).getUnit1());
//
//                price = document.createElement("ceb:price");
//                price.setTextContent(inventoryBodyList.get(i).getPrice());
//
//                totalPrice = document.createElement("ceb:totalPrice");
//                totalPrice.setTextContent(inventoryBodyList.get(i).getTotal_price());
//
//                note = document.createElement("ceb:note");
//                note.setTextContent(inventoryBodyList.get(i).getNote());
//
//                InventoryList.appendChild(gnum);
//                InventoryList.appendChild(itemRecordNo);
//                InventoryList.appendChild(itemNo);
//                InventoryList.appendChild(itemName);
//                InventoryList.appendChild(gcode);
//                InventoryList.appendChild(gname);
//                InventoryList.appendChild(gmodel);
//                InventoryList.appendChild(barCode);
//                InventoryList.appendChild(country);
//                InventoryList.appendChild(currency);
//                InventoryList.appendChild(qty);
//                InventoryList.appendChild(unit);
//                InventoryList.appendChild(qty1);
//                InventoryList.appendChild(unit1);
//                InventoryList.appendChild(price);
//                InventoryList.appendChild(totalPrice);
//
//                Inventory.appendChild(InventoryList);
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
