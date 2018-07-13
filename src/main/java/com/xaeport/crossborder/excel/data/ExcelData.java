package com.xaeport.crossborder.excel.data;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;

import java.util.List;
import java.util.Map;

/**
 * excel 数据封装接口
 * Created by lzy on 2018/06/28.
 */
public interface ExcelData {

    //获取最终封装数据
    Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception;

//    //封装订单表头数据
//    List<ImpOrderHead> getOrderHeadData(Map<String, List<String>> data);
//
//    //封装订单表体数据
//    List<ImpOrderBody> impOrderBodyData(List<String> entryLists, ImpOrderBody impOrderBody, List<ImpOrderBody> impOrderBodyData);

    //初始化索引值
    void getIndexValue(List<String> entryLists);

}
