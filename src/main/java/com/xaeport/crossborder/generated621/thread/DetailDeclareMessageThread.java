//package com.xaeport.crossborder.generated621.thread;
//
//import com.xaeport.crossborder.configuration.AppConfiguration;
//import com.xaeport.crossborder.convert513.generate.BaseLogisticsStatusXml;
//import com.xaeport.crossborder.convert621.generate.BaseDetailDeclareXML;
//import com.xaeport.crossborder.data.entity.*;
//import com.xaeport.crossborder.data.mapper.DetailDeclareMapper;
//import com.xaeport.crossborder.data.mapper.UserMapper;
//import com.xaeport.crossborder.data.mapper.WaybillDeclareMapper;
//import com.xaeport.crossborder.data.status.StatusCode;
//import com.xaeport.crossborder.tools.FileUtils;
//import com.xaeport.crossborder.tools.SpringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import javax.xml.transform.TransformerException;
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class DetailDeclareMessageThread implements Runnable {
//
//    private Log logger = LogFactory.getLog(this.getClass());
//    private DetailDeclareMapper detailDeclareMapper;
//    private AppConfiguration appConfiguration;
//    private BaseDetailDeclareXML baseDetailDeclareXML;
//    private UserMapper userMapper = SpringUtils.getBean(UserMapper.class);
//
//    //无参数的构造方法。
//    public DetailDeclareMessageThread() {
//    }
//    //有参数的构造方法。
//    public DetailDeclareMessageThread(DetailDeclareMapper detailDeclareMapper, AppConfiguration appConfiguration, BaseDetailDeclareXML baseDetailDeclareXML) {
//        this.detailDeclareMapper = detailDeclareMapper;
//        this.appConfiguration = appConfiguration;
//        this.baseDetailDeclareXML = baseDetailDeclareXML;
//    }
//
//
//
//    @Override
//    public void run() {
//        Map<String, String> paramMap = new HashMap<String, String>();
//        paramMap.put("dataStatus", StatusCode.QDSBZ);//在map中添加状态（dataStatus）为：清单申报中（CBDS60）
//
//        CEB621Message ceb621Message = new CEB621Message();
//
//        List<ImpInventoryHead> entryHeadList;
//
//        while (true){
//            try {
//                entryHeadList = detailDeclareMapper.findWaitGenerated(paramMap);
//
//                if (CollectionUtils.isEmpty(entryHeadList)) {
//                    // 如无待生成数据，则等待3s后重新确认
//                    try {
//                        Thread.sleep(3000);
//                        logger.debug("未发现需生成清单报文的数据，中等待3秒");
//                    } catch (InterruptedException e) {
//                        logger.error("清单报文生成器暂停时发生异常", e);
//                    }
//                    continue;
//                }
//                for (ImpInventoryHead entryHead: entryHeadList) {
//                    //获取head表的id
//                    String headGuid = entryHead.getGuid();
//                    try {
//                        // ceb311Message = this.messageUtils.getCEB311Message(senderId,receiverId);
//                        ceb621Message = new CEB621Message();
//                        String orderNo = entryHead.getOrder_no();//订单号,用于报文头信息
//                        //设置表头
//                        ceb621Message.setImpInventoryHead(entryHead);
//                        //用id来获取list表体
//                        List<ImpInventoryBody> InventoryLists = new ArrayList<ImpInventoryBody>();
//                        InventoryLists = detailDeclareMapper.querydetailDeclareListByGuid(headGuid);
//                        if (CollectionUtils.isEmpty(InventoryLists)){
//                            this.logger.error(String.format("获取清单详情[headGuid: %s]表体信息失败", headGuid));
//                            continue;
//                        }
//                        ceb621Message.setImpInventoryBodyList(InventoryLists);
//                        //获取basetransfer节点数据(根据创建人)
//                        BaseTransfer baseTransfer = detailDeclareMapper.queryCompany(entryHead.getCrt_id());
//                        //加载baseTransfer节点
//                        ceb621Message.setBaseTransfer(baseTransfer);
//                        //开始生成报文
//                        this.entryProcess(ceb621Message, headGuid, orderNo);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//    private void entryProcess(CEB621Message ceb621Message, String headGuid, String orderNo) throws TransformerException, IOException {
//        try {
//            this.detailDeclareMapper.updateEntryHeadDetailStatus(headGuid, StatusCode.QDYSB);
//            this.logger.debug(String.format("更新清单[head_id: %s]状态为: %s", headGuid, StatusCode.QDYSB));
//            // 更新状态变化记录表
//
//
//            // 生成报单申报报文
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
//            String fileName = "CEB621_" + orderNo + "_" + sdf.format(new Date()) + ".xml";
//            byte[] xmlByte = this.baseDetailDeclareXML.createXML(ceb621Message, "DetailDeclare");//flag
//            saveXmlFile(fileName, xmlByte);
//            this.logger.debug(String.format("完成生成清单申报报文[fileName: %s]", fileName));
//        } catch (Exception e) {
//            String exceptionMsg = String.format("处理清单[headGuid: %s]时发生异常", headGuid);
//            this.logger.error(exceptionMsg, e);
//        }
//    }
//    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "customs" + File.separator + sdf.format(new Date()) + File.separator + fileName;
//        this.logger.debug(String.format("清单申报报文发送备份文件[backFilePath: %s]", backFilePath));
//
//        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
//        this.logger.debug(String.format("清单申报报文发送文件[sendFilePath: %s]", sendFilePath));
//
//        File backupFile = new File(backFilePath);
//        FileUtils.save(backupFile, xmlByte);
//        this.logger.debug(String.format("清单申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));
//
//        File sendFile = new File(sendFilePath);
//        FileUtils.save(sendFile, xmlByte);
//        this.logger.info("清单发送完毕" + fileName);
//        this.logger.debug(String.format("清单申报报文发送文件[backFilePath: %s]生成完毕", backFilePath));
//    }
//}
