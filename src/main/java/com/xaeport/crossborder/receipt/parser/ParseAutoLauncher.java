package com.xaeport.crossborder.receipt.parser;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.receipt.ReadFile;
import com.xaeport.crossborder.receipt.WriteFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 文件解析启动器
 * 1. 启动文件加载程序
 * 2. 启动文件解析服务
 */
@Component
public class ParseAutoLauncher implements ApplicationListener<ApplicationReadyEvent> {
    private final Log log = LogFactory.getLog(this.getClass());

    // 线程池
    private int threadCount = 4;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private ReadFile readFile;
    private WriteFile writeFile;

    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private MessagePreprocessThread msgPreprocessThread;// 报文预处理线程

    @Autowired
    private MessageParseThread msgParseThread;// 报文解析线程

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("系统准备完毕，开始解析报文");

//        executorService.submit(msgPreprocessThread);

        // 使用多线程解析报文
//        for (int i = 0; i < threadCount; i++) {
//            executorService.submit(msgParseThread);
//        }

    }

}
