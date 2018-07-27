package com.xaeport.crossborder.receipt;

import com.xaeport.crossborder.configuration.AppConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 封装文件夹访问
 * Created by Administrator on 2017/3/23.
 */
public class DataFolder {
    private final Log logger = LogFactory.getLog(this.getClass());
    private File baseFolder;

    public DataFolder(String baseFolder) {

        File folder = new File(baseFolder);

        if (folder.isAbsolute())
            this.baseFolder = folder;
        else {
            this.baseFolder = new File(AppConfiguration.getBaseFolder(), baseFolder);
        }

        if (!folder.exists())
            this.baseFolder.mkdirs();

    }

    public File make(String... paths) {
        Path path = Paths.get(this.baseFolder.getAbsolutePath(), paths);
        File folder = path.toFile();
        if (!folder.exists())
            folder.mkdirs();
        return folder;
    }

    public File cd(String subFolder) {
        this.baseFolder = this.make(subFolder);
        return this.baseFolder;
    }

    public File toFile() {
        return this.baseFolder;
    }

    public void save(File folder, DataFile dataFile) throws IOException {
        File file = new File(folder, dataFile.getFileName());
        if (file.exists())
            file.delete();

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            FileChannel channel = outputStream.getChannel();
            FileLock lock = null;
            try {
                lock = channel.tryLock();
                outputStream.write(dataFile.getFileData());
                outputStream.flush();
            } finally {
                if (lock != null)
                    lock.release();
            }
        }
    }
}
