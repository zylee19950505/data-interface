package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BaseTransfer;
import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpLogisticsData;
import com.xaeport.crossborder.data.entity.ImpLogisticsStatus;
import com.xaeport.crossborder.data.provider.WaybillDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface WaybillDeclareMapper {

    @SelectProvider(type = WaybillDeclareSQLProvider.class,method = "queryWaybillDeclareDataList")
    List<ImpLogisticsData> queryWaybillDeclareDataList(Map<String, String> paramMap) throws Exception;


    @SelectProvider(type = WaybillDeclareSQLProvider.class,method = "queryWaybillDeclareCount")
    Integer queryWaybillDeclareCount(Map<String, String> paramMap) throws Exception;

    /*
     * 更新提交海关的数据，变为运单申报中
     */
    @UpdateProvider(type = WaybillDeclareSQLProvider.class, method = "updateSubmitWaybill")
    void updateSubmitWaybill(Map<String, String> paramMap) throws Exception;
    /*
     * 更新提交海关的数据，变为运单状态申报中
     */
    @UpdateProvider(type = WaybillDeclareSQLProvider.class, method = "updateSubmitWaybillToStatus")
    void updateSubmitWaybillToStatus(Map<String, String> paramMap) throws Exception;
    /*
     * 生产运单报文数据查询
     */
    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "findWaitGenerated")
    List<ImpLogistics> findWaitGenerated(Map<String, String> paramMap) throws Exception;
    /*
     * 修改运单申报状态
     */
    @UpdateProvider(type = WaybillDeclareSQLProvider.class, method = "updateImpLogisticsStatus")
    void updateImpLogisticsStatus(@Param("guid") String guid, @Param("CBDS41") String CBDS41) throws Exception;
    /*
     * 生产运单状态报文数据查询
     */
    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "findWaitGeneratedStatus")
    List<ImpLogisticsStatus> findWaitGeneratedStatus(Map<String, String> paramMap) throws Exception;
    /*
     * 修改运单状态申报状态
     */
    @UpdateProvider(type = WaybillDeclareSQLProvider.class, method = "updateToLogisticsStatus")
    void updateToLogisticsStatus(@Param("guid") String guid, @Param("CBDS51") String CBDS51) throws Exception;

    //修改运单申报状态
    @UpdateProvider(type = WaybillDeclareSQLProvider.class, method = "updateToLogistics")
    void updateToLogistics(@Param("logisticsNo") String logisticsNo, @Param("CBDS51") String CBDS51)throws Exception;

    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "queryDateStatus")
    int queryDateStatus(@Param("logisticsNo") String logisticsNo);

    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "queryStaDateStatus")
	int queryStaDateStatus(@Param("logisticsNo") String logisticsNo);

    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "queryCompany")
    BaseTransfer queryCompany(@Param("ent_id") String ent_id);

}
