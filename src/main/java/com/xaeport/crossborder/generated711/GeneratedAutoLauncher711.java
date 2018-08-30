//package com.xaeport.crossborder.generated711;
//
//import com.xaeport.crossborder.configuration.AppConfiguration;
//import com.xaeport.crossborder.convert621.BaseDetailDeclareXML;
//import com.xaeport.crossborder.convert711.BaseDeliveryDeclareXML;
//import com.xaeport.crossborder.data.mapper.DeliveryDeclareMapper;
//import com.xaeport.crossborder.data.mapper.DetailDeclareMapper;
//import com.xaeport.crossborder.generated621.thread.DetailDeclareMessageThread;
//import com.xaeport.crossborder.generated711.thread.DeliveryDeclareMessageThread;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//
///**
// * Created by zwf on 2018-7-24.
// * 报文生成启动器
// */
//@Component
//public class GeneratedAutoLauncher711 implements ApplicationListener<ApplicationReadyEvent> {
//
//    private Log logger = LogFactory.getLog(this.getClass());
//    private ExecutorService executorService = Executors.newCachedThreadPool();
//
//    private DeliveryDeclareMessageThread deliveryDeclareMessageThread;
//
//    @Autowired
//    DeliveryDeclareMapper deliveryDeclareMapper;
//
//    @Autowired
//    AppConfiguration appConfiguration;
//
//    @Autowired
//    BaseDeliveryDeclareXML baseDeliveryDeclareXML;
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//
//        this.logger.debug("入库明细单报文CEB711生成启动器初始化开始");
//
//        deliveryDeclareMessageThread = new DeliveryDeclareMessageThread(this.deliveryDeclareMapper, this.appConfiguration, this.baseDeliveryDeclareXML);
//        executorService.execute(deliveryDeclareMessageThread);
//
//    }
//}
