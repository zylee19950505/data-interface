package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.provider.DockingSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface DockingMapper {

    @InsertProvider(type = DockingSQLProvider.class, method = "insertBondInvtBsc")
    void insertBondInvtBsc(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    @InsertProvider(type = DockingSQLProvider.class, method = "insertBondInvtDt")
    void insertBondInvtDt(@Param("bondInvtDt") BondInvtDt bondInvtDt);

    @InsertProvider(type = DockingSQLProvider.class, method = "insertImpOrderHead")
    void insertImpOrderHead(@Param("impOrderHead") ImpOrderHead impOrderHead);

    @InsertProvider(type = DockingSQLProvider.class, method = "insertImpOrderBody")
    void insertImpOrderBody(@Param("impOrderBody") ImpOrderBody impOrderBody);

    @SelectProvider(type = DockingSQLProvider.class, method = "queryEntInfoById")
    Enterprise queryEntInfoById(String id);

    @SelectProvider(type = DockingSQLProvider.class, method = "findRepeatOrder")
    int findRepeatOrder(@Param("orderId") String orderId, @Param("orderNo") String orderNo);

    @SelectProvider(type = DockingSQLProvider.class, method = "findRepeatBondInvt")
    int findRepeatBondInvt(@Param("etpsInnerInvtNo") String etpsInnerInvtNo);

}
