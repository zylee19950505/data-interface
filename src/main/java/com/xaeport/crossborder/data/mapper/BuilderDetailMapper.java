package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.BondinvenImportSQLProvider;
import com.xaeport.crossborder.data.provider.BuilderDetailSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BuilderDetailMapper {


    @SelectProvider(type = BuilderDetailSQLProvider.class, method = "findBuilderOrderNo")
    List<String> findBuilderOrderNo(Map<String, String> paramMap);

    @SelectProvider(type = BuilderDetailSQLProvider.class, method = "queryOrderHead")
    ImpOrderHead queryOrderHead(@Param("orderNo") String orderNo);

    @SelectProvider(type = BuilderDetailSQLProvider.class, method = "queryOrderList")
    List<ImpOrderBody> queryOrderList(@Param("orderNo") String orderNo);

    @SelectProvider(type = BuilderDetailSQLProvider.class, method = "queryLogistics")
    ImpLogistics queryLogistics(@Param("orderNo") String orderNo);

    @SelectProvider(type = BuilderDetailSQLProvider.class, method = "queryBwsNoByEntId")
    String queryBwsNoByEntId(@Param("ent_code") String ent_code, @Param("ent_name") String ent_name);

    @Select("SELECT * FROM T_ENTERPRISE t where t.ID = #{entId}")
    Enterprise getEnterpriseDetail(@Param("entId") String entId);

    @Select("select * from T_BWL_LIST_TYPE t where t.BWS_NO = #{bws_no} and t.GDS_MTNO = #{item_no} and t.GDS_MTNO like #{brevity_code}||'%'")
    BwlListType queryBwsListByEntBwsNo(@Param("bws_no") String bws_no, @Param("item_no") String item_no, @Param("brevity_code") String brevity_code);

    @InsertProvider(type = BuilderDetailSQLProvider.class, method = "insertImpInventoryBody")
    void insertImpInventoryBody(@Param("impInventoryBody") ImpInventoryBody impInventoryBody);

    @InsertProvider(type = BuilderDetailSQLProvider.class, method = "insertImpInventoryHead")
    void insertImpInventoryHead(@Param("impInventoryHead") ImpInventoryHead impInventoryHead);

    @UpdateProvider(type = BuilderDetailSQLProvider.class, method = "updateBuilderCacheByOrderNo")
    void updateBuilderCacheByOrderNo(@Param("orderNo") String orderNo, @Param("dataStatus") String dataStatus);

    @SelectProvider(type = BondinvenImportSQLProvider.class, method = "queryBwlHeadType")
    String queryBwlHeadType(@Param("id") String id);

    @SelectProvider(type = BondinvenImportSQLProvider.class, method = "queryAreaenterprise")
    Enterprise queryAreaenterprise(@Param("area_code") String area_code);
}
