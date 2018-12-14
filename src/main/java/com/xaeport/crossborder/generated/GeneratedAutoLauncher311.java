package com.xaeport.crossborder.generated;

import com.xaeport.crossborder.generated.thread.OrderMessageThread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by baozhe on 2017-8-08.
 * 报文生成启动器
 */
@Component
public class GeneratedAutoLauncher311 implements ApplicationListener<ApplicationReadyEvent> {

    private Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        OrderMessageThread manifestGenMsgThread = OrderMessageThread.getInstance();
        try {
            Thread thread = new Thread(manifestGenMsgThread);
            thread.start();
        } catch (Exception e) {
            this.logger.error("订单报文CEB311生成程序运行过程中发生异常", e);
        }
    }
}
