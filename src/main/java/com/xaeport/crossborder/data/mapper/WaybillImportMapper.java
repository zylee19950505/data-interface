package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.OrderNo;
import com.xaeport.crossborder.data.provider.WaybillImportSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WaybillImportMapper {
    /*
     * 导入插入impLogistics表数据
     */
    @InsertProvider(type= WaybillImportSQLProvider.class,method = "insertImpLogistics")
    boolean insertImpLogistics(@Param("impLogistics") ImpLogistics impLogistics) throws Exception;

    /*
     * 查询有无重复物流运单编号表信息
     */
    @SelectProvider(type = WaybillImportSQLProvider.class,method = "isRepeatLogisticsNo")
    int isRepeatLogisticsNo(@Param("impLogistics") ImpLogistics impLogistics) throws Exception;

    //查询订单号
    @SelectProvider(type = WaybillImportSQLProvider.class,method = "queryOrderNoList")
    List<OrderNo> queryOrderNoList() throws Exception;

    @SelectProvider(type = WaybillImportSQLProvider.class,method = "queryOrderNoData")
    ImpOrderHead queryOrderNoData(@Param("orderNo") OrderNo orderNo) throws Exception;

    @SelectProvider(type = WaybillImportSQLProvider.class,method = "queryLogisticsNo")
    String queryLogisticsNo(String type) throws Exception;

    @Update("DELETE T_ORDER_NO WHERE ORDER_NO = #{orderNo}")
    void deleteOrderNo(String orderNo) throws Exception;

    @Update("UPDATE T_LOGISTICS_NO SET USED = '1' WHERE LOGISTICS_NO = #{logisticsNo}")
    void updateLogisticsNo(String logisticsNo) throws Exception;

}
