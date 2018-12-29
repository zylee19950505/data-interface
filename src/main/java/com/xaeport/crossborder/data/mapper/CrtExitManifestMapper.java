package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.PassPortAcmp;
import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.CrtExitManifestSQLProvider;
import com.xaeport.crossborder.data.provider.ExitInventorySQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CrtExitManifestMapper {

    //查询出区核注清单数据
    @SelectProvider(type = CrtExitManifestSQLProvider.class, method = "queryEInventoryList")
    List<BondInvtBsc> queryEInventoryList(Map<String, String> paramMap) throws Exception;

    //查询出区核注清单数据总数
    @SelectProvider(type = CrtExitManifestSQLProvider.class, method = "queryEInventoryCount")
    Integer queryEInventoryCount(Map<String, String> paramMap) throws Exception;

    //查询出区核注清单数据数据是否重复
    @SelectProvider(type = CrtExitManifestSQLProvider.class, method = "queryBondinvtIsRepeat")
    Integer queryBondinvtIsRepeat(Map<String, String> paramMap) throws Exception;

    @Select("SELECT * FROM T_BOND_INVT_BSC t WHERE t.BOND_INVT_NO = #{bond_invt_no}")
    BondInvtBsc queryBondInvtBsc(String bond_invt_no) throws Exception;

    //更新核注清单表状态
    @UpdateProvider(type = CrtExitManifestSQLProvider.class,method = "updateBondInvt")
    void updateBondInvt(@Param("passPortHead") PassPortHead passPortHead);

    //创建插入出区核放单表头
    @InsertProvider(type = CrtExitManifestSQLProvider.class, method = "insertPassPortHead")
    void insertPassPortHead(@Param("passPortHead") PassPortHead passPortHead);

    //创建插入出区核放单表体
    @InsertProvider(type = CrtExitManifestSQLProvider.class, method = "insertPassPortAcmp")
    void insertPassPortAcmp(@Param("passPortAcmp") PassPortAcmp passPortAcmp);

    //更新核注清单表状态
    @UpdateProvider(type = CrtExitManifestSQLProvider.class, method = "updateBondInvtStatus")
    void updateBondInvtStatus(@Param("passPortHead") LinkedHashMap<String, String> passPortHead);

    //保存并更新出区核放单表头
    @InsertProvider(type = CrtExitManifestSQLProvider.class, method = "savePassPortHead")
    void savePassPortHead(@Param("passPortHead") LinkedHashMap<String, String> passPortHead, @Param("userInfo") Users userInfo);

    //保存并更新出区核放单表体
    @InsertProvider(type = CrtExitManifestSQLProvider.class, method = "savePassPortAcmpList")
    void savePassPortAcmpList(@Param("passPortHead") LinkedHashMap<String, String> passPortHead, @Param("passPortAcmp") LinkedHashMap<String, String> passPortAcmp, @Param("userInfo") Users userInfo);

}
