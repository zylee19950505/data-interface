package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpOrderGoodsList;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.provider.OrderImportSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderImportMapper {


    @InsertProvider(type= OrderImportSQLProvider.class,method = "insertImpOrderHead")
    boolean insertImpOrderHead(ImpOrderHead impOrderHead) throws Exception;

    @InsertProvider(type= OrderImportSQLProvider.class,method = "insertImpOrderGoodsList")
    boolean insertImpOrderGoodsList(ImpOrderGoodsList impOrderGoodsList) throws Exception;



}
