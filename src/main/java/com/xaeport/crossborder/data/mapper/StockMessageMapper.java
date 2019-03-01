package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.StockMessageSQLProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StockMessageMapper {

    //订单报文表头入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpOrderHead")
    void insertImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead);

    //修改订单报文表头
    @UpdateProvider(type = StockMessageSQLProvider.class, method = "updateImpOrderHead")
    void updateImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead);

    //订单报文表体入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpOrderBody")
    void insertImpOrderBody(@Param("impOrderBody") ImpOrderBody impOrderBody);


    //支付单报文入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpPayment")
    void insertImpPayment(@Param("impPayment") ImpPayment impPayment);

    //修改支付单表头
    @UpdateProvider(type = StockMessageSQLProvider.class, method = "updateImpPayment")
    void updateImpPayment(@Param("impPayment") ImpPayment impPayment);


    //运单报文入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpLogistics")
    void insertImpLogistics(@Param("impLogistics") ImpLogistics impLogistics);

    //运单报文入库
    @UpdateProvider(type = StockMessageSQLProvider.class, method = "updateImpLogistics")
    void updateImpLogistics(@Param("impLogistics") ImpLogistics impLogistics);


    //运单状态报文入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpLogisticsStatus")
    void insertImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus);

    //运单状态报文入库
    @UpdateProvider(type = StockMessageSQLProvider.class, method = "updateImpLogisticsStatus")
    void updateImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus);


    //清单报文表头入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpInventoryHead")
    void insertImpInventoryHead(@Param("impInventoryHead") ImpInventoryHead impInventoryHead);

    //修改清单表头数据
    @UpdateProvider(type = StockMessageSQLProvider.class, method = "updateImpInventoryHead")
    void updateImpInventoryHead(@Param("impInventoryHead") ImpInventoryHead impInventoryHead);

    //清单报文表体入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpInventoryBody")
    void insertImpInventoryBody(@Param("impInventoryBody") ImpInventoryBody impInventoryBody);


    //入库明细单表头入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpDeliveryHead")
    void insertImpDeliveryHead(@Param("impDeliveryHead") ImpDeliveryHead impDeliveryHead);

    //入库明细单表体入库
    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpDeliveryBody")
    void insertImpDeliveryBody(@Param("impDeliveryBody") ImpDeliveryBody impDeliveryBody);


    //查询订单表头数据
    @Select("SELECT COUNT(1) FROM T_IMP_ORDER_HEAD t WHERE t.GUID = #{impOrderHead.guid} and t.ORDER_NO = #{impOrderHead.order_No}")
    int queryImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead);

    //查询订单表头数据
    @Select("SELECT ORDER_NO,APP_TYPE FROM T_IMP_ORDER_HEAD t WHERE t.GUID = #{impOrderHead.guid}")
    ImpOrderHead queryImpOrderhead(String guid);

    //删除订单表体数据
    @Select("DELETE FROM T_IMP_ORDER_BODY t WHERE t.HEAD_GUID = #{guid}")
    void deleteImpOrderBody(String guid);

    //查询支付单数据
    @Select("SELECT COUNT(1) FROM T_IMP_PAYMENT t WHERE t.GUID = #{impPayment.guid} and t.PAY_TRANSACTION_ID = #{impPayment.pay_transaction_id}")
    int queryImpPayment(@Param("impPayment") ImpPayment impPayment);

    //查询物流运单数据
    @Select("SELECT COUNT(1) FROM T_IMP_LOGISTICS t WHERE t.GUID = #{impLogistics.guid} and t.LOGISTICS_NO = #{impLogistics.logistics_no}")
    int queryImpLogistics(@Param("impLogistics") ImpLogistics impLogistics);

    //查询物流运单数据
    @Select("SELECT COUNT(1) FROM T_IMP_LOGISTICS_STATUS t WHERE t.GUID = #{impLogisticsStatus.guid} and t.LOGISTICS_NO = #{impLogisticsStatus.logistics_no}")
    int queryImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus);

    //查询清单表头数据
    @Select("SELECT COUNT(1) FROM T_IMP_INVENTORY_HEAD t WHERE t.GUID = #{impInventoryHead.guid} and t.ORDER_NO = #{impInventoryHead.order_no}")
    int queryImpInventoryHead(@Param("impInventoryHead") ImpInventoryHead impInventoryHead);

    //删除订单表体数据
    @Select("DELETE FROM T_IMP_INVENTORY_BODY t WHERE t.HEAD_GUID = #{guid}")
    void deleteImpInventoryBody(String guid);

    //查询清单表体数据
    @Select("SELECT COUNT(1) FROM T_IMP_INVENTORY_BODY t WHERE t.GUID = #{guid}")
    int queryImpInventoryBody(String guid);


    //跨境核放单
    @SelectProvider(type = StockMessageSQLProvider.class, method = "queryManifestData")
    int queryManifestData(@Param("manifestHead") ManifestHead manifestHead);

    @UpdateProvider(type = StockMessageSQLProvider.class, method = "updateManifestData")
    void updateManifestData(@Param("manifestHead") ManifestHead manifestHead);

    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertManifestData")
    void insertManifestData(@Param("manifestHead") ManifestHead manifestHead);

}
