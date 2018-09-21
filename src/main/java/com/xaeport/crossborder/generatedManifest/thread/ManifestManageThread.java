package com.xaeport.crossborder.generatedManifest.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert811.BaseManifestXML;
import com.xaeport.crossborder.data.entity.CEB811Message;
import com.xaeport.crossborder.data.entity.ManifestHead;
import com.xaeport.crossborder.data.entity.MessageHead;
import com.xaeport.crossborder.data.mapper.ManifestManageMapper;
import com.xaeport.crossborder.data.mapper.UserMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.FileUtils;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ManifestManageThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private ManifestManageMapper manifestManageMapper;
    private AppConfiguration appConfiguration;
    private UserMapper userMapper = SpringUtils.getBean(UserMapper.class);
    private BaseManifestXML baseManifestXML;

    //无参数的构造方法。
    public ManifestManageThread() {
    }
    //有参数的构造方法。
    public ManifestManageThread(ManifestManageMapper manifestManageMapper, AppConfiguration appConfiguration, BaseManifestXML baseManifestXML) {
        this.manifestManageMapper = manifestManageMapper;
        this.appConfiguration = appConfiguration;
        this.baseManifestXML = baseManifestXML;
    }



    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.HFDSBZ);//CBDS80 核放单申报中

        CEB811Message ceb811Message = new CEB811Message();
        ManifestHead manifestHead;
        List<ManifestHead> manifestHeadList;
        String manifestNo = null;
        while(true){
            try {
               manifestHeadList = manifestManageMapper.findManifestManage(paramMap);
               if (CollectionUtils.isEmpty(manifestHeadList)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成核放单811报文的数据，等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("核放单811报文生成器暂停时发生异常", e);
                    }
                    continue;
                }
                for (int i = 0; i <manifestHeadList.size() ; i++) {
                    manifestHead = manifestHeadList.get(i);
                    MessageHead messageHead = this.getMessageHead();
                    manifestNo = manifestHead.getManifest_no();

                    //修改核放单数据状态
                    try {
                        this.manifestManageMapper.updateManifestManage(manifestNo);
                        this.logger.debug(String.format("更新核放单为已申报[: %s]状态为: %s",manifestNo,StatusCode.HFDYSB));
                    } catch (Exception e) {
                        String exceptionMsg = String.format("更新核放单[manifestNo: %s]状态时发生异常", manifestNo);
                        this.logger.error(exceptionMsg, e);
                    }

                    //装载数据
                    ceb811Message.setManifestHead(manifestHead);
                    ceb811Message.setMessageHead(messageHead);
                    //开始生成报文
                    this.entryProcess(ceb811Message,manifestNo);
                }
            }catch (Exception e){
                try {
                    Thread.sleep(5000);
                    logger.error("生成核放单811报文时发生异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("核放单811报文生成器暂停时发生异常", ie);
                }
            }
        }
    }

    /*
    * 生成报文
    * */
    private void entryProcess(CEB811Message ceb811Message, String manifestNo) {
        try {
            // 生成核放单申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String fileName = "CEB811Message_" + manifestNo + "_" + sdf.format(new Date()) + ".xml";
            byte[] xmlByte = this.baseManifestXML.createXML(ceb811Message);
            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("完成生成运单申报报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("生成运单报文时发生异常");
            this.logger.error(exceptionMsg, e);
        }

    }
    /*
    * 生成文件的路径和名称
    * */
    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "Manifest" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("核放单811申报报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
        this.logger.debug(String.format("核放单811申报报文发送文件[sendFilePath: %s]", sendFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("运核放单811申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("核放单811发送完毕" + fileName);
        this.logger.debug(String.format("核放单811申报报文发送文件[sendFilePath: %s]生成完毕", sendFilePath));
    }

    private MessageHead getMessageHead() {
        MessageHead messageHead = new MessageHead();
        messageHead.setMessageType("DISQ");
        messageHead.setMessageId("3d9801a9-54a6-4055-8bff-a61027ed23ea");
        messageHead.setMessageTime(new Date());
        messageHead.setSenderId("ANKJ");
        messageHead.setSenderAddress("XAKJ@JC.COM");
        messageHead.setReceiveId("KCUST001");
        messageHead.setReceiveAddress("KCUST001@ecidh.com");
        messageHead.setPlatFormNo("XAKJE");
        messageHead.setCustomCode("9007");
        messageHead.setSeqNo("H18012910000004321");
        return messageHead;
    }
}
