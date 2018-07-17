package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpLogisticsStatus;
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
    private int  logisticsStatusIndex; //物流运单状态";//head
    private int  logisticsTimeIndex; //物流状态时间";//head
    private int  noteIndex; //备注";//head

    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        this.getIndexValue(excelData.get(0));//初始化表头索引
        List<ImpLogisticsStatus> impLogisticsStatusList = this.getImpLogisticsStatusData(excelData);
        map.put("ImpLogisticsStatus", impLogisticsStatusList);
        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 封装ImpLogistics方法
     */
    public List<ImpLogisticsStatus> getImpLogisticsStatusData(List<List<String>> excelData) {
        List<ImpLogisticsStatus> listData = new ArrayList<>();
        ImpLogisticsStatus impLogisticsStatus;
        for (int i = 1; i < excelData.size(); i++) {
            impLogisticsStatus = new ImpLogisticsStatus();
            impLogisticsStatus.setLogistics_no(excelData.get(i).get(logisticsNoIndex));//物流运单编号
            impLogisticsStatus.setLogistics_code(excelData.get(i).get(logisticsCodeIndex));//物流企业代码
            impLogisticsStatus.setLogistics_name(excelData.get(i).get(logisticsNameIndex));//物流企业名称
            impLogisticsStatus.setLogistics_status(excelData.get(i).get(logisticsStatusIndex));//物流运单状态
            impLogisticsStatus.setNote(excelData.get(i).get(noteIndex));//备注

            //对时间进行格式化。
            String logisticsTime = excelData.get(i).get(logisticsTimeIndex);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                Date LogisticsTime = sdf.parse(logisticsTime);
                impLogisticsStatus.setLogistics_time(LogisticsTime);//物流状态时间
            } catch (ParseException e) {
                e.printStackTrace();
            }

            listData.add(impLogisticsStatus);
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
        logisticsStatusIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.logisticsStatus);
        logisticsTimeIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.logisticsTime);
        noteIndex = waybillStatusLists.indexOf(ExcelHeadWaybillStatus.note);

    }

}
