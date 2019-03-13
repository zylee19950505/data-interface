package com.xaeport.crossborder.data.mapper;

/*
 * 订单申报
 */

import com.xaeport.crossborder.data.entity.BaseTransfer;
import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.OrderSum;
import com.xaeport.crossborder.data.provider.BondOrderDeclSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface BondOrderDeclMapper {

    //查询订单申报数据
    @SelectProvider(type = BondOrderDeclSQLProvider.class, method = "queryOrderDeclareList")
    List<OrderSum> queryOrderDeclareList(Map<String, String> paramMap);

    /*
     * 查询订单申报数据总数
     */
    @SelectProvider(type = BondOrderDeclSQLProvider.class, method = "queryOrderDeclareCount")
    Integer queryOrderDeclareCount(Map<String, Object> paramMap);

    @UpdateProvider(type = BondOrderDeclSQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap);

    /*
    * 根据状态查找订单
    * */
    @SelectProvider(type = BondOrderDeclSQLProvider.class, method = "findWaitGenerated")
    List<ImpOrderHead> findWaitGenerated(Map<String, String> paramMap);

    /*
     * 根据状态查找订单
     * */
    @SelectProvider(type = BondOrderDeclSQLProvider.class, method = "findWaitGeneratedByXml")
    List<ImpOrderHead> findWaitGeneratedByXml(Map<String, String> paramMap);

    /*
     * 根据状态查找订单
     * */
    @SelectProvider(type = BondOrderDeclSQLProvider.class, method = "findWaitGeneratedByXmlCount")
    double findWaitGeneratedByXmlCount(Map<String, String> paramMap);

    /*
    * 根据id查找标题信息
    * */
    @SelectProvider(type = BondOrderDeclSQLProvider.class, method = "queryOrderListByGuid")
    List<ImpOrderBody> queryOrderListByGuid(@Param("headGuid") String headGuid);


    /*
    * 修改申报状态
    * */
    @UpdateProvider(type = BondOrderDeclSQLProvider.class, method = "updateEntryHeadOrderStatus")
    void updateEntryHeadOrderStatus(@Param("headGuid") String headGuid, @Param("ddysb") String ddysb);

    @SelectProvider(type = BondOrderDeclSQLProvider.class, method = "queryEntId")
    String queryEntId(@Param("crt_id") String crt_id);

    @SelectProvider(type = BondOrderDeclSQLProvider.class, method = "queryCompany")
    BaseTransfer queryCompany(@Param("ent_id") String ent_id);


}
