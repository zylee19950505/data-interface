package com.xaeport.crossborder.verification.DataThread;

import com.xaeport.crossborder.verification.ProcesserThread;
import com.xaeport.crossborder.verification.dataLoad.OrderLoader;
import com.xaeport.crossborder.verification.dataLoad.impl.OrderDataLoader;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class OrderDataThread implements Runnable {
    private Log logger = LogFactory.getLog(this.getClass());

    private OrderLoader orderLoader = new OrderDataLoader();

    @Override
    public void run() {
        while(true){
            if(ProcesserThread.isProcessing()){
                try {
                    Thread.sleep(3000);
                    logger.debug("由于订单校验器处理数据中等待3秒");
                } catch (InterruptedException e) {
                    logger.error("订单数据库数据加载器暂停时发生异常",e);
                }
                continue;
            }

            List<ImpCBHeadVer> impCBHeadVers = orderLoader.loadingData();
            if(CollectionUtils.isEmpty(impCBHeadVers)){
                try {
                    Thread.sleep(5000);
                    logger.debug("由于未找到符合条件的订单数据中等待5秒");
                } catch (InterruptedException e) {
                    logger.error("未获取到待逻辑校验的订单数据等待时发生异常",e);
                }
                continue;
            }
            ProcesserThread.getPendingQueue().addAll(impCBHeadVers);
        }

    }
}
