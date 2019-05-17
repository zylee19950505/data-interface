package com.xaeport.crossborder.service.logic;

import com.xaeport.crossborder.data.entity.ImpCrossBorderHead;
import com.xaeport.crossborder.data.mapper.BondLogicalMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.data.status.VerifyType;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BondLogicalService {
    private Log log = LogFactory.getLog(this.getClass());


    @Autowired
    BondLogicalMapper bondlogicalMapper;

    public List<ImpCrossBorderHead> getOrderLogicData(Map<String,String> map){
        return bondlogicalMapper.getOrderLogicData(map);
    }

    public List<ImpCrossBorderHead> getInventoryLogicData(Map<String,String> map){
        return bondlogicalMapper.getInventoryLogicData(map);
    }

    public List<ImpCrossBorderHead> getPaymentLogicData(Map<String,String> map){
        return bondlogicalMapper.getPaymentLogicData(map);
    }

    public List<ImpCrossBorderHead> getLogisticsLogicData(Map<String,String> map){
        return bondlogicalMapper.getLogisticsLogicData(map);
    }

    //删除订单逻辑校验未通过数据
    @Transactional
    public void deleteLogicalByOrder(String submitKeys, String entId) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("headGuid", submitKeys);
        paramMap.put("entId", entId);
        paramMap.put("type", VerifyType.LOGIC);
        paramMap.put("dataStatus", StatusCode.BSYDR);
        List<ImpCrossBorderHead> orderLogicList;
        try {
            orderLogicList = this.bondlogicalMapper.queryDeleteOrderByGuids(paramMap);
            if (CollectionUtils.isEmpty(orderLogicList)) return;
            String headGuid;
            for (int i = 0; i < orderLogicList.size(); i++) {
                headGuid = orderLogicList.get(i).getGuid();
                this.bondlogicalMapper.deleteImpOrderBody(headGuid);
                this.bondlogicalMapper.deleteImpOrderHead(headGuid);
                this.bondlogicalMapper.deleteLogicalVerifyStatus(headGuid);
            }
        } catch (Exception e) {
            this.log.error("删除订单逻辑校验数据失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception("删除订单逻辑校验数据失败");
        }
    }

    //删除清单逻辑校验未通过数据
    @Transactional
    public void deleteLogicalByInventory(String submitKeys, String entId) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("headGuid", submitKeys);
        paramMap.put("entId", entId);
        paramMap.put("type", VerifyType.LOGIC);
        paramMap.put("dataStatus", StatusCode.BSYDR);
        List<ImpCrossBorderHead> InventoryLogicList;
        try {
            InventoryLogicList = this.bondlogicalMapper.queryDeleteInventoryByGuids(paramMap);
            if (CollectionUtils.isEmpty(InventoryLogicList)) return;
            String headGuid;
            for (int i = 0; i < InventoryLogicList.size(); i++) {
                headGuid = InventoryLogicList.get(i).getGuid();
                this.bondlogicalMapper.deleteImpInventoryBody(headGuid);
                this.bondlogicalMapper.deleteImpInventoryHead(headGuid);
                this.bondlogicalMapper.deleteLogicalVerifyStatus(headGuid);
            }
        } catch (Exception e) {
            this.log.error("删除清单逻辑校验数据失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception("删除清单逻辑校验数据失败");
        }
    }

    //删除运单逻辑校验未通过数据
    @Transactional
    public void deleteLogicalByLogistics(String submitKeys, String entId) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("headGuid", submitKeys);
        paramMap.put("entId", entId);
        paramMap.put("type", VerifyType.LOGIC);
        paramMap.put("dataStatus", StatusCode.BSYDR);
        List<ImpCrossBorderHead> LogisticsLogicList;
        try {
            LogisticsLogicList = this.bondlogicalMapper.queryDeleteLogisticsByGuids(paramMap);
            if (CollectionUtils.isEmpty(LogisticsLogicList)) return;
            String headGuid;
            for (int i = 0; i < LogisticsLogicList.size(); i++) {
                headGuid = LogisticsLogicList.get(i).getGuid();
                this.bondlogicalMapper.deleteImpLogistics(headGuid);
                this.bondlogicalMapper.deleteLogicalVerifyStatus(headGuid);
            }
        } catch (Exception e) {
            this.log.error("删除运单逻辑校验数据失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception("删除运单逻辑校验数据失败");
        }
    }

    //删除支付单逻辑校验未通过数据
    @Transactional
    public void deleteLogicalByPayment(String submitKeys, String entId) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("headGuid", submitKeys);
        paramMap.put("entId", entId);
        paramMap.put("type", VerifyType.LOGIC);
        paramMap.put("dataStatus", StatusCode.BSYDR);
        List<ImpCrossBorderHead> PaymentLogicList;
        try {
            PaymentLogicList = this.bondlogicalMapper.queryDeletePaymentByGuids(paramMap);
            if (CollectionUtils.isEmpty(PaymentLogicList)) return;
            String headGuid;
            for (int i = 0; i < PaymentLogicList.size(); i++) {
                headGuid = PaymentLogicList.get(i).getGuid();
                this.bondlogicalMapper.deleteImpPayment(headGuid);
                this.bondlogicalMapper.deleteLogicalVerifyStatus(headGuid);
            }
        } catch (Exception e) {
            this.log.error("删除支付单逻辑校验数据失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception("删除支付单逻辑校验数据失败");
        }
    }

}
