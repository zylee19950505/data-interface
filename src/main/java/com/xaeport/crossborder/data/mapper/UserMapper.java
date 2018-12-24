package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.Menu;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.UserSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;

/**
 * Created by lzy on 2018/7/4.
 */
@Mapper
public interface UserMapper {

    @Select("select * from t_users")
    List<Users> getUserInfoList();

    //获取登录用户认证信息
    @SelectProvider(type= UserSQLProvider.class,method = "getUserById")
    Users getUserById(String id);

    //获取用户企业信息
    @Select("SELECT * FROM T_ENTERPRISE t where t.ID = #{enterpriseId}")
    Enterprise getEnterpriseDetail(@Param("enterpriseId") String enterpriseId);

    //获取父级菜单列表
    @SelectProvider(type= UserSQLProvider.class,method = "getRoleParentMenu")
    List<Menu> getRoleParentMenu(String id);

    //获取子级菜单列表
    @SelectProvider(type= UserSQLProvider.class,method = "getRoleChildMenu")
    List<Menu> getRoleChildMenu(String id);

    @SelectProvider(type= UserSQLProvider.class,method = "authUserLogin")
    Users authUserLogin(@Param("id") String id, @Param("loginName") String loginName);

    @SelectProvider(type= UserSQLProvider.class,method = "changePassword")
    Integer changePassword(@Param("newPassword") String newPassword, @Param("updateTime") Date updateTime, @Param("userId") String userId);

    @Select("select t.ENT_ID from T_USERS t where t.id = #{id}")
    String getEnterpriseId(@Param("id") String id);
}
