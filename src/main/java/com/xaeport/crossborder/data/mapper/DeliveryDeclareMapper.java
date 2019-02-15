package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.DeliveryDeclareSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface DeliveryDeclareMapper {

//    //查询符合生成入库明细单的运单数据
//    @SelectProvider(type = DeliveryDeclareSQLProvider.class, method = "findLogisticsData")
//    List<ImpLogistics> findLogisticsData(Map<String, String> paramMap) throws Exception;
//
//    //对于生成过的运单设置状态
//    @UpdateProvider(type = DeliveryDeclareSQLProvider.class, method = "updateLogistics")
//    void updateLogistics(String guid) throws Exception;

    //查询入库明细单数据
    @SelectProvider(type = DeliveryDeclareSQLProvider.class, method = "queryDeliveryDeclareList")
    List<ImpDelivery> queryDeliveryDeclareList(Map<String, String> paramMap) throws Exception;

    //查询入库明细单总数
    @SelectProvider(type = DeliveryDeclareSQLProvider.class, method = "queryDeliveryDeclareCount")
    Integer queryDeliveryDeclareCount(Map<String, String> paramMap) throws Exception;

    //查询符合生成入库明细单的预定数据
    @SelectProvider(type = DeliveryDeclareSQLProvider.class, method = "findCheckGoodsInfo")
    List<CheckGoodsInfo> findCheckGoodsInfo() throws Exception;

    //插入入库明细单表
    @InsertProvider(type = DeliveryDeclareSQLProvider.class, method = "insertImpDelivery")
    void insertImpDelivery(@Param("impDeliveryHead") ImpDeliveryHead impDeliveryHead) throws Exception;

    //对于生成过的预订数据设置状态
    @UpdateProvider(type = DeliveryDeclareSQLProvider.class, method = "updateCheckGoodsInfoStatus")
    void updateCheckGoodsInfoStatus(String guid) throws Exception;

    //置为申报中状态
    @UpdateProvider(type = DeliveryDeclareSQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap);

    //置为申报中状态
    @UpdateProvider(type = DeliveryDeclareSQLProvider.class, method = "setDeliveryData")
    void setDeliveryData(@Param("enterprise") Enterprise enterprise, @Param("submitKeys") String submitKeys);

    //查询企业信息
    @SelectProvider(type = DeliveryDeclareSQLProvider.class, method = "queryCompany")
    BaseTransfer queryCompany(@Param("ent_id") String ent_id);

    //根据申报中状态查找入库明细单
    @SelectProvider(type = DeliveryDeclareSQLProvider.class, method = "findWaitGenerated")
    List<ImpDeliveryHead> findWaitGenerated(Map<String, String> paramMap);

    //修改申报状态
    @UpdateProvider(type = DeliveryDeclareSQLProvider.class, method = "updateDeliveryStatus")
    void updateDeliveryStatus(@Param("guid") String guid, @Param("dataStatus") String dataStatus);

    @Update("UPDATE T_IMP_DELIVERY_HEAD t SET t.COP_NO = #{impDeliveryHead.cop_no} WHERE t.guid = #{impDeliveryHead.guid}")
    void setDeliveryCopNo(@Param("impDeliveryHead") ImpDeliveryHead impDeliveryHead);

    //根据提运单号查询运单数据的航班航次号
    @Select("select * from (SELECT VOYAGE_NO FROM T_IMP_LOGISTICS WHERE BILL_NO = #{billNo} ORDER BY VOYAGE_NO NULLS LAST) where rownum = 1")
    String queryLigisticsInfo(String billNo);

    //根据提运单号查询入库明细单数据的航班航次号
    @Select("select * from (SELECT VOYAGE_NO FROM T_IMP_DELIVERY_HEAD WHERE BILL_NO = #{billNo} ORDER BY VOYAGE_NO NULLS LAST) where rownum = 1")
    String queryDeliveryInfo(String billNo);

    //根据提运单号查询入库明细单数据的航班航次号
    @SelectProvider(type = DeliveryDeclareSQLProvider.class, method = "queryDeliveryByEmptyVoyage")
    List<String> queryDeliveryByEmptyVoyage(String billNos);

    //根据运单表航班号更新运单的航班号
    @UpdateProvider(type = DeliveryDeclareSQLProvider.class, method = "updateDeliveryByLogistics")
    void updateDeliveryByLogistics(@Param("billNo") String billNo,@Param("voyage") String voyage);

    //查询入库明细单待填写数据
    @SelectProvider(type = DeliveryDeclareSQLProvider.class, method = "querydeliverytofill")
    List<ImpDelivery> querydeliverytofill(String billNodata) throws Exception;

    //填充入库明细单信息
    @UpdateProvider(type = DeliveryDeclareSQLProvider.class, method = "fillDeliveryInfo")
    void fillDeliveryInfo(@Param("deliveryHead") LinkedHashMap<String, String> deliveryHead, @Param("userInfo") Users userInfo);

}
