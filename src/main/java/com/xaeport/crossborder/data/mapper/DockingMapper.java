package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.provider.DockingSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

}
