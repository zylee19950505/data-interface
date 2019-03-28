package com.xaeport.crossborder.receipt.parser;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.receipt.DataFile;
import com.xaeport.crossborder.receipt.DataFolder;
import com.xaeport.crossborder.receipt.ThreadBase;
import com.xaeport.crossborder.tools.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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
                    this.log.debug("待处理文件夹没有文件，等待3秒");
                    sleep(3000);
                }
            } catch (Exception e) {
                log.error("预处理线程崩溃，3秒后重启", e);
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
        if (newfiles.length >= 1000) {
            this.log.debug("处理中文件夹数量大于1000，等待3秒");
            sleep(3000);
            return;
        }
        //文件移动至处理中目录
        this.moveToProcess(oldPath, newPath, oldfiles, files);
    }

    //文件移动至处理中目录
    private void moveToProcess(String oldPath, String newPath, String[] oldfiles, List<File> files) {
        List<String> list = Arrays.asList(oldfiles);
        if (list.size() > 100) {
            list = list.subList(0, 100);
        }
        String oldFilePath, newFilePath;
        File oldFileObj, newFileObj;
        for (int i = 0; i < list.size(); i++) {
            oldFilePath = oldPath + File.separator + list.get(i);
            newFilePath = newPath + File.separator + list.get(i);
            oldFileObj = new File(oldFilePath);
            newFileObj = new File(newFilePath);
            if (oldFileObj.isDirectory()) {
                this.moveFiles(oldFilePath, newFilePath, files);
                continue;
            }
            if (oldFileObj.isFile()) {
                //移动文件至另一个目录
//                oldFileObj.renameTo(newFileObj);
                try {
                    this.copyFileUsingFileChannels(oldFileObj, newFileObj);
                    oldFileObj.delete();
                } catch (Exception e) {
                    this.log.debug("复制入库文件错误");
                    e.printStackTrace();
                }
                this.log.info("移至处理中文件夹成功");
                files.add(newFileObj);
            }
        }
    }

    //报文加载至队列中（最大加载文件数1000）
    private void loadFileToProcess(List<File> files) {
//        File[] filesdata = files.toArray(new File[0]);
        // 缓存数据量是否超过1000，超过则等待3秒
        if (this.preprocessFilePathQueue.size() >= 1000) {
            this.log.debug("预处理队列数量大于1000，等待3秒");
            sleep(3000);
            return;
        } else if (files != null) {
            this.addPreprocessQueue(files);
            return;
        }
    }

    //文件移至预处理队列中
    private void addPreprocessQueue(List<File> files) {
        for (int i = 0; i < files.size(); i++) {
            if (!files.get(i).exists()) {
                this.log.debug(String.format("文件已不存在[fileName: %s]", files.get(i).getName()));
                continue;
            }
            if (files.get(i).length() == 0) {
                continue;
            }
            // 每次提取100个文件转移到“处理中”文件夹下，并将文件添加到文件缓存中等待处理
            boolean isContain = this.preprocessFilePathQueue.contains(files.get(i));
            if (!isContain && !FileUtils.isLocked(files.get(i))) {
                try {
                    DataFile dataFile = new DataFile();
                    dataFile.loadFile(files.get(i));
                    this.preprocessFilePathQueue.put(dataFile);
                    this.log.debug("加入预处理队列中");
                } catch (Exception e) {
                    this.log.error("装载文件错误,fileName=" + files.get(i).getName() + ",filePath=" + files.get(i).getAbsolutePath(), e);
                }
            }
        }
    }

//    private void addPreprocessQueue(List<File> files) {
//        for (int i = files.size() - 1; i >= 0; i--) {
//            if (!files.get(i).exists()) {
//                this.log.debug(String.format("文件已不存在[fileName: %s]", files.get(i).getName()));
//                continue;
//            }
//            if (files.get(i).length() == 0) {
//                continue;
//            }
//            // 每次提取100个文件转移到“处理中”文件夹下，并将文件添加到文件缓存中等待处理
//            boolean isContain = this.preprocessFilePathQueue.contains(files.get(i));
//            if (!isContain && !FileUtils.isLocked(files.get(i))) {
//                try {
//                    DataFile dataFile = new DataFile();
//                    dataFile.loadFile(files.get(i));
//                    this.preprocessFilePathQueue.put(dataFile);
//                    this.log.info("加入预处理队列中");
//                } catch (Exception e) {
//                    this.log.error("装载文件错误,fileName=" + files.get(i).getName() + ",filePath=" + files.get(i).getAbsolutePath(), e);
//                }
//            }
//            files.remove(files.get(i));
//        }
//
//        if (!files.isEmpty()) {
//            this.addPreprocessQueue(files);
//        }
//    }

    // 获取需要处理的报文文件
    public static DataFile getProcessingFileByCache() throws InterruptedException {
        return preprocessFilePathQueue.take();
    }

    public static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }


}
