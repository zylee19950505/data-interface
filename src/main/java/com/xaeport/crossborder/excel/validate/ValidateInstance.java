package com.xaeport.crossborder.excel.validate;


import com.xaeport.crossborder.excel.validate.entryOrder.ValidateOrder;
import com.xaeport.crossborder.excel.validate.entryOrder.ValidatePayment;

/**
 * Created by lzy on 2018/06/28.
 */
public class ValidateInstance {

    public static ValidateBase getValidateObject(String type) {

        ValidateBase validateBase =null;
        switch (type) {
            case "order": {
                validateBase = new ValidateOrder();
                break;
            }
            case "payment": {
                validateBase = new ValidatePayment();
                break;
            }
        }
        return validateBase;
    }
}
