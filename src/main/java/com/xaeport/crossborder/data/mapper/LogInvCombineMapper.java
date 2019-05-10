package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.LogInvCombine;
import com.xaeport.crossborder.data.provider.LogInvCombineSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogInvCombineMapper {

    //查询可生成整合报文的数据
    @SelectProvider(type = LogInvCombineSQLProvider.class, method = "findWaitGenerated")
    List<LogInvCombine> findWaitGenerated(Map<String, String> paramMap);

    //插入运单清单整合报文
    @InsertProvider(type = LogInvCombineSQLProvider.class, method = "insertLogInvCombineHis")
    void insertLogInvCombineHis(@Param("logInvCombine") LogInvCombine logInvCombine);

    //插入运单清单整合报文
    @DeleteProvider(type = LogInvCombineSQLProvider.class, method = "deleteLogInvCombine")
    void deleteLogInvCombine(@Param("logInvCombine") LogInvCombine logInvCombine);

    //查询数据
    @SelectProvider(type = LogInvCombineSQLProvider.class, method = "queryInventoryHead")
    ImpInventoryHead queryInventoryHead(@Param("logInvCombine") LogInvCombine logInvCombine);

    //查询数据
    @SelectProvider(type = LogInvCombineSQLProvider.class, method = "queryInventoryBody")
    List<ImpInventoryBody> queryInventoryBody(@Param("logInvCombine") LogInvCombine logInvCombine);

    //查询数据
    @SelectProvider(type = LogInvCombineSQLProvider.class, method = "queryLogistics")
    ImpLogistics queryLogistics(@Param("logInvCombine") LogInvCombine logInvCombine);

}
