package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BwlListType;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.BondinvenImportSQLProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BondinvenImportMapper {


    /*
     * 导入插入ImpInventoryHead数据
     */
    @InsertProvider(type = BondinvenImportSQLProvider.class, method = "insertImpBondInvenHead")
    boolean insertImpBondInvenHead(@Param("impInventoryHead") ImpInventoryHead impInventoryHead) throws Exception;

    /*
     * 导入插入impInventoryBody数据
     */
    @InsertProvider(type = BondinvenImportSQLProvider.class, method = "insertImpBondInvenBody")
    boolean insertImpBondInvenBody(@Param("impInventoryBody") ImpInventoryBody impInventoryBody) throws Exception;

    /*
     * 查询有无重复订单号表头信息
     */
    @SelectProvider(type = BondinvenImportSQLProvider.class, method = "isRepeatOrderNo")
    int isRepeatOrderNo(@Param("impInventoryHead") ImpInventoryHead impInventoryHead) throws Exception;

    @SelectProvider(type = BondinvenImportSQLProvider.class, method = "checkStockSurplus")
    BwlListType checkStockSurplus(@Param("user") Users user, @Param("item_record_no") String item_record_no, @Param("emsNo") String emsNo);

    @UpdateProvider(type = BondinvenImportSQLProvider.class, method = "setPrevdRedcQty")
    void setPrevdRedcQty(@Param("qtySum") double qtySum, @Param("item_record_no") String item_record_no, @Param("emsNo") String emsNo);

}