package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import org.apache.poi.util.StringUtil;
import org.springframework.util.StringUtils;
import sun.awt.SunHints;

public class ReceiptSQLProvider extends BaseSQLProvider {

    public String findByCopNo(String copNo) {
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_IMP_INVENTORY_HEAD t");
                WHERE("t.COP_NO = #{copNo}");
            }
        }.toString();
    }

    public String findDeliveryByCopNo(String copNo) {
        return new SQL() {
            {
                SELECT("t.*");
                FROM("T_IMP_DELIVERY_HEAD t");
                WHERE("t.COP_NO = #{copNo}");
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
                /*if (!StringUtils.isEmpty(impOrderHead.getEbc_Code())) {
                    SET("t.EBC_CODE = #{impOrderHead.ebc_Code}");
                }*/
                if (!StringUtils.isEmpty(impOrderHead.getEbp_Code())) {
                    SET("t.EBP_CODE = #{impOrderHead.ebp_Code}");
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
                WHERE("t.DATA_STATUS in ('CBDS61','CBDS62','InvenOver')");
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
                /*if (!StringUtils.isEmpty(impLogistics.getLogistics_code())) {
                    SET("t.LOGISTICS_CODE = #{impLogistics.logistics_code}");
                }*/
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
                if (!StringUtils.isEmpty(impLogistics.getData_status())){
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
                /*if (!StringUtils.isEmpty(impRecLogisticsStatus.getLogistics_Code())) {
                    SET("t.LOGISTICS_CODE = #{impRecLogisticsStatus.logistics_Code}");
                }*/
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
                if (!StringUtils.isEmpty(impDeliveryHead.getBill_no())) {
                    WHERE("t.BILL_NO = #{impDeliveryHead.bill_no}");
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
