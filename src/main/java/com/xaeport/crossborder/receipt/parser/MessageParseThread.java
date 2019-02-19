package com.xaeport.crossborder.receipt.parser;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.receipt.ThreadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 报文解析线程（多例）
 */
@Component
@Scope("prototype")
public class MessageParseThread extends ThreadBase {


    @Autowired
    private AppConfiguration appConfiguration;



    @Override
    public void run() {





        try {
            MessagePreprocessThread.getProcessingFileByCache();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
