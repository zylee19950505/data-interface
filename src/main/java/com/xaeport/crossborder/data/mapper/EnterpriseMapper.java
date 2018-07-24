package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.Enterprise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EnterpriseMapper {

    @Select("SELECT * FROM T_ENTERPRISE t where t.ID = #{entId}")
    Enterprise getEnterpriseDetail(@Param("entId") String entId);

}
