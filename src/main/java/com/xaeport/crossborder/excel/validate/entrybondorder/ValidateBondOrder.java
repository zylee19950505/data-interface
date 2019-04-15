package com.xaeport.crossborder.excel.validate.entrybondorder;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.excel.headings.ExcelHeadBondOrder;
import com.xaeport.crossborder.excel.validate.ValidateBase;
import com.xaeport.crossborder.excel.validate.ValidateUtil;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateBondOrder extends ValidateBase {

    //要校验字段索引
    private LoadData loadData = SpringUtils.getBean(LoadData.class);
    private int orderNoIndex; //订单编号";//head //list
    private int ebp_CodeIndex; //电商平台代码";//head
    private int ebp_NameIndex; //电商平台名称";//head
    private int ebc_CodeIndex; //电商企业代码";//head
    private int ebc_NameIndex; //电商企业名称";//head
    private int buyer_Reg_NoIndex; //订购人注册号";//head
    private int buyer_Id_NumberIndex; //订购人证件号码";//head
    private int buyer_NameIndex; //订购人姓名";//head
    private int buyer_TelephoneIndex; //订购人电话;//head
    private int consigneeIndex; //收货人姓名";//head
    private int consignee_TelephoneIndex; //收货人电话";//head
    private int consignee_AddressIndex; //收货地址";//head
    private int freightIndex; //运杂费";//head
    private int discountIndex; //非现金抵扣金额";//head
    private int tax_TotalIndex; //代扣税款";//head
    private int grossWeightIndex;//毛重//head
    private int netWeightIndex;//净重//head
    private int insuredFeeIndex;//保价费//head

    private int itemNoIndex; //企业商品货号";//list
    private int itemNameIndex; //商品名称";//list
    private int g_modelIndex; //商品规格型号";//list
    private int qtyIndex; //数量";//list
    private int unitIndex; //计量单位";//list
    private int total_PriceIndex; //总价";//list
    private int originCountryIndex; //原产国";//list
    private int noteIndex; //备注";//list

    private Map<Integer, String> indexMap = new HashMap<>();
    private Map<String, Integer> orderNoMap = new HashMap<>();

    public void getIndexValue(List<String> list) {
        orderNoIndex = list.indexOf(ExcelHeadBondOrder.orderNo);//订单编号
        itemNoIndex = list.indexOf(ExcelHeadBondOrder.itemNo);//企业商品货号
        itemNameIndex = list.indexOf(ExcelHeadBondOrder.itemName);//商品名称
        g_modelIndex = list.indexOf(ExcelHeadBondOrder.g_model);//商品规格型号
        qtyIndex = list.indexOf(ExcelHeadBondOrder.qty);//数量
        unitIndex = list.indexOf(ExcelHeadBondOrder.unit);//计量单位
        total_PriceIndex = list.indexOf(ExcelHeadBondOrder.total_Price);//总价
        ebp_CodeIndex = list.indexOf(ExcelHeadBondOrder.ebp_Code);//电商平台代码
        ebp_NameIndex = list.indexOf(ExcelHeadBondOrder.ebp_Name);//电商平台名称
        ebc_CodeIndex = list.indexOf(ExcelHeadBondOrder.ebc_Code);//电商企业代码
        ebc_NameIndex = list.indexOf(ExcelHeadBondOrder.ebc_Name);//电商企业名称
        buyer_Reg_NoIndex = list.indexOf(ExcelHeadBondOrder.buyer_Reg_No);//订购人注册号
        buyer_Id_NumberIndex = list.indexOf(ExcelHeadBondOrder.buyer_Id_Number);//订购人证件号码
        buyer_NameIndex = list.indexOf(ExcelHeadBondOrder.buyer_Name);//订购人姓名
        buyer_TelephoneIndex = list.indexOf(ExcelHeadBondOrder.buyer_TelePhone);//订购人姓名
        consigneeIndex = list.indexOf(ExcelHeadBondOrder.consignee);//收货人姓名
        consignee_TelephoneIndex = list.indexOf(ExcelHeadBondOrder.consignee_Telephone);//收货人电话
        consignee_AddressIndex = list.indexOf(ExcelHeadBondOrder.consignee_Address);//收货地址
        freightIndex = list.indexOf(ExcelHeadBondOrder.freight);//运杂费
        discountIndex = list.indexOf(ExcelHeadBondOrder.discount);//非现金抵扣金额
        tax_TotalIndex = list.indexOf(ExcelHeadBondOrder.tax_Total);//代扣税款
        originCountryIndex = list.indexOf(ExcelHeadBondOrder.originCountry);//原产国
        grossWeightIndex = list.indexOf(ExcelHeadBondOrder.grossWeight);//毛重
        netWeightIndex = list.indexOf(ExcelHeadBondOrder.netWeight);//净重
        insuredFeeIndex = list.indexOf(ExcelHeadBondOrder.insuredFee);//保价费
        noteIndex = list.indexOf(ExcelHeadBondOrder.note);//备注
        this.initMap();
    }

    public void initMap() {
        indexMap.put(orderNoIndex, "订单编号,60");
        indexMap.put(itemNoIndex, "企业商品货号,30");
        indexMap.put(itemNameIndex, "企业商品名称,250");
        indexMap.put(g_modelIndex, "商品规格型号,510");
        indexMap.put(qtyIndex, "数量,19");//double
        indexMap.put(unitIndex, "计量单位,10");
        indexMap.put(total_PriceIndex, "总价,19");//double
        indexMap.put(ebp_CodeIndex, "电商平台代码,18");
        indexMap.put(ebp_NameIndex, "电商平台名称,100");
        indexMap.put(ebc_CodeIndex, "电商企业代码,18");
        indexMap.put(ebc_NameIndex, "电商企业名称,100");
        indexMap.put(buyer_Reg_NoIndex, "订购人注册号,60");
        indexMap.put(buyer_Id_NumberIndex, "订购人证件号码,18");
        indexMap.put(buyer_NameIndex, "订购人姓名,60");
        indexMap.put(buyer_TelephoneIndex, "订购人电话,30");
        indexMap.put(consigneeIndex, "收货人姓名,100");
        indexMap.put(consignee_TelephoneIndex, "收货人电话,50");
        indexMap.put(consignee_AddressIndex, "收货地址,200");
        indexMap.put(freightIndex, "运杂费,19");//double
        indexMap.put(discountIndex, "非现金抵扣金额,19");//double
        indexMap.put(tax_TotalIndex, "代扣税款,19");//double
        indexMap.put(originCountryIndex, "原产国,3");
        indexMap.put(grossWeightIndex, "毛重,19");//double
        indexMap.put(netWeightIndex, "净重,19");//double
        indexMap.put(insuredFeeIndex, "保价费,19");//double
//        indexMap.put(noteIndex, "备注,1000");

    }


    public int CheckRowError(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {

        //导入excel模板非空和长度判断
        boolean isEmpty = this.CheckedEmptyAndLen(indexMap, error_num, cell, rowNum, cell_num);
        if (!isEmpty) {
            return -1;
        }


        // 导入数据double类型判断
        if (cell_num == discountIndex || cell_num == tax_TotalIndex || cell_num == qtyIndex ||
                cell_num == total_PriceIndex || cell_num == freightIndex || cell_num == netWeightIndex ||
                cell_num == insuredFeeIndex || cell_num == grossWeightIndex) {
            String message = indexMap.get(cell_num).split(",")[0];
            int flag = ValidateUtil.checkDoubleValue(cell);
            boolean checkNumberType = this.CheckNumberType(flag, error_num, rowNum, cell_num, message);
            if (!checkNumberType) {
                return -1;
            }
        }
        return 0;
    }

    public int getUnitCode(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {
        int flag = 0;
        String unitValue = cell.toString().replace(" ", "");
        if (cell_num == unitIndex) {
            String message = indexMap.get(cell_num).split(",")[0];
            if (!unitMap.containsKey(unitValue)) {
                error_num.put("error", String.format(String.format("导入失败请修改后重新导入，第%%d行第%%d列。<%s>数据格式不对！", message), rowNum + 1, cell_num + 1));
                flag = -1;
            } else {
                String unitCode = unitMap.get(unitValue);
                cell.setCellValue(unitCode);
                flag = 1;
            }
        }
        return flag;
    }

    public int checkRowAmount(List list, Map<String, Object> map) {
        int num = 0;
        String orderno = list.get(orderNoIndex).toString();
        if (orderNoMap.containsKey(orderno)) {
            int count = Integer.parseInt(orderNoMap.get(orderno).toString()) + 1;
            if (count > 99) {
                map.put("error", String.format("订单<%s>的商品条目不能超过99", orderno));
                num = 1;
            }
            orderNoMap.put(orderno, count);
        } else {
            orderNoMap.put(orderno, 1);
        }
        return 0;
    }

}
