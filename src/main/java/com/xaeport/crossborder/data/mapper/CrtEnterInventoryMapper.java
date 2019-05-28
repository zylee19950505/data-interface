package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.provider.CrtEnterInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CrtEnterInventoryMapper {

    @InsertProvider(type = CrtEnterInventorySQLProvider.class, method = "insertEnterInventoryDt")
    boolean insertEnterInventoryDt(@Param("bondInvtDt") BondInvtDt bondInvtDt);

    @InsertProvider(type = CrtEnterInventorySQLProvider.class, method = "insertEnterInventoryBsc")
    boolean insertEnterInventoryBsc(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    @SelectProvider(type = CrtEnterInventorySQLProvider.class, method = "queryLogicVerify")
    Verify queryLogicVerify(Map<String, String> paramMap);

    @SelectProvider(type = CrtEnterInventorySQLProvider.class, method = "queryEnterInventoryBsc")
    BondInvtBsc queryEnterInventoryBsc(Map<String, String> paramMap);

    @SelectProvider(type = CrtEnterInventorySQLProvider.class, method = "queryEnterInventoryDt")
    List<BondInvtDt> queryEnterInventoryDt(Map<String, String> paramMap);

    //修改入区核注清单表头信息
    @UpdateProvider(type = CrtEnterInventorySQLProvider.class, method = "updateEnterInvtHead")
    void updateEnterInvtHead(@Param("bondInvtBsc") BondInvtBsc bondInvtBsc);

    //修改入区核注清单表体信息
    @UpdateProvider(type = CrtEnterInventorySQLProvider.class, method = "updateEnterInvtBody")
    void updateEnterInvtBody(@Param("bondInvtDt") LinkedHashMap<String, String> bondInvtDt, @Param("users") Users users);

    @DeleteProvider(type = CrtEnterInventorySQLProvider.class, method = "deleteEnterInvenBsc")
    void deleteEnterInvenBsc(@Param("etps_inner_invt_no") String etps_inner_invt_no);

    @DeleteProvider(type = CrtEnterInventorySQLProvider.class, method = "deleteEnterInvenDt")
    void deleteEnterInvenDt(@Param("etps_inner_invt_no") String etps_inner_invt_no);

//    @Select("SELECT t.BWS_NO FROM T_BWL_HEAD_TYPE t WHERE t.CRT_ENT_ID = #{entId}")
//    String queryBws_no(String entId);

    //查询账册表头账册编码
    @Select("SELECT * FROM ( " +
            "select t.BWS_NO FROM T_BWL_HEAD_TYPE t WHERE t.CRT_ENT_ID = #{ent_id} ORDER BY t.CRT_TIME ASC " +
            ") WHERE ROWNUM = 1")
    String queryBws_no(@Param("ent_id") String ent_id);

    @SelectProvider(type = CrtEnterInventorySQLProvider.class, method = "getMaxGdsSeqno")
    String getMaxGdsSeqno(@Param("customs_code") String customs_code);
}
