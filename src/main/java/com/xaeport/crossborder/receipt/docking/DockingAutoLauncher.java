package com.xaeport.crossborder.receipt.docking;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.parser.ExpParser;
import com.xaeport.crossborder.service.receipt.DockingService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class DockingAutoLauncher implements ApplicationListener<ApplicationReadyEvent> {

    private final Log logger = LogFactory.getLog(this.getClass());
    private int threadCount = 1;
    private ExecutorService dockingExecutor = Executors.newCachedThreadPool();
    private ReadDoc readDoc;
    private WriteDoc writeDoc;

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    ExpParser expParser;
    @Autowired
    DockingService dockingService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.debug("-------------------系统准备完毕，开始解析对接报文");
        readDoc = new ReadDoc(this.appConfiguration);
        dockingExecutor.execute(readDoc);
        for (int i = 0; i < this.threadCount; i++) {
            writeDoc = new WriteDoc(this.appConfiguration, this.dockingService);
            dockingExecutor.execute(writeDoc);
        }

    }


}
