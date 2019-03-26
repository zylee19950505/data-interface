package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.provider.BondinvenbudDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface BondinvenbudDeclareMapper {

    @SelectProvider(type = BondinvenbudDeclareSQLProvider.class, method = "queryBondinvenbudDeclareList")
    List<ImpInventory> queryBondinvenbudDeclareList(Map<String, String> paramMap);

    @UpdateProvider(type = BondinvenbudDeclareSQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap);
}
