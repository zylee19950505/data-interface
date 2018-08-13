package com.xaeport.crossborder.receipt;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.parser.ExpParser;
import com.xaeport.crossborder.service.receipt.ReceiptService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 启动解析回执线程
 * Created by Administrator on 2017/08/02.
 */
@Component
public class AutoLauncher implements ApplicationListener<ApplicationReadyEvent> {
    private final Log logger = LogFactory.getLog(this.getClass());
    //TODO 暂时为单线程，后期优化可以改为多线程

    private int threadCount = 1;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private ReadFile readFile;
    private WriteFile writeFile;

    private ParserFile parserFile;

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    ExpParser expParser;
    @Autowired
    ReceiptService receiptService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.debug("系统准备完毕，开始解析文件");
        this.setDeclareStatus();
        readFile = new ReadFile(this.appConfiguration);
        executorService.execute(readFile);
        for (int i = 0; i < this.threadCount; i++) {
            writeFile = new WriteFile(this.appConfiguration, this.expParser, this.receiptService);
            executorService.execute(writeFile);
        }

    }

    private void setDeclareStatus() {
//        FileData.setMap(StatusCode.ZXRKCG, 1);
//        FileData.setMap(StatusCode.ZXRKSB, 2);
//        FileData.setMap(StatusCode.FWHGCG, 3);
//        FileData.setMap(StatusCode.FWHGSB, 4);
//        FileData.setMap(StatusCode.XZXGZJCG, 5);
//        FileData.setMap(StatusCode.XZXGZJSB, 6);
//
//        FileData.setMap(StatusCode.YXZJ, 7);
//        FileData.setMap(StatusCode.BYXZJ, 8);
//        FileData.setMap(StatusCode.TD, 9);
//        FileData.setMap(StatusCode.ZPHBG, 10);
//        FileData.setMap(StatusCode.ZRGSH, 11);
//        FileData.setMap(StatusCode.TJZMDJ, 12);
//
//        FileData.setMap(StatusCode.CY, 13);
//        FileData.setMap(StatusCode.KL, 14);
//        FileData.setMap(StatusCode.MS, 15);
//        FileData.setMap(StatusCode.XD, 16);
//        FileData.setMap(StatusCode.SD, 17);
//        FileData.setMap(StatusCode.SHTG, 18);
//
//        FileData.setMap(StatusCode.FX, 19);
//        FileData.setMap(StatusCode.CYHBSFX, 20);
//        FileData.setMap(StatusCode.ZYHBZFX, 21);
//        FileData.setMap(StatusCode.ZYHBSBZFX, 22);
//        FileData.setMap(StatusCode.ZYHCFHFX, 23);
//        FileData.setMap(StatusCode.ZYHGDFX, 24);
//
//        FileData.setMap(StatusCode.ZYHDBFX, 25);
//        FileData.setMap(StatusCode.ZYHZCHWFX, 26);
//        FileData.setMap(StatusCode.ZYHKL, 27);
//        FileData.setMap(StatusCode.ZYHTD, 28);
//        FileData.setMap(StatusCode.ZYHZPHBG, 29);
//        FileData.setMap(StatusCode.CYHZBG, 30);
//
//        FileData.setMap(StatusCode.CYHZGRWPBG, 31);
//        FileData.setMap(StatusCode.CYHGD, 32);
//
    }
}
