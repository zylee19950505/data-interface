package com.xaeport.crossborder.receipt;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 队列
 * Created by zwj on 2017/8/8.
 */
public class FileData {
    static BlockingQueue<DataFile> readingQueues = new LinkedBlockingQueue<>();
    static BlockingQueue<File> processingQueues = new LinkedBlockingQueue<>();
    public static Map timeMap = new HashMap();
    static Map<String, Integer> map = new HashMap();

    public static int getMap(String key) {
        return map.get(key);
    }

    public static void setMap(String key, int value) {
        map.put(key, value);
    }
}
