package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BwlHeadType;
import com.xaeport.crossborder.data.provider.AccountRecordSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountRecordMapper {

//    @InsertProvider(type = AccountRecordSQLProvider.class,method = "crtAccountRecord")
//    boolean crtAccountRecord(@Param("bwlHeadType") BwlHeadType bwlHeadType) throws Exception;

    @SelectProvider(type = AccountRecordSQLProvider.class, method = "queryAllAccountsInfo")
    List<BwlHeadType> queryAllAccountsInfo(Map<String, String> paramMap) throws Exception;

    @SelectProvider(type = AccountRecordSQLProvider.class, method = "getAccountById")
    BwlHeadType getAccountById(String id);

    @InsertProvider(type = AccountRecordSQLProvider.class, method = "crtAccountInfo")
    boolean crtAccountInfo(@Param("bwlHeadType") BwlHeadType bwlHeadType) throws Exception;

    @Select("SELECT count(1) FROM T_BWL_HEAD_TYPE t WHERE t.id = #{id}")
    Integer getAccountCount(String id);

    @UpdateProvider(type = AccountRecordSQLProvider.class, method = "accountUpdate")
    boolean accountUpdate(@Param("bwlHeadType") BwlHeadType bwlHeadType) throws Exception;

    @SelectProvider(type = AccountRecordSQLProvider.class, method = "getEmsNos")
    List<BwlHeadType> getEmsNos(Map<String, String> map) throws Exception;

}
