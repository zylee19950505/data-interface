package com.xaeport.crossborder.service.receipt;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.ReceiptMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支付单回值解析
 * Created by Administrator on 2017/7/25.
 */
@Service
public class ReceiptService {
    private final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    ReceiptMapper receiptMapper;

    @Transactional(rollbackForClassName = "Exception")
    public boolean createReceipt(Map map, String refileName) {
        boolean flag = true;
        try {
            String type = (String) map.get("type");
            Map<String, List<List<Map<String, String>>>> receipt = (Map<String, List<List<Map<String, String>>>>) map.get("Receipt");
            switch (type) {
                case "CEB412"://支付单回执代码
                    this.createImpRecPayment(receipt, refileName);
//                    ImpRecPayment impRecPayment = this.createImpRecPayment(receipt, refileName);
//                    this.receiptMapper.createImpRecPayment(impRecPayment); //插入支付单状态表数据
//                    this.updateImpPaymentStatus(impRecPayment);    //更新支付单表状态
                    break;
            }
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("报文回执入库失败,文件名为:%s", refileName), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    /**
     * 插入支付单的数据
     *
     * @param receipt    回执数据
     * @param refileName 回执文件名
     * @throws Exception
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createImpRecPayment(Map<String, List<List<Map<String, String>>>> receipt, String refileName) throws Exception {
//        List<Map<String, String>> list = receipt.get("PaymentReturn").get(0);
        List<List<Map<String, String>>> list = receipt.get("PaymentReturn");
        if (!StringUtils.isEmpty(list)) {
            ImpRecPayment impRecPayment;
            for (int i = 0; i < list.size(); i++) {
                impRecPayment = new ImpRecPayment();
                impRecPayment.setId(IdUtils.getUUId());
                impRecPayment.setCrt_tm(new Date());
                impRecPayment.setUpd_tm(new Date());

                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("guid")) {
                        impRecPayment.setGuid(map.get("guid"));
                    }
                    if (map.containsKey("payCode")) {
                        impRecPayment.setPay_code(map.get("payCode"));
                    }
                    if (map.containsKey("payTransactionId")) {
                        impRecPayment.setPay_transaction_id(map.get("payTransactionId"));
                    }
                    if (map.containsKey("returnStatus")) {
                        impRecPayment.setReturn_status(map.get("returnStatus"));
                    }
                    if (map.containsKey("returnTime")) {
                        impRecPayment.setReturn_time(map.get("returnTime"));
                    }
                    if (map.containsKey("returnInfo")) {
                        impRecPayment.setReturn_info(map.get("returnInfo"));
                    }
                }
                this.receiptMapper.createImpRecPayment(impRecPayment); //插入支付单状态表数据
                this.updateImpPaymentStatus(impRecPayment);    //更新支付单表状态
            }
        }
//        ImpRecPayment impRecPayment = new ImpRecPayment();
//        for (Map<String, String> map : list) {
//            impRecPayment.setId(IdUtils.getUUId());
//            if(map.containsKey("guid")){
//                impRecPayment.setGuid(map.get("guid"));
//            }
//            if (map.containsKey("payCode")) {
//                impRecPayment.setPay_code(map.get("payCode"));
//            }
//            if (map.containsKey("payTransactionId")) {
//                impRecPayment.setPay_transaction_id(map.get("payTransactionId"));
//            }
//            if (map.containsKey("returnStatus")) {
//                impRecPayment.setReturn_status(map.get("returnStatus"));
//            }
//            if (map.containsKey("returnTime")) {
//                impRecPayment.setReturn_time(map.get("returnTime"));
//            }
//            if (map.containsKey("returnInfo")) {
//                impRecPayment.setReturn_info(map.get("returnInfo"));
//            }
//            impRecPayment.setCrt_tm(new Date());
//            impRecPayment.setUpd_tm(new Date());
//        }
//        return impRecPayment;
    }

    /**
     * 根据支付单回执更新支付单的状态
     *
     * @param impRecPayment 支付单回执数据对象
     */
    private void updateImpPaymentStatus(ImpRecPayment impRecPayment) throws Exception {
        ImpPayment impPayment = new ImpPayment();
        impPayment.setPay_code(impRecPayment.getPay_code());//支付企业的海关注册登记编号
        impPayment.setPay_transaction_id(impRecPayment.getPay_transaction_id());//支付企业唯一的支付流水号
        impPayment.setReturn_status(impRecPayment.getReturn_status());//操作结果（2电子口岸申报中/3发送海关成功/4发送海关失败/100海关退单/120海关入库）,若小于0 数字表示处理异常回执
        impPayment.setReturn_info(impRecPayment.getReturn_info());//备注（如:退单原因）
        impPayment.setReturn_time(impRecPayment.getReturn_time());//操作时间(格式：yyyyMMddHHmmssfff)
//        String result = impRecPayment.getReturn_status();

        this.receiptMapper.updateImpPayment(impPayment);  //更新支付单表中的回执状态

    }

}
