package com.xaeport.crossborder.excel.validate.entrybondinven;

import com.xaeport.crossborder.excel.headings.ExcelHeadBondInven;
import com.xaeport.crossborder.excel.validate.ValidateBase;
import com.xaeport.crossborder.excel.validate.ValidateUtil;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateBondInven extends ValidateBase {

    private Log log = LogFactory.getLog(this.getClass());
    private int orderNoIndex; //订单编号
    private int ebpCodeIndex; //电商平台代码
    private int ebpNameIndex; //电商平台名称
    private int logisticsNoIndex; //物流运单编号
    private int logisticsCodeIndex; //物流企业代码
    private int logisticsNameIndex; //物流企业名称
    private int portCodeIndex; //进境关别
    private int customsCodeIndex; //申报地海关代码
    private int grossWeightIndex; //毛重
    private int netWeightIndex; //净重
    private int buyerIdNumberIndex; //订购人证件号码
    private int buyerNameIndex; //订购人姓名
    private int buyerTelephoneIndex; //订购人电话
    private int consigneeAddressIndex; //收件地址
    private int trafModeIndex; //运输方式代码
    private int freightIndex; //运费
    private int insuredFeeIndex; //保费
    private int wrapTypeIndex;//包装种类代码

    private int itemNoIndex; //账册备案料号
    private int gCodeIndex; //商品编码
    private int gNameIndex; //商品名称
    private int gModelIndex; //商品规格型号
    private int qtyIndex; //数量
    private int unitIndex; //计量单位
    private int qty1Index; //法定数量
    private int unit1Index; //法定单位
    private int qty2Index; //第二法定数量
    private int unit2Index; //第二法定单位
    private int totalPriceIndex; //总价
    private int originCountryIndex; //原产国代码
    private int noteIndex; //备注

    private Map<Integer, String> indexMap = new HashMap<>();
    private Map<String, Integer> orderNoMap = new HashMap<>();

    public void initMap() {
        indexMap.put(orderNoIndex, "订单编号,60");
        indexMap.put(ebpCodeIndex, "电商平台代码,18");
        indexMap.put(ebpNameIndex, "电商平台名称,100");
        indexMap.put(logisticsNoIndex, "物流运单编号,60");
        indexMap.put(logisticsCodeIndex, "物流企业代码,18");
        indexMap.put(logisticsNameIndex, "物流企业名称,100");
        indexMap.put(portCodeIndex, "进境关别,4");
        indexMap.put(customsCodeIndex, "申报地海关代码,4");
        indexMap.put(grossWeightIndex, "毛重,19");
        indexMap.put(netWeightIndex, "净重,19");
        indexMap.put(buyerIdNumberIndex, "订购人证件号码,18");
        indexMap.put(buyerNameIndex, "订购人姓名,60");
        indexMap.put(buyerTelephoneIndex, "订购人电话,30");
        indexMap.put(consigneeAddressIndex, "收件地址,200");
        indexMap.put(trafModeIndex, "运输方式代码,1");
        indexMap.put(freightIndex, "运费,19");
        indexMap.put(insuredFeeIndex, "保费,19");
//        indexMap.put(wrapTypeIndex, "包装种类,1");
        indexMap.put(itemNoIndex, "账册备案料号,30");
        indexMap.put(gCodeIndex, "商品编码,20");
        indexMap.put(gNameIndex, "商品名称,250");
        indexMap.put(gModelIndex, "商品规格型号,510");
        indexMap.put(qtyIndex, "数量,19");
        indexMap.put(unitIndex, "计量单位,10");
        indexMap.put(qty1Index, "法定数量,19");
        indexMap.put(unit1Index, "法定单位,10");
//        indexMap.put(qty2Index, "第二法定数量,19");
//        indexMap.put(unit2Index, "第二法定单位,10");
        indexMap.put(totalPriceIndex, "总价,19");
        indexMap.put(originCountryIndex, "原产国代码,3");
//        indexMap.put(noteIndex, "备注,1000");
    }

    public int CheckRowError(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {

        //导入excel模板非空和长度判断
        boolean isEmpty = this.CheckedEmptyAndLen(indexMap, error_num, cell, rowNum, cell_num);
        if (!isEmpty) {
            return -1;
        }

        // 导入数据double类型判断
        if (cell_num == qtyIndex || cell_num == qty1Index || cell_num == freightIndex || cell_num == totalPriceIndex || cell_num == netWeightIndex || cell_num == insuredFeeIndex || cell_num == grossWeightIndex) {
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
        if (cell_num == unit2Index) {
            if (!unitMap.containsKey(unitValue)) {
                cell.setCellValue("");
            } else {
                String unitCode = unitMap.get(unitValue);
                cell.setCellValue(unitCode);
            }
        }
        if (cell_num == unit1Index || cell_num == unitIndex) {
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

    /**
     * 初始化索引值
     */
    public void getIndexValue(List<String> list) {
        orderNoIndex = list.indexOf(ExcelHeadBondInven.orderNo);
        ebpCodeIndex = list.indexOf(ExcelHeadBondInven.ebpCode);
        ebpNameIndex = list.indexOf(ExcelHeadBondInven.ebpName);
        logisticsNoIndex = list.indexOf(ExcelHeadBondInven.logisticsNo);
        logisticsCodeIndex = list.indexOf(ExcelHeadBondInven.logisticsCode);
        logisticsNameIndex = list.indexOf(ExcelHeadBondInven.logisticsName);
        portCodeIndex = list.indexOf(ExcelHeadBondInven.portCode);
        customsCodeIndex = list.indexOf(ExcelHeadBondInven.customsCode);
        grossWeightIndex = list.indexOf(ExcelHeadBondInven.grossWeight);
        netWeightIndex = list.indexOf(ExcelHeadBondInven.netWeight);
        buyerIdNumberIndex = list.indexOf(ExcelHeadBondInven.buyerIdNumber);
        buyerNameIndex = list.indexOf(ExcelHeadBondInven.buyerName);
        buyerTelephoneIndex = list.indexOf(ExcelHeadBondInven.buyerTelephone);
        consigneeAddressIndex = list.indexOf(ExcelHeadBondInven.consigneeAddress);
        trafModeIndex = list.indexOf(ExcelHeadBondInven.trafMode);
        freightIndex = list.indexOf(ExcelHeadBondInven.freight);
        insuredFeeIndex = list.indexOf(ExcelHeadBondInven.insuredFee);
        wrapTypeIndex = list.indexOf(ExcelHeadBondInven.wrapType);
        itemNoIndex = list.indexOf(ExcelHeadBondInven.itemNo);
        gCodeIndex = list.indexOf(ExcelHeadBondInven.gCode);
        gNameIndex = list.indexOf(ExcelHeadBondInven.gName);
        gModelIndex = list.indexOf(ExcelHeadBondInven.gModel);
        qtyIndex = list.indexOf(ExcelHeadBondInven.qty);
        unitIndex = list.indexOf(ExcelHeadBondInven.unit);
        qty1Index = list.indexOf(ExcelHeadBondInven.qty1);
        unit1Index = list.indexOf(ExcelHeadBondInven.unit1);
        qty2Index = list.indexOf(ExcelHeadBondInven.qty2);
        unit2Index = list.indexOf(ExcelHeadBondInven.unit2);
        totalPriceIndex = list.indexOf(ExcelHeadBondInven.totalPrice);
        originCountryIndex = list.indexOf(ExcelHeadBondInven.originCountry);
        noteIndex = list.indexOf(ExcelHeadBondInven.note);
        this.initMap();
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
