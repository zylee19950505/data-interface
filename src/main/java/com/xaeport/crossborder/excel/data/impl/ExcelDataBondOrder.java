package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.headings.ExcelHeadBondOrder;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

//保税订单模板校验
public class ExcelDataBondOrder implements ExcelData {
    private Log log = LogFactory.getLog(this.getClass());
    private int orderNoIndex; //订单编号
    private int batch_numbersIndex; //商品批次号
    private int trade_modeIndex; //贸易方式
    private int ebp_CodeIndex; //电商平台代码
    private int ebp_NameIndex; //电商平台名称
    private int ebc_CodeIndex; //电商企业代码
    private int ebc_NameIndex; //电商企业名称
    private int port_codeIndex; //口岸海关代码
    private int buyer_Reg_NoIndex; //订购人注册号
    private int buyer_NameIndex; //订购人姓名
    private int buyer_Id_NumberIndex; //订购人身份证号码
    private int buyer_TelePhoneIndex; //订购人姓名
    private int consigneeIndex; //收货人姓名
    private int consignee_TelephoneIndex; //收货人电话
    private int consignee_AddressIndex; //收件地址
    private int insuredFeeIndex;//保价费
    private int freightIndex; //运杂费
    private int discountIndex; //非现金抵扣金额
    private int tax_TotalIndex; //代扣税款
    private int grossWeightIndex;//订单总毛重
    private int netWeightIndex;//订单总净重

    private int itemNameIndex; //商品名称
    private int itemNoIndex; //企业商品货号
    private int g_modelIndex; //商品规格型号
    private int qtyIndex; //数量
    private int unitIndex; //计量单位
    private int total_PriceIndex; //总价
    private int noteIndex; //备注

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
        DecimalFormat df = new DecimalFormat("0.00");
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            impOrderHead = new ImpOrderHead();
            List<String> value = entry.getValue();

            impOrderHead.setOrder_No(value.get(orderNoIndex));//订单编号
            impOrderHead.setBatch_Numbers(value.get(batch_numbersIndex));//商品批次号
            impOrderHead.setTrade_mode(value.get(trade_modeIndex));//贸易方式
            impOrderHead.setEbp_Code(value.get(ebp_CodeIndex));//电商平台代码
            impOrderHead.setEbp_Name(value.get(ebp_NameIndex));//电商平台名称
            impOrderHead.setEbc_Code(value.get(ebc_CodeIndex));//电商企业代码
            impOrderHead.setEbc_Name(value.get(ebc_NameIndex));//电商企业名称
            impOrderHead.setPort_code(value.get(port_codeIndex));//口岸海关代码

            impOrderHead.setBuyer_Reg_No(value.get(buyer_Reg_NoIndex));//订购人注册号
            impOrderHead.setBuyer_Name(value.get(buyer_NameIndex));//订购人姓名
            impOrderHead.setBuyer_Id_Number(value.get(buyer_Id_NumberIndex));//订购人证件号码
            impOrderHead.setBuyer_TelePhone(value.get(buyer_TelePhoneIndex));//订购人电话
            impOrderHead.setConsignee(value.get(consigneeIndex));//收货人姓名
            impOrderHead.setConsignee_Telephone(value.get(consignee_TelephoneIndex));//收货人电话
            impOrderHead.setConsignee_Address(value.get(consignee_AddressIndex));//收件地址

            impOrderHead.setInsured_fee(getDouble(value.get(insuredFeeIndex)));//保价费
            impOrderHead.setFreight(getDouble(value.get(freightIndex)));//运杂费
            impOrderHead.setDiscount(getDouble(value.get(discountIndex)));//非现金抵扣金额
            impOrderHead.setTax_Total(getDouble(value.get(tax_TotalIndex)));//代扣税款
            impOrderHead.setGross_weight(getDouble(value.get(grossWeightIndex)));//订单总毛重
            impOrderHead.setNet_weight(getDouble(value.get(netWeightIndex)));//订单总净重

            impOrderHead.setGoods_Value(getDouble(value.get(total_PriceIndex)));//商品价格
            double actural_Paid = Double.parseDouble(impOrderHead.getGoods_Value()) + Double.parseDouble(impOrderHead.getFreight()) + Double.parseDouble(impOrderHead.getTax_Total()) - Double.parseDouble(impOrderHead.getDiscount());
            // 实际支付金额 = 商品价格 + 运杂费 + 代扣税款 - 非现金抵扣金额，与支付凭证的支付金额一致。
            if (!StringUtils.isEmpty(actural_Paid)) {
                String acturalPaid = df.format(actural_Paid);
                impOrderHead.setActural_Paid(acturalPaid);
            }
//            impOrderHead.setNote(excelData.get(i).get(noteIndex));//备注
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
        DecimalFormat df = new DecimalFormat("0.00");
        if (impOrderHeadMap.containsKey(orderNo)) {
            List<String> list = impOrderHeadMap.get(orderNo);//存放每次合并的结果
            String allTotalPrice = list.get(total_PriceIndex);
            String addTotalPrice = orderHeadLists.get(total_PriceIndex);
            if (StringUtils.isEmpty(allTotalPrice)) {
                allTotalPrice = "0";
            }
            if (StringUtils.isEmpty(addTotalPrice)) {
                addTotalPrice = "0";
            }
            double total_value_Total = Double.parseDouble(allTotalPrice) + Double.parseDouble(addTotalPrice);//合并所有表体的总价
            list.set(total_PriceIndex, df.format(total_value_Total));//商品价格
            impOrderHeadMap.put(orderNo, list);
        } else {
            String totalPrice = orderHeadLists.get(total_PriceIndex);
            if (StringUtils.isEmpty(totalPrice)) {
                totalPrice = "0";
                orderHeadLists.set(total_PriceIndex, totalPrice);
            }
            impOrderHeadMap.put(orderNo, orderHeadLists);
        }
        return impOrderHeadMap;
    }

    /**
     * 封装ImpOrderBody数据
     *
     * @param entryLists
     * @param impOrderBody
     * @param impOrderBodyData
     * @return
     */
    public List<ImpOrderBody> impOrderBodyData(List<String> entryLists, ImpOrderBody impOrderBody, List<ImpOrderBody> impOrderBodyData) {
        DecimalFormat df = new DecimalFormat("0.00");
        impOrderBody.setOrder_No(entryLists.get(orderNoIndex));//订单编号
        impOrderBody.setItem_Name(entryLists.get(itemNameIndex));//商品名称
        impOrderBody.setItem_No(entryLists.get(itemNoIndex));//企业商品货号
        impOrderBody.setG_Model(entryLists.get(g_modelIndex));//商品规格型号
        impOrderBody.setUnit(entryLists.get(unitIndex));//计量单位
        impOrderBody.setNote(entryLists.get(noteIndex));//备注
        impOrderBody.setQty(getDouble(entryLists.get(qtyIndex)));//数量
        impOrderBody.setTotal_Price(getDouble(entryLists.get(total_PriceIndex)));//总价
        double Price = Double.parseDouble(impOrderBody.getTotal_Price()) / Double.parseDouble(impOrderBody.getQty());
        if (!StringUtils.isEmpty(Price)) {
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
        orderNoIndex = orderLists.indexOf(ExcelHeadBondOrder.orderNo);//订单编号
        batch_numbersIndex = orderLists.indexOf(ExcelHeadBondOrder.batch_numbers);//商品批次号
        trade_modeIndex = orderLists.indexOf(ExcelHeadBondOrder.trade_mode);//贸易方式
        ebp_CodeIndex = orderLists.indexOf(ExcelHeadBondOrder.ebp_Code);//电商平台代码
        ebp_NameIndex = orderLists.indexOf(ExcelHeadBondOrder.ebp_Name);//电商平台名称
        ebc_CodeIndex = orderLists.indexOf(ExcelHeadBondOrder.ebc_Code);//电商企业代码
        ebc_NameIndex = orderLists.indexOf(ExcelHeadBondOrder.ebc_Name);//电商企业名称
        port_codeIndex = orderLists.indexOf(ExcelHeadBondOrder.port_code);//口岸海关代码
        buyer_Reg_NoIndex = orderLists.indexOf(ExcelHeadBondOrder.buyer_Reg_No);//订购人注册号
        buyer_NameIndex = orderLists.indexOf(ExcelHeadBondOrder.buyer_Name);//订购人姓名
        buyer_Id_NumberIndex = orderLists.indexOf(ExcelHeadBondOrder.buyer_Id_Number);//订购人身份证号码
        buyer_TelePhoneIndex = orderLists.indexOf(ExcelHeadBondOrder.buyer_TelePhone);//订购人电话
        consigneeIndex = orderLists.indexOf(ExcelHeadBondOrder.consignee);//收货人姓名
        consignee_TelephoneIndex = orderLists.indexOf(ExcelHeadBondOrder.consignee_Telephone);//收货人电话
        consignee_AddressIndex = orderLists.indexOf(ExcelHeadBondOrder.consignee_Address);//收件地址
        insuredFeeIndex = orderLists.indexOf(ExcelHeadBondOrder.insuredFee);//保价费
        freightIndex = orderLists.indexOf(ExcelHeadBondOrder.freight);//运杂费
        discountIndex = orderLists.indexOf(ExcelHeadBondOrder.discount);//非现金抵扣金额
        tax_TotalIndex = orderLists.indexOf(ExcelHeadBondOrder.tax_Total);//代扣税款
        grossWeightIndex = orderLists.indexOf(ExcelHeadBondOrder.grossWeight);//订单总毛重
        netWeightIndex = orderLists.indexOf(ExcelHeadBondOrder.netWeight);//订单总净重

        itemNameIndex = orderLists.indexOf(ExcelHeadBondOrder.itemName);//商品名称
        itemNoIndex = orderLists.indexOf(ExcelHeadBondOrder.itemNo);//企业商品货号
        g_modelIndex = orderLists.indexOf(ExcelHeadBondOrder.g_model);//商品规格型号
        qtyIndex = orderLists.indexOf(ExcelHeadBondOrder.qty);//数量
        unitIndex = orderLists.indexOf(ExcelHeadBondOrder.unit);//计量单位
        total_PriceIndex = orderLists.indexOf(ExcelHeadBondOrder.total_Price);//总价
        noteIndex = orderLists.indexOf(ExcelHeadBondOrder.note);//备注
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
