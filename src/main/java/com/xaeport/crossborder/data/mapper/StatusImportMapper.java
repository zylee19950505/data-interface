package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpLogisticsStatus;
import com.xaeport.crossborder.data.provider.StatusImportSQLProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StatusImportMapper {

    /*
     * 导入插入impLogisticsStatusStatus表数据
     */
    @UpdateProvider(type= StatusImportSQLProvider.class,method = "insertImpLogisticsStatus")
    boolean insertImpLogisticsStatus(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus) throws Exception;

    /*
     * 查询有无重复物流运单编号表信息
     */
    @SelectProvider(type = StatusImportSQLProvider.class,method = "isRepeatLogisticsStatusNo")
    int isRepeatLogisticsStatusNo(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus) throws Exception;

    /*
    * 查询导入的运单状态是否有对应的运单
    * */
    @SelectProvider(type = StatusImportSQLProvider.class,method = "isEmptyLogisticsNo")
	int isEmptyLogisticsNo(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus);

    /*
    * //判断运单是否申报成功,是否有回执
    * */
    @SelectProvider(type = StatusImportSQLProvider.class,method = "getLogisticsSuccess")
    int getLogisticsSuccess(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus);

    /*
    * 有回执,申报成功后把运单状态改为CBDS5
    * */
    @UpdateProvider(type = StatusImportSQLProvider.class,method = "updateLogisticsStatus")
    void updateLogisticsStatus(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus);

    @UpdateProvider(type = StatusImportSQLProvider.class,method = "updateLogistics")
    void updateLogistics(@Param("impLogisticsStatus") ImpLogistics impLogisticsStatus);
}
