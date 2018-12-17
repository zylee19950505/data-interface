package com.xaeport.crossborder.generated;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.inventory621.BaseDetailDeclareXML;
import com.xaeport.crossborder.data.mapper.DetailDeclareMapper;
import com.xaeport.crossborder.generated.thread.DetailDeclareMessageThread;
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

        this.logger.debug("清单报文CEB621生成启动器初始化开始");

        detailDeclareMessageThread = new DetailDeclareMessageThread(this.detailDeclareMapper, this.appConfiguration, this.baseDetailDeclareXML);
        executorService.execute(detailDeclareMessageThread);

    }
}