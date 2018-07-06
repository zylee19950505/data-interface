package com.xaeport.crossborder.excel.utils;

import org.springframework.util.StringUtils;

/**
 * 字符串截取
 * Created by lzy on 2018/06/27.
 */
public class SubStr {

    public static String subStrUtils(String str, int len) {
        String resultStr;
        if (!StringUtils.isEmpty(str)) {
            resultStr = str.substring(0, str.length() < len ? str.length() : len);
        } else {
            resultStr = "";
        }
        return resultStr;
    }
}
