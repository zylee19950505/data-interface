package com.xaeport.crossborder.generated513;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert513.BaseLogisticsStatusXml;
import com.xaeport.crossborder.data.mapper.WaybillDeclareMapper;
import com.xaeport.crossborder.generated513.thread.LogisticsStatusMessageThread;
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
public class GeneratedAutoLauncher513 implements ApplicationListener<ApplicationReadyEvent> {

    private Log logger = LogFactory.getLog(this.getClass());
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private LogisticsStatusMessageThread logisticsMessageThread;

    @Autowired
    WaybillDeclareMapper waybillDeclareMapper;

    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    BaseLogisticsStatusXml baseLogisticsStatusXml;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        this.logger.debug("运单状态报文生成启动器初始化开始");

        logisticsMessageThread = new LogisticsStatusMessageThread(this.waybillDeclareMapper, this.appConfiguration, this.baseLogisticsStatusXml);
        executorService.execute(logisticsMessageThread);
//        PaymentMessageThread paymentMessageThread = PaymentMessageThread.getInstance();
//        try {
//            Thread thread = new Thread(paymentMessageThread);
//            thread.start();
//        } catch (Exception e) {
//            this.logger.error("支付单单报文生成程序运行过程中发生异常", e);
//        }

    }
}
