package com.xaeport.crossborder.verification.processor;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.inventory.inventoryBaseVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.inventory.inventoryRegularVerifyChain;
//import com.xaeport.crossborder.verification.chains.impl.logistics.logisticsBaseVerifyChain;
//import com.xaeport.crossborder.verification.chains.impl.logistics.logisticsRegularVerifyChain;
//import com.xaeport.crossborder.verification.chains.impl.order.orderBaseVerifyChain;
//import com.xaeport.crossborder.verification.chains.impl.order.orderRegularVerifyChain;
//import com.xaeport.crossborder.verification.chains.impl.payment.paymentBaseVerifyChain;
//import com.xaeport.crossborder.verification.chains.impl.payment.paymentRegularVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.order.orderBaseVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.order.orderRegularVerifyChain;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;
import com.xaeport.crossborder.verification.tools.VerificationResultUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 校验执行器作用：
 * 1. 使用通用数据结构，并构建校验链
 * 2. 使用校验链校验通用结构中的数据
 * 3. 通过执行校验方法返回校验结果
 */
public class VerificationProcessor {

    private Log logger = LogFactory.getLog(this.getClass());

    private final static List<CrossBorderVerifyChain> orderChainList = new ArrayList<CrossBorderVerifyChain>();// 订单校验连接
    private final static List<CrossBorderVerifyChain> paymentChainList = new ArrayList<CrossBorderVerifyChain>();// 支付单校验连接
    private final static List<CrossBorderVerifyChain> logisticsChainList = new ArrayList<CrossBorderVerifyChain>();// 运单校验连接
    private final static List<CrossBorderVerifyChain> inventoryChainList = new ArrayList<CrossBorderVerifyChain>();// 清单校验连接

    static {
        // 字段校验
        orderChainList.add(new orderBaseVerifyChain());
        // 正则校验
        orderChainList.add(new orderRegularVerifyChain());

//        // 字段校验
//        paymentChainList.add(new paymentBaseVerifyChain());
//        // 正则校验
//        paymentChainList.add(new paymentRegularVerifyChain());
//
//        // 字段校验
//        logisticsChainList.add(new logisticsBaseVerifyChain());
//        // 正则校验
//        logisticsChainList.add(new logisticsRegularVerifyChain());

        // 字段校验
        inventoryChainList.add(new inventoryBaseVerifyChain());
        // 正则校验
        inventoryChainList.add(new inventoryRegularVerifyChain());

    }


    /**
     * 执行校验
     *
     * @return
     */
    public VerificationResult excuteVerification(ImpCBHeadVer impCBHeadVer) {
        VerificationResult verificationResult = null;

        try {
            if (impCBHeadVer == null) {
                logger.debug("VerificationProcessor.impCBHeadVer 为NULL");
                return verificationResult;
            }

            List<CrossBorderVerifyChain> chainList = null;
            String entryType = impCBHeadVer.getBusiness_type();
            switch (entryType) {
                case SystemConstants.T_IMP_ORDER: {//订单校验
                    chainList = orderChainList;
                } ;
                break;
                case SystemConstants.T_IMP_PAYMENT: {//支付单校验
                    chainList = paymentChainList;
                } ;
                break;
                case SystemConstants.T_IMP_LOGISTICS: {//运单校验
                    chainList = logisticsChainList;
                }
                ;
                break;
                case SystemConstants.T_IMP_INVENTORY: {//清单校验
                    chainList = inventoryChainList;
                } ;
                break;
                default: {
                    VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 申报类型错误", "business_type");
                    return verificationResult;
                }
            }
            // 执行校验链
            for (CrossBorderVerifyChain vc : chainList) {

                // 使用校验链获取校验结果
                verificationResult = vc.executeVerification(impCBHeadVer);

                // 如果存在异常立即返回
                if (verificationResult.hasError()) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("执行校验链时发生异常", e);
        }
        return verificationResult;
    }

}

