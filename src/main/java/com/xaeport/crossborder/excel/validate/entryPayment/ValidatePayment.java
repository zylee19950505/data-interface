package com.xaeport.crossborder.excel.validate.entryPayment;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.excel.headings.ExcelHeadPayment;
import com.xaeport.crossborder.excel.validate.ValidateBase;
import com.xaeport.crossborder.excel.validate.ValidateUtil;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zwf on 2018/07/10.
 */
public class ValidatePayment extends ValidateBase {
    //要校验字段索引
    private LoadData loadData = SpringUtils.getBean(LoadData.class);
    private int orderNoIndex; //订单编号";
    private int payCodeIndex; //支付企业代码";//QING
    private int payNameIndex; //支付企业名称";//QING
    private int payTransactionIdIndex; //支付交易编号";//list//QING
    private int ebpCodeIndex; //电商平台代码";//QING
    private int epbNameIndex; //电商平台名称";//QING
    private int amountPaidIndex; //支付金额";//list
    private int payerIdNumberIndex; //支付人证件号码";//list
    private int payerNameIndex; //支付人姓名";//list
    private int payTimeIndex; //支付时间";//list
    private int noteIndex; //备注";//list

    private Map<Integer, String> indexMap = new HashMap<>();

    public void getIndexValue(List<String> list) {
        orderNoIndex = list.indexOf(ExcelHeadPayment.orderNo);//订单编号
        payCodeIndex = list.indexOf(ExcelHeadPayment.payCode);//支付企业代码
        payNameIndex = list.indexOf(ExcelHeadPayment.payName);//支付企业名称
        payTransactionIdIndex = list.indexOf(ExcelHeadPayment.payTransactionId);//支付交易编号
        ebpCodeIndex = list.indexOf(ExcelHeadPayment.ebpCode);//电商平台代码
        epbNameIndex = list.indexOf(ExcelHeadPayment.epbName);//电商平台名称
        amountPaidIndex = list.indexOf(ExcelHeadPayment.amountPaid);//支付金额
        payerIdNumberIndex = list.indexOf(ExcelHeadPayment.payerIdNumber);//支付人证件号码
        payerNameIndex = list.indexOf(ExcelHeadPayment.payerName);//支付人姓名
        payTimeIndex = list.indexOf(ExcelHeadPayment.payTime);//支付时间
        noteIndex = list.indexOf(ExcelHeadPayment.note);//备注
        this.initMap();
    }

    public void initMap() {
        indexMap.put(orderNoIndex, "订单编号,60");
        indexMap.put(payCodeIndex, "支付企业代码,18");
        indexMap.put(payNameIndex, "支付企业名称,100");
        indexMap.put(payTransactionIdIndex, "支付交易编码,60");
        indexMap.put(ebpCodeIndex, "电商平台代码,18");
        indexMap.put(epbNameIndex, "电商平台名称,100");
        indexMap.put(amountPaidIndex, "支付金额,19");//double
        indexMap.put(payerIdNumberIndex, "支付人证件号码,18");
        indexMap.put(payerNameIndex, "支付人姓名,60");
        indexMap.put(payTimeIndex, "支付时间,14");

    }


    public int CheckRowError(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {
        //导入excel模板非空和长度判断
        boolean isEmpty = this.CheckedEmptyAndLen(indexMap, error_num, cell, rowNum, cell_num);
        if (!isEmpty) {
            return -1;
        }
        // 导入数据double类型判断
        if (cell_num == amountPaidIndex) {
            String message = indexMap.get(cell_num).split(",")[0];
            int flag = ValidateUtil.checkDoubleValue(cell);
            boolean checkNumberType = this.CheckNumberType(flag, error_num, rowNum, cell_num, message);
            if (!checkNumberType) {
                return -1;
            }
        }
        return 0;
    }


    @Override
    public int getUnitCode(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {
        return 0;
    }

    @Override
    public int checkRowAmount(List list, Map<String, Object> map) {
        return 0;
    }

}
