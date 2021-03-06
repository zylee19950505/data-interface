package com.xaeport.crossborder.service.paymentmanage;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.data.mapper.PaymentImportMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.*;

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
    @Autowired
    EnterpriseMapper enterpriseMapper;

    /*
     * 支付单导入
     */
    @Transactional
    public int createOrderForm(Map<String, Object> excelMap, Users user) {
        int flag;
        try {
            String id = user.getId();
            flag = this.createImpOrderHead(excelMap, user);
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
    private int createImpOrderHead(Map<String, Object> excelMap, Users user) throws Exception {
        int flag = 0;
        Enterprise enterprise = enterpriseMapper.getEnterpriseDetail(user.getEnt_Id());
        ImpPayment impPayment = null;
        List<ImpPayment> ImpPayment = (List<ImpPayment>) excelMap.get("ImpPayment");
        for (ImpPayment anImpOrderHeadList : ImpPayment) {
            impPayment = this.impPaymentHeadData(anImpOrderHeadList, user, enterprise);
            //查找同一批编号不能重复代码， 查找方式为在impPayment中查找， 代码完成80%先留着，因为后期这里可能会出现bug
            //查询同一批订单号不能重复方法， 因为是插入一条查一条，所以导致的问题有
            //1.excal中已经插入了一半的数据后发现有重复这个时候数据未回滚可能会出现，继续插入的时候提示有重复。
            //解决方法：1.写数据回滚，如果一旦flag>0，就把数据回滚。
            //         2.在封装impPayment的时候就就对其进行判断操作。
            flag = this.paymentImportMapper.isRepeatOrderNo(impPayment);
            if (flag > 0) {
                return 1;
            }
            this.paymentImportMapper.insertImpPaymentHead(impPayment);//查询无订单号则插入ImpPayment数据
        }
        return flag;
    }

    /*
     * 查询有无重复订单号
     */
    public String getOrderNoCount(Map<String, Object> excelMap) throws Exception {
        int flag = 0;
        List<ImpPayment> list = (List<ImpPayment>) excelMap.get("ImpPayment");
        for (int i = 0; i < list.size(); i++) {
            ImpPayment impPayment = list.get(i);
            flag = this.paymentImportMapper.isRepeatOrderNo(impPayment);
            if (flag > 0) {
                return impPayment.getOrder_no();
            }
        }
        return "0";
    }

    /*
    * 查询支付单流水号是否重复
    * */
    public String getPaytransIdCount(Map<String, Object> excelMap) throws Exception {
        int flag = 0;
        List<ImpPayment> list = (List<ImpPayment>) excelMap.get("ImpPayment");
        for (int i = 0; i < list.size(); i++) {
            ImpPayment impPayment = list.get(i);
            flag = this.paymentImportMapper.isRepeatPaytransId(impPayment);
            if (flag > 0) {
                return impPayment.getPay_transaction_id();
            }
        }
        return "0";
    }

    /**
     * 表头自生成信息
     */
    private ImpPayment impPaymentHeadData(ImpPayment impPayment, Users user, Enterprise enterprise) throws Exception {
        impPayment.setGuid(IdUtils.getUUId());//企业系统生成36 位唯一序号（英文字母大写）
        impPayment.setApp_type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
        impPayment.setApp_status("2");//业务状态:1-暂存,2-申报,默认为2。
        impPayment.setCurrency("142");//币制
        impPayment.setPayer_id_type("1");//1-身份证,2-其它。限定为身份证，填写1。
        impPayment.setData_status("CBDS1");//设为已导入状态
        impPayment.setCrt_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//创建人
        impPayment.setCrt_tm(new Date());//创建时间
        impPayment.setUpd_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//更新人
        impPayment.setUpd_tm(new Date());//更新时间
        impPayment.setEnt_id(enterprise.getId());
        impPayment.setEnt_name(enterprise.getEnt_name());
        impPayment.setEnt_customs_code(enterprise.getCustoms_code());
        impPayment.setBusiness_type(SystemConstants.T_IMP_PAYMENT);
        return impPayment;
    }


}
