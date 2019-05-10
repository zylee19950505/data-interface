package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BaseTransfer;
import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.LogInvCombine;
import com.xaeport.crossborder.data.entity.LogisticsSum;
import com.xaeport.crossborder.data.provider.WaybillDeclareSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface WaybillDeclareMapper {

    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "queryWaybillDeclareDataList")
    List<LogisticsSum> queryWaybillDeclareDataList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "queryWaybillDeclareCount")
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
    List<ImpLogistics> findWaitGeneratedStatus(Map<String, String> paramMap) throws Exception;

    /*
     * 修改运单状态申报状态
     */
    @UpdateProvider(type = WaybillDeclareSQLProvider.class, method = "updateToLogisticsStatus")
    void updateToLogisticsStatus(@Param("guid") String guid, @Param("CBDS51") String CBDS51) throws Exception;

    //修改运单申报状态
    @UpdateProvider(type = WaybillDeclareSQLProvider.class, method = "updateToLogistics")
    void updateToLogistics(@Param("logisticsNo") String logisticsNo, @Param("CBDS51") String CBDS51) throws Exception;

    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "queryDateStatus")
    int queryDateStatus(@Param("billNo") String billNo);

    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "queryStaDateStatus")
    int queryStaDateStatus(@Param("billNo") String billNo);

    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "queryCompany")
    BaseTransfer queryCompany(@Param("ent_id") String ent_id);

    //查询运单清单表
    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "queryLogInvCombine")
    LogInvCombine queryLogInvCombine(@Param("billNo") String billNo, @Param("orderNo") String orderNo, @Param("logisticsNo") String logisticsNo);

    //设置运单清单表数据值
    @UpdateProvider(type = WaybillDeclareSQLProvider.class, method = "updateLogInvCombine")
    void updateLogInvCombine(@Param("billNo") String billNo, @Param("orderNo") String orderNo, @Param("logisticsNo") String logisticsNo, @Param("mark") String mark);

    //插入整合表
    @InsertProvider(type = WaybillDeclareSQLProvider.class, method = "insertLogInvCombine")
    void insertLogInvCombine(@Param("logInvCombine") LogInvCombine logInvCombine);

}
