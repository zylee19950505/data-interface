package com.xaeport.crossborder.data.provider;

/**
 * Created by baozhe on 2017-8-03.
 */
public class BaseSQLProvider {

    public String splitJointIn(String keyName,String inValue){
        String values = inValue.replaceAll("\\)||=||'","").replace(",","','");
        String inSQL = keyName + " in ('"+values+"')";
        return inSQL;
    }

}
