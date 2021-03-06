package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.CrtExitInventorySQLProvider;
import com.xaeport.crossborder.data.provider.ExitInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CrtExitInventoryMapper {

    @Select("SELECT CBEC_BILL_NO FROM T_NEMS_INVT_CBEC_BILL_TYPE t WHERE t.HEAD_ETPS_INNER_INVT_NO = #{etpsInnerInvtNo}")
    List<NemsInvtCbecBillType> queryNemsInvtCbecBillList(@Param("etpsInnerInvtNo") String etpsInnerInvtNo);

    //查询核注清单申报中所对应表体信息
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryImpInventoryBodyList")
    List<ImpInventoryBody> queryImpInventoryBodyList(@Param("invtNo") String invtNo);

    //查询核注清单申报中所对应表体信息
    @SelectProvider(type = ExitInventorySQLProvider.class, method = "queryNemsInvtCbecBillTypeList")
    List<NemsInvtCbecBillType> queryNemsInvtCbecBillTypeList(@Param("etpsInnerInvtNo") String etpsInnerInvtNo);

    //查询保税清单数据
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryCrtEInventoryData")
    List<ImpInventory> queryCrtEInventoryData(Map<String, String> paramMap) throws Exception;

    //查询保税清单数据
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryCrtEInventoryList")
    List<ImpInventory> queryCrtEInventoryList(Map<String, String> paramMap) throws Exception;

    //查询保税清单数据总数
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryCrtEInventoryCount")
    Integer queryCrtEInventoryCount(Map<String, String> paramMap) throws Exception;

    //查询默认申报企业数据
    @Select("SELECT * FROM T_DCL_ETPS t WHERE t.ENT_ID = #{entId} and t.SELECT_PRIORITY = '1'")
    DclEtps queryPriorityEnt(String entId);

    //查询账册表头海关编码
    @Select("SELECT * FROM ( " +
            "SELECT t.MASTER_CUSCD FROM T_BWL_HEAD_TYPE t WHERE t.CRT_ENT_ID = #{ent_id} ORDER BY t.CRT_TIME ASC " +
            ") WHERE ROWNUM = 1")
    String queryDcl_plc_cuscd(String ent_id);

    //查询账册表头账册编码
    @Select("SELECT * FROM ( " +
            "select t.BWS_NO FROM T_BWL_HEAD_TYPE t WHERE t.CRT_ENT_ID = #{ent_id} ORDER BY t.CRT_TIME ASC " +
            ") WHERE ROWNUM = 1")
    String queryBws_no(String ent_id);

    //查询该企业海关编码
    @Select("SELECT t.PORT FROM T_ENTERPRISE t WHERE t.ID = #{ent_id}")
    String queryCustomsByEndId(String ent_id);

    //根据保税清单编码查询对应guid
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryGuidByBillNos")
    List<ImpInventory> queryGuidByBillNos(Map<String, String> paramMap);

    //根据保税清单编码查询对应guid
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryListByBillNos")
    List<ImpInventory> queryListByBillNos(Map<String, String> paramMap);

    //根据保税清单编码查询对应保税清单表头数据
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryInvtNos")
    List<ImpInventoryHead> queryInvtNos(String guids);

//    //查询保税清单数据表体数据
//    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryImpInventoryBodyList")
//    List<ImpInventoryBody> queryImpInventoryBodyList(String dataList) throws Exception;

    //根据所选数据修改对应保税清单的数据状态
    @UpdateProvider(type = CrtExitInventorySQLProvider.class, method = "updateInventoryDataByBondInvt")
    void updateInventoryDataByBondInvt(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("ebcCode") String ebcCode, @Param("userInfo") Users userInfo);

    //插入核注清单新数据（表头数据）
    @InsertProvider(type = CrtExitInventorySQLProvider.class, method = "saveBondInvtBsc")
    void saveBondInvtBsc(@Param("BondInvtBsc") LinkedHashMap<String, String> BondInvtBsc, @Param("userInfo") Users userInfo);

    //插入核注清单新数据（表体数据）
    @InsertProvider(type = CrtExitInventorySQLProvider.class, method = "saveNemsInvtCbecBillType")
    void saveNemsInvtCbecBillType(@Param("nemsInvtCbecBillType") NemsInvtCbecBillType nemsInvtCbecBillType, @Param("userInfo") Users userInfo);

    //插入核注清单新数据（表体数据）
    @InsertProvider(type = CrtExitInventorySQLProvider.class, method = "insertInvtListType")
    void insertInvtListType(@Param("invtListType") InvtListType invtListType);

    //查询统计——查询电商企业
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryEbusinessEnt")
    List<Enterprise> queryEbusinessEnt(Map<String, String> paramMap) throws Exception;


}
