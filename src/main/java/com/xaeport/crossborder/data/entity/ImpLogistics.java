package com.xaeport.crossborder.data.entity;

import java.util.Date;

//进口运单表
public class ImpLogistics {
    private String guid;//企业系统生成36 位唯一序号（英文字母大写）
    private String app_type;//企业报送类型。1-新增2-变更3-删除。默认为1。
    private Date app_time;//企业报送时间。格式:YYYYMMDDhhmmss。
    private String app_status;//业务状态:1-暂存,2-申报,默认为2。
    private String logistics_code;//物流企业的海关注册登记编号。
    private String logistics_name;//物流企业在海关注册登记的名称。
    private String logistics_no;//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
    private String logistics_status;//物流签收状态，限定S
    private Date logistics_time;//物流状态发生的实际时间。格式:YYYYMMDDhhmmss。
    private String bill_no;//直购进口为海运提单、空运总单或汽车载货清单
    private String voyage_no;//直购进口必填。货物进出境的运输工具的航次编号。保税进口免填。
    private String freight;//商品运输费用，无则填0。
    private String insured_fee;//商品保险费用，无则填0。
    private String currency;//限定为人民币，填写142。
    private String weight;//单位为千克。
    private String pack_no;//单个运单下包裹数，限定为1。
    private String goods_info;//配送的商品信息，包括商品名称、数量等。
    private String consingee;//收货人姓名。
    private String consignee_address;//收货地址。
    private String consignee_telephone;//收货人电话号码。
    private String note;//备注
    private String data_status;//数据状态
    private String crt_id;//创建人ID
    private Date crt_tm;//创建时间
    private String upd_id;//更新人ID
    private Date upd_tm;//更新时间
    private String return_status;   //回执状态
    private String return_info;//回执备注原因
    private String return_time;//回执时间

    private String rec_return_status;   //回执状态
    private String rec_return_info;//回执备注原因
    private String rec_return_time;//回执时间

    public String getRec_return_status() {
        return rec_return_status;
    }

    public void setRec_return_status(String rec_return_status) {
        this.rec_return_status = rec_return_status;
    }

    public String getRec_return_info() {
        return rec_return_info;
    }

    public void setRec_return_info(String rec_return_info) {
        this.rec_return_info = rec_return_info;
    }

    public String getRec_return_time() {
        return rec_return_time;
    }

    public void setRec_return_time(String rec_return_time) {
        this.rec_return_time = rec_return_time;
    }

    private String ent_id;//导入企业ID码
    private String ent_name;//导入企业名称
    private String ent_customs_code;//导入企业海关十位

    public String getVoyage_no() {
        return voyage_no;
    }

    public void setVoyage_no(String voyage_no) {
        this.voyage_no = voyage_no;
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

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getInsured_fee() {
        return insured_fee;
    }

    public void setInsured_fee(String insured_fee) {
        this.insured_fee = insured_fee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPack_no() {
        return pack_no;
    }

    public void setPack_no(String pack_no) {
        this.pack_no = pack_no;
    }

    public String getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(String goods_info) {
        this.goods_info = goods_info;
    }

    public String getConsingee() {
        return consingee;
    }

    public void setConsingee(String consingee) {
        this.consingee = consingee;
    }

    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
    }

    public String getConsignee_telephone() {
        return consignee_telephone;
    }

    public void setConsignee_telephone(String consignee_telephone) {
        this.consignee_telephone = consignee_telephone;
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
