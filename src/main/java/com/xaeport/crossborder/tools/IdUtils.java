package com.xaeport.crossborder.tools;

import java.util.UUID;

/**
 * Created by xcp on 2017/8/3.
 */
public class IdUtils {

    public static String getUUId() {
        return UUID.randomUUID().toString().toUpperCase();
    }
}
