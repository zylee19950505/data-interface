package com.xaeport.crossborder.generated;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.manifest.BaseManifestXML;
import com.xaeport.crossborder.data.mapper.ManifestManageMapper;
import com.xaeport.crossborder.generated.thread.ManifestManageThread;
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
public class GeneratedAutoLauncherManifest implements ApplicationListener<ApplicationReadyEvent> {

    private Log logger = LogFactory.getLog(this.getClass());
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private ManifestManageThread manifestManageThread;

    @Autowired
    ManifestManageMapper manifestManageMapper;

    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    BaseManifestXML baseManifestXML;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        this.logger.debug("核放单报文CebManifest生成启动器初始化开始");

        manifestManageThread = new ManifestManageThread(this.manifestManageMapper, this.appConfiguration, this.baseManifestXML);
        executorService.execute(manifestManageThread);

    }
}
