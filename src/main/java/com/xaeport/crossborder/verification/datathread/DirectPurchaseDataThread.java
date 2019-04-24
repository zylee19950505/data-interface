package com.xaeport.crossborder.verification.datathread;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.verification.ProcesserThread;
import com.xaeport.crossborder.verification.dataload.CrossBorderLoader;
import com.xaeport.crossborder.verification.dataload.impl.*;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class DirectPurchaseDataThread implements Runnable {

    private Log log = LogFactory.getLog(this.getClass());

    private CrossBorderLoader crossBorderLoader;

    private String businessType;

    public DirectPurchaseDataThread() {
    }

    public DirectPurchaseDataThread(String businessType) {
        this.businessType = businessType;
    }

    @Override
    public void run() {
        String msg = null;
        switch (businessType) {
            case SystemConstants.T_IMP_ORDER:
                crossBorderLoader = new OrderDataLoader();
                msg = "订单";
                break;
            case SystemConstants.T_IMP_PAYMENT:
                crossBorderLoader = new PaymentDataloader();
                msg = "支付单";
                break;
            case SystemConstants.T_IMP_LOGISTICS:
                crossBorderLoader = new LogisticsDataLoader();
                msg = "运单";
                break;
            case SystemConstants.T_IMP_INVENTORY:
                crossBorderLoader = new InventoryDataLoader();
                msg = "清单";
                break;
        }
        while (true) {
            if (ProcesserThread.isProcessing()) {
                try {
                    Thread.sleep(3000);
                    log.debug("由于" + msg + "校验器处理数据中等待3秒");
                } catch (InterruptedException e) {
                    log.error(msg + "数据库数据加载器暂停时发生异常", e);
                }
                continue;
            }

            List<ImpCBHeadVer> impCBHeadVers = crossBorderLoader.loadingData();
            if (CollectionUtils.isEmpty(impCBHeadVers)) {
                try {
                    Thread.sleep(5000);
                    log.debug("由于未找到符合条件的" + msg + "数据中等待5秒");
                } catch (InterruptedException e) {
                    log.error("未获取到待逻辑校验的" + msg + "数据等待时发生异常", e);
                }
                continue;
            }
            ProcesserThread.getPendingQueue().addAll(impCBHeadVers);
        }
    }

}
