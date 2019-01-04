package com.xaeport.crossborder.convert.bondinven;


import com.xaeport.crossborder.data.entity.CEB621Message;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.InventoryHead;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * 进口保税清单报文生成
 */
@Component
public class BondInvenXML {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 构建报文表头节点
     */
    public void getBondInvenHead(Document document, CEB621Message ceb621Message, Element rootElement) {

        List<InventoryHead> inventoryHeads = ceb621Message.getInventoryHeadList();

        Element Inventory;
        Element InventoryHead;
        Element guid;
        Element appType;
        Element appTime;
        Element appStatus;
        Element orderNo;
        Element ebpCode;
        Element ebpName;
        Element ebcCode;
        Element ebcName;
        Element logisticsNo;
        Element logisticsCode;
        Element logisticsName;
        Element copNo;
        Element preNo;
        Element assureCode;
        Element emsNo;
        Element invtNo;
        Element ieFlag;
        Element declTime;
        Element customsCode;
        Element portCode;
        Element ieDate;
        Element buyerIdType;
        Element buyerIdNumber;
        Element buyerName;
        Element buyerTelephone;
        Element consigneeAddress;
        Element agentCode;
        Element agentName;
        Element areaCode;
        Element areaName;
        Element tradeMode;
        Element trafMode;
        Element trafNo;
        Element voyageNo;
        Element billNo;
        Element loctNo;
        Element licenseNo;
        Element country;
        Element freight;
        Element insuredFee;
        Element currency;
        Element wrapType;
        Element packNo;
        Element grossWeight;
        Element netWeight;
        Element note;

        for (int i = 0; i < inventoryHeads.size(); i++) {
            Inventory = document.createElement("ceb:Inventory");
            InventoryHead = document.createElement("ceb:InventoryHead");

            guid = document.createElement("ceb:guid");
            guid.setTextContent(inventoryHeads.get(i).getGuid());

            appType = document.createElement("ceb:appType");
            appType.setTextContent(inventoryHeads.get(i).getAppType());

            appTime = document.createElement("ceb:appTime");
            appTime.setTextContent(inventoryHeads.get(i).getAppTime());

            appStatus = document.createElement("ceb:appStatus");
            appStatus.setTextContent(inventoryHeads.get(i).getAppStatus());

            orderNo = document.createElement("ceb:orderNo");
            orderNo.setTextContent(inventoryHeads.get(i).getOrderNo());

            ebpCode = document.createElement("ceb:ebpCode");
            ebpCode.setTextContent(inventoryHeads.get(i).getEbpCode());

            ebpName = document.createElement("ceb:ebpName");
            ebpName.setTextContent(inventoryHeads.get(i).getEbpName());

            ebcCode = document.createElement("ceb:ebcCode");
            ebcCode.setTextContent(inventoryHeads.get(i).getEbcCode());

            ebcName = document.createElement("ceb:ebcName");
            ebcName.setTextContent(inventoryHeads.get(i).getEbcName());

            logisticsNo = document.createElement("ceb:logisticsNo");
            logisticsNo.setTextContent(inventoryHeads.get(i).getLogisticsNo());

            logisticsCode = document.createElement("ceb:logisticsCode");
            logisticsCode.setTextContent(inventoryHeads.get(i).getLogisticsCode());

            logisticsName = document.createElement("ceb:logisticsName");
            logisticsName.setTextContent(inventoryHeads.get(i).getLogisticsName());

            copNo = document.createElement("ceb:copNo");
            copNo.setTextContent(inventoryHeads.get(i).getCopNo());

            preNo = document.createElement("ceb:preNo");
            preNo.setTextContent(inventoryHeads.get(i).getPreNo());

            assureCode = document.createElement("ceb:assureCode");
            assureCode.setTextContent(inventoryHeads.get(i).getAssureCode());

            emsNo = document.createElement("ceb:emsNo");
            emsNo.setTextContent(inventoryHeads.get(i).getEmsNo());

            invtNo = document.createElement("ceb:invtNo");
            invtNo.setTextContent(inventoryHeads.get(i).getInvtNo());

            ieFlag = document.createElement("ceb:ieFlag");
            ieFlag.setTextContent(inventoryHeads.get(i).getIeFlag());

            declTime = document.createElement("ceb:declTime");
            declTime.setTextContent(inventoryHeads.get(i).getDeclTime());

            customsCode = document.createElement("ceb:customsCode");
            customsCode.setTextContent(inventoryHeads.get(i).getCustomsCode());

            portCode = document.createElement("ceb:portCode");
            portCode.setTextContent(inventoryHeads.get(i).getPortCode());

            ieDate = document.createElement("ceb:ieDate");
            ieDate.setTextContent(inventoryHeads.get(i).getIeDate());

            buyerIdType = document.createElement("ceb:buyerIdType");
            buyerIdType.setTextContent(inventoryHeads.get(i).getBuyerIdType());

            buyerIdNumber = document.createElement("ceb:buyerIdNumber");
            buyerIdNumber.setTextContent(inventoryHeads.get(i).getBuyerIdNumber());

            buyerName = document.createElement("ceb:buyerName");
            buyerName.setTextContent(inventoryHeads.get(i).getBuyerName());

            buyerTelephone = document.createElement("ceb:buyerTelephone");
            buyerTelephone.setTextContent(inventoryHeads.get(i).getBuyerTelephone());

            consigneeAddress = document.createElement("ceb:consigneeAddress");
            consigneeAddress.setTextContent(inventoryHeads.get(i).getConsigneeAddress());

            agentCode = document.createElement("ceb:agentCode");
            agentCode.setTextContent(inventoryHeads.get(i).getAgentCode());

            agentName = document.createElement("ceb:agentName");
            agentName.setTextContent(inventoryHeads.get(i).getAgentName());

            areaCode = document.createElement("ceb:areaCode");
            areaCode.setTextContent(inventoryHeads.get(i).getAreaCode());

            areaName = document.createElement("ceb:areaName");
            areaName.setTextContent(inventoryHeads.get(i).getAgentName());

            tradeMode = document.createElement("ceb:tradeMode");
            tradeMode.setTextContent(inventoryHeads.get(i).getTradeMode());

            trafMode = document.createElement("ceb:trafMode");
            trafMode.setTextContent(inventoryHeads.get(i).getTrafMode());

            trafNo = document.createElement("ceb:trafNo");
            trafNo.setTextContent(inventoryHeads.get(i).getTrafNo());

            voyageNo = document.createElement("ceb:voyageNo");
            voyageNo.setTextContent(inventoryHeads.get(i).getVoyageNo());

            billNo = document.createElement("ceb:billNo");
            billNo.setTextContent(inventoryHeads.get(i).getBillNo());

            loctNo = document.createElement("ceb:loctNo");
            loctNo.setTextContent(inventoryHeads.get(i).getLoctNo());

            licenseNo = document.createElement("ceb:licenseNo");
            licenseNo.setTextContent(inventoryHeads.get(i).getLicenseNo());

            country = document.createElement("ceb:country");
            country.setTextContent(inventoryHeads.get(i).getCountry());

            freight = document.createElement("ceb:freight");
            freight.setTextContent(inventoryHeads.get(i).getFreight());

            insuredFee = document.createElement("ceb:insuredFee");
            insuredFee.setTextContent(inventoryHeads.get(i).getInsuredFee());

            currency = document.createElement("ceb:currency");
            currency.setTextContent(inventoryHeads.get(i).getCurrency());

            packNo = document.createElement("ceb:packNo");
            packNo.setTextContent(inventoryHeads.get(i).getPackNo());

            grossWeight = document.createElement("ceb:grossWeight");
            grossWeight.setTextContent(inventoryHeads.get(i).getGrossWeight());

            netWeight = document.createElement("ceb:netWeight");
            netWeight.setTextContent(inventoryHeads.get(i).getNetWeight());

            note = document.createElement("ceb:note");
            note.setTextContent(inventoryHeads.get(i).getNote());

            InventoryHead.appendChild(guid);
            InventoryHead.appendChild(appType);
            InventoryHead.appendChild(appTime);
            InventoryHead.appendChild(appStatus);
            InventoryHead.appendChild(orderNo);
            InventoryHead.appendChild(ebpCode);
            InventoryHead.appendChild(ebpName);
            InventoryHead.appendChild(ebcCode);
            InventoryHead.appendChild(ebcName);
            InventoryHead.appendChild(logisticsNo);
            InventoryHead.appendChild(logisticsCode);
            InventoryHead.appendChild(logisticsName);
            InventoryHead.appendChild(copNo);
            InventoryHead.appendChild(preNo);
            InventoryHead.appendChild(assureCode);
            InventoryHead.appendChild(emsNo);
            InventoryHead.appendChild(invtNo);
            InventoryHead.appendChild(ieFlag);
            InventoryHead.appendChild(declTime);
            InventoryHead.appendChild(customsCode);
            InventoryHead.appendChild(portCode);
            InventoryHead.appendChild(ieDate);
            InventoryHead.appendChild(buyerIdType);
            InventoryHead.appendChild(buyerIdNumber);
            InventoryHead.appendChild(buyerName);
            InventoryHead.appendChild(buyerTelephone);
            InventoryHead.appendChild(consigneeAddress);
            InventoryHead.appendChild(agentCode);
            InventoryHead.appendChild(agentName);
            InventoryHead.appendChild(areaCode);
            InventoryHead.appendChild(areaName);
            InventoryHead.appendChild(tradeMode);
            InventoryHead.appendChild(trafMode);
            InventoryHead.appendChild(trafNo);
            InventoryHead.appendChild(voyageNo);
            InventoryHead.appendChild(billNo);
            InventoryHead.appendChild(loctNo);
            InventoryHead.appendChild(licenseNo);
            InventoryHead.appendChild(country);
            InventoryHead.appendChild(freight);
            InventoryHead.appendChild(insuredFee);
            InventoryHead.appendChild(currency);
            if (!StringUtils.isEmpty(inventoryHeads.get(i).getWrapType())) {
                wrapType = document.createElement("ceb:wrapType");
                wrapType.setTextContent(inventoryHeads.get(i).getWrapType());
                InventoryHead.appendChild(wrapType);
            }
            InventoryHead.appendChild(packNo);
            InventoryHead.appendChild(grossWeight);
            InventoryHead.appendChild(netWeight);
            InventoryHead.appendChild(note);

            String headGuid = inventoryHeads.get(i).getGuid();

            Inventory.appendChild(InventoryHead);
            this.getBondInvenList(document, ceb621Message, headGuid, Inventory);

            rootElement.appendChild(Inventory);

        }
    }

    /**
     * 构建报文表体节点
     */
    public void getBondInvenList(Document document, CEB621Message ceb621Message, String headGuid, Element Inventory) {

        List<ImpInventoryBody> inventoryBodyList = ceb621Message.getImpInventoryBodyList();

        Element InventoryList;
        Element gnum;
        Element itemRecordNo;
        Element itemNo;
        Element itemName;
        Element gcode;
        Element gname;
        Element gmodel;
        Element barCode;
        Element country;
        Element currency;
        Element qty;
        Element unit;
        Element qty1;
        Element unit1;
        Element qty2;
        Element unit2;
        Element price;
        Element totalPrice;
        Element note;

        for (int i = 0; i < inventoryBodyList.size(); i++) {

            if ((inventoryBodyList.get(i).getHead_guid()).equals(headGuid)) {
                InventoryList = document.createElement("ceb:InventoryList");

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

                price = document.createElement("ceb:price");
                price.setTextContent(inventoryBodyList.get(i).getPrice());

                totalPrice = document.createElement("ceb:totalPrice");
                totalPrice.setTextContent(inventoryBodyList.get(i).getTotal_price());

                note = document.createElement("ceb:note");
                note.setTextContent(inventoryBodyList.get(i).getNote());

                InventoryList.appendChild(gnum);
                InventoryList.appendChild(itemRecordNo);
                InventoryList.appendChild(itemNo);
                InventoryList.appendChild(itemName);
                InventoryList.appendChild(gcode);
                InventoryList.appendChild(gname);
                InventoryList.appendChild(gmodel);
                InventoryList.appendChild(barCode);
                InventoryList.appendChild(country);
                InventoryList.appendChild(currency);
                InventoryList.appendChild(qty);
                InventoryList.appendChild(unit);
                InventoryList.appendChild(qty1);
                InventoryList.appendChild(unit1);
                if (!(inventoryBodyList.get(i).getQty2()).equals("0")) {
                    qty2 = document.createElement("ceb:qty2");
                    qty2.setTextContent(inventoryBodyList.get(i).getQty2());
                    InventoryList.appendChild(qty2);
                }
                if (!StringUtils.isEmpty(inventoryBodyList.get(i).getUnit2())) {
                    unit2 = document.createElement("ceb:unit2");
                    unit2.setTextContent(inventoryBodyList.get(i).getUnit2());
                    InventoryList.appendChild(unit2);
                }
                InventoryList.appendChild(price);
                InventoryList.appendChild(totalPrice);

                Inventory.appendChild(InventoryList);
            }
        }
    }


}
