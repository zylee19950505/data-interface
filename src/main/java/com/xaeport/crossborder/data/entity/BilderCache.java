package com.xaeport.crossborder.data.entity;


//创建清单信息缓存表
public class BilderCache {
    private String id;
    private String order_no;//订单编号
    private String dataStatus;//数据状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }
}
