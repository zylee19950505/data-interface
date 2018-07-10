package com.xaeport.crossborder.excel.validate.entryOrder;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.excel.headings.ExcelHeadOrder;
import com.xaeport.crossborder.excel.validate.ValidateBase;
import com.xaeport.crossborder.excel.validate.ValidateUtil;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/06/28.
 */
public class ValidateOrder extends ValidateBase {
    //要校验字段索引
    private LoadData loadData = SpringUtils.getBean(LoadData.class);
    private int orderNoIndex; //订单编号";//head //list
    private int copNoIndex ; //企业内部编号";//QING
    private int logisticsNoIndex; //物流运单编号";//QING
    private int itemNameIndex; //商品名称";//list//QING
    private int gcodeIndex; //商品编码";//QING
    private int gmodelIndex; //商品规格型号";//QING
    private int qtyIndex; //数量";//list
    private int unitIndex; //计量单位";//list
    private int qty1Index; //第一法定数量";//list
    private int unit1Index; //第一法定单位";//list
    private int qty2Index; //第二法定数量";//list
    private int unit2Index; //第二法定单位";//list
    private int total_PriceIndex; //总价";//list
    private int ebp_CodeIndex; //电商平台代码";//head//QING
    private int ebp_NameIndex; //电商平台名称";//head//QING
    private int ebc_CodeIndex; //电商企业代码";//head//QING
    private int ebc_NameIndex; //电商企业名称";//head//QING
    private int assureCodeIndex; //担保企业编号";//QING
    private int customsCodeIndex; //申报海关代码";//QING
    private int portCodeIndex; //口岸海关代码";//QING
    private int ieDateIndex; //进口日期";//
    private int buyer_Reg_NoIndex; //订购人注册号";//head
    private int buyer_Id_NumberIndex; //订购人证件号码";//head
    private int buyer_NameIndex; //订购人姓名";//head
    private int buyerTelephoneIndex; //订购人电话";//QING
    private int consigneeIndex; //收货人姓名";//head
    private int consignee_TelephoneIndex; //收货人电话";//head
    private int consignee_AddressIndex; //收货地址";//head//QING
    private int goodsValueIndex; //商品价格";//head
    private int freightIndex; //运杂费";//head//QING
    private int discountIndex; //非现金抵扣金额";//head
    private int tax_TotalIndex; //代扣税款";//head
    private int agentCodeIndex; //申报企业代码";//QING
    private int agentNameIndex; //申报企业名称";//QING
    private int trafModeIndex; //运输方式"; //QING
    private int trafNoIndex; //运输工具编号"; //QING
    private int flightVoyageIndex; //航班航次";//QING
    private int billNoIndex; //提运单号 //QING
    private int originCountryIndex; //原产国";//list//QING
    private int startCountryIndex; //起运国";//QING
    private int netWeightIndex; //净重";//QING
    private int noteIndex; //备注";//head

    private Map<Integer, String> indexMap = new HashMap<>();

    public void getIndexValue(List<String> list) {
        orderNoIndex = list.indexOf(ExcelHeadOrder.orderNo);//订单编号
        copNoIndex = list.indexOf(ExcelHeadOrder.copNo);//企业内部编号
        logisticsNoIndex = list.indexOf(ExcelHeadOrder.logisticsNo);//物流运单编号
        itemNameIndex = list.indexOf(ExcelHeadOrder.itemName);//商品名称
        gcodeIndex = list.indexOf(ExcelHeadOrder.gcode);//商品编码
        gmodelIndex = list.indexOf(ExcelHeadOrder.gmodel);//商品规格型号
        qtyIndex = list.indexOf(ExcelHeadOrder.qty);//数量
        unitIndex = list.indexOf(ExcelHeadOrder.unit);//计量单位
        qty1Index = list.indexOf(ExcelHeadOrder.qty1);//第一法定数量
        unit1Index = list.indexOf(ExcelHeadOrder.unit1);//第一法定单位
        qty2Index = list.indexOf(ExcelHeadOrder.qty2);//第二法定数量
        unit2Index = list.indexOf(ExcelHeadOrder.unit2);//第二法定单位
        total_PriceIndex = list.indexOf(ExcelHeadOrder.total_Price);//总价
        ebp_CodeIndex = list.indexOf(ExcelHeadOrder.ebp_Code);//电商平台代码
        ebp_NameIndex = list.indexOf(ExcelHeadOrder.ebp_Name);//电商平台名称
        ebc_CodeIndex = list.indexOf(ExcelHeadOrder.ebc_Code);//电商企业代码
        ebc_NameIndex = list.indexOf(ExcelHeadOrder.ebc_Name);//电商企业名称
        assureCodeIndex = list.indexOf(ExcelHeadOrder.assureCode);//担保企业编号
        customsCodeIndex = list.indexOf(ExcelHeadOrder.customsCode);//申报海关代码
        portCodeIndex = list.indexOf(ExcelHeadOrder.portCode);//口岸海关代码
        ieDateIndex = list.indexOf(ExcelHeadOrder.ieDate);//进口日期
        buyer_Reg_NoIndex = list.indexOf(ExcelHeadOrder.buyer_Reg_No);//订购人注册号
        buyer_Id_NumberIndex = list.indexOf(ExcelHeadOrder.buyer_Id_Number);//订购人证件号码
        buyer_NameIndex = list.indexOf(ExcelHeadOrder.buyer_Name);//订购人姓名
        buyerTelephoneIndex = list.indexOf(ExcelHeadOrder.buyerTelephone);//订购人电话
        consigneeIndex = list.indexOf(ExcelHeadOrder.consignee);//收货人姓名
        consignee_TelephoneIndex = list.indexOf(ExcelHeadOrder.consignee_Telephone);//收货人电话
        consignee_AddressIndex = list.indexOf(ExcelHeadOrder.consignee_Address);//收货地址
        goodsValueIndex = list.indexOf(ExcelHeadOrder.goodsValue);//商品价格
        freightIndex = list.indexOf(ExcelHeadOrder.freight);//运杂费
        discountIndex = list.indexOf(ExcelHeadOrder.discount);//非现金抵扣金额
        tax_TotalIndex = list.indexOf(ExcelHeadOrder.tax_Total);//代扣税款
        agentCodeIndex = list.indexOf(ExcelHeadOrder.agentCode);//申报企业代码
        agentNameIndex = list.indexOf(ExcelHeadOrder.agentName);//申报企业名称
        trafModeIndex = list.indexOf(ExcelHeadOrder.trafMode);//运输方式
        trafNoIndex = list.indexOf(ExcelHeadOrder.trafNo);//运输工具编号
        flightVoyageIndex = list.indexOf(ExcelHeadOrder.flightVoyage);//航班航次
        billNoIndex = list.indexOf(ExcelHeadOrder.billNo); //提运单号
        originCountryIndex = list.indexOf(ExcelHeadOrder.originCountry);//原产国
        startCountryIndex = list.indexOf(ExcelHeadOrder.startCountry);//起运国
        netWeightIndex = list.indexOf(ExcelHeadOrder.netWeight);//净重
        noteIndex = list.indexOf(ExcelHeadOrder.note);//备注

        this.initMap();
    }

    public void initMap() {
        indexMap.put(orderNoIndex, "订单编号,60");
        indexMap.put(copNoIndex, "企业内部编号,60");
        indexMap.put(logisticsNoIndex, "物流运单编号,60");
        indexMap.put(itemNameIndex, "商品名称,250");
        indexMap.put(gcodeIndex, "商品编码,16");
        indexMap.put(gmodelIndex, "商品规格型号,200");
        indexMap.put(qtyIndex, "数量,19");//double
        indexMap.put(unitIndex, "计量单位,10");
        indexMap.put(qty1Index, "第一法定数量,19");//double
        indexMap.put(unit1Index, "第一法定计量单位,10");
//        indexMap.put(qty2Index, "第二法定数量,19");//double
//        indexMap.put(unit2Index, "第二法定计量单位,10");
        indexMap.put(total_PriceIndex, "总价,19");//double
        indexMap.put(ebp_CodeIndex, "电商平台代码,18");
        indexMap.put(ebp_NameIndex, "电商平台名称,100");
        indexMap.put(ebc_CodeIndex, "电商企业代码,18");
        indexMap.put(ebc_NameIndex, "电商企业名称,100");
        indexMap.put(assureCodeIndex, "担保企业编号,20");
        indexMap.put(customsCodeIndex, "申报海关代码,10");
        indexMap.put(portCodeIndex, "口岸海关代码,10");
//        indexMap.put(ieDateIndex, "进口日期,3");
        indexMap.put(buyer_Reg_NoIndex, "订购人注册号,60");
        indexMap.put(buyer_Id_NumberIndex, "订购人证件号码,60");
        indexMap.put(buyer_NameIndex, "订购人姓名,60");
        indexMap.put(buyerTelephoneIndex, "订购人电话,60");
        indexMap.put(consigneeIndex, "收货人姓名,100");
        indexMap.put(consignee_TelephoneIndex, "收货人电话,50");
        indexMap.put(consignee_AddressIndex, "收货地址,200");
        indexMap.put(goodsValueIndex, "商品价格,19");//double
        indexMap.put(freightIndex, "运杂费,19");//double
        indexMap.put(discountIndex, "非现金抵扣金额,19");//double
        indexMap.put(tax_TotalIndex, "代扣税款,19");//double
        indexMap.put(agentCodeIndex, "申报企业代码,20");
        indexMap.put(agentNameIndex, "申报企业名称,200");
        indexMap.put(trafModeIndex, "运输方式,5");
        indexMap.put(trafNoIndex, "运输工具编号,32");
        indexMap.put(flightVoyageIndex, "航班航次,32");
        indexMap.put(billNoIndex,"提运单号,20");
        indexMap.put(originCountryIndex, "原产国,3");
        indexMap.put(startCountryIndex, "起运国,3");
        indexMap.put(netWeightIndex, "净重,19");//double
//        indexMap.put(noteIndex, "备注,1000");

    }


    public int CheckRowError(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {

        //导入excel模板非空和长度判断
        if (cell_num != noteIndex || cell_num != ieDateIndex || cell_num != qty2Index || cell_num != unit2Index) {
            boolean isEmpty = this.CheckedEmptyAndLen(indexMap, error_num, cell, rowNum, cell_num);
            if (!isEmpty) {
                return -1;
            }
        }

        // 导入数据double类型判断
        if (cell_num == goodsValueIndex || cell_num == discountIndex || cell_num == tax_TotalIndex ||
                cell_num == qtyIndex || cell_num == total_PriceIndex || cell_num == freightIndex ||
                cell_num == netWeightIndex || cell_num == qty1Index) {
            String message = indexMap.get(cell_num).split(",")[0];
            int flag = ValidateUtil.checkDoubleValue(cell);
            boolean checkNumberType = this.CheckNumberType(flag, error_num, rowNum, cell_num, message);
            if (!checkNumberType) {
                return -1;
            }
        }

        return 0;
    }


}
