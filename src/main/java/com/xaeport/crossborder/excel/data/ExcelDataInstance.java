package com.xaeport.crossborder.excel.data;


import com.xaeport.crossborder.excel.data.impl.*;

/*
 * Created by lzy on 2018/06/27
 */
public class ExcelDataInstance {
    public static ExcelData getExcelDataObject(String type) {
        ExcelData excelData = null;
        switch (type) {
            case "order": {
                excelData = new ExcelDataOrder();
                break;
            }
            case "payment": {
                excelData = new ExcelDataPayment();
                break;
            }
            case "waybill": {
                excelData = new ExcelDataWaybill();
                break;
            }
            case "waybillStatus": {
                excelData = new ExcelDataWaybillStatus();
                break;
            }
            case "detail": {
                excelData = new ExcelDataDetail();
                break;
            }
            case "enterInventory": {
                excelData = new ExcelDataEnterInstance();
                break;
            }

        }
        return excelData;
    }
}
