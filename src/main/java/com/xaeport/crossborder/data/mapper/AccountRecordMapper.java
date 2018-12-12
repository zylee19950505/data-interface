package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BwlHeadType;
import com.xaeport.crossborder.data.provider.AccountRecordSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRecordMapper {

    @InsertProvider(type = AccountRecordSQLProvider.class,method = "crtAccountRecord")
    boolean crtAccountRecord(BwlHeadType bwlHeadType)throws Exception;

}
