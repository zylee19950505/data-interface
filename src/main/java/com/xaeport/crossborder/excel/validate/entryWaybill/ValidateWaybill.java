package com.xaeport.crossborder.excel.validate.entryWaybill;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.data.entity.UnitCode;
import com.xaeport.crossborder.data.mapper.SystemToolMapper;
import com.xaeport.crossborder.excel.headings.ExcelHeadWaybill;
import com.xaeport.crossborder.excel.validate.ValidateBase;
import com.xaeport.crossborder.excel.validate.ValidateUtil;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateWaybill extends ValidateBase{

    private Log log = LogFactory.getLog(this.getClass());
    private int  logisticsNoIndex; //物流运单编号";//head
    private int  logisticsCodeIndex; //物流企业代码";//head
    private int  logisticsNameIndex; //物流企业名称";//head
    private int  consigneeIndex; //收货人姓名";//head
    private int  consigneeTelephoneIndex; //收货人电话";//head
    private int  consigneeAddressIndex; //收件地址";//head
    private int  freightIndex; //运费";//head
    private int  insuredFeeIndex; //保价费";//head
    private int  grossWeightIndex; //毛重";//head
    private int  noteIndex; //备注";//head

    private Map<Integer, String> indexMap = new HashMap<>();

    public void initMap() {
        indexMap.put(logisticsNoIndex, "物流运单编号,60");
        indexMap.put(logisticsCodeIndex, "物流企业代码,18");
        indexMap.put(logisticsNameIndex, "物流企业名称,100");
        indexMap.put(consigneeIndex, "收货人姓名,100");
        indexMap.put(consigneeTelephoneIndex, "收货人电话,50");
        indexMap.put(consigneeAddressIndex, "收件地址,200");
        indexMap.put(freightIndex, "运费,19");//double
        indexMap.put(insuredFeeIndex, "保价费,19");//double
        indexMap.put(grossWeightIndex, "毛重,19");//double
        indexMap.put(noteIndex, "备注,1000");
    }

    public int CheckRowError(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {

        //导入excel模板非空和长度判断
        if (cell_num != noteIndex) {
            boolean isEmpty = this.CheckedEmptyAndLen(indexMap, error_num, cell, rowNum, cell_num);
            if (!isEmpty) {
                return -1;
            }
        }

        // 导入数据double类型判断
        if (cell_num == grossWeightIndex || cell_num == insuredFeeIndex || cell_num == freightIndex) {
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
//        if (cell_num == unitIndex) {
//            String message = indexMap.get(cell_num).split(",")[0];
//            if (!unitMap.containsKey(unitValue)) {
//                error_num.put("error", String.format(String.format("导入失败请修改后重新导入，第%%d行第%%d列。<%s>数据格式不对！", message), rowNum + 1, cell_num + 1));
//                flag = -1;
//            } else {
//                String unitCode = unitMap.get(unitValue);
//                cell.setCellValue(unitCode);
//                flag = 1;
//            }
//        }
        return flag;
    }


    /**
     * 初始化索引值
     *
     * @param list
     */
    public void getIndexValue(List<String> list) {
        logisticsNoIndex = list.indexOf(ExcelHeadWaybill.logisticsNo);
        logisticsCodeIndex = list.indexOf(ExcelHeadWaybill.logisticsCode);
        logisticsNameIndex = list.indexOf(ExcelHeadWaybill.logisticsName);
        consigneeIndex = list.indexOf(ExcelHeadWaybill.consignee);
        consigneeTelephoneIndex = list.indexOf(ExcelHeadWaybill.consigneeTelephone);
        consigneeAddressIndex = list.indexOf(ExcelHeadWaybill.consigneeAddress);
        freightIndex = list.indexOf(ExcelHeadWaybill.freight);
        insuredFeeIndex = list.indexOf(ExcelHeadWaybill.insuredFee);
        grossWeightIndex = list.indexOf(ExcelHeadWaybill.grossWeight);
        noteIndex = list.indexOf(ExcelHeadWaybill.note);

    }
    
}
