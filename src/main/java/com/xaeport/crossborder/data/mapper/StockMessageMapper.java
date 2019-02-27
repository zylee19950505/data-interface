package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.StockMessageSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockMessageMapper {

    //订单报文表头入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpOrderHead")
    void insertImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead);

    //订单报文表体入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpOrderBody")
    void insertImpOrderBody(@Param("impOrderBody") ImpOrderBody impOrderBody);

    //支付单报文入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpPayment")
    void insertImpPayment(@Param("impPayment") ImpPayment impPayment);

    //运单报文入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpLogistics")
    void insertImpLogistics(@Param("impLogistics") ImpLogistics impLogistics);

    //运单状态报文入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpLogisticsStatus")
    void insertImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus);

    //清单报文表头入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpInventoryHead")
    void insertImpInventoryHead(@Param("impInventoryHead") ImpInventoryHead impInventoryHead);

    //清单报文表体入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpInventoryBody")
    void insertImpInventoryBody(@Param("impInventoryBody") ImpInventoryBody impInventoryBody);

    //入库明细单表头入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpDeliveryHead")
    void insertImpDeliveryHead(@Param("impDeliveryHead") ImpDeliveryHead impDeliveryHead);

    //入库明细单表体入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpDeliveryBody")
    void insertImpDeliveryBody(@Param("impDeliveryBody") ImpDeliveryBody impDeliveryBody);

}
