package com.xaeport.crossborder.receipt.parser;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.receipt.DataFile;
import com.xaeport.crossborder.receipt.DataFolder;
import com.xaeport.crossborder.receipt.FileData;
import com.xaeport.crossborder.receipt.ThreadBase;
import com.xaeport.crossborder.tools.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 报文预处理线程（单例）
 */
@Component
public class MessagePreprocessThread extends ThreadBase {

    private Log log = LogFactory.getLog(this.getClass());
    private File processFolder;

    // 待处理文件队列，缓存
    public static LinkedBlockingQueue<DataFile> preprocessFilePathQueue = new LinkedBlockingQueue<>();

    @Autowired
    private AppConfiguration appConfiguration;

    public MessagePreprocessThread(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @PostConstruct
    private void init() {
        // 初始化路径
        this.log.info("初始化读取入库报文路径");
        // 系统启动时优先加载“处理中”文件夹下的文件路径到缓存
        this.processFolder = new DataFolder(this.appConfiguration.getProcessFolder()).toFile();
    }

    @Override
    public void run() {
        this.log.info("初始化预处理报文线程");
        while (true) {
            try {
                List<File> files = new ArrayList<>();
                File[] listFiles = new File(appConfiguration.getPreprocessFolder()).listFiles();
                if (listFiles != null) {
                    //移动文件位置，路径由preprocess至process
                    this.moveFiles(appConfiguration.getPreprocessFolder(), appConfiguration.getProcessFolder(), files);
                    //报文加载至队列中（最大加载文件数1000）
                    this.loadFileToProcess(files);
                } else {
                    // 待处理文件夹中是否存在文件，没有文件则等待3秒
                    this.log.info("待处理接受文件夹没有文件，等待三秒");
                    sleep(3000);
                }
            } catch (Exception e) {
                log.error("消息预处理线程崩溃，3秒后重启", e);
                sleep(3000);
            }
            sleep(20);
        }
    }

    //移动文件位置，路径由preprocess至process
    public void moveFiles(String oldPath, String newPath, List<File> files) {
        File newFile = new File(newPath);
        //判断字段是否为路径
        if (!newFile.isDirectory()) {
            return;
        }
        //判断路径是否存在
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        String[] oldfiles = new File(oldPath).list();
        String[] newfiles = newFile.list();
        //判断路径是否有效
        if (CollectionUtils.isEmpty(Arrays.asList(oldfiles))) {
            return;
        }
        //对process文件数量限制为1000条，超出则等待
        if (newfiles.length > 1000) {
            this.log.info("处理中文件夹大于1000条数据，等待三秒");
            sleep(3000);
            return;
        }
        List<String> list = Arrays.asList(oldfiles);
        if (list.size() >= 100) {
            list = list.subList(0, 99);
        }
        String oldFilePath, newFilePath;
        File oldFileObj, newFileObj;
        for (int i = 0; i < list.size(); i++) {
            oldFilePath = oldPath + File.separator + oldfiles[i];
            newFilePath = newPath + File.separator + oldfiles[i];
            oldFileObj = new File(oldFilePath);
            newFileObj = new File(newFilePath);
            if (oldFileObj.isDirectory()) {
                this.moveFiles(oldFilePath, newFilePath, files);
                continue;
            }
            if (oldFileObj.isFile()) {
                //移动文件至另一个目录
                oldFileObj.renameTo(newFileObj);
                this.log.info("移动文件成功");
                if (oldFileObj.exists()) {
                    oldFileObj.delete();
                }
            }
        }
        // 转移报文文件后，把需要解析的报文放入list中
        for (int i = 0; i < list.size(); i++) {
            newFilePath = newPath + File.separator + oldfiles[i];
            newFileObj = new File(newFilePath);
            files.add(newFileObj);
        }
    }

    //报文加载至队列中（最大加载文件数1000）
    private void loadFileToProcess(List<File> files) {
        File[] filesdata = (File[]) files.toArray(new File[0]);
        // 缓存数据量是否超过1000，超过则等待3秒
        if (this.preprocessFilePathQueue.size() >= 1000) {
            this.log.info("预处理队列中数据大于1000条，等待3秒");
            sleep(3000);
            return;
        } else {
            if (filesdata != null) {
                for (int i = 0; i < files.size(); i++) {
                    // 每次提取100个文件转移到“处理中”文件夹下，并将文件添加到文件缓存中等待处理
                    boolean isContain = this.preprocessFilePathQueue.contains(filesdata[i]);
                    if (!isContain && !FileUtils.isLocked(filesdata[i])) {
                        try {
                            DataFile dataFile = new DataFile();
                            dataFile.loadFile(filesdata[i]);
                            this.preprocessFilePathQueue.put(dataFile);
                            this.log.info("加入预处理队列当中");
                        } catch (Exception e) {
                            this.log.error("装载文件错误,fileName=" + filesdata[i].getName() + ",filePath=" + filesdata[i].getAbsolutePath(), e);
                        }
                    }
                }
                return;
            }
        }
    }

    // 获取需要处理的报文文件
    public static DataFile getProcessingFileByCache() throws InterruptedException {
        return preprocessFilePathQueue.take();
    }

}
