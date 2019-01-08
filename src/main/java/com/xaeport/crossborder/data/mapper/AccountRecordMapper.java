package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BwlHeadType;
import com.xaeport.crossborder.data.provider.AccountRecordSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountRecordMapper {

    //查询该企业下所有账册数据
    @SelectProvider(type = AccountRecordSQLProvider.class, method = "queryAllAccountsInfo")
    List<BwlHeadType> queryAllAccountsInfo(Map<String, String> paramMap) throws Exception;

    //加载账册信息
    @SelectProvider(type = AccountRecordSQLProvider.class, method = "getAccountById")
    BwlHeadType getAccountById(String id);

    //新建账册信息
    @InsertProvider(type = AccountRecordSQLProvider.class, method = "crtAccountInfo")
    boolean crtAccountInfo(@Param("bwlHeadType") BwlHeadType bwlHeadType) throws Exception;

    //查询账册信息个数
    @Select("SELECT count(1) FROM T_BWL_HEAD_TYPE t WHERE t.id = #{id}")
    Integer getAccountCount(String id);

    //修改账册信息
    @UpdateProvider(type = AccountRecordSQLProvider.class, method = "accountUpdate")
    boolean accountUpdate(@Param("bwlHeadType") BwlHeadType bwlHeadType) throws Exception;

    //查询企业所属账册编码参数表
    @SelectProvider(type = AccountRecordSQLProvider.class, method = "getEmsNos")
    List<BwlHeadType> getEmsNos(Map<String, String> map) throws Exception;

}
