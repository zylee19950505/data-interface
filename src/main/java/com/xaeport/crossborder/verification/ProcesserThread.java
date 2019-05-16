package com.xaeport.crossborder.verification;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.verification.datathread.*;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 数据处理线程
 */
public class ProcesserThread implements Runnable {

    private static ProcesserThread processerThread;

    private Log logger = LogFactory.getLog(this.getClass());

    protected static boolean isRunning = false;// 运行状态
    protected static boolean closing = false;// 关闭状态

    // 直购待处理数据队列
    private static BlockingQueue<ImpCBHeadVer> pendingQueue = new LinkedBlockingQueue<ImpCBHeadVer>();
    // 直购处理中数据队列
    private static BlockingQueue<ImpCBHeadVer> processQueue = new LinkedBlockingQueue<ImpCBHeadVer>();

    // 保税待处理数据队列
    private static BlockingQueue<ImpBDHeadVer> pendingBondQueue = new LinkedBlockingQueue<ImpBDHeadVer>();
    // 保税处理中数据队列
    private static BlockingQueue<ImpBDHeadVer> processBondQueue = new LinkedBlockingQueue<ImpBDHeadVer>();

    private ProcesserThread() {
        super();
    }

    public static ProcesserThread getInstance() {
        if (processerThread == null) {
            processerThread = new ProcesserThread();
        }
        return processerThread;
    }

    @Override
    public void run() {
        /*
         * 逻辑校验数据：
         * 1. 初始化数据加载器（负责加载数据）
         * 2. 初始化数据执行器（按照数据量构建多线程）
         */
        try {
            //直购业务数据类型
            List<String> CbBusinessType = new ArrayList<>();
            CbBusinessType.add(SystemConstants.T_IMP_ORDER);
            CbBusinessType.add(SystemConstants.T_IMP_PAYMENT);
            CbBusinessType.add(SystemConstants.T_IMP_LOGISTICS);
            CbBusinessType.add(SystemConstants.T_IMP_INVENTORY);

            //保税业务数据类型
            List<String> BdBusinessType = new ArrayList<>();
            BdBusinessType.add(SystemConstants.T_IMP_BOND_ORDER);
            BdBusinessType.add(SystemConstants.T_IMP_BOND_INVEN);
            BdBusinessType.add(SystemConstants.T_BOND_INVT + SystemConstants.BSRQ);
            BdBusinessType.add(SystemConstants.T_BOND_INVT);
            BdBusinessType.add(SystemConstants.T_PASS_PORT);

            // 初始化数据加载器（数据库数据加载器）
            CrossBorderDataFactory crossBorderDataFactory = new CrossBorderDataFactory();
            for (int i = 0; i < CbBusinessType.size(); i++) {
                Thread thread = crossBorderDataFactory.newThread(new DirectPurchaseDataThread(CbBusinessType.get(i)));
                thread.start();
            }

            for (int i = 0; i < BdBusinessType.size(); i++) {
                Thread thread = crossBorderDataFactory.newThread(new BondedDataThread(BdBusinessType.get(i)));
                thread.start();
            }

        } catch (Exception e) {
            this.logger.error("逻辑校验：数据加载过程中发生异常", e);
        }

        try {
            // 初始化校验线程，默认启动8个校验线程
            for (int i = 0; i < 4; i++) {
                Thread verificationThread = new Thread(new VerificationThread());
                verificationThread.start();
                Thread verificationBondThread = new Thread(new VerificationThreadBond());
                verificationBondThread.start();
            }
        } catch (Exception e) {
            this.logger.error("逻辑校验：数据校验过程中发生异常", e);
        }

    }


    public static BlockingQueue<ImpCBHeadVer> getPendingQueue() {
        return pendingQueue;
    }

    public static void setPendingQueue(BlockingQueue<ImpCBHeadVer> pendingQueue) {
        ProcesserThread.pendingQueue = pendingQueue;
    }

    public static BlockingQueue<ImpCBHeadVer> getProcessQueue() {
        return processQueue;
    }

    public static void setProcessQueue(BlockingQueue<ImpCBHeadVer> processQueue) {
        ProcesserThread.processQueue = processQueue;
    }

    public static synchronized ImpCBHeadVer takePendingQueueData() throws InterruptedException {
        return pendingQueue.take();
    }

    // 是否在处理数据, 即待处理队列及处理中队列均无数据时表示为fasle
    public static boolean isProcessing() {
        return !(getPendingQueue().isEmpty() && getProcessQueue().isEmpty());
    }

    // 是否有待处理数据 有:true,无:false
    public static boolean hasVerificationData() {
        return !getPendingQueue().isEmpty();
    }


    public static boolean isRunning() {
        return isRunning;
    }

    protected void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public static boolean isClosing() {
        return closing;
    }

    protected void setClosing(boolean isClosing) {
        closing = isClosing;
    }

    public boolean isCreate() {
        return processerThread != null;
    }


    public static BlockingQueue<ImpBDHeadVer> getPendingBondQueue() {
        return pendingBondQueue;
    }

    public static void setPendingBondQueue(BlockingQueue<ImpBDHeadVer> pendingBondQueue) {
        ProcesserThread.pendingBondQueue = pendingBondQueue;
    }

    public static BlockingQueue<ImpBDHeadVer> getProcessBondQueue() {
        return processBondQueue;
    }

    public static void setProcessBondQueue(BlockingQueue<ImpBDHeadVer> processBondQueue) {
        ProcesserThread.processBondQueue = processBondQueue;
    }

    public static synchronized ImpBDHeadVer takePendingQueueBondData() throws InterruptedException {
        return pendingBondQueue.take();
    }

    //是否在处理数据, 即待处理队列及处理中队列均无数据时表示为fasle
    public static boolean isBondProcessing() {
        return !(getPendingBondQueue().isEmpty() && getProcessBondQueue().isEmpty());
    }

    // 是否有待处理数据 有:true,无:false
    public static boolean hasVerificationBondData() {
        return !getPendingBondQueue().isEmpty();
    }

}
