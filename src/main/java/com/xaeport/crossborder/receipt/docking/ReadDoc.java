package com.xaeport.crossborder.receipt.docking;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.receipt.DataFile;
import com.xaeport.crossborder.receipt.DataFolder;
import com.xaeport.crossborder.receipt.ThreadBase;
import com.xaeport.crossborder.tools.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ReadDoc extends ThreadBase implements Closeable {
    private final Log logger = LogFactory.getLog(this.getClass());
    private AppConfiguration appConfiguration;
    private boolean isClosed;
    private File dockingFolder;

    public ReadDoc(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Override
    public void run() {
        this.logger.info("-----------------初始化读取对接报文线程");
        this.dockingFolder = new DataFolder(this.appConfiguration.getDockingFolder()).toFile();
        this.isClosed = false;
        try {
            int times = 0;
            while (!isClosed) {
                if (!DocQueueData.readQueue.isEmpty()) {
                    sleep(500);
                } else {
                    this.filesPath();
                    sleep(500);
                }
            }
        } catch (InterruptedException e) {
            this.logger.error("读取对接报文失败，将在5s后重启入库线程", e);
            this.sleep(5000);
        }

    }

    // 装载文件
    private void filesPath() throws InterruptedException {
        BlockingQueue<File> folderQueue = new LinkedBlockingQueue<>();
        folderQueue.add(this.dockingFolder);
        File folder;
        File[] files;
        while (!folderQueue.isEmpty()) {
            folder = folderQueue.take();
            files = folder.listFiles();
            // 源文件夹不存在了就跳过
            if (files == null)
                continue;

            // 删除无用的空文件夹
            if ((files.length == 0) && !folder.equals(this.dockingFolder))
                folder.delete();

            for (File file : files) {
                if (file.isDirectory()) {
                    logger.debug(String.format("找到子文件夹[fileName:%s]", file.getName()));
                    folderQueue.put(file);
                    continue;
                }

                if (!file.exists()) {
                    logger.debug(String.format("文件已不存在[fileName: %s]", file.getName()));
                    continue;
                }
                // 跳过空文件，可能是写入方没写完
                if (file.length() == 0)
                    continue;

                boolean isContain = DocQueueData.processQueue.contains(file);
                if (!isContain && !FileUtils.isLocked(file)) {
                    //logger.debug(String.format("向队列中放入文件[fileName:%s]  %s", file.getName(), file.toString()));
                    try {
                        DataFile dataFile = new DataFile();
                        dataFile.loadFile(file);
                        DocQueueData.readQueue.put(dataFile);
                        DocQueueData.processQueue.put(file);
                    } catch (IOException e) {
                        this.logger.error("装载文件错误,fileName=" + file.getName() + ",filePath=" + file.getAbsolutePath(), e);
                    }
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        this.isClosed = true;
    }
}
