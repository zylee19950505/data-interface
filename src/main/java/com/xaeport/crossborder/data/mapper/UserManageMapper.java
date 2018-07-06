package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.UserRole;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.UserManageSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */
@Mapper
public interface UserManageMapper {

    //用户管理
    @SelectProvider(type = UserManageSQLProvider.class, method = "userList")
    List<Map<String, String>> userList(Map<String, String> paramMap);

    //用户管理—查询用户信息，角色信息
    @SelectProvider(type=UserManageSQLProvider.class,method = "findUsersById")
    Users findUsersById(String id);

    //用户管理—查询用户信息
    @SelectProvider(type=UserManageSQLProvider.class,method = "userDetail")
    Map<String, String> userDetail(@Param("id") String id);

    //用户管理—更改用户密码
    @UpdateProvider(type=UserManageSQLProvider.class,method = "updatePwById")
    boolean updatePwById(@Param("id") String id, @Param("password") String password);

    //用户管理—增添用户信息
    @InsertProvider(type=UserManageSQLProvider.class,method = "insertUser")
    int insertUser(@Param("users") Users users);

    //用户管理—增添用户角色信息表
    @InsertProvider(type=UserManageSQLProvider.class,method = "insertUserRole")
    int insertUserRole(@Param("userRole") UserRole userRole);

    //用户管理—更改用户信息
    @UpdateProvider(type=UserManageSQLProvider.class,method = "updateUser")
    int updateUser(@Param("users") Users users);

    //用户管理—更改用户角色信息表
    @UpdateProvider(type=UserManageSQLProvider.class,method = "updateUserRole")
    int updateUserRole(@Param("userRole") UserRole userRole);

    //用户管理—获取角色下拉菜单
    @SelectProvider(type=UserManageSQLProvider.class,method = "roleSelectList")
    List<Map<String, String>> roleSelectList();

    //用户管理—用户表重复查询
    @Select("select count(1) from t_users t where t.id = #{id} ")
    int idRepeat(@Param("id") String id);

    //用户管理—删除用户信息
    @Delete("delete from t_users where id = #{id} ")
    void userDelete(String id);




}
