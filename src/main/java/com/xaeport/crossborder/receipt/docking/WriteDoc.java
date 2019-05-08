package com.xaeport.crossborder.receipt.docking;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.xml.PackageXml;
import com.xaeport.crossborder.receipt.DataFile;
import com.xaeport.crossborder.receipt.DataFolder;
import com.xaeport.crossborder.receipt.ThreadBase;
import com.xaeport.crossborder.service.receipt.DockingService;
import com.xaeport.crossborder.tools.DateTools;
import com.xaeport.crossborder.tools.FileUtils;
import com.xaeport.crossborder.tools.xml.AnalysisXmlUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class WriteDoc extends ThreadBase implements Closeable {
    private final Log logger = LogFactory.getLog(this.getClass());
    private boolean isClosed;
    private boolean isAlive;
    private DataFolder dockBackupFolder;
    private DataFolder dockErrorFolder;
    private AppConfiguration appConfiguration;
    private DockingService dockingService;

    public WriteDoc(AppConfiguration appConfiguration,DockingService dockingService) {
        this.appConfiguration = appConfiguration;
        this.dockingService = dockingService;
    }

    @Override
    public void run() {
        this.isAlive = true;
        this.logger.info("---------------启动对接报文入库线程");
        try {
            if (!StringUtils.isEmpty(this.appConfiguration.getDockingBackupFolder())) {
                this.dockBackupFolder = new DataFolder(this.appConfiguration.getDockingBackupFolder());
            }
            if (!StringUtils.isEmpty(this.appConfiguration.getDockingErrorFolder())) {
                this.dockErrorFolder = new DataFolder(this.appConfiguration.getDockingErrorFolder());
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
        DataFile dataFile = DocQueueData.readQueue.take();

        if (dataFile != null && dataFile.getFileData().length > 0) {
            String refileName = dataFile.getFileName();//回值文件名
            this.logger.debug(String.format("从队列中读取文件[fileName:%s]", refileName));
            Map map;
            try {
                String xmlContent = new String(dataFile.getFileData(), "UTF-8").trim();
                List<PackageXml> packageXmlList = AnalysisXmlUtils.convertXmlToType(xmlContent, PackageXml.class);

                boolean flag = this.dockingService.crtDockingData(packageXmlList, refileName);//插入数据
                if (flag) {
                    this.appBackup(dataFile);//回执文件备份
                } else {
                    this.appErrorBackup(dataFile);//错误文件备份
                }
            } catch (Exception e) {
                this.appErrorBackup(dataFile);//错误文件备份
                this.logger.error(String.format("对接报文解析失败[%s]", refileName), e);
            }
            logger.debug("开始删除文件,存在：" + dataFile.getRawFile().exists() + " 锁：" + FileUtils.isLocked(dataFile.getRawFile()));

            if (dataFile.getRawFile().exists()) dataFile.getRawFile().delete();
            logger.debug("文件删除完毕" + dataFile.getRawFile().exists());
            if (!dataFile.getRawFile().exists()) {
                DocQueueData.processQueue.remove(dataFile.getRawFile());
            }

            dataProceed++;
            this.logger.debug("总耗时---------------->" + DocQueueData.timeMap.get("time"));
        }
        return dataProceed;
    }

    private void appBackup(DataFile dataFile) throws IOException {
        File archiveFolder;
        if (!StringUtils.isEmpty(this.dockBackupFolder)) {
            archiveFolder = this.dockBackupFolder.make(DateTools.getShortDateString(new Date()));
            this.dockBackupFolder.save(archiveFolder, dataFile);
        }
    }

    private void appErrorBackup(DataFile dataFile) throws IOException {
        File archiveFolder;
        if (!StringUtils.isEmpty(this.dockErrorFolder)) {
            archiveFolder = this.dockErrorFolder.make(DateTools.getShortDateString(new Date()));
            this.dockErrorFolder.save(archiveFolder, dataFile);
        }
    }

    @Override
    public void close() throws IOException {
        this.isClosed = true;
    }
}
