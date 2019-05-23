package com.xaeport.crossborder.data.entity;

import java.util.Date;

//跨境保税逻辑校验实体通用类
public class VerifyBondHead {

    //创建企业ID
    private String crt_ent_id;
    //校验结果字段
    private String vs_result;

    //订单数据
    private String guid;//企业系统生成36 位唯一序号（英文字母大写）
    private String app_type;//企业报送类型。1-新增2-变更3-删除。默认为1。
    private Date app_time;//企业报送时间。格式:YYYYMMDDhhmmss。
    private String app_status;//业务状态:1-暂存,2-申报,默认为2。
    private String order_type;//电子订单类型：I进口
    private String order_no;//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
    private String bill_no;//提运单号
    private String ebp_code;//电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。
    private String ebp_name;//电商平台的海关注册登记名称；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台名称为准。
    private String ebc_code;//电商企业的海关注册登记编号。
    private String ebc_name;//电商企业的海关注册登记名称。
    private String goods_value;//商品实际成交价，含非现金抵扣金额。
    private String freight;//不包含在商品价格中的运杂费，无则填写"0"。
    private String discount;//使用积分、虚拟货币、代金券等非现金支付金额，无则填写"0"。
    private String tax_total;//企业预先代扣的税款金额，无则填写0
    private String actural_paid;//商品价格+运杂费+代扣税款-非现金抵扣金额，与支付凭证的支付金额一致。
    private String currency;//限定为人民币，填写142。
    private String buyer_reg_no;//订购人的交易平台注册号。
    private String buyer_name;//订购人的真实姓名。
    private String buyer_telephone;//订购人的真实姓名。
    private String buyer_id_type;//1-身份证,2-其它。限定为身份证，填写1。
    private String buyer_id_number;//订购人的身份证件号码。
    private String pay_code;//支付企业的海关注册登记编号。
    private String payname;//支付企业在海关注册登记的企业名称。
    private String pay_transaction_id;//支付企业唯一的支付流水号。
    private String batch_numbers;//商品批次号。
    private String consignee;//收货人姓名，必须与电子运单的收货人姓名一致。
    private String consignee_telephone;//收货人联系电话，必须与电子运单的收货人电话一致
    private String consignee_address;//"收货地址，必须与电子运单的收货地址一致。"
    private String consignee_ditrict;//参照国家统计局公布的国家行政区划标准填制。
    private String note;//备注
    private String data_status;//数据状态
    private String business_type;//业务类型
    private String writing_mode;
    private String insured_fee;//物流企业实际收取的商品保价费用。
    private String gross_weight;//货物及其包装材料的重量之和，计量单位为千克。
    private String net_weight;//货物的毛重减去外包装材料后的重量，即货物本身的实际重量，计量单位为千克。

    //清单数据
    private String logistics_no;//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
    private String logistics_code;//物流企业的海关注册登记编号。
    private String logistics_name;//物流企业在海关注册登记的名称。
    private String cop_no;//企业内部标识单证的编号。
    private String pre_no;//电子口岸标识单证的编号。
    private String assure_code;//担保扣税的企业海关注册登记编号，只限清单的电商平台企业、电商企业、物流企业。
    private String ems_no;//保税模式必填，填写区内仓储企业在海关备案的账册编号，用于保税进口业务在特殊区域辅助系统记账（二线出区核减）。
    private String ie_flag;//I-进口,E-出口
    private Date decl_time;//申报日期，以海关计算机系统接受清单申报数据时记录的日期为准。格式:YYYYMMDD。
    private String customs_code;//接受清单申报的海关关区代码，参照JGS/T 18《海关关区代码》。
    private String port_code;//商品实际进出我国关境口岸海关的关区代码，参照JGS/T 18《海关关区代码》。
    private Date ie_date;//运载所申报商品的运输工具申报进境的日期，进口申报时无法确知相应的运输工具的实际进境日期时，免填。格式:YYYYMMDD
    private String agent_code;//申报单位的海关注册登记编号。
    private String agent_name;//申报单位在海关注册登记的名称。
    private String area_code;//保税模式必填，区内仓储企业的海关注册登记编号。
    private String area_name;//保税模式必填，区内仓储企业在海关注册登记的名称。
    private String trade_mode;//直购进口填写9610，保税进口填写1210。
    private String traf_mode;//填写海关标准的参数代码，参照《JGS-20 海关业务代码集》- 运输方式代码。直购进口指跨境段物流运输方式，保税进口指二线出区物流运输方式。
    private String traf_no;//直购进口必填。货物进出境的运输工具的名称或运输工具编号。填报内容应与运输部门向海关申报的载货清单所列相应内容一致；同报关单填制规范。保税进口免填。
    private String voyage_no;//直购进口必填。货物进出境的运输工具的航次编号。保税进口免填。
    private String loct_no;//针对同一申报地海关下有多个跨境电子商务的监管场所,需要填写区分
    private String license_no;//商务主管部门及其授权发证机关签发的进出口货物许可证件的编号
    private String country;//直购进口填写起始发出国家（地区）代码，参照《JGS-20 海关业务代码集》的国家（地区）代码表；保税进口填写代码“142”。
    private String wrap_type;//海关对进出口货物实际采用的外部包装方式的标识代码，采用1 位数字表示，如：木箱、纸箱、桶装、散装、托盘、包、油罐车等
    private String pack_no;//件数为包裹数量，限定为1。
    private String total_prices;//商品总价

    //核注清单数据
    private String id;//ID
    private String bond_invt_no;//保税清单编号 QD+4位主管海关+2位年份+1位进出口标志+9位流水号,首次备案填写清单预录入编号
    private Integer chg_tms_cnt;//变更次数 首次备案时为0
    private String invt_preent_no;//清单预录入编号 企业端生成的清单预录入号
    private String putrec_no;//备案编号 清单对应电子账册号或手册等
    private String etps_inner_invt_no;//企业内部清单编号 由企业自行编写，必须保证每份清单具有唯一编号
    private String bizop_etps_sccd;//经营企业社会信用代码
    private String bizop_etpsno;//经营企业编号 关联海关参数库
    private String bizop_etps_nm;//经营企业名称 关联海关参数库
    private String rvsngd_etps_sccd;//收发货企业社会信用代码
    private String rcvgd_etpsno;//收货企业编号 关联海关参数库
    private String rcvgd_etps_nm;//收货企业名称 关联海关参数库
    private String dcl_etps_sccd;//申报企业社会信用代码
    private String dcl_etpsno;//申报企业编号 关联海关参数库
    private String dcl_etps_nm;//申报企业名称 关联海关参数库
    private Date invt_dcl_time;//清单申报日期 企业端自动反填
    private Date entry_dcl_time;//报关单申报时间 清单报关时使用。海关端报关单入库时，反填并反馈企业端
    private String entry_no;//报关单编号 清单报关时使用。海关端报关单入库时，反填并反馈企业端
    private String rlt_invt_no;//关联清单编号 结转类专用，检控要求复杂，见需求文档
    private String rlt_putrec_no;//关联备案编号 结转类专用
    private String rlt_entry_no;//关联报关单编号 报关类型为关联报关时必填。二线取消报关的情况下使用，用于生成区外一般贸易报关单。暂未使用
    private String rlt_entry_bizop_etps_sccd;//关联报关单境内收发货人社会信用代码 报关类型为关联报关时必填。二线取消报关的情况下使用，用于生成区外一般贸易报关单。暂未使用
    private String rlt_entry_bizop_etpsno;//关联报关单境内收发货人编号 报关类型为关联报关时必填。二线取消报关的情况下使用，用于生成区外一般贸易报关单。暂未使用
    private String rlt_entry_bizop_etps_nm;//关联报关单境内收发货人名称 报关类型为关联报关时必填。二线取消报关的情况下使用，用于生成区外一般贸易报关单。暂未使用
    private String impexp_portcd;//进出境关别 关联海关参数库
    private String dcl_plc_cuscd;//主管海关 关联海关参数库
    private String impexp_markcd;//进出口标记代码 I：进口,E：出口
    private String mtpck_endprd_markcd;//料件成品标记代码 I：料件,E：成品
    private String supv_modecd;//监管方式代码 关联海关参数库
    private String trsp_modecd;//运输方式代码 关联海关参数库
    private String apply_no;//申请编号 深加工结转用
    private String stship_trsarv_natcd;//起运/运抵国(地区) 参照国别代码表(COUNTRY)
    private String dclcus_flag;//是否报关标志 1.报关2.非报关。系统需需校验报关标志是否正确
    private String dclcus_typecd;//报关类型代码 1.关联报关2.对应报关；"是否需要报关"字段填写为"是"，企业可选择"关联报关单"/"对应报关单"; "是否需要报关"字段填写为"否"，该项不可填。
    private Date prevd_time;//预核扣时间 清单预核扣完成日期
    private Date formal_vrfded_time;//正式核扣时间 清单正式核扣完成日期
    private String invt_iochkpt_stucd;//清单进出卡口状态代码 0-未出卡口 1-已出卡口。
    private String vrfded_markcd;//核扣标记代码 0-未核扣 1-预核扣 2-已核扣 3-已核销
    private String invt_stucd;//清单状态代码 1-申报 2-退单 3删单 0-审核通过
    private String vrfded_modecd;//核扣方式代码 D:正扣、F:反扣、N：不扣 NF：不扣但检查余量、E：保税仓扣减
    private String du_code;//核算代码 两位代码
    private String rmk;//备注
    private String bond_invt_typecd;//清单类型 标识清单类别，0：普通清单，1：集报清单，3：先入区后报关，4：简单加工，5：保税展示交易，6：区内流转，7：区港联动 8:保税电商,9：一纳成品内销默认为0：普通清单
    private String entry_stucd;//报关状态 标明对应（关联）报关单放行状态，目前只区分 0：未放行，1：已放行。该字段用于区域或物流账册的清单，该类型清单满足两个条件才能核扣：报关单被放行+货物全部过卡(SAS项目新增)
    private String passport_used_typecd;//核放单生成标志代码 1：未生成、2：部分生成、3：已生成，核放单生成时系统返填(SAS项目新增)
    private String need_entry_modified;//报关单同步修改标志 1：报关单未修改，其他情况为空 此标志用于标识清单修改后，报关单是否做过同步修改。
    private String levy_bl_amt;//计征金额 保税展示交易、一纳成品内销时，计算计征金额并反填此字段
    private String dcl_typecd;//申报类型 1-备案申请 2-变更申请 3-删除申请。备用字段，企业自主发起变更、删除功能转用，此功能明年上线。
    private String status;
    private String flag;
    private String invt_no;
    private String sum;
    private Integer original_nm;//核注清单原有可绑定数量
    private Integer usable_nm;//核注清单可绑定数量
    private Integer bound_nm;//绑定数量
    private String corr_entry_dcl_etps_sccd;//对应报关单申报单位社会统一信用代码
    private String corr_entry_dcl_etps_no;//对应报关单申报单位代码
    private String corr_entry_dcl_etps_nm;//对应报关单申报单位名称
    private String dec_type;//报关单类型

    //核放单回执
    private String passport_no;
    private String passport_typecd;
    private String sas_passport_preent_no;
    private String io_typecd;
    private String bind_typecd;
    private String master_cuscd;
    private String rlt_tb_typecd;
    private String rlt_no;
    private String areain_etpsno;
    private String areain_etps_nm;
    private String areain_etps_sccd;
    private String vehicle_no;
    private String vehicle_ic_no;
    private String container_no;
    private String vehicle_wt;
    private String vehicle_frame_no;
    private String vehicle_frame_wt;
    private String container_type;
    private String container_wt;
    private String total_wt;
    private String pass_collect_wt;
    private String wt_error;
    private String total_gross_wt;
    private String total_net_wt;
    private String dcl_er_conc;
    private Date dcl_time;
    private String pass_id;
    private String secd_pass_id;
    private Date pass_time;
    private Date secd_pass_time;
    private String stucd;
    private String emapv_markcd;
    private String logistics_stucd;
    private String owner_system;
    private String etps_preent_no;
    private String input_code;
    private String input_name;
    private String areain_oriact_no;

    public String getVs_result() {
        return vs_result;
    }

    public void setVs_result(String vs_result) {
        this.vs_result = vs_result;
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

    public String getTotal_prices() {
        return total_prices;
    }

    public void setTotal_prices(String total_prices) {
        this.total_prices = total_prices;
    }

    public String getCrt_ent_id() {
        return crt_ent_id;
    }

    public void setCrt_ent_id(String crt_ent_id) {
        this.crt_ent_id = crt_ent_id;
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

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getCorr_entry_dcl_etps_sccd() {
        return corr_entry_dcl_etps_sccd;
    }

    public void setCorr_entry_dcl_etps_sccd(String corr_entry_dcl_etps_sccd) {
        this.corr_entry_dcl_etps_sccd = corr_entry_dcl_etps_sccd;
    }

    public String getCorr_entry_dcl_etps_no() {
        return corr_entry_dcl_etps_no;
    }

    public void setCorr_entry_dcl_etps_no(String corr_entry_dcl_etps_no) {
        this.corr_entry_dcl_etps_no = corr_entry_dcl_etps_no;
    }

    public String getCorr_entry_dcl_etps_nm() {
        return corr_entry_dcl_etps_nm;
    }

    public void setCorr_entry_dcl_etps_nm(String corr_entry_dcl_etps_nm) {
        this.corr_entry_dcl_etps_nm = corr_entry_dcl_etps_nm;
    }

    public String getDec_type() {
        return dec_type;
    }

    public void setDec_type(String dec_type) {
        this.dec_type = dec_type;
    }

    public String getPassport_no() {
        return passport_no;
    }

    public void setPassport_no(String passport_no) {
        this.passport_no = passport_no;
    }

    public String getPassport_typecd() {
        return passport_typecd;
    }

    public void setPassport_typecd(String passport_typecd) {
        this.passport_typecd = passport_typecd;
    }

    public String getSas_passport_preent_no() {
        return sas_passport_preent_no;
    }

    public void setSas_passport_preent_no(String sas_passport_preent_no) {
        this.sas_passport_preent_no = sas_passport_preent_no;
    }

    public String getIo_typecd() {
        return io_typecd;
    }

    public void setIo_typecd(String io_typecd) {
        this.io_typecd = io_typecd;
    }

    public String getBind_typecd() {
        return bind_typecd;
    }

    public void setBind_typecd(String bind_typecd) {
        this.bind_typecd = bind_typecd;
    }

    public String getMaster_cuscd() {
        return master_cuscd;
    }

    public void setMaster_cuscd(String master_cuscd) {
        this.master_cuscd = master_cuscd;
    }

    public String getRlt_tb_typecd() {
        return rlt_tb_typecd;
    }

    public void setRlt_tb_typecd(String rlt_tb_typecd) {
        this.rlt_tb_typecd = rlt_tb_typecd;
    }

    public String getRlt_no() {
        return rlt_no;
    }

    public void setRlt_no(String rlt_no) {
        this.rlt_no = rlt_no;
    }

    public String getAreain_etpsno() {
        return areain_etpsno;
    }

    public void setAreain_etpsno(String areain_etpsno) {
        this.areain_etpsno = areain_etpsno;
    }

    public String getAreain_etps_nm() {
        return areain_etps_nm;
    }

    public void setAreain_etps_nm(String areain_etps_nm) {
        this.areain_etps_nm = areain_etps_nm;
    }

    public String getAreain_etps_sccd() {
        return areain_etps_sccd;
    }

    public void setAreain_etps_sccd(String areain_etps_sccd) {
        this.areain_etps_sccd = areain_etps_sccd;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getVehicle_ic_no() {
        return vehicle_ic_no;
    }

    public void setVehicle_ic_no(String vehicle_ic_no) {
        this.vehicle_ic_no = vehicle_ic_no;
    }

    public String getContainer_no() {
        return container_no;
    }

    public void setContainer_no(String container_no) {
        this.container_no = container_no;
    }

    public String getVehicle_wt() {
        return vehicle_wt;
    }

    public void setVehicle_wt(String vehicle_wt) {
        this.vehicle_wt = vehicle_wt;
    }

    public String getVehicle_frame_no() {
        return vehicle_frame_no;
    }

    public void setVehicle_frame_no(String vehicle_frame_no) {
        this.vehicle_frame_no = vehicle_frame_no;
    }

    public String getVehicle_frame_wt() {
        return vehicle_frame_wt;
    }

    public void setVehicle_frame_wt(String vehicle_frame_wt) {
        this.vehicle_frame_wt = vehicle_frame_wt;
    }

    public String getContainer_type() {
        return container_type;
    }

    public void setContainer_type(String container_type) {
        this.container_type = container_type;
    }

    public String getContainer_wt() {
        return container_wt;
    }

    public void setContainer_wt(String container_wt) {
        this.container_wt = container_wt;
    }

    public String getTotal_wt() {
        return total_wt;
    }

    public void setTotal_wt(String total_wt) {
        this.total_wt = total_wt;
    }

    public String getPass_collect_wt() {
        return pass_collect_wt;
    }

    public void setPass_collect_wt(String pass_collect_wt) {
        this.pass_collect_wt = pass_collect_wt;
    }

    public String getWt_error() {
        return wt_error;
    }

    public void setWt_error(String wt_error) {
        this.wt_error = wt_error;
    }

    public String getTotal_gross_wt() {
        return total_gross_wt;
    }

    public void setTotal_gross_wt(String total_gross_wt) {
        this.total_gross_wt = total_gross_wt;
    }

    public String getTotal_net_wt() {
        return total_net_wt;
    }

    public void setTotal_net_wt(String total_net_wt) {
        this.total_net_wt = total_net_wt;
    }

    public String getDcl_er_conc() {
        return dcl_er_conc;
    }

    public void setDcl_er_conc(String dcl_er_conc) {
        this.dcl_er_conc = dcl_er_conc;
    }

    public Date getDcl_time() {
        return dcl_time;
    }

    public void setDcl_time(Date dcl_time) {
        this.dcl_time = dcl_time;
    }

    public String getPass_id() {
        return pass_id;
    }

    public void setPass_id(String pass_id) {
        this.pass_id = pass_id;
    }

    public String getSecd_pass_id() {
        return secd_pass_id;
    }

    public void setSecd_pass_id(String secd_pass_id) {
        this.secd_pass_id = secd_pass_id;
    }

    public Date getPass_time() {
        return pass_time;
    }

    public void setPass_time(Date pass_time) {
        this.pass_time = pass_time;
    }

    public Date getSecd_pass_time() {
        return secd_pass_time;
    }

    public void setSecd_pass_time(Date secd_pass_time) {
        this.secd_pass_time = secd_pass_time;
    }

    public String getStucd() {
        return stucd;
    }

    public void setStucd(String stucd) {
        this.stucd = stucd;
    }

    public String getEmapv_markcd() {
        return emapv_markcd;
    }

    public void setEmapv_markcd(String emapv_markcd) {
        this.emapv_markcd = emapv_markcd;
    }

    public String getLogistics_stucd() {
        return logistics_stucd;
    }

    public void setLogistics_stucd(String logistics_stucd) {
        this.logistics_stucd = logistics_stucd;
    }

    public String getOwner_system() {
        return owner_system;
    }

    public void setOwner_system(String owner_system) {
        this.owner_system = owner_system;
    }

    public String getEtps_preent_no() {
        return etps_preent_no;
    }

    public void setEtps_preent_no(String etps_preent_no) {
        this.etps_preent_no = etps_preent_no;
    }

    public String getInput_code() {
        return input_code;
    }

    public void setInput_code(String input_code) {
        this.input_code = input_code;
    }

    public String getInput_name() {
        return input_name;
    }

    public void setInput_name(String input_name) {
        this.input_name = input_name;
    }

    public String getAreain_oriact_no() {
        return areain_oriact_no;
    }

    public void setAreain_oriact_no(String areain_oriact_no) {
        this.areain_oriact_no = areain_oriact_no;
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

    public String getGoods_value() {
        return goods_value;
    }

    public void setGoods_value(String goods_value) {
        this.goods_value = goods_value;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTax_total() {
        return tax_total;
    }

    public void setTax_total(String tax_total) {
        this.tax_total = tax_total;
    }

    public String getActural_paid() {
        return actural_paid;
    }

    public void setActural_paid(String actural_paid) {
        this.actural_paid = actural_paid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBuyer_reg_no() {
        return buyer_reg_no;
    }

    public void setBuyer_reg_no(String buyer_reg_no) {
        this.buyer_reg_no = buyer_reg_no;
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

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPayname() {
        return payname;
    }

    public void setPayname(String payname) {
        this.payname = payname;
    }

    public String getPay_transaction_id() {
        return pay_transaction_id;
    }

    public void setPay_transaction_id(String pay_transaction_id) {
        this.pay_transaction_id = pay_transaction_id;
    }

    public String getBatch_numbers() {
        return batch_numbers;
    }

    public void setBatch_numbers(String batch_numbers) {
        this.batch_numbers = batch_numbers;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_telephone() {
        return consignee_telephone;
    }

    public void setConsignee_telephone(String consignee_telephone) {
        this.consignee_telephone = consignee_telephone;
    }

    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
    }

    public String getConsignee_ditrict() {
        return consignee_ditrict;
    }

    public void setConsignee_ditrict(String consignee_ditrict) {
        this.consignee_ditrict = consignee_ditrict;
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

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    public String getWriting_mode() {
        return writing_mode;
    }

    public void setWriting_mode(String writing_mode) {
        this.writing_mode = writing_mode;
    }

    public String getInsured_fee() {
        return insured_fee;
    }

    public void setInsured_fee(String insured_fee) {
        this.insured_fee = insured_fee;
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

    public Integer getChg_tms_cnt() {
        return chg_tms_cnt;
    }

    public void setChg_tms_cnt(Integer chg_tms_cnt) {
        this.chg_tms_cnt = chg_tms_cnt;
    }

    public String getInvt_preent_no() {
        return invt_preent_no;
    }

    public void setInvt_preent_no(String invt_preent_no) {
        this.invt_preent_no = invt_preent_no;
    }

    public String getPutrec_no() {
        return putrec_no;
    }

    public void setPutrec_no(String putrec_no) {
        this.putrec_no = putrec_no;
    }

    public String getEtps_inner_invt_no() {
        return etps_inner_invt_no;
    }

    public void setEtps_inner_invt_no(String etps_inner_invt_no) {
        this.etps_inner_invt_no = etps_inner_invt_no;
    }

    public String getBizop_etps_sccd() {
        return bizop_etps_sccd;
    }

    public void setBizop_etps_sccd(String bizop_etps_sccd) {
        this.bizop_etps_sccd = bizop_etps_sccd;
    }

    public String getBizop_etpsno() {
        return bizop_etpsno;
    }

    public void setBizop_etpsno(String bizop_etpsno) {
        this.bizop_etpsno = bizop_etpsno;
    }

    public String getBizop_etps_nm() {
        return bizop_etps_nm;
    }

    public void setBizop_etps_nm(String bizop_etps_nm) {
        this.bizop_etps_nm = bizop_etps_nm;
    }

    public String getRvsngd_etps_sccd() {
        return rvsngd_etps_sccd;
    }

    public void setRvsngd_etps_sccd(String rvsngd_etps_sccd) {
        this.rvsngd_etps_sccd = rvsngd_etps_sccd;
    }

    public String getRcvgd_etpsno() {
        return rcvgd_etpsno;
    }

    public void setRcvgd_etpsno(String rcvgd_etpsno) {
        this.rcvgd_etpsno = rcvgd_etpsno;
    }

    public String getRcvgd_etps_nm() {
        return rcvgd_etps_nm;
    }

    public void setRcvgd_etps_nm(String rcvgd_etps_nm) {
        this.rcvgd_etps_nm = rcvgd_etps_nm;
    }

    public String getDcl_etps_sccd() {
        return dcl_etps_sccd;
    }

    public void setDcl_etps_sccd(String dcl_etps_sccd) {
        this.dcl_etps_sccd = dcl_etps_sccd;
    }

    public String getDcl_etpsno() {
        return dcl_etpsno;
    }

    public void setDcl_etpsno(String dcl_etpsno) {
        this.dcl_etpsno = dcl_etpsno;
    }

    public String getDcl_etps_nm() {
        return dcl_etps_nm;
    }

    public void setDcl_etps_nm(String dcl_etps_nm) {
        this.dcl_etps_nm = dcl_etps_nm;
    }

    public Date getInvt_dcl_time() {
        return invt_dcl_time;
    }

    public void setInvt_dcl_time(Date invt_dcl_time) {
        this.invt_dcl_time = invt_dcl_time;
    }

    public Date getEntry_dcl_time() {
        return entry_dcl_time;
    }

    public void setEntry_dcl_time(Date entry_dcl_time) {
        this.entry_dcl_time = entry_dcl_time;
    }

    public String getEntry_no() {
        return entry_no;
    }

    public void setEntry_no(String entry_no) {
        this.entry_no = entry_no;
    }

    public String getRlt_invt_no() {
        return rlt_invt_no;
    }

    public void setRlt_invt_no(String rlt_invt_no) {
        this.rlt_invt_no = rlt_invt_no;
    }

    public String getRlt_putrec_no() {
        return rlt_putrec_no;
    }

    public void setRlt_putrec_no(String rlt_putrec_no) {
        this.rlt_putrec_no = rlt_putrec_no;
    }

    public String getRlt_entry_no() {
        return rlt_entry_no;
    }

    public void setRlt_entry_no(String rlt_entry_no) {
        this.rlt_entry_no = rlt_entry_no;
    }

    public String getRlt_entry_bizop_etps_sccd() {
        return rlt_entry_bizop_etps_sccd;
    }

    public void setRlt_entry_bizop_etps_sccd(String rlt_entry_bizop_etps_sccd) {
        this.rlt_entry_bizop_etps_sccd = rlt_entry_bizop_etps_sccd;
    }

    public String getRlt_entry_bizop_etpsno() {
        return rlt_entry_bizop_etpsno;
    }

    public void setRlt_entry_bizop_etpsno(String rlt_entry_bizop_etpsno) {
        this.rlt_entry_bizop_etpsno = rlt_entry_bizop_etpsno;
    }

    public String getRlt_entry_bizop_etps_nm() {
        return rlt_entry_bizop_etps_nm;
    }

    public void setRlt_entry_bizop_etps_nm(String rlt_entry_bizop_etps_nm) {
        this.rlt_entry_bizop_etps_nm = rlt_entry_bizop_etps_nm;
    }

    public String getImpexp_portcd() {
        return impexp_portcd;
    }

    public void setImpexp_portcd(String impexp_portcd) {
        this.impexp_portcd = impexp_portcd;
    }

    public String getDcl_plc_cuscd() {
        return dcl_plc_cuscd;
    }

    public void setDcl_plc_cuscd(String dcl_plc_cuscd) {
        this.dcl_plc_cuscd = dcl_plc_cuscd;
    }

    public String getImpexp_markcd() {
        return impexp_markcd;
    }

    public void setImpexp_markcd(String impexp_markcd) {
        this.impexp_markcd = impexp_markcd;
    }

    public String getMtpck_endprd_markcd() {
        return mtpck_endprd_markcd;
    }

    public void setMtpck_endprd_markcd(String mtpck_endprd_markcd) {
        this.mtpck_endprd_markcd = mtpck_endprd_markcd;
    }

    public String getSupv_modecd() {
        return supv_modecd;
    }

    public void setSupv_modecd(String supv_modecd) {
        this.supv_modecd = supv_modecd;
    }

    public String getTrsp_modecd() {
        return trsp_modecd;
    }

    public void setTrsp_modecd(String trsp_modecd) {
        this.trsp_modecd = trsp_modecd;
    }

    public String getApply_no() {
        return apply_no;
    }

    public void setApply_no(String apply_no) {
        this.apply_no = apply_no;
    }

    public String getStship_trsarv_natcd() {
        return stship_trsarv_natcd;
    }

    public void setStship_trsarv_natcd(String stship_trsarv_natcd) {
        this.stship_trsarv_natcd = stship_trsarv_natcd;
    }

    public String getDclcus_flag() {
        return dclcus_flag;
    }

    public void setDclcus_flag(String dclcus_flag) {
        this.dclcus_flag = dclcus_flag;
    }

    public String getDclcus_typecd() {
        return dclcus_typecd;
    }

    public void setDclcus_typecd(String dclcus_typecd) {
        this.dclcus_typecd = dclcus_typecd;
    }

    public Date getPrevd_time() {
        return prevd_time;
    }

    public void setPrevd_time(Date prevd_time) {
        this.prevd_time = prevd_time;
    }

    public Date getFormal_vrfded_time() {
        return formal_vrfded_time;
    }

    public void setFormal_vrfded_time(Date formal_vrfded_time) {
        this.formal_vrfded_time = formal_vrfded_time;
    }

    public String getInvt_iochkpt_stucd() {
        return invt_iochkpt_stucd;
    }

    public void setInvt_iochkpt_stucd(String invt_iochkpt_stucd) {
        this.invt_iochkpt_stucd = invt_iochkpt_stucd;
    }

    public String getVrfded_markcd() {
        return vrfded_markcd;
    }

    public void setVrfded_markcd(String vrfded_markcd) {
        this.vrfded_markcd = vrfded_markcd;
    }

    public String getInvt_stucd() {
        return invt_stucd;
    }

    public void setInvt_stucd(String invt_stucd) {
        this.invt_stucd = invt_stucd;
    }

    public String getVrfded_modecd() {
        return vrfded_modecd;
    }

    public void setVrfded_modecd(String vrfded_modecd) {
        this.vrfded_modecd = vrfded_modecd;
    }

    public String getDu_code() {
        return du_code;
    }

    public void setDu_code(String du_code) {
        this.du_code = du_code;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public String getBond_invt_typecd() {
        return bond_invt_typecd;
    }

    public void setBond_invt_typecd(String bond_invt_typecd) {
        this.bond_invt_typecd = bond_invt_typecd;
    }

    public String getEntry_stucd() {
        return entry_stucd;
    }

    public void setEntry_stucd(String entry_stucd) {
        this.entry_stucd = entry_stucd;
    }

    public String getPassport_used_typecd() {
        return passport_used_typecd;
    }

    public void setPassport_used_typecd(String passport_used_typecd) {
        this.passport_used_typecd = passport_used_typecd;
    }

    public String getNeed_entry_modified() {
        return need_entry_modified;
    }

    public void setNeed_entry_modified(String need_entry_modified) {
        this.need_entry_modified = need_entry_modified;
    }

    public String getLevy_bl_amt() {
        return levy_bl_amt;
    }

    public void setLevy_bl_amt(String levy_bl_amt) {
        this.levy_bl_amt = levy_bl_amt;
    }

    public String getDcl_typecd() {
        return dcl_typecd;
    }

    public void setDcl_typecd(String dcl_typecd) {
        this.dcl_typecd = dcl_typecd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getInvt_no() {
        return invt_no;
    }

    public void setInvt_no(String invt_no) {
        this.invt_no = invt_no;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public Integer getOriginal_nm() {
        return original_nm;
    }

    public void setOriginal_nm(Integer original_nm) {
        this.original_nm = original_nm;
    }

    public Integer getUsable_nm() {
        return usable_nm;
    }

    public void setUsable_nm(Integer usable_nm) {
        this.usable_nm = usable_nm;
    }

    public Integer getBound_nm() {
        return bound_nm;
    }

    public void setBound_nm(Integer bound_nm) {
        this.bound_nm = bound_nm;
    }
}
