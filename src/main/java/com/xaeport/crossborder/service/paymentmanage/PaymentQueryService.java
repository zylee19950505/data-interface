package com.xaeport.crossborder.service.paymentmanage;

import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.ImpPaymentDetail;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.mapper.PaymentQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class PaymentQueryService {

    @Autowired
    PaymentQueryMapper paymentQueryMapper;

    //查询支付单查询数据
    public List<ImpPayment> queryPaymentQueryList(Map<String, String> paramMap) throws Exception {
        return paymentQueryMapper.queryPaymentQueryList(paramMap);
    }

    //查询支付单查询数据总数
    public Integer queryPaymentQueryCount(Map<String, String> paramMap) throws Exception {
        return paymentQueryMapper.queryPaymentQueryCount(paramMap);
    }

    public ImpPaymentDetail seePaymentDetail(Map<String, String> paramMap) {
        ImpPayment impPayment = paymentQueryMapper.seePaymentDetail(paramMap);
        Verify verify = paymentQueryMapper.queryVerifyDetail(paramMap);
        ImpPaymentDetail impPaymentDetail = new ImpPaymentDetail();
        impPaymentDetail.setImpPayment(impPayment);
        impPaymentDetail.setVerify(verify);
        return impPaymentDetail;
    }

    public ImpPayment getImpPaymentRec(Map<String, String> paramMap) {
        return paymentQueryMapper.getImpPaymentRec(paramMap);
    }

    @Transactional
    public Map<String, String> savePaymentDetail(
            LinkedHashMap<String, String> entryHead
    ) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveDetail(entryHead, rtnMap, "支付单查询-编辑-重报")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功，请到“支付单申报”处重新进行申报！");
        return rtnMap;

    }


    public boolean saveDetail(
            LinkedHashMap<String, String> entryHead,
            Map<String, String> rtnMap,
            String notes
    ) {
        if (CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        String entryHeadId = entryHead.get("entryhead_guid");
        if (!CollectionUtils.isEmpty(entryHead) && entryHead.size() > 1) {
            // 更新表头数据
            this.paymentQueryMapper.updateImpPayment(entryHead);
        }
        return false;
    }


    @Transactional
    public Map<String, String> saveLogicalDetail(
            LinkedHashMap<String, String> entryHead
    ) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveLogisticsLogicalDetail(entryHead, rtnMap, "支付单查询-编辑-重报")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功，请到“支付单申报”处确认是否校验通过！");
        return rtnMap;

    }


    public boolean saveLogisticsLogicalDetail(
            LinkedHashMap<String, String> entryHead,
            Map<String, String> rtnMap,
            String notes
    ) {
        if (CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        String guid = entryHead.get("entryhead_guid");
        if (!CollectionUtils.isEmpty(entryHead) && entryHead.size() > 1) {
            // 更新表头数据
            this.paymentQueryMapper.updateImpPaymentByLogic(entryHead);
        }
        this.paymentQueryMapper.deleteVerifyStatus(guid);
        return false;
    }

}
