package com.xaeport.crossborder.receipt.parser;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.parser.ExpParserStock;
import com.xaeport.crossborder.receipt.DataFile;
import com.xaeport.crossborder.receipt.DataFolder;
import com.xaeport.crossborder.receipt.ThreadBase;
import com.xaeport.crossborder.service.receipt.StockMessageService;
import com.xaeport.crossborder.tools.DateTools;
import com.xaeport.crossborder.tools.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 报文解析线程（多例）
 */
@Component
@Scope("prototype")
public class MessageParseThread extends ThreadBase implements Cloneable {
    private final Log log = LogFactory.getLog(this.getClass());
    private boolean isClosed;
    private boolean isAlive;
    private AppConfiguration appConfiguration;
    private ExpParserStock stockExpParser;
    private StockMessageService stockMessageService;
    private DataFolder stockBackupFolder;
    private DataFolder stockErrorFolder;

    public MessageParseThread(AppConfiguration appConfiguration, ExpParserStock stockExpParser, StockMessageService stockMessageService) {
        this.appConfiguration = appConfiguration;
        this.stockExpParser = stockExpParser;
        this.stockMessageService = stockMessageService;
    }

    @Override
    public void run() {
        this.isAlive = true;
        this.log.info("启动入库报文解析线程");
        try {
            if (!StringUtils.isEmpty(this.appConfiguration.getStockbackupFolder())) {
                this.stockBackupFolder = new DataFolder(this.appConfiguration.getStockbackupFolder());
            }
            if (!StringUtils.isEmpty(this.appConfiguration.getStockerrorFolder())) {
                this.stockErrorFolder = new DataFolder(this.appConfiguration.getStockerrorFolder());
            }
        } catch (Exception e) {
            this.isClosed = true;
            this.isAlive = false;
            return;
        }
        this.isClosed = false;
        while (!this.isClosed) {
            try {
                if (this.processing() == 0) {
                    this.sleep(100);
                }
            } catch (Exception e) {
                this.log.error("========入库报文解析失败，将在5s后重启解析线程========", e);
                this.sleep(5000);
            }
        }
        this.log.info("完成入库报文写入操作");
        this.isAlive = false;
    }

    private int processing() throws InterruptedException, IOException {
        int dataProceed = 0;
        DataFile dataFile = MessagePreprocessThread.getProcessingFileByCache();

        if (dataFile != null && dataFile.getFileData().length > 0) {
            long start = System.currentTimeMillis();
            String refileName = dataFile.getFileName();//回值文件名
            this.log.info(String.format("从预处理队列中读取报文[fileName:%s]", refileName));
            Map map;
            try {
                map = this.stockExpParser.stockExpParser(dataFile.getFileData());
                boolean flag = this.stockMessageService.createStockData(map, refileName);//插入数据
                if (flag) {
                    this.stockBackup(dataFile);//回执文件备份
                } else {
                    this.stockErrorBackup(dataFile);//错误文件备份
                }
            } catch (Exception e) {
                this.stockErrorBackup(dataFile);//错误文件备份
                this.log.error(String.format("入库报文解析失败[%s]", refileName), e);
            }
            this.log.debug("开始删除入库报文,存在：" + dataFile.getRawFile().exists() + " 锁：" + FileUtils.isLocked(dataFile.getRawFile()));

            if (dataFile.getRawFile().exists()) dataFile.getRawFile().delete();
            this.log.debug("入库报文删除完毕" + dataFile.getRawFile().exists());
            if (!dataFile.getRawFile().exists()) {
                MessagePreprocessThread.preprocessFilePathQueue.remove(dataFile.getRawFile());
            }
            long end = System.currentTimeMillis();
            dataProceed++;
            this.log.debug("总耗时---------------->" + (end - start) + "ms");
        }
        return dataProceed;
    }

    private void stockBackup(DataFile dataFile) throws IOException {
        File archiveFolder;
        if (!StringUtils.isEmpty(this.stockBackupFolder)) {
            archiveFolder = this.stockBackupFolder.make(DateTools.getShortDateString(new Date()));
            this.stockBackupFolder.save(archiveFolder, dataFile);
        }
    }

    private void stockErrorBackup(DataFile dataFile) throws IOException {
        File archiveFolder;
        if (!StringUtils.isEmpty(this.stockErrorFolder)) {
            archiveFolder = this.stockErrorFolder.make(DateTools.getShortDateString(new Date()));
            this.stockErrorFolder.save(archiveFolder, dataFile);
        }
    }

    public void close() throws IOException {
        this.isClosed = true;
    }
}
