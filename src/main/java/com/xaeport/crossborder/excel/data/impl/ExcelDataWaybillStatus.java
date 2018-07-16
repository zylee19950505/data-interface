package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.headings.ExcelHeadWaybillStatus;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelDataWaybillStatus implements ExcelData {

    private Log log = LogFactory.getLog(this.getClass());
    private int  logisticsNoIndex; //物流运单编号";//head
    private int  logisticsCodeIndex; //物流企业代码";//head
    private int  logisticsNameIndex; //物流企业名称";//head
    private int  logisticsStatusIndex; //物流运单状态";//head
    private int  logisticsTimeIndex; //物流状态时间";//head
    private int  noteIndex; //备注";//head

    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
//        List<ImpOrderBody> impOrderBodyList = new ArrayList<>();
//        Map<String, List<String>> impOrderHeadMap = new LinkedHashMap<>();
//        ImpOrderBody impOrderBody;
//        Map<String, String> temporary = new HashMap<>();//用于存放临时计算值
//        this.getIndexValue(excelData.get(0));//初始化表头索引
//        for (int i = 1, length = excelData.size(); i < length; i++) {
//            impOrderBody = new ImpOrderBody();
//            impOrderHeadMap = this.getMergeData(excelData.get(i), impOrderHeadMap);
//            impOrderBodyList = this.impOrderBodyData(excelData.get(i), impOrderBody, impOrderBodyList);
//        }
//
//        List<ImpOrderHead> impOrderHeadList = this.getOrderHeadData(impOrderHeadMap);
//        map.put("ImpOrderHead", impOrderHeadList);
//        map.put("ImpOrderBody", impOrderBodyList);
        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 初始化索引值
     *
     * @param waybillStatusLists
     */
    public void getIndexValue(List<String> waybillStatusLists) {
        logisticsNoIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.logisticsNo);
        logisticsCodeIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.logisticsCode);
        logisticsNameIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.logisticsName);
        logisticsStatusIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.logisticsStatus);
        logisticsTimeIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.logisticsTime);
        noteIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.note);

    }

}
