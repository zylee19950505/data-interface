package com.xaeport.crossborder.data.entity;

import java.util.List;
import java.util.Map;

/**
 * datatable 实体
 * Created by xcp on 2017/9/4.
 */
public class DataList<T> {
    private String draw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data;

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
