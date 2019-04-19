package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BwlListType;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
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

    //查询检验库存余量是否大于excel导入数量
    @SelectProvider(type = BondinvenImportSQLProvider.class, method = "checkStockSurplus")
    BwlListType checkStockSurplus(@Param("entCustomsCode") String entCustomsCode, @Param("item_no") String item_no, @Param("emsNo") String emsNo);

    //确认保税清单库存无误后，设置账册表体预减数量
    @UpdateProvider(type = BondinvenImportSQLProvider.class, method = "setPrevdRedcQty")
    void setPrevdRedcQty(@Param("qtySum") double qtySum, @Param("item_no") String item_no, @Param("emsNo") String emsNo, @Param("entCustomsCode") String entCustomsCode);

    @SelectProvider(type = BondinvenImportSQLProvider.class, method = "queryBwlHeadType")
    String queryBwlHeadType(@Param("id") String id);

    @SelectProvider(type = BondinvenImportSQLProvider.class, method = "queryAreaenterprise")
    Enterprise queryAreaenterprise(@Param("area_code") String area_code);
}
