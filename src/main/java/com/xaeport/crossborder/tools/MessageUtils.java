package com.xaeport.crossborder.tools;

import com.xaeport.crossborder.data.entity.EnvelopInfo;
import com.xaeport.crossborder.data.entity.SignedData;
import com.xaeport.crossborder.data.mapper.SystemToolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by baozhe on 2017-7-24.
 */
@Component
public class MessageUtils {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemToolMapper systemToolMapper;

    /**
     * 生成MessageID
     * 保证唯一性，sender_id+ receiver_id +时间YYYYMMDDHHMISS （14位）+（11位）序列号（各自保证唯一）
     * 对于同一个总运单，同一次申报的报关单，message_id必须保持一致
     *
     * @return
     */
    public String generateMessageID(String senderId, String receiverId) {
        logger.debug("开始生成MessageId[senderId: {}; receiverId: {}]", senderId, receiverId);
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyyMMddHHmmss");
        // 生成日期字符串
        String dateStr = dateFmt.format(new Date());
        logger.debug("生成MessageId的时间串[{}]", dateStr);
        // 通过数据库序列获取序列号
        String serialNo = systemToolMapper.getMessageIdSequeue();
        logger.debug("生成MessageId的序列号[{}]", serialNo);
        // 拼接MessageId
        String message_id = senderId + receiverId + dateStr + serialNo;
        logger.debug("完成生成MessageId[senderId: {}; receiverId: {}; messageId: {}]", senderId, receiverId, message_id);
        return message_id;
    }

    /**
     * 报单申报 EXP301 SignedData对象生成方法
     *
     * @param senderId   发送方ID
     * @param receiverId 接收方ID
     * @return SignedData对象
     */
    public SignedData getEXP301SigneData(String senderId, String receiverId) {
        logger.debug("开始生成EXP301 SignedData对象[senderId: {}; receiverId: {}]", senderId, receiverId);
        String messageId = generateMessageID(senderId, receiverId);

        // 拼装SingData 以便生成XML
        SignedData signedData = new SignedData();
        signedData.setBzType("EXP301");
        // 报文头信息
        EnvelopInfo envelopInfo = new EnvelopInfo();
        envelopInfo.setVersion("1.0");
        envelopInfo.setMessage_id(messageId);
        envelopInfo.setFile_name(messageId + ".EXP");
        envelopInfo.setMessage_type("EXP301");
        envelopInfo.setSender_id(senderId);
        envelopInfo.setReceiver_id(receiverId);
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateStr = dateFmt.format(new Date());
        logger.debug("SignedData.EnvelopInfo生成发送时间[{}]", dateStr);
        envelopInfo.setSend_time(dateStr);
        // 装载报文头信息
        signedData.setEnvelopInfo(envelopInfo);
        logger.debug("完成生成EXP301 SignedData对象[senderId: {}; receiverId: {}]", senderId, receiverId);
        return signedData;
    }

    /**
     * 舱单申报 Exp311 SignedData对象生成方法
     *
     * @param senderId   发送方ID
     * @param receiverId 接收方ID
     * @return SignedData对象
     */
    public SignedData getEXP311SigneData(String senderId, String receiverId) {
        logger.debug("开始生成EXP311 SignedData对象[senderId: {}; receiverId: {}]", senderId, receiverId);
        String messageId = generateMessageID(senderId, receiverId);

        // 拼装SingData 以便生成XML
        SignedData signedData = new SignedData();
        signedData.setBzType("EXP311");
        // 报文头信息
        EnvelopInfo envelopInfo = new EnvelopInfo();
        envelopInfo.setVersion("1.0");
        envelopInfo.setMessage_id(messageId);
        envelopInfo.setFile_name(messageId + ".EXP");
        envelopInfo.setMessage_type("EXP311");
        envelopInfo.setSender_id(senderId);
        envelopInfo.setReceiver_id(receiverId);
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateStr = dateFmt.format(new Date());
        logger.debug("SignedData.EnvelopInfo生成发送时间[{}]", dateStr);
        envelopInfo.setSend_time(dateStr);
        // 装载报文头信息
        signedData.setEnvelopInfo(envelopInfo);
        logger.debug("完成生成EXP311 SignedData对象[senderId: {}; receiverId: {}]", senderId, receiverId);
        return signedData;
    }
}
