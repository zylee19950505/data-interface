package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.provider.OrderImportSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

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


}
