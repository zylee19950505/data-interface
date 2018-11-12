package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.DetailDeclareSQLProvider;
import com.xaeport.crossborder.data.provider.OrderDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface DetailDeclareMapper {

    @SelectProvider(type = DetailDeclareSQLProvider.class, method = "queryInventoryDeclareList")
    List<ImpInventory> queryInventoryDeclareList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = DetailDeclareSQLProvider.class, method = "queryInventoryDeclareCount")
    Integer queryInventoryDeclareCount(Map<String, String> paramMap) throws Exception;

    /*
     * 更新提交海关的数据，变为清单申报中
     */
    @UpdateProvider(type = DetailDeclareSQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap) throws Exception;

    /*
     * 根据状态查找订单
     * */
    @SelectProvider(type = DetailDeclareSQLProvider.class, method = "findWaitGenerated")
    List<ImpInventoryHead> findWaitGenerated(Map<String, String> paramMap);

    /*
    * 根据状态查找订单
    * */
    @SelectProvider(type = DetailDeclareSQLProvider.class, method = "findWaitGeneratedByXml")
    List<ImpInventoryHead> findWaitGeneratedByXml(Map<String, String> paramMap);

    /*
     * 根据状态查找订单
     * */
    @SelectProvider(type = DetailDeclareSQLProvider.class, method = "findWaitGeneratedByXmlCount")
    double findWaitGeneratedByXmlCount(Map<String, String> paramMap);

    /*
     * 根据id查找标题信息
     * */
    @SelectProvider(type = DetailDeclareSQLProvider.class, method = "querydetailDeclareListByGuid")
    List<ImpInventoryBody> querydetailDeclareListByGuid(@Param("headGuid") String headGuid);

    /*
     * 修改申报状态
     * */
    @UpdateProvider(type = DetailDeclareSQLProvider.class, method = "updateEntryHeadDetailStatus")
    void updateEntryHeadDetailStatus(@Param("headGuid") String headGuid, @Param("QDYSB") String QDYSB);

    @SelectProvider(type = DetailDeclareSQLProvider.class, method = "queryEntId")
    String queryEntId(@Param("crt_id") String crt_id);

    @SelectProvider(type = DetailDeclareSQLProvider.class, method = "queryCompany")
    BaseTransfer queryCompany(@Param("ent_id") String ent_id);
}
