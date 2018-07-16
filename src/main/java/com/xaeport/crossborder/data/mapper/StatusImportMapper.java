package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpLogisticsStatus;
import com.xaeport.crossborder.data.provider.StatusImportSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface StatusImportMapper {

    /*
     * 导入插入impLogisticsStatusStatus表数据
     */
    @InsertProvider(type= StatusImportSQLProvider.class,method = "insertImpLogisticsStatus")
    boolean insertImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus) throws Exception;

    /*
     * 查询有无重复物流运单编号表信息
     */
    @SelectProvider(type = StatusImportSQLProvider.class,method = "isRepeatLogisticsStatusNo")
    int isRepeatLogisticsStatusNo(@Param("impLogisticsStatus") ImpLogisticsStatus impLogisticsStatus) throws Exception;

}
