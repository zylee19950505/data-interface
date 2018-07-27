package com.xaeport.crossborder.receipt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;

/**
 * Created by xcp on 2017/8/17.
 */
public class DataFile implements Closeable {
    private final Log logger = LogFactory.getLog(this.getClass());

    private String fileName;
    private byte[] fileData;
    private File rawFile;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public void save(String folder) throws IOException {
        this.save(new File(folder));
    }

    public void save(File folder) throws IOException {
        File file = new File(folder, this.getFileName());
        if (file.exists())
            file.delete();

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            FileChannel channel = outputStream.getChannel();
            FileLock lock = null;
            try {
                lock = channel.tryLock();
                outputStream.write(this.getFileData());
                outputStream.flush();
            } finally {
                if (lock != null)
                    lock.release();
            }
        }
    }


    public boolean loadFile(File file) throws IOException {
        this.rawFile = file;
        this.setFileName(file.getName());
        long freeMemory = (long) (Runtime.getRuntime().freeMemory() * 0.7);
        if (file.length() > freeMemory) {
            this.logger.debug("装载失败，文件大小与空余内存之比为 " + (file.length() / 1024 / 1024) + "/" + (freeMemory / 1024 / 1024) + " MB");
            return false;
        }

        byte[] data = Files.readAllBytes(file.toPath());
        this.setFileData(data);

        return true;
    }

    @Override
    public void close() throws IOException {
        this.fileData = null;
        this.fileName = null;
        this.rawFile = null;
    }

    public File getRawFile() {
        return this.rawFile;
    }
}
