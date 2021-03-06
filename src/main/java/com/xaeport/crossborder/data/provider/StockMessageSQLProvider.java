package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.ManifestHead;
import com.xaeport.crossborder.data.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class StockMessageSQLProvider extends BaseSQLProvider {

    public String insertImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_ORDER_HEAD");
                if (!StringUtils.isEmpty(impOrderHead.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impOrderHead.writing_mode}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getGuid())) {
                    VALUES("GUID", "#{impOrderHead.guid}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Type())) {
                    VALUES("APP_TYPE", "#{impOrderHead.app_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Time())) {
                    VALUES("APP_TIME", "#{impOrderHead.app_Time}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Status())) {
                    VALUES("APP_STATUS", "#{impOrderHead.app_Status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getOrder_Type())) {
                    VALUES("ORDER_TYPE", "#{impOrderHead.order_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getOrder_No())) {
                    VALUES("ORDER_NO", "#{impOrderHead.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbp_Code())) {
                    VALUES("EBP_CODE", "#{impOrderHead.ebp_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbp_Name())) {
                    VALUES("EBP_NAME", "#{impOrderHead.ebp_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbc_Code())) {
                    VALUES("EBC_CODE", "#{impOrderHead.ebc_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbc_Name())) {
                    VALUES("EBC_NAME", "#{impOrderHead.ebc_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getGoods_Value())) {
                    VALUES("GOODS_VALUE", "#{impOrderHead.goods_Value}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getFreight())) {
                    VALUES("FREIGHT", "#{impOrderHead.freight}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getDiscount())) {
                    VALUES("DISCOUNT", "#{impOrderHead.discount}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getTax_Total())) {
                    VALUES("TAX_TOTAL", "#{impOrderHead.tax_Total}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getActural_Paid())) {
                    VALUES("ACTURAL_PAID", "#{impOrderHead.actural_Paid}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getCurrency())) {
                    VALUES("CURRENCY", "#{impOrderHead.currency}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Reg_No())) {
                    VALUES("BUYER_REG_NO", "#{impOrderHead.buyer_Reg_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Name())) {
                    VALUES("BUYER_NAME", "#{impOrderHead.buyer_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Id_Type())) {
                    VALUES("BUYER_ID_TYPE", "#{impOrderHead.buyer_Id_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Id_Number())) {
                    VALUES("BUYER_ID_NUMBER", "#{impOrderHead.buyer_Id_Number}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPay_Code())) {
                    VALUES("PAY_CODE", "#{impOrderHead.pay_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPayName())) {
                    VALUES("PAYNAME", "#{impOrderHead.payName}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPay_Transaction_Id())) {
                    VALUES("PAY_TRANSACTION_ID", "#{impOrderHead.pay_Transaction_Id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBatch_Numbers())) {
                    VALUES("BATCH_NUMBERS", "#{impOrderHead.batch_Numbers}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee())) {
                    VALUES("CONSIGNEE", "#{impOrderHead.consignee}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Telephone())) {
                    VALUES("CONSIGNEE_TELEPHONE", "#{impOrderHead.consignee_Telephone}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Address())) {
                    VALUES("CONSIGNEE_ADDRESS", "#{impOrderHead.consignee_Address}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Ditrict())) {
                    VALUES("CONSIGNEE_DITRICT", "#{impOrderHead.consignee_Ditrict}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getNote())) {
                    VALUES("NOTE", "#{impOrderHead.note}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getData_status())) {
                    VALUES("DATA_STATUS", "#{impOrderHead.data_status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getCrt_id())) {
                    VALUES("CRT_ID", "#{impOrderHead.crt_id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impOrderHead.crt_tm}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getUpd_id())) {
                    VALUES("UPD_ID", "#{impOrderHead.upd_id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getUpd_tm())) {
                    VALUES("UPD_TM", "#{impOrderHead.upd_tm}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{impOrderHead.return_status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{impOrderHead.return_info}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{impOrderHead.return_time}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEnt_id())) {
                    VALUES("ENT_ID", "#{impOrderHead.ent_id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEnt_name())) {
                    VALUES("ENT_NAME", "#{impOrderHead.ent_name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEnt_customs_code())) {
                    VALUES("ENT_CUSTOMS_CODE", "#{impOrderHead.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBill_No())) {
                    VALUES("BILL_NO", "#{impOrderHead.bill_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_TelePhone())) {
                    VALUES("BUYER_TELEPHONE", "#{impOrderHead.buyer_TelePhone}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBusiness_type())) {
                    VALUES("BUSINESS_TYPE", "#{impOrderHead.business_type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getInsured_fee())) {
                    VALUES("INSURED_FEE", "0");
                }
                if (!StringUtils.isEmpty(impOrderHead.getGross_weight())) {
                    VALUES("GROSS_WEIGHT", "#{impOrderHead.gross_weight}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getNet_weight())) {
                    VALUES("NET_WEIGHT", "#{impOrderHead.net_weight}");
                }
            }
        }.toString();
    }

    public String insertImpOrderBody(@Param("impOrderBody") ImpOrderBody impOrderBody) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_ORDER_BODY");
                if (!StringUtils.isEmpty(impOrderBody.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impOrderBody.writing_mode}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getG_num())) {
                    VALUES("G_NUM", "#{impOrderBody.g_num}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getHead_guid())) {
                    VALUES("HEAD_GUID", "#{impOrderBody.head_guid}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getOrder_No())) {
                    VALUES("ORDER_NO", "#{impOrderBody.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getItem_No())) {
                    VALUES("ITEM_NO", "#{impOrderBody.item_No}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getItem_Name())) {
                    VALUES("ITEM_NAME", "#{impOrderBody.item_Name}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getItem_Describe())) {
                    VALUES("ITEM_DESCRIBE", "#{impOrderBody.item_Describe}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getBar_Code())) {
                    VALUES("BAR_CODE", "#{impOrderBody.bar_Code}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getUnit())) {
                    VALUES("UNIT", "#{impOrderBody.unit}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getQty())) {
                    VALUES("QTY", "#{impOrderBody.qty}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getPrice())) {
                    VALUES("PRICE", "#{impOrderBody.price}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getTotal_Price())) {
                    VALUES("TOTAL_PRICE", "#{impOrderBody.total_Price}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getCurrency())) {
                    VALUES("CURRENCY", "#{impOrderBody.currency}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getCountry())) {
                    VALUES("COUNTRY", "#{impOrderBody.country}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getNote())) {
                    VALUES("NOTE", "#{impOrderBody.note}");
                }
                if (!StringUtils.isEmpty(impOrderBody.getG_Model())) {
                    VALUES("G_MODEL", "#{impOrderBody.g_Model}");
                }
            }
        }.toString();
    }

    public String insertOrderNo(@Param("orderNo") OrderNo orderNo) {
        return new SQL() {
            {
                INSERT_INTO("T_ORDER_NO");
                VALUES("ID", "#{orderNo.id}");
                VALUES("ORDER_NO", "#{orderNo.order_no}");
                VALUES("CRT_TM", "#{orderNo.crt_tm}");
                VALUES("USED", "#{orderNo.used}");
            }
        }.toString();
    }

    public String insertImpPayment(@Param("impPayment") ImpPayment impPayment) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_PAYMENT");
                if (!StringUtils.isEmpty(impPayment.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impPayment.writing_mode}");
                }
                if (!StringUtils.isEmpty(impPayment.getGuid())) {
                    VALUES("GUID", "#{impPayment.guid}");
                }
                if (!StringUtils.isEmpty(impPayment.getApp_type())) {
                    VALUES("APP_TYPE", "#{impPayment.app_type}");
                }
                if (!StringUtils.isEmpty(impPayment.getApp_time())) {
                    VALUES("APP_TIME", "#{impPayment.app_time}");
                }
                if (!StringUtils.isEmpty(impPayment.getApp_status())) {
                    VALUES("APP_STATUS", "#{impPayment.app_status}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_code())) {
                    VALUES("PAY_CODE", "#{impPayment.pay_code}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_name())) {
                    VALUES("PAY_NAME", "#{impPayment.pay_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_transaction_id())) {
                    VALUES("PAY_TRANSACTION_ID", "#{impPayment.pay_transaction_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getOrder_no())) {
                    VALUES("ORDER_NO", "#{impPayment.order_no}");
                }
                if (!StringUtils.isEmpty(impPayment.getEbp_code())) {
                    VALUES("EBP_CODE", "#{impPayment.ebp_code}");
                }
                if (!StringUtils.isEmpty(impPayment.getEbp_name())) {
                    VALUES("EBP_NAME", "#{impPayment.ebp_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getPayer_id_type())) {
                    VALUES("PAYER_ID_TYPE", "#{impPayment.payer_id_type}");
                }
                if (!StringUtils.isEmpty(impPayment.getPayer_id_number())) {
                    VALUES("PAYER_ID_NUMBER", "#{impPayment.payer_id_number}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_name())) {
                    VALUES("PAYER_NAME", "#{impPayment.payer_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getTelephone())) {
                    VALUES("TELEPHONE", "#{impPayment.telephone}");
                }
                if (!StringUtils.isEmpty(impPayment.getAmount_paid())) {
                    VALUES("AMOUNT_PAID", "#{impPayment.amount_paid}");
                }
                if (!StringUtils.isEmpty(impPayment.getCurrency())) {
                    VALUES("CURRENCY", "#{impPayment.currency}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_time())) {
                    VALUES("PAY_TIME", "#{impPayment.pay_time}");
                }
                if (!StringUtils.isEmpty(impPayment.getNote())) {
                    VALUES("NOTE", "#{impPayment.note}");
                }
                if (!StringUtils.isEmpty(impPayment.getData_status())) {
                    VALUES("DATA_STATUS", "#{impPayment.data_status}");
                }
                if (!StringUtils.isEmpty(impPayment.getCrt_id())) {
                    VALUES("CRT_ID", "#{impPayment.crt_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impPayment.crt_tm}");
                }
                if (!StringUtils.isEmpty(impPayment.getUpd_id())) {
                    VALUES("UPD_ID", "#{impPayment.upd_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getUpd_tm())) {
                    VALUES("UPD_TM", "#{impPayment.upd_tm}");
                }
                if (!StringUtils.isEmpty(impPayment.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{impPayment.return_status}");
                }
                if (!StringUtils.isEmpty(impPayment.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{impPayment.return_info}");
                }
                if (!StringUtils.isEmpty(impPayment.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{impPayment.return_time}");
                }
                if (!StringUtils.isEmpty(impPayment.getEnt_id())) {
                    VALUES("ENT_ID", "#{impPayment.ent_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getEnt_name())) {
                    VALUES("ENT_NAME", "#{impPayment.ent_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getEnt_customs_code())) {
                    VALUES("ENT_CUSTOMS_CODE", "#{impPayment.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(impPayment.getBusiness_type())) {
                    VALUES("BUSINESS_TYPE", "#{impPayment.business_type}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_time_char())) {
                    VALUES("PAY_TIME_CHAR", "#{impPayment.pay_time_char}");
                }
            }
        }.toString();
    }

    public String insertImpLogistics(@Param("impLogistics") ImpLogistics impLogistics) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_LOGISTICS");
                if (!StringUtils.isEmpty(impLogistics.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impLogistics.writing_mode}");
                }
                if (!StringUtils.isEmpty(impLogistics.getGuid())) {
                    VALUES("GUID", "#{impLogistics.guid}");
                }
                if (!StringUtils.isEmpty(impLogistics.getApp_type())) {
                    VALUES("APP_TYPE", "#{impLogistics.app_type}");
                }
                if (!StringUtils.isEmpty(impLogistics.getApp_time())) {
                    VALUES("APP_TIME", "#{impLogistics.app_time}");
                }
                if (!StringUtils.isEmpty(impLogistics.getApp_status())) {
                    VALUES("APP_STATUS", "#{impLogistics.app_status}");
                }
                if (!StringUtils.isEmpty(impLogistics.getLogistics_code())) {
                    VALUES("LOGISTICS_CODE", "#{impLogistics.logistics_code}");
                }
                if (!StringUtils.isEmpty(impLogistics.getLogistics_name())) {
                    VALUES("LOGISTICS_NAME", "#{impLogistics.logistics_name}");
                }
                if (!StringUtils.isEmpty(impLogistics.getLogistics_no())) {
                    VALUES("LOGISTICS_NO", "#{impLogistics.logistics_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getBill_no())) {
                    VALUES("BILL_NO", "#{impLogistics.bill_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getFreight())) {
                    VALUES("FREIGHT", "#{impLogistics.freight}");
                }
                if (!StringUtils.isEmpty(impLogistics.getInsured_fee())) {
                    VALUES("INSURED_FEE", "#{impLogistics.insured_fee}");
                }
                if (!StringUtils.isEmpty(impLogistics.getCurrency())) {
                    VALUES("CURRENCY", "#{impLogistics.currency}");
                }
                if (!StringUtils.isEmpty(impLogistics.getWeight())) {
                    VALUES("WEIGHT", "#{impLogistics.weight}");
                }
                if (!StringUtils.isEmpty(impLogistics.getPack_no())) {
                    VALUES("PACK_NO", "#{impLogistics.pack_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getGoods_info())) {
                    VALUES("GOODS_INFO", "#{impLogistics.goods_info}");
                }
                if (!StringUtils.isEmpty(impLogistics.getConsingee())) {
                    VALUES("CONSINGEE", "#{impLogistics.consingee}");
                }
                if (!StringUtils.isEmpty(impLogistics.getConsignee_address())) {
                    VALUES("CONSIGNEE_ADDRESS", "#{impLogistics.consignee_address}");
                }
                if (!StringUtils.isEmpty(impLogistics.getConsignee_telephone())) {
                    VALUES("CONSIGNEE_TELEPHONE", "#{impLogistics.consignee_telephone}");
                }
                if (!StringUtils.isEmpty(impLogistics.getNote())) {
                    VALUES("NOTE", "#{impLogistics.note}");
                }
                if (!StringUtils.isEmpty(impLogistics.getData_status())) {
                    VALUES("DATA_STATUS", "#{impLogistics.data_status}");
                }
                if (!StringUtils.isEmpty(impLogistics.getCrt_id())) {
                    VALUES("CRT_ID", "#{impLogistics.crt_id}");
                }
                if (!StringUtils.isEmpty(impLogistics.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impLogistics.crt_tm}");
                }
                if (!StringUtils.isEmpty(impLogistics.getUpd_id())) {
                    VALUES("UPD_ID", "#{impLogistics.upd_id}");
                }
                if (!StringUtils.isEmpty(impLogistics.getUpd_tm())) {
                    VALUES("UPD_TM", "#{impLogistics.upd_tm}");
                }
                if (!StringUtils.isEmpty(impLogistics.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{impLogistics.return_status}");
                }
                if (!StringUtils.isEmpty(impLogistics.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{impLogistics.return_info}");
                }
                if (!StringUtils.isEmpty(impLogistics.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{impLogistics.return_time}");
                }
                if (!StringUtils.isEmpty(impLogistics.getEnt_id())) {
                    VALUES("ENT_ID", "#{impLogistics.ent_id}");
                }
                if (!StringUtils.isEmpty(impLogistics.getEnt_name())) {
                    VALUES("ENT_NAME", "#{impLogistics.ent_name}");
                }
                if (!StringUtils.isEmpty(impLogistics.getEnt_customs_code())) {
                    VALUES("ENT_CUSTOMS_CODE", "#{impLogistics.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(impLogistics.getVoyage_no())) {
                    VALUES("VOYAGE_NO", "#{impLogistics.voyage_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getOrder_no())) {
                    VALUES("ORDER_NO", "#{impLogistics.order_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getBusiness_type())) {
                    VALUES("BUSINESS_TYPE", "#{impLogistics.business_type}");
                }
            }
        }.toString();
    }

    public String insertImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_LOGISTICS_STATUS");
                if (!StringUtils.isEmpty(impLogisticsStatus.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impLogisticsStatus.writing_mode}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getGuid())) {
                    VALUES("GUID", "#{impLogisticsStatus.guid}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getApp_type())) {
                    VALUES("APP_TYPE", "#{impLogisticsStatus.app_type}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getApp_time())) {
                    VALUES("APP_TIME", "#{impLogisticsStatus.app_time}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getApp_status())) {
                    VALUES("APP_STATUS", "#{impLogisticsStatus.app_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_code())) {
                    VALUES("LOGISTICS_CODE", "#{impLogisticsStatus.logistics_code}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_name())) {
                    VALUES("LOGISTICS_NAME", "#{impLogisticsStatus.logistics_name}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_no())) {
                    VALUES("LOGISTICS_NO", "#{impLogisticsStatus.logistics_no}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_status())) {
                    VALUES("LOGISTICS_STATUS", "#{impLogisticsStatus.logistics_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_time())) {
                    VALUES("LOGISTICS_TIME", "#{impLogisticsStatus.logistics_time}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getNote())) {
                    VALUES("NOTE", "#{impLogisticsStatus.note}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getData_status())) {
                    VALUES("DATA_STATUS", "#{impLogisticsStatus.data_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getCrt_id())) {
                    VALUES("CRT_ID", "#{impLogisticsStatus.crt_id}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impLogisticsStatus.crt_tm}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getUpd_id())) {
                    VALUES("UPD_ID", "#{impLogisticsStatus.upd_id}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getUpd_tm())) {
                    VALUES("UPD_TM", "#{impLogisticsStatus.upd_tm}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{impLogisticsStatus.return_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{impLogisticsStatus.return_info}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{impLogisticsStatus.return_time}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getEnt_id())) {
                    VALUES("ENT_ID", "#{impLogisticsStatus.ent_id}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getEnt_name())) {
                    VALUES("ENT_NAME", "#{impLogisticsStatus.ent_name}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getEnt_customs_code())) {
                    VALUES("ENT_CUSTOMS_CODE", "#{impLogisticsStatus.ent_customs_code}");
                }
            }
        }.toString();
    }

    public String insertImpInventoryHead(@Param("impInventoryHead") ImpInventoryHead impInventoryHead) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_INVENTORY_HEAD");
                if (!StringUtils.isEmpty(impInventoryHead.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impInventoryHead.writing_mode}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getGuid())) {
                    VALUES("GUID", "#{impInventoryHead.guid}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getApp_type())) {
                    VALUES("APP_TYPE", "#{impInventoryHead.app_type}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getApp_time())) {
                    VALUES("APP_TIME", "#{impInventoryHead.app_time}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getApp_status())) {
                    VALUES("APP_STATUS", "#{impInventoryHead.app_status}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getOrder_no())) {
                    VALUES("ORDER_NO", "#{impInventoryHead.order_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbp_code())) {
                    VALUES("EBP_CODE", "#{impInventoryHead.ebp_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbp_name())) {
                    VALUES("EBP_NAME", "#{impInventoryHead.ebp_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbc_code())) {
                    VALUES("EBC_CODE", "#{impInventoryHead.ebc_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbc_name())) {
                    VALUES("EBC_NAME", "#{impInventoryHead.ebc_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLogistics_no())) {
                    VALUES("LOGISTICS_NO", "#{impInventoryHead.logistics_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLogistics_code())) {
                    VALUES("LOGISTICS_CODE", "#{impInventoryHead.logistics_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLogistics_name())) {
                    VALUES("LOGISTICS_NAME", "#{impInventoryHead.logistics_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCop_no())) {
                    VALUES("COP_NO", "#{impInventoryHead.cop_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getPre_no())) {
                    VALUES("PRE_NO", "#{impInventoryHead.pre_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getAssure_code())) {
                    VALUES("ASSURE_CODE", "#{impInventoryHead.assure_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEms_no())) {
                    VALUES("EMS_NO", "#{impInventoryHead.ems_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getInvt_no())) {
                    VALUES("INVT_NO", "#{impInventoryHead.invt_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getIe_flag())) {
                    VALUES("IE_FLAG", "#{impInventoryHead.ie_flag}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getDecl_time())) {
                    VALUES("DECL_TIME", "#{impInventoryHead.decl_time}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCustoms_code())) {
                    VALUES("CUSTOMS_CODE", "#{impInventoryHead.customs_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getPort_code())) {
                    VALUES("PORT_CODE", "#{impInventoryHead.port_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getIe_date())) {
                    VALUES("IE_DATE", "#{impInventoryHead.ie_date}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_id_type())) {
                    VALUES("BUYER_ID_TYPE", "#{impInventoryHead.buyer_id_type}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_id_number())) {
                    VALUES("BUYER_ID_NUMBER", "#{impInventoryHead.buyer_id_number}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_name())) {
                    VALUES("BUYER_NAME", "#{impInventoryHead.buyer_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_telephone())) {
                    VALUES("BUYER_TELEPHONE", "#{impInventoryHead.buyer_telephone}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getConsignee_address())) {
                    VALUES("CONSIGNEE_ADDRESS", "#{impInventoryHead.consignee_address}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getAgent_code())) {
                    VALUES("AGENT_CODE", "#{impInventoryHead.agent_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getAgent_name())) {
                    VALUES("AGENT_NAME", "#{impInventoryHead.agent_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getArea_code())) {
                    VALUES("AREA_CODE", "#{impInventoryHead.area_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getArea_name())) {
                    VALUES("AREA_NAME", "#{impInventoryHead.area_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getTrade_mode())) {
                    VALUES("TRADE_MODE", "#{impInventoryHead.trade_mode}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getTraf_mode())) {
                    VALUES("TRAF_MODE", "#{impInventoryHead.traf_mode}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getTraf_no())) {
                    VALUES("TRAF_NO", "#{impInventoryHead.traf_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getVoyage_no())) {
                    VALUES("VOYAGE_NO", "#{impInventoryHead.voyage_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBill_no())) {
                    VALUES("BILL_NO", "#{impInventoryHead.bill_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLoct_no())) {
                    VALUES("LOCT_NO", "#{impInventoryHead.loct_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLicense_no())) {
                    VALUES("LICENSE_NO", "#{impInventoryHead.license_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCountry())) {
                    VALUES("COUNTRY", "#{impInventoryHead.country}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getFreight())) {
                    VALUES("FREIGHT", "#{impInventoryHead.freight}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getInsured_fee())) {
                    VALUES("INSURED_FEE", "#{impInventoryHead.insured_fee}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCurrency())) {
                    VALUES("CURRENCY", "#{impInventoryHead.currency}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getWrap_type())) {
                    VALUES("WRAP_TYPE", "#{impInventoryHead.wrap_type}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getPack_no())) {
                    VALUES("PACK_NO", "#{impInventoryHead.pack_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getGross_weight())) {
                    VALUES("GROSS_WEIGHT", "#{impInventoryHead.gross_weight}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getNet_weight())) {
                    VALUES("NET_WEIGHT", "#{impInventoryHead.net_weight}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getNote())) {
                    VALUES("NOTE", "#{impInventoryHead.note}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getData_status())) {
                    VALUES("DATA_STATUS", "#{impInventoryHead.data_status}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCrt_id())) {
                    VALUES("CRT_ID", "#{impInventoryHead.crt_id}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impInventoryHead.crt_tm}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getUpd_id())) {
                    VALUES("UPD_ID", "#{impInventoryHead.upd_id}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCrt_tm())) {
                    VALUES("UPD_TM", "#{impInventoryHead.upd_tm}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{impInventoryHead.return_status}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{impInventoryHead.return_info}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{impInventoryHead.return_time}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEnt_id())) {
                    VALUES("ENT_ID", "#{impInventoryHead.ent_id}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEnt_name())) {
                    VALUES("ENT_NAME", "#{impInventoryHead.ent_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEnt_customs_code())) {
                    VALUES("ENT_CUSTOMS_CODE", "#{impInventoryHead.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBusiness_type())) {
                    VALUES("BUSINESS_TYPE", "#{impInventoryHead.business_type}");
                }
            }
        }.toString();
    }

    public String insertImpInventoryBody(@Param("impInventoryBody") ImpInventoryBody impInventoryBody) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_INVENTORY_BODY");
                if (!StringUtils.isEmpty(impInventoryBody.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impInventoryBody.writing_mode}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getG_num())) {
                    VALUES("G_NUM", "#{impInventoryBody.g_num}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getHead_guid())) {
                    VALUES("HEAD_GUID", "#{impInventoryBody.head_guid}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getOrder_no())) {
                    VALUES("ORDER_NO", "#{impInventoryBody.order_no}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getItem_record_no())) {
                    VALUES("ITEM_RECORD_NO", "#{impInventoryBody.item_record_no}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getItem_no())) {
                    VALUES("ITEM_NO", "#{impInventoryBody.item_no}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getItem_name())) {
                    VALUES("ITEM_NAME", "#{impInventoryBody.item_name}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getG_code())) {
                    VALUES("G_CODE", "#{impInventoryBody.g_code}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getG_name())) {
                    VALUES("G_NAME", "#{impInventoryBody.g_name}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getG_model())) {
                    VALUES("G_MODEL", "#{impInventoryBody.g_model}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getBar_code())) {
                    VALUES("BAR_CODE", "#{impInventoryBody.bar_code}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getCountry())) {
                    VALUES("COUNTRY", "#{impInventoryBody.country}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getCurrency())) {
                    VALUES("CURRENCY", "#{impInventoryBody.currency}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getQty())) {
                    VALUES("QTY", "#{impInventoryBody.qty}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getQty1())) {
                    VALUES("QTY1", "#{impInventoryBody.qty1}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getQty2())) {
                    VALUES("QTY2", "#{impInventoryBody.qty2}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getUnit())) {
                    VALUES("UNIT", "#{impInventoryBody.unit}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getUnit1())) {
                    VALUES("UNIT1", "#{impInventoryBody.unit1}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getUnit2())) {
                    VALUES("UNIT2", "#{impInventoryBody.unit2}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getPrice())) {
                    VALUES("PRICE", "#{impInventoryBody.price}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getTotal_price())) {
                    VALUES("TOTAL_PRICE", "#{impInventoryBody.total_price}");
                }
                if (!StringUtils.isEmpty(impInventoryBody.getNote())) {
                    VALUES("NOTE", "#{impInventoryBody.note}");
                }
            }
        }.toString();
    }

    public String insertImpDeliveryHead(@Param("impDeliveryHead") ImpDeliveryHead impDeliveryHead) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_DELIVERY_HEAD");
                if (!StringUtils.isEmpty(impDeliveryHead.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impDeliveryHead.writing_mode}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getGuid())) {
                    VALUES("GUID", "#{impDeliveryHead.guid}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getApp_type())) {
                    VALUES("APP_TYPE", "#{impDeliveryHead.app_type}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getApp_time())) {
                    VALUES("APP_TIME", "#{impDeliveryHead.app_time}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getApp_status())) {
                    VALUES("APP_STATUS", "#{impDeliveryHead.app_status}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getCustoms_code())) {
                    VALUES("CUSTOMS_CODE", "#{impDeliveryHead.customs_code}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getCop_no())) {
                    VALUES("COP_NO", "#{impDeliveryHead.cop_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getPre_no())) {
                    VALUES("PRE_NO", "#{impDeliveryHead.pre_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getRkd_no())) {
                    VALUES("RKD_NO", "#{impDeliveryHead.rkd_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getOperator_code())) {
                    VALUES("OPERATOR_CODE", "#{impDeliveryHead.operator_code}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getOperator_name())) {
                    VALUES("OPERATOR_NAME", "#{impDeliveryHead.operator_name}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getIe_flag())) {
                    VALUES("IE_FLAG", "#{impDeliveryHead.ie_flag}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getTraf_mode())) {
                    VALUES("TRAF_MODE", "#{impDeliveryHead.traf_mode}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getTraf_no())) {
                    VALUES("TRAF_NO", "#{impDeliveryHead.traf_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getVoyage_no())) {
                    VALUES("VOYAGE_NO", "#{impDeliveryHead.voyage_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getBill_no())) {
                    VALUES("BILL_NO", "#{impDeliveryHead.bill_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getLogistics_no())) {
                    VALUES("LOGISTICS_NO", "#{impDeliveryHead.logistics_no}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getLogistics_code())) {
                    VALUES("LOGISTICS_CODE", "#{impDeliveryHead.logistics_code}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getLogistics_name())) {
                    VALUES("LOGISTICS_NAME", "#{impDeliveryHead.logistics_name}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getUnload_location())) {
                    VALUES("UNLOAD_LOCATION", "#{impDeliveryHead.unload_location}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getNote())) {
                    VALUES("NOTE", "#{impDeliveryHead.note}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getData_status())) {
                    VALUES("DATA_STATUS", "#{impDeliveryHead.data_status}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getCrt_id())) {
                    VALUES("CRT_ID", "#{impDeliveryHead.crt_id}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getCrt_tm())) {
                    VALUES("CRT_TM", "#{impDeliveryHead.crt_tm}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getUpd_id())) {
                    VALUES("UPD_ID", "#{impDeliveryHead.upd_id}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getUpd_tm())) {
                    VALUES("UPD_TM", "#{impDeliveryHead.upd_tm}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getReturn_status())) {
                    VALUES("RETURN_STATUS", "#{impDeliveryHead.return_status}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getReturn_info())) {
                    VALUES("RETURN_INFO", "#{impDeliveryHead.return_info}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getReturn_time())) {
                    VALUES("RETURN_TIME", "#{impDeliveryHead.return_time}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getEnt_id())) {
                    VALUES("ENT_ID", "#{impDeliveryHead.ent_id}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getEnt_name())) {
                    VALUES("ENT_NAME", "#{impDeliveryHead.ent_name}");
                }
                if (!StringUtils.isEmpty(impDeliveryHead.getEnt_customs_code())) {
                    VALUES("ENT_CUSTOMS_CODE", "#{impDeliveryHead.ent_customs_code}");
                }
            }
        }.toString();
    }

    public String insertImpDeliveryBody(@Param("impDeliveryBody") ImpDeliveryBody impDeliveryBody) {
        return new SQL() {
            {
                INSERT_INTO("T_IMP_DELIVERY_BODY");
                if (!StringUtils.isEmpty(impDeliveryBody.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{impDeliveryBody.writing_mode}");
                }
                if (!StringUtils.isEmpty(impDeliveryBody.getHead_guid())) {
                    VALUES("HEAD_GUID", "#{impDeliveryBody.head_guid}");
                }
                if (!StringUtils.isEmpty(impDeliveryBody.getG_num())) {
                    VALUES("G_NUM", "#{impDeliveryBody.g_num}");
                }
                if (!StringUtils.isEmpty(impDeliveryBody.getG_code())) {
                    VALUES("G_CODE", "#{impDeliveryBody.g_code}");
                }
                if (!StringUtils.isEmpty(impDeliveryBody.getG_name())) {
                    VALUES("G_NAME", "#{impDeliveryBody.g_name}");
                }
                if (!StringUtils.isEmpty(impDeliveryBody.getQty())) {
                    VALUES("QTY", "#{impDeliveryBody.qty}");
                }
                if (!StringUtils.isEmpty(impDeliveryBody.getUnit())) {
                    VALUES("UNIT", "#{impDeliveryBody.unit}");
                }
                if (!StringUtils.isEmpty(impDeliveryBody.getNote())) {
                    VALUES("NOTE", "#{impDeliveryBody.note}");
                }
                if (!StringUtils.isEmpty(impDeliveryBody.getLogistics_no())) {
                    VALUES("LOGISTICS_NO", "#{impDeliveryBody.logistics_no}");
                }
            }
        }.toString();
    }

    public String queryManifestData(@Param("manifestHead") ManifestHead manifestHead) {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("T_MANIFEST_HEAD mh");
                WHERE("mh.AUTO_ID = #{manifestHead.auto_id}");
            }
        }.toString();
    }

    public String updateManifestData(@Param("manifestHead") ManifestHead manifestHead) {
        return new SQL() {
            {
                UPDATE("T_MANIFEST_HEAD mh");
                WHERE("mh.AUTO_ID = #{manifestHead.auto_id}");
                SET("mh.MANIFEST_NO = #{manifestHead.manifest_no}");
                SET("mh.BIZ_TYPE = #{manifestHead.biz_type}");
                SET("mh.BIZ_MODE = #{manifestHead.biz_mode}");
                SET("mh.I_E_FLAG = #{manifestHead.i_e_flag}");
                SET("mh.I_E_MARK = #{manifestHead.i_e_mark}");
                SET("mh.START_LAND = #{manifestHead.start_land}");
                SET("mh.GOAL_LAND = #{manifestHead.goal_land}");
                SET("mh.CAR_NO = #{manifestHead.car_no}");
                SET("mh.CAR_WT = #{manifestHead.car_wt}");
                SET("mh.IC_CODE = #{manifestHead.ic_code}");
                SET("mh.GOODS_WT = #{manifestHead.goods_wt}");
                SET("mh.FACT_WEIGHT = #{manifestHead.fact_weight}");
                SET("mh.PACK_NO = #{manifestHead.pack_no}");
                SET("mh.M_STATUS = #{manifestHead.m_status}");
                SET("mh.B_STATUS = #{manifestHead.b_status}");
                SET("mh.STATUS = #{manifestHead.status}");
                SET("mh.PORT_STATUS = #{manifestHead.port_status}");
                SET("mh.APP_PERSON = #{manifestHead.app_person}");
                SET("mh.APP_DATE = #{manifestHead.app_date}");
                SET("mh.INPUT_CODE = #{manifestHead.input_code}");
                SET("mh.INPUT_NAME = #{manifestHead.input_name}");
                SET("mh.TRADE_CODE = #{manifestHead.trade_code}");
                SET("mh.TRADE_NAME = #{manifestHead.trade_name}");
                SET("mh.REGION_CODE = #{manifestHead.region_code}");
                SET("mh.CUSTOMS_CODE = #{manifestHead.customs_code}");
                SET("mh.NOTE = #{manifestHead.note}");
                SET("mh.EXTEND_FIELD_3 = #{manifestHead.extend_field_3}");
                SET("mh.PLAT_FROM = #{manifestHead.plat_from}");
            }
        }.toString();
    }

    public String insertManifestData(@Param("manifestHead") ManifestHead manifestHead) {
        return new SQL() {
            {
                INSERT_INTO("T_MANIFEST_HEAD");
                if (!StringUtils.isEmpty(manifestHead.getAuto_id())) {
                    VALUES("AUTO_ID", "#{manifestHead.auto_id}");
                }
                if (!StringUtils.isEmpty(manifestHead.getManifest_no())) {
                    VALUES("MANIFEST_NO", "#{manifestHead.manifest_no}");
                }
                if (!StringUtils.isEmpty(manifestHead.getBiz_type())) {
                    VALUES("BIZ_TYPE", "#{manifestHead.biz_type}");
                }
                if (!StringUtils.isEmpty(manifestHead.getBiz_mode())) {
                    VALUES("BIZ_MODE", "#{manifestHead.biz_mode}");
                }
                if (!StringUtils.isEmpty(manifestHead.getI_e_flag())) {
                    VALUES("I_E_FLAG", "#{manifestHead.i_e_flag}");
                }
                if (!StringUtils.isEmpty(manifestHead.getI_e_mark())) {
                    VALUES("I_E_MARK", "#{manifestHead.i_e_mark}");
                }
                if (!StringUtils.isEmpty(manifestHead.getStart_land())) {
                    VALUES("START_LAND", "#{manifestHead.start_land}");
                }
                if (!StringUtils.isEmpty(manifestHead.getGoal_land())) {
                    VALUES("GOAL_LAND", "#{manifestHead.goal_land}");
                }
                if (!StringUtils.isEmpty(manifestHead.getCar_no())) {
                    VALUES("CAR_NO", "#{manifestHead.car_no}");
                }
                if (!StringUtils.isEmpty(manifestHead.getCar_wt())) {
                    VALUES("CAR_WT", "#{manifestHead.car_wt}");
                }
                if (!StringUtils.isEmpty(manifestHead.getIc_code())) {
                    VALUES("IC_CODE", "#{manifestHead.ic_code}");
                }
                if (!StringUtils.isEmpty(manifestHead.getGoods_wt())) {
                    VALUES("GOODS_WT", "#{manifestHead.goods_wt}");
                }
                if (!StringUtils.isEmpty(manifestHead.getFact_weight())) {
                    VALUES("FACT_WEIGHT", "#{manifestHead.fact_weight}");
                }
                if (!StringUtils.isEmpty(manifestHead.getPack_no())) {
                    VALUES("PACK_NO", "#{manifestHead.pack_no}");
                }
                if (!StringUtils.isEmpty(manifestHead.getM_status())) {
                    VALUES("M_STATUS", "#{manifestHead.m_status}");
                }
                if (!StringUtils.isEmpty(manifestHead.getB_status())) {
                    VALUES("B_STATUS", "#{manifestHead.b_status}");
                }
                if (!StringUtils.isEmpty(manifestHead.getStatus())) {
                    VALUES("STATUS", "#{manifestHead.status}");
                }
                if (!StringUtils.isEmpty(manifestHead.getPort_status())) {
                    VALUES("PORT_STATUS", "#{manifestHead.port_status}");
                }
                if (!StringUtils.isEmpty(manifestHead.getApp_person())) {
                    VALUES("APP_PERSON", "#{manifestHead.app_person}");
                }
                if (!StringUtils.isEmpty(manifestHead.getApp_date())) {
                    VALUES("APP_DATE", "#{manifestHead.app_date}");
                }
                if (!StringUtils.isEmpty(manifestHead.getInput_code())) {
                    VALUES("INPUT_CODE", "#{manifestHead.input_code}");
                }
                if (!StringUtils.isEmpty(manifestHead.getInput_name())) {
                    VALUES("INPUT_NAME", "#{manifestHead.input_name}");
                }
                if (!StringUtils.isEmpty(manifestHead.getTrade_code())) {
                    VALUES("TRADE_CODE", "#{manifestHead.trade_code}");
                }
                if (!StringUtils.isEmpty(manifestHead.getTrade_name())) {
                    VALUES("TRADE_NAME", "#{manifestHead.trade_name}");
                }
                if (!StringUtils.isEmpty(manifestHead.getRegion_code())) {
                    VALUES("REGION_CODE", "#{manifestHead.region_code}");
                }
                if (!StringUtils.isEmpty(manifestHead.getCustoms_code())) {
                    VALUES("CUSTOMS_CODE", "#{manifestHead.customs_code}");
                }
                if (!StringUtils.isEmpty(manifestHead.getNote())) {
                    VALUES("NOTE", "#{manifestHead.note}");
                }
                if (!StringUtils.isEmpty(manifestHead.getExtend_field_3())) {
                    VALUES("EXTEND_FIELD_3", "#{manifestHead.extend_field_3}");
                }
                if (!StringUtils.isEmpty(manifestHead.getPlat_from())) {
                    VALUES("PLAT_FROM", "#{manifestHead.plat_from}");
                }
                if (!StringUtils.isEmpty(manifestHead.getWriting_mode())) {
                    VALUES("WRITING_MODE", "#{manifestHead.writing_mode}");
                }


            }
        }.toString();
    }

    public String updateImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead) {
        return new SQL() {
            {
                UPDATE("T_IMP_ORDER_HEAD t");
                if (!StringUtils.isEmpty(impOrderHead.getGuid())) {
                    WHERE("t.GUID = #{impOrderHead.guid}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getOrder_No())) {
                    WHERE("t.ORDER_NO = #{impOrderHead.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Type())) {
                    SET("t.APP_TYPE = #{impOrderHead.app_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Time())) {
                    SET("t.APP_TIME = #{impOrderHead.app_Time}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Status())) {
                    SET("t.APP_STATUS = #{impOrderHead.app_Status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getOrder_Type())) {
                    SET("t.ORDER_TYPE = #{impOrderHead.order_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Type())) {
                    SET("t.ORDER_NO = #{impOrderHead.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Type())) {
                    SET("t.EBP_CODE = #{impOrderHead.ebp_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Type())) {
                    SET("t.EBP_NAME = #{impOrderHead.ebp_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Type())) {
                    SET("t.EBC_CODE = #{impOrderHead.ebc_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getApp_Type())) {
                    SET("t.EBC_NAME = #{impOrderHead.ebc_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getGoods_Value())) {
                    SET("t.GOODS_VALUE = #{impOrderHead.goods_Value}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getFreight())) {
                    SET("t.FREIGHT = #{impOrderHead.freight}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getDiscount())) {
                    SET("t.DISCOUNT = #{impOrderHead.discount}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getTax_Total())) {
                    SET("t.TAX_TOTAL = #{impOrderHead.tax_Total}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getActural_Paid())) {
                    SET("t.ACTURAL_PAID = #{impOrderHead.actural_Paid}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getCurrency())) {
                    SET("t.CURRENCY = #{impOrderHead.currency}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Reg_No())) {
                    SET("t.BUYER_REG_NO = #{impOrderHead.buyer_Reg_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Name())) {
                    SET("t.BUYER_NAME = #{impOrderHead.buyer_Name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Id_Type())) {
                    SET("t.BUYER_ID_TYPE = #{impOrderHead.buyer_Id_Type}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_Id_Number())) {
                    SET("t.BUYER_ID_NUMBER = #{impOrderHead.buyer_Id_Number}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPay_Code())) {
                    SET("t.PAY_CODE = #{impOrderHead.pay_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPayName())) {
                    SET("t.PAYNAME = #{impOrderHead.payName}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getPay_Transaction_Id())) {
                    SET("t.PAY_TRANSACTION_ID = #{impOrderHead.pay_Transaction_Id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBatch_Numbers())) {
                    SET("t.BATCH_NUMBERS = #{impOrderHead.batch_Numbers}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee())) {
                    SET("t.CONSIGNEE = #{impOrderHead.consignee}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Telephone())) {
                    SET("t.CONSIGNEE_TELEPHONE = #{impOrderHead.consignee_Telephone}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Address())) {
                    SET("CONSIGNEE_ADDRESS = #{impOrderHead.consignee_Address}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getConsignee_Ditrict())) {
                    SET("CONSIGNEE_DITRICT = #{impOrderHead.consignee_Ditrict}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getNote())) {
                    SET("NOTE = #{impOrderHead.note}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getData_status())) {
                    SET("DATA_STATUS = #{impOrderHead.data_status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getUpd_id())) {
                    SET("UPD_ID = #{impOrderHead.upd_id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getData_status())) {
                    SET("UPD_TM = sysdate");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_status())) {
                    SET("RETURN_STATUS = #{impOrderHead.return_status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_info())) {
                    SET("RETURN_INFO = #{impOrderHead.return_info}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_time())) {
                    SET("RETURN_TIME = #{impOrderHead.return_time}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEnt_id())) {
                    SET("ENT_ID = #{impOrderHead.ent_id}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEnt_name())) {
                    SET("ENT_NAME = #{impOrderHead.ent_name}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEnt_customs_code())) {
                    SET("ENT_CUSTOMS_CODE = #{impOrderHead.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBill_No())) {
                    SET("BILL_NO = #{impOrderHead.bill_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBuyer_TelePhone())) {
                    SET("BUYER_TELEPHONE = #{impOrderHead.buyer_TelePhone}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getBusiness_type())) {
                    SET("BUSINESS_TYPE = #{impOrderHead.business_type}");
                }

            }
        }.toString();
    }

    public String updateImpPayment(@Param("impPayment") ImpPayment impPayment) {
        return new SQL() {
            {
                UPDATE("T_IMP_PAYMENT");
                if (!StringUtils.isEmpty(impPayment.getGuid())) {
                    WHERE("GUID = #{impPayment.guid}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_transaction_id())) {
                    WHERE("PAY_TRANSACTION_ID = #{impPayment.pay_transaction_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getApp_type())) {
                    SET("APP_TYPE = #{impPayment.app_type}");
                }
                if (!StringUtils.isEmpty(impPayment.getApp_time())) {
                    SET("APP_TIME = #{impPayment.app_time}");
                }
                if (!StringUtils.isEmpty(impPayment.getApp_status())) {
                    SET("APP_STATUS = #{impPayment.app_status}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_code())) {
                    SET("PAY_CODE = #{impPayment.pay_code}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_name())) {
                    SET("PAY_NAME = #{impPayment.pay_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getOrder_no())) {
                    SET("ORDER_NO = #{impPayment.order_no}");
                }
                if (!StringUtils.isEmpty(impPayment.getEbp_code())) {
                    SET("EBP_CODE = #{impPayment.ebp_code}");
                }
                if (!StringUtils.isEmpty(impPayment.getEbp_name())) {
                    SET("EBP_NAME = #{impPayment.ebp_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getPayer_id_type())) {
                    SET("PAYER_ID_TYPE = #{impPayment.payer_id_type}");
                }
                if (!StringUtils.isEmpty(impPayment.getPayer_id_number())) {
                    SET("PAYER_ID_NUMBER = #{impPayment.payer_id_number}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_name())) {
                    SET("PAYER_NAME = #{impPayment.payer_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getTelephone())) {
                    SET("TELEPHONE = #{impPayment.telephone}");
                }
                if (!StringUtils.isEmpty(impPayment.getAmount_paid())) {
                    SET("AMOUNT_PAID = #{impPayment.amount_paid}");
                }
                if (!StringUtils.isEmpty(impPayment.getCurrency())) {
                    SET("CURRENCY = #{impPayment.currency}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_time())) {
                    SET("PAY_TIME = #{impPayment.pay_time}");
                }
                if (!StringUtils.isEmpty(impPayment.getNote())) {
                    SET("NOTE = #{impPayment.note}");
                }
                if (!StringUtils.isEmpty(impPayment.getData_status())) {
                    SET("DATA_STATUS = #{impPayment.data_status}");
                }
                if (!StringUtils.isEmpty(impPayment.getUpd_id())) {
                    SET("UPD_ID = #{impPayment.upd_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getUpd_tm())) {
                    SET("UPD_TM = #{impPayment.upd_tm}");
                }
                if (!StringUtils.isEmpty(impPayment.getReturn_status())) {
                    SET("RETURN_STATUS = #{impPayment.return_status}");
                }
                if (!StringUtils.isEmpty(impPayment.getReturn_info())) {
                    SET("RETURN_INFO = #{impPayment.return_info}");
                }
                if (!StringUtils.isEmpty(impPayment.getReturn_time())) {
                    SET("RETURN_TIME = #{impPayment.return_time}");
                }
                if (!StringUtils.isEmpty(impPayment.getEnt_id())) {
                    SET("ENT_ID = #{impPayment.ent_id}");
                }
                if (!StringUtils.isEmpty(impPayment.getEnt_name())) {
                    SET("ENT_NAME = #{impPayment.ent_name}");
                }
                if (!StringUtils.isEmpty(impPayment.getEnt_customs_code())) {
                    SET("ENT_CUSTOMS_CODE = #{impPayment.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(impPayment.getBusiness_type())) {
                    SET("BUSINESS_TYPE = #{impPayment.business_type}");
                }
                if (!StringUtils.isEmpty(impPayment.getPay_time_char())) {
                    SET("PAY_TIME_CHAR = #{impPayment.pay_time_char}");
                }
            }
        }.toString();
    }

    public String updateImpLogistics(@Param("impLogistics") ImpLogistics impLogistics) {
        return new SQL() {
            {
                UPDATE("T_IMP_LOGISTICS");
                if (!StringUtils.isEmpty(impLogistics.getGuid())) {
                    WHERE("GUID = #{impLogistics.guid}");
                }
                if (!StringUtils.isEmpty(impLogistics.getLogistics_no())) {
                    WHERE("LOGISTICS_NO = #{impLogistics.logistics_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getApp_type())) {
                    SET("APP_TYPE = #{impLogistics.app_type}");
                }
                if (!StringUtils.isEmpty(impLogistics.getApp_time())) {
                    SET("APP_TIME = #{impLogistics.app_time}");
                }
                if (!StringUtils.isEmpty(impLogistics.getApp_status())) {
                    SET("APP_STATUS = #{impLogistics.app_status}");
                }
                if (!StringUtils.isEmpty(impLogistics.getLogistics_code())) {
                    SET("LOGISTICS_CODE = #{impLogistics.logistics_code}");
                }
                if (!StringUtils.isEmpty(impLogistics.getLogistics_name())) {
                    SET("LOGISTICS_NAME = #{impLogistics.logistics_name}");
                }
                if (!StringUtils.isEmpty(impLogistics.getBill_no())) {
                    SET("BILL_NO = #{impLogistics.bill_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getFreight())) {
                    SET("FREIGHT = #{impLogistics.freight}");
                }
                if (!StringUtils.isEmpty(impLogistics.getInsured_fee())) {
                    SET("INSURED_FEE = #{impLogistics.insured_fee}");
                }
                if (!StringUtils.isEmpty(impLogistics.getCurrency())) {
                    SET("CURRENCY = #{impLogistics.currency}");
                }
                if (!StringUtils.isEmpty(impLogistics.getWeight())) {
                    SET("WEIGHT = #{impLogistics.weight}");
                }
                if (!StringUtils.isEmpty(impLogistics.getPack_no())) {
                    SET("PACK_NO = #{impLogistics.pack_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getGoods_info())) {
                    SET("GOODS_INFO = #{impLogistics.goods_info}");
                }
                if (!StringUtils.isEmpty(impLogistics.getConsingee())) {
                    SET("CONSINGEE = #{impLogistics.consingee}");
                }
                if (!StringUtils.isEmpty(impLogistics.getConsignee_address())) {
                    SET("CONSIGNEE_ADDRESS = #{impLogistics.consignee_address}");
                }
                if (!StringUtils.isEmpty(impLogistics.getConsignee_telephone())) {
                    SET("CONSIGNEE_TELEPHONE = #{impLogistics.consignee_telephone}");
                }
                if (!StringUtils.isEmpty(impLogistics.getNote())) {
                    SET("NOTE = #{impLogistics.note}");
                }
                if (!StringUtils.isEmpty(impLogistics.getData_status())) {
                    SET("DATA_STATUS = #{impLogistics.data_status}");
                }
                if (!StringUtils.isEmpty(impLogistics.getUpd_id())) {
                    SET("UPD_ID = #{impLogistics.upd_id}");
                }
                if (!StringUtils.isEmpty(impLogistics.getUpd_tm())) {
                    SET("UPD_TM = #{impLogistics.upd_tm}");
                }
                if (!StringUtils.isEmpty(impLogistics.getReturn_status())) {
                    SET("RETURN_STATUS = #{impLogistics.return_status}");
                }
                if (!StringUtils.isEmpty(impLogistics.getReturn_info())) {
                    SET("RETURN_INFO = #{impLogistics.return_info}");
                }
                if (!StringUtils.isEmpty(impLogistics.getReturn_time())) {
                    SET("RETURN_TIME = #{impLogistics.return_time}");
                }
                if (!StringUtils.isEmpty(impLogistics.getEnt_id())) {
                    SET("ENT_ID = #{impLogistics.ent_id}");
                }
                if (!StringUtils.isEmpty(impLogistics.getEnt_name())) {
                    SET("ENT_NAME = #{impLogistics.ent_name}");
                }
                if (!StringUtils.isEmpty(impLogistics.getEnt_customs_code())) {
                    SET("ENT_CUSTOMS_CODE = #{impLogistics.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(impLogistics.getVoyage_no())) {
                    SET("VOYAGE_NO = #{impLogistics.voyage_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getOrder_no())) {
                    SET("ORDER_NO = #{impLogistics.order_no}");
                }
                if (!StringUtils.isEmpty(impLogistics.getBusiness_type())) {
                    SET("BUSINESS_TYPE = #{impLogistics.business_type}");
                }
            }
        }.toString();
    }

    public String updateImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus) {
        return new SQL() {
            {
                UPDATE("T_IMP_LOGISTICS_STATUS");
                if (!StringUtils.isEmpty(impLogisticsStatus.getGuid())) {
                    WHERE("GUID = #{impLogisticsStatus.guid}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_no())) {
                    WHERE("LOGISTICS_NO = #{impLogisticsStatus.logistics_no}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getApp_type())) {
                    SET("APP_TYPE = #{impLogisticsStatus.app_type}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getApp_time())) {
                    SET("APP_TIME = #{impLogisticsStatus.app_time}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getApp_status())) {
                    SET("APP_STATUS = #{impLogisticsStatus.app_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_code())) {
                    SET("LOGISTICS_CODE = #{impLogisticsStatus.logistics_code}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_name())) {
                    SET("LOGISTICS_NAME = #{impLogisticsStatus.logistics_name}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_status())) {
                    SET("LOGISTICS_STATUS = #{impLogisticsStatus.logistics_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getLogistics_time())) {
                    SET("LOGISTICS_TIME = #{impLogisticsStatus.logistics_time}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getNote())) {
                    SET("NOTE = #{impLogisticsStatus.note}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getData_status())) {
                    SET("DATA_STATUS = #{impLogisticsStatus.data_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getUpd_id())) {
                    SET("UPD_ID = #{impLogisticsStatus.upd_id}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getUpd_tm())) {
                    SET("UPD_TM = #{impLogisticsStatus.upd_tm}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getReturn_status())) {
                    SET("RETURN_STATUS = #{impLogisticsStatus.return_status}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getReturn_info())) {
                    SET("RETURN_INFO = #{impLogisticsStatus.return_info}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getReturn_time())) {
                    SET("RETURN_TIME = #{impLogisticsStatus.return_time}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getEnt_id())) {
                    SET("ENT_ID = #{impLogisticsStatus.ent_id}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getEnt_name())) {
                    SET("ENT_NAME = #{impLogisticsStatus.ent_name}");
                }
                if (!StringUtils.isEmpty(impLogisticsStatus.getEnt_customs_code())) {
                    SET("ENT_CUSTOMS_CODE = #{impLogisticsStatus.ent_customs_code}");
                }
            }
        }.toString();
    }

    public String updateImpInventoryHead(@Param("impInventoryHead") ImpInventoryHead impInventoryHead) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD");
                if (!StringUtils.isEmpty(impInventoryHead.getGuid())) {
                    WHERE("GUID = #{impInventoryHead.guid}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getOrder_no())) {
                    WHERE("ORDER_NO = #{impInventoryHead.order_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getApp_type())) {
                    SET("APP_TYPE = #{impInventoryHead.app_type}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getApp_time())) {
                    SET("APP_TIME = #{impInventoryHead.app_time}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getApp_status())) {
                    SET("APP_STATUS = #{impInventoryHead.app_status}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbp_code())) {
                    SET("EBP_CODE = #{impInventoryHead.ebp_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbp_name())) {
                    SET("EBP_NAME = #{impInventoryHead.ebp_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbc_code())) {
                    SET("EBC_CODE = #{impInventoryHead.ebc_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEbc_name())) {
                    SET("EBC_NAME = #{impInventoryHead.ebc_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLogistics_no())) {
                    SET("LOGISTICS_NO = #{impInventoryHead.logistics_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLogistics_code())) {
                    SET("LOGISTICS_CODE = #{impInventoryHead.logistics_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLogistics_name())) {
                    SET("LOGISTICS_NAME = #{impInventoryHead.logistics_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCop_no())) {
                    SET("COP_NO = #{impInventoryHead.cop_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getPre_no())) {
                    SET("PRE_NO = #{impInventoryHead.pre_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getAssure_code())) {
                    SET("ASSURE_CODE = #{impInventoryHead.assure_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEms_no())) {
                    SET("EMS_NO = #{impInventoryHead.ems_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getInvt_no())) {
                    SET("INVT_NO = #{impInventoryHead.invt_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getIe_flag())) {
                    SET("IE_FLAG = #{impInventoryHead.ie_flag}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getDecl_time())) {
                    SET("DECL_TIME = #{impInventoryHead.decl_time}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCustoms_code())) {
                    SET("CUSTOMS_CODE = #{impInventoryHead.customs_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getPort_code())) {
                    SET("PORT_CODE = #{impInventoryHead.port_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getIe_date())) {
                    SET("IE_DATE = #{impInventoryHead.ie_date}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_id_type())) {
                    SET("BUYER_ID_TYPE = #{impInventoryHead.buyer_id_type}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_id_number())) {
                    SET("BUYER_ID_NUMBER = #{impInventoryHead.buyer_id_number}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_name())) {
                    SET("BUYER_NAME = #{impInventoryHead.buyer_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBuyer_telephone())) {
                    SET("BUYER_TELEPHONE = #{impInventoryHead.buyer_telephone}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getConsignee_address())) {
                    SET("CONSIGNEE_ADDRESS = #{impInventoryHead.consignee_address}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getAgent_code())) {
                    SET("AGENT_CODE = #{impInventoryHead.agent_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getAgent_name())) {
                    SET("AGENT_NAME = #{impInventoryHead.agent_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getArea_code())) {
                    SET("AREA_CODE = #{impInventoryHead.area_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getArea_name())) {
                    SET("AREA_NAME = #{impInventoryHead.area_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getTrade_mode())) {
                    SET("TRADE_MODE = #{impInventoryHead.trade_mode}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getTraf_mode())) {
                    SET("TRAF_MODE = #{impInventoryHead.traf_mode}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getTraf_no())) {
                    SET("TRAF_NO = #{impInventoryHead.traf_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getVoyage_no())) {
                    SET("VOYAGE_NO = #{impInventoryHead.voyage_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBill_no())) {
                    SET("BILL_NO = #{impInventoryHead.bill_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLoct_no())) {
                    SET("LOCT_NO = #{impInventoryHead.loct_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getLicense_no())) {
                    SET("LICENSE_NO = #{impInventoryHead.license_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCountry())) {
                    SET("COUNTRY = #{impInventoryHead.country}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getFreight())) {
                    SET("FREIGHT = #{impInventoryHead.freight}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getInsured_fee())) {
                    SET("INSURED_FEE = #{impInventoryHead.insured_fee}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCurrency())) {
                    SET("CURRENCY = #{impInventoryHead.currency}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getWrap_type())) {
                    SET("WRAP_TYPE = #{impInventoryHead.wrap_type}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getPack_no())) {
                    SET("PACK_NO = #{impInventoryHead.pack_no}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getGross_weight())) {
                    SET("GROSS_WEIGHT = #{impInventoryHead.gross_weight}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getNet_weight())) {
                    SET("NET_WEIGHT = #{impInventoryHead.net_weight}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getNote())) {
                    SET("NOTE = #{impInventoryHead.note}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getData_status())) {
                    SET("DATA_STATUS = #{impInventoryHead.data_status}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getUpd_id())) {
                    SET("UPD_ID = #{impInventoryHead.upd_id}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getCrt_tm())) {
                    SET("UPD_TM = #{impInventoryHead.upd_tm}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getReturn_status())) {
                    SET("RETURN_STATUS = #{impInventoryHead.return_status}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getReturn_info())) {
                    SET("RETURN_INFO = #{impInventoryHead.return_info}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getReturn_time())) {
                    SET("RETURN_TIME = #{impInventoryHead.return_time}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEnt_id())) {
                    SET("ENT_ID = #{impInventoryHead.ent_id}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEnt_name())) {
                    SET("ENT_NAME = #{impInventoryHead.ent_name}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getEnt_customs_code())) {
                    SET("ENT_CUSTOMS_CODE = #{impInventoryHead.ent_customs_code}");
                }
                if (!StringUtils.isEmpty(impInventoryHead.getBusiness_type())) {
                    SET("BUSINESS_TYPE = #{impInventoryHead.business_type}");
                }
            }
        }.toString();
    }


    public String queryInventoryHeadPrice(@Param("guid") String guid) {
        return new SQL() {
            {
                SELECT("TOTAL_PRICES");
                FROM("T_IMP_INVENTORY_HEAD");
                WHERE("GUID = #{guid}");
            }
        }.toString();
    }

    public String setInventoryHeadPrice(@Param("impInventoryBody") ImpInventoryBody impInventoryBody) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD");
                WHERE("GUID = #{impInventoryBody.head_guid}");
                SET("TOTAL_PRICES = #{impInventoryBody.total_price}");
            }
        }.toString();
    }

    public String countInventoryHeadPrice(@Param("impInventoryBody") ImpInventoryBody impInventoryBody) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD");
                WHERE("GUID = #{impInventoryBody.head_guid}");
                SET("TOTAL_PRICES = TOTAL_PRICES + #{impInventoryBody.total_price}");
            }
        }.toString();
    }

}
