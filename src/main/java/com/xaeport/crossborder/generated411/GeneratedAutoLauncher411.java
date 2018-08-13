package com.xaeport.crossborder.generated411;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert411.BasePaymentXml;
import com.xaeport.crossborder.data.mapper.PaymentDeclareMapper;
import com.xaeport.crossborder.generated411.thread.PaymentMessageThread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by baozhe on 2017-8-08.
 * 报文生成启动器
 */
@Component
public class GeneratedAutoLauncher411 implements ApplicationListener<ApplicationReadyEvent> {
    private Log logger = LogFactory.getLog(this.getClass());
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private PaymentMessageThread paymentMessageThread;

    @Autowired
    PaymentDeclareMapper paymentDeclareMapper;
    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    BasePaymentXml basePaymentXml;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        this.logger.debug("支付单报文CEB411生成启动器初始化开始");

        paymentMessageThread = new PaymentMessageThread(this.paymentDeclareMapper, this.appConfiguration, this.basePaymentXml);
        executorService.execute(paymentMessageThread);


    }
}
