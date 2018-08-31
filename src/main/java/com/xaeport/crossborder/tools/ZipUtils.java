package com.xaeport.crossborder.tools;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.Application;
import com.xaeport.crossborder.configuration.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static final int BUFFER_SIZE = 2 * 1024;

//    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
//        long start = System.currentTimeMillis();
//        ZipOutputStream zos = null;
//        try {
//            zos = new ZipOutputStream(out);
//            File sourceFile = new File(srcDir);
//            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
//            long end = System.currentTimeMillis();
//            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
//        } catch (Exception e) {
//            throw new RuntimeException("zip error from ZipUtils", e);
//        } finally {
//            if (zos != null) {
//                try {
//                    zos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public static long toZip(List<File> srcFiles, OutputStream out) throws RuntimeException {
        long start = System.currentTimeMillis();
        long zipConsumeTime = 0;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
                srcFile.delete();
            }
            long end = System.currentTimeMillis();
            zipConsumeTime = end - start;
            System.out.println("压缩完成，耗时：" + zipConsumeTime + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return zipConsumeTime;
    }

//    public static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
//        byte[] buf = new byte[BUFFER_SIZE];
//        if (sourceFile.isFile()) {
//            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
//            zos.putNextEntry(new ZipEntry(name));
//            // copy文件到zip输出流中
//            int len;
//            FileInputStream in = new FileInputStream(sourceFile);
//            while ((len = in.read(buf)) != -1) {
//                zos.write(buf, 0, len);
//            }
//            // Complete the entry
//            zos.closeEntry();
//            in.close();
//        } else {
//            File[] listFiles = sourceFile.listFiles();
//            if (listFiles == null || listFiles.length == 0) {
//                // 需要保留原来的文件结构时,需要对空文件夹进行处理
//                if (KeepDirStructure) {
//                    // 空文件夹的处理
//                    zos.putNextEntry(new ZipEntry(name + "/"));
//                    // 没有文件，不需要文件的copy
//                    zos.closeEntry();
//                }
//            } else {
//                for (File file : listFiles) {
//                    // 判断是否需要保留原来的文件结构
//                    if (KeepDirStructure) {
//                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
//                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
//                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
//                    } else {
//                        compress(file, zos, file.getName(), KeepDirStructure);
//                    }
//                }
//            }
//        }
//    }


}
