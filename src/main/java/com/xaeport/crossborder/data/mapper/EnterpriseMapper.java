package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.provider.BondinvenImportSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface EnterpriseMapper {

    @Select("SELECT " +
            "t.*, " +
            "(SELECT CUSTOMS_NAME FROM T_CUSTOMS WHERE CUSTOMS_CODE = t.PORT) portCnName " +
            "FROM T_ENTERPRISE t where t.ID = #{entId}")
    Enterprise getEnterpriseDetail(@Param("entId") String entId);


    @SelectProvider(type = BondinvenImportSQLProvider.class, method = "queryAreaenterprise")
    Enterprise queryAreaenterprise(@Param("area_code") String area_code);
}
