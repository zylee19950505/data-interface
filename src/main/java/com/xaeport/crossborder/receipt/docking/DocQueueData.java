package com.xaeport.crossborder.receipt.docking;

import com.xaeport.crossborder.receipt.DataFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DocQueueData {
    static BlockingQueue<DataFile> readQueue = new LinkedBlockingQueue<>();
    static BlockingQueue<File> processQueue = new LinkedBlockingQueue<>();
    public static Map timeMap = new HashMap();
    static Map<String, Integer> map = new HashMap();

    public static int getMap(String key) {
        return map.get(key);
    }

    public static void setMap(String key, int value) {
        map.put(key, value);
    }
}
