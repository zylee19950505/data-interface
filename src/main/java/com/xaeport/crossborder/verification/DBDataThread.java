package com.xaeport.crossborder.verification;


import com.xaeport.crossborder.verification.dataLoad.DataLoader;
import com.xaeport.crossborder.verification.dataLoad.impl.DbDataLoader;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class DBDataThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());

    private DataLoader dataLoader = new DbDataLoader();

    @Override
    public void run() {
        while(true){
            if(ProcesserThread.isProcessing()){
                try {
                    Thread.sleep(3000);
                    logger.debug("由于校验器处理数据中等待3秒");
                } catch (InterruptedException e) {
                    logger.error("数据库数据加载器暂停时发生异常",e);
                }
                continue;
            }

            List<ImpCBHeadVer> impCBHeadVers = dataLoader.loadingData();
            if(CollectionUtils.isEmpty(impCBHeadVers)){
                try {
                    Thread.sleep(5000);
                    logger.debug("由于未找到符合条件数据中等待5秒");
                } catch (InterruptedException e) {
                    logger.error("未获取到待逻辑校验的数据等待时发生异常",e);
                }
                continue;
            }
            ProcesserThread.getPendingQueue().addAll(impCBHeadVers);
        }

    }
}
