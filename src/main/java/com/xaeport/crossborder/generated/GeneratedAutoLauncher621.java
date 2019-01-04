package com.xaeport.crossborder.generated;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.bondinven.BaseBondInvenXML;
import com.xaeport.crossborder.convert.inventory621.BaseDetailDeclareXML;
import com.xaeport.crossborder.data.mapper.BondinvenDeclareMapper;
import com.xaeport.crossborder.data.mapper.DetailDeclareMapper;
import com.xaeport.crossborder.generated.thread.BondInvenMessageThread;
import com.xaeport.crossborder.generated.thread.DetailDeclareMessageThread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by zwf on 2018-7-24.
 * 报文生成启动器
 */
@Component
public class GeneratedAutoLauncher621 implements ApplicationListener<ApplicationReadyEvent> {

    private Log logger = LogFactory.getLog(this.getClass());
    private ExecutorService executorService = Executors.newCachedThreadPool();

    //跨境清单线程
    private DetailDeclareMessageThread detailDeclareMessageThread;
    //保税清单线程
    private BondInvenMessageThread bondInvenMessageThread;

    //跨境
    @Autowired
    DetailDeclareMapper detailDeclareMapper;
    //保税
    @Autowired
    BondinvenDeclareMapper bondinvenDeclareMapper;

    //跨境报文
    @Autowired
    BaseDetailDeclareXML baseDetailDeclareXML;
    //保税报文
    @Autowired
    BaseBondInvenXML baseBondInvenXML;

    @Autowired
    AppConfiguration appConfiguration;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        this.logger.debug("清单报文CEB621生成启动器初始化开始");
        detailDeclareMessageThread = new DetailDeclareMessageThread(this.detailDeclareMapper, this.appConfiguration, this.baseDetailDeclareXML);
        executorService.execute(detailDeclareMessageThread);

        this.logger.debug("保税清单报文CEB621生成启动器初始化开始");
        bondInvenMessageThread = new BondInvenMessageThread(this.bondinvenDeclareMapper, this.appConfiguration, this.baseBondInvenXML);
        executorService.execute(bondInvenMessageThread);
    }
}
