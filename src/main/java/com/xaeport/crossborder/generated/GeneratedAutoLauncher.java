package com.xaeport.crossborder.generated;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.delivery711.BaseDeliveryXML;
import com.xaeport.crossborder.convert.inventory621.BaseDetailDeclareXML;
import com.xaeport.crossborder.convert.logistics511.BaseLogisticsXml;
import com.xaeport.crossborder.convert.logstatus513.BaseLogisticsStatusXml;
import com.xaeport.crossborder.convert.manifest.BaseManifestXML;
import com.xaeport.crossborder.convert.payment411.BasePaymentXml;
import com.xaeport.crossborder.data.mapper.*;
import com.xaeport.crossborder.generated.thread.*;
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
    private PaymentMessageThread paymentMessageThread;
    private OrderMessageThread manifestGenMsgThread;
    private LogisticsMessageThread logisticsMessageThread;
    private LogisticsStatusMessageThread logisticsStatusMessageThread;
    private DetailDeclareMessageThread detailDeclareMessageThread;
    private DeliveryMessageThread deliveryMessageThread;
    private ManifestManageThread manifestManageThread;


    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    BasePaymentXml basePaymentXml;
    @Autowired
    BaseLogisticsXml baseLogisticsXml;
    @Autowired
    BaseLogisticsStatusXml baseLogisticsStatusXml;
    @Autowired
    BaseDetailDeclareXML baseDetailDeclareXml;
    @Autowired
    BaseDeliveryXML baseDeliveryXml;
    @Autowired
    BaseManifestXML baseManifestXml;

    @Autowired
    DeliveryDeclareMapper deliveryDeclareMapper;
    @Autowired
    PaymentDeclareMapper paymentDeclareMapper;
    @Autowired
    WaybillDeclareMapper waybillDeclareMapper;
    @Autowired
    DetailDeclareMapper detailDeclareMapper;
    @Autowired
    ManifestManageMapper manifestManageMapper;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        this.logger.debug("入库明细单报文CEB711生成启动器初始化开始");
        deliveryDataThread = new DeliveryDataThread(this.deliveryDeclareMapper);
        executorService.execute(deliveryDataThread);

        this.logger.debug("订单报文CEB311生成启动器初始化开始");
        manifestGenMsgThread = OrderMessageThread.getInstance();
        executorService.execute(manifestGenMsgThread);

        this.logger.debug("支付单报文CEB411生成启动器初始化开始");
        paymentMessageThread = new PaymentMessageThread(this.paymentDeclareMapper, this.appConfiguration, this.basePaymentXml);
        executorService.execute(paymentMessageThread);

        this.logger.debug("运单报文CEB511生成启动器初始化开始");
        logisticsMessageThread = new LogisticsMessageThread(this.waybillDeclareMapper, this.appConfiguration, this.baseLogisticsXml);
        executorService.execute(logisticsMessageThread);

        this.logger.debug("运单状态报文CEB513生成启动器初始化开始");
        logisticsStatusMessageThread = new LogisticsStatusMessageThread(this.waybillDeclareMapper, this.appConfiguration, this.baseLogisticsStatusXml);
        executorService.execute(logisticsStatusMessageThread);

        this.logger.debug("清单报文CEB621生成启动器初始化开始");
        detailDeclareMessageThread = new DetailDeclareMessageThread(this.detailDeclareMapper, this.appConfiguration, this.baseDetailDeclareXml);
        executorService.execute(detailDeclareMessageThread);

        this.logger.debug("入库明细单报文CEB711生成启动器初始化开始");
        deliveryMessageThread = new DeliveryMessageThread(this.deliveryDeclareMapper, this.appConfiguration, this.baseDeliveryXml);
        executorService.execute(deliveryMessageThread);

        this.logger.debug("核放单报文CebManifest生成启动器初始化开始");
        manifestManageThread = new ManifestManageThread(this.manifestManageMapper, this.appConfiguration, this.baseManifestXml);
        executorService.execute(manifestManageThread);

    }
}
