package com.xaeport.crossborder.generated513.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert513.BaseLogisticsStatusXml;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.UserMapper;
import com.xaeport.crossborder.data.mapper.WaybillDeclareMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.FileUtils;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogisticsStatusMessageThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private WaybillDeclareMapper waybillDeclareMapper;
    private AppConfiguration appConfiguration;
    private BaseLogisticsStatusXml baseLogisticsStatusXml;
    private UserMapper userMapper = SpringUtils.getBean(UserMapper.class);

    //无参数的构造方法。
    public LogisticsStatusMessageThread() {
    }
    //有参数的构造方法。
    public LogisticsStatusMessageThread(WaybillDeclareMapper waybillDeclareMapper, AppConfiguration appConfiguration, BaseLogisticsStatusXml baseLogisticsStatusXml) {
        this.waybillDeclareMapper = waybillDeclareMapper;
        this.appConfiguration = appConfiguration;
        this.baseLogisticsStatusXml = baseLogisticsStatusXml;
    }



    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.YDZTSBZ);//在map中添加状态（dataStatus）为：运单状态申报中（CBDS50）

        CEB513Message ceb513Message = new CEB513Message();

        List<ImpLogisticsStatus> impLogisticsStatusLists;
        List<LogisticsStatusHead> logisticsStatusHeadsLists;
        LogisticsStatusHead logisticsStatusHead;
        ImpLogisticsStatus lmpLogisticsStatus;
        String guid;
        String crtId = null;
        while (true) {
            try {
                // 查找运单申报中的数据
                impLogisticsStatusLists = waybillDeclareMapper.findWaitGeneratedToLogisticsStatus(paramMap);
                if (CollectionUtils.isEmpty(impLogisticsStatusLists)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成运单状态513报文的数据，等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("运单状态513报文生成器暂停时发生异常", e);
                    }
                    continue;
                }
                logisticsStatusHeadsLists = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                String nameLogisticsNo = null;
                String xmlHeadGuid = null;

                for (int i = 0; i < impLogisticsStatusLists.size(); i++) {
                    lmpLogisticsStatus = impLogisticsStatusLists.get(i);
                    xmlHeadGuid = impLogisticsStatusLists.get(0).getGuid();
                    nameLogisticsNo = impLogisticsStatusLists.get(0).getLogistics_no();
                    guid = lmpLogisticsStatus.getGuid();
                    crtId = lmpLogisticsStatus.getCrt_id();
                    logisticsStatusHead = new LogisticsStatusHead();

                    logisticsStatusHead.setGuid(guid);//企业系统生成36 位唯一序号（英文字母大写）
                    logisticsStatusHead.setAppType(lmpLogisticsStatus.getApp_type());//企业报送类型。1-新增2-变更3-删除。默认为1。
                    logisticsStatusHead.setAppTime(sdf.format(lmpLogisticsStatus.getApp_time()));//企业报送时间。格式:YYYYMMDDhhmmss。
                    logisticsStatusHead.setAppStatus(lmpLogisticsStatus.getApp_status());//业务状态:1-暂存,2-申报,默认为2。
                    logisticsStatusHead.setLogisticsCode(lmpLogisticsStatus.getLogistics_code());//物流企业的海关注册登记编号。
                    logisticsStatusHead.setLogisticsName(lmpLogisticsStatus.getLogistics_name());//物流企业在海关注册登记的名称。
                    logisticsStatusHead.setLogisticsNo(lmpLogisticsStatus.getLogistics_no());//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
                    logisticsStatusHead.setLogisticsStatus(StringUtils.isEmpty(lmpLogisticsStatus.getLogistics_status()) ? "S" : lmpLogisticsStatus.getLogistics_status());//物流签收状态，限定S
                    logisticsStatusHead.setLogisticsTime(lmpLogisticsStatus.getLogistics_time());//物流状态发生的实际时间。格式:YYYYMMDDhhmmss。
                    logisticsStatusHead.setNote(lmpLogisticsStatus.getNote());//备注
                    try {
                        // 更新运单状态
                        this.waybillDeclareMapper.updateToLogisticsStatus(guid, StatusCode.YDZTYSB);
                        this.logger.debug(String.format("更新运单状态的状态为已申报[guid: %s]状态为: %s", guid, StatusCode.YDZTYSB));
                    } catch (Exception e) {
                        String exceptionMsg = String.format("更改运单状态513，[headGuid: %s]状态时发生异常", logisticsStatusHead.getGuid());
                        this.logger.error(exceptionMsg, e);
                    }
                    logisticsStatusHeadsLists.add(logisticsStatusHead);
                }

                ceb513Message.setLogisticsStatusHeadList(logisticsStatusHeadsLists);
                //开始生成报文
                BaseTransfer baseTransfer = new BaseTransfer();
                if (!StringUtils.isEmpty(crtId)) {
                    baseTransfer = waybillDeclareMapper.queryCompany(crtId);
                }
                //给basetransfer字段值
                ceb513Message.setBaseTransfer(baseTransfer);
                this.entryProcess(ceb513Message, nameLogisticsNo, xmlHeadGuid);

            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成运单状态513报文时发生异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("运单状态513报文生成器暂停时发生异常", ie);
                }
            }

        }
    }

    private void entryProcess(CEB513Message ceb513Message, String nameLogisticsNo, String xmlHeadGuid) throws TransformerException, IOException {
        try {
            // 生成运单状态申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String fileName = "CEB513Message_" + nameLogisticsNo + "_" + sdf.format(new Date()) + ".xml";

            byte[] xmlByte = this.baseLogisticsStatusXml.createXML(ceb513Message, "logisticsStatus", xmlHeadGuid);

            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("完成生成运单状态513申报报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("生成运单状态513报文时发生异常");
            this.logger.error(exceptionMsg, e);
        }
    }

    /**
     * 生成Xml文件
     *
     * @param fileName 文件名称
     * @param xmlByte  文件内容Bytes
     */
    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "logisticsStatus" + File.separator + sdf.format(new Date()) + File.separator + fileName;
        this.logger.debug(String.format("运单状态513申报报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
        this.logger.debug(String.format("运单状态513申报报文发送文件[sendFilePath: %s]", sendFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("运单状态513申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("运单状态513发送完毕" + fileName);
        this.logger.debug(String.format("运单状态513申报报文发送文件[backFilePath: %s]生成完毕", backFilePath));
    }

}
