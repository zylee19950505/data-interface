package com.xaeport.crossborder.verification;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 逻辑校验启动器
 */
@Component
public class VerificationAutoLauncher implements ApplicationListener<ApplicationReadyEvent> {

    private Log logger = LogFactory.getLog(this.getClass());

    private boolean isStart = true;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.logger.debug("逻辑校验程序初始化开始");

//         初始化处理器线程
        ProcesserThread processerThread = ProcesserThread.getInstance();

        try {
            Thread thread = new Thread(processerThread);
            thread.start();
        } catch (Exception e) {
            this.logger.error("逻辑校验程序运行过程中发生异常", e);
        }
    }
}
