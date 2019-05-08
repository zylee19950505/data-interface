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
                if (!StringUtils.isEmpty(impOrderHead.getTrade_mode())) {
                    VALUES("trade_mode", "#{impOrderHead.trade_mode}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getOrder_No())) {
                    VALUES("order_No", "#{impOrderHead.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBatch_Numbers())) {
                    VALUES("batch_Numbers", "#{impOrderHead.batch_Numbers}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbp_Code())) {
                    VALUES("ebp_Code", "#{impOrderHead.ebp_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbp_Name())) {
                    VALUES("ebp_Name", "#{impOrderHead.ebp_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPort_code())) {
                    VALUES("port_code", "#{impOrderHead.port_code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Reg_No())) {
                    VALUES("buyer_Reg_No", "#{impOrderHead.buyer_Reg_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Name())) {
                    VALUES("buyer_Name", "#{impOrderHead.buyer_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Id_Number())) {
                    VALUES("buyer_Id_Number", "#{impOrderHead.buyer_Id_Number}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_TelePhone())) {
                    VALUES("buyer_TelePhone", "#{impOrderHead.buyer_TelePhone}");
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
                if (!StringUtils.isEmpty(impOrderHead.getInsured_fee())) {
                    VALUES("insured_fee", "#{impOrderHead.insured_fee}");
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
                if (!StringUtils.isEmpty(impOrderHead.getGross_weight())) {
                    VALUES("gross_weight", "#{impOrderHead.gross_weight}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getNet_weight())) {
                    VALUES("net_weight", "#{impOrderHead.net_weight}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getGuid())) {
                    VALUES("CRT_TM", "sysdate");
                }
                if (!StringUtils.isEmpty(impOrderHead.getGuid())) {
                    VALUES("UPD_TM", "sysdate");
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
                if (!StringUtils.isEmpty(impOrderBody.getItem_Name())) {
                    VALUES("item_Name", "#{impOrderBody.item_Name}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getItem_No())) {
                    VALUES("item_No", "#{impOrderBody.item_No}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getG_Model())) {
                    VALUES("g_Model", "#{impOrderBody.g_Model}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getQty())) {
                    VALUES("qty", "#{impOrderBody.qty}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getUnit())) {
                    VALUES("unit", "#{impOrderBody.unit}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getTotal_Price())) {
                    VALUES("total_Price", "#{impOrderBody.total_Price}");
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
                if (!StringUtils.isEmpty(bondInvtBsc.getEtps_inner_invt_no())) {
                    VALUES("ETPS_INNER_INVT_NO", "#{bondInvtBsc.etps_inner_invt_no}");
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
                if (!StringUtils.isEmpty(bondInvtBsc.getId())) {
                    VALUES("CRT_TIME", "sysdate");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getId())) {
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
                if (!StringUtils.isEmpty(bondInvtBsc.getCrt_user())) {
                    VALUES("CRT_USER", "#{bondInvtBsc.crt_user}");
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
                if (!StringUtils.isEmpty(bondInvtDt.getId())) {
                    VALUES("CRT_TIME", "sysdate");
                }
                if (!StringUtils.isEmpty(bondInvtDt.getId())) {
                    VALUES("UPD_TIME", "sysdate");
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
            }
        }.toString();
    }

}
