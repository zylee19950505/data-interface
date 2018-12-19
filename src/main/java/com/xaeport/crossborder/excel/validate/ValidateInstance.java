package com.xaeport.crossborder.excel.validate;


import com.xaeport.crossborder.excel.validate.entryDetail.ValidateDetail;
import com.xaeport.crossborder.excel.validate.entryEnterInstance.ValidateEnterInstance;
import com.xaeport.crossborder.excel.validate.entryOrder.ValidateOrder;
import com.xaeport.crossborder.excel.validate.entryPayment.ValidatePayment;
import com.xaeport.crossborder.excel.validate.entryWaybill.ValidateWaybill;
import com.xaeport.crossborder.excel.validate.entryWaybillStatus.ValidateWaybillStatus;

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
        }
        return validateBase;
    }
}
