package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.apache.poi.util.StringUtil;
import org.springframework.util.StringUtils;

public class ReceiptSQLProvider extends BaseSQLProvider {

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

    public String updateImpPayment(
            @Param("impPayment") ImpPayment impPayment
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_PAYMENT t");
                WHERE("t.DATA_STATUS = 'CBDS31'");
                if(!StringUtils.isEmpty(impPayment.getPay_transaction_id())){
                    WHERE("t.PAY_TRANSACTION_ID = #{impPayment.pay_transaction_id}");
                }
                if(!StringUtils.isEmpty(impPayment.getPay_code())){
                    WHERE("t.PAY_CODE = #{impPayment.pay_code}");
                }
                if(!StringUtils.isEmpty(impPayment.getReturn_status())){
                    SET("t.RETURN_STATUS = #{impPayment.return_status}");
                }
                if(!StringUtils.isEmpty(impPayment.getReturn_info())){
                    SET("t.RETURN_INFO = #{impPayment.return_info}");
                }
                if(!StringUtils.isEmpty(impPayment.getReturn_time())){
                    SET("t.RETURN_TIME = #{impPayment.return_time}");
                }
                if(!StringUtils.isEmpty(impPayment.getUpd_tm())){
                    SET("t.UPD_TM = #{impPayment.upd_tm}");
                }
            }
        }.toString();
    }

    /*
    * 插入订单状态表数据
    * */
    public String createImpRecOrder(@Param("impRecOrder") ImpRecOrder impRecOrder){
        return new SQL(){
            {
                INSERT_INTO("T_IMP_REC_ORDER");
                if (!StringUtils.isEmpty(impRecOrder.getId())){
                    VALUES("ID","#{impRecOrder.id}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getGuid())){
                    VALUES("GUID","#{impRecOrder.guid}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getEbpCode())){
                    VALUES("EBP_CODE","#{impRecOrder.ebpCode}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getEbcCode())){
                    VALUES("EBC_CODE","#{impRecOrder.ebcCode}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getOrderNo())){
                    VALUES("ORDER_NO","#{impRecOrder.orderNo}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getReturnStatus())){
                    VALUES("RETURN_STATUS","#{impRecOrder.returnStatus}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getReturnTime())){
                    VALUES("RETURN_TIME","#{impRecOrder.returnTime}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getReturnInfo())){
                    VALUES("RETURN_INFO","#{impRecOrder.returnInfo}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getCrtTm())){
                    VALUES("CRT_TM","#{impRecOrder.crtTm}");
                }
                if (!StringUtils.isEmpty(impRecOrder.getUpdTm())){
                    VALUES("UPD_TM","#{impRecOrder.updTm}");
                }
            }
        }.toString();
    }
    /*
    * 根据回执信息更新订单支付状态
    * */
    public String updateImpOrder(@Param("impOrderHead") ImpOrderHead impOrderHead){
        return new SQL(){
            {
                UPDATE("T_IMP_ORDER_HEAD t");
                WHERE("t.DATA_STATUS = 'CBDS21'");
                if (!StringUtils.isEmpty(impOrderHead.getGuid())){
                    WHERE("t.GUID = #{impOrderHead.guid}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getOrder_No())){
                    WHERE("t.ORDER_NO = #{impOrderHead.order_No}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbc_Code())){
                    SET("t.EBC_CODE = #{impOrderHead.ebc_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getEbp_Code())){
                    SET("t.EBP_CODE = #{impOrderHead.ebp_Code}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_status())){
                    SET("t.RETURN_STATUS = #{impOrderHead.return_status}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_time())){
                    SET("t.RETURNTIME = #{impOrderHead.return_time}");
                }
                if (!StringUtils.isEmpty(impOrderHead.getReturn_info())){
                    SET("t.RETURNINFO = #{impOrderHead.return_info}");
                }
            }
        }.toString();
    }

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

    public String updateImpInventory(
            @Param("impInventoryHead") ImpInventoryHead impInventoryHead
    ) {
        return new SQL() {
            {
                UPDATE("T_IMP_INVENTORY_HEAD t");
                WHERE("t.DATA_STATUS = 'CBDS61'");
                if(!StringUtils.isEmpty(impInventoryHead.getCop_no())){
                    WHERE("t.COP_NO = #{impInventoryHead.cop_no}");
                }
                if(!StringUtils.isEmpty(impInventoryHead.getReturn_status())){
                    SET("t.RETURN_STATUS = #{impInventoryHead.return_status}");
                }
                if(!StringUtils.isEmpty(impInventoryHead.getReturn_info())){
                    SET("t.RETURN_INFO = #{impInventoryHead.return_info}");
                }
                if(!StringUtils.isEmpty(impInventoryHead.getReturn_time())){
                    SET("t.RETURN_TIME = #{impInventoryHead.return_time}");
                }
                if(!StringUtils.isEmpty(impInventoryHead.getUpd_tm())){
                    SET("t.UPD_TM = #{impInventoryHead.upd_tm}");
                }
            }
        }.toString();
    }

}
