package com.xaeport.crossborder.service.parametermanage;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.PaymentImportMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/*
* zwf
* 2018/07/11
* 支付单service
* */
@Service
public class PaymentImportService {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    PaymentImportMapper paymentImportMapper;

    /*
     * 订单导入
     * @param importTime 申报时间
     */
    @Transactional
    public int createOrderForm(Map<String, Object> excelMap, String importTime, Users user) {
        int flag;
        try {
            String id = user.getId();
            flag = this.createImpOrderHead(excelMap, importTime, user);
        } catch (Exception e) {
            flag = 2;
            this.log.error("导入失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    /*
     * 创建ImpPayment信息
     */
    private int createImpOrderHead(Map<String, Object> excelMap, String importTime, Users user) throws Exception {
        int flag = 0;

        List<ImpPayment> ImpPayment = (List<com.xaeport.crossborder.data.entity.ImpPayment>) excelMap.get("ImpPayment");
        for (com.xaeport.crossborder.data.entity.ImpPayment anImpOrderHeadList : ImpPayment) {
            com.xaeport.crossborder.data.entity.ImpPayment impPayment = this.impPaymentHeadData(importTime, anImpOrderHeadList, user);
            flag = this.paymentImportMapper.isRepeatOrderNo(impPayment);
            if (flag > 0) {
                return 0;
            }
            this.paymentImportMapper.insertImpPaymentHead(impPayment);//查询无订单号则插入ImpPayment数据
        }
        return flag;
    }
    /*
     * 查询有无重复订单号
     */
    public int getOrderNoCount(Map<String, Object> excelMap) throws Exception{
        int flag = 0;
        List<ImpPayment> list = (List<ImpPayment>) excelMap.get("ImpPayment");
        for(int i=0;i<list.size();i++){
            ImpPayment impPayment = list.get(i);
            flag = this.paymentImportMapper.isRepeatOrderNo(impPayment);
            if (flag > 0) {
                return 1;
            }
        }
        return flag;
    }
    /**
     * 表头自生成信息
     */
    private ImpPayment impPaymentHeadData(String declareTime, ImpPayment impPayment, Users user) throws Exception {
        impPayment.setGuid(IdUtils.getUUId());//企业系统生成36 位唯一序号（英文字母大写）
        impPayment.setApp_type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
        impPayment.setApp_time(new Date());//企业报送时间。格式:YYYYMMDDhhmmss。
        impPayment.setApp_status("2");//业务状态:1-暂存,2-申报,默认为2。
        impPayment.setCurrency("142");//币制
        impPayment.setCrt_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//创建人
        impPayment.setCrt_tm(new Date());//创建时间
        impPayment.setUpd_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//更新人
        impPayment.setUpd_tm(new Date());//更新时间
        return impPayment;
    }
}
