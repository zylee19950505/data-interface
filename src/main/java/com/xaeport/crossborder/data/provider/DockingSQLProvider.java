package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class DockingSQLProvider extends BaseSQLProvider {

    public String insertImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_ORDER_HEAD");
                if (!StringUtils.isEmpty(impOrderHead.getGuid())) {
                    VALUES("guid", "#{impOrderHead.guid}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Type())) {
                    VALUES("app_Type", "#{impOrderHead.app_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Time())) {
                    VALUES("app_Time", "#{impOrderHead.app_Time}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Status())) {
                    VALUES("app_Status", "#{impOrderHead.app_Status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getOrder_Type())) {
                    VALUES("order_Type", "#{impOrderHead.order_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getOrder_No())) {
                    VALUES("order_No", "#{impOrderHead.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBill_No())) {
                    VALUES("bill_No", "#{impOrderHead.bill_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbp_Code())) {
                    VALUES("ebp_Code", "#{impOrderHead.ebp_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbp_Name())) {
                    VALUES("ebp_Name", "#{impOrderHead.ebp_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbc_Code())) {
                    VALUES("ebc_Code", "#{impOrderHead.ebc_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbc_Name())) {
                    VALUES("ebc_Name", "#{impOrderHead.ebc_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getGoods_Value())) {
                    VALUES("goods_Value", "#{impOrderHead.goods_Value}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getFreight())) {
                    VALUES("freight", "#{impOrderHead.freight}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getDiscount())) {
                    VALUES("discount", "#{impOrderHead.discount}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getTax_Total())) {
                    VALUES("tax_Total", "#{impOrderHead.tax_Total}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getActural_Paid())) {
                    VALUES("actural_Paid", "#{impOrderHead.actural_Paid}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getCurrency())) {
                    VALUES("currency", "#{impOrderHead.currency}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Reg_No())) {
                    VALUES("buyer_Reg_No", "#{impOrderHead.buyer_Reg_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Name())) {
                    VALUES("buyer_Name", "#{impOrderHead.buyer_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_TelePhone())) {
                    VALUES("buyer_telePhone", "#{impOrderHead.buyer_TelePhone}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Id_Type())) {
                    VALUES("buyer_Id_Type", "#{impOrderHead.buyer_Id_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Id_Number())) {
                    VALUES("buyer_Id_Number", "#{impOrderHead.buyer_Id_Number}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPay_Code())) {
                    VALUES("pay_Code", "#{impOrderHead.pay_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPayName())) {
                    VALUES("payName", "#{impOrderHead.payName}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPay_Transaction_Id())) {
                    VALUES("pay_Transaction_Id", "#{impOrderHead.pay_Transaction_Id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBatch_Numbers())) {
                    VALUES("batch_Numbers", "#{impOrderHead.batch_Numbers}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee())) {
                    VALUES("consignee", "#{impOrderHead.consignee}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Telephone())) {
                    VALUES("consignee_Telephone", "#{impOrderHead.consignee_Telephone}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Address())) {
                    VALUES("consignee_Address", "#{impOrderHead.consignee_Address}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Ditrict())) {
                    VALUES("consignee_Ditrict", "#{impOrderHead.consignee_Ditrict}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getNote())) {
                    VALUES("note", "#{impOrderHead.note}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getCrt_id())) {
                    VALUES("crt_id", "#{impOrderHead.crt_id}");
                    VALUES("crt_tm", "sysdate");
                    VALUES("upd_id", "#{impOrderHead.crt_id}");
                    VALUES("upd_tm", "sysdate");
                }
                if (!StringUtils.isEmpty(impOrderHead.getData_status())) {
                    VALUES("data_status", "#{impOrderHead.data_status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEnt_id())) {
                    VALUES("ent_id", "#{impOrderHead.ent_id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEnt_name())) {
                    VALUES("ent_name", "#{impOrderHead.ent_name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEnt_customs_code())) {
                    VALUES("ent_customs_code", "#{impOrderHead.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBusiness_type())) {
                    VALUES("business_type", "#{impOrderHead.business_type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getInsured_fee())) {
                    VALUES("insured_fee", "#{impOrderHead.insured_fee}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getGross_weight())) {
                    VALUES("gross_weight", "#{impOrderHead.gross_weight}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getNet_weight())) {
                    VALUES("net_weight", "#{impOrderHead.net_weight}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getTrade_mode())) {
                    VALUES("trade_mode", "#{impOrderHead.trade_mode}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPort_code())) {
                    VALUES("port_code", "#{impOrderHead.port_code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impOrderHead.writing_mode}");
                }
            }
        }.toString();
    }

    public String insertImpOrderBody(@Param("impOrderBody") ImpOrderBody impOrderBody) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_ORDER_BODY");
                if (!StringUtils.isEmpty(impOrderBody.getG_num())) {
                    VALUES("g_num", "#{impOrderBody.g_num}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getHead_guid())) {
                    VALUES("head_guid", "#{impOrderBody.head_guid}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getOrder_No())) {
                    VALUES("order_No", "#{impOrderBody.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getItem_No())) {
                    VALUES("item_No", "#{impOrderBody.item_No}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getItem_Name())) {
                    VALUES("item_Name", "#{impOrderBody.item_Name}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getItem_Describe())) {
                    VALUES("item_Describe", "#{impOrderBody.item_Describe}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getBar_Code())) {
                    VALUES("bar_Code", "#{impOrderBody.bar_Code}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getUnit())) {
                    VALUES("unit", "#{impOrderBody.unit}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getQty())) {
                    VALUES("qty", "#{impOrderBody.qty}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getPrice())) {
                    VALUES("price", "#{impOrderBody.price}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getTotal_Price())) {
                    VALUES("total_Price", "#{impOrderBody.total_Price}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getCurrency())) {
                    VALUES("currency", "#{impOrderBody.currency}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getCountry())) {
                    VALUES("country", "#{impOrderBody.country}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getNote())) {
                    VALUES("note", "#{impOrderBody.note}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getG_Model())) {
                    VALUES("g_Model", "#{impOrderBody.g_Model}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getGds_seqno())) {
                    VALUES("gds_seqno", "#{impOrderBody.gds_seqno}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impOrderBody.writing_mode}");
                }
            }
        }.toString();
    }

    public String insertBondInvtBsc(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc) {
        return new SQL() {
            {
                INSERT_INTO("T_BOND_INVT_BSC");
                if (!StringUtils.isEmpty(bondInvtBsc.getId())) {
                    VALUES("ID", "#{bondInvtBsc.id}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBond_invt_no())) {
                    VALUES("BOND_INVT_NO", "#{bondInvtBsc.bond_invt_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getEtps_inner_invt_no())) {
                    VALUES("ETPS_INNER_INVT_NO", "#{bondInvtBsc.etps_inner_invt_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getChg_tms_cnt())) {
                    VALUES("CHG_TMS_CNT", "#{bondInvtBsc.chg_tms_cnt}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getInvt_preent_no())) {
                    VALUES("INVT_PREENT_NO", "#{bondInvtBsc.invt_preent_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getPutrec_no())) {
                    VALUES("PUTREC_NO", "#{bondInvtBsc.putrec_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_plc_cuscd())) {
                    VALUES("DCL_PLC_CUSCD", "#{bondInvtBsc.dcl_plc_cuscd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_etps_sccd())) {
                    VALUES("DCL_ETPS_SCCD", "#{bondInvtBsc.dcl_etps_sccd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_etpsno())) {
                    VALUES("DCL_ETPSNO", "#{bondInvtBsc.dcl_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_etps_nm())) {
                    VALUES("DCL_ETPS_NM", "#{bondInvtBsc.dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDclcus_typecd())) {
                    VALUES("DCLCUS_TYPECD", "#{bondInvtBsc.dclcus_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDec_type())) {
                    VALUES("DEC_TYPE", "#{bondInvtBsc.dec_type}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getImpexp_markcd())) {
                    VALUES("IMPEXP_MARKCD", "#{bondInvtBsc.impexp_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getTrsp_modecd())) {
                    VALUES("TRSP_MODECD", "#{bondInvtBsc.trsp_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getStship_trsarv_natcd())) {
                    VALUES("STSHIP_TRSARV_NATCD", "#{bondInvtBsc.stship_trsarv_natcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCorr_entry_dcl_etps_sccd())) {
                    VALUES("CORR_ENTRY_DCL_ETPS_SCCD", "#{bondInvtBsc.corr_entry_dcl_etps_sccd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCorr_entry_dcl_etps_no())) {
                    VALUES("CORR_ENTRY_DCL_ETPS_NO", "#{bondInvtBsc.corr_entry_dcl_etps_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCorr_entry_dcl_etps_nm())) {
                    VALUES("CORR_ENTRY_DCL_ETPS_NM", "#{bondInvtBsc.corr_entry_dcl_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCrt_user())) {
                    VALUES("CRT_USER", "#{bondInvtBsc.crt_user}");
                    VALUES("CRT_TIME", "sysdate");
                    VALUES("UPD_USER", "#{bondInvtBsc.crt_user}");
                    VALUES("UPD_TIME", "sysdate");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCrt_ent_id())) {
                    VALUES("CRT_ENT_ID", "#{bondInvtBsc.crt_ent_id}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getCrt_ent_name())) {
                    VALUES("CRT_ENT_NAME", "#{bondInvtBsc.crt_ent_name}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBizop_etpsno())) {
                    VALUES("BIZOP_ETPSNO", "#{bondInvtBsc.bizop_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBizop_etps_nm())) {
                    VALUES("BIZOP_ETPS_NM", "#{bondInvtBsc.bizop_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBizop_etps_sccd())) {
                    VALUES("BIZOP_ETPS_SCCD", "#{bondInvtBsc.bizop_etps_sccd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRvsngd_etps_sccd())) {
                    VALUES("RVSNGD_ETPS_SCCD", "#{bondInvtBsc.rvsngd_etps_sccd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRcvgd_etpsno())) {
                    VALUES("RCVGD_ETPSNO", "#{bondInvtBsc.rcvgd_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRcvgd_etps_nm())) {
                    VALUES("RCVGD_ETPS_NM", "#{bondInvtBsc.rcvgd_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getOriginal_nm())) {
                    VALUES("ORIGINAL_NM", "#{bondInvtBsc.original_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getUsable_nm())) {
                    VALUES("USABLE_NM", "#{bondInvtBsc.usable_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBound_nm())) {
                    VALUES("BOUND_NM", "#{bondInvtBsc.bound_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getEc_customs_code())) {
                    VALUES("EC_CUSTOMS_CODE", "#{bondInvtBsc.ec_customs_code}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getMtpck_endprd_markcd())) {
                    VALUES("MTPCK_ENDPRD_MARKCD", "#{bondInvtBsc.mtpck_endprd_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getSupv_modecd())) {
                    VALUES("SUPV_MODECD", "#{bondInvtBsc.supv_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDclcus_flag())) {
                    VALUES("DCLCUS_FLAG", "#{bondInvtBsc.dclcus_flag}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBond_invt_typecd())) {
                    VALUES("BOND_INVT_TYPECD", "#{bondInvtBsc.bond_invt_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDcl_typecd())) {
                    VALUES("DCL_TYPECD", "#{bondInvtBsc.dcl_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getInvt_dcl_time())) {
                    VALUES("INVT_DCL_TIME", "#{bondInvtBsc.invt_dcl_time}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getEntry_dcl_time())) {
                    VALUES("ENTRY_DCL_TIME", "#{bondInvtBsc.entry_dcl_time}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getEntry_no())) {
                    VALUES("ENTRY_NO", "#{bondInvtBsc.entry_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRlt_invt_no())) {
                    VALUES("RLT_INVT_NO", "#{bondInvtBsc.rlt_invt_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRlt_putrec_no())) {
                    VALUES("RLT_PUTREC_NO", "#{bondInvtBsc.rlt_putrec_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRlt_entry_no())) {
                    VALUES("RLT_ENTRY_NO", "#{bondInvtBsc.rlt_entry_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRlt_entry_bizop_etps_sccd())) {
                    VALUES("RLT_ENTRY_BIZOP_ETPS_SCCD", "#{bondInvtBsc.rlt_entry_bizop_etps_sccd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRlt_entry_bizop_etpsno())) {
                    VALUES("RLT_ENTRY_BIZOP_ETPSNO", "#{bondInvtBsc.rlt_entry_bizop_etpsno}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRlt_entry_bizop_etps_nm())) {
                    VALUES("RLT_ENTRY_BIZOP_ETPS_NM", "#{bondInvtBsc.rlt_entry_bizop_etps_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getImpexp_portcd())) {
                    VALUES("IMPEXP_PORTCD", "#{bondInvtBsc.impexp_portcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getApply_no())) {
                    VALUES("APPLY_NO", "#{bondInvtBsc.apply_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getPrevd_time())) {
                    VALUES("PREVD_TIME", "#{bondInvtBsc.prevd_time}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getFormal_vrfded_time())) {
                    VALUES("FORMAL_VRFDED_TIME", "#{bondInvtBsc.formal_vrfded_time}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getInvt_iochkpt_stucd())) {
                    VALUES("INVT_IOCHKPT_STUCD", "#{bondInvtBsc.invt_iochkpt_stucd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getVrfded_markcd())) {
                    VALUES("VRFDED_MARKCD", "#{bondInvtBsc.vrfded_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getInvt_stucd())) {
                    VALUES("INVT_STUCD", "#{bondInvtBsc.invt_stucd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getVrfded_modecd())) {
                    VALUES("VRFDED_MODECD", "#{bondInvtBsc.vrfded_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getDu_code())) {
                    VALUES("DU_CODE", "#{bondInvtBsc.du_code}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getRmk())) {
                    VALUES("RMK", "#{bondInvtBsc.rmk}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getEntry_stucd())) {
                    VALUES("ENTRY_STUCD", "#{bondInvtBsc.entry_stucd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getPassport_used_typecd())) {
                    VALUES("PASSPORT_USED_TYPECD", "#{bondInvtBsc.passport_used_typecd}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getNeed_entry_modified())) {
                    VALUES("NEED_ENTRY_MODIFIED", "#{bondInvtBsc.need_entry_modified}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getLevy_bl_amt())) {
                    VALUES("LEVY_BL_AMT", "#{bondInvtBsc.levy_bl_amt}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getStatus())) {
                    VALUES("STATUS", "#{bondInvtBsc.status}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getFlag())) {
                    VALUES("FLAG", "#{bondInvtBsc.flag}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getInvt_no())) {
                    VALUES("INVT_NO", "#{bondInvtBsc.invt_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getBusiness_type())) {
                    VALUES("BUSINESS_TYPE", "#{bondInvtBsc.business_type}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{bondInvtBsc.writing_mode}");
                }
            }
        }.toString();
    }

    public String insertBondInvtDt(@Param("bondInvtDt") BondInvtDt bondInvtDt) {
        return new SQL() {
            {
                INSERT_INTO("T_BOND_INVT_DT t");
                if (!StringUtils.isEmpty(bondInvtDt.getId())) {
                    VALUES("ID", "#{bondInvtDt.id}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getBond_invt_no())) {
                    VALUES("BOND_INVT_NO", "#{bondInvtDt.bond_invt_no}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getChg_tms_cnt())) {
                    VALUES("CHG_TMS_CNT", "#{bondInvtDt.chg_tms_cnt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getHead_etps_inner_invt_no())) {
                    VALUES("HEAD_ETPS_INNER_INVT_NO", "#{bondInvtDt.head_etps_inner_invt_no}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGds_seqno())) {
                    VALUES("GDS_SEQNO", "#{bondInvtDt.gds_seqno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getPutrec_seqno())) {
                    VALUES("PUTREC_SEQNO", "#{bondInvtDt.putrec_seqno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGds_mtno())) {
                    VALUES("GDS_MTNO", "#{bondInvtDt.gds_mtno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGdecd())) {
                    VALUES("GDECD", "#{bondInvtDt.gdecd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGds_nm())) {
                    VALUES("GDS_NM", "#{bondInvtDt.gds_nm}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGds_spcf_model_desc())) {
                    VALUES("GDS_SPCF_MODEL_DESC", "#{bondInvtDt.gds_spcf_model_desc}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_qty())) {
                    VALUES("DCL_QTY", "#{bondInvtDt.dcl_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_qty())) {
                    VALUES("SURPLUS_NM", "#{bondInvtDt.dcl_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_unitcd())) {
                    VALUES("DCL_UNITCD", "#{bondInvtDt.dcl_unitcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getLawf_qty())) {
                    VALUES("LAWF_QTY", "#{bondInvtDt.lawf_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getLawf_unitcd())) {
                    VALUES("LAWF_UNITCD", "#{bondInvtDt.lawf_unitcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getSecd_lawf_qty())) {
                    VALUES("SECD_LAWF_QTY", "#{bondInvtDt.secd_lawf_qty}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getSecd_lawf_unitcd())) {
                    VALUES("SECD_LAWF_UNITCD", "#{bondInvtDt.secd_lawf_unitcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_total_amt())) {
                    VALUES("DCL_TOTAL_AMT", "#{bondInvtDt.dcl_total_amt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_currcd())) {
                    VALUES("DCL_CURRCD", "#{bondInvtDt.dcl_currcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getNatcd())) {
                    VALUES("NATCD", "#{bondInvtDt.natcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getLvyrlf_modecd())) {
                    VALUES("LVYRLF_MODECD", "#{bondInvtDt.lvyrlf_modecd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getRmk())) {
                    VALUES("RMK", "#{bondInvtDt.rmk}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getCrt_user())) {
                    VALUES("CRT_USER", "#{bondInvtDt.crt_user}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getId())) {
                    VALUES("CRT_TIME", "sysdate");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getGross_wt())) {
                    VALUES("GROSS_WT", "#{bondInvtDt.gross_wt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getNet_wt())) {
                    VALUES("NET_WT", "#{bondInvtDt.net_wt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDcl_uprc_amt())) {
                    VALUES("DCL_UPRC_AMT", "#{bondInvtDt.dcl_uprc_amt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getUsd_stat_total_amt())) {
                    VALUES("USD_STAT_TOTAL_AMT", "#{bondInvtDt.usd_stat_total_amt}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getUsecd())) {
                    VALUES("USECD", "#{bondInvtDt.usecd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getEc_customs_code())) {
                    VALUES("EC_CUSTOMS_CODE", "#{bondInvtDt.ec_customs_code}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getDestination_natcd())) {
                    VALUES("DESTINATION_NATCD", "#{bondInvtDt.destination_natcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getModf_markcd())) {
                    VALUES("MODF_MARKCD", "#{bondInvtDt.modf_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getEntry_gds_seqno())) {
                    VALUES("ENTRY_GDS_SEQNO", "#{bondInvtDt.entry_gds_seqno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getWt_sf_val())) {
                    VALUES("WT_SF_VAL", "#{bondInvtDt.wt_sf_val}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getFst_sf_val())) {
                    VALUES("FST_SF_VAL", "#{bondInvtDt.fst_sf_val}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getSecd_sf_val())) {
                    VALUES("SECD_SF_VAL", "#{bondInvtDt.secd_sf_val}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getUcns_verno())) {
                    VALUES("UCNS_VERNO", "#{bondInvtDt.ucns_verno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getApply_tb_seqno())) {
                    VALUES("APPLY_TB_SEQNO", "#{bondInvtDt.apply_tb_seqno}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getCly_markcd())) {
                    VALUES("CLY_MARKCD", "#{bondInvtDt.cly_markcd}");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{bondInvtDt.writing_mode}");
                }
            }
        }.toString();
    }

    public String queryEntInfoByDxpId(String DxpId) {
        return new SQL() {
            {
                SELECT("*");
                FROM("T_ENTERPRISE");
                WHERE("DXP_ID = #{DxpId}");
            }
        }.toString();
    }

    public String findRepeatOrder(@Param("orderId") String orderId, @Param("orderNo") String orderNo) {
        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_IMP_ORDER_HEAD");
                WHERE("GUID = #{orderId} OR ORDER_NO = #{orderNo}");
            }
        }.toString();
    }

    public String findRepeatBondInvt(@Param("etpsInnerInvtNo") String etpsInnerInvtNo) {
        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_BOND_INVT_BSC");
                WHERE("ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}");
            }
        }.toString();
    }

}
