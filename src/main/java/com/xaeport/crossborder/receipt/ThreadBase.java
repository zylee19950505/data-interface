package com.xaeport.crossborder.receipt;

import com.xaeport.crossborder.event.SimpleEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;

/**
 * Created by shixu on 16-12-6.
 */
public abstract class ThreadBase extends SimpleEvent implements Runnable {
    private final Log logger = LogFactory.getLog(this.getClass());
    private HashMap<String, Long> timeSlot = new HashMap<>();

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // just let it go~
        }
    }

    public void timeStart(String key) {
        this.timeSlot.put(key, System.currentTimeMillis());
    }

    public int timesUp(String key) {
        if (!this.timeSlot.containsKey(key)) {
            this.timeStart(key);
            return 0;
        }

        return (int) (System.currentTimeMillis() - this.timeSlot.get(key));
    }

}
