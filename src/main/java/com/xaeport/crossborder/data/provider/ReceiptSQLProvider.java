package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class ReceiptSQLProvider extends BaseSQLProvider {

    //插入核注清单处理成功回执数据
    public String createInvtCommon(@Param("recBondInvtCommon") RecBondInvtCommon recBondInvtCommon) {
        return new SQL() {
            {
                INSERT_INTO("T_REC_BOND_INVT_COMMON");
                if (!StringUtils.isEmpty(recBondInvtCommon.getGuid())) {
                    VALUES("GUID", "#{recBondInvtCommon.GUID}");
                }
                if (!StringUtils.isEmpty(recBondInvtCommon.getSeq_no())) {
                    VALUES("SEQ_NO", "#{recBondInvtCommon.SEQ_NO}");
                }
                if (!StringUtils.isEmpty(recBondInvtCommon.getEtps_preent_no())) {
                    VALUES("ETPS_PREENT_NO", "#{recBondInvtCommon.ETPS_PREENT_NO}");
                }
                if (!StringUtils.isEmpty(recBondInvtCommon.getCheck_info())) {
                    VALUES("CHECK_INFO", "#{recBondInvtCommon.CHECK_INFO}");
                }
                if (!StringUtils.isEmpty(recBondInvtCommon.getDeal_flag())) {
                    VALUES("DEAL_FLAG", "#{recBondInvtCommon.DEAL_FLAG}");
                }
                if (!StringUtils.isEmpty(recBondInvtCommon.getCrt_tm())) {
                    VALUES("CRT_TM", "#{recBondInvtCommon.CRT_TM}");
                }
                if (!StringUtils.isEmpty(recBondInvtCommon.getUpd_tm())) {
                    VALUES("UPD_TM", "#{recBondInvtCommon.UPD_TM}");
                }
            }
        }.toString();
    }

    //根据核注清单处理成功回执更新状态
    public String updateBondInvtStatusByCommon(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc) {
        return new SQL() {
            {
                UPDATE("T_BOND_INVT_BSC");
                WHERE("STATUS in ('BDDS21','BDDS22')");
                if (!StringUtils.isEmpty(bondInvtBsc.getEtps_inner_invt_no())) {
                    WHERE("ETPS_INNER_INVT_NO = #{bondInvtBsc.etps_inner_invt_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getInvt_preent_no())) {
                    VALUES("INVT_PREENT_NO", "#{bondInvtBsc.invt_preent_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{bondInvtBsc.return_status}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{bondInvtBsc.return_info}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getStatus())) {
                    VALUES("STATUS", "#{bondInvtBsc.status}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getUpd_time())) {
                    VALUES("upd_time", "#{bondInvtBsc.upd_time}");
                }
            }
        }.toString();
    }

    //根据核注清单处理成功回执更新状态
    public String updateNemsInvtByCommon(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc) {
        return new SQL() {
            {
                UPDATE("T_NEMS_INVT_CBEC_BILL_TYPE");
                if (!StringUtils.isEmpty(bondInvtBsc.getEtps_inner_invt_no())) {
                    WHERE("HEAD_ETPS_INNER_INVT_NO = #{bondInvtBsc.etps_inner_invt_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getInvt_preent_no())) {
                    VALUES("SEQ_NO", "#{bondInvtBsc.invt_preent_no}");
                }
                if (!StringUtils.isEmpty(bondInvtBsc.getUpd_time())) {
                    VALUES("UPD_TIME", "#{bondInvtBsc.upd_time}");
                }
            }
        }.toString();
    }


    //核注清单(报文回执/审核回执)
    public String createInvtHdeAppr(@Param("recBondInvtHdeAppr") RecBondInvtHdeAppr recBondInvtHdeAppr) {
        return new SQL() {
            {
                INSERT_INTO("T_REC_BOND_INVT_HDEAPPR");
                if (!StringUtils.isEmpty(recBondInvtHdeAppr.getGuid())) {
                    VALUES("GUID", "#{recBondInvtHdeAppr.guid}");
                }
                if (!StringUtils.isEmpty(recBondInvtHdeAppr.getEtps_preent_no())) {
                    VALUES("ETPS_PREENT_NO", "#{recBondInvtHdeAppr.etps_preent_no}");
                }
                if (!StringUtils.isEmpty(recBondInvtHdeAppr.getBusiness_id())) {
                    VALUES("BUSINESS_ID", "#{recBondInvtHdeAppr.business_id}");
                }
                if (!StringUtils.isEmpty(recBondInvtHdeAppr.getTms_cnt())) {
                    VALUES("TMS_CNT", "#{recBondInvtHdeAppr.tms_cnt}");
                }
                if (!StringUtils.isEmpty(recBondInvtHdeAppr.getTypecd())) {
                    VALUES("TYPECD", "#{recBondInvtHdeAppr.typecd}");
                }
                if (!StringUtils.isEmpty(recBondInvtHdeAppr.getManage_result())) {
                    VALUES("MANAGE_RESULT", "#{recBondInvtHdeAppr.manage_result}");
                }
                if (!StringUtils.isEmpty(recBondInvtHdeAppr.getManage_date())) {
                    VALUES("MANAGE_DATE", "#{recBondInvtHdeAppr.manage_date}");
                }
                if (!StringUtils.isEmpty(recBondInvtHdeAppr.getRmk())) {
                    VALUES("RMK", "#{recBondInvtHdeAppr.rmk}");
                }
                if (!StringUtils.isEmpty(recBondInvtHdeAppr.getCrt_tm())) {
                    VALUES("CRT_TM", "#{recBondInvtHdeAppr.crt_tm}");
                }
                if (!StringUtils.isEmpty(recBondInvtHdeAppr.getUpd_tm())) {
                    VALUES("UPD_TM", "#{recBondInvtHdeAppr.upd_tm}");
                }
            }
        }.toString();
    }
    //核注清单生成报关单回执
    public String createInvtInvAppr(@Param("recBondInvtInvAppr") RecBondInvtInvAppr recBondInvtInvAppr) {
        return new SQL() {
            {
                INSERT_INTO("T_REC_BOND_INVT_INVAPPR");
                if (!StringUtils.isEmpty(recBondInvtInvAppr.getGuid())) {
                    VALUES("GUID", "#{recBondInvtInvAppr.guid}");
                }
                if (!StringUtils.isEmpty(recBondInvtInvAppr.getInv_preent_no())) {
                    VALUES("INV_PREENT_NO", "#{recBondInvtInvAppr.inv_preent_no}");
                }
                if (!StringUtils.isEmpty(recBondInvtInvAppr.getBusiness_id())) {
                    VALUES("BUSINESS_ID", "#{recBondInvtInvAppr.business_id}");
                }
                if (!StringUtils.isEmpty(recBondInvtInvAppr.getEntry_seq_no())) {
                    VALUES("ENTRY_SEQ_NO", "#{recBondInvtInvAppr.entry_seq_no}");
                }
                if (!StringUtils.isEmpty(recBondInvtInvAppr.getManage_result())) {
                    VALUES("MANAGE_RESULT", "#{recBondInvtInvAppr.manage_result}");
                }
                if (!StringUtils.isEmpty(recBondInvtInvAppr.getCreate_date())) {
                    VALUES("CREATE_DATE", "#{recBondInvtInvAppr.create_date}");
                }
                if (!StringUtils.isEmpty(recBondInvtInvAppr.getReason())) {
                    VALUES("REASON", "#{recBondInvtInvAppr.reason}");
                }
                if (!StringUtils.isEmpty(recBondInvtInvAppr.getCrt_tm())) {
                    VALUES("CRT_TM", "#{recBondInvtInvAppr.crt_tm}");
                }
                if (!StringUtils.isEmpty(recBondInvtInvAppr.getUpd_tm())) {
                    VALUES("UPD_TM", "#{recBondInvtInvAppr.upd_tm}");
                }
            }
        }.toString();
    }


    //插入电子税单表头
    public String InsertTaxHeadRd(
            @Param("taxHeadRd") TaxHeadRd taxHeadRd
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_TAX_HEADRD");
                if (!StringUtils.isEmpty(taxHeadRd.getGuid())) {
                    VALUES("GUID", "#{taxHeadRd.guid}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getPrivate_guid())) {
                    VALUES("PRIVATE_GUID", "#{taxHeadRd.private_guid}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{taxHeadRd.return_time}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getInvt_no())) {
                    VALUES("INVT_NO", "#{taxHeadRd.invt_no}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getTax_no())) {
                    VALUES("TAX_NO", "#{taxHeadRd.tax_no}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getCustoms_tax())) {
                    VALUES("CUSTOMS_TAX", "#{taxHeadRd.customs_tax}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getValue_added_tax())) {
                    VALUES("VALUE_ADDED_TAX", "#{taxHeadRd.value_added_tax}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getConsumption_tax())) {
                    VALUES("CONSUMPTION_TAX", "#{taxHeadRd.consumption_tax}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getStatus())) {
                    VALUES("STATUS", "#{taxHeadRd.status}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getEntduty_no())) {
                    VALUES("ENTDUTY_NO", "#{taxHeadRd.entduty_no}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getNote())) {
                    VALUES("NOTE", "#{taxHeadRd.note}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getAssure_code())) {
                    VALUES("ASSURE_CODE", "#{taxHeadRd.assure_code}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getEbc_code())) {
                    VALUES("EBC_CODE", "#{taxHeadRd.ebc_code}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getLogistics_code())) {
                    VALUES("LOGISTICS_CODE", "#{taxHeadRd.logistics_code}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getAgent_code())) {
                    VALUES("AGENT_CODE", "#{taxHeadRd.agent_code}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getCustoms_code())) {
                    VALUES("CUSTOMS_CODE", "#{taxHeadRd.customs_code}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getOrder_no())) {
                    VALUES("ORDER_NO", "#{taxHeadRd.order_no}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getLogistics_no())) {
                    VALUES("LOGISTICS_NO", "#{taxHeadRd.logistics_no}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getCrt_tm())) {
                    VALUES("CRT_TM", "#{taxHeadRd.crt_tm}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getUpd_tm())) {
                    VALUES("UPD_TM", "#{taxHeadRd.upd_tm}");
                }
            }
        }.toString();
    }

    //插入电子税单表体
    public String InsertTaxListRd(
            @Param("taxListRd") TaxListRd taxListRd
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_TAX_LISTRD");
                if (!StringUtils.isEmpty(taxListRd.getHead_guid())) {
                    VALUES("HEAD_GUID", "#{taxListRd.head_guid}");
                }
                if (!StringUtils.isEmpty(taxListRd.getG_num())) {
                    VALUES("G_NUM", "#{taxListRd.g_num}");
                }
                if (!StringUtils.isEmpty(taxListRd.getG_code())) {
                    VALUES("G_CODE", "#{taxListRd.g_code}");
                }
                if (!StringUtils.isEmpty(taxListRd.getTax_price())) {
                    VALUES("TAX_PRICE", "#{taxListRd.tax_price}");
                }
                if (!StringUtils.isEmpty(taxListRd.getCustoms_tax())) {
                    VALUES("CUSTOMS_TAX", "#{taxListRd.customs_tax}");
                }
                if (!StringUtils.isEmpty(taxListRd.getValue_added_tax())) {
                    VALUES("VALUE_ADDED_TAX", "#{taxListRd.value_added_tax}");
                }
                if (!StringUtils.isEmpty(taxListRd.getConsumption_tax())) {
                    VALUES("CONSUMPTION_TAX", "#{taxListRd.consumption_tax}");
                }
            }
        }.toString();
    }

    //更新清单表头税额
    public String updateInventoryHeadTax(
            @Param("taxHeadRd") TaxHeadRd taxHeadRd
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                if (!StringUtils.isEmpty(taxHeadRd.getInvt_no())) {
                    WHERE("t.INVT_NO = #{taxHeadRd.invt_no}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getCustoms_tax())) {
                    SET("t.CUSTOMS_TAX = #{taxHeadRd.customs_tax}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getValue_added_tax())) {
                    SET("t.VALUE_ADDED_TAX = #{taxHeadRd.value_added_tax}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getConsumption_tax())) {
                    SET("t.CONSUMPTION_TAX = #{taxHeadRd.consumption_tax}");
                }
                if (!StringUtils.isEmpty(taxHeadRd.getReturn_time())) {
                    SET("t.TAX_RETURN_TIME = #{taxHeadRd.return_time}");
                    SET("t.UPD_ID = '税额变更'");
                    SET("t.UPD_TM = sysdate");
                }
            }
        }.toString();
    }

    //更新清单表体税额
    public String updateInventoryListTax(
            @Param("taxHeadRd") TaxHeadRd taxHeadRd,
            @Param("taxListRd") TaxListRd taxListRd
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_BODY t");
                if (!StringUtils.isEmpty(taxHeadRd.getInvt_no())) {
                    WHERE("t.HEAD_GUID = " +
                            "(SELECT tt.GUID FROM T_IMP_INVENTORY_HEAD tt WHERE tt.INVT_NO = #{taxHeadRd.invt_no})");
                }
                if (!StringUtils.isEmpty(taxListRd.getG_num())) {
                    WHERE("t.G_NUM = #{taxListRd.g_num}");
                }
                if (!StringUtils.isEmpty(taxListRd.getCustoms_tax())) {
                    SET("t.CUSTOMS_TAX = #{taxListRd.customs_tax}");
                }
                if (!StringUtils.isEmpty(taxListRd.getValue_added_tax())) {
                    SET("t.VALUE_ADDED_TAX = #{taxListRd.value_added_tax}");
                }
                if (!StringUtils.isEmpty(taxListRd.getConsumption_tax())) {
                    SET("t.CONSUMPTION_TAX = #{taxListRd.consumption_tax}");
                }
            }
        }.toString();
    }

    //查询最晚一票三位数字的清单回执状态
    public String queryMaxTimeReturnStatus(String copNo) {
        return new SQL() {
            {
                SELECT("return_status");
                FROM("( select return_status " +
                        "from t_imp_rec_inventory t " +
                        "where length(t.return_status) = 3 " +
                        "and t.cop_no = #{copNo} order by t.return_time desc )");
                WHERE("rownum = 1");
            }
        }.toString();
    }

    //插入预定历史表数据
    public String createCheckGoodsInfoHis(
            @Param("checkGoodsInfo") CheckGoodsInfo checkGoodsInfo
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_CHECK_GOODS_INFO_HIS");
                if (!StringUtils.isEmpty(checkGoodsInfo.getGuid())) {
                    VALUES("GUID", "#{checkGoodsInfo.guid}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getEntry_id())) {
                    VALUES("ENTRY_ID", "#{checkGoodsInfo.entry_id}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getCustoms_code())) {
                    VALUES("CUSTOMS_CODE", "#{checkGoodsInfo.customs_code}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getI_e_flag())) {
                    VALUES("I_E_FLAG", "#{checkGoodsInfo.i_e_flag}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getTotal_logistics_no())) {
                    VALUES("TOTAL_LOGISTICS_NO", "#{checkGoodsInfo.total_logistics_no}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getLogistics_no())) {
                    VALUES("LOGISTICS_NO", "#{checkGoodsInfo.logistics_no}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getOrder_no())) {
                    VALUES("ORDER_NO", "#{checkGoodsInfo.order_no}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getLogistics_code())) {
                    VALUES("LOGISTICS_CODE", "#{checkGoodsInfo.logistics_code}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getLogistics_name())) {
                    VALUES("LOGISTICS_NAME", "#{checkGoodsInfo.logistics_name}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getPack_num())) {
                    VALUES("PACK_NUM", "#{checkGoodsInfo.pack_num}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getGross_wt())) {
                    VALUES("GROSS_WT", "#{checkGoodsInfo.gross_wt}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getNet_wt())) {
                    VALUES("NET_WT", "#{checkGoodsInfo.net_wt}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getGoods_value())) {
                    VALUES("GOODS_VALUE", "#{checkGoodsInfo.goods_value}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getStatus())) {
                    VALUES("STATUS", "#{checkGoodsInfo.status}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getControlled_status())) {
                    VALUES("CONTROLLED_STATUS", "#{checkGoodsInfo.controlled_status}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getMessage_time())) {
                    VALUES("MESSAGE_TIME", "#{checkGoodsInfo.message_time}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getCrt_id())) {
                    VALUES("CRT_ID", "#{checkGoodsInfo.crt_id}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getCrt_tm())) {
                    VALUES("CRT_TM", "#{checkGoodsInfo.crt_tm}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getUpd_id())) {
                    VALUES("UPD_ID", "#{checkGoodsInfo.upd_id}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getUpd_tm())) {
                    VALUES("UPD_TM", "#{checkGoodsInfo.upd_tm}");
                }
            }
        }.toString();
    }

    //插入预定数据
    public String createCheckGoodsInfo(
            @Param("checkGoodsInfo") CheckGoodsInfo checkGoodsInfo
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_CHECK_GOODS_INFO");
                if (!StringUtils.isEmpty(checkGoodsInfo.getGuid())) {
                    VALUES("GUID", "#{checkGoodsInfo.guid}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getEntry_id())) {
                    VALUES("ENTRY_ID", "#{checkGoodsInfo.entry_id}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getCustoms_code())) {
                    VALUES("CUSTOMS_CODE", "#{checkGoodsInfo.customs_code}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getI_e_flag())) {
                    VALUES("I_E_FLAG", "#{checkGoodsInfo.i_e_flag}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getTotal_logistics_no())) {
                    VALUES("TOTAL_LOGISTICS_NO", "#{checkGoodsInfo.total_logistics_no}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getLogistics_no())) {
                    VALUES("LOGISTICS_NO", "#{checkGoodsInfo.logistics_no}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getOrder_no())) {
                    VALUES("ORDER_NO", "#{checkGoodsInfo.order_no}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getLogistics_code())) {
                    VALUES("LOGISTICS_CODE", "#{checkGoodsInfo.logistics_code}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getLogistics_name())) {
                    VALUES("LOGISTICS_NAME", "#{checkGoodsInfo.logistics_name}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getPack_num())) {
                    VALUES("PACK_NUM", "#{checkGoodsInfo.pack_num}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getGross_wt())) {
                    VALUES("GROSS_WT", "#{checkGoodsInfo.gross_wt}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getNet_wt())) {
                    VALUES("NET_WT", "#{checkGoodsInfo.net_wt}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getGoods_value())) {
                    VALUES("GOODS_VALUE", "#{checkGoodsInfo.goods_value}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getStatus())) {
                    VALUES("STATUS", "#{checkGoodsInfo.status}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getControlled_status())) {
                    VALUES("CONTROLLED_STATUS", "#{checkGoodsInfo.controlled_status}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getMessage_time())) {
                    VALUES("MESSAGE_TIME", "#{checkGoodsInfo.message_time}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getCrt_id())) {
                    VALUES("CRT_ID", "#{checkGoodsInfo.crt_id}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getCrt_tm())) {
                    VALUES("CRT_TM", "#{checkGoodsInfo.crt_tm}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getUpd_id())) {
                    VALUES("UPD_ID", "#{checkGoodsInfo.upd_id}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getUpd_tm())) {
                    VALUES("UPD_TM", "#{checkGoodsInfo.upd_tm}");
                }
                VALUES("IS_MANIFEST", "'N'");
            }
        }.toString();
    }

    public String findByOrderNo(String orderNo) {
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_CHECK_GOODS_INFO t");
                WHERE("t.ORDER_NO = #{orderNo}");
            }
        }.toString();
    }

    //更新预定数据
    public String updateCheckGoodsInfo(
            @Param("checkGoodsInfo") CheckGoodsInfo checkGoodsInfo
    ) {
        return new SQL() {
            {
                UPDATE("T_CHECK_GOODS_INFO t");
                if (!StringUtils.isEmpty(checkGoodsInfo.getOrder_no())) {
                    WHERE("t.ORDER_NO = #{checkGoodsInfo.order_no}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getEntry_id())) {
                    SET("t.ENTRY_ID = #{checkGoodsInfo.entry_id}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getCustoms_code())) {
                    SET("t.CUSTOMS_CODE = #{checkGoodsInfo.customs_code}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getI_e_flag())) {
                    SET("t.I_E_FLAG = #{checkGoodsInfo.i_e_flag}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getTotal_logistics_no())) {
                    SET("t.TOTAL_LOGISTICS_NO = #{checkGoodsInfo.total_logistics_no}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getLogistics_no())) {
                    SET("t.LOGISTICS_NO = #{checkGoodsInfo.logistics_no}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getLogistics_code())) {
                    SET("t.LOGISTICS_CODE = #{checkGoodsInfo.logistics_code}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getLogistics_name())) {
                    SET("t.LOGISTICS_NAME = #{checkGoodsInfo.logistics_name}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getPack_num())) {
                    SET("t.PACK_NUM = #{checkGoodsInfo.pack_num}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getGross_wt())) {
                    SET("t.GROSS_WT = #{checkGoodsInfo.gross_wt}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getNet_wt())) {
                    SET("t.NET_WT = #{checkGoodsInfo.net_wt}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getGoods_value())) {
                    SET("t.GOODS_VALUE = #{checkGoodsInfo.goods_value}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getStatus())) {
                    SET("t.STATUS = #{checkGoodsInfo.status}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getControlled_status())) {
                    SET("t.CONTROLLED_STATUS = #{checkGoodsInfo.controlled_status}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getMessage_time())) {
                    SET("t.MESSAGE_TIME = #{checkGoodsInfo.message_time}");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getUpd_id())) {
                    SET("t.UPD_ID = '系统变更'");
                }
                if (!StringUtils.isEmpty(checkGoodsInfo.getUpd_tm())) {
                    SET("t.UPD_TM = sysdate");
                }

            }
        }.toString();
    }

    public String findByCopNo(String copNo) {
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.COP_NO = #{copNo}");
            }
        }.toString();
    }

    public String findByInvtNo(String invtNo) {
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.INVT_NO = #{invtNo}");
            }
        }.toString();
    }

    public String findDeliveryByCopNo(String copNo) {
        return new SQL() {
            {
                SELECT("*");
                FROM("(SELECT * FROM T_IMP_DELIVERY_HEAD t where t.COP_NO = #{copNo})");
                WHERE("rownum = 1");
            }
        }.toString();
    }

    //插入支付单回执表数据
    public String createImpRecPayment(
            @Param("impRecPayment") ImpRecPayment impRecPayment
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_REC_PAYMENT");
                if (!StringUtils.isEmpty(impRecPayment.getId())) {
                    VALUES("ID", "#{impRecPayment.id}");
                }
                if (!StringUtils.isEmpty(impRecPayment.getGuid())) {
                    VALUES("GUID", "#{impRecPayment.guid}");
                }
                if (!StringUtils.isEmpty(impRecPayment.getPay_code())) {
                    VALUES("PAY_CODE", "#{impRecPayment.pay_code}");
                }
                if (!StringUtils.isEmpty(impRecPayment.getPay_transaction_id())) {
                    VALUES("PAY_TRANSACTION_ID", "#{impRecPayment.pay_transaction_id}");
                }
                if (!StringUtils.isEmpty(impRecPayment.getOrder_no())) {
                    VALUES("ORDER_NO", "#{impRecPayment.order_no}");
                }
                if (!StringUtils.isEmpty(impRecPayment.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{impRecPayment.return_status}");
                }
                if (!StringUtils.isEmpty(impRecPayment.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{impRecPayment.return_time}");
                }
                if (!StringUtils.isEmpty(impRecPayment.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{impRecPayment.return_info}");
                }
                if (!StringUtils.isEmpty(impRecPayment.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impRecPayment.crt_tm}");
                }
                if (!StringUtils.isEmpty(impRecPayment.getUpd_tm())) {
                    VALUES("UPD_TM", "#{impRecPayment.upd_tm}");
                }
            }
        }.toString();
    }

    //更新支付单表回执信息
    public String updateImpPayment(
            @Param("impPayment") ImpPayment impPayment
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_PAYMENT t");
                WHERE("t.DATA_STATUS in ('CBDS31','CBDS32')");
                if (!StringUtils.isEmpty(impPayment.getPay_transaction_id())) {
                    WHERE("t.PAY_TRANSACTION_ID = #{impPayment.pay_transaction_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getReturn_status())) {
                    SET("t.RETURN_STATUS = #{impPayment.return_status}");
                }
                if (!StringUtils.isEmpty(impPayment.getReturn_info())) {
                    SET("t.RETURN_INFO = #{impPayment.return_info}");
                }
                if (!StringUtils.isEmpty(impPayment.getReturn_time())) {
                    SET("t.RETURN_TIME = #{impPayment.return_time}");
                }
                if (!StringUtils.isEmpty(impPayment.getData_status())) {
                    SET("t.DATA_STATUS = #{impPayment.data_status}");
                }
                if (!StringUtils.isEmpty(impPayment.getUpd_tm())) {
                    SET("t.UPD_TM = #{impPayment.upd_tm}");
                }
            }
        }.toString();
    }


    //插入订单回执表数据
    public String createImpRecOrder(@Param("impRecOrder") ImpRecOrder impRecOrder) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_REC_ORDER");
                if (!StringUtils.isEmpty(impRecOrder.getId())) {
                    VALUES("ID", "#{impRecOrder.id}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getGuid())) {
                    VALUES("GUID", "#{impRecOrder.guid}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getEbpCode())) {
                    VALUES("EBP_CODE", "#{impRecOrder.ebpCode}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getEbcCode())) {
                    VALUES("EBC_CODE", "#{impRecOrder.ebcCode}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getOrderNo())) {
                    VALUES("ORDER_NO", "#{impRecOrder.orderNo}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getReturnStatus())) {
                    VALUES("RETURN_STATUS", "#{impRecOrder.returnStatus}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getReturnTime())) {
                    VALUES("RETURN_TIME", "#{impRecOrder.returnTime}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getReturnInfo())) {
                    VALUES("RETURN_INFO", "#{impRecOrder.returnInfo}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getCrtTm())) {
                    VALUES("CRT_TM", "#{impRecOrder.crtTm}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getUpdTm())) {
                    VALUES("UPD_TM", "#{impRecOrder.updTm}");
                }
            }
        }.toString();
    }

    //更新订单表回执信息
    public String updateImpOrder(@Param("impOrderHead") ImpOrderHead impOrderHead) {
        return new SQL() {
            {
                UPDATE("T_IMP_ORDER_HEAD t");
                WHERE("t.DATA_STATUS in ('CBDS21','CBDS22','OrderOver')");
                if (!StringUtils.isEmpty(impOrderHead.getOrder_No())) {
                    WHERE("t.ORDER_NO = #{impOrderHead.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_status())) {
                    SET("t.RETURN_STATUS = #{impOrderHead.return_status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_time())) {
                    SET("t.RETURN_TIME = #{impOrderHead.return_time}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_info())) {
                    SET("t.RETURN_INFO = #{impOrderHead.return_info}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getData_status())) {
                    SET("t.DATA_STATUS = #{impOrderHead.data_status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getUpd_tm())) {
                    SET("t.UPD_TM = #{impOrderHead.upd_tm}");
                }
            }
        }.toString();
    }

    //插入清单回执表数据
    public String createImpRecInventory(
            @Param("impRecInventory") ImpRecInventory impRecInventory
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_REC_INVENTORY");
                if (!StringUtils.isEmpty(impRecInventory.getId())) {
                    VALUES("ID", "#{impRecInventory.id}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getGuid())) {
                    VALUES("GUID", "#{impRecInventory.guid}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getCustoms_code())) {
                    VALUES("CUSTOMS_CODE", "#{impRecInventory.customs_code}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getEbp_code())) {
                    VALUES("EBP_CODE", "#{impRecInventory.ebp_code}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getEbc_code())) {
                    VALUES("EBC_CODE", "#{impRecInventory.ebc_code}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getAgent_code())) {
                    VALUES("AGENT_CODE", "#{impRecInventory.agent_code}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getCop_no())) {
                    VALUES("COP_NO", "#{impRecInventory.cop_no}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getPre_no())) {
                    VALUES("PRE_NO", "#{impRecInventory.pre_no}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getInvt_no())) {
                    VALUES("INVT_NO", "#{impRecInventory.invt_no}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{impRecInventory.return_status}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{impRecInventory.return_time}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{impRecInventory.return_info}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impRecInventory.crt_tm}");
                }
                if (!StringUtils.isEmpty(impRecInventory.getUpd_tm())) {
                    VALUES("UPD_TM", "#{impRecInventory.upd_tm}");
                }
            }
        }.toString();
    }

    //更新清单表回执信息
    public String updateImpInventory(
            @Param("impInventoryHead") ImpInventoryHead impInventoryHead
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE("t.DATA_STATUS in ('CBDS61','CBDS62','BDDS51','BDDS52','InvenOver')");
                if (!StringUtils.isEmpty(impInventoryHead.getCop_no())) {
                    WHERE("t.COP_NO = #{impInventoryHead.cop_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getPre_no())) {
                    SET("t.PRE_NO = #{impInventoryHead.pre_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getInvt_no())) {
                    SET("t.INVT_NO = #{impInventoryHead.invt_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getReturn_status())) {
                    SET("t.RETURN_STATUS = #{impInventoryHead.return_status}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getReturn_info())) {
                    SET("t.RETURN_INFO = #{impInventoryHead.return_info}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getReturn_time())) {
                    SET("t.RETURN_TIME = #{impInventoryHead.return_time}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getData_status())) {
                    SET("t.DATA_STATUS = #{impInventoryHead.data_status}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getUpd_tm())) {
                    SET("t.UPD_TM = #{impInventoryHead.upd_tm}");
                }
            }
        }.toString();
    }


    //插入运单回执表信息
    public String createImpRecLogistics(
            @Param("impRecLogistics") ImpRecLogistics impRecLogistics
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_REC_LOGISTICS");
                if (!StringUtils.isEmpty(impRecLogistics.getId())) {
                    VALUES("ID", "#{impRecLogistics.id}");
                }
                if (!StringUtils.isEmpty(impRecLogistics.getGuid())) {
                    VALUES("GUID", "#{impRecLogistics.guid}");
                }
                if (!StringUtils.isEmpty(impRecLogistics.getLogistics_Code())) {
                    VALUES("LOGISTICS_CODE", "#{impRecLogistics.logistics_Code}");
                }
                if (!StringUtils.isEmpty(impRecLogistics.getLogistics_No())) {
                    VALUES("LOGISTICS_NO", "#{impRecLogistics.logistics_No}");
                }
                if (!StringUtils.isEmpty(impRecLogistics.getReturn_Status())) {
                    VALUES("RETURN_STATUS", "#{impRecLogistics.return_Status}");
                }
                if (!StringUtils.isEmpty(impRecLogistics.getReturn_Info())) {
                    VALUES("RETURN_INFO", "#{impRecLogistics.return_Info}");
                }
                if (!StringUtils.isEmpty(impRecLogistics.getReturn_Time())) {
                    VALUES("RETURN_TIME", "#{impRecLogistics.return_Time}");
                }
                if (!StringUtils.isEmpty(impRecLogistics.getCrtTm())) {
                    VALUES("CRT_TM", "#{impRecLogistics.crtTm}");
                }
                if (!StringUtils.isEmpty(impRecLogistics.getUpdTm())) {
                    VALUES("UPD_TM", "#{impRecLogistics.updTm}");
                }

            }
        }.toString();
    }

    //更新运单表回执信息
    public String updateImpLogistics(
            @Param("impLogistics") ImpLogistics impLogistics
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_LOGISTICS t");
                if (!StringUtils.isEmpty(impLogistics.getLogistics_no())) {
                    WHERE("t.LOGISTICS_NO = #{impLogistics.logistics_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getReturn_status())) {
                    SET("t.RETURN_STATUS = #{impLogistics.return_status}");
                }
                if (!StringUtils.isEmpty(impLogistics.getReturn_time())) {
                    SET("t.RETURN_TIME = #{impLogistics.return_time}");
                }
                if (!StringUtils.isEmpty(impLogistics.getReturn_info())) {
                    SET("t.RETURN_INFO = #{impLogistics.return_info}");
                }
                if (!StringUtils.isEmpty(impLogistics.getUpd_tm())) {
                    SET("t.UPD_TM = #{impLogistics.upd_tm}");
                }
                if (!StringUtils.isEmpty(impLogistics.getData_status())) {
                    SET("t.DATA_STATUS = #{impLogistics.data_status}");
                }
            }
        }.toString();
    }

    //插入运单状态回执表数据
    public String createImpRecLogisticsStatus(
            @Param("impRecLogisticsStatus") ImpRecLogisticsStatus impRecLogisticsStatus
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_REC_LOGISTICS_STATUS");
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getId())) {
                    VALUES("ID", "#{impRecLogisticsStatus.id}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getGuid())) {
                    VALUES("GUID", "#{impRecLogisticsStatus.guid}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getLogistics_Code())) {
                    VALUES("LOGISTICS_CODE", "#{impRecLogisticsStatus.logistics_Code}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getLogistics_No())) {
                    VALUES("LOGISTICS_NO", "#{impRecLogisticsStatus.logistics_No}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getLogistics_Status())) {
                    VALUES("LOGISTICS_STATUS", "#{impRecLogisticsStatus.logistics_Status}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getReturn_Status())) {
                    VALUES("RETURN_STATUS", "#{impRecLogisticsStatus.return_Status}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getReturn_Info())) {
                    VALUES("RETURN_INFO", "#{impRecLogisticsStatus.return_Info}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getReturn_Time())) {
                    VALUES("RETURN_TIME", "#{impRecLogisticsStatus.return_Time}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getCrtTm())) {
                    VALUES("CRT_TM", "#{impRecLogisticsStatus.crtTm}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getUpdTm())) {
                    VALUES("UPD_TM", "#{impRecLogisticsStatus.updTm}");
                }

            }
        }.toString();
    }

    //更新运单状态表回执信息
    public String updateImpLogisticsStatus(
            @Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_LOGISTICS_STATUS t");
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_no())) {
                    WHERE("t.LOGISTICS_NO = #{impLogisticsStatus.logistics_no}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_status())) {
                    SET("t.LOGISTICS_STATUS = #{impLogisticsStatus.logistics_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_code())) {
                    SET("t.LOGISTICS_CODE = #{impLogisticsStatus.logistics_code}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getReturn_status())) {
                    SET("t.RETURN_STATUS = #{impLogisticsStatus.return_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getReturn_info())) {
                    SET("t.RETURN_INFO = #{impLogisticsStatus.return_info}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getReturn_time())) {
                    SET("t.RETURN_TIME = #{impLogisticsStatus.return_time}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getData_status())) {
                    SET("t.DATA_STATUS = #{impLogisticsStatus.data_status}");
                }
                // if (!StringUtils.isEmpty(impLogisticsStatus.getUpd_tm())) {
                //     SET("t.UPD_TM = #{impLogisticsStatus.upd_tm}");
                // }
            }
        }.toString();
    }

    /*
    * 运单表置为运单申报成功（CBDS52状态）
    * */
    public String updateImpLogisticsDataStatus(
            @Param("impRecLogisticsStatus") ImpRecLogisticsStatus impRecLogisticsStatus,
            @Param("ydztsbcg") String ydztsbcg
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_LOGISTICS t");
                WHERE("t.LOGISTICS_NO = #{impRecLogisticsStatus.logistics_No}");
                SET("t.DATA_STATUS = #{ydztsbcg}");
                SET("t.UPD_TM = sysdate");
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getLogistics_Status())) {
                    SET("t.LOGISTICS_STATUS = #{impRecLogisticsStatus.logistics_Status}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getReturn_Status())) {
                    SET("t.REC_RETURN_STATUS = #{impRecLogisticsStatus.return_Status}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getReturn_Info())) {
                    SET("t.REC_RETURN_INFO = #{impRecLogisticsStatus.return_Info}");
                }
                if (!StringUtils.isEmpty(impRecLogisticsStatus.getReturn_Time())) {
                    SET("t.REC_RETURN_TIME = #{impRecLogisticsStatus.return_Time}");
                }
            }
        }.toString();
    }

    //插入入库明细单回执表数据
    public String createImpRecDelivery(
            @Param("impRecDelivery") ImpRecDelivery impRecDelivery
    ) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_REC_DELIVERY");
                if (!StringUtils.isEmpty(impRecDelivery.getId())) {
                    VALUES("ID", "#{impRecDelivery.id}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getGuid())) {
                    VALUES("GUID", "#{impRecDelivery.guid}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getCustoms_code())) {
                    VALUES("CUSTOMS_CODE", "#{impRecDelivery.customs_code}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getOperator_code())) {
                    VALUES("OPERATOR_CODE", "#{impRecDelivery.operator_code}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getCop_no())) {
                    VALUES("COP_NO", "#{impRecDelivery.cop_no}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getPre_no())) {
                    VALUES("PRE_NO", "#{impRecDelivery.pre_no}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getRkd_no())) {
                    VALUES("RKD_NO", "#{impRecDelivery.rkd_no}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{impRecDelivery.return_status}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{impRecDelivery.return_time}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{impRecDelivery.return_info}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impRecDelivery.crt_tm}");
                }
                if (!StringUtils.isEmpty(impRecDelivery.getUpd_tm())) {
                    VALUES("UPD_TM", "#{impRecDelivery.upd_tm}");
                }
            }
        }.toString();
    }

    //更新清单表回执信息
    public String updateImpDelivery(
            @Param("impDeliveryHead") ImpDeliveryHead impDeliveryHead
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_DELIVERY_HEAD t");
                WHERE("t.DATA_STATUS in ('CBDS71','CBDS72')");
                if (!StringUtils.isEmpty(impDeliveryHead.getCop_no())) {
                    WHERE("t.COP_NO = #{impDeliveryHead.cop_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getPre_no())) {
                    SET("t.PRE_NO = #{impDeliveryHead.pre_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getRkd_no())) {
                    SET("t.RKD_NO = #{impDeliveryHead.rkd_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getReturn_status())) {
                    SET("t.RETURN_STATUS = #{impDeliveryHead.return_status}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getReturn_info())) {
                    SET("t.RETURN_INFO = #{impDeliveryHead.return_info}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getReturn_time())) {
                    SET("t.RETURN_TIME = #{impDeliveryHead.return_time}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getData_status())) {
                    SET("t.DATA_STATUS = #{impDeliveryHead.data_status}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getUpd_tm())) {
                    SET("t.UPD_TM = #{impDeliveryHead.upd_tm}");
                }
            }
        }.toString();
    }

}
