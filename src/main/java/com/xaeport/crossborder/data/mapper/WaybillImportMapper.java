package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.provider.WaybillImportSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

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

}
