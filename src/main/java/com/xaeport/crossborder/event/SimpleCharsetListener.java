package com.xaeport.crossborder.event;

import org.mozilla.universalchardet.CharsetListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单字符集监听器，扩展了回收编码列表的方法
 * Created by Administrator on 2017/3/23.
 */
public class SimpleCharsetListener implements CharsetListener {
    private List<String> charsetList;

    public SimpleCharsetListener() {
        this.charsetList = new ArrayList<>();
    }

    public List<String> getCharsetList() {
        return charsetList;
    }

    @Override
    public void report(String charset) {
        this.charsetList.add(charset);
    }


}
