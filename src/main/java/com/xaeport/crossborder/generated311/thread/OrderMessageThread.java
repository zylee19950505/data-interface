package com.xaeport.crossborder.generated311.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert311.BaseBill;
import com.xaeport.crossborder.data.entity.BaseTransfer;
import com.xaeport.crossborder.data.entity.CEB311Message;
import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.mapper.OrderDeclareMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.ordermanage.OrderDeclareSevice;
import com.xaeport.crossborder.tools.FileUtils;
import com.xaeport.crossborder.tools.MessageUtils;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderMessageThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private AppConfiguration appConfiguration = SpringUtils.getBean(AppConfiguration.class);
    private OrderDeclareMapper orderDeclareMapper = SpringUtils.getBean(OrderDeclareMapper.class);
    private OrderDeclareSevice orderDeclareSevice = SpringUtils.getBean(OrderDeclareSevice.class);
    private MessageUtils messageUtils = SpringUtils.getBean(MessageUtils.class);
    private BaseBill baseBill = SpringUtils.getBean(BaseBill.class);
    private static OrderMessageThread manifestGenMsgThread;

    public OrderMessageThread(OrderDeclareMapper orderDeclareMapper, AppConfiguration appConfiguration, MessageUtils messageUtils, BaseBill baseBill, OrderDeclareSevice orderDeclareSevice) {
        this.orderDeclareMapper = orderDeclareMapper;
        this.appConfiguration = appConfiguration;
        this.messageUtils = messageUtils;
        this.baseBill = baseBill;
        this.orderDeclareSevice = orderDeclareSevice;
    }
    private OrderMessageThread() {
        super();
    }

    public static OrderMessageThread getInstance() {
        if (manifestGenMsgThread == null) {
            manifestGenMsgThread = new OrderMessageThread();
        }
        return manifestGenMsgThread;
    }


    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.DDSBZ);//订单申报中

        String senderId = this.appConfiguration.getSenderId();// 发送方ID
        String receiverId = this.appConfiguration.getReceiverId();// 接收方ID

        CEB311Message ceb311Message;

        List<ImpOrderHead> entryHeadList;

        while (true){
            try {
                entryHeadList = orderDeclareMapper.findWaitGenerated(paramMap);
                if (CollectionUtils.isEmpty(entryHeadList)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成订单报文的数据，中等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("订单报文生成器暂停时发生异常", e);
                    }
                    continue;
                }
                for (ImpOrderHead entryHead: entryHeadList) {
                    //获取head表的id
                    String headGuid = entryHead.getGuid();
                    try {
                        ceb311Message = new CEB311Message();
                        String orderNo = entryHead.getOrder_No();//订单号,用于报文头信息
                        //设置表头
                        ceb311Message.setOrderHead(entryHead);
                        //用id来获取list表体
                        List<ImpOrderBody> orderLists = new ArrayList<ImpOrderBody>();
                        orderLists = orderDeclareMapper.queryOrderListByGuid(headGuid);
                        if (CollectionUtils.isEmpty(orderLists)){
                            this.logger.error(String.format("订单获取订单详情[headGuid: %s]表体信息失败", headGuid));
                            continue;
                        }
                        ceb311Message.setOrderBodyList(orderLists);
                        //获取basetransfer节点数据(根据创建人)
						BaseTransfer baseTransfer = orderDeclareSevice.queryCompany(entryHead.getCrt_id());
						//加载baseTransfer节点
						ceb311Message.setBaseTransfer(baseTransfer);
                        //开始生成报文
                        this.entryProcess(ceb311Message, headGuid, orderNo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void entryProcess(CEB311Message ceb311Message, String headGuid, String orderNo) throws TransformerException, IOException {
        try {
            this.orderDeclareMapper.updateEntryHeadOrderStatus(headGuid, StatusCode.DDYSB);
            this.logger.debug(String.format("更新订单[head_id: %s]状态为: %s", headGuid, StatusCode.DDYSB));
            // 更新状态变化记录表


            // 生成报单申报报文
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String fileName = "CEB311_" + orderNo + "_" + sdf.format(new Date()) + ".xml";
            byte[] xmlByte = this.baseBill.createXML(ceb311Message, "orderDeclare");//flag
            saveXmlFile(fileName, xmlByte);
            this.logger.debug(String.format("完成生成订单申报报文[fileName: %s]", fileName));
        } catch (Exception e) {
            String exceptionMsg = String.format("处理订单[headGuid: %s]时发生异常", headGuid);
            this.logger.error(exceptionMsg, e);
        }
    }
    private void saveXmlFile(String fileName, byte[] xmlByte) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String backFilePath = this.appConfiguration.getXmlPath().get("sendBakPath") + File.separator + "order" + File.separator + sdf.format(new Date()) + File.separator + fileName;
       this.logger.debug(String.format("订单申报报文发送备份文件[backFilePath: %s]", backFilePath));

        String sendFilePath = this.appConfiguration.getXmlPath().get("sendPath") + File.separator + fileName;
        this.logger.debug(String.format("订单申报报文发送文件[sendFilePath: %s]", sendFilePath));

        File backupFile = new File(backFilePath);
        FileUtils.save(backupFile, xmlByte);
        this.logger.debug(String.format("订单申报报文发送备份文件[backFilePath: %s]生成完毕", backFilePath));

        File sendFile = new File(sendFilePath);
        FileUtils.save(sendFile, xmlByte);
        this.logger.info("订单发送完毕" + fileName);
        this.logger.debug(String.format("订单申报报文发送文件[backFilePath: %s]生成完毕", backFilePath));
    }
}
