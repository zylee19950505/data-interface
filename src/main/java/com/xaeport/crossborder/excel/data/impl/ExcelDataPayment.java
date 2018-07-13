package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.headings.ExcelHeadPayment;
import com.xaeport.crossborder.tools.DateTools;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付单模板数据
 * Created by zwf on 2018/07/10.
 */
public class ExcelDataPayment implements ExcelData {
    private Log log = LogFactory.getLog(this.getClass());
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


    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        this.getIndexValue(excelData.get(0));//初始化表头索引
        List<ImpPayment> orderList =  this.getEIntlMailDataToPayment(excelData);
        map.put("ImpPayment", orderList);

        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 封装ImpOrderHead方法
     */
    public List<ImpPayment> getEIntlMailDataToPayment(List<List<String>> excelData) {
        List<ImpPayment> listData = new ArrayList<>();
        ImpPayment impPayment;
        DecimalFormat df = new DecimalFormat("0.00000");
        for (int i = 1; i < excelData.size(); i++) {
            impPayment = new ImpPayment();
            impPayment.setOrder_no(excelData.get(i).get(orderNoIndex));//支付企业代码
            impPayment.setPay_code(excelData.get(i).get(payCodeIndex));//支付企业名称
            impPayment.setPay_name(excelData.get(i).get(payNameIndex));//支付交易编号
            impPayment.setPay_transaction_id(excelData.get(i).get(payTransactionIdIndex));//电商平台代码
            impPayment.setEbp_code(excelData.get(i).get(ebpCodeIndex));//电商企业名称
            impPayment.setEbp_name(excelData.get(i).get(epbNameIndex));//电商平台名称
            impPayment.setPayer_id_number(excelData.get(i).get(payerIdNumberIndex));//支付人证件号码
            impPayment.setPayer_name(excelData.get(i).get(payerNameIndex));//支付人姓名

            try {
                impPayment.setPay_time(DateTools.parseDateTimeString(excelData.get(i).get(payTimeIndex)));//支付时间
            } catch (ParseException e) {
                e.printStackTrace();
            }
            impPayment.setNote(excelData.get(i).get(noteIndex));//备注

            String amountPaid = excelData.get(i).get(amountPaidIndex);//支付金额
            if (!StringUtils.isEmpty(amountPaid)) {
                amountPaid = df.format(Double.parseDouble(amountPaid));
                impPayment.setAmount_paid(amountPaid);
            }
            listData.add(impPayment);
        }
        return listData;
    }
    /**
     * 初始化索引值
     *
     * @param paymentLists
     */
    public void getIndexValue(List<String> paymentLists) {
        orderNoIndex = paymentLists.indexOf(ExcelHeadPayment.orderNo);//订单编号
        payCodeIndex = paymentLists.indexOf(ExcelHeadPayment.payCode);//支付企业代码
        payNameIndex = paymentLists.indexOf(ExcelHeadPayment.payName);//支付企业名称
        payTransactionIdIndex = paymentLists.indexOf(ExcelHeadPayment.payTransactionId);//支付交易编号
        ebpCodeIndex = paymentLists.indexOf(ExcelHeadPayment.ebpCode);//电商平台代码
        epbNameIndex = paymentLists.indexOf(ExcelHeadPayment.epbName);//电商平台名称
        amountPaidIndex = paymentLists.indexOf(ExcelHeadPayment.amountPaid);//支付金额
        payerIdNumberIndex = paymentLists.indexOf(ExcelHeadPayment.payerIdNumber);//支付人证件号码
        payerNameIndex = paymentLists.indexOf(ExcelHeadPayment.payerName);//支付人姓名
        payTimeIndex = paymentLists.indexOf(ExcelHeadPayment.payTime);//支付时间
        noteIndex = paymentLists.indexOf(ExcelHeadPayment.note);//备注


    }



}
