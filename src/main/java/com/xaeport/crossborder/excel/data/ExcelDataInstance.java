package com.xaeport.crossborder.excel.data;


import com.xaeport.crossborder.excel.data.impl.ExcelDataOrder;

/*
 * Created by lzy on 2018/06/27
 */
public class ExcelDataInstance {
    public static ExcelData getExcelDataObject() {

        ExcelData excelData = new ExcelDataOrder();

        return excelData;
    }
}
