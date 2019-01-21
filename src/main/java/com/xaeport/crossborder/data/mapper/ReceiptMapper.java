package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.ReceiptSQLProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface ReceiptMapper {

    @Select("SELECT BUSINESS_TYPE FROM T_IMP_INVENTORY_HEAD WHERE COP_NO = #{copNo}")
    String queryBusiTypeByCopNo(@Param("copNo") String copNo);

    //插入核注清单处理成功回执数据
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createInvtCommon")
    void createInvtCommon(@Param("recBondInvtCommon") RecBondInvtCommon recBondInvtCommon);
    //根据核注清单处理成功回执更新状态
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateBondInvtStatusByCommon")
    void updateBondInvtStatusByCommon(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);
    //根据核注清单处理成功回执更新表体数据
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateNemsInvtByCommon")
    void updateNemsInvtByCommon(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    //根据核放单处理成功回执更新状态
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updatePassPortStatusByCommon")
    void updatePassPortStatusByCommon(@Param("passPortHead") PassPortHead passPortHead);
    //根据核放单处理成功回执更新表体数据
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updatePassPortAcmpByCommon")
    void updatePassPortAcmpByCommon(@Param("passPortHead") PassPortHead passPortHead);

    //核注清单(报文回执/审核回执)
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createInvtHdeAppr")
    void createInvtHdeAppr(@Param("recBondInvtHdeAppr") RecBondInvtHdeAppr recBondInvtHdeAppr);
    //核注清单生成报关单回执
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createInvtInvAppr")
    void createInvtInvAppr(@Param("recBondInvtInvAppr") RecBondInvtInvAppr recBondInvtInvAppr);

    //更新修改核注清单状态(HdeAppr)
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateBondInvtStatusByHdeAppr")
    void updateBondInvtStatusByHdeAppr(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);
//    //更新修改核注清单状态(InvAppr)
//    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateBondInvtStatusByInvAppr")
//    void updateBondInvtStatusByInvAppr(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    //核放单(报文回执/审核回执)
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createPassPortHdeAppr")
    void createPassPortHdeAppr(@Param("recPassPortHdeAppr") RecPassPortHdeAppr recPassPortHdeAppr);



    @Select("SELECT * FROM T_BOND_INVT_BSC WHERE ETPS_INNER_INVT_NO = #{etpsPreentNo}")
    BondInvtBsc queryIsBondInvt(@Param("etpsPreentNo") String etpsPreentNo);

    @Select("SELECT * FROM T_PASS_PORT_HEAD WHERE ETPS_PREENT_NO = #{etpsPreentNo}")
    PassPortHead queryIsPassPort(@Param("etpsPreentNo") String etpsPreentNo);


    @Select("SELECT * FROM T_BOND_INVT_BSC WHERE INVT_PREENT_NO = #{invt_preent_no}")
    BondInvtBsc queryBondInvt(@Param("invt_preent_no") String invt_preent_no);


//    @Select("SELECT * FROM T_BOND_INVT_BSC WHERE ETPS_INNER_INVT_NO = #{etpsPreentNo}")
//    BondInvtBsc queryBondInvt(@Param("etpsPreentNo") String etpsPreentNo);



    //插入电子税单表头数据
    @InsertProvider(type = ReceiptSQLProvider.class, method = "InsertTaxHeadRd")
    void InsertTaxHeadRd(@Param("taxHeadRd") TaxHeadRd taxHeadRd);

    //插入电子税单表体数据
    @InsertProvider(type = ReceiptSQLProvider.class, method = "InsertTaxListRd")
    void InsertTaxListRd(@Param("taxListRd") TaxListRd taxListRd);

    //更新清单表头税额
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateInventoryHeadTax")
    void updateInventoryHeadTax(@Param("taxHeadRd") TaxHeadRd taxHeadRd);

    //更新清单表体税额
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateInventoryListTax")
    void updateInventoryListTax(@Param("taxHeadRd") TaxHeadRd taxHeadRd, @Param("taxListRd") TaxListRd taxListRd);

    //核放单预定数据
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createCheckGoodsInfoHis")
    void createCheckGoodsInfoHis(@Param("checkGoodsInfo") CheckGoodsInfo checkGoodsInfo);

    //核放单预定数据
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createCheckGoodsInfo")
    void createCheckGoodsInfo(@Param("checkGoodsInfo") CheckGoodsInfo checkGoodsInfo);

    //清单数据查询
    @SelectProvider(type = ReceiptSQLProvider.class, method = "findByOrderNo")
    CheckGoodsInfo findByOrderNo(String orderNo) throws Exception;

    //清单更新状态
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateCheckGoodsInfo")
    void updateCheckGoodsInfo(@Param("checkGoodsInfo") CheckGoodsInfo checkGoodsInfo) throws Exception;

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

    //清单数据查询
    @SelectProvider(type = ReceiptSQLProvider.class, method = "findByInvtNo")
    ImpInventoryHead findByInvtNo(String invtNo) throws Exception;

    //查询该COP_NO的最晚时间的三位有效状态码
    @SelectProvider(type = ReceiptSQLProvider.class, method = "queryMaxTimeReturnStatus")
    String queryMaxTimeReturnStatus(String copNo) throws Exception;


    //入库明细单回执插入
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createImpRecDelivery")
    boolean createImpRecDelivery(@Param("impRecDelivery") ImpRecDelivery impRecDelivery) throws Exception;

    //入库明细单更新状态
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateImpDelivery")
    boolean updateImpDelivery(@Param("impDeliveryHead") ImpDeliveryHead impDeliveryHead) throws Exception;

    //入库明细单数据查询
    @SelectProvider(type = ReceiptSQLProvider.class, method = "findDeliveryByCopNo")
    ImpDeliveryHead findDeliveryByCopNo(String copNo) throws Exception;

}
