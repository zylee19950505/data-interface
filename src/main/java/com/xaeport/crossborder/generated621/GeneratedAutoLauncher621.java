package com.xaeport.crossborder.generated621;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert621.BaseDetailDeclareXML;
import com.xaeport.crossborder.data.mapper.DetailDeclareMapper;
import com.xaeport.crossborder.generated621.thread.DetailDeclareMessageThread;
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
public class GeneratedAutoLauncher621 implements ApplicationListener<ApplicationReadyEvent> {

    private Log logger = LogFactory.getLog(this.getClass());
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private DetailDeclareMessageThread detailDeclareMessageThread;

    @Autowired
    DetailDeclareMapper detailDeclareMapper;

    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    BaseDetailDeclareXML baseDetailDeclareXML;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        this.logger.debug("清单状态报文生成启动器初始化开始");

        detailDeclareMessageThread = new DetailDeclareMessageThread(this.detailDeclareMapper, this.appConfiguration, this.baseDetailDeclareXML);
        executorService.execute(detailDeclareMessageThread);
//        PaymentMessageThread paymentMessageThread = PaymentMessageThread.getInstance();
//        try {
//            Thread thread = new Thread(paymentMessageThread);
//            thread.start();
//        } catch (Exception e) {
//            this.logger.error("支付单单报文生成程序运行过程中发生异常", e);
//        }

    }
}