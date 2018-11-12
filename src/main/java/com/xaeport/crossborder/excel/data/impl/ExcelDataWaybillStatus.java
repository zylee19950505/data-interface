package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.headings.ExcelHeadWaybillStatus;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelDataWaybillStatus implements ExcelData {

    private Log log = LogFactory.getLog(this.getClass());
    private int  logisticsNoIndex; //物流运单编号";//head
    private int  logisticsCodeIndex; //物流企业代码";//head
    private int  logisticsNameIndex; //物流企业名称";//head
    //private int  logisticsStatusIndex; //物流运单状态";//head
    private int  logisticsTimeIndex; //物流状态时间";//head
    private int  noteIndex; //备注";//head

    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        this.getIndexValue(excelData.get(0));//初始化表头索引
        List<ImpLogistics> impLogisticsStatusList = this.getImpLogisticsStatusData(excelData);
        map.put("ImpLogisticsStatus", impLogisticsStatusList);
        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 封装ImpLogistics方法
     */
    public List<ImpLogistics> getImpLogisticsStatusData(List<List<String>> excelData) {
        List<ImpLogistics> listData = new ArrayList<>();
        ImpLogistics impLogistics;
        for (int i = 1; i < excelData.size(); i++) {
            impLogistics = new ImpLogistics();
            impLogistics.setLogistics_no(excelData.get(i).get(logisticsNoIndex));//物流运单编号
            impLogistics.setLogistics_code(excelData.get(i).get(logisticsCodeIndex));//物流企业代码
            impLogistics.setLogistics_name(excelData.get(i).get(logisticsNameIndex));//物流企业名称
            //impLogisticsStatus.setLogistics_status(excelData.get(i).get(logisticsStatusIndex));//物流运单状态
            impLogistics.setNote(excelData.get(i).get(noteIndex));//备注
            impLogistics.setLogistics_time_char(excelData.get(i).get(logisticsTimeIndex));//备注

            listData.add(impLogistics);
        }
        return listData;
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
        //logisticsStatusIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.logisticsStatus);
        logisticsTimeIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.logisticsTime);
        noteIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.note);
    }

    protected String getString(String str) {
        if (!StringUtils.isEmpty(str)) {
            return str;
        } else {
            return "";
        }

    }

    protected String getDouble(String str) {
        DecimalFormat df = new DecimalFormat("0.00000");
        if (!StringUtils.isEmpty(str)) {
            return df.format(Double.parseDouble(str));
        } else {
            return "0";
        }
    }

}
