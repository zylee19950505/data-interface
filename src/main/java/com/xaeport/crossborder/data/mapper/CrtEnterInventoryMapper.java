package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.provider.EnterInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrtEnterInventoryMapper {

    @InsertProvider(type = EnterInventorySQLProvider.class,method="insertEnterInventoryDt")
    boolean insertEnterInventoryDt(@Param("bondInvtDt") BondInvtDt bondInvtDt);

    @InsertProvider(type = EnterInventorySQLProvider.class,method="insertEnterInventoryBsc")
    boolean insertEnterInventoryBsc(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    @SelectProvider(type = EnterInventorySQLProvider.class,method="queryEnterInventoryBsc")
    BondInvtBsc queryEnterInventoryBsc(Map<String, String> paramMap);

    @SelectProvider(type = EnterInventorySQLProvider.class,method="queryEnterInventoryDt")
    List<BondInvtDt> queryEnterInventoryDt(Map<String, String> paramMap);

    @UpdateProvider(type = EnterInventorySQLProvider.class,method="updateEnterInventoryDetail")
    void updateEnterInventoryDetail(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    @DeleteProvider(type = EnterInventorySQLProvider.class,method="deleteEnterInvenBsc")
    void deleteEnterInvenBsc(@Param("invt_no") String invt_no);

    @DeleteProvider(type = EnterInventorySQLProvider.class,method="deleteEnterInvenDt")
    void deleteEnterInvenDt(@Param("invt_no") String invt_no);
}
