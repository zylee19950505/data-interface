package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.provider.BondinvenDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BondinvenDeclareMapper {


    //查询保税清单数据
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "queryBondInvenDeclareList")
    List<ImpInventory> queryBondInvenDeclareList(Map<String, String> paramMap) throws Exception;

    //查询保税清单数据总数
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "queryBondInvenDeclareCount")
    Integer queryBondInvenDeclareCount(Map<String, String> paramMap) throws Exception;

    //提交海关，更新保税清单数据为申报中
    @UpdateProvider(type = BondinvenDeclareSQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap) throws Exception;

    //查询保税清单表头详情
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "queryImpBondInvenHead")
    ImpInventoryHead queryImpBondInvenHead(Map<String, String> paramMap);

    //查询保税清单表体详情
    @SelectProvider(type = BondinvenDeclareSQLProvider.class, method = "queryImpBondInvenBodies")
    List<ImpInventoryBody> queryImpBondInvenBodies(Map<String, String> paramMap);

    //修改保税清单表头信息（保税清单申报）
    @UpdateProvider(type = BondinvenDeclareSQLProvider.class, method = "updateImpBondInvenHead")
    void updateImpBondInvenHead(LinkedHashMap<String, String> entryHead);

    //修改清单表头数据根据表体（保税清单申报）
    @UpdateProvider(type = BondinvenDeclareSQLProvider.class, method = "updateImpBondInvenHeadByList")
    void updateImpBondInvenHeadByList(LinkedHashMap<String, String> entryHead);

    //修改保税清单表体信息（保税清单申报）
    @UpdateProvider(type = BondinvenDeclareSQLProvider.class, method = "updateImpBondInvenBodies")
    void updateImpBondInvenBodies(LinkedHashMap<String, String> entryList);


}
