package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.SysLog;
import com.xaeport.crossborder.data.provider.EntManageProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */
@Mapper
public interface EntManageMapper {

    //企业管理——企业所有信息数据
    @SelectProvider(type = EntManageProvider.class, method = "queryAllEntInfo")
    List<Enterprise> queryAllEntInfo(Map<String, String> paramMap);

    @Select("SELECT COUNT(0) FROM T_ENTERPRISE t where t.ID = #{ID} ")
    int getEnterpriseCount(@Param("ID") String ID);

    @UpdateProvider(type = EntManageProvider.class, method = "updateEnterprise")
    boolean updateEnterprise(Enterprise enterprise) throws Exception;

    @InsertProvider(type = EntManageProvider.class, method = "createEntInfo")
    boolean createEntInfo(Enterprise enterprise) throws Exception;

    @Select("select t.AGENT_CODE as value,t.AGENT_NAME as name from T_AGENT_CODE t")
    List<Map<String,String>> getAgentCode();

    @Select("select t.CUSTOMS_CODE as value,t.CUSTOMS_NAME as name from T_CUSTOMS t")
    List<Map<String,String>> getCustomsCode();

    @Select("select t.AGENT_CODE as value,t.AGENT_NAME as name from T_AGENT_TYPE t")
    List<Map<String,String>> getAgentType();

    @Select("select t.AGENT_CODE as value,t.AGENT_NAME as name from T_AGENT_NATURE t")
    List<Map<String,String>> getAgentNature();

    @Select("SELECT * FROM T_ENTERPRISE t where t.ID = #{entId}")
    Enterprise getEnterpriseDetail(String entId);

























}
