package com.xaeport.crossborder.generated;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.logstatus513.BaseLogisticsStatusXml;
import com.xaeport.crossborder.data.mapper.WaybillDeclareMapper;
import com.xaeport.crossborder.generated.thread.LogisticsStatusMessageThread;
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

        this.logger.debug("运单状态报文CEB513生成启动器初始化开始");

        logisticsMessageThread = new LogisticsStatusMessageThread(this.waybillDeclareMapper, this.appConfiguration, this.baseLogisticsStatusXml);
        executorService.execute(logisticsMessageThread);

    }
}