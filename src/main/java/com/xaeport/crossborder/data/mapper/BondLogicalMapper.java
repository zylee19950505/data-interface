package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpCrossBorderHead;
import com.xaeport.crossborder.data.provider.BondLogicalSQLProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface BondLogicalMapper {

    @SelectProvider(type = BondLogicalSQLProvider.class, method = "getOrderLogicData")
    List<ImpCrossBorderHead> getOrderLogicData(Map<String, String> map);

    @SelectProvider(type = BondLogicalSQLProvider.class, method = "getInventoryLogicData")
    List<ImpCrossBorderHead> getInventoryLogicData(Map<String, String> map);

    @SelectProvider(type = BondLogicalSQLProvider.class, method = "getPaymentLogicData")
    List<ImpCrossBorderHead> getPaymentLogicData(Map<String, String> map);

    @SelectProvider(type = BondLogicalSQLProvider.class, method = "getLogisticsLogicData")
    List<ImpCrossBorderHead> getLogisticsLogicData(Map<String, String> map);

    @SelectProvider(type = BondLogicalSQLProvider.class, method = "queryDeleteOrderByGuids")
    List<ImpCrossBorderHead> queryDeleteOrderByGuids(Map<String, String> paramMap);

    @SelectProvider(type = BondLogicalSQLProvider.class, method = "queryDeleteInventoryByGuids")
    List<ImpCrossBorderHead> queryDeleteInventoryByGuids(Map<String, String> paramMap);

    @SelectProvider(type = BondLogicalSQLProvider.class, method = "queryDeleteLogisticsByGuids")
    List<ImpCrossBorderHead> queryDeleteLogisticsByGuids(Map<String, String> paramMap);

    @SelectProvider(type = BondLogicalSQLProvider.class, method = "queryDeletePaymentByGuids")
    List<ImpCrossBorderHead> queryDeletePaymentByGuids(Map<String, String> paramMap);

    @Delete("DELETE FROM T_VERIFY_STATUS WHERE CB_HEAD_ID = #{headGuid}")
    void deleteLogicalVerifyStatus(String headGuid);

    @Delete("DELETE FROM T_IMP_ORDER_BODY WHERE HEAD_GUID = #{headGuid}")
    void deleteImpOrderBody(String headGuid);

    @Delete("DELETE FROM T_IMP_ORDER_HEAD WHERE GUID = #{headGuid}")
    void deleteImpOrderHead(String headGuid);

    @Delete("DELETE FROM T_IMP_INVENTORY_BODY WHERE HEAD_GUID = #{headGuid}")
    void deleteImpInventoryBody(String headGuid);

    @Delete("DELETE FROM T_IMP_INVENTORY_HEAD WHERE GUID = #{headGuid}")
    void deleteImpInventoryHead(String headGuid);

    @Delete("DELETE FROM T_IMP_LOGISTICS WHERE GUID = #{headGuid}")
    void deleteImpLogistics(String headGuid);

    @Delete("DELETE FROM T_IMP_PAYMENT WHERE GUID = #{headGuid}")
    void deleteImpPayment(String headGuid);


}
