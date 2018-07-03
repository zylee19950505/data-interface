package com.xaeport.crossborder.event;

import java.util.EventListener;
import java.util.EventObject;

/**
 * 简单事件监听器，支持按 eventType 匹配触发事件处理程序
 * Created by shixu on 16-9-14.
 */
public abstract class SimpleEventListener<T extends EventObject> implements EventListener {
    private String eventType;
    private Object data;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public abstract void onEvent(T eventObject);

}
