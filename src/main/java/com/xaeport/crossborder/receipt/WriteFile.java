package com.xaeport.crossborder.receipt;

//import com.xaeport.single.window.configuration.AppConfiguration;
//import com.xaeport.single.window.convert.parser.ExpParser;
//import com.xaeport.single.window.service.receipt.ReceiptService;
//import com.xaeport.single.window.tools.DateTools;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert411.parser.ExpParser;
import com.xaeport.crossborder.service.receipt.ReceiptService;
import com.xaeport.crossborder.tools.DateTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 解析回执报文
 * Created by zwj on 2017/8/8.
 */
public class WriteFile extends ThreadBase implements Closeable {
    private final Log logger = LogFactory.getLog(this.getClass());
    private boolean isClosed;
    private boolean isAlive;
    private DataFolder appBackupFolder;
    private DataFolder errorFolder;
    private AppConfiguration appConfiguration;
    private ExpParser expParser;
    private ReceiptService receiptService;

    public WriteFile(AppConfiguration appConfiguration, ExpParser expParser, ReceiptService receiptService) {
        this.appConfiguration = appConfiguration;
        this.expParser = expParser;
        this.receiptService = receiptService;
    }

    @Override
    public void run() {
        this.isAlive = true;
        this.logger.info("启动回执报文入库线程");
        try {
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
                if (this.process() == 0) {
                    this.sleep(100);
                }
            } catch (Exception e) {
                this.logger.error("========入库失败，将在5s后重启入库线程========", e);
                this.sleep(5000);
            }
        }
        this.logger.info("完成入库操作");
        this.isAlive = false;
    }

    private int process() throws InterruptedException, IOException {
        int dataProceed = 0;
        DataFile dataFile = FileData.readingQueues.take();

        if (dataFile != null && dataFile.getFileData().length > 0) {
            String refileName = dataFile.getFileName();//回值文件名
            //this.logger.debug(String.format("从队列中读取文件[fileName:%s]", refileName));
            Map map;
            try {
//                map = this.expParser.expParser(dataFile.getFileData());
//                boolean flag = this.receiptService.createReceipt(map, refileName);//插入数据
//                if (flag) {
//                    this.appBackup(dataFile);//回执文件备份
//                } else {
//                    this.appErrorBackup(dataFile);//错误文件备份
//                }
            } catch (Exception e) {
                this.appErrorBackup(dataFile);//错误文件备份
                this.logger.error(String.format("回执解析失败[%s]", refileName), e);
            }
            // logger.debug("开始删除文件,存在：" + dataFile.getRawFile().exists() + " 锁：" + FileUtils.isLocked(dataFile.getRawFile()));

            if (dataFile.getRawFile().exists()) dataFile.getRawFile().delete();
            //logger.debug("文件删除完毕" + dataFile.getRawFile().exists());
            if (!dataFile.getRawFile().exists()) {
                FileData.processingQueues.remove(dataFile.getRawFile());
            }

            dataProceed++;
            // this.logger.debug("总耗时---------------->" + FileData.timeMap.get("time"));
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


    @Override
    public void close() throws IOException {
        this.isClosed = true;
    }
}
