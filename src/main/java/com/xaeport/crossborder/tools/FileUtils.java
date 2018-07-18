package com.xaeport.crossborder.tools;

import com.xaeport.crossborder.event.SimpleCharsetListener;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;

/**
 * Created by baozhe on 2017-7-25.
 */
public class FileUtils {

    /**
     * 根据文件对象及内容生成物理文件
     *
     * @param file        文件对象
     * @param fileContent 文件内容
     * @throws IOException
     */
    public static void save(File file, byte[] fileContent) throws IOException {
        if (file == null) {
            throw new IOException("file not found");
        }
        if (file.isDirectory()) {
            throw new IOException("file is directory");
        }
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            FileChannel channel = outputStream.getChannel();
            FileLock lock = null;
            try {
                lock = channel.tryLock();
                outputStream.write(fileContent);
                outputStream.flush();
            } finally {
                if (lock != null)
                    lock.release();
            }
        }
    }

    /**
     * 检测报文编码
     *
     * @param data
     * @return
     */
    public static Charset detectCharset(byte[] data) {
        SimpleCharsetListener listener = new SimpleCharsetListener();
        UniversalDetector detector = new UniversalDetector(listener);
        detector.handleData(data, 0, data.length);
        detector.dataEnd();

        String charset = detector.getDetectedCharset();
        if (listener.getCharsetList().isEmpty())
            charset = "iso8859-1";

        return Charset.forName(charset);
    }

    public static boolean isLocked(File file) {
        boolean ret;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            FileLock fileLock = fileChannel.tryLock();
            ret = !fileLock.isValid();
            if (ret) {
                fileLock.release();
                fileChannel.close();
            }
            randomAccessFile.close();
        } catch (Exception ex) {
            try {
                if (randomAccessFile != null)
                    randomAccessFile.close();
            } catch (IOException e) {
            }
            ret = true;
        }
        return ret;
    }
}
