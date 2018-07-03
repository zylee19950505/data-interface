package com.xaeport.crossborder.event;

import java.util.EventObject;

/**
 * 简单事件对象
 * Created by shixu on 16-9-14.
 */
public class SimpleEventObject extends EventObject {
    private String eventType;
    private Object data;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public SimpleEventObject(Object source, String eventType) {
        super(source);
        this.eventType = eventType;
    }

    public SimpleEventObject(Object source, String eventType, Object data) {
        super(source);
        this.eventType = eventType;
        this.data = data;
    }

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
}
