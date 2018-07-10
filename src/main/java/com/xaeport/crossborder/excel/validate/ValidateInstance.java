package com.xaeport.crossborder.excel.validate;


import com.xaeport.crossborder.excel.validate.entryOrder.ValidateOrder;

/**
 * Created by lzy on 2018/06/28.
 */
public class ValidateInstance {

    public static ValidateBase getValidateObject() {

        ValidateBase validateBase = new ValidateOrder();

        return validateBase;
    }
}
