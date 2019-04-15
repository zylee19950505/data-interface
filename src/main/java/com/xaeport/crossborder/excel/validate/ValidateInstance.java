package com.xaeport.crossborder.excel.validate;


import com.xaeport.crossborder.excel.validate.entrybondinven.ValidateBondInven;
import com.xaeport.crossborder.excel.validate.entrybondorder.ValidateBondOrder;
import com.xaeport.crossborder.excel.validate.entrydetail.ValidateDetail;
import com.xaeport.crossborder.excel.validate.entryenterinstance.ValidateEnterInstance;
import com.xaeport.crossborder.excel.validate.entryorder.ValidateOrder;
import com.xaeport.crossborder.excel.validate.entrypayment.ValidatePayment;
import com.xaeport.crossborder.excel.validate.entrywaybill.ValidateWaybill;
import com.xaeport.crossborder.excel.validate.entrywaybillstatus.ValidateWaybillStatus;


/**
 * Created by lzy on 2018/06/28.
 */
public class ValidateInstance {

    public static ValidateBase getValidateObject(String type) {

        ValidateBase validateBase = null;
        switch (type) {
            case "order": {
                validateBase = new ValidateOrder();
                break;
            }
            case "payment": {
                validateBase = new ValidatePayment();
                break;
            }
            case "waybill": {
                validateBase = new ValidateWaybill();
                break;
            }
            case "detail": {
                validateBase = new ValidateDetail();
                break;
            }
            case "waybillStatus": {
                validateBase = new ValidateWaybillStatus();
                break;
            }
            case "enterInventory": {
                validateBase = new ValidateEnterInstance();
                break;
            }
            case "bondInven": {
                validateBase = new ValidateBondInven();
                break;
            }
            case "bondorder": {
                validateBase = new ValidateBondOrder();
                break;
            }
        }
        return validateBase;
    }
}
