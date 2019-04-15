package com.xaeport.crossborder.excel.validate.entrywaybillstatus;

import com.xaeport.crossborder.excel.headings.ExcelHeadWaybillStatus;
import com.xaeport.crossborder.excel.validate.ValidateBase;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateWaybillStatus extends ValidateBase {


    private Log log = LogFactory.getLog(this.getClass());
    private int logisticsNoIndex; //物流运单编号";//head
    private int logisticsCodeIndex; //物流企业代码";//head
    private int logisticsNameIndex; //物流企业名称";//head
   // private int logisticsStatusIndex; //物流运单状态";//head
    private int logisticsTimeIndex; //物流状态时间";//head
    private int noteIndex; //备注";//head

    private Map<Integer, String> indexMap = new HashMap<>();

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
        this.initMap();
    }

    public void initMap() {
        indexMap.put(logisticsNoIndex, "物流运单编号,60");
        indexMap.put(logisticsCodeIndex, "物流企业代码,18");
        indexMap.put(logisticsNameIndex, "物流企业名称,100");
       // indexMap.put(logisticsStatusIndex, "物流运单状态,1");
        indexMap.put(logisticsTimeIndex, "物流状态时间,14");
//        indexMap.put(noteIndex, "备注,1000");
    }

    public int CheckRowError(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {

        //导入excel模板非空和长度判断
        boolean isEmpty = this.CheckedEmptyAndLen(indexMap, error_num, cell, rowNum, cell_num);
        if (!isEmpty) {
            return -1;
        }


        return 0;
    }

    public int getUnitCode(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {
        return 0;
    }


    public int checkRowAmount(List list, Map<String, Object> map) {
        return 0;
    }

}
