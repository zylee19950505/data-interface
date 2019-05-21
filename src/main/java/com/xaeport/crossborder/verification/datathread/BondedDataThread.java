package com.xaeport.crossborder.verification.datathread;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.verification.ProcesserThread;
import com.xaeport.crossborder.verification.dataload.BondLoader;
import com.xaeport.crossborder.verification.dataload.impl.*;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class BondedDataThread implements Runnable {

    private Log log = LogFactory.getLog(this.getClass());

    private BondLoader bondLoader;

    private String businessType;

    public BondedDataThread() {
    }

    public BondedDataThread(String businessType) {
        this.businessType = businessType;
    }

    @Override
    public void run() {
        String msg = null;
        switch (businessType) {
            case SystemConstants.T_IMP_BOND_ORDER:
                bondLoader = new BondOrderDataLoader();
                msg = "保税订单";
                break;
            case SystemConstants.T_IMP_BOND_INVEN:
                bondLoader = new BondInvenDataLoader();
                msg = "保税清单";
                break;
            case SystemConstants.T_BOND_INVT + SystemConstants.BSRQ:
                bondLoader = new EnterBondInvtDataLoader();
                msg = "入区核注清单";
                break;
            case SystemConstants.T_BOND_INVT + SystemConstants.BSCQ:
                bondLoader = new BondInvtDataLoader();
                msg = "出区核注清单";
                break;
            case SystemConstants.T_PASS_PORT + SystemConstants.BSRQ:
                bondLoader = new EnterPassPortDataLoader();
                msg = "入区保税核放单";
                break;
            case SystemConstants.T_PASS_PORT + SystemConstants.BSCQ:
                bondLoader = new ExitPassPortDataLoader();
                msg = "出区保税核放单";
                break;
        }
        while (true) {
            if (ProcesserThread.isBondProcessing()) {
                try {
                    Thread.sleep(3000);
                    log.debug("由于" + msg + "校验器处理数据中等待3秒");
                } catch (InterruptedException e) {
                    log.error(msg + "数据库数据加载器暂停时发生异常", e);
                }
                continue;
            }

            List<ImpBDHeadVer> impBDHeadVers = bondLoader.loadingData();
            if (CollectionUtils.isEmpty(impBDHeadVers)) {
                try {
                    Thread.sleep(5000);
                    log.debug("由于未找到符合条件的" + msg + "数据中等待5秒");
                } catch (InterruptedException e) {
                    log.error("未获取到待逻辑校验的" + msg + "数据等待时发生异常", e);
                }
                continue;
            }
            ProcesserThread.getPendingBondQueue().addAll(impBDHeadVers);
        }
    }

}
