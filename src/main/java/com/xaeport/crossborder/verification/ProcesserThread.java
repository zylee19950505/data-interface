package com.xaeport.crossborder.verification;

import com.xaeport.crossborder.verification.DataThread.InventoryDataThread;
import com.xaeport.crossborder.verification.DataThread.LogisticsDataThread;
import com.xaeport.crossborder.verification.DataThread.OrderDataThread;
import com.xaeport.crossborder.verification.DataThread.PaymentDataThread;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    // 待处理数据队列
    private static BlockingQueue<ImpCBHeadVer> pendingQueue = new LinkedBlockingQueue<ImpCBHeadVer>();
    // 处理中数据队列
    private static BlockingQueue<ImpCBHeadVer> processQueue = new LinkedBlockingQueue<ImpCBHeadVer>();

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
            // 初始化数据加载器（数据库数据加载器）
            Thread inventoryDataThread = new Thread(new InventoryDataThread());
            inventoryDataThread.start();
            Thread orderDataThread = new Thread(new OrderDataThread());
            orderDataThread.start();
            Thread paymentDataThread = new Thread(new PaymentDataThread());
            paymentDataThread.start();
            Thread logisticsDataThread = new Thread(new LogisticsDataThread());
            logisticsDataThread.start();
        } catch (Exception e) {
            this.logger.error("数据加载过程中发生异常", e);
        }

        try {
            // 初始化校验线程，默认启动3个校验线程
            for (int i = 0; i < 3; i++) {
                Thread verificationThread1 = new Thread(new VerificationThread());
                verificationThread1.start();
                Thread verificationThread2 = new Thread(new VerificationThread());
                verificationThread2.start();
//                Thread verificationThread3 = new Thread(new VerificationThread());
//                verificationThread3.start();
//                Thread verificationThread4 = new Thread(new VerificationThread());
//                verificationThread4.start();
            }

        } catch (Exception e) {
            this.logger.error("数据校验过程中发生异常", e);
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

    /**
     * 是否在处理数据, 即待处理队列及处理中队列均无数据时表示为fasle
     *
     * @return
     */
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


}
