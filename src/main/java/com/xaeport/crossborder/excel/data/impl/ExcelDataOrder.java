package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.data.entity.ImpOrderGoodsList;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.headings.ExcelHeadOrder;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

/**
 * 订单模板数据
 * Created by lzy on 2018/06/28.
 */
public class ExcelDataOrder implements ExcelData {
    private Log log = LogFactory.getLog(this.getClass());
    private int orderNoIndex; //订单编号";//head //list
    private int logisticsNoIndex; //物流运单编号";//
    private int itemNameIndex; //商品名称";//list
    private int gcodeIndex; //商品编码";//
    private int gmodelIndex; //商品规格型号";//
    private int qtyIndex; //数量";//list
    private int unitIndex; //计量单位";//list
    private int qty1Index; //第一法定数量";//
    private int unit1Index; //第一法定单位";//
    private int qty2Index; //第二法定数量";//
    private int unit2Index; //第二法定单位";//
    private int total_PriceIndex; //总价";//list
    private int ebp_CodeIndex; //电商平台代码";//
    private int ebp_NameIndex; //电商平台名称";//
    private int assureCodeIndex; //担保企业编号";//
    private int declTimeIndex; //申报日期";//
    private int customsCodeIndex; //申报海关代码";//
    private int portCodeIndex; //口岸海关代码";//enterprise
    private int ieDateIndex; //进口日期";//
    private int buyer_Reg_NoIndex; //订购人注册号";//head
    private int buyer_Id_TypeIndex; //订购人证件类型";//head
    private int buyer_Id_NumberIndex; //订购人证件号码";//head
    private int buyer_NameIndex; //订购人姓名";//head
    private int buyerTelephoneIndex; //订购人电话";//
    private int goodsValueIndex; //商品价格";//head
    private int discountIndex; //非现金抵扣金额";//head
    private int tax_TotalIndex; //代扣税款";//head
    private int agentCodeIndex; //申报企业代码";//enterprise
    private int agentNameIndex; //申报企业名称";//enterprise
    private int trafModeIndex; //运输方式";
    private int originCountryIndex; //原产国";//
    private int startCountryIndex; //起运国";//
    private int noteIndex; //备注";//head


    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<ImpOrderGoodsList> entryListData = new ArrayList<>();
        Map<String, List<String>> eIntlMailMap = new LinkedHashMap<>();

        ImpOrderGoodsList impOrderGoodsList;

        this.getIndexValue(excelData.get(0));//初始化表头索引
        for (int i = 1, length = excelData.size(); i < length; i++) {
            impOrderGoodsList = new ImpOrderGoodsList();
//            entryHeadMap = this.getMergeData(excelData.get(i), entryHeadMap, temporary);
            entryListData = this.impOrderGoodsData(excelData.get(i), impOrderGoodsList, entryListData);
        }

        List<ImpOrderHead> orderList = this.getEIntlMailData(excelData);
        map.put("ImpOrderHead", orderList);
        map.put("ImpOrderList", entryListData);
        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 封装ImpOrderHead方法
     */
    public List<ImpOrderHead> getEIntlMailData(List<List<String>> excelData) {
        List<ImpOrderHead> listData = new ArrayList<>();
        ImpOrderHead impOrderHead;
        DecimalFormat df = new DecimalFormat("0.00000");
        for (int i = 1; i < excelData.size(); i++) {
            impOrderHead = new ImpOrderHead();
            impOrderHead.setOrder_No(excelData.get(i).get(orderNoIndex));//订单编号
            impOrderHead.setEbp_Code(excelData.get(i).get(ebp_CodeIndex));//电商平台代码
            impOrderHead.setEbp_Name(excelData.get(i).get(ebp_NameIndex));//电商平台名称
            impOrderHead.setBuyer_Reg_No(excelData.get(i).get(buyer_Reg_NoIndex));//订购人注册号
            impOrderHead.setBuyer_Id_Type(excelData.get(i).get(buyer_Id_TypeIndex));//订购人证件类型
            impOrderHead.setBuyer_Id_Number(excelData.get(i).get(buyer_Id_NumberIndex));//订购人证件号码
            impOrderHead.setBuyer_Name(excelData.get(i).get(buyer_NameIndex));//订购人姓名
            impOrderHead.setNote(excelData.get(i).get(noteIndex));//备注

            String goodsValue = excelData.get(i).get(goodsValueIndex);
            if (!StringUtils.isEmpty(goodsValue)) {
                goodsValue = df.format(Double.parseDouble(goodsValue));
                impOrderHead.setGoods_Value(goodsValue);//商品价格
            }

//            String freight = excelData.get(i).get();
//            if (!StringUtils.isEmpty(freight)) {
//                freight = df.format(Double.parseDouble(freight));
//                impOrderHead.setGoods_Value(freight);//运杂费
//            }

            String discount = excelData.get(i).get(discountIndex);
            if (!StringUtils.isEmpty(discount)) {
                discount = df.format(Double.parseDouble(discount));
                impOrderHead.setDiscount(discount);//非现金抵扣金额
            }

            String tax_Total = excelData.get(i).get(tax_TotalIndex);
            if (!StringUtils.isEmpty(tax_Total)) {
                tax_Total = df.format(Double.parseDouble(tax_Total));
                impOrderHead.setTax_Total(tax_Total);//代扣税款
            }

//            double actural_Paid = Double.parseDouble(goodsValue) + Double.parseDouble(freight) + Double.parseDouble(tax_Total) - Double.parseDouble(discount);
//            if(!StringUtils.isEmpty(actural_Paid)){
//                String acturalPaid = df.format(actural_Paid);
//                impOrderHead.setActural_Paid(acturalPaid);
//            }

            listData.add(impOrderHead);
        }
        return listData;
    }

    /**
     * 封装ImpOrderGoodsList数据
     *
     * @param entryLists
     * @param impOrderGoodsList
     * @param impOrderGoodsListData
     * @return
     */
    public List<ImpOrderGoodsList> impOrderGoodsData(List<String> entryLists, ImpOrderGoodsList impOrderGoodsList, List<ImpOrderGoodsList> impOrderGoodsListData) {
        DecimalFormat df = new DecimalFormat("0.00000");
        impOrderGoodsList.setOrder_No(entryLists.get(orderNoIndex));//订单编号
        impOrderGoodsList.setItem_Name(entryLists.get(itemNameIndex));//商品名称
        impOrderGoodsList.setCountry(entryLists.get(originCountryIndex));//原产国

        String qty = entryLists.get(qtyIndex);
        if (!StringUtils.isEmpty(qty)) {
            qty = df.format(Double.parseDouble(qty));
            impOrderGoodsList.setQty(qty);//数量
        }

        String unit = entryLists.get(unitIndex);
        if (!StringUtils.isEmpty(unit)) {
            unit = df.format(Double.parseDouble(unit));
            impOrderGoodsList.setUnit(unit);//计量单位
        }

        String total_Price = entryLists.get(total_PriceIndex);
        if (!StringUtils.isEmpty(total_Price)) {
            total_Price = df.format(Double.parseDouble(total_Price));
            impOrderGoodsList.setTotal_Price(total_Price);//总价
        }

        double Price = Double.parseDouble(total_Price)/Double.parseDouble(qty);
        if(!StringUtils.isEmpty(Price)){
            String price = df.format(Price);
            impOrderGoodsList.setPrice(price);//单价
        }

        impOrderGoodsListData.add(impOrderGoodsList);
        return impOrderGoodsListData;
    }

    /**
     * 初始化索引值
     *
     * @param orderLists
     */
    public void getIndexValue(List<String> orderLists) {
        orderNoIndex = orderLists.indexOf(ExcelHeadOrder.orderNo);//订单编号
        logisticsNoIndex = orderLists.indexOf(ExcelHeadOrder.logisticsNo);//物流运单编号
        itemNameIndex = orderLists.indexOf(ExcelHeadOrder.itemName);//商品名称
        gcodeIndex = orderLists.indexOf(ExcelHeadOrder.gcode);//商品编码
        gmodelIndex = orderLists.indexOf(ExcelHeadOrder.gmodel);//商品规格型号
        qtyIndex = orderLists.indexOf(ExcelHeadOrder.qty);//数量
        unitIndex = orderLists.indexOf(ExcelHeadOrder.unit);//计量单位
        qty1Index = orderLists.indexOf(ExcelHeadOrder.qty1);//第一法定数量
        unit1Index = orderLists.indexOf(ExcelHeadOrder.unit1);//第一法定单位
        qty2Index = orderLists.indexOf(ExcelHeadOrder.qty2);//第二法定数量
        unit2Index = orderLists.indexOf(ExcelHeadOrder.unit2);//第二法定单位
        total_PriceIndex = orderLists.indexOf(ExcelHeadOrder.total_Price);//总价
        ebp_CodeIndex = orderLists.indexOf(ExcelHeadOrder.ebp_Code);//电商平台代码
        ebp_NameIndex = orderLists.indexOf(ExcelHeadOrder.ebp_Name);//电商平台名称
        assureCodeIndex = orderLists.indexOf(ExcelHeadOrder.assureCode);//担保企业编号
        declTimeIndex = orderLists.indexOf(ExcelHeadOrder.declTime);//申报日期
        customsCodeIndex = orderLists.indexOf(ExcelHeadOrder.customsCode);//申报海关代码
        portCodeIndex = orderLists.indexOf(ExcelHeadOrder.portCode);//口岸海关代码
        ieDateIndex = orderLists.indexOf(ExcelHeadOrder.ieDate);//进口日期
        buyer_Reg_NoIndex = orderLists.indexOf(ExcelHeadOrder.buyer_Reg_No);//订购人注册号
        buyer_Id_TypeIndex = orderLists.indexOf(ExcelHeadOrder.buyer_Id_Type);//订购人证件类型
        buyer_Id_NumberIndex = orderLists.indexOf(ExcelHeadOrder.buyer_Id_Number);//订购人证件号码
        buyer_NameIndex = orderLists.indexOf(ExcelHeadOrder.buyer_Name);//订购人姓名
        buyerTelephoneIndex = orderLists.indexOf(ExcelHeadOrder.buyerTelephone);//订购人电话
        goodsValueIndex = orderLists.indexOf(ExcelHeadOrder.goodsValue);//商品价格
        discountIndex = orderLists.indexOf(ExcelHeadOrder.discount);//非现金抵扣金额
        tax_TotalIndex = orderLists.indexOf(ExcelHeadOrder.tax_Total);//代扣税款
        agentCodeIndex = orderLists.indexOf(ExcelHeadOrder.agentCode);//申报企业代码
        agentNameIndex = orderLists.indexOf(ExcelHeadOrder.agentName);//申报企业名称
        trafModeIndex = orderLists.indexOf(ExcelHeadOrder.trafMode);//运输方式
        originCountryIndex = orderLists.indexOf(ExcelHeadOrder.originCountry);//原产国
        startCountryIndex = orderLists.indexOf(ExcelHeadOrder.startCountry);//起运国
        noteIndex = orderLists.indexOf(ExcelHeadOrder.note);//备注
    }


}
