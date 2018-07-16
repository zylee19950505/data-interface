package com.xaeport.crossborder.data.entity;

import java.util.Date;

/**
 * 信封数据
 * Created by zwj on 2017/07/18.
 */
public class EnvelopInfo {
    private String re_id = "";//ID
    private String message_id = "";//报文唯一编号
    private String file_name = "";//报文名
    private String message_type = "";//报文类型
    private String sender_id = "";//发送方唯一编号
    private String receiver_id = "";//接收方编号
    private String send_time = "";//发送时间
    private String version = "";//版本号
    private String refile_name = "";//回执报文名
    private Date create_time;//创建时间

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRe_id() {
        return re_id;
    }

    public void setRe_id(String re_id) {
        this.re_id = re_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getRefile_name() {
        return refile_name;
    }

    public void setRefile_name(String refile_name) {
        this.refile_name = refile_name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
