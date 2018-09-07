package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.provider.OrderImportSQLProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderImportMapper {

    /*
     * 导入插入impOrderHead表头数据
     */
    @InsertProvider(type= OrderImportSQLProvider.class,method = "insertImpOrderHead")
    boolean insertImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead) throws Exception;

    /*
     * 导入插入insertImpOrderGoodsList表体数据
     */
    @InsertProvider(type= OrderImportSQLProvider.class,method = "insertImpOrderBody")
    boolean insertImpOrderBody(@Param("impOrderBody") ImpOrderBody impOrderBody) throws Exception;

    /*
     * 查询有无重复订单号表头信息
     */
    @SelectProvider(type = OrderImportSQLProvider.class,method = "isRepeatOrderNo")
    int isRepeatOrderNo(@Param("impOrderHead") ImpOrderHead impOrderHead) throws Exception;

    /*
     * 根据订单号查询表头Id 码
     */
    @SelectProvider(type = OrderImportSQLProvider.class,method = "queryHeadGuidByOrderNo")
    String queryHeadGuidByOrderNo(String listOrder_no) throws Exception;

    /*
     * 根据订单号查询表体最大商品序号
     */
    @SelectProvider(type = OrderImportSQLProvider.class,method = "queryMaxG_num")
    Integer queryMaxG_num(String listOrder_no) throws Exception;


//    @Select("select count(1) count from T_IMP_ORDER_HEAD  t where t.ORDER_NO = #{orderNo}")
//	int testQueryOrderNo(@Param("orderNo") String orderNo);
//
//    @Delete("delete from T_IMP_ORDER_HEAD t where t.ORDER_NO = #{orderNo}")
//    void testDeleteOrderNo(@Param("orderNo") String orderNo);
//
//    @Insert("insert into T_IMP_ORDER_HEAD t (t.GUID,t.ORDER_NO) values(#{impOrderHead.guid},#{impOrderHead.order_No})")
//    void testInsertOrderNo(@Param("impOrderHead") ImpOrderHead impOrderHead);
}
