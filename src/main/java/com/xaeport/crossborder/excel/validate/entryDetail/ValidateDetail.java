package com.xaeport.crossborder.excel.validate.entryDetail;

import com.xaeport.crossborder.excel.headings.ExcelHeadDetail;
import com.xaeport.crossborder.excel.validate.ValidateBase;
import com.xaeport.crossborder.excel.validate.ValidateUtil;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateDetail extends ValidateBase {

    private Log log = LogFactory.getLog(this.getClass());
    private int orderNoIndex; //"订单编号";//head //list
    //    private int copNoIndex; //"企业内部编号";//head
    private int logisticsNoIndex; //"物流运单编号";//head
    private int logisticsCodeIndex; //"物流企业代码";//head
    private int logisticsNameIndex; //"物流企业名称";//head
    private int gnameIndex; //"商品名称";//list
    private int gcodeIndex; //"商品编码";//list
    private int gmodelIndex; //"商品规格型号";//list
    private int qtyIndex; //"数量";//list
    private int unitIndex; //"计量单位";//list
    private int qty1Index; //"第一法定数量";//list
    private int unit1Index; //"第一法定计量单位";//list
    private int qty2Index; //"第二法定数量";//list
    private int unit2Index; //"第二法定计量单位";//list
    private int total_PriceIndex; //"总价";//list
    private int ebp_CodeIndex; //"电商平台代码";//head
    private int ebp_NameIndex; //"电商平台名称";//head
    private int ebc_CodeIndex; //"电商企业代码";//head
    private int ebc_NameIndex; //"电商企业名称";//head
    private int assureCodeIndex; //"担保企业编号";//head
    private int customsCodeIndex; //"申报海关代码";//head
    private int portCodeIndex; //"口岸海关代码";//head
    private int ieDateIndex; //"进口日期";//head
    private int buyer_Id_NumberIndex; //"订购人证件号码";//head
    private int buyer_NameIndex; //"订购人姓名";//head
    private int buyerTelephoneIndex; //"订购人电话";//head
    private int consignee_AddressIndex; //"收件地址";//head
    private int freightIndex; //"运费";//head
    private int insuredFeeIndex; //"保费";//head
    private int agentCodeIndex; //"申报企业代码";//head
    private int agentNameIndex; //"申报企业名称";//head
    private int trafModeIndex; //"运输方式";//head
    private int trafNoIndex; //"运输工具编号";//head
    private int flightVoyageIndex; //"航班航次号";//head
    private int billNoIndex; //"提运单号";//head
    private int originCountryIndex; //"原产国";//list
    private int startCountryIndex; //"起运国";//head
    private int grossWeightIndex; //"毛重";//head
    private int netWeightIndex; //"净重";//head
    private int noteIndex; //"备注";//list

    private Map<Integer, String> indexMap = new HashMap<>();

    public void initMap() {
        indexMap.put(orderNoIndex, "订单编号,60");
//        indexMap.put(copNoIndex, "企业内部编号,20");
        indexMap.put(logisticsNoIndex, "物流运单编号,60");
        indexMap.put(logisticsCodeIndex, "物流企业代码,18");
        indexMap.put(logisticsNameIndex, "物流企业名称,100");
        indexMap.put(gnameIndex, "商品名称,250");
        indexMap.put(gcodeIndex, "商品编码,20");
        indexMap.put(gmodelIndex, "商品规格型号,250");
        indexMap.put(qtyIndex, "数量,19");
        indexMap.put(unitIndex, "计量单位,10");
        indexMap.put(qty1Index, "第一法定数量,19");
        indexMap.put(unit1Index, "第一法定计量单位,10");
//        indexMap.put(qty2Index, "第二法定数量,19");
//        indexMap.put(unit2Index, "第二法定计量单位,10");
        indexMap.put(total_PriceIndex, "总价,19");
        indexMap.put(ebp_CodeIndex, "电商平台代码,18");
        indexMap.put(ebp_NameIndex, "电商平台名称,100");
        indexMap.put(ebc_CodeIndex, "电商企业代码,18");
        indexMap.put(ebc_NameIndex, "电商企业名称,100");
        indexMap.put(assureCodeIndex, "担保企业编号,30");
        indexMap.put(customsCodeIndex, "申报海关代码,4");
        indexMap.put(portCodeIndex, "口岸海关代码,4");
//        indexMap.put(ieDateIndex, "进口日期,14");
        indexMap.put(buyer_Id_NumberIndex, "订购人证件号码,60");
        indexMap.put(buyer_NameIndex, "订购人姓名,60");
        indexMap.put(buyerTelephoneIndex, "订购人电话,30");
        indexMap.put(consignee_AddressIndex, "收件地址,200");
        indexMap.put(freightIndex, "运费,19");
        indexMap.put(insuredFeeIndex, "保费,19");
        indexMap.put(agentCodeIndex, "申报企业代码,18");
        indexMap.put(agentNameIndex, "申报企业名称,100");
        indexMap.put(trafModeIndex, "运输方式,1");
        indexMap.put(trafNoIndex, "运输工具编号,100");
        indexMap.put(flightVoyageIndex, "航班航次号,32");
        indexMap.put(billNoIndex, "提运单号,37");
        indexMap.put(originCountryIndex, "原产国,3");
        indexMap.put(startCountryIndex, "起运国,3");
        indexMap.put(grossWeightIndex, "毛重,19");
        indexMap.put(netWeightIndex, "净重,19");
//        indexMap.put(noteIndex, "备注,1000");

    }

    public int CheckRowError(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {

        //导入excel模板非空和长度判断
        boolean isEmpty = this.CheckedEmptyAndLen(indexMap, error_num, cell, rowNum, cell_num);
        if (!isEmpty) {
            return -1;
        }

        // 导入数据double类型判断
        if (cell_num == qtyIndex || cell_num == qty1Index || cell_num == freightIndex || cell_num == total_PriceIndex || cell_num == netWeightIndex || cell_num == insuredFeeIndex || cell_num == grossWeightIndex) {
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
     *
     * @param list
     */
    public void getIndexValue(List<String> list) {
        orderNoIndex = list.indexOf(ExcelHeadDetail.orderNo);
//        copNoIndex = list.indexOf(ExcelHeadDetail.copNo);
        logisticsNoIndex = list.indexOf(ExcelHeadDetail.logisticsNo);
        logisticsCodeIndex = list.indexOf(ExcelHeadDetail.logisticsCode);
        logisticsNameIndex = list.indexOf(ExcelHeadDetail.logisticsName);
        gnameIndex = list.indexOf(ExcelHeadDetail.gname);
        gcodeIndex = list.indexOf(ExcelHeadDetail.gcode);
        gmodelIndex = list.indexOf(ExcelHeadDetail.gmodel);
        qtyIndex = list.indexOf(ExcelHeadDetail.qty);
        unitIndex = list.indexOf(ExcelHeadDetail.unit);
        qty1Index = list.indexOf(ExcelHeadDetail.qty1);
        unit1Index = list.indexOf(ExcelHeadDetail.unit1);
//        qty2Index = list.indexOf(ExcelHeadDetail.qty2);
        unit2Index = list.indexOf(ExcelHeadDetail.unit2);
        total_PriceIndex = list.indexOf(ExcelHeadDetail.total_Price);
        ebp_CodeIndex = list.indexOf(ExcelHeadDetail.ebp_Code);
        ebp_NameIndex = list.indexOf(ExcelHeadDetail.ebp_Name);
        ebc_CodeIndex = list.indexOf(ExcelHeadDetail.ebc_Code);
        ebc_NameIndex = list.indexOf(ExcelHeadDetail.ebc_Name);
        assureCodeIndex = list.indexOf(ExcelHeadDetail.assureCode);
        customsCodeIndex = list.indexOf(ExcelHeadDetail.customsCode);
        portCodeIndex = list.indexOf(ExcelHeadDetail.portCode);
        ieDateIndex = list.indexOf(ExcelHeadDetail.ieDate);
        buyer_Id_NumberIndex = list.indexOf(ExcelHeadDetail.buyer_Id_Number);
        buyer_NameIndex = list.indexOf(ExcelHeadDetail.buyer_Name);
        buyerTelephoneIndex = list.indexOf(ExcelHeadDetail.buyerTelephone);
        consignee_AddressIndex = list.indexOf(ExcelHeadDetail.consignee_Address);
        freightIndex = list.indexOf(ExcelHeadDetail.freight);
        insuredFeeIndex = list.indexOf(ExcelHeadDetail.insuredFee);
        agentCodeIndex = list.indexOf(ExcelHeadDetail.agentCode);
        agentNameIndex = list.indexOf(ExcelHeadDetail.agentName);
        trafModeIndex = list.indexOf(ExcelHeadDetail.trafMode);
        trafNoIndex = list.indexOf(ExcelHeadDetail.trafNo);
        flightVoyageIndex = list.indexOf(ExcelHeadDetail.flightVoyage);
        billNoIndex = list.indexOf(ExcelHeadDetail.billNo);
        originCountryIndex = list.indexOf(ExcelHeadDetail.originCountry);
        startCountryIndex = list.indexOf(ExcelHeadDetail.startCountry);
        grossWeightIndex = list.indexOf(ExcelHeadDetail.grossWeight);
        netWeightIndex = list.indexOf(ExcelHeadDetail.netWeight);
        noteIndex = list.indexOf(ExcelHeadDetail.note);
        this.initMap();
    }

}
