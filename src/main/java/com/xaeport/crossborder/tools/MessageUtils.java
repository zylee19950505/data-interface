package com.xaeport.crossborder.tools;

import com.xaeport.crossborder.data.entity.CEB311Message;
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

/*
* 订单报文申报
*
* */
	public CEB311Message getCEB311Message(String senderId, String receiverId) {
        logger.debug("开始生成CEB311 SignedData对象[senderId: {}; receiverId: {}]", senderId, receiverId);
        //String messageId = generateMessageID(senderId, receiverId);

        // 拼装SingData 以便生成XML
        CEB311Message ceb311Message = new CEB311Message();
        //ceb311Message.setBzType("CEB311");
        // 报文头信息
       /* EnvelopInfo envelopInfo = new EnvelopInfo();
        envelopInfo.setVersion("1.0");
        envelopInfo.setMessage_id(messageId);
        envelopInfo.setFile_name(messageId + ".EXP");
        envelopInfo.setMessage_type("CEB311");
        envelopInfo.setSender_id(senderId);
        envelopInfo.setReceiver_id(receiverId);*/
     /*   SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateStr = dateFmt.format(new Date());
        logger.debug("SignedData.EnvelopInfo生成发送时间[{}]", dateStr);
        ceb311Message.setSend_time(dateStr);
        // 装载报文头信息
        ceb311Message.setEnvelopInfo(envelopInfo);
        logger.debug("完成生成CEB311 SignedData对象[senderId: {}; receiverId: {}]", senderId, receiverId);*/
        return ceb311Message;
	}
}
