package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.provider.EnterInventorySQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CrtEnterInventoryMapper {

    @InsertProvider(type = EnterInventorySQLProvider.class,method="insertEnterInventory")
    boolean insertEnterInventory(@Param("bondInvtDt") BondInvtDt bondInvtDt);
}
