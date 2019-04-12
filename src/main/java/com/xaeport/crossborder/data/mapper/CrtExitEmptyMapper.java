package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.provider.CrtExitEmptySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrtExitEmptyMapper {

    @SelectProvider(type = CrtExitEmptySQLProvider.class, method = "querExitEmptyPassportList")
    List<PassPortHead> querExitEmptyPassportList(Map<String, String> paramMap);

    @SelectProvider(type = CrtExitEmptySQLProvider.class, method = "queryExitEmptyPassportCount")
    Integer queryExitEmptyPassportCount(Map<String, String> paramMap);

    @InsertProvider(type = CrtExitEmptySQLProvider.class, method = "saveExitEmptyInfo")
    void saveExitEmptyInfo(@Param("passPortHead") PassPortHead passPortHead);

    @DeleteProvider(type = CrtExitEmptySQLProvider.class, method = "deleteExitEmpty")
    void deleteExitEmpty(@Param("submitKeys") String submitKeys);

    //更新修改出区核放单数据为申报中状态（提交海关）
    @UpdateProvider(type = CrtExitEmptySQLProvider.class, method = "updateSubmitCustom")
    void updateSubmitCustom(Map<String, String> paramMap) throws Exception;
}
