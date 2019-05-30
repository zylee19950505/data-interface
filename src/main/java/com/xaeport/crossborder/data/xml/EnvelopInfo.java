package com.xaeport.crossborder.data.xml;

import com.xaeport.crossborder.tools.xml.RootXPath;
import com.xaeport.crossborder.tools.xml.XPath;
import com.xaeport.crossborder.tools.xml.XmlEntry;

/**
 * @Author BaoZhe
 * @Date 2019-04-26
 * @Version 1.0
 */
@RootXPath("EnvelopInfo")
public class EnvelopInfo implements XmlEntry {

    @XPath("msg_id")
    private String msgId;// 企业内部编码
    @XPath("msg_type")
    private String msgIype;// CEBINV101
    @XPath("sender_id")
    private String senderId;// 企业ID
    @XPath("receiver_id")
    private String receiverId;// 9009000001
    @XPath("ic_card")
    private String icCard;// IC卡号
    @XPath("status")
    private String status;// 申报为"1";暂存为"0"（申报时，所有字段为必填）


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgIype() {
        return msgIype;
    }

    public void setMsgIype(String msgIype) {
        this.msgIype = msgIype;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getIcCard() {
        return icCard;
    }

    public void setIcCard(String icCard) {
        this.icCard = icCard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
