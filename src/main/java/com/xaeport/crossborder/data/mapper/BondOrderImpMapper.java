package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BwlListType;
import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.OrderNo;
import com.xaeport.crossborder.data.provider.BondOrderImpSQLProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BondOrderImpMapper {

    /*
     * 导入插入impOrderHead表头数据
     */
    @InsertProvider(type = BondOrderImpSQLProvider.class, method = "insertImpOrderHead")
    boolean insertImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead) throws Exception;

    /*
     * 导入插入insertImpOrderGoodsList表体数据
     */
    @InsertProvider(type = BondOrderImpSQLProvider.class, method = "insertImpOrderBody")
    boolean insertImpOrderBody(@Param("impOrderBody") ImpOrderBody impOrderBody) throws Exception;

    /*
     * 查询有无重复订单号表头信息
     */
    @SelectProvider(type = BondOrderImpSQLProvider.class, method = "isRepeatOrderNo")
    int isRepeatOrderNo(@Param("impOrderHead") ImpOrderHead impOrderHead) throws Exception;

    /*
     * 根据订单号查询表头Id 码
     */
    @SelectProvider(type = BondOrderImpSQLProvider.class, method = "queryHeadGuidByOrderNo")
    String queryHeadGuidByOrderNo(String listOrder_no) throws Exception;

    /*
     * 根据订单号查询表体最大商品序号
     */
    @SelectProvider(type = BondOrderImpSQLProvider.class, method = "queryMaxG_num")
    Integer queryMaxG_num(String listOrder_no) throws Exception;

    //插入订单表数据
    @InsertProvider(type = BondOrderImpSQLProvider.class, method = "insertOrderNo")
    void insertOrderNo(@Param("orderNo") OrderNo orderNo);

    //根据企业简码查询物流企业是否存在
    @SelectProvider(type = BondOrderImpSQLProvider.class, method = "queryEntInfoByBrevityCode")
    Integer queryEntInfoByBrevityCode(String brevityCode);

    //根据料号和简码查询账册表体数据
    @Select("SELECT GDS_SEQNO,NATCD FROM T_BWL_LIST_TYPE t WHERE t.GDS_MTNO = #{gds_mtno} AND t.GDS_MTNO like #{brevity_code}||'%'")
    BwlListType queryBwlListTypeByItemNo(@Param("gds_mtno") String gds_mtno, @Param("brevity_code") String brevity_code);


}
