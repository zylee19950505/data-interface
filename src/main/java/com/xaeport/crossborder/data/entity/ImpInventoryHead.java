package com.xaeport.crossborder.data.entity;

import java.util.Date;

//进口清单表头
public class ImpInventoryHead {
    private String guid;//企业系统生成36 位唯一序号（英文字母大写）
    private String app_type;//企业报送类型。1-新增2-变更3-删除。默认为1。
    private Date app_time;//企业报送时间。格式:YYYYMMDDhhmmss。
    private String app_status;//业务状态:1-暂存,2-申报,默认为2。
    private String order_no;//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
    private String ebp_code;//电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。
    private String ebp_name;//电商平台的海关注册登记名称；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台名称为准。
    private String ebc_code;//电商企业的海关注册登记编号。
    private String ebc_name;//电商企业的海关注册登记名称。
    private String logistics_no;//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
    private String logistics_code;//物流企业的海关注册登记编号。
    private String logistics_name;//物流企业在海关注册登记的名称。
    private String cop_no;//企业内部标识单证的编号。
    private String pre_no;//电子口岸标识单证的编号。
    private String assure_code;//担保扣税的企业海关注册登记编号，只限清单的电商平台企业、电商企业、物流企业。
    private String ems_no;//保税模式必填，填写区内仓储企业在海关备案的账册编号，用于保税进口业务在特殊区域辅助系统记账（二线出区核减）。
    private String invt_no;//海关接受申报生成的清单编号。
    private String ie_flag;//I-进口,E-出口
    private Date decl_time;//申报日期，以海关计算机系统接受清单申报数据时记录的日期为准。格式:YYYYMMDD。
    private String customs_code;//接受清单申报的海关关区代码，参照JGS/T 18《海关关区代码》。
    private String port_code;//商品实际进出我国关境口岸海关的关区代码，参照JGS/T 18《海关关区代码》。
    private Date ie_date;//运载所申报商品的运输工具申报进境的日期，进口申报时无法确知相应的运输工具的实际进境日期时，免填。格式:YYYYMMDD
    private String buyer_id_type;//1-身份证,2-其它。限定为身份证，填写1。
    private String buyer_id_number;//订购人的身份证件号码。
    private String buyer_name;//订购人的真实姓名。
    private String buyer_telephone;//订购人电话。
    private String consignee_address;//"收件地址。收货地址一致。"
    private String agent_code;//申报单位的海关注册登记编号。
    private String agent_name;//申报单位在海关注册登记的名称。
    private String area_code;//保税模式必填，区内仓储企业的海关注册登记编号。
    private String area_name;//保税模式必填，区内仓储企业在海关注册登记的名称。
    private String trade_mode;//直购进口填写9610，保税进口填写1210。
    private String traf_mode;//填写海关标准的参数代码，参照《JGS-20 海关业务代码集》- 运输方式代码。直购进口指跨境段物流运输方式，保税进口指二线出区物流运输方式。
    private String traf_no;//直购进口必填。货物进出境的运输工具的名称或运输工具编号。填报内容应与运输部门向海关申报的载货清单所列相应内容一致；同报关单填制规范。保税进口免填。
    private String voyage_no;//直购进口必填。货物进出境的运输工具的航次编号。保税进口免填。
    private String bill_no;//直购进口必填。货物提单或运单的编号，保税进口免填。
    private String loct_no;//针对同一申报地海关下有多个跨境电子商务的监管场所,需要填写区分
    private String license_no;//商务主管部门及其授权发证机关签发的进出口货物许可证件的编号
    private String country;//直购进口填写起始发出国家（地区）代码，参照《JGS-20 海关业务代码集》的国家（地区）代码表；保税进口填写代码“142”。
    private String freight;//物流企业实际收取的运输费用。
    private String insured_fee;//物流企业实际收取的商品保价费用。
    private String currency;//限定为人民币，填写142。
    private String wrap_type;//海关对进出口货物实际采用的外部包装方式的标识代码，采用1 位数字表示，如：木箱、纸箱、桶装、散装、托盘、包、油罐车等
    private String pack_no;//件数为包裹数量，限定为1。
    private String gross_weight;//货物及其包装材料的重量之和，计量单位为千克。
    private String net_weight;//货物的毛重减去外包装材料后的重量，即货物本身的实际重量，计量单位为千克。
    private String note;//备注
    private String data_status;//数据状态
    private String crt_id;//创建人ID
    private Date crt_tm;//创建时间
    private String upd_id;//更新人ID
    private Date upd_tm;//更新时间

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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getEbp_code() {
        return ebp_code;
    }

    public void setEbp_code(String ebp_code) {
        this.ebp_code = ebp_code;
    }

    public String getEbp_name() {
        return ebp_name;
    }

    public void setEbp_name(String ebp_name) {
        this.ebp_name = ebp_name;
    }

    public String getEbc_code() {
        return ebc_code;
    }

    public void setEbc_code(String ebc_code) {
        this.ebc_code = ebc_code;
    }

    public String getEbc_name() {
        return ebc_name;
    }

    public void setEbc_name(String ebc_name) {
        this.ebc_name = ebc_name;
    }

    public String getLogistics_no() {
        return logistics_no;
    }

    public void setLogistics_no(String logistics_no) {
        this.logistics_no = logistics_no;
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

    public String getCop_no() {
        return cop_no;
    }

    public void setCop_no(String cop_no) {
        this.cop_no = cop_no;
    }

    public String getPre_no() {
        return pre_no;
    }

    public void setPre_no(String pre_no) {
        this.pre_no = pre_no;
    }

    public String getAssure_code() {
        return assure_code;
    }

    public void setAssure_code(String assure_code) {
        this.assure_code = assure_code;
    }

    public String getEms_no() {
        return ems_no;
    }

    public void setEms_no(String ems_no) {
        this.ems_no = ems_no;
    }

    public String getInvt_no() {
        return invt_no;
    }

    public void setInvt_no(String invt_no) {
        this.invt_no = invt_no;
    }

    public String getIe_flag() {
        return ie_flag;
    }

    public void setIe_flag(String ie_flag) {
        this.ie_flag = ie_flag;
    }

    public Date getDecl_time() {
        return decl_time;
    }

    public void setDecl_time(Date decl_time) {
        this.decl_time = decl_time;
    }

    public String getCustoms_code() {
        return customs_code;
    }

    public void setCustoms_code(String customs_code) {
        this.customs_code = customs_code;
    }

    public String getPort_code() {
        return port_code;
    }

    public void setPort_code(String port_code) {
        this.port_code = port_code;
    }

    public Date getIe_date() {
        return ie_date;
    }

    public void setIe_date(Date ie_date) {
        this.ie_date = ie_date;
    }

    public String getBuyer_id_type() {
        return buyer_id_type;
    }

    public void setBuyer_id_type(String buyer_id_type) {
        this.buyer_id_type = buyer_id_type;
    }

    public String getBuyer_id_number() {
        return buyer_id_number;
    }

    public void setBuyer_id_number(String buyer_id_number) {
        this.buyer_id_number = buyer_id_number;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public String getBuyer_telephone() {
        return buyer_telephone;
    }

    public void setBuyer_telephone(String buyer_telephone) {
        this.buyer_telephone = buyer_telephone;
    }

    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getTrade_mode() {
        return trade_mode;
    }

    public void setTrade_mode(String trade_mode) {
        this.trade_mode = trade_mode;
    }

    public String getTraf_mode() {
        return traf_mode;
    }

    public void setTraf_mode(String traf_mode) {
        this.traf_mode = traf_mode;
    }

    public String getTraf_no() {
        return traf_no;
    }

    public void setTraf_no(String traf_no) {
        this.traf_no = traf_no;
    }

    public String getVoyage_no() {
        return voyage_no;
    }

    public void setVoyage_no(String voyage_no) {
        this.voyage_no = voyage_no;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getLoct_no() {
        return loct_no;
    }

    public void setLoct_no(String loct_no) {
        this.loct_no = loct_no;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getWrap_type() {
        return wrap_type;
    }

    public void setWrap_type(String wrap_type) {
        this.wrap_type = wrap_type;
    }

    public String getPack_no() {
        return pack_no;
    }

    public void setPack_no(String pack_no) {
        this.pack_no = pack_no;
    }

    public String getGross_weight() {
        return gross_weight;
    }

    public void setGross_weight(String gross_weight) {
        this.gross_weight = gross_weight;
    }

    public String getNet_weight() {
        return net_weight;
    }

    public void setNet_weight(String net_weight) {
        this.net_weight = net_weight;
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
}
