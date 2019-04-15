package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.provider.CrtEnterEmptySQLProvider;
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

    @Select("select t.* from T_PASS_PORT_HEAD t where t.ETPS_PREENT_NO = #{etps_preent_no}")
    PassPortHead queryPassportDetail(Map<String, String> paramMap);

    @UpdateProvider(type = CrtExitEmptySQLProvider.class, method = "updatePassport")
    void updatePassport(@Param("passPortHead") PassPortHead passPortHead);
}
