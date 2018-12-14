package com.xaeport.crossborder.generated;
import com.xaeport.crossborder.data.mapper.DeliveryDeclareMapper;
import com.xaeport.crossborder.generated.thread.DeliveryDataThread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by zwf on 2018-7-24.
 * 报文生成启动器
 */
@Component
public class GeneratedAutoLauncher implements ApplicationListener<ApplicationReadyEvent> {

    private Log logger = LogFactory.getLog(this.getClass());
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private DeliveryDataThread deliveryDataThread;

    @Autowired
    DeliveryDeclareMapper deliveryDeclareMapper;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        this.logger.debug("入库明细单报文CEB711生成启动器初始化开始");

        deliveryDataThread = new DeliveryDataThread(this.deliveryDeclareMapper);
        executorService.execute(deliveryDataThread);

    }
}
