package com.xaeport.crossborder.data.entity;

import java.util.Date;

//进口运单状态表
public class ImpLogisticsStatus {
    private String guid;//企业系统生成36 位唯一序号（英文字母大写）
    private String app_type;//企业报送类型。1-新增2-变更3-删除。默认为1。
    private Date app_time;//企业报送时间。格式:YYYYMMDDhhmmss。
    private String app_status;//业务状态:1-暂存,2-申报,默认为2。
    private String logistics_code;//物流企业的海关注册登记编号。
    private String logistics_name;//物流企业在海关注册登记的名称。
    private String logistics_no;//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
    private String logistics_status;//物流签收状态，限定S
    private Date logistics_time;//物流状态发生的实际时间。格式:YYYYMMDDhhmmss。
    private String note;//备注
    private String data_status;//数据状态
    private String crt_id;//创建人ID
    private Date crt_tm;//创建时间
    private String upd_id;//更新人ID
    private Date upd_tm;//更新时间
    private String return_status;   //回执状态
    private String return_info;//回执备注原因
    private String return_time;//回执时间
    private String ent_id;//导入企业ID码
    private String ent_name;//导入企业名称
    private String ent_customs_code;//导入企业海关十位
    private String writing_mode;

    public String getWriting_mode() {
        return writing_mode;
    }

    public void setWriting_mode(String writing_mode) {
        this.writing_mode = writing_mode;
    }

    public String getReturn_status() {
        return return_status;
    }

    public void setReturn_status(String return_status) {
        this.return_status = return_status;
    }

    public String getReturn_info() {
        return return_info;
    }

    public void setReturn_info(String return_info) {
        this.return_info = return_info;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public Date getApp_time() {
        return app_time;
    }

    public void setApp_time(Date app_time) {
        this.app_time = app_time;
    }

    public String getApp_status() {
        return app_status;
    }

    public void setApp_status(String app_status) {
        this.app_status = app_status;
    }

    public String getLogistics_code() {
        return logistics_code;
    }

    public void setLogistics_code(String logistics_code) {
        this.logistics_code = logistics_code;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public String getLogistics_no() {
        return logistics_no;
    }

    public void setLogistics_no(String logistics_no) {
        this.logistics_no = logistics_no;
    }

    public String getLogistics_status() {
        return logistics_status;
    }

    public void setLogistics_status(String logistics_status) {
        this.logistics_status = logistics_status;
    }

    public Date getLogistics_time() {
        return logistics_time;
    }

    public void setLogistics_time(Date logistics_time) {
        this.logistics_time = logistics_time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getData_status() {
        return data_status;
    }

    public void setData_status(String data_status) {
        this.data_status = data_status;
    }

    public String getCrt_id() {
        return crt_id;
    }

    public void setCrt_id(String crt_id) {
        this.crt_id = crt_id;
    }

    public Date getCrt_tm() {
        return crt_tm;
    }

    public void setCrt_tm(Date crt_tm) {
        this.crt_tm = crt_tm;
    }

    public String getUpd_id() {
        return upd_id;
    }

    public void setUpd_id(String upd_id) {
        this.upd_id = upd_id;
    }

    public Date getUpd_tm() {
        return upd_tm;
    }

    public void setUpd_tm(Date upd_tm) {
        this.upd_tm = upd_tm;
    }

    public String getEnt_id() {
        return ent_id;
    }

    public void setEnt_id(String ent_id) {
        this.ent_id = ent_id;
    }

    public String getEnt_name() {
        return ent_name;
    }

    public void setEnt_name(String ent_name) {
        this.ent_name = ent_name;
    }

    public String getEnt_customs_code() {
        return ent_customs_code;
    }

    public void setEnt_customs_code(String ent_customs_code) {
        this.ent_customs_code = ent_customs_code;
    }
}
