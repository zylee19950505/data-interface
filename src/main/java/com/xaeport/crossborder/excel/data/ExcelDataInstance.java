package com.xaeport.crossborder.excel.data;


import com.xaeport.crossborder.excel.data.impl.ExcelDataOrder;
import com.xaeport.crossborder.excel.data.impl.ExcelDataPayment;

/*
 * Created by lzy on 2018/06/27
 */
public class ExcelDataInstance {
    public static ExcelData getExcelDataObject(String type) {
        ExcelData excelData =null;
        switch (type) {
            case "order": {
                excelData = new ExcelDataOrder();
                break;
            }
            case "payment": {
                excelData = new ExcelDataPayment();
                break;
            }
        }
        return excelData;
    }
}
