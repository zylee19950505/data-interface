package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.provider.PaymentDeclareSQLProvider;
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
    List<ImpLogistics> queryWaybillDeclareDataList(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = WaybillDeclareSQLProvider.class,method = "queryWaybillDeclareCount")
    Integer queryWaybillDeclareCount(Map<String, String> paramMap) throws Exception;

    /*
     * 更新提交海关的数据，变为运单申报中
     */
    @UpdateProvider(type = WaybillDeclareSQLProvider.class, method = "updateSubmitWaybill")
    void updateSubmitWaybill(Map<String, String> paramMap) throws Exception;
    /*
     * 生产支付单报文数据查询
     */
    @SelectProvider(type = WaybillDeclareSQLProvider.class, method = "findWaitGenerated")
    List<ImpLogistics> findWaitGenerated(Map<String, String> paramMap) throws Exception;
    /*
     * 修改支付单申报状态
     */
    @UpdateProvider(type = WaybillDeclareSQLProvider.class, method = "updateImpLogisticsStatus")
    void updateImpLogisticsStatus(@Param("guid") String guid, @Param("CBDS41") String CBDS41) throws Exception;


}
