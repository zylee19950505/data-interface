package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.headings.ExcelHeadBondInven;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

public class ExcelDataBondInven implements ExcelData {
    private Log log = LogFactory.getLog(this.getClass());

    private int orderNoIndex; //"订单编号";//head //list
    private int logisticsNoIndex; //"物流运单编号";//head
    private int logisticsCodeIndex; //"物流企业代码";//head
    private int logisticsNameIndex; //"物流企业名称";//head
    private int assureCodeIndex; //"担保企业编号";//head
    private int portCodeIndex; //"口岸海关代码";//head
    private int customsCodeIndex; //"申报海关代码";//head
    private int areaCodeIndex; //"电商平台代码";//head
    private int areaNameIndex; //"电商平台名称";//head
    private int grossWeightIndex; //"毛重";//head
    private int netWeightIndex; //"净重";//head
    private int buyerIdNumberIndex; //"订购人证件号码";//head
    private int buyerNameIndex; //"订购人姓名";//head
    private int buyerTelephoneIndex; //"订购人电话";//head
    private int consigneeAddressIndex; //"收件地址";//head
    private int trafModeIndex; //"运输方式";//head
    private int freightIndex; //"运费";//head
    private int insuredFeeIndex; //"保费";//head
    private int wrapTypeIndex; //包装种类 //head

    private int itemNoIndex; //"商品料号";//list
    private int gCodeIndex; //"商品编码";//list
    private int gNameIndex; //"商品名称";//list
    private int gModelIndex; //"商品规格型号";//list
    private int unitIndex; //"计量单位";//list
    private int qtyIndex; //"数量";//list
    private int unit1Index; //"第一法定计量单位";//list
    private int qty1Index; //"第一法定数量";//list
    private int unit2Index; //"第二法定计量单位";//list
    private int qty2Index; //"第二法定数量";//list
    private int totalPriceIndex; //"总价";//list
    private int originCountryIndex; //"原产国";//list
    private int noteIndex; //"备注";//list


    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<ImpInventoryBody> impInventoryBodyList = new ArrayList<>();
        Map<String, List<String>> impInventoryHeadMap = new LinkedHashMap<>();
        ImpInventoryBody impInventoryBody;
        this.getIndexValue(excelData.get(0));//初始化表头索引
        for (int i = 1, length = excelData.size(); i < length; i++) {
            impInventoryBody = new ImpInventoryBody();
            impInventoryHeadMap = this.getMergeData(excelData.get(i), impInventoryHeadMap);
            impInventoryBodyList = this.impInventoryBodyData(excelData.get(i), impInventoryBody, impInventoryBodyList);
        }

        List<ImpInventoryHead> impInventoryHeadList = this.getInventoryHeadData(impInventoryHeadMap);
        map.put("ImpInventoryHead", impInventoryHeadList);
        map.put("ImpInventoryBody", impInventoryBodyList);
        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 封装ImpOrderHead方法
     */
    public List<ImpInventoryHead> getInventoryHeadData(Map<String, List<String>> data) {
        List<ImpInventoryHead> listData = new ArrayList<>();
        ImpInventoryHead impInventoryHead;
        DecimalFormat df = new DecimalFormat("0.00");
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            impInventoryHead = new ImpInventoryHead();
            List<String> value = entry.getValue();

            impInventoryHead.setOrder_no(value.get(orderNoIndex));//交易平台的订单编号
            impInventoryHead.setLogistics_no(value.get(logisticsNoIndex));//物流企业的运单包裹面单号
            impInventoryHead.setLogistics_code(value.get(logisticsCodeIndex));//物流企业的海关注册登记编号。
            impInventoryHead.setLogistics_name(value.get(logisticsNameIndex));//物流企业在海关注册登记的名称。
            impInventoryHead.setAssure_code(value.get(assureCodeIndex));//担保扣税的企业海关注册登记编号
            impInventoryHead.setPort_code(value.get(portCodeIndex));//商品实际进出我国关境口岸海关的关区代码
            impInventoryHead.setCustoms_code(value.get(customsCodeIndex));//接受清单申报的海关关区代码
            impInventoryHead.setArea_code(value.get(areaCodeIndex));//保税模式必填，区内仓储企业的海关注册登记编号。
            impInventoryHead.setArea_name(value.get(areaNameIndex));//保税模式必填，区内仓储企业在海关注册登记的名称
            impInventoryHead.setGross_weight(getDouble(value.get(grossWeightIndex)));//货物及其包装材料的重量之和
            impInventoryHead.setNet_weight(getDouble(value.get(netWeightIndex)));//货物的毛重减去外包装材料后的重量

            impInventoryHead.setBuyer_id_number(value.get(buyerIdNumberIndex));//订购人的身份证件号码。
            impInventoryHead.setBuyer_name(value.get(buyerNameIndex));//订购人的真实姓名。
            impInventoryHead.setBuyer_telephone(value.get(buyerTelephoneIndex));//订购人电话。
            impInventoryHead.setConsignee_address(value.get(consigneeAddressIndex));//收货地址
            impInventoryHead.setTraf_mode(value.get(trafModeIndex));//填写海关标准的参数代码
            impInventoryHead.setFreight(getDouble(value.get(freightIndex)));//运杂费
            impInventoryHead.setInsured_fee(getDouble(value.get(insuredFeeIndex)));//物流企业实际收取的商品保价费用。
            impInventoryHead.setWrap_type(value.get(wrapTypeIndex));//包装种类
            impInventoryHead.setTotal_prices(value.get(totalPriceIndex));//取表体商品总价之和

            listData.add(impInventoryHead);
        }
        return listData;
    }

    /**
     * 封装ImpOrderBody数据
     *
     * @param inventoryBodies
     * @param impInventoryBody
     * @param impInventoryBodyData
     * @return
     */
    public List<ImpInventoryBody> impInventoryBodyData(List<String> inventoryBodies, ImpInventoryBody impInventoryBody, List<ImpInventoryBody> impInventoryBodyData) {
        DecimalFormat df = new DecimalFormat("0.00");
        impInventoryBody.setOrder_no(inventoryBodies.get(orderNoIndex));//清单编号

        impInventoryBody.setItem_no(inventoryBodies.get(itemNoIndex));//账册备案料号: 保税进口必填
        impInventoryBody.setG_code(inventoryBodies.get(gCodeIndex));//商品编码
        impInventoryBody.setG_name(inventoryBodies.get(gNameIndex));//商品名称
        impInventoryBody.setG_model(inventoryBodies.get(gModelIndex));//商品规格型号

        impInventoryBody.setQuantity(Double.parseDouble(inventoryBodies.get(qtyIndex)));

        impInventoryBody.setUnit(inventoryBodies.get(unitIndex));//计量单位
        impInventoryBody.setQty(getDouble(inventoryBodies.get(qtyIndex)));//商品实际数量
        impInventoryBody.setUnit1(inventoryBodies.get(unit1Index));//第一计量单位
        impInventoryBody.setQty1(getDouble(inventoryBodies.get(qty1Index)));//第一法定数量
        impInventoryBody.setUnit2(inventoryBodies.get(unit2Index));//第二计量单位
        impInventoryBody.setQty2(getDouble(inventoryBodies.get(qty2Index)));//第二法定数量

        impInventoryBody.setTotal_price(getDouble(inventoryBodies.get(totalPriceIndex)));//总价
        impInventoryBody.setCountry(inventoryBodies.get(originCountryIndex));//原产国（地区)
        impInventoryBody.setNote(inventoryBodies.get(noteIndex));//促销活动，商品单价偏离市场价格的，可以在此说明。

        double Price = Double.parseDouble(impInventoryBody.getTotal_price()) / Double.parseDouble(impInventoryBody.getQty());
        if (!StringUtils.isEmpty(Price)) {
            String price = df.format(Price);
            impInventoryBody.setPrice(price);//单价
        }
        impInventoryBodyData.add(impInventoryBody);
        return impInventoryBodyData;
    }

    /**
     * 初始化索引值
     *
     * @param bondInvenLists
     */
    public void getIndexValue(List<String> bondInvenLists) {

        orderNoIndex = bondInvenLists.indexOf(ExcelHeadBondInven.orderNo);
        logisticsNoIndex = bondInvenLists.indexOf(ExcelHeadBondInven.logisticsNo);
        logisticsCodeIndex = bondInvenLists.indexOf(ExcelHeadBondInven.logisticsCode);
        logisticsNameIndex = bondInvenLists.indexOf(ExcelHeadBondInven.logisticsName);
        assureCodeIndex = bondInvenLists.indexOf(ExcelHeadBondInven.assureCode);
        portCodeIndex = bondInvenLists.indexOf(ExcelHeadBondInven.portCode);
        customsCodeIndex = bondInvenLists.indexOf(ExcelHeadBondInven.customsCode);
        areaCodeIndex = bondInvenLists.indexOf(ExcelHeadBondInven.areaCode);
        areaNameIndex = bondInvenLists.indexOf(ExcelHeadBondInven.areaName);
        grossWeightIndex = bondInvenLists.indexOf(ExcelHeadBondInven.grossWeight);
        netWeightIndex = bondInvenLists.indexOf(ExcelHeadBondInven.netWeight);
        buyerIdNumberIndex = bondInvenLists.indexOf(ExcelHeadBondInven.buyerIdNumber);
        buyerNameIndex = bondInvenLists.indexOf(ExcelHeadBondInven.buyerName);
        buyerTelephoneIndex = bondInvenLists.indexOf(ExcelHeadBondInven.buyerTelephone);
        consigneeAddressIndex = bondInvenLists.indexOf(ExcelHeadBondInven.consigneeAddress);
        trafModeIndex = bondInvenLists.indexOf(ExcelHeadBondInven.trafMode);
        freightIndex = bondInvenLists.indexOf(ExcelHeadBondInven.freight);
        insuredFeeIndex = bondInvenLists.indexOf(ExcelHeadBondInven.insuredFee);
        wrapTypeIndex = bondInvenLists.indexOf(ExcelHeadBondInven.wrapType);

        itemNoIndex = bondInvenLists.indexOf(ExcelHeadBondInven.itemNo);
        gCodeIndex = bondInvenLists.indexOf(ExcelHeadBondInven.gCode);
        gNameIndex = bondInvenLists.indexOf(ExcelHeadBondInven.gName);
        gModelIndex = bondInvenLists.indexOf(ExcelHeadBondInven.gModel);
        unitIndex = bondInvenLists.indexOf(ExcelHeadBondInven.unit);
        qtyIndex = bondInvenLists.indexOf(ExcelHeadBondInven.qty);
        unit1Index = bondInvenLists.indexOf(ExcelHeadBondInven.unit1);
        qty1Index = bondInvenLists.indexOf(ExcelHeadBondInven.qty1);
        unit2Index = bondInvenLists.indexOf(ExcelHeadBondInven.unit2);
        qty2Index = bondInvenLists.indexOf(ExcelHeadBondInven.qty2);
        totalPriceIndex = bondInvenLists.indexOf(ExcelHeadBondInven.totalPrice);
        originCountryIndex = bondInvenLists.indexOf(ExcelHeadBondInven.originCountry);
        noteIndex = bondInvenLists.indexOf(ExcelHeadBondInven.note);
    }

    /**
     * 合并excel中属于EntryHead的数据
     *
     * @param impInventoryHeadLists
     * @param impInventoryHeadMap
     * @return
     */
    public Map<String, List<String>> getMergeData(List<String> impInventoryHeadLists, Map<String, List<String>> impInventoryHeadMap) throws Exception {
        String orderNo = impInventoryHeadLists.get(orderNoIndex);
        DecimalFormat df = new DecimalFormat("0.00");
        if (impInventoryHeadMap.containsKey(orderNo)) {
            List<String> list = impInventoryHeadMap.get(orderNo);//存放每次合并的结果
            String allTotalPrice = list.get(totalPriceIndex);
            String addTotalPrice = impInventoryHeadLists.get(totalPriceIndex);
            if (StringUtils.isEmpty(allTotalPrice)) {
                allTotalPrice = "0";
            }
            if (StringUtils.isEmpty(addTotalPrice)) {
                addTotalPrice = "0";
            }
            double total_value_Total = Double.parseDouble(allTotalPrice) + Double.parseDouble(addTotalPrice);//合并所有表体的总价
            list.set(totalPriceIndex, df.format(total_value_Total));//商品价格
            impInventoryHeadMap.put(orderNo, list);
        } else {
            String totalPrice = impInventoryHeadLists.get(totalPriceIndex);
            if (StringUtils.isEmpty(totalPrice)) {
                totalPrice = "0";
                impInventoryHeadLists.set(totalPriceIndex, totalPrice);
            }
            impInventoryHeadMap.put(orderNo, impInventoryHeadLists);
        }
        return impInventoryHeadMap;
    }

    protected String getString(String str) {
        if (!StringUtils.isEmpty(str)) {
            return str;
        } else {
            return "";
        }

    }

    protected String getDouble(String str) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (!StringUtils.isEmpty(str)) {
            return df.format(Double.parseDouble(str));
        } else {
            return "0";
        }
    }

}
