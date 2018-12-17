package com.xaeport.crossborder.generated;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.logistics511.BaseLogisticsXml;
import com.xaeport.crossborder.data.mapper.WaybillDeclareMapper;

import com.xaeport.crossborder.generated.thread.LogisticsMessageThread;
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
public class GeneratedAutoLauncher511  implements ApplicationListener<ApplicationReadyEvent> {

    private Log logger = LogFactory.getLog(this.getClass());
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private LogisticsMessageThread logisticsMessageThread;

    @Autowired
    WaybillDeclareMapper waybillDeclareMapper;

    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    BaseLogisticsXml baseLogisticsXml;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        this.logger.debug("运单报文CEB511生成启动器初始化开始");

        logisticsMessageThread = new LogisticsMessageThread(this.waybillDeclareMapper, this.appConfiguration, this.baseLogisticsXml);
        executorService.execute(logisticsMessageThread);

    }
}
