package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.provider.StockMessageSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockMessageMapper {

    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpOrderHead")
    void insertImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead);

    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpOrderBody")
    void insertImpOrderBody(@Param("impOrderBody") ImpOrderBody impOrderBody);

    @InsertProvider(type = StockMessageSQLProvider.class, method = "insertImpPayment")
    void insertImpPayment(@Param("impPayment") ImpPayment impPayment);

}
