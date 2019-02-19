package com.xaeport.crossborder.receipt.parser;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.receipt.ThreadBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 报文预处理线程（单例）
 */
@Component
public class MessagePreprocessThread extends ThreadBase {

    private Log log = LogFactory.getLog(this.getClass());

    // 待处理文件缓存
    private static LinkedBlockingQueue<File> preprocessFilePathQueue = new LinkedBlockingQueue<>();

    @Autowired
    private AppConfiguration appConfiguration;

    @PostConstruct
    private void init() {
        // 初始化路径

        // 系统启动时优先加载“处理中”文件夹下的文件路径到缓存

    }

    @Override
    public void run() {
        while (true) {
            try {
                // 加载及转移报文文件（最大加载文件数1000）
                loadFileToProcess();
            } catch (Exception e) {
                log.error("消息预处理线程崩溃，3秒后重启", e);
                sleep(3 * 1000);
            }
            sleep(20);
        }
    }

    /**
     * 加载及转移报文文件（最大加载文件数1000）
     */
    private void loadFileToProcess() {
        // 缓存数据量是否超过1000，超过则等待3秒
        if (preprocessFilePathQueue.size() >= 1000) {
            sleep(3 * 1000);
            return;
        }
        // 待处理文件夹中是否存在文件，没有文件则等待3秒

        // 每次提取100个文件转移到“处理中”文件夹下，并将文件添加到文件缓存中等待处理


    }

    // 获取需要处理的报文文件
    public static File getProcessingFileByCache() throws InterruptedException {
        return preprocessFilePathQueue.take();
    }

}
