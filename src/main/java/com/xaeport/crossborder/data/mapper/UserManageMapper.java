package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.UserRole;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.UserManageSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/4/27.
 */
@Mapper
public interface UserManageMapper {

    //用户管理
    @SelectProvider(type = UserManageSQLProvider.class, method = "userList")
    List<Map<String, String>> userList(Map<String, String> paramMap);

    //用户管理—查询用户信息，角色信息
//    @Select("select t1.id,t1.ic,t1.loginName,t1.phone,t1.email,t1.password,t1.userType,t2.roleid from cpo_userinfo t1,cpo_user_role t2 where t1.id = t2.userinfoid and t1.id = #{id}")
    @SelectProvider(type=UserManageSQLProvider.class,method = "findUsersById")
    Users findUsersById(String id);

    //用户管理—查询用户信息
//    @Select("select t1.id,t1.ic,t1.loginName,t1.userType,t1.state,t1.phone,t1.email,t1.password,t2.roleId,t3.r_name from cpo_userinfo t1,cpo_user_role t2,cpo_role t3 where t1.id = #{id} and t1.id = t2.userinfoid and t2.roleId = t3.r_id")
    @SelectProvider(type=UserManageSQLProvider.class,method = "userDetail")
    Map<String, String> userDetail(@Param("id") String id);

    //用户管理—更改用户密码
//    @Update("update cpo_userinfo t set t.password = #{password},t.updatetime = sysdate where t.id = #{id} ")
    @UpdateProvider(type=UserManageSQLProvider.class,method = "updatePwById")
    boolean updatePwById(@Param("id") String id, @Param("password") String password);

    //用户管理—增添用户信息
//    @Insert("insert into cpo_userinfo (id,ic,loginName,phone,email,password,state,creatorId,createTime,updatorId,updateTime,userType)" +
//            " values (#{users.id},#{users.ic},#{users.loginName},#{users.phone},#{users.email},#{users.password},#{users.state},#{users.creatorId},#{users.createTime},#{users.updatorId},#{users.updateTime},#{users.userType})")
    @InsertProvider(type=UserManageSQLProvider.class,method = "insertUser")
    int insertUser(@Param("users") Users users);

    //用户管理—增添用户角色信息表
//    @Insert("insert into cpo_user_role (id,userInfoId,roleId,state,creatorId,createTime,updatorId,updateTime)" +
//            " values (#{userRole.id},#{userRole.userInfoId},#{userRole.roleId},#{userRole.state},#{userRole.creatorId},#{userRole.createTime},#{userRole.updatorId},#{userRole.updateTime})")
    @InsertProvider(type=UserManageSQLProvider.class,method = "insertUserRole")
    int insertUserRole(@Param("userRole") UserRole userRole);

    //用户管理—更改用户信息
//    @Update("update cpo_userinfo t set t.ic = #{users.ic},t.loginName = #{users.loginName},t.phone = #{users.phone},t.email = #{users.email},t.state = #{users.state},t.updateTime = #{users.updateTime},t.userType = #{users.userType} where t.id = #{users.id}")
    @UpdateProvider(type=UserManageSQLProvider.class,method = "updateUser")
    int updateUser(@Param("users") Users users);

    //用户管理—更改用户角色信息表
//    @Update("update cpo_user_role t set t.roleId = #{userRole.roleId},t.updateTime = #{userRole.updateTime} where t.userInfoId = #{userRole.userInfoId}")
    @UpdateProvider(type=UserManageSQLProvider.class,method = "updateUserRole")
    int updateUserRole(@Param("userRole") UserRole userRole);

    //用户管理—获取角色下拉菜单
//    @Select("SELECT r.r_id, r.r_name FROM cpo_role r ORDER BY r.createTime ASC")
    @SelectProvider(type=UserManageSQLProvider.class,method = "roleSelectList")
    List<Map<String, String>> roleSelectList();

    //用户管理—用户表重复查询
    @Select("select count(1) from cpo_userinfo t where t.id = #{id} ")
    int idRepeat(@Param("id") String id);

    //用户管理—删除用户信息
    @Delete("delete from cpo_userinfo where id = #{id} ")
    void userDelete(String id);




}
