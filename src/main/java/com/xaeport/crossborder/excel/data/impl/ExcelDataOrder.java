package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
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
    private int itemNameIndex; //商品名称";//list
    private int qtyIndex; //数量";//list
    private int unitIndex; //计量单位";//list
    private int total_PriceIndex; //总价";//list
    private int ebp_CodeIndex; //电商平台代码";//head
    private int ebp_NameIndex; //电商平台名称";//head
    private int ebc_CodeIndex; //电商企业代码";//head
    private int ebc_NameIndex; //电商企业名称";//head
    private int buyer_Reg_NoIndex; //订购人注册号";//head
    private int buyer_Id_NumberIndex; //订购人证件号码";//head
    private int buyer_NameIndex; //订购人姓名";//head
    private int consigneeIndex; //收货人姓名";//head
    private int consignee_TelephoneIndex; //收货人电话";//head
    private int consignee_AddressIndex; //收货地址";//head
    private int freightIndex; //运杂费";//head
    private int discountIndex; //非现金抵扣金额";//head
    private int tax_TotalIndex; //代扣税款";//head
    private int originCountryIndex; //原产国";//list
    private int noteIndex; //备注";//list


    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<ImpOrderBody> impOrderBodyList = new ArrayList<>();
        Map<String, List<String>> impOrderHeadMap = new LinkedHashMap<>();
        ImpOrderBody impOrderBody;
        Map<String, String> temporary = new HashMap<>();//用于存放临时计算值
        this.getIndexValue(excelData.get(0));//初始化表头索引
        for (int i = 1, length = excelData.size(); i < length; i++) {
            impOrderBody = new ImpOrderBody();
            impOrderHeadMap = this.getMergeData(excelData.get(i), impOrderHeadMap);
            impOrderBodyList = this.impOrderBodyData(excelData.get(i), impOrderBody, impOrderBodyList);
        }

        List<ImpOrderHead> impOrderHeadList = this.getOrderHeadData(impOrderHeadMap);
        map.put("ImpOrderHead", impOrderHeadList);
        map.put("ImpOrderBody", impOrderBodyList);
        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 封装ImpOrderHead方法
     */
    public List<ImpOrderHead> getOrderHeadData(Map<String, List<String>> data) {
        List<ImpOrderHead> listData = new ArrayList<>();
        ImpOrderHead impOrderHead;
        DecimalFormat df = new DecimalFormat("0.00000");
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            impOrderHead = new ImpOrderHead();
            List<String> value = entry.getValue();

            impOrderHead.setOrder_No(value.get(orderNoIndex));//订单编号
            impOrderHead.setEbp_Code(value.get(ebp_CodeIndex));//电商平台代码
            impOrderHead.setEbp_Name(value.get(ebp_NameIndex));//电商平台名称
            impOrderHead.setEbc_Code(value.get(ebc_CodeIndex));//电商企业代码
            impOrderHead.setEbc_Name(value.get(ebc_NameIndex));//电商企业名称
            impOrderHead.setBuyer_Reg_No(value.get(buyer_Reg_NoIndex));//订购人注册号
            impOrderHead.setBuyer_Id_Number(value.get(buyer_Id_NumberIndex));//订购人证件号码
            impOrderHead.setBuyer_Name(value.get(buyer_NameIndex));//订购人姓名
            impOrderHead.setConsignee(value.get(consigneeIndex));//收货人姓名
            impOrderHead.setConsignee_Telephone(value.get(consignee_TelephoneIndex));//收货人电话
            impOrderHead.setConsignee_Address(value.get(consignee_AddressIndex));//收件地址
//            impOrderHead.setNote(excelData.get(i).get(noteIndex));//备注

            String goodsValue = value.get(total_PriceIndex);
            if (!StringUtils.isEmpty(goodsValue)) {
                goodsValue = df.format(Double.parseDouble(goodsValue));
                impOrderHead.setGoods_Value(goodsValue);//商品价格
            }
            String freight = value.get(freightIndex);
            if (!StringUtils.isEmpty(freight)) {
                freight = df.format(Double.parseDouble(freight));
                impOrderHead.setFreight(freight);//运杂费
            }
            String discount = value.get(discountIndex);
            if (!StringUtils.isEmpty(discount)) {
                discount = df.format(Double.parseDouble(discount));
                impOrderHead.setDiscount(discount);//非现金抵扣金额
            }
            String tax_Total = value.get(tax_TotalIndex);
            if (!StringUtils.isEmpty(tax_Total)) {
                tax_Total = df.format(Double.parseDouble(tax_Total));
                impOrderHead.setTax_Total(tax_Total);//代扣税款
            }
            double actural_Paid = Double.parseDouble(goodsValue) + Double.parseDouble(freight) + Double.parseDouble(tax_Total) - Double.parseDouble(discount);
            // 实际支付金额 = 商品价格 + 运杂费 + 代扣税款 - 非现金抵扣金额，与支付凭证的支付金额一致。
            if(!StringUtils.isEmpty(actural_Paid)){
                String acturalPaid = df.format(actural_Paid);
                impOrderHead.setActural_Paid(acturalPaid);
            }

            listData.add(impOrderHead);
        }
        return listData;
    }

    /**
     * 合并excel中属于EntryHead的数据
     *
     * @param orderHeadLists
     * @param impOrderHeadMap
     * @return
     */
    public Map<String, List<String>> getMergeData(List<String> orderHeadLists, Map<String, List<String>> impOrderHeadMap) throws Exception {
        String orderNo = orderHeadLists.get(orderNoIndex);
        DecimalFormat df = new DecimalFormat("0.00000");
        if (impOrderHeadMap.containsKey(orderNo)) {
            List<String> list = impOrderHeadMap.get(orderNo);//存放每次合并的结果
            double total_value_Total = Double.parseDouble(list.get(total_PriceIndex)) + Double.parseDouble(orderHeadLists.get(total_PriceIndex));//合并所有表体的总价

            list.set(total_PriceIndex, df.format(total_value_Total));//商品价格
            impOrderHeadMap.put(orderNo, list);
        } else {
            impOrderHeadMap.put(orderNo, orderHeadLists);
        }
        return impOrderHeadMap;
    }

    /**
     * 封装ImpOrderGoodsList数据
     *
     * @param entryLists
     * @param impOrderBody
     * @param impOrderBodyData
     * @return
     */
    public List<ImpOrderBody> impOrderBodyData(List<String> entryLists, ImpOrderBody impOrderBody, List<ImpOrderBody> impOrderBodyData) {
        DecimalFormat df = new DecimalFormat("0.00000");
        impOrderBody.setOrder_No(entryLists.get(orderNoIndex));//订单编号
        impOrderBody.setItem_Name(entryLists.get(itemNameIndex));//商品名称
        impOrderBody.setCountry(entryLists.get(originCountryIndex));//原产国
        impOrderBody.setUnit(entryLists.get(unitIndex));//计量单位
        impOrderBody.setNote(entryLists.get(noteIndex));//备注

        String qty = entryLists.get(qtyIndex);
        if (!StringUtils.isEmpty(qty)) {
            qty = df.format(Double.parseDouble(qty));
            impOrderBody.setQty(qty);//数量
        }
        String total_Price = entryLists.get(total_PriceIndex);
        if (!StringUtils.isEmpty(total_Price)) {
            total_Price = df.format(Double.parseDouble(total_Price));
            impOrderBody.setTotal_Price(total_Price);//总价
        }
        double Price = Double.parseDouble(total_Price)/Double.parseDouble(qty);
        if(!StringUtils.isEmpty(Price)){
            String price = df.format(Price);
            impOrderBody.setPrice(price);//单价
        }

        impOrderBodyData.add(impOrderBody);
        return impOrderBodyData;
    }

    /**
     * 初始化索引值
     *
     * @param orderLists
     */
    public void getIndexValue(List<String> orderLists) {
        orderNoIndex = orderLists.indexOf(ExcelHeadOrder.orderNo);//订单编号
        itemNameIndex = orderLists.indexOf(ExcelHeadOrder.itemName);//商品名称
        qtyIndex = orderLists.indexOf(ExcelHeadOrder.qty);//数量
        unitIndex = orderLists.indexOf(ExcelHeadOrder.unit);//计量单位
        total_PriceIndex = orderLists.indexOf(ExcelHeadOrder.total_Price);//总价
        ebp_CodeIndex = orderLists.indexOf(ExcelHeadOrder.ebp_Code);//电商平台代码
        ebp_NameIndex = orderLists.indexOf(ExcelHeadOrder.ebp_Name);//电商平台名称
        ebc_CodeIndex = orderLists.indexOf(ExcelHeadOrder.ebc_Code);//电商企业代码
        ebc_NameIndex = orderLists.indexOf(ExcelHeadOrder.ebc_Name);//电商企业名称
        buyer_Reg_NoIndex = orderLists.indexOf(ExcelHeadOrder.buyer_Reg_No);//订购人注册号
        buyer_Id_NumberIndex = orderLists.indexOf(ExcelHeadOrder.buyer_Id_Number);//订购人证件号码
        buyer_NameIndex = orderLists.indexOf(ExcelHeadOrder.buyer_Name);//订购人姓名
        consigneeIndex = orderLists.indexOf(ExcelHeadOrder.consignee);//收货人姓名
        consignee_TelephoneIndex = orderLists.indexOf(ExcelHeadOrder.consignee_Telephone);//收货人电话
        consignee_AddressIndex = orderLists.indexOf(ExcelHeadOrder.consignee_Address);//收货地址
        freightIndex = orderLists.indexOf(ExcelHeadOrder.freight);//运杂费
        discountIndex = orderLists.indexOf(ExcelHeadOrder.discount);//非现金抵扣金额
        tax_TotalIndex = orderLists.indexOf(ExcelHeadOrder.tax_Total);//代扣税款
        originCountryIndex = orderLists.indexOf(ExcelHeadOrder.originCountry);//原产国
        noteIndex = orderLists.indexOf(ExcelHeadOrder.note);//备注

    }


}
