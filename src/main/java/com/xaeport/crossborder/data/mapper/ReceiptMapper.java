package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.ReceiptSQLProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface ReceiptMapper {

    //根据企业内部编码查询保税清单表头信息
    @SelectProvider(type = ReceiptSQLProvider.class, method = "queryImpInventoryHeads")
    List<ImpInventoryHead> queryImpInventoryHeads(String EtpsInnerInvtNo);

    //根据企业内部编码查询保税清单表体信息
    @SelectProvider(type = ReceiptSQLProvider.class, method = "queryImpInventoryBodyList")
    List<ImpInventoryBody> queryImpInventoryBodyList(String EtpsInnerInvtNo);

    //确认保税清单库存无误后，设置账册表体预减数量
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "setPrevdRedcQty")
    void setPrevdRedcQty(@Param("qtySum") double qtySum, @Param("item_record_no") String item_record_no, @Param("emsNo") String emsNo);

    //查询检验库存余量是否大于excel导入数量
    @SelectProvider(type = ReceiptSQLProvider.class, method = "checkStockSurplus")
    BwlListType checkStockSurplus(@Param("id") String id, @Param("item_record_no") String item_record_no, @Param("emsNo") String emsNo);


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

    //根据核放单处理成功回执更新表体数据
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updatePassPortListByCommon")
    void updatePassPortListByCommon(@Param("passPortHead") PassPortHead passPortHead);

    //核注清单(报文回执/审核回执)(HdeAppr)（报文一）
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createInvtHdeAppr")
    void createInvtHdeAppr(@Param("recBondInvtHdeAppr") RecBondInvtHdeAppr recBondInvtHdeAppr);

    //更新修改核注清单表头数据(HdeAppr)——保税出区
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateBondInvtStatusByHdeAppr")
    void updateBondInvtStatusByHdeAppr(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    //更新修改核注清单表体数据(HdeAppr)——保税出区
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateNemssByHdeAppr")
    void updateNemssByHdeAppr(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    //更新修改核注清单表头数据(HdeAppr)——保税入区
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateBondInvtBscByHdeAppr")
    void updateBondInvtBscByHdeAppr(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    //更新修改核注清单表体数据(HdeAppr)——保税入区
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateBondInvtDtByHdeAppr")
    void updateBondInvtDtByHdeAppr(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);


    //核注清单生成报关单回执(InvAppr)（报文二）
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createInvtInvAppr")
    void createInvtInvAppr(@Param("recBondInvtInvAppr") RecBondInvtInvAppr recBondInvtInvAppr);

    //更新修改核注清单表头数据(InvAppr)
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateBondInvtStatusByInvAppr")
    void updateBondInvtStatusByInvAppr(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    //更新修改核注清单表体数据(InvAppr)
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updateNemssByInvAppr")
    void updateNemssByInvAppr(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);


    //插入核放单(报文回执/审核回执)(HdeAppr)（报文一）
    @InsertProvider(type = ReceiptSQLProvider.class, method = "createPassPortHdeAppr")
    void createPassPortHdeAppr(@Param("recPassPortHdeAppr") RecPassPortHdeAppr recPassPortHdeAppr);

    //更新核放单表头数据(HdeAppr)
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updatePassportStatusByHdeAppr")
    void updatePassportStatusByHdeAppr(@Param("passPortHead") PassPortHead passPortHead);

    //更新核放单表体数据(HdeAppr)
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updatePassPortAcmpByHdeAppr")
    void updatePassPortAcmpByHdeAppr(@Param("passPortHead") PassPortHead passPortHead);

    //更新核放单表体数据(HdeAppr)(一票多车情况)
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "updatePassPortListByHdeAppr")
    void updatePassPortListByHdeAppr(@Param("passPortHead") PassPortHead passPortHead);


    //
    @Select("SELECT * FROM T_BOND_INVT_BSC WHERE ETPS_INNER_INVT_NO = #{etpsPreentNo}")
    BondInvtBsc queryIsBondInvt(@Param("etpsPreentNo") String etpsPreentNo);

    //根据企业内部编码查询核放单信息（系统自生成编码）
    @Select("SELECT * FROM T_PASS_PORT_HEAD WHERE ETPS_PREENT_NO = #{etpsPreentNo}")
    PassPortHead queryIsPassPort(@Param("etpsPreentNo") String etpsPreentNo);

    //根据企业统一编号查询核注清单信息（数据中心所给编号）
    @Select("SELECT * FROM T_BOND_INVT_BSC WHERE INVT_PREENT_NO = #{invt_preent_no}")
    BondInvtBsc queryBondInvt(@Param("invt_preent_no") String invt_preent_no);

    //根据企业统一编号查询核放单信息（数据中心所给编号）
    @Select("SELECT * FROM T_PASS_PORT_HEAD WHERE SAS_PASSPORT_PREENT_NO = #{invt_preent_no}")
    PassPortHead queryPassPort(@Param("invt_preent_no") String invt_preent_no);


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


    //查询入区预增数据表头查询
    @SelectProvider(type = ReceiptSQLProvider.class, method = "queryBondInvtBscList")
    List<BondInvtBsc> queryBondInvtBscList(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    //查询入区预增数据表体查询
    @SelectProvider(type = ReceiptSQLProvider.class, method = "queryBondInvtDtList")
    List<BondInvtDt> queryBondInvtDtList(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    //查询是否存在账册信息
    @SelectProvider(type = ReceiptSQLProvider.class, method = "checkBwlHeadType")
    BwlHeadType checkBwlHeadType(String emsNo);

    //查询是否存在账册表体信息
    @SelectProvider(type = ReceiptSQLProvider.class, method = "checkBwlListType")
    BwlListType checkBwlListType(@Param("emsNo") String emsNo, @Param("gds_mtno") String gds_mtno);

    //插入入区账册预增数据
    @SelectProvider(type = ReceiptSQLProvider.class, method = "insertBwlListType")
    void insertBwlListType(@Param("bwlListType") BwlListType bwlListType);

    //入区账册预增叠加操作
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "addBwlListType")
    void addBwlListType(@Param("qtySum") double qtySum, @Param("emsNo") String emsNo, @Param("gds_mtno") String gds_mtno);

    //入区账册预增叠加操作
    @UpdateProvider(type = ReceiptSQLProvider.class, method = "actlIncreaseBwlListType")
    void actlIncreaseBwlListType(@Param("qtySum") double qtySum, @Param("emsNo") String emsNo, @Param("gds_mtno") String gds_mtno);

    //根据保税清单编号查询核注清单表头信息
    @Select("SELECT * FROM T_BOND_INVT_BSC t WHERE t.BOND_INVT_NO = #{bondInvtNo}")
    BondInvtBsc queryBondInvtByBondInvtNo(String bondInvtNo);

    //根据保税清单编号查询核注清单表头信息
    @SelectProvider(type = ReceiptSQLProvider.class, method = "queryBondInvtListByNo")
    List<BondInvtBsc> queryBondInvtListByNo(String listNo);

    //根据保税清单编号查询核注清单表体信息
    @SelectProvider(type = ReceiptSQLProvider.class, method = "queryBondInvtDtLists")
    List<BondInvtDt> queryBondInvtDtLists(String listNo);

    //查询入区核放单的表体数据（一单多车的表体数据）
//    @Select("SELECT t.DCL_QTY quantity,t.* FROM T_PASS_PORT_LIST t WHERE t.PASSPORT_NO = #{passPortHead.etps_preent_no}")
    @Select("SELECT t.DCL_QTY quantity,t.* FROM T_PASS_PORT_LIST t WHERE t.HEAD_ID = #{passPortHead.etps_preent_no}")
    List<PassPortList> queryPassPortList(@Param("passPortHead") PassPortHead passPortHead);

}
