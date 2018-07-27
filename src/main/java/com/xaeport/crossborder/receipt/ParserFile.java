package com.xaeport.crossborder.receipt;


import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert411.parser.ExpParser;
import com.xaeport.crossborder.service.receipt.ReceiptService;
import com.xaeport.crossborder.tools.DateTools;
import com.xaeport.crossborder.tools.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.eclipse.jetty.util.ArrayQueue;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/17.
 */
public class ParserFile extends ThreadBase implements Runnable, Closeable {
    private final Log logger = LogFactory.getLog(this.getClass());
    private boolean isClosed;
    private boolean isAlive;
    private DataFolder appBackupFolder;
    private DataFolder errorFolder;
    private AppConfiguration appConfiguration;
    private ExpParser expParser;
    private File sourceFolder;
    private ReceiptService receiptService;

    public ParserFile(AppConfiguration appConfiguration, ExpParser expParser, ReceiptService receiptService) {
        this.appConfiguration = appConfiguration;
        this.expParser = expParser;
        this.receiptService = receiptService;
    }

    @Override
    public void run() {
        this.isAlive = true;
        this.logger.info("启动回执报文入库线程");
        try {
            this.sourceFolder = new DataFolder(this.appConfiguration.getReceiptFolder()).toFile();

            if (!StringUtils.isEmpty(this.appConfiguration.getBackupFolder())) {
                this.appBackupFolder = new DataFolder(this.appConfiguration.getBackupFolder());
                this.appBackupFolder.cd("receipt");
            }
            if (!StringUtils.isEmpty(this.appConfiguration.getErrorFolder())) {
                this.errorFolder = new DataFolder(this.appConfiguration.getErrorFolder());
            }
        } catch (Exception e) {
            this.isClosed = true;
            this.isAlive = false;
            return;
        }
        this.isClosed = false;
        while (!this.isClosed) {
            try {
                if (this.process() == 0)
                    this.sleep(100);
            } catch (Exception e) {
                this.logger.error("========入库失败，将在5s后重启入库线程========", e);
                this.sleep(5000);
            }
        }
        this.logger.info("完成入库操作");
        this.isAlive = false;
    }

    private int process() throws IOException, InterruptedException {
        int dataProceed = 0;
        File[] srcFiles = this.loadFiles(100);
        DataFile dataFile;
        for (File file : srcFiles) {
            dataFile = new DataFile();
            if ((file == null) || !file.exists())
                continue;
            if (file.length() == 0)
                continue;
            if (FileUtils.isLocked(file)) {
                continue;
            }
            this.logger.debug(String.format("从队列中读取文件[fileName:%s]", file.getName()));
            String refileName = file.getName();//回值文件名
            Map map;
            try {
                long startTime = System.currentTimeMillis();
                dataFile.loadFile(file);

                map = this.expParser.expParser(dataFile.getFileData());
                byte[] data = Files.readAllBytes(file.toPath());
                map = this.expParser.expParser(data);
//                boolean flag = receiptService.createReceipt(map, refileName);//插入数据
//                if (flag) {
//                    this.appBackup(dataFile);//回执文件备份
//                } else {
//                    this.appErrorBackup(dataFile);//错误文件备份
//                }
            } catch (DocumentException e) {
                this.appErrorBackup(dataFile);//错误文件备份
                this.logger.error(String.format("回执解析失败[%s]", file.getName()), e);
            }

            file.delete();
            dataProceed++;
            this.logger.debug("总耗时---------------->" + FileData.timeMap.get("time"));
        }
        return dataProceed;
    }

    private void appBackup(DataFile dataFile) throws IOException {
        File archiveFolder;
        if (!StringUtils.isEmpty(this.appBackupFolder)) {
            archiveFolder = this.appBackupFolder.make(DateTools.getShortDateString(new Date()));
            this.appBackupFolder.save(archiveFolder, dataFile);
        }
    }

    private void appErrorBackup(DataFile dataFile) throws IOException {
        File archiveFolder;
        if (!StringUtils.isEmpty(this.errorFolder)) {
            archiveFolder = this.errorFolder.make(DateTools.getShortDateString(new Date()));
            this.errorFolder.save(archiveFolder, dataFile);
        }
    }
    // 装载文件
    private File[] loadFiles(int batchSize) {
        ArrayQueue<File> fileQueue = new ArrayQueue<>(batchSize);
        ArrayQueue<File> folderQueue = new ArrayQueue<>();
        folderQueue.add(this.sourceFolder);

        File folder;
        File[] files;
        int fileCount = 0;
        while (!folderQueue.isEmpty() && (fileCount < batchSize)) {
            folder = folderQueue.poll();
            files = folder.listFiles();
            // 源文件夹不存在了就跳过
            if (files == null)
                continue;
            // 删除无用的空文件夹
            if ((files.length == 0) && !folder.equals(this.sourceFolder))
                folder.delete();

            for (File file : files) {
                if (file.isDirectory()) {
                    folderQueue.add(file);
                    continue;
                }

                // 跳过空文件，可能是写入方没写完
                if (file.length() == 0)
                    continue;

                fileQueue.add(file);
                if (++fileCount >= batchSize) {
                    break;
                }

            }
        }

        return fileQueue.toArray(new File[batchSize]);
    }

    @Override
    public void close() throws IOException {
        this.isClosed = true;
    }
}
