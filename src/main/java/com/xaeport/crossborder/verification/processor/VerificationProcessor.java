package com.xaeport.crossborder.verification.processor;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.verification.chains.BondVerifyChain;
import com.xaeport.crossborder.verification.chains.CrossBorderVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.bondinven.bondinvenBaseChain;
import com.xaeport.crossborder.verification.chains.impl.bondinven.bondinvenRegularChain;
import com.xaeport.crossborder.verification.chains.impl.bondinvt.bondInvtBaseChain;
import com.xaeport.crossborder.verification.chains.impl.bondinvt.bondInvtRegularChain;
import com.xaeport.crossborder.verification.chains.impl.bondorder.bondOrderBaseChain;
import com.xaeport.crossborder.verification.chains.impl.bondorder.bondOrderRegularChain;
import com.xaeport.crossborder.verification.chains.impl.inventory.inventoryBaseVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.inventory.inventoryRegularVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.logistics.logisticsBaseVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.logistics.logisticsRegularVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.order.orderBaseVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.order.orderRegularVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.passport.passPortBaseChain;
import com.xaeport.crossborder.verification.chains.impl.passport.passPortRegularChain;
import com.xaeport.crossborder.verification.chains.impl.payment.paymentBaseVerifyChain;
import com.xaeport.crossborder.verification.chains.impl.payment.paymentRegularVerifyChain;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
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

    private final static List<BondVerifyChain> bondOrderChainList = new ArrayList<BondVerifyChain>();// 保税订单校验连接
    private final static List<BondVerifyChain> bondInvenChainList = new ArrayList<BondVerifyChain>();// 保税清单校验连接
    private final static List<BondVerifyChain> bondInvtChainList = new ArrayList<BondVerifyChain>();// 核注清单校验连接
    private final static List<BondVerifyChain> passPortChainList = new ArrayList<BondVerifyChain>();// 核放单校验连接

    static {
        //校验链：base为字段校验，regular为正则校验
        //直购模块
        //订单
        orderChainList.add(new orderBaseVerifyChain());
        orderChainList.add(new orderRegularVerifyChain());
        //支付单
        paymentChainList.add(new paymentBaseVerifyChain());
        paymentChainList.add(new paymentRegularVerifyChain());
        //运单
        logisticsChainList.add(new logisticsBaseVerifyChain());
        logisticsChainList.add(new logisticsRegularVerifyChain());
        //清单
        inventoryChainList.add(new inventoryBaseVerifyChain());
        inventoryChainList.add(new inventoryRegularVerifyChain());

        //保税模块
        //保税订单
        bondOrderChainList.add(new bondOrderBaseChain());
        bondOrderChainList.add(new bondOrderRegularChain());
        //保税清单
        bondInvenChainList.add(new bondinvenBaseChain());
        bondInvenChainList.add(new bondinvenRegularChain());
        //核注清单
        bondInvtChainList.add(new bondInvtBaseChain());
        bondInvtChainList.add(new bondInvtRegularChain());
        //核放单
        passPortChainList.add(new passPortBaseChain());
        passPortChainList.add(new passPortRegularChain());
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
                logger.debug("直购VerificationProcessor为NULL");
                return verificationResult;
            }

            List<CrossBorderVerifyChain> chainList = null;
            String entryType = impCBHeadVer.getBusiness_type();
            switch (entryType) {
                case SystemConstants.T_IMP_ORDER: {//订单校验
                    chainList = orderChainList;
                }
                break;
                case SystemConstants.T_IMP_PAYMENT: {//支付单校验
                    chainList = paymentChainList;
                }
                break;
                case SystemConstants.T_IMP_LOGISTICS: {//运单校验
                    chainList = logisticsChainList;
                }
                break;
                case SystemConstants.T_IMP_INVENTORY: {//清单校验
                    chainList = inventoryChainList;
                }
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
            logger.error("执行直购数据校验链时发生异常", e);
        }
        return verificationResult;
    }

    /**
     * 执行校验
     *
     * @return
     */
    public VerificationResult excuteBondVerification(ImpBDHeadVer impBDHeadVer) {
        VerificationResult verificationResult = null;

        try {
            if (impBDHeadVer == null) {
                logger.debug("保税VerificationProcessor为NULL");
                return verificationResult;
            }

            List<BondVerifyChain> chainList = null;
            String entryType = impBDHeadVer.getBusiness_type();
            switch (entryType) {
                //保税订单校验
                case SystemConstants.T_IMP_BOND_ORDER: {
                    chainList = bondOrderChainList;
                }
                break;
                //保税清单校验
                case SystemConstants.T_IMP_BOND_INVEN: {
                    chainList = bondInvenChainList;
                }
                break;
                //核注清单校验
                case SystemConstants.T_BOND_INVT: {
                    chainList = bondInvtChainList;
                }
                break;
                //核放单校验
                case SystemConstants.T_PASS_PORT: {
                    chainList = passPortChainList;
                }
                break;
                default: {
                    VerificationResultUtil.setEntryHeadErrorResult(verificationResult, "表头: 申报类型错误", "business_type");
                    return verificationResult;
                }
            }
            // 执行校验链
            for (BondVerifyChain vc : chainList) {

                // 使用校验链获取校验结果
                verificationResult = vc.executeBondVerification(impBDHeadVer);

                // 如果存在异常立即返回
                if (verificationResult.hasError()) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("执行保税数据校验链时发生异常", e);
        }
        return verificationResult;
    }


}

