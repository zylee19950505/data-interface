package com.xaeport.crossborder.data.entity;

//跨境保税逻辑校验表体通用实体类
public class VerifyBondBody {

    //订单表体数据
    private int g_num;//从1开始的递增序号。
    private String head_guid;//出口电子订单表头系统唯一序号
    private String order_no;//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
    private String item_no;//电商企业自定义的商品货号（SKU）。
    private String item_name;//交易平台销售商品的中文名称。
    private String item_describe;//交易平台销售商品的描述信息。
    private String bar_code;//商品条形码一般由前缀部分、制造厂商代码、商品代码和校验码组成。
    private String unit;//海关标准的参数代码海关标准的参数代码《JGS-20 海关业务代码集》- 计量单位代码
    private String g_model;//商品规格型号
    private String qty;//商品实际数量
    private String price;//商品单价。赠品单价填写为 0。
    private String total_price;//商品总价，等于单价乘以数量。
    private String currency;//限定为人民币，填写142。
    private String country;//填写海关标准的参数代码，参照《JGS-20 海关业务代码集》- 国家（地区）代码表。
    private String note;//促销活动，商品单价偏离市场价格的，可以在此说明。
    private String writing_mode;

    //清单表体数据
    private String item_record_no;//账册备案料号: 保税进口必填
    private String g_code;//商品编码: 按商品分类编码规则确定的进出口商品的商品编号，分为商品编号和附加编号，其中商品编号栏应填报《中华人民共和国进出口税则》8位税则号列，附加编号应填报商品编号，附加编号第9、10位。
    private String g_name;//商品名称: 商品名称应据实填报，与电子订单一致。
    private String qty1;//按照商品编码规则对应的法定计量单位的实际数量填写。
    private String qty2;//第二法定数量
    private String unit1;//海关标准的参数代码《JGS-20海关业务代码集》- 计量单位代码
    private String unit2;//海关标准的参数代码《JGS-20海关业务代码集》- 计量单位代码

    //入区核注清单表体数据
    private String id;//ID
    private String bond_invt_no;//保税清单编号
    private String chg_tms_cnt;//变更次数
    private Integer gds_seqno;//商品序号
    private Integer putrec_seqno;//备案序号
    private String gds_mtno;//商品料号
    private String gdecd;//商品编码
    private String gds_nm;//商品名称
    private String gds_spcf_model_desc;//商品规格型号描述
    private String dcl_unitcd;//申报计量单位
    private String lawf_unitcd;//法定计量单位
    private String secd_lawf_unitcd;//法定第二计量
    private String natcd;//原产国(地区)
    private String dcl_uprc_amt;//申报单价金额
    private String dcl_total_amt;//申报总金额
    private String usd_stat_total_amt;//美元统计总金额 按清单申报日期取汇率折算返填
    private String dcl_currcd;//申报币制代码
    private String lawf_qty;//法定数量
    private String secd_lawf_qty;//第二法定数量
    private String wt_sf_val;//重量比例因子值
    private String fst_sf_val;//第一比例因子
    private String secd_sf_val;//第二比例因子
    private String dcl_qty;//申报数量
    private String gross_wt;//毛重量
    private String net_wt;//净重量
    private String lvyrlf_modecd;//征减免方式代码
    private String ucns_verno;//账册备案单耗明细版本号
    private Integer entry_gds_seqno;//报关单商品序号 需报关的清单，为必填项
    private String apply_tb_seqno;//申请表序号 流转类专用。用于建立清单商品与流转申请表商品之间的关系
    private String cly_markcd;//归类标记代码
    private String rmk;//备注
    private String destination_natcd;//最终目的国（地区） 参照国别代码表(COUNTRY)
    private String modf_markcd;//修改标志 0-未修改 1-修改 2-删除 3-增加。备用字段，企业自主发起变更、删除功能转用，此功能明年上线。
    private String head_etps_inner_invt_no;//表头唯一关联码
    private String surplus_nm;//剩余数量
    private double quantity;//申报数量
    private String Usecd;//用途代码
    private String ec_customs_code;//电商海关编码

    public String getItem_record_no() {
        return item_record_no;
    }

    public void setItem_record_no(String item_record_no) {
        this.item_record_no = item_record_no;
    }

    public String getG_code() {
        return g_code;
    }

    public void setG_code(String g_code) {
        this.g_code = g_code;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getQty1() {
        return qty1;
    }

    public void setQty1(String qty1) {
        this.qty1 = qty1;
    }

    public String getQty2() {
        return qty2;
    }

    public void setQty2(String qty2) {
        this.qty2 = qty2;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public String getUnit2() {
        return unit2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public int getG_num() {
        return g_num;
    }

    public void setG_num(int g_num) {
        this.g_num = g_num;
    }

    public String getHead_guid() {
        return head_guid;
    }

    public void setHead_guid(String head_guid) {
        this.head_guid = head_guid;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_describe() {
        return item_describe;
    }

    public void setItem_describe(String item_describe) {
        this.item_describe = item_describe;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getG_model() {
        return g_model;
    }

    public void setG_model(String g_model) {
        this.g_model = g_model;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWriting_mode() {
        return writing_mode;
    }

    public void setWriting_mode(String writing_mode) {
        this.writing_mode = writing_mode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBond_invt_no() {
        return bond_invt_no;
    }

    public void setBond_invt_no(String bond_invt_no) {
        this.bond_invt_no = bond_invt_no;
    }

    public String getChg_tms_cnt() {
        return chg_tms_cnt;
    }

    public void setChg_tms_cnt(String chg_tms_cnt) {
        this.chg_tms_cnt = chg_tms_cnt;
    }

    public Integer getGds_seqno() {
        return gds_seqno;
    }

    public void setGds_seqno(Integer gds_seqno) {
        this.gds_seqno = gds_seqno;
    }

    public Integer getPutrec_seqno() {
        return putrec_seqno;
    }

    public void setPutrec_seqno(Integer putrec_seqno) {
        this.putrec_seqno = putrec_seqno;
    }

    public String getGds_mtno() {
        return gds_mtno;
    }

    public void setGds_mtno(String gds_mtno) {
        this.gds_mtno = gds_mtno;
    }

    public String getGdecd() {
        return gdecd;
    }

    public void setGdecd(String gdecd) {
        this.gdecd = gdecd;
    }

    public String getGds_nm() {
        return gds_nm;
    }

    public void setGds_nm(String gds_nm) {
        this.gds_nm = gds_nm;
    }

    public String getGds_spcf_model_desc() {
        return gds_spcf_model_desc;
    }

    public void setGds_spcf_model_desc(String gds_spcf_model_desc) {
        this.gds_spcf_model_desc = gds_spcf_model_desc;
    }

    public String getDcl_unitcd() {
        return dcl_unitcd;
    }

    public void setDcl_unitcd(String dcl_unitcd) {
        this.dcl_unitcd = dcl_unitcd;
    }

    public String getLawf_unitcd() {
        return lawf_unitcd;
    }

    public void setLawf_unitcd(String lawf_unitcd) {
        this.lawf_unitcd = lawf_unitcd;
    }

    public String getSecd_lawf_unitcd() {
        return secd_lawf_unitcd;
    }

    public void setSecd_lawf_unitcd(String secd_lawf_unitcd) {
        this.secd_lawf_unitcd = secd_lawf_unitcd;
    }

    public String getNatcd() {
        return natcd;
    }

    public void setNatcd(String natcd) {
        this.natcd = natcd;
    }

    public String getDcl_uprc_amt() {
        return dcl_uprc_amt;
    }

    public void setDcl_uprc_amt(String dcl_uprc_amt) {
        this.dcl_uprc_amt = dcl_uprc_amt;
    }

    public String getDcl_total_amt() {
        return dcl_total_amt;
    }

    public void setDcl_total_amt(String dcl_total_amt) {
        this.dcl_total_amt = dcl_total_amt;
    }

    public String getUsd_stat_total_amt() {
        return usd_stat_total_amt;
    }

    public void setUsd_stat_total_amt(String usd_stat_total_amt) {
        this.usd_stat_total_amt = usd_stat_total_amt;
    }

    public String getDcl_currcd() {
        return dcl_currcd;
    }

    public void setDcl_currcd(String dcl_currcd) {
        this.dcl_currcd = dcl_currcd;
    }

    public String getLawf_qty() {
        return lawf_qty;
    }

    public void setLawf_qty(String lawf_qty) {
        this.lawf_qty = lawf_qty;
    }

    public String getSecd_lawf_qty() {
        return secd_lawf_qty;
    }

    public void setSecd_lawf_qty(String secd_lawf_qty) {
        this.secd_lawf_qty = secd_lawf_qty;
    }

    public String getWt_sf_val() {
        return wt_sf_val;
    }

    public void setWt_sf_val(String wt_sf_val) {
        this.wt_sf_val = wt_sf_val;
    }

    public String getFst_sf_val() {
        return fst_sf_val;
    }

    public void setFst_sf_val(String fst_sf_val) {
        this.fst_sf_val = fst_sf_val;
    }

    public String getSecd_sf_val() {
        return secd_sf_val;
    }

    public void setSecd_sf_val(String secd_sf_val) {
        this.secd_sf_val = secd_sf_val;
    }

    public String getDcl_qty() {
        return dcl_qty;
    }

    public void setDcl_qty(String dcl_qty) {
        this.dcl_qty = dcl_qty;
    }

    public String getGross_wt() {
        return gross_wt;
    }

    public void setGross_wt(String gross_wt) {
        this.gross_wt = gross_wt;
    }

    public String getNet_wt() {
        return net_wt;
    }

    public void setNet_wt(String net_wt) {
        this.net_wt = net_wt;
    }

    public String getLvyrlf_modecd() {
        return lvyrlf_modecd;
    }

    public void setLvyrlf_modecd(String lvyrlf_modecd) {
        this.lvyrlf_modecd = lvyrlf_modecd;
    }

    public String getUcns_verno() {
        return ucns_verno;
    }

    public void setUcns_verno(String ucns_verno) {
        this.ucns_verno = ucns_verno;
    }

    public Integer getEntry_gds_seqno() {
        return entry_gds_seqno;
    }

    public void setEntry_gds_seqno(Integer entry_gds_seqno) {
        this.entry_gds_seqno = entry_gds_seqno;
    }

    public String getApply_tb_seqno() {
        return apply_tb_seqno;
    }

    public void setApply_tb_seqno(String apply_tb_seqno) {
        this.apply_tb_seqno = apply_tb_seqno;
    }

    public String getCly_markcd() {
        return cly_markcd;
    }

    public void setCly_markcd(String cly_markcd) {
        this.cly_markcd = cly_markcd;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public String getDestination_natcd() {
        return destination_natcd;
    }

    public void setDestination_natcd(String destination_natcd) {
        this.destination_natcd = destination_natcd;
    }

    public String getModf_markcd() {
        return modf_markcd;
    }

    public void setModf_markcd(String modf_markcd) {
        this.modf_markcd = modf_markcd;
    }

    public String getHead_etps_inner_invt_no() {
        return head_etps_inner_invt_no;
    }

    public void setHead_etps_inner_invt_no(String head_etps_inner_invt_no) {
        this.head_etps_inner_invt_no = head_etps_inner_invt_no;
    }

    public String getSurplus_nm() {
        return surplus_nm;
    }

    public void setSurplus_nm(String surplus_nm) {
        this.surplus_nm = surplus_nm;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUsecd() {
        return Usecd;
    }

    public void setUsecd(String usecd) {
        Usecd = usecd;
    }

    public String getEc_customs_code() {
        return ec_customs_code;
    }

    public void setEc_customs_code(String ec_customs_code) {
        this.ec_customs_code = ec_customs_code;
    }
}
