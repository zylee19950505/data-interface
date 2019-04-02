package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.DclEtps;
import com.xaeport.crossborder.data.provider.DeclareEntSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeclareEntMapper {

    @SelectProvider(type = DeclareEntSQLProvider.class, method = "queryDclEtpsList")
    List<DclEtps> queryDclEtpsList(Map<String, String> map) throws Exception;

    @DeleteProvider(type = DeclareEntSQLProvider.class, method = "deleteDcletps")
    void deleteDcletps(String id) throws Exception;

    @InsertProvider(type = DeclareEntSQLProvider.class, method = "createDcletps")
    void createDcletps(@Param("dclEtps") DclEtps dclEtps) throws Exception;

}
