package com.xaeport.crossborder.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;

/**
 * 简单事件基类
 * Created by shixu on 16-9-14.
 */
public class SimpleEvent {
    private final Log logger = LogFactory.getLog(this.getClass());
    private HashSet<SimpleEventListener<SimpleEventObject>> listeners;

    public SimpleEvent() {
        this.listeners = new HashSet<>();
    }

    /**
     * 注册事件监听器
     *
     * @param listener
     */
    protected void addListener(SimpleEventListener<SimpleEventObject> listener) {
        this.listeners.add(listener);
    }

    /**
     * 触发特定事件
     *
     * @param eventType
     */
    protected void triggerEventListener(Object source, String eventType) {
        this.logger.debug("触发事件 " + eventType);
        this.listeners.stream()
                .filter(listener -> listener.getEventType().equals(eventType))
                .forEach(listener -> listener.onEvent(new SimpleEventObject(source, eventType, listener.getData())));
    }

    /**
     * 移除事件监听器
     *
     * @param listener
     */
    public void removeListener(SimpleEventListener<SimpleEventObject> listener) {
        if (this.listeners.contains(listener))
            this.listeners.remove(listener);
    }

    /**
     * 移除全部已注册的监听器
     */
    public void removeListener() {
        this.listeners.clear();
    }

    /**
     * 手动触发事件，用于测试
     *
     * @param type
     */
    public void fireEvent(String type) {
        this.triggerEventListener(this, type);
    }
}
