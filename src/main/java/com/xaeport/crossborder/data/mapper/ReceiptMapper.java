package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.ReceiptSQLProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface ReceiptMapper {

    //订单回执插入
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createImpRecOrder")
    void createImpRecOrder(@Param("impRecOrder") ImpRecOrder impRecOrder);

    //订单更新状态
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpOrder")
    void updateImpOrder(@Param("impOrderHead") ImpOrderHead impOrderHead);

    //支付单回执插入
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createImpRecPayment")
    boolean createImpRecPayment(@Param("impRecPayment") ImpRecPayment impRecPayment) throws Exception;

    //支付单更新状态
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpPayment")
    boolean updateImpPayment(@Param("impPayment") ImpPayment impPayment) throws Exception;

    //运单回执插入
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createImpRecLogistics")
    void createImpRecLogistics(@Param("impRecLogistics") ImpRecLogistics impRecLogistics);

    //运单更新状态
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpLogistics")
    void updateImpLogistics(@Param("impLogistics") ImpLogistics impLogistics);

    //运单状态回执插入
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createImpRecLogisticsStatus")
    void createImpRecLogisticsStatus(@Param("impRecLogisticsStatus") ImpRecLogisticsStatus impRecLogisticsStatus);

    //运单状态更新状态
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpLogisticsStatus")
    void updateImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus);

    //运单更新最终状态为“CBDS52”（运单申报成功，运单状态申报成功）
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpLogisticsDataStatus")
    void updateImpLogisticsDataStatus(@Param("impRecLogisticsStatus") ImpRecLogisticsStatus impRecLogisticsStatus, @Param("ydztsbcg") String ydztsbcg);

    //清单回执插入
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createImpRecInventory")
    boolean createImpRecInventory(@Param("impRecInventory") ImpRecInventory impRecInventory) throws Exception;

    //清单更新状态
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpInventory")
    boolean updateImpInventory(@Param("impInventoryHead") ImpInventoryHead impInventoryHead) throws Exception;

    //清单数据查询
    @SelectProvider(type = ReceiptSQLProvider.class, method = "findByCopNo")
    ImpInventoryHead findByCopNo(String copNo) throws Exception;

}
