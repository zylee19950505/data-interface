package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.provider.CrtEnterEmptySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrtEnterEmptyMapper {


    @SelectProvider(type = CrtEnterEmptySQLProvider.class,method = "queryCrtEnterEmptyList")
    List<PassPortHead> queryCrtEnterEmptyList(Map<String, String> paramMap);

    @SelectProvider(type = CrtEnterEmptySQLProvider.class,method = "queryCrtEnterEmptyCount")
    Integer queryCrtEnterEmptyCount(Map<String, String> paramMap);

    @InsertProvider(type = CrtEnterEmptySQLProvider.class,method = "saveEntryEmptyInfo")
    void saveEntryEmptyInfo(@Param("passPortHead") PassPortHead passPortHead);

    @Delete("DELETE from T_PASS_PORT_HEAD t where t.id = #{id}")
    void deleteEnterEmpty(@Param("id") String id);

    @Select("select t.* from T_PASS_PORT_HEAD t where t.ETPS_PREENT_NO = #{etps_preent_no}")
    PassPortHead queryEnterEmptyDetails(Map<String, String> paramMap);

    @UpdateProvider(type = CrtEnterEmptySQLProvider.class,method = "updateEntryEmptyInfo")
    void updateEntryEmptyInfo(@Param("passPortHead") PassPortHead passPortHead);

    @UpdateProvider(type = CrtEnterEmptySQLProvider.class,method = "submitEmptyCustom")
    boolean submitEmptyCustom(Map<String, String> paramMap);
}
