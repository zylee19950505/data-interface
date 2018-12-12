package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class BondInvtBsc {

    private String id;//ID
    private String bond_invt_no;//保税清单编号 QD+4位主管海关+2位年份+1位进出口标志+9位流水号,首次备案填写清单预录入编号
    private String chg_tms_cnt;//变更次数 首次备案时为0
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
    private String dcl_plc_cuscd;//申报地关区代码 关联海关参数库
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
    private String param1;//备用1 (SAS项目新增)
    private String param2;//备用2 (SAS项目新增)
    private String param3;//备用3 (SAS项目新增)
    private Date param4;//备用4 (SAS项目新增)
    private String need_entry_modified;//报关单同步修改标志 1：报关单未修改，其他情况为空 此标志用于标识清单修改后，报关单是否做过同步修改。

    private String levy_bl_amt;//计征金额 保税展示交易、一纳成品内销时，计算计征金额并反填此字段
    private String dcl_typecd;//申报类型 1-备案申请 2-变更申请 3-删除申请。备用字段，企业自主发起变更、删除功能转用，此功能明年上线。

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

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public Date getParam4() {
        return param4;
    }

    public void setParam4(Date param4) {
        this.param4 = param4;
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
}
