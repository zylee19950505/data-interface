package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.provider.CrtExitInventorySQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CrtExitInventoryMapper {

    //查询清单数据
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryCrtEInventoryList")
    List<ImpInventory> queryCrtEInventoryList(Map<String, String> paramMap) throws Exception;

    //查询清单数据总数
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryCrtEInventoryCount")
    Integer queryCrtEInventoryCount(Map<String, String> paramMap) throws Exception;

    @Select("SELECT t.BWS_NO FROM T_BWL_HEAD_TYPE t WHERE t.CRT_ENT_ID = #{ent_id}")
    String queryBws_no(String ent_id);

    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryGuids")
    List<String> queryGuids(String invtNos);

    //查询清单数据总数
    @SelectProvider(type = CrtExitInventorySQLProvider.class, method = "queryImpInventoryBodyList")
    List<ImpInventoryBody> queryImpInventoryBodyList(String dataList) throws Exception;

    @InsertProvider(type = CrtExitInventorySQLProvider.class, method = "saveBondInvtBsc")
    void saveBondInvtBsc(LinkedHashMap<String, String> BondInvtBsc);

    @InsertProvider(type = CrtExitInventorySQLProvider.class, method = "saveBondInvtDt")
    void saveBondInvtDt(LinkedHashMap<String, String> BondInvtDt);


}
